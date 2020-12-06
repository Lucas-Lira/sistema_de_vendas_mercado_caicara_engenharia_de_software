package prjmercadocaiçara.db.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.modelos.Cidade;
import prjmercadocaiçara.db.modelos.Endereco;
import prjmercadocaiçara.db.modelos.Estado;

public class EnderecoBD
{
    public static boolean salvar(Endereco end)
    {
        String sql = "";
        int cod_end = 0;
        sql = "insert into endereco (end_cep, end_rua, end_bairro, end_num, cid_cod)"
                + " values ($1, '$2', '$3', $4, $5)";
        sql = sql.replace("$1", end.getCep());
        sql = sql.replace("$2", end.getRua().toUpperCase());
        sql = sql.replace("$3", end.getBairro().toUpperCase());
        sql = sql.replace("$4", "" + end.getNumero());
        sql = sql.replace("$5", "" + end.getCidade().getId());
        
        try{ cod_end = (int) Banco.getCon().getMaxPK("endereco", "end_cod"); }catch(Exception ex){ cod_end = 0; }
        end.setId(cod_end);
        
        return Banco.getCon().manipular(sql);
    }
    
    public static boolean alterar(Endereco end)
    {
        String sql = "";
        sql = "update endereco set end_cep = $1, end_rua = '$2', end_bairro = '$3', end_num = $4, "
                + "cid_cod = $5 where end_cod = " + end.getId();
        
        sql = sql.replace("$1", end.getCep());
        sql = sql.replace("$2", end.getRua().toUpperCase());
        sql = sql.replace("$3", end.getBairro().toUpperCase());
        sql = sql.replace("$4", "" + end.getNumero());
        sql = sql.replace("$5", "" + end.getCidade().getId());
        
        return Banco.getCon().manipular(sql);
    }
    
    public static boolean apagar(int id)
    {
        return Banco.getCon().manipular("delete from endereco where end_cod = " + id);
    }
    
    public static List<Endereco> buscaEnderecoCodigo(int cod)
    {
        List <Endereco> listendereco = new ArrayList();
        List <Cidade> listcid = null;
        CidadeBD cidbd = new CidadeBD();
        String query = "select * from endereco where end_cod = " + cod;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listcid = cidbd.buscaCidadeCodigo(rs.getInt("cid_cod"));
                if(listcid.size() > 0)
                {
                    listendereco.add(new Endereco(rs.getInt("end_cod"), rs.getInt("end_num"), rs.getString("end_rua").toUpperCase(), 
                            "" +  rs.getInt("end_cep"), rs.getString("end_bairro").toUpperCase(), listcid.get(0)));
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listendereco;
    }
    
    public static List get(String filtro)
    {
        List <Endereco> listendereco = new ArrayList();
        List <Cidade> listcid = null;
        CidadeBD cidbd = new CidadeBD();
        String query = "select * from endereco";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listcid = CidadeBD.buscaCidadeCodigo(rs.getInt("cid_cod"));
                if(listcid.size() > 0)
                {
                    listendereco.add(new Endereco(rs.getInt("end_cod"), rs.getInt("end_num"), rs.getString("end_rua").toUpperCase(), 
                            "" +  rs.getInt("end_cep"), rs.getString("end_bairro").toUpperCase(), listcid.get(0)));
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listendereco;
    }
    
    public static Endereco buscarEndereco(String filtro)
    {
        Endereco end = null;
        String query = "select * from endereco inner join cidade on endereco.cid_cod=cidade.cid_cod "
                + "inner join estado on cidade.est_cod=estado.est_cod";
        if(!filtro.isEmpty())
            query+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            if(rs.next())
            {
                end = new Endereco(rs.getInt("end_cod"),rs.getInt("end_num"),rs.getString("end_rua"),""+rs.getInt("end_cep"),rs.getString("end_bairro"),
                                    new Cidade(rs.getInt("cid_cod"),rs.getString("cid_nome"),new Estado(rs.getInt("est_cod"),
                                            rs.getString("est_nome"),rs.getString("est_sgl"))));
            }
        }
        catch(SQLException e){}
        return end;
    }
}