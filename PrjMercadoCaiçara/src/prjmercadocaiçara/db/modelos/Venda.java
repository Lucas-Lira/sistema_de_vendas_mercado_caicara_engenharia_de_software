
package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import prjmercadocaiçara.db.controladoras.CtrlCaixa;
import prjmercadocaiçara.db.controladoras.CtrlProduto;
import prjmercadocaiçara.db.persistencia.Banco;

public class Venda {
    private int id, cliente, funcionario;
    private Date datavenda;
    private double valortotal;
    private String formapagamento;
    private List<ParcelasV> parcelas;
    private List</*Produto*/VendaItem> itensvenda;

    public Venda() {
        this(0, null, 0.0, null, null, null, 0, 0);
    }

    public Venda(Date datavenda, double valortotal, String formapagamento, List<ParcelasV> parcelas, List<VendaItem> itensvenda, int cliente, int funcionario) {
        this.datavenda = datavenda;
        this.valortotal = valortotal;
        this.formapagamento = formapagamento;
        this.cliente = cliente;
        this.funcionario = funcionario;
        
        if(parcelas != null)
            this.parcelas = parcelas;
        else
            this.parcelas = new ArrayList<ParcelasV>();
        
        if(itensvenda != null)
            this.itensvenda = itensvenda;
        else
            this.itensvenda = new ArrayList<VendaItem>();
    }

    public Venda(int id, Date datavenda, double valortotal, String formapagamento, List<ParcelasV> parcelas, List<VendaItem> itensvenda, int cliente, int funcionario) {
        this.id = id;
        this.datavenda = datavenda;
        this.valortotal = valortotal;
        this.formapagamento = formapagamento;
        this.cliente = cliente;
        this.funcionario = funcionario;
        
        if(parcelas != null)
            this.parcelas = parcelas;
        else
            this.parcelas = new ArrayList<ParcelasV>();
        
        if(itensvenda != null)
            this.itensvenda = itensvenda;
        else
            this.itensvenda = new ArrayList<VendaItem>();
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(Date datavenda) {
        this.datavenda = datavenda;
    }

    public double getValortotal() {
        return valortotal;
    }

    public void setValortotal(double valortotal) {
        this.valortotal = valortotal;
    }

    public String getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(String formapagamento) {
        this.formapagamento = formapagamento;
    }

    public List<ParcelasV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ParcelasV> parcelas) {
        this.parcelas = parcelas;
    }

    public List<VendaItem> getItensvenda() {
        return itensvenda;
    }

    public void setItensvenda(List<VendaItem> itensvenda) {
        this.itensvenda = itensvenda;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }
    
    public void atualizaIntancias_da_Venda_ItensVenda()
    {
        for (int i = 0; i < itensvenda.size(); i++)
            itensvenda.get(i).setVenda(this);
    }
    
// ------------------------------------------------------------------------------------------------------------------
// PADRÃO FAÇADE:
// ------------------------------------------------------------------------------------------------------------------
private static CtrlCaixa dalcaixa = CtrlCaixa.instanciarCtrlCaixa();

    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    
    // Salvando a venda
    public boolean salvarVenda()
    {
        Boolean executou = false;
        int codvenda=0;
        
        String sql = "insert into venda (ven_valortotal, ven_formapagto, cli_cod, fun_cod, ven_data)"
            + " values ('$1', '$2', '$3', '$4', '$5')";
        sql = sql.replace("$1", ""+valortotal);
        sql = sql.replace("$2", formapagamento);
        sql = sql.replace("$3", ""+cliente);
        sql = sql.replace("$4", ""+funcionario);
        sql = sql.replace("$5", ""+datavenda);

        executou = Banco.getCon().manipular(sql);

        if(executou){
            try{ codvenda = (int) Banco.getCon().getMaxPK("venda", "ven_cod"); }catch(Exception e){ codvenda = 0; }
            id = codvenda;
        }
        return executou;
    }
    
