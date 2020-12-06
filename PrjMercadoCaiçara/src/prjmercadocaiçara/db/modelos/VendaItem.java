
package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class VendaItem
{
    private Venda venda;
    private Produto produto;
    private int qtde;
    private double valor, valor_antigo;
    private Lote lote;

    public VendaItem(Venda venda, Produto produto, int qtde, double valor, Lote lote, int valor_antigo) {
        this.venda = venda;
        this.produto = produto;
        this.qtde = qtde;
        this.valor = valor;
        this.lote = lote;
        this.valor_antigo = valor_antigo;
    }

    public VendaItem(Produto produto, int qtde, double valor, Lote lote, int valor_antigo) {
        this.produto = produto;
        this.qtde = qtde;
        this.valor = valor;
        this.lote = lote;
        this.valor_antigo = valor_antigo;
    }
    
    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public double getValor_antigo() {
        return valor_antigo;
    }

    public void setValor_antigo(double valor_antigo) {
        this.valor_antigo = valor_antigo;
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static boolean salvar(VendaItem vi)
    {
        String sql = "insert into itens_venda (ven_cod, pro_cod, qtd, valor_unit, num_lot) values "
                + "($1, $2, $3, $4, $5)";

        sql = sql.replace("$1", "" + vi.getVenda().getId());
        sql = sql.replace("$2", "" + vi.getProduto().getId());
        sql = sql.replace("$3", "" + vi.getQtde());
        sql = sql.replace("$4", "" + vi.getValor());
        sql = sql.replace("$5", "" + vi.getLote().getId());
        
        return Banco.getCon().manipular(sql);
    }
    
    public static List getItensVendaPorLoteEPorProd(int lote, int prod)
    { return VendaItem.getItensVenda("num_lote = " + lote + " and prod_cod = " + prod); }
    
    public static boolean apagar(int ven_cod, int prod_cod, int lote)
    {
        String sql = "delete from itens_venda where ven_cod = " + ven_cod + " and pro_cod = "
                + "" + prod_cod + " and num_lot = " + lote;
        return Banco.getCon().manipular(sql);
    }
    
    public static List getItensVenda(String filtro)
    {
        List<VendaItem> list = new ArrayList();
        String query = "select * from itens_venda";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList<Produto> list_prod = null;
            ArrayList<Lote> list_lote = null;
            Produto p = new Produto();
            Venda venda = new Venda();
            
            ArrayList<Venda> list_vp = null;
            
            while(rs.next())
            {
                list_prod = (ArrayList<Produto>) p.buscarProdutoFiltro("" + rs.getInt("pro_cod"), 1);
                if(!list_prod.isEmpty())
                {
                    list_lote = (ArrayList<Lote>) Lote.buscaLoteCodigo(rs.getInt("num_lot"));
                    if(!list_lote.isEmpty())
                    {
                        
                        
                        
                        
                        list_vp = (ArrayList<Venda>) venda.get("ven_cod = " + rs.getInt("ven_cod"));
                        
                        
                        if(!list_vp.isEmpty())
                        {
                            list.add(new VendaItem(list_vp.get(0), list_prod.get(0), rs.getInt("qtd"), 
                                    rs.getDouble("valor_unit"), list_lote.get(0), 0));
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
