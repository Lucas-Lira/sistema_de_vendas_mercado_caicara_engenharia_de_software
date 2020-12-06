
package prjmercadocaiçara.db.controladoras;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import prjmercadocaiçara.db.modelos.Caixa;
import prjmercadocaiçara.db.modelos.Lote;
import prjmercadocaiçara.db.modelos.ParcelasV;
import prjmercadocaiçara.db.modelos.Prateleira;
import prjmercadocaiçara.db.modelos.Produto;
import prjmercadocaiçara.db.modelos.TipoProduto;
import prjmercadocaiçara.db.modelos.Venda;
import prjmercadocaiçara.db.modelos.VendaItem;
import prjmercadocaiçara.ui.template.CtrlMovimentacao;
import static prjmercadocaiçara.ui.template.CtrlMovimentacao.parcelas;

public class CtrlVenderProduto extends CtrlMovimentacao {
    // ========================================================
    // Padrão SINGLETON
    // ========================================================
    private static CtrlVenderProduto instancia=null;
    private List<Venda> list_venda = null;
    private Venda atual;
    
    private CtrlVenderProduto(){
        Venda v = new Venda();
        list_venda = v.get("");
    }
    
    public static CtrlVenderProduto instanciar(){
        if(instancia == null)
            instancia = new CtrlVenderProduto();
        return instancia;
    }
    // ========================================================
    
    /*public boolean salvarVenda(Date data, double valortotalvenda, String formapagamento, List<Map<String, String>> listparc, List<Map<String, String>> listp, int idcliente, int idfuncionario, List<LocalDate> auxDatas, List flagsobserver)
    { 
        List<VendaItem> listprodutos = new ArrayList();
        Map<String, String> dataRow;
        TipoProduto tipo = new TipoProduto();
        Produto prod = new Produto();
        List<Prateleira> listprateleira;
        List<Lote> listlotes;
        int prat_cod=0;
        Produto novo;
        
        // Obtendo dados do produto vindos via MAP respeitando o padrão FACADE
        for (int i = 0; i < listp.size(); i++)
        {
            dataRow = listp.get(i);
            prat_cod = prod.buscaCodPrateleiraID(Integer.parseInt(dataRow.get("id")));
            listprateleira = Prateleira.get("prat_cod = "+prat_cod);
            listlotes = Lote.get("prod_cod = "+Integer.parseInt(dataRow.get("id")));
            novo = new Produto(Integer.parseInt(dataRow.get("id")), Integer.parseInt(dataRow.get("qtd")), dataRow.get("descricao"), Double.parseDouble(dataRow.get("vl_unit")), (TipoProduto) tipo.get("tp_cod = "+dataRow.get("idtipo")).get(0), listprateleira.get(0));
            novo.setLote(listlotes);
            
            listprodutos.add(new VendaItem(novo, Integer.parseInt(dataRow.get("qtd")), Double.parseDouble(dataRow.get("vl_unit")), (Lote) Lote.buscaLoteCodigoProduto(Integer.parseInt(dataRow.get("id"))).get(0)));
            
        }
        
        // Obtendo os dados das parcelas passadas por parametro via MAP, visando respeitar o padrão FAÇADE
        List<ParcelasV> listparcelas = new ArrayList();
        dataRow = new HashMap<>();
        LocalDate datavecimento = auxDatas.get(0);
        LocalDate datacaixa = auxDatas.get(1);
        for (int i = 0; i < listparc.size(); i++)
        {
            dataRow = listparc.get(i);    
            listparcelas.add(new ParcelasV(Integer.parseInt(dataRow.get("id")), Integer.parseInt(dataRow.get("idfuncionario")), 0.0, Double.parseDouble(dataRow.get("valorparcela")), 0.0, null, asDate(datavecimento), asDate(datacaixa), dataRow.get("descricao")));
            datavecimento = datavecimento.plusMonths(1);      
        }
        
        atual = new Venda(data, valortotalvenda, formapagamento, listparcelas, listprodutos, idcliente, idfuncionario);
        
        
        
        
        
        // Essa parte será alterada
        if(atual.salvar(flagsobserver))
        {
            atual.atualizaIntancias_da_Venda_ItensVenda();
            list_venda = atual.get("");
            return true;
        }
        else
            return false;
    }*/
    
    public boolean alterarVenda(int id, Date data, double valortotalvenda, String formapagamento, List<Map<String, String>> listparc, List<Map<String, String>> listp, int idcliente, int idfuncionario, List<LocalDate> auxDatas, List flagsobserver)
    {
        List<VendaItem> listprodutos = new ArrayList();
        Map<String, String> dataRow;
        TipoProduto tipo = new TipoProduto();
        
        for (int i = 0; i < listp.size(); i++)
        {
            dataRow = listp.get(i);
            listprodutos.add(new VendaItem(new Produto(Integer.parseInt(dataRow.get("id")), Integer.parseInt(dataRow.get("qtd")), dataRow.get("descricao"), Double.parseDouble(dataRow.get("vl_unit")), (TipoProduto) tipo.get("tp_cod = "+dataRow.get("idtipo")).get(0)), Integer.parseInt(dataRow.get("qtd")), Double.parseDouble(dataRow.get("vl_unit")), (Lote) Lote.buscaLoteCodigoProduto(Integer.parseInt(dataRow.get("id"))).get(0), 0));           
        }
        
        // Mesmo processo para as percelas
        List<ParcelasV> listparcelas = new ArrayList();
        dataRow = new HashMap<>();
        
        LocalDate datavecimento = auxDatas.get(0);
        LocalDate datacaixa = auxDatas.get(1);
        for (int i = 0; i < listparc.size(); i++)
        {
           dataRow = listparc.get(i);
           listparcelas.add(new ParcelasV(Integer.parseInt(dataRow.get("id")), Integer.parseInt(dataRow.get("idfuncionario")), 0.0, Double.parseDouble(dataRow.get("valorparcela")), 0.0, null, asDate(datavecimento), asDate(datacaixa),dataRow.get("descricao")));
           datavecimento = datavecimento.plusMonths(1);   
        }
        
        atual = new Venda(id, data, valortotalvenda, formapagamento, listparcelas, listprodutos, idcliente, idfuncionario);
        // Fazer o mesmo esquema para o alterar
        
        if(atual.alterar())
        {
            atual.atualizaIntancias_da_Venda_ItensVenda();
            list_venda = atual.get("");
            return true;
        }
        else
            return false;
    }
    
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    /*public boolean apagarVenda(int id)
    {
        Venda v = new Venda();
        v.setId(id);
        
        if(v.apagar())
        {
            list_venda = atual.get("");
            return true;
        }
        else
            return false;
    }*/
    
    // Métodos de busca para a controladora de venda de produtos
    public List<Venda> buscar(String filtro)
    { 
        Venda v = new Venda();
        return v.get(filtro);
    }
    
    public void getVendaCarregaTabela(String filtro, TableView tabela)
    {
        tabela.getItems().clear();
        Venda v = new Venda();
        List<Venda> l = v.get(filtro);
        if(!l.isEmpty())
            tabela.setItems(FXCollections.observableArrayList(l));
    }
    
    public void getVendaCarregaTabelaConsultarVenda(String filtro, TableView tabela)
    { 
        ObservableList<Map> allData = FXCollections.observableArrayList();
        Venda v = new Venda();
        List<Venda> l = v.get(filtro);
        
        if(!l.isEmpty())
        {
            for(int i = 0; i < l.size(); i++)
            {
                Map<String, String> dataRow = new HashMap<>();
                dataRow.put("id", ""+l.get(i).getId());
                dataRow.put("cliente", ""+l.get(i).getCliente());
                dataRow.put("funcionario", ""+l.get(i).getFuncionario());
                dataRow.put("formapagamento", ""+l.get(i).getFormapagamento());
                dataRow.put("valortotal", ""+l.get(i).getValortotal());
                allData.add(dataRow);
            }
            tabela.setItems(allData);
        }
    }
    
    private int buscaPosicaoIdListaVenda(int id)
    {
        int i = 0;
        while(i < list_venda.size() && list_venda.get(i).getId() != id)
            i++;
        return i;
    }
    
    public Date buscarDataDaVendaID(int id)
    { return list_venda.get(buscaPosicaoIdListaVenda(id)).getDatavenda(); }
    
    public int buscarQtdParcelasDaVendaID(int id)
    { return list_venda.get(buscaPosicaoIdListaVenda(id)).getParcelas().size(); }
    
