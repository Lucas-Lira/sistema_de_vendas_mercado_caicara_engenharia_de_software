package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Turno
{
    private int id;
    private String periodo, hor_inicio, hor_fim;

    public Turno(int id, String periodo, String hor_inicio, String hor_fim)
    {
        this.id = id;
        this.periodo = periodo;
        this.hor_inicio = hor_inicio;
        this.hor_fim = hor_fim;
    }

    public Turno()
    { this(0, "", "00:00", "00:00"); }

    public Turno(String periodo, String hor_inicio, String hor_fim)
    { this(0, periodo, hor_inicio, hor_fim); }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public String getPeriodo()
    { return periodo; }

    public void setPeriodo(String periodo)
    { this.periodo = periodo; }

    public String getHor_inicio()
    { return hor_inicio; }

    public void setHor_inicio(String hor_inicio)
    { this.hor_inicio = hor_inicio; }

    public String getHor_fim()
    { return hor_fim; }

    public void setHor_fim(String hor_fim)
    { this.hor_fim = hor_fim; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static List buscaTurnoPeriodo(String periodo)
    {
        List <Turno> listturno = new ArrayList();
        String query = "select * from turno where tur_periodo like '" + periodo + "%'";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listturno.add(new Turno(rs.getInt("tur_cod"), rs.getString("tur_periodo").toUpperCase(), 
                        rs.getString("tur_horini"), rs.getString("tur_horfim")));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listturno;
    }
    
    public static List buscaTurnoCodigo(int cod)
    {
        List <Turno> listturno = new ArrayList();
        String query = "select * from turno where tur_cod = " + cod;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listturno.add(new Turno(rs.getInt("tur_cod"), rs.getString("tur_periodo").toUpperCase(), 
                        rs.getString("tur_horini"), rs.getString("tur_horfim")));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listturno;
    }
    
    public static List buscaTurnoHoraInicio(String hora)
    {
        List <Turno> listturno = new ArrayList();
        String query = "select * from turno where tur_horini like '" + hora + "%'";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listturno.add(new Turno(rs.getInt("tur_cod"), rs.getString("tur_periodo").toUpperCase(), 
                        rs.getString("tur_horini"), rs.getString("tur_horfim")));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listturno;
    }
    
    public static List buscaTurnoHoraFim(String hora)
    {
        List <Turno> listturno = new ArrayList();
        String query = "select * from turno where tur_horfim like '" + hora + "%'";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listturno.add(new Turno(rs.getInt("tur_cod"), rs.getString("tur_periodo").toUpperCase(), 
                        rs.getString("tur_horini"), rs.getString("tur_horfim")));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listturno;
    }
    
    public static List get(String filtro)
    {
        List <Turno> listturno = new ArrayList();
        String query = "select * from turno";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listturno.add(new Turno(rs.getInt("tur_cod"), rs.getString("tur_periodo").toUpperCase(), 
                        rs.getString("tur_horini"), rs.getString("tur_horfim")));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listturno;
    }
    // ------------------------------------------------------------------------------------------------------------------
}