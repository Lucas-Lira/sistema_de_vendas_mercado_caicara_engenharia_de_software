package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Prateleira
{
    private int id;
    private String descricao;
    private Fileira fileira;

    public Prateleira(int id, String descricao, Fileira fileira)
    {
        this.id = id;
        this.descricao = descricao;
        this.fileira = fileira;
    }
    
    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public String getDescricao()
    { return descricao; }

    public void setDescricao(String descricao)
    { this.descricao = descricao; }

    public Fileira getFileira()
    { return fileira; }

    public void setFileira(Fileira fileira)
    { this.fileira = fileira; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static List buscaPrateleiraDescricao(String descricao)
    {
        List <Prateleira> listprateleira = get("prat_descricao like '" + descricao + "'");
        return listprateleira;
    }
    
    public static List buscaPrateleiraCodigo(int cod)
    {
        List <Prateleira> listprateleira = get("prat_cod = " + cod);
        return listprateleira;
    }
    
    public static List buscaPrateleiraFileiraCodigo(int cod)
    {
        List <Prateleira> listprateleira = get("fil_cod = " + cod);
        return listprateleira;
    }
    
    public static List buscaPrateleiraFileiraCodigoSemFileirasDependentes(int cod)
    {
        List <Prateleira> listprateleira = getSemFileirasDependentes("fil_cod = " + cod);
        return listprateleira;
    }
    
    public static List get(String filtro)
    {
        List <Prateleira> listprateleira = new ArrayList();
        String query = "select * from prateleira";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList aux = null;
            while(rs.next())
            {
                aux = (ArrayList) Fileira.buscaFileiraCodigo(rs.getInt("fil_cod"));
                listprateleira.add(new Prateleira(rs.getInt("prat_cod"), rs.getString("prat_descricao").toUpperCase(), 
                        (Fileira) aux.get(0)));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listprateleira;
    }
    
    public static List getSemFileirasDependentes(String filtro)
    {
        List <Prateleira> listprateleira = new ArrayList();
        String query = "select * from prateleira";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listprateleira.add(new Prateleira(rs.getInt("prat_cod"), rs.getString("prat_descricao").toUpperCase(), null));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listprateleira;
    }
    // ------------------------------------------------------------------------------------------------------------------
}