    public void getVendaCarregaTabelaVenderProdutosID(int id, TableView tabela)
    { 
        int posilist = buscaPosicaoIdListaVenda(id);
        ObservableList<Map> allData = FXCollections.observableArrayList();
        if(!list_venda.isEmpty())
        {
            for(int i = 0; i < list_venda.get(posilist).getItensvenda().size(); i++)
            {
                Map<String, String> dataRow = new HashMap<>();
                dataRow.put("id", ""+list_venda.get(posilist).getItensvenda().get(i).getProduto().getId());
                dataRow.put("descricao", list_venda.get(posilist).getItensvenda().get(i).getProduto().getDescricao());
                dataRow.put("vl_unit", ""+list_venda.get(posilist).getItensvenda().get(i).getValor());
                dataRow.put("qtd", ""+list_venda.get(posilist).getItensvenda().get(i).getQtde());
                dataRow.put("idtipo", ""+list_venda.get(posilist).getItensvenda().get(i).getProduto().getTipo().getId());
                allData.add(dataRow);
            }
            tabela.setItems(allData);
        }
    }
    
    public void getVendaCarregaTabelaParcelasDaVendaID(int id, TableView tabela)
    { 
        int posilist = buscaPosicaoIdListaVenda(id);
        ObservableList<Map> allData = FXCollections.observableArrayList();
        if(!list_venda.isEmpty())
        {
            for(int i = 0; i < list_venda.get(posilist).getParcelas().size(); i++)
            {
                Map<String, String> dataRow = new HashMap<>();
                dataRow.put("id", ""+list_venda.get(posilist).getParcelas().get(i).getId());
                dataRow.put("descricao", list_venda.get(posilist).getParcelas().get(i).getDescricao());
                dataRow.put("valorparcela", ""+list_venda.get(posilist).getParcelas().get(i).getValorparcela());
                dataRow.put("datavencimento", ""+list_venda.get(posilist).getParcelas().get(i).getDatavencimento().toString());
                dataRow.put("datacaixa", ""+list_venda.get(posilist).getParcelas().get(i).getDatacaixa().toString());
                dataRow.put("idfuncionario", ""+list_venda.get(posilist).getParcelas().get(i).getIdfuncionario());
                allData.add(dataRow);
            }
            tabela.setItems(allData);
        }
    }

    
    // Modificações Realizadas para implementar o padrão Template Method
    // ==================================================================================================================
    public void CarregaDadosEssenciaisVendaAtual(Date data, double valortotalvenda, String formapagamento, List<Map<String, String>> listparc, List<Map<String, String>> listp, int idcliente, int idfuncionario, List<LocalDate> auxDatas, List flagsobserver)
    { 
        List<VendaItem> listprodutos = new ArrayList();
        Map<String, String> dataRow;
        TipoProduto tipo = new TipoProduto();
        Produto prod = new Produto();
        List<Prateleira> listprateleira;
        List<Lote> listlotes;
        int prat_cod=0;
        Produto novo;
        
        // Obtendo dados do produto vindos via MAP respeitando o padrão FACADE
        for (int i = 0; i < listp.size(); i++)
        {
            dataRow = listp.get(i);
            prat_cod = prod.buscaCodPrateleiraID(Integer.parseInt(dataRow.get("id")));
            listprateleira = Prateleira.get("prat_cod = "+prat_cod);
            listlotes = Lote.get("prod_cod = "+Integer.parseInt(dataRow.get("id")));
            novo = new Produto(Integer.parseInt(dataRow.get("id")), Integer.parseInt(dataRow.get("qtd")), dataRow.get("descricao"), Double.parseDouble(dataRow.get("vl_unit")), (TipoProduto) tipo.get("tp_cod = "+dataRow.get("idtipo")).get(0), listprateleira.get(0));
            novo.setLote(listlotes);
            
            listprodutos.add(new VendaItem(novo, Integer.parseInt(dataRow.get("qtd")), Double.parseDouble(dataRow.get("vl_unit")), (Lote) Lote.buscaLoteCodigoProduto(Integer.parseInt(dataRow.get("id"))).get(0), 0));
            
        }
        
        // Obtendo os dados das parcelas passadas por parametro via MAP, visando respeitar o padrão FAÇADE
        List<ParcelasV> listparcelas = new ArrayList();
        dataRow = new HashMap<>();
        LocalDate datavecimento = auxDatas.get(0);
        LocalDate datacaixa = auxDatas.get(1);
        for (int i = 0; i < listparc.size(); i++)
        {
            dataRow = listparc.get(i);    
            listparcelas.add(new ParcelasV(Integer.parseInt(dataRow.get("id")), Integer.parseInt(dataRow.get("idfuncionario")), 0.0, Double.parseDouble(dataRow.get("valorparcela")), 0.0, null, asDate(datavecimento), asDate(datacaixa), dataRow.get("descricao")));
            datavecimento = datavecimento.plusMonths(1);      
        }
        // Atualizando os dados da venda atual
        atual = new Venda(data, valortotalvenda, formapagamento, listparcelas, listprodutos, idcliente, idfuncionario);
    }
    