    public boolean alterar()
    {
        boolean executou = false;
        int erros = 0;
        try
        {
            Banco.getCon().getConnect().setAutoCommit(false);
            
            String sql = "update venda set ven_valortotal = '$1', cli_cod = '$2', fun_cod = '$3', ven_data = '$4', ven_formapagto = '$5' where ven_cod = "+getId();
            sql = sql.replace("$1", ""+getValortotal());
            sql = sql.replace("$2", ""+getCliente());
            sql = sql.replace("$3", ""+getFuncionario());
            sql = sql.replace("$4", ""+getDatavenda());
            sql = sql.replace("$5", ""+getFormapagamento());

            if(!Banco.getCon().manipular(sql))
                erros++;
            if(!Banco.getCon().manipular("delete from parcelav where ven_cod = "+getId()))
                erros++;
            if(!Banco.getCon().manipular("delete from itens_venda where ven_cod = "+getId()))
                erros++;
            
            // Inserir Parcelas da Venda
            //int parc_cod = 1;
            for(int i = 0; i < getParcelas().size(); i++)
            {
                sql= "insert into parcelav (parc_cod, ven_cod, parc_valorpagto, parc_juros, parc_descricao, parc_valor, parc_dtvencto, cai_data, fun_cod) "
                        + "values ('$1', '$2', '$3', '$4', '$5', '$6', '$7', '$8', '$9')";
                
                sql = sql.replace("$1", ""+getParcelas().get(i).getId()); 
                sql = sql.replace("$2", ""+getId());
                sql = sql.replace("$3", ""+getParcelas().get(i).getValorpago());
                sql = sql.replace("$4", ""+getParcelas().get(i).getParcjuros());
                sql = sql.replace("$5", ""+getParcelas().get(i).getDescricao());
                sql = sql.replace("$6", ""+getParcelas().get(i).getValorparcela());
                sql = sql.replace("$7", ""+getParcelas().get(i).getDatavencimento());
                sql = sql.replace("$8", ""+getParcelas().get(i).getDatacaixa());
                sql = sql.replace("$9", ""+getParcelas().get(i).getIdfuncionario());
                
                if(!Banco.getCon().manipular(sql))
                    erros++;
            }
            
            /* Insert de Itens de Venda: */
            for(int i = 0; i < getItensvenda().size(); i++)
            {
                sql= "insert into itens_venda (ven_cod, pro_cod, qtd, valor_unit) values ('$1', '$2', '$3', '$4')";
                
                sql = sql.replace("$1", ""+getId());
                sql = sql.replace("$2", ""+getItensvenda().get(i).getProduto().getId());
                sql = sql.replace("$3", ""+getItensvenda().get(i).getQtde());
                sql = sql.replace("$4", ""+getItensvenda().get(i).getValor());
                
                if(!Banco.getCon().manipular(sql))
                    erros++;
                
                // Atualizar o estoque do produto
                Produto p = new Produto();
                List<Produto> listp;
                listp = p.get("prod_cod = "+getItensvenda().get(i).getProduto().getId());
                
                if(listp.size() > 0)
                {
                    listp.get(0).setQtd(listp.get(0).getQtd() - getItensvenda().get(i).getQtde());
                    
                    p = new Produto(listp.get(0).getId(), listp.get(0).getQtd(), listp.get(0).getDescricao(), listp.get(0).getVl_unit(), listp.get(0).getTipo());
                    if(!p.alterar())
                        erros++;
                }
                else
                    erros++;
            }
            
            if(erros > 0)
            {
                executou = false;
                try{ Banco.getCon().getConnect().rollback(); }catch(SQLException ex){ }
            }
            else
            {
                Banco.getCon().getConnect().commit();
                executou = true;
            }
        }
        catch(Exception e)
        { 
            try{ Banco.getCon().getConnect().rollback(); }catch(SQLException ex){ }
            executou = false;
        }
        finally
        { 
            try{ Banco.getCon().getConnect().setAutoCommit(true); }catch(SQLException ex){ }
        }
        return executou;
    }
    
    public boolean apagarVenda()
    { return Banco.getCon().manipular("delete from parcelav where ven_cod = "+id); }
    
    public List<Venda> get(String filtro)
    {
        List<Venda> listvenda = new ArrayList();
        List<ParcelasV> listparcelas = new ArrayList();
        List<VendaItem> listitensvenda = new ArrayList();
        List<Produto> aux = new ArrayList();
        CtrlProduto dal = CtrlProduto.instanciar();
        Venda v=null;
        
        String sql = "select * from venda";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
            {
                int codvenda = rs.getInt("ven_cod");
                // Zerar os ArrayLists
                listparcelas.clear();
                listitensvenda.clear();
                
                sql = "select * from parcelav where ven_cod = "+codvenda;
                ResultSet r = Banco.getCon().consultar(sql);
                while(r.next())
                    listparcelas.add(new ParcelasV(r.getInt("parc_cod"),r.getInt("ven_cod"), r.getInt("fun_cod"), r.getDouble("parc_valorpagto"), r.getDouble("parc_valor"), r.getDouble("parc_juros"), r.getDate("parc_dtpagto"), r.getDate("parc_dtvencto"), r.getDate("cai_data"), r.getString("parc_descricao")));
                
                sql = "select * from itens_venda where ven_cod = "+codvenda;
                ResultSet rp = Banco.getCon().consultar(sql);
                while(rp.next())
                {
                    int prodcod = rp.getInt("pro_cod");
                    
                    aux = dal.getProduto("prod_cod = "+prodcod);
                    if(!aux.isEmpty())
                    {
                        listitensvenda.add(new VendaItem(aux.get(0), rp.getInt("qtd"), rp.getDouble("valor_unit"), aux.get(0).getLotes().get(0), 0));
                        aux.remove(0);
                    } 
                }
                
                v = new Venda(codvenda, rs.getDate("ven_data"), rs.getDouble("ven_valortotal"), rs.getString("ven_formapagto"), listparcelas, listitensvenda, rs.getInt("cli_cod"), rs.getInt("fun_cod"));
                for (int i = 0; i < listitensvenda.size(); i++)
                    listitensvenda.get(i).setVenda(v);
                listvenda.add(v);     
            }  
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return listvenda;
    }    
}
