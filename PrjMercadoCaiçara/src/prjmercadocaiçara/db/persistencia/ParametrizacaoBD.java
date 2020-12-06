
package prjmercadocaiçara.db.persistencia;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import prjmercadocaiçara.db.modelos.Endereco;
import prjmercadocaiçara.db.modelos.Parametrizacao;

public class ParametrizacaoBD {
    static public boolean salvar(Parametrizacao p)
    {
        boolean result = false;
        EnderecoBD endbd = new EnderecoBD();
        if (endbd.salvar(p.getEndereco())) 
        {
            int end_cod = Banco.getCon().getMaxPK("endereco", "end_cod");
            String sql = "insert into parametrizacao (par_nomefantasia,par_razaosocial,par_telefone,par_email,par_site,par_fonte,par_corfundo,end_cod) "
                    + "values ('$1','$2','$3','$4','$5','$6','$7',$8)";
            sql = sql.replace("$1", p.getNome());
            sql = sql.replace("$2", p.getRazao());
            sql = sql.replace("$3", p.getTelefone());
            sql = sql.replace("$4", p.getEmail());
            sql = sql.replace("$5", p.getSite());
            sql = sql.replace("$6", p.getFonte());
            sql = sql.replace("$7", p.getCor());
            sql = sql.replace("$8", ""+end_cod);
            if(Banco.getCon().manipular(sql))
            {
                if (p.getLogogrande() != null) 
                {
                    try 
                    {
                        BufferedImage bimg;
                        PreparedStatement st = Banco.getCon().getConnect().prepareStatement(
                                "update parametrizacao set par_logogrande=?");
                        bimg = SwingFXUtils.fromFXImage(p.getLogogrande(), null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write((RenderedImage) bimg, "jpg", baos);
                        InputStream is = new ByteArrayInputStream(baos.toByteArray());
                        st.setBinaryStream(1, is, baos.toByteArray().length);
                        st.executeUpdate();
                        result = true;
                        if (p.getLogopequeno()!= null) 
                        {
                            st = Banco.getCon().getConnect().prepareStatement(
                                "update parametrizacao set par_logopequeno=?");
                            bimg = SwingFXUtils.fromFXImage(p.getLogopequeno(), null);
                            baos = new ByteArrayOutputStream();
                            ImageIO.write((RenderedImage) bimg, "jpg", baos);
                            is = new ByteArrayInputStream(baos.toByteArray());
                            st.setBinaryStream(1, is, baos.toByteArray().length);
                            st.executeUpdate();
                            result = true;
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        result = false;
                    }
                }
            }
        }
        return result;
    }
    
    static public boolean alterar(Parametrizacao p)
    {
        boolean result = true;
        EnderecoBD endbd = new EnderecoBD();
        Endereco en = endbd.buscarEndereco("end_cep='"+p.getEndereco().getCep()+"' and end_rua='"+p.getEndereco().getRua()+"' and end_num='"+p.getEndereco().getNumero()+"'");
        if(en==null)
        {
            result = endbd.salvar(p.getEndereco());
            p.getEndereco().setId(Banco.getCon().getMaxPK("endereco", "end_cod"));
        }
        else
            p.getEndereco().setId(en.getId());
        if(result)
        {
            String sql = "update parametrizacao set par_nomefantasia='$1',par_razaosocial='$2',par_telefone='$3',par_email='$4',par_site='$5',par_fonte='$6',par_corfundo='$7',end_cod=$8";
            sql = sql.replace("$1", p.getNome());
            sql = sql.replace("$2", p.getRazao());
            sql = sql.replace("$3", p.getTelefone());
            sql = sql.replace("$4", p.getEmail());
            sql = sql.replace("$5", p.getSite());
            sql = sql.replace("$6", p.getFonte());
            sql = sql.replace("$7", p.getCor());
            sql = sql.replace("$8", ""+p.getEndereco().getId());
            if(Banco.getCon().manipular(sql))
            {
                if (p.getLogogrande() != null) 
                {
                    try 
                    {
                        BufferedImage bimg;
                        PreparedStatement st = Banco.getCon().getConnect().prepareStatement(
                                "update parametrizacao set par_logogrande=?");
                        bimg = SwingFXUtils.fromFXImage(p.getLogogrande(), null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write((RenderedImage) bimg, "jpg", baos);
                        InputStream is = new ByteArrayInputStream(baos.toByteArray());
                        st.setBinaryStream(1, is, baos.toByteArray().length);
                        st.executeUpdate();
                        result = true;
                        if (p.getLogopequeno()!= null) 
                        {
                            st = Banco.getCon().getConnect().prepareStatement(
                                "update parametrizacao set par_logopequeno=?");
                            bimg = SwingFXUtils.fromFXImage(p.getLogopequeno(), null);
                            baos = new ByteArrayOutputStream();
                            ImageIO.write((RenderedImage) bimg, "jpg", baos);
                            is = new ByteArrayInputStream(baos.toByteArray());
                            st.setBinaryStream(1, is, baos.toByteArray().length);
                            st.executeUpdate();
                            result = true;
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        result = false;
                    }
                }
            }
        }
        return result;
    }
    
    static public Parametrizacao buscar()
    {
        Parametrizacao pr=null;
        ResultSet rs = Banco.getCon().consultar("select * from parametrizacao");
        try
        {
            if(rs.next())
            {
                EnderecoBD endbd = new EnderecoBD();
                pr = new Parametrizacao(rs.getString("par_nomefantasia"),rs.getString("par_razaosocial"),rs.getString("par_telefone"),
                    rs.getString("par_email"),rs.getString("par_site"),rs.getString("par_fonte"),rs.getString("par_corfundo"),carregarLogo(rs.getBytes("par_logogrande")),carregarLogo(rs.getBytes("par_logopequeno")),endbd.buscarEndereco("end_cod="+rs.getInt("end_cod")));
                
            }
        }
        catch(SQLException e)
        {   
            System.out.println(Banco.getCon().getMensagemErro());
            System.out.println(e.getMessage());
        }
        return pr;
    }
    
    static private Image carregarLogo(byte[] imgbytes)
    {
        BufferedImage bimage=null;    
        try {
            InputStream in = new ByteArrayInputStream(imgbytes);
            bimage = ImageIO.read(in);
        } catch (Exception ex) {}
        return SwingFXUtils.toFXImage(bimage, null);
    }
}
