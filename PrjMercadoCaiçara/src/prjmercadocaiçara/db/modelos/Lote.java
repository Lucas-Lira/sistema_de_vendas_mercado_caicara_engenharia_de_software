package prjmercadocaiçara.db.modelos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Lote
{
    private int id, qtde;
    LocalDate dtfabric, dtvenc;

    public Lote(int id, int qtde, LocalDate dtfabric, LocalDate dtvenc)
    {
        this.id = id;
        this.qtde = qtde;
        this.dtfabric = dtfabric;
        this.dtvenc = dtvenc;
    }
    
    public Lote(int qtde, LocalDate dtfabric, LocalDate dtvenc)
    { this(0, qtde, dtfabric, dtvenc); }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public int getQtde()
    { return qtde; }

    public void setQtde(int qtde)
    { this.qtde = qtde; }

    public LocalDate getDtfabric()
    { return dtfabric; }

    public void setDtfabric(LocalDate dtfabric)
    { this.dtfabric = dtfabric; }

    public LocalDate getDtvenc()
    { return dtvenc; }

    public void setDtvenc(LocalDate dtvenc)
    { this.dtvenc = dtvenc; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static int getMaxPKLoteProd(int prod)
    {
        int cod = 0;
        try
        {
            ResultSet rs = Banco.getCon().consultar("select max(num_lot) as num_lot from lote where lote.prod_cod = " + prod);
            
            if(rs.next())
            { cod = rs.getInt("num_lot"); }
            else
                cod = 0;
        }
        catch(Exception ex) { cod = (-1); }
        
        return cod;
    }
    
    public static boolean salvar(Lote lote, int prod)
    {
        int cod = lote.getId();
        
        if(cod > 0 && prod > 0)
        {
            String sql = "insert into lote (num_lot, prod_cod, qtde, dt_fabric, dt_venc)"
                    + " values ($1, $2, $3, '$4', '$5')";
            sql = sql.replace("$1", "" + cod);
            sql = sql.replace("$2", "" + prod);
            sql = sql.replace("$3", "" + lote.getQtde());
            sql = sql.replace("$4", "" + lote.getDtfabric().toString());
            sql = sql.replace("$5", "" + lote.getDtvenc().toString());

            return Banco.getCon().manipular(sql);
        }
        
        return false;
    }
    
    public static boolean alterar(Lote lote, int prod)
    {
        if(prod > 0 && lote.getId() > 0)
        {
            String sql = "update lote set qtde = $1, dt_fabric = '$2', dt_venc = '$3' where num_lot = " + lote.getId()
                    + "and prod_cod = " + prod;
            sql = sql.replace("$1", "" + lote.getQtde());
            sql = sql.replace("$2", "" + lote.getDtfabric().toString());
            sql = sql.replace("$3", "" + lote.getDtvenc().toString());

            return Banco.getCon().manipular(sql);
        }
        
        return false;
    }
    
    public static boolean apagar(int lote, int prod)
    {
        if(prod > 0 && lote > 0)
            return Banco.getCon().manipular("delete from lote where num_lot = " + lote + 
                    " and prod_cod = " + prod);
        
        return false;
    }
    
    public static List buscaLoteDtFabric(LocalDate data)
    {
        try
        {
            List <Lote> listlote = get("dt_fabric = '" + data.toString() + "'");
            return listlote;
        }
        catch(Exception e){ return new ArrayList(); }
    }
    
    public static List buscaLoteDtVenc(LocalDate data)
    {
        try
        {
            List <Lote> listlote = get("dt_venc = '" + data.toString() + "'");
            return listlote;
        }
        catch(Exception e){ return new ArrayList(); }
    }
    
    public static List buscaLoteCodigoProduto(int cod)
    {
        try
        {
            List <Lote> listlote = get("prod_cod = " + cod);
            return listlote;
        }
        catch(Exception e){ return new ArrayList(); }
    }
    
    public static List buscaLoteCodigo(int cod)
    {
        try
        {
            List <Lote> listlote = get("num_lot = " + cod);
            return listlote;
        }
        catch(Exception e){ return new ArrayList(); }
    }
    
    public static List buscaLoteCodigoProdCodigo(int cod, int prod)
    {
        try
        {
            List <Lote> listlote = get("num_lot = " + cod + " and prod_cod = " + prod);
            return listlote;
        }
        catch(Exception e){ return new ArrayList(); }
    }
    
    public static List get(String filtro)
    {
        List <Lote> listlote = new ArrayList();
        String query = "select * from lote";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listlote.add(new Lote(rs.getInt("num_lot"), rs.getInt("qtde"), rs.getDate("dt_fabric").toLocalDate(), 
                    rs.getDate("dt_venc").toLocalDate()));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listlote;
    }
    // ------------------------------------------------------------------------------------------------------------------
}