package prjmercadocaiçara.db.modelos;

import java.util.List;
import prjmercadocaiçara.db.persistencia.EnderecoBD;

public class Endereco
{
    private int id, numero;
    private String rua, cep, bairro;
    private Cidade cidade;

    public Endereco(int id, int numero, String rua, String cep, String bairro, Cidade cidade)
    {
        this.id = id;
        this.numero = numero;
        this.rua = rua;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public Endereco()
    { this(0, 0, "", "", "", null); }

    public Endereco(int numero, String rua, String cep, String bairro, Cidade cidade)
    { this(0, numero, rua, cep, bairro, cidade); }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public int getNumero()
    { return numero; }

    public void setNumero(int numero)
    { this.numero = numero; }

    public String getRua()
    { return rua; }

    public void setRua(String rua)
    { this.rua = rua; }

    public String getCep()
    { return cep; }

    public void setCep(String cep)
    { this.cep = cep; }

    public String getBairro()
    { return bairro; }

    public void setBairro(String bairro)
    { this.bairro = bairro; }

    public Cidade getCidade()
    { return cidade; }

    public void setCidade(Cidade cidade)
    { this.cidade = cidade; }
    
    static public boolean salvar(Endereco end)
    {
        return EnderecoBD.salvar(end);
    }
    
    static public boolean alterar(Endereco end)
    {
        return EnderecoBD.alterar(end);
    }
    
    static public Endereco buscar(String filtro)
    {
        return EnderecoBD.buscarEndereco(filtro);
    }
    
    static public List<Endereco> buscaEnderecoCodigo(int cod)
    {
        return EnderecoBD.buscaEnderecoCodigo(cod);
    }
    
    static public boolean apagar(int codigo)
    {
        return EnderecoBD.apagar(codigo);
    }
    
    public static String retiraMascaraCEP(String cep)
    {
        String str1, str2;
        for(int i = 0; i < cep.length(); i++)
        {
            if(cep.charAt(i) == '-')
            {
                str1 = cep.substring(0, i);
                str2 = cep.substring(i+1, cep.length());
                return str1+str2;
            }
        }
        return "";
    }
    
}