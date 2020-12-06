package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Horario
{
    private int dia_semana; //'dia_semana' É UM DOS ID'S!
    private Turno turno;

    public Horario(int dia_semana, Turno turno)
    {
        this.dia_semana = dia_semana;
        this.turno = turno;
    }

    public Horario()
    { this(1, null); }

    public Horario(Turno turno)
    { this(1, turno); }

    public int getDia_semana()
    { return dia_semana; }

    public void setDia_semana(int dia_semana)
    { this.dia_semana = dia_semana; }

    public Turno getTurno()
    { return turno; }

    public void setTurno(Turno turno)
    { this.turno = turno; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static boolean salvar(Horario h, int fun)
    {
        String sql = "";
        sql = "insert into horario (fun_cod, tur_cod, hor_diasemana)"
                + " values ($1, $2, $3)";
        sql = sql.replace("$1", "" + fun);
        sql = sql.replace("$2", "" + h.getTurno().getId());
        sql = sql.replace("$3", "" + h.getDia_semana());
        
        return Banco.getCon().manipular(sql);
    }
    
    public static boolean apagarHorFunCod(int id)
    {
        return Banco.getCon().manipular("delete from horario where fun_cod = " + id);
    }
    
    public static List buscaHorarioFunCod(int fun)
    {
        List <Horario> listhorario = new ArrayList();
        List <Turno> listturno = null;
        Turno turno = new Turno();
        String query = "select * from horario where fun_cod = " + fun;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listturno = turno.buscaTurnoCodigo(rs.getInt("tur_cod"));
                if(listturno.size() > 0)
                {
                    listhorario.add(new Horario(rs.getInt("hor_diasemana"), listturno.get(0)));
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listhorario;
    }
    
    public static List get(String filtro)
    {
        List <Horario> listhorario = new ArrayList();
        List <Turno> listturno = null;
        Turno turno = new Turno();
        String query = "select * from horario";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listturno = turno.buscaTurnoCodigo(rs.getInt("tur_cod"));
                if(listturno.size() > 0)
                {
                    listhorario.add(new Horario(rs.getInt("hor_diasemana"), listturno.get(0)));
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listhorario;
    }
    // ------------------------------------------------------------------------------------------------------------------
}