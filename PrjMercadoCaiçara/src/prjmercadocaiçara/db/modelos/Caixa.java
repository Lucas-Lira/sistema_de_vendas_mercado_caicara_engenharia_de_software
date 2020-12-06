package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Caixa
{
    private LocalDate data;
    private int id;
    private double valorini, totalentradas, totalsaidas;
    private LocalDate datafechamento;

    public Caixa(LocalDate data, int id, double valorini, double totalentradas, double totalsaidas, LocalDate datafechamento)
    {
        this.data = data;
        this.id = id;
        this.valorini = valorini;
        this.totalentradas = totalentradas;
        this.totalsaidas = totalsaidas;
        this.datafechamento = datafechamento;
    }
    
    public Caixa(){ this(LocalDate.now(), 0, 0, 0, 0, null); }
    
    public Caixa(LocalDate data, int id, double valorini, double totalentradas, double totalsaidas)
    { this(data, id, valorini, totalentradas, totalsaidas, null); }

    public LocalDate getData()
    { return data; }

    public void setData(LocalDate data)
    { this.data = data; }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public double getValorini()
    { return valorini; }

    public void setValorini(double valorini)
    { this.valorini = valorini; }

    public double getTotalentradas()
    { return totalentradas; }

    public void setTotalentradas(double totalentradas)
    { this.totalentradas = totalentradas; }

    public double getTotalsaidas()
    { return totalsaidas; }

    public void setTotalsaidas(double totalsaidas)
    { this.totalsaidas = totalsaidas; }

    public LocalDate getDatafechamento()
    { return datafechamento; }

    public void setDatafechamento(LocalDate datafechamento)
    { this.datafechamento = datafechamento; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static boolean salvar(Caixa c)
    {
        String sql = "";
        
        if(c.getDatafechamento() != null)
        {
            sql = "insert into caixa (cai_data, fun_cod, cai_valorini, cai_totalentradas, cai_totalsaidas, cai_datafechamento)"
                    + " values ('$1', $2, $3, $4, $5, '$6')";
            sql = sql.replace("$1", c.getData().toString());
            sql = sql.replace("$2", "" + c.getId());
            sql = sql.replace("$3", "" + c.getValorini());
            sql = sql.replace("$4", "" + c.getTotalentradas());
            sql = sql.replace("$5", "" + c.getTotalsaidas());
            sql = sql.replace("$6", c.getDatafechamento().toString());
        }
        else
        {
            sql = "insert into caixa (cai_data, fun_cod, cai_valorini, cai_totalentradas, cai_totalsaidas)"
                    + " values ('$1', $2, $3, $4, $5)";
            sql = sql.replace("$1", c.getData().toString());
            sql = sql.replace("$2", "" + c.getId());
            sql = sql.replace("$3", "" + c.getValorini());
            sql = sql.replace("$4", "" + c.getTotalentradas());
            sql = sql.replace("$5", "" + c.getTotalsaidas());
        }
        
        return Banco.getCon().manipular(sql);
    }
    
    public static boolean alterar(Caixa c)
    {
        String sql = "";
        
        if(c.getDatafechamento() != null)
        {
            sql = "update caixa set cai_valorini = $1, cai_totalentradas = $2, "
                    + "cai_totalsaidas = $3, cai_datafechamento = '$4' where fun_cod = " + c.getId() + 
                    " and cai_data = '" + c.getData().toString() + "'";
        
            sql = sql.replace("$1", "" + c.getValorini());
            sql = sql.replace("$2", "" + c.getTotalentradas());
            sql = sql.replace("$3", "" + c.getTotalsaidas());
            sql = sql.replace("$4", c.getDatafechamento().toString());
        }
        else
        {
            sql = "update caixa  set cai_valorini = $1, cai_totalentradas = $2, "
                    + "cai_totalsaidas = $3, cai_datafechamento = null where fun_cod = " + c.getId() + 
                    " and cai_data = '" + c.getData().toString() + "'";
        
            sql = sql.replace("$1", "" + c.getValorini());
            sql = sql.replace("$2", "" + c.getTotalentradas());
            sql = sql.replace("$3", "" + c.getTotalsaidas());
        }
        
        return Banco.getCon().manipular(sql);
    }
    
    public static List buscaCaixaFuncionarioEData(int fun_cod, LocalDate d)
    {
        List <Caixa> listcaixa = new ArrayList();
        String query = "select * from caixa where fun_cod = " + fun_cod + " and cai_data = '" + d.toString() + "'";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                LocalDate data;
                try{ data = rs.getDate("cai_datafechamento").toLocalDate(); }catch(Exception e){ data = null; }
                
                listcaixa.add(new Caixa(rs.getDate("cai_data").toLocalDate(), rs.getInt("fun_cod"),
                        rs.getDouble("cai_valorini"), rs.getDouble("cai_totalentradas"), 
                        rs.getDouble("cai_totalsaidas"), data));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listcaixa;
    }
    
    public static List buscaCaixaData(LocalDate data)
    {
        List <Caixa> listcaixa = new ArrayList();
        String query = "select * from caixa where cai_data = '" + data.toString() + "'";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                LocalDate data2;
                try{ data2 = rs.getDate("cai_datafechamento").toLocalDate(); }catch(Exception e){ data2 = null; }
                
                listcaixa.add(new Caixa(rs.getDate("cai_data").toLocalDate(), rs.getInt("fun_cod"),
                        rs.getDouble("cai_valorini"), rs.getDouble("cai_totalentradas"), 
                        rs.getDouble("cai_totalsaidas"), data2));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listcaixa;
    }
    
     public static LocalDate buscaDataAnterior()
    {
        LocalDate data = null;
        String query = "select (current_date - 1) as data_anterior";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        { while(rs.next()){ data = rs.getDate(1).toLocalDate(); } }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        
        return data;
    }
    
    public static List buscaCaixaFuncionario(int fun_cod)
    {
        List <Caixa> listcaixa = new ArrayList();
        String query = "select * from caixa where fun_cod = " + fun_cod;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                LocalDate data;
                try{ data = rs.getDate("cai_datafechamento").toLocalDate(); }catch(Exception e){ data = null; }
                
                listcaixa.add(new Caixa(rs.getDate("cai_data").toLocalDate(), rs.getInt("fun_cod"),
                        rs.getDouble("cai_valorini"), rs.getDouble("cai_totalentradas"), 
                        rs.getDouble("cai_totalsaidas"), data));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listcaixa;
    }
    
    public static List get(String filtro)
    {
        List <Caixa> listcaixa = new ArrayList();
        String query = "select * from caixa";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                LocalDate data;
                try{ data = rs.getDate("cai_datafechamento").toLocalDate(); }catch(Exception e){ data = null; }
                
                listcaixa.add(new Caixa(rs.getDate("cai_data").toLocalDate(), rs.getInt("fun_cod"),
                        rs.getDouble("cai_valorini"), rs.getDouble("cai_totalentradas"), 
                        rs.getDouble("cai_totalsaidas"), data));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listcaixa;
    }
    // ------------------------------------------------------------------------------------------------------------------
}