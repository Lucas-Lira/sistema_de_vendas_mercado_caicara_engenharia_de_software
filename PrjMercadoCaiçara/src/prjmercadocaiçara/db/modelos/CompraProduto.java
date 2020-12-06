package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class CompraProduto
{
    private int id;
    private LocalDate data;
    private double valor_total;
    private Fornecedor fornecedor;
    private ArrayList<Parcela> parcelas;
    private ArrayList<CompraItem> itens;

    public CompraProduto(int id, LocalDate data, double valor_total, Fornecedor fornecedor)
    {
        this.id = id;
        this.data = data;
        this.valor_total = valor_total;
        this.fornecedor = fornecedor;
        
        parcelas = new ArrayList();
        itens = new ArrayList();
    }
    
    public CompraProduto(LocalDate data, double valor_total, Fornecedor fornecedor)
    { this(0, data, valor_total, fornecedor); }
    
    public CompraProduto()
    { this(0, null, 0, null); }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public LocalDate getData()
    { return data; }

    public void setData(LocalDate data)
    { this.data = data; }

    public double getValor_total()
    { return valor_total; }

    public void setValor_total(double valor_total)
    { this.valor_total = valor_total; }

    public Fornecedor getFornecedor()
    { return fornecedor; }

    public void setFornecedor(Fornecedor fornecedor)
    { this.fornecedor = fornecedor; }

    public ArrayList<Parcela> getParcelas()
    { return parcelas; }

    public void clearParcelas()
    { parcelas = new ArrayList(); }
    
    public void addParcela(Parcela p)
    { parcelas.add(p); }
    
    public int buscaParcela(Parcela p)
    {
        int pos= -1;
        if(!parcelas.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < parcelas.size() && !achou; i++)
                if(parcelas.get(i) == p)
                {
                    pos= i;
                    achou= true;
                }
        }
        
        return pos;
    }
        
    public boolean removeParcela(Parcela p)
    {
        int indice= buscaParcela(p);
        if(indice != (-1))
        {
            parcelas.remove(indice);
            return true;
        }
        
        return false;
    }

    public ArrayList<CompraItem> getItens()
    { return itens; }
    
    public void clearItens()
    { itens = new ArrayList(); }
    
    public void addItem(CompraItem it)
    { itens.add(it); }
    
    public int buscaItem(CompraItem it)
    {
        int pos= -1;
        if(!itens.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < itens.size() && !achou; i++)
                if(itens.get(i) == it)
                {
                    pos= i;
                    achou= true;
                }
        }
        
        return pos;
    }
        
    public boolean removeItem(CompraItem it)
    {
        int indice= buscaItem(it);
        if(indice != (-1))
        {
            itens.remove(indice);
            return true;
        }
        
        return false;
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static boolean salvar(CompraProduto cp)
    {
        boolean executou = false;
        int cod = 0;
        
        String sql = "insert into compra (com_data, com_valortotal, for_cod) values ('$1', $2, $3)";
            
        sql = sql.replace("$1", cp.getData().toString());
        sql = sql.replace("$2", "" + cp.getValor_total());
        sql = sql.replace("$3", "" + cp.getFornecedor().getId());

        executou = Banco.getCon().manipular(sql);
        
        if(executou)
        {
            try{ cod = Banco.getCon().getMaxPK("compra", "com_cod"); }catch(Exception e){ cod = 0; }
            cp.setId(cod);
        }
        
        return executou;
    }
    
    public static boolean apagar(CompraProduto cp)
    {
        String sql = "delete from compra where com_cod = " + cp.getId();
        return Banco.getCon().manipular(sql);
    }
    
    public static List get(String filtro)
    {
        List<CompraProduto> list = new ArrayList();
        CompraProduto cp = null;
        String query = "select * from compra";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList aux = null;
            
            while(rs.next())
            {
                aux = (ArrayList) new Fornecedor().get("f_cod = " + rs.getInt("for_cod"));
                
                cp = new CompraProduto(rs.getInt("com_cod"), rs.getDate("com_data").toLocalDate(), 
                    rs.getDouble("com_valortotal"), null);
                
                if(!aux.isEmpty())
                    cp.setFornecedor((Fornecedor) aux.get(0));
                
                aux = (ArrayList) Parcela.getParcelas("com_cod = " + rs.getInt("com_cod"));
                
                for(int i = 0; i < aux.size(); i++)
                    cp.addParcela((Parcela) aux.get(i));
                
                aux = (ArrayList) CompraItem.getItensCompra("com_cod = " + rs.getInt("com_cod"));
                
                for(int i = 0; i < aux.size(); i++)
                    cp.addItem((CompraItem) aux.get(i));
                
                list.add(cp);
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return list;
    }
    
    public static List getApenasDadosPrincipais(String filtro)
    {
        List<CompraProduto> list = new ArrayList();
        CompraProduto cp = null;
        String query = "select * from compra";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList aux = null;
            
            while(rs.next())
            {
                aux = (ArrayList) new Fornecedor().get("f_cod = " + rs.getInt("for_cod"));
                
                cp = new CompraProduto(rs.getInt("com_cod"), rs.getDate("com_data").toLocalDate(), 
                    rs.getDouble("com_valortotal"), null);
                
                if(!aux.isEmpty())
                    cp.setFornecedor((Fornecedor) aux.get(0));
                
                aux = (ArrayList) Parcela.getParcelas("com_cod = " + rs.getInt("com_cod"));
                
                for(int i = 0; i < aux.size(); i++)
                    cp.addParcela((Parcela) aux.get(i));
                
                list.add(cp);
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return list;
    }
    
    public static List getComFornecedor(String filtro)
    {
        List<CompraProduto> list = new ArrayList();
        CompraProduto cp = null;
        String query = "select * from compra inner join fornecedor on compra.for_cod = fornecedor.f_cod";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList aux = null;
            
            while(rs.next())
            {
                aux = (ArrayList) new Fornecedor().get("f_cod = " + rs.getInt("for_cod"));
                
                cp = new CompraProduto(rs.getInt("com_cod"), rs.getDate("com_data").toLocalDate(), 
                    rs.getDouble("com_valortotal"), null);
                
                if(!aux.isEmpty())
                    cp.setFornecedor((Fornecedor) aux.get(0));
                
                aux = (ArrayList) Parcela.getParcelas("com_cod = " + rs.getInt("com_cod"));
                
                for(int i = 0; i < aux.size(); i++)
                    cp.addParcela((Parcela) aux.get(i));
                
                aux = (ArrayList) CompraItem.getItensCompra("com_cod = " + rs.getInt("com_cod"));
                
                for(int i = 0; i < aux.size(); i++)
                    cp.addItem((CompraItem) aux.get(i));
                
                list.add(cp);
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return list;
    }
    
    public static ArrayList buscarCompraFiltro(String info, int flag)
    {
        ArrayList list = null;
        
        switch (flag)
        {
            case 1: list = (ArrayList) get("com_cod = " + info); break;
            case 2: list = (ArrayList) getComFornecedor("fornecedor.f_cod = " + info); break;
            case 3:
                if(!info.isEmpty())
                    list = (ArrayList) getComFornecedor("fornecedor.f_descricao like '" + info + "'");
                else
                    list = (ArrayList) getComFornecedor("");
            break;
            case 4: list = (ArrayList) getComFornecedor("fornecedor.f_cnpj = '" + info + "'"); break;
        }
        
        return list;
    }
    // ------------------------------------------------------------------------------------------------------------------
}