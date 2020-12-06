package prjmercadocaiçara.db.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.modelos.Estado;

public class EstadoBD
{
    public List buscaEstadoNome(String nome)
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
    
    public List buscaEstadoCodigo(int cod)
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
    
    public List get(String filtro)
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
}