    @Override
    public boolean gravarObjeto() {
        boolean executou = false;
        try
        {
            if(atual.getId() == 0 && atual.getValortotal() > 0 && atual.getDatavenda() != null && atual.getItensvenda().size() > 0 && atual.getParcelas().size() > 0)
            {
                executou = atual.salvarVenda();
                if(executou)
                {
                    // Inserindo os dados da compra nos list de produtos
                    for(int i = 0; i < atual.getItensvenda().size(); i++)
                        atual.getItensvenda().get(i).setVenda(new Venda(atual.getId(), atual.getDatavenda(), atual.getValortotal(), atual.getFormapagamento(), atual.getParcelas(), atual.getItensvenda(), atual.getCliente(), atual.getFuncionario()));
                    // Inserindo os dados da compra no list de parcelas
                    for (int i = 0; i < atual.getParcelas().size(); i++)
                        atual.getParcelas().get(i).setIdvenda(atual.getId());
                    
                    // Setando os List de Itens e Parcelas nos lists da superclasse
                    itens = (ArrayList<Object>)((Object)atual.getItensvenda());
                    parcelas = (ArrayList<Object>) ((Object)atual.getParcelas());
                }
            }
        }catch(Exception e){ }
        return executou;
    }
    
    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    
    @Override
    public boolean gravarParcelas(int indice) {
        boolean executou = true;
        try
        {
            int erros = 0;
            ParcelasV p = ((ParcelasV)parcelas.get(indice));

            // VERIFICANDO SE SERÁ PRECISO ATUALIZAR O CAIXA
            if(p.getValorparcela() > 0 && p.getDatapagamento() != null)
            {
                Caixa caixa = (Caixa) Caixa.buscaCaixaData(toLocalDate(p.getDatacaixa())).get(0);
                caixa.setTotalsaidas(caixa.getTotalsaidas() + p.getValorpago());
                if(!Caixa.alterar(caixa))
                    erros++;
            }
            // SALVANDO A PARCELA
            if(!ParcelasV.salvar(p, atual.getFormapagamento(), atual.getValortotal(), atual.getDatavenda()))
                erros++;
            if(erros == 0)
                executou = true;
        }catch(Exception e){ }
        
        return executou;
    }

    @Override
    public boolean gravarItem(int indice) {
        boolean executou = false;
        try
        {
            int erros = 0, num_lote = 0;
            // CRIANDO OU ATUALIZANDO O LOTE DO PRODUTO
            ArrayList<Lote> lotes = null;
            Lote lote = ((VendaItem)itens.get(indice)).getProduto().getLotes().get(0);

            try
            {
                lotes = (ArrayList<Lote>) Lote.buscaLoteCodigoProdCodigo(lote.getId(), 
                    ((VendaItem)itens.get(indice)).getProduto().getId());
            }
            catch(Exception e){ lotes = new ArrayList(); }

            if(!lotes.isEmpty()) // ATUALIZANDO O LOTE
            {
               lote.setQtde(lote.getQtde() + lotes.get(0).getQtde());
               if(!Lote.alterar(lote, ((VendaItem)itens.get(indice)).getProduto().getId()))
                   erros++;
            }
            else // SALVANDO O LOTE
            {
                if(!Lote.salvar(lote, ((VendaItem)itens.get(indice)).getProduto().getId()))
                    erros++;

                try{ num_lote = Lote.getMaxPKLoteProd(((VendaItem)itens.get(indice)).getProduto().getId()); }
                catch(Exception e2){ num_lote = 0; }

                lote.setId(num_lote);
            }

            // SALVANDO O ITEM DA COMPRA
            ((VendaItem)itens.get(indice)).setLote(lote);

            if(erros == 0)
                if(VendaItem.salvar(((VendaItem)itens.get(indice))))
                { executou = true; }
        }catch(Exception e){ }
        return executou;
    }
    
