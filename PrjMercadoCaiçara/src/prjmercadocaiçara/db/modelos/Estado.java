package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Estado
{
    private int id;
    private String nome, sigla;

    public Estado(String nome, String sigla)
    { this(0, nome, sigla); }

    public Estado(int id, String nome, String sigla)
    {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }
    
    public Estado()
    { this(0, "", ""); }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public String getNome()
    { return nome; }

    public void setNome(String nome)
    { this.nome = nome; }

    public String getSigla()
    { return sigla; }

    public void setSigla(String sigla)
    { this.sigla = sigla; }

    @Override
    public String toString()
    { return nome; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static List buscaEstadoNome(String nome)
    {
        List <Estado> listestado = new ArrayList();
        String query = "select * from estado where est_nome like '" + nome + "%'";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listestado.add(new Estado(rs.getInt("est_cod"), rs.getString("est_nome").toUpperCase(), 
                        rs.getString("est_sgl").toUpperCase()));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listestado;
    }
    
    public static List buscaEstadoCodigo(int cod)
    {
        List <Estado> listestado = new ArrayList();
        String query = "select * from estado where est_cod = " + cod;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listestado.add(new Estado(rs.getInt("est_cod"), rs.getString("est_nome").toUpperCase(), 
                        rs.getString("est_sgl").toUpperCase()));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listestado;
    }
    
    public static List get(String filtro)
    {
        List <Estado> listestado = new ArrayList();
        String query = "select * from estado";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listestado.add(new Estado(rs.getInt("est_cod"), rs.getString("est_nome").toUpperCase(), 
                        rs.getString("est_sgl").toUpperCase()));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listestado;
    }
    // ------------------------------------------------------------------------------------------------------------------
}