
package prjmercadocaiçara.db.controladoras;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import prjmercadocaiçara.db.modelos.Cidade;
import prjmercadocaiçara.db.modelos.Endereco;
import prjmercadocaiçara.db.modelos.Estado;
import prjmercadocaiçara.db.modelos.Parametrizacao;
import prjmercadocaiçara.db.persistencia.ParametrizacaoBD;

public class CtrlParametrizacao 
{
    private static CtrlParametrizacao instancia;
    private static Parametrizacao parametrizacao;
    private CtrlParametrizacao(){
    }
    public static CtrlParametrizacao obterInstancia()
    {
        if(instancia == null)
        {
            instancia = new CtrlParametrizacao();
            
        }
        return instancia;
    }
    
    public void aplicarEstilo(Node root)
    {
        try
            {
                root.setStyle("-fx-background-color:"+parametrizacao.getCor()+"; -fx-font-family: "+parametrizacao.getFonte()+";");
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
    }
    
    public boolean salvar(String nome, String razao, String telefone, String email, 
            String site, int numero, String rua, String cep, String bairro, int codigocidade, String nomecidade, String sigla, String fonte, String cor, Image logogrande, Image logopequeno)
    {
        parametrizacao = new Parametrizacao();
        parametrizacao.setNome(nome);
        parametrizacao.setRazao(razao);
        parametrizacao.setEmail(email);
        parametrizacao.setCor(cor);
        parametrizacao.setFonte(fonte);
        parametrizacao.setLogogrande(logogrande);
        parametrizacao.setLogopequeno(logopequeno);
        parametrizacao.setSite(site);
        parametrizacao.setTelefone(telefone);
        parametrizacao.setEndereco(new Endereco(numero,rua,cep,bairro,new Cidade(codigocidade,nomecidade,new Estado("",sigla))));
        return Parametrizacao.salvar(parametrizacao);
    }
    
    public boolean alterar(String nome, String razao, String telefone, String email, 
            String site, int numero, String rua, String cep, String bairro,  int codigocidade, String nomecidade, String sigla, String fonte, String cor, Image logogrande, Image logopequeno)
    {
        parametrizacao.setNome(nome);
        parametrizacao.setRazao(razao);
        parametrizacao.setEmail(email);
        parametrizacao.setCor(cor);
        parametrizacao.setFonte(fonte);
        parametrizacao.setLogogrande(logogrande);
        parametrizacao.setLogopequeno(logopequeno);
        parametrizacao.setSite(site);
        parametrizacao.setTelefone(telefone);
        parametrizacao.setEndereco(new Endereco(numero,rua,cep,bairro,new Cidade(codigocidade,nomecidade,new Estado("",sigla))));
        return Parametrizacao.alterar(parametrizacao);
    }
    
    public boolean buscar()
    {
        parametrizacao = Parametrizacao.buscar();
        return parametrizacao!=null;
    }
    
    public Map obterParametrizacao()
    {
        if(parametrizacao!=null)
        {
            Map data = new HashMap<>();
            data.put("nome", parametrizacao.getNome());
            data.put("razao", parametrizacao.getRazao());
            data.put("telefone", parametrizacao.getTelefone());
            data.put("email", parametrizacao.getEmail());
            data.put("site", parametrizacao.getSite());
            data.put("cep", parametrizacao.getEndereco().getCep());
            data.put("rua", parametrizacao.getEndereco().getRua());
            data.put("bairro", parametrizacao.getEndereco().getBairro());
            data.put("cidade", parametrizacao.getEndereco().getCidade().toString());
            data.put("cor", parametrizacao.getCor());
            data.put("fonte", parametrizacao.getFonte());
            data.put("numero", parametrizacao.getEndereco().getNumero());
            return data;
        }
        return null;
    }
    
    public Image obterLogoGrande()
    {
        return parametrizacao.getLogogrande();
    }
    
    public Image obterLogoPequeno()
    {
        return parametrizacao.getLogopequeno();
    }
}