    @Override
    public boolean atualizarEstoqueProduto(int indice, boolean remover) {
        boolean executou = false;
        int erros = 0;
        
        try
        {
            if(!remover)
            {
                // DECREMENTANDO O ESTOQUE DO PRODUTO
                ArrayList<Produto> list_aux = (ArrayList) ((VendaItem)itens.get(indice)).getProduto().buscarProdutoFiltro("" + 
                        ((VendaItem)itens.get(indice)).getProduto().getId(), 1);
                int est;
                double v;
                Produto p_aux = null;

                if(!list_aux.isEmpty())
                {
                    p_aux = ((VendaItem)itens.get(indice)).getProduto();

                    est = ((Produto)list_aux.get(0)).getQtd();
                    v = p_aux.getVl_unit();
                    est += p_aux.getQtd();

                    p_aux.setQtd(est);
                    p_aux.setVl_unit(v);
                    if(!p_aux.alterar())
                        erros++;
                }
            }
            else
            {
                VendaItem ci = ((VendaItem)itens.get(indice));
                Produto p_aux;
                ArrayList<Produto> list = null;
                int est;
                double v;
            
                list = (ArrayList<Produto>) ci.getProduto().buscarProdutoFiltro("" + ci.getProduto().getId(), 1);
                
                if(!list.isEmpty())
                {
                    p_aux = list.get(0);
                    est = p_aux.getQtd();
                    v = ci.getValor_antigo();
                    est -= ci.getQtde();
                    if(est < 0)
                        est = 0;
                    
                    p_aux.setQtd(est);
                    
                    // ATUALIZANDO O VALOR DO PRODUTO P/ O ANTIGO APENAS SE O MESMO FOR MAIOR QUE O VALOR ATUAL
                    //      P/ TRATAR INCONSISTÊNCIAS (REMOÇÃO DE COMPRAS ANTERIORES A MAIS RECENTE, POR EXEMPLO)
                    if(p_aux.getVl_unit() < v)
                        p_aux.setVl_unit(v);
                    
                    if(!p_aux.alterar())
                        erros++;
                }
                else
                    erros++;
            }
            
            if(erros == 0)
                executou = true;
        }catch(Exception e){ }
        
        return executou;
    }

    public void setIdVendaAtual(int id)
    { 
        atual = new Venda(); 
        atual.setId(id);
        itens = new ArrayList<Object>();
    }
    
    @Override
    public boolean removerObjeto() {
        boolean executou = false;
        try
        {
            if(atual != null && atual.getId() > 0)
                executou = atual.apagarVenda();//CompraProduto.apagar(compra_atual);
        }catch(Exception e){ executou = false; }
        return executou;
    }

    @Override
    public boolean removerItem(int indice) {
        boolean executou = false;
        int erros = 0;
        try
        {
            VendaItem ci = ((VendaItem)itens.get(indice));
            
            // ATUALIZANDO OU REMOVENDO O LOTE ENVOLVIDO NA COMPRA
            Lote lote = null;
            lote = ci.getLote();

            ArrayList<VendaItem>list = (ArrayList<VendaItem>) VendaItem.getItensVendaPorLoteEPorProd(lote.getId(), 
                    ci.getProduto().getId());

            if(list.size() <= 1) // REMOÇÃO
            {
                if(!Lote.apagar(lote.getId(), ci.getProduto().getId()))
                    erros++;
            }
            else // ATUALIZAÇÃO
            {
                int est = lote.getQtde();
                est -= ci.getQtde();
                if(est < 0)
                    est = 0;

                lote.setQtde(est);
                if(!Lote.alterar(lote, ci.getProduto().getId()))
                    erros++;
            }
            // REMOVENDO O ITEM DA COMPRA
            if(!VendaItem.apagar(ci.getVenda().getId(), ci.getProduto().getId(), ci.getLote().getId()))
                erros++;
            
            if(erros == 0)
                executou = true;
        }catch(Exception e){ }
        return executou;
    }

    @Override
    public boolean removerParcelas(int indice) {
        boolean executou = false;
        try{ executou = ParcelasV.apagar(((ParcelasV)parcelas.get(indice))); }catch(Exception e){ }
        return executou;
    }
}
