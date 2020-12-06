
/*package prjmercadocaiçara.db.persistencia;

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
import prjmercadocaiçara.db.modelos.Caixa;
import prjmercadocaiçara.db.modelos.ParcelasV;
import prjmercadocaiçara.db.modelos.Produto;
import prjmercadocaiçara.db.modelos.Venda;

public class VendaBD
{
    private static CtrlCaixa dalcaixa = CtrlCaixa.instanciarCtrlCaixa();
    private static CtrlProduto dalproduto = CtrlProduto.instanciar(); 
    
    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    
    public static boolean salvar(Venda v)
    {
        Boolean executou = false;
        int i, erros = 0, codvenda = 0;
        
        try
        {
            String sql = "";
            Banco.getCon().getConnect().setAutoCommit(false);
            
            // Insert da Venda: 
            sql = "insert into venda (ven_valortotal, ven_formapagto, cli_cod, fun_cod, ven_data)"
                + " values ('$1', '$2', '$3', '$4', '$5')";
            sql = sql.replace("$1", "" + v.getValortotal());
            sql = sql.replace("$2", v.getFormapagamento());
            sql = sql.replace("$3", ""+v.getCliente());
            sql = sql.replace("$4", "" + v.getFuncionario());
            sql = sql.replace("$5", "" + v.getDatavenda());

            if(!Banco.getCon().manipular(sql))
                erros++;
            
            try{ codvenda = (int) Banco.getCon().getMaxPK("venda", "ven_cod"); }catch(Exception e){ erros++; }
            
            // Inserir Parcelas da Venda
            if(!v.getFormapagamento().equals("À Vista"))
            {
                System.out.println("\n\nEntrou!\n\n");
                //int parc_cod = 1;
                for(i = 0; i < v.getParcelas().size(); i++)
                {
                    sql= "insert into parcelav (parc_cod, ven_cod, parc_valorpagto, parc_juros, parc_descricao, parc_valor, parc_dtvencto, cai_data, fun_cod) "
                            + "values ('$1', '$2', '$3', '$4', '$5', '$6', '$7', '$8', '$9')";

                    sql = sql.replace("$1", "" + v.getParcelas().get(i).getId()); 
                    sql = sql.replace("$2", ""+codvenda);
                    sql = sql.replace("$3", "" + v.getParcelas().get(i).getValorpago());
                    sql = sql.replace("$4", ""+v.getParcelas().get(i).getParcjuros());
                    sql = sql.replace("$5", ""+v.getParcelas().get(i).getDescricao());
                    sql = sql.replace("$6", ""+v.getParcelas().get(i).getValorparcela());
                    sql = sql.replace("$7", ""+v.getParcelas().get(i).getDatavencimento());
                    sql = sql.replace("$8", ""+v.getParcelas().get(i).getDatacaixa());
                    sql = sql.replace("$9", ""+v.getParcelas().get(i).getIdfuncionario());

                    if(!Banco.getCon().manipular(sql))
                        erros++;
                }
            }
            else
            {
                //int parc_cod = 1;
                for(i = 0; i < v.getParcelas().size(); i++)
                {
                    sql= "insert into parcelav (parc_cod, ven_cod, parc_valorpagto, parc_juros, parc_descricao,"
                            + " parc_valor, parc_dtvencto, cai_data, fun_cod, parc_dtpagto) "
                            + "values ('$1', '$2', '$3', '$4', '$5', '$6', '$7', '$8', '$9', '$0')";

                    sql = sql.replace("$1", "" + v.getParcelas().get(i).getId()); 
                    sql = sql.replace("$2", ""+codvenda);
                    sql = sql.replace("$3", "" +v.getValortotal());
                    sql = sql.replace("$4", ""+v.getParcelas().get(i).getParcjuros());
                    sql = sql.replace("$5", ""+v.getParcelas().get(i).getDescricao());
                    sql = sql.replace("$6", ""+v.getParcelas().get(i).getValorparcela());
                    sql = sql.replace("$7", ""+v.getParcelas().get(i).getDatavencimento());
                    sql = sql.replace("$8", ""+v.getParcelas().get(i).getDatacaixa());
                    sql = sql.replace("$9", ""+v.getParcelas().get(i).getIdfuncionario());
                    sql = sql.replace("$0", toLocalDate(v.getDatavenda()).toString());
                        
                    if(!Banco.getCon().manipular(sql))
                        erros++;
                }
            }
            
            // Insert de Itens de Venda: 
            for(i = 0; i < v.getItensvenda().size(); i++)
            {
                sql= "insert into itens_venda (ven_cod, pro_cod, qtd, valor_unit) values ('$1', '$2', '$3', '$4')";
                
                sql = sql.replace("$1", ""+codvenda);
                sql = sql.replace("$2", ""+v.getItensvenda().get(i).getId());
                sql = sql.replace("$3", ""+v.getItensvenda().get(i).getQtd());
                sql = sql.replace("$4", ""+v.getItensvenda().get(i).getVl_unit());
                
                if(!Banco.getCon().manipular(sql))
                    erros++;
                
                // Atualizar o estoque do produto
                List<Produto> listp;
                listp = dalproduto.getProduto("prod_cod = "+v.getItensvenda().get(i).getId());
                
                if(listp.size() > 0)
                {
                    listp.get(0).setQtd(listp.get(0).getQtd() - v.getItensvenda().get(i).getQtd());
                    
                    if(!dalproduto.alterarProduto(listp.get(0).getId(), listp.get(0).getQtd(), listp.get(0).getDescricao(), listp.get(0).getVl_unit(), listp.get(0).getTipo()))
                        erros++;
                }
                else
                    erros++;
            }
            
            // Realizar a atualização do caixa de acordo com a forma de pagamento
            //À Vista
            // Cartão de crédito e debito
            // Cheque
            // Parcelado
            
            if(v.getFormapagamento().equals("À Vista"))
            {
                List<Caixa> list;// = new ArrayList();
                list = dalcaixa.buscaCaixaFuncionarioEData(v.getFuncionario(), LocalDate.now());
                if(list.size() > 0)
                {
                    list.get(0).setTotalentradas(list.get(0).getTotalentradas()+v.getValortotal());
                
                    if(!dalcaixa.alterarCaixa(list.get(0)))
                        erros++;
                }
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
        catch(SQLException e)
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
    
    public static boolean alterar(Venda v)
    {
        boolean executou = false;
        int erros = 0;
        try
        {
            Banco.getCon().getConnect().setAutoCommit(false);
            
            String sql = "update venda set ven_valortotal = '$1', cli_cod = '$2', fun_cod = '$3', ven_data = '$4', ven_formapagto = '$5' where ven_cod = "+v.getId();
            sql = sql.replace("$1", ""+v.getValortotal());
            sql = sql.replace("$2", ""+v.getCliente());
            sql = sql.replace("$3", ""+v.getFuncionario());
            sql = sql.replace("$4", ""+v.getDatavenda());
            sql = sql.replace("$5", ""+v.getFormapagamento());

            if(!Banco.getCon().manipular(sql))
                erros++;
            if(!Banco.getCon().manipular("delete from parcelav where ven_cod = "+v.getId()))
                erros++;
            if(!Banco.getCon().manipular("delete from itens_venda where ven_cod = "+v.getId()))
                erros++;
            
            // Inserir Parcelas da Venda
            //int parc_cod = 1;
            for(int i = 0; i < v.getParcelas().size(); i++)
            {
                sql= "insert into parcelav (parc_cod, ven_cod, parc_valorpagto, parc_juros, parc_descricao, parc_valor, parc_dtvencto, cai_data, fun_cod) "
                        + "values ('$1', '$2', '$3', '$4', '$5', '$6', '$7', '$8', '$9')";
                
                sql = sql.replace("$1", "" + v.getParcelas().get(i).getId()); 
                sql = sql.replace("$2", ""+v.getId());
                sql = sql.replace("$3", "" + v.getParcelas().get(i).getValorpago());
                sql = sql.replace("$4", ""+v.getParcelas().get(i).getParcjuros());
                sql = sql.replace("$5", ""+v.getParcelas().get(i).getDescricao());
                sql = sql.replace("$6", ""+v.getParcelas().get(i).getValorparcela());
                sql = sql.replace("$7", ""+v.getParcelas().get(i).getDatavencimento());
                sql = sql.replace("$8", ""+v.getParcelas().get(i).getDatacaixa());
                sql = sql.replace("$9", ""+v.getParcelas().get(i).getIdfuncionario());
                
                if(!Banco.getCon().manipular(sql))
                    erros++;
            }
            
            // Insert de Itens de Venda: 
            for(int i = 0; i < v.getItensvenda().size(); i++)
            {
                sql= "insert into itens_venda (ven_cod, pro_cod, qtd, valor_unit) values ('$1', '$2', '$3', '$4')";
                
                sql = sql.replace("$1", ""+v.getId());
                sql = sql.replace("$2", ""+v.getItensvenda().get(i).getId());
                sql = sql.replace("$3", ""+v.getItensvenda().get(i).getQtd());
                sql = sql.replace("$4", ""+v.getItensvenda().get(i).getVl_unit());
                
                if(!Banco.getCon().manipular(sql))
                    erros++;
                
                // Atualizar o estoque do produto
                List<Produto> listp;
                listp = dalproduto.getProduto("prod_cod = "+v.getItensvenda().get(i).getId());
                
                if(listp.size() > 0)
                {
                    listp.get(0).setQtd(listp.get(0).getQtd() - v.getItensvenda().get(i).getQtd());
                    
                    if(!dalproduto.alterarProduto(listp.get(0).getId(), listp.get(0).getQtd(), listp.get(0).getDescricao(), listp.get(0).getVl_unit(), listp.get(0).getTipo()))
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
    
    public static boolean apagar(Venda v){
        int erros = 0;
        boolean executou = false;
        try
        {
            Banco.getCon().getConnect().setAutoCommit(false);
            
            if(!Banco.getCon().manipular("delete from parcelav where ven_cod = "+v.getId()))
            erros++;
        
            if(!Banco.getCon().manipular("delete from itens_venda where ven_cod = "+v.getId()))
                erros++;

            if(!Banco.getCon().manipular("delete from venda where ven_cod = "+v.getId()))
                erros++;
            
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
    
    public static List<Venda> get(String filtro)
    {
        List<Venda> listvenda = new ArrayList();
        List<ParcelasV> listparcelas = new ArrayList();
        List<Produto> listitensvenda = new ArrayList();
        List<Produto> aux = new ArrayList();
        CtrlProduto dal = CtrlProduto.instanciar();
        
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
                        listitensvenda.add(new Produto(prodcod, rp.getInt("qtd"), aux.get(0).getDescricao(), rp.getDouble("valor_unit"), aux.get(0).getTipo()));
                        aux.remove(0);
                    } 
                }
                listvenda.add(new Venda(codvenda, rs.getDate("ven_data"), rs.getDouble("ven_valortotal"), rs.getString("ven_formapagto"), listparcelas, listitensvenda, rs.getInt("cli_cod"), rs.getInt("fun_cod")));     
            }  
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return listvenda;
    }
}*/
