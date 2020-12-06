package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Fileira
{
    private int id;
    private String descricao;
    private ArrayList<Prateleira> prateleiras;

    public Fileira(int id, String descricao)
    {
        this.id = id;
        this.descricao = descricao;
        prateleiras = new ArrayList();
    }
    
    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public String getDescricao()
    { return descricao; }

    public void setDescricao(String descricao)
    { this.descricao = descricao; }

    public ArrayList<Prateleira> getPrateleiras()
    { return prateleiras; }
    
    public void clearPrateleiras()
    { prateleiras = new ArrayList(); }
    
    public void addPrateleiras(Prateleira p)
    { prateleiras.add(p); }
    
    public void setPrateleiras(List<Prateleira> prateleiras)
    {
        this.prateleiras = (ArrayList<Prateleira>) prateleiras;
    }
    
    public int buscaPrateleira(Prateleira p)
    {
        int pos = -1;
        if(!prateleiras.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < prateleiras.size() && !achou; i++)
                if(prateleiras.get(i) == p)
                {
                    pos= i;
                    achou= true;
                }
        }
        return pos;
    }
        
    public boolean removePrateleira(Prateleira p)
    {
        int indice = buscaPrateleira(p);
        if(indice != (-1))
        {
            prateleiras.remove(indice);
            return true;
        }
        
        return false;
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static List buscaFileiraDescricao(String descricao)
    {
        List <Fileira> listfileira = get("fil_descricao like '" + descricao + "'");
        return listfileira;
    }
    
    public static List buscaFileiraCodigo(int cod)
    {
        List <Fileira> listfileira = get("fil_cod = " + cod);
        return listfileira;
    }
    
    public static List get(String filtro)
    {
        List <Fileira> listfileira = new ArrayList();
        Fileira f = null;
        List <Prateleira> listprateleira = null;
        String query = "select * from fileira";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listprateleira = Prateleira.buscaPrateleiraFileiraCodigoSemFileirasDependentes(rs.getInt("fil_cod"));
                if(!listprateleira.isEmpty())
                {
                    f = new Fileira(rs.getInt("fil_cod"), rs.getString("fil_descricao").toUpperCase());
                    
                    for(int i = 0; i < listprateleira.size(); i++)
                        f.addPrateleiras(listprateleira.get(i));
                    
                    listfileira.add(f);
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listfileira;
    }
    // ------------------------------------------------------------------------------------------------------------------
}