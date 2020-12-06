package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class CompraItem
{
    private CompraProduto compra;
    private Produto produto;
    private int qtde;
    private double valor, valor_antigo;
    private Lote lote;

    public CompraItem(CompraProduto compra, Produto produto, int qtde, double valor, double valor_antigo, Lote lote)
    {
        this.compra = compra;
        this.produto = produto;
        this.qtde = qtde;
        this.valor = valor;
        this.valor_antigo = valor_antigo;
        this.lote = lote;
    }

    public CompraProduto getCompra()
    { return compra; }

    public void setCompra(CompraProduto compra)
    { this.compra = compra; }

    public Produto getProduto()
    { return produto; }

    public void setProduto(Produto produto)
    { this.produto = produto; }

    public int getQtde()
    { return qtde; }

    public void setQtde(int qtde)
    { this.qtde = qtde; }

    public double getValor()
    { return valor; }

    public void setValor(double valor)
    { this.valor = valor; }

    public double getValor_antigo()
    { return valor_antigo; }

    public void setValor_antigo(double valor_antigo)
    { this.valor_antigo = valor_antigo; }

    public Lote getLote()
    { return lote; }

    public void setLote(Lote lote)
    { this.lote = lote; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static boolean salvar(CompraItem ci)
    {
        double prod_valor = 0;
        String sql = "insert into compra_item (com_cod, prod_cod, num_lote, prod_valor, prod_qtd, prod_valorantigo) values "
                + "($1, $2, $3, $4, $5, $6)";
        
        ArrayList<Produto> list = (ArrayList) ci.getProduto().buscarProdutoFiltro("" + ci.getProduto().getId(), 1);
        if(list.isEmpty())
            prod_valor = ci.getProduto().getVl_unit();
        else
            prod_valor = ((Produto)list.get(0)).getVl_unit();
        ci.setValor_antigo(prod_valor);

        sql = sql.replace("$1", "" + ci.getCompra().getId());
        sql = sql.replace("$2", "" + ci.getProduto().getId());
        sql = sql.replace("$3", "" + ci.getLote().getId());
        sql = sql.replace("$4", "" + ci.getValor());
        sql = sql.replace("$5", "" + ci.getQtde());
        sql = sql.replace("$6", "" + ci.getValor_antigo());

        return Banco.getCon().manipular(sql);
    }
    
    public static boolean apagar(int cod_compra, int prod_cod, int lote)
    {
        String sql = "delete from compra_item where com_cod = " + cod_compra + " and prod_cod = "
                + "" + prod_cod + " and num_lote = " + lote;
        return Banco.getCon().manipular(sql);
    }
    
    public static List getItensCompraPorLoteEPorProd(int lote, int prod)
    { return CompraItem.getItensCompra("num_lote = " + lote + " and prod_cod = " + prod); }
    
    public static List getItensCompra(String filtro)
    {
        List<CompraItem> list = new ArrayList();
        String query = "select * from compra_item";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList<Produto> list_prod = null;
            ArrayList<Lote> list_lote = null;
            Produto p = new Produto();
            ArrayList<CompraProduto> list_cp = null;
            
            while(rs.next())
            {
                list_prod = (ArrayList<Produto>) p.buscarProdutoFiltro("" + rs.getInt("prod_cod"), 1);
                if(!list_prod.isEmpty())
                {
                    list_lote = (ArrayList<Lote>) Lote.buscaLoteCodigoProdCodigo(rs.getInt("num_lote"), rs.getInt("prod_cod"));
                    if(!list_lote.isEmpty())
                    {
                        list_cp = (ArrayList<CompraProduto>) CompraProduto.getApenasDadosPrincipais("com_cod = " + rs.getInt("com_cod"));
                        if(!list_cp.isEmpty())
                        {
                            list.add(new CompraItem(list_cp.get(0), list_prod.get(0), rs.getInt("prod_qtd"), 
                                    rs.getDouble("prod_valor"), rs.getDouble("prod_valorantigo"), 
                                    list_lote.get(0)));
                        }
                    }
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return list;
    }
    // ------------------------------------------------------------------------------------------------------------------
}