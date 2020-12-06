package prjmercadocaiçara.db.controladoras;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import prjmercadocaiçara.db.modelos.Caixa;
import prjmercadocaiçara.db.modelos.CompraItem;
import prjmercadocaiçara.db.modelos.CompraProduto;
import prjmercadocaiçara.db.modelos.Fileira;
import prjmercadocaiçara.db.modelos.Fornecedor;
import prjmercadocaiçara.db.modelos.Funcionario;
import prjmercadocaiçara.db.modelos.Lote;
import prjmercadocaiçara.db.modelos.Parcela;
import prjmercadocaiçara.db.modelos.Prateleira;
import prjmercadocaiçara.db.modelos.Produto;
import prjmercadocaiçara.db.modelos.TipoProduto;
import prjmercadocaiçara.ui.template.CtrlMovimentacao;
import prjmercadocaiçara.util.facade.DisplayComboBox;

public class CtrlComprarProdutos extends CtrlMovimentacao
{
    private ArrayList<CompraProduto> list_cp = null;
    private CompraProduto compra_atual = null;
    private Funcionario fun_atual = null;
    private Caixa caixa_atual = null;
    private ArrayList<Fornecedor> list_fornecedor = null;
    private ArrayList<Produto> list_prod_consultado = null;
    private ArrayList<Fileira> list_fileira = null;
    private ArrayList<Prateleira> list_prateleira = null;
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO SINGLETON:
    // ------------------------------------------------------------------------------------------------------------------
    private static CtrlComprarProdutos ctrl_cp = null;
    
    public static CtrlComprarProdutos instanciarCtrlCP()
    {
        if(ctrl_cp == null)
            ctrl_cp = new CtrlComprarProdutos();
        
        return ctrl_cp;
    }
    
    private CtrlComprarProdutos()
    {
        fun_atual = new Funcionario();
        caixa_atual = new Caixa();
        list_fornecedor = new ArrayList();
        list_cp = new ArrayList();
        
        list_fileira = new ArrayList();
        list_prateleira = new ArrayList();
        
        clearCompraAtual();
    }
    // ------------------------------------------------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public boolean carregarFuncionarioAtual(int id)
    {
        ArrayList<Funcionario> list = (ArrayList<Funcionario>) Funcionario.buscaFuncionarioFiltro(1, "" + id);
        if(!list.isEmpty())
        {
            fun_atual = list.get(0);
            return true;
        }
        return false;
    }
    
    public boolean carregarCaixaEFuncionarioAtuais(int id, LocalDate data)
    {
        ArrayList<Caixa> list = (ArrayList<Caixa>) Caixa.buscaCaixaFuncionarioEData(id, data);
        if(!list.isEmpty())
        {
            if(list.get(0).getDatafechamento() == null)
            {
                caixa_atual = list.get(0);
                return true;
            }
            return false;
        }
        return false;
    }
    
    public String getNomeFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getNome();
        return "";
    }
    
    public int getCodigoFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getId();
        return (-1);
    }
    
    public void clearCompraAtual()
    {
        if(list_prod_consultado == null)
            list_prod_consultado = new ArrayList();
        else
            list_prod_consultado.clear();
        
        if(compra_atual == null)
            compra_atual =  new CompraProduto();
        else
        {
            compra_atual.setId(0);
            compra_atual.setData(LocalDate.now());
            compra_atual.setValor_total(0);
            compra_atual.setFornecedor(null);
            
            compra_atual.clearItens();
            compra_atual.clearParcelas();
        }
        
        if(itens == null)
            itens = new ArrayList();
        else
            itens.clear();
        
        clearParcelasCompraAtual();
    }
    
    public void clearParcelasCompraAtual()
    {
        if(parcelas == null)
            parcelas = new ArrayList();
        else
            parcelas.clear();
    }
    
    public void carregarComboBoxFiltroConsultaCompra(ComboBox cbfiltro)
    {
        cbfiltro.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("CÓDIGO PROD.", "1");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("CÓD. FORNECEDOR", "2");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("DESCRIÇÃO FORNECEDOR", "3");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("CNPJ FORNECEDOR", "4");
        cbfiltro.getItems().add(obj);
    }
    
    public void carregarComboBoxFiltroConsultaProduto(ComboBox cbfiltro)
    {
        cbfiltro.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("CÓDIGO", "1");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("DESCRIÇÃO", "2");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("VALOR UNITÁRIO PROD.", "3");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("CÓD. TIPO", "4");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("DESCRIÇÃO TIPO", "5");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("LOTE", "6");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("FILEIRA", "7");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("PRATELEIRA", "8");
        cbfiltro.getItems().add(obj);
    }
    
    public void carregarComboBoxFornecedor(ComboBox cbfornecedor, String filtro)
    {
        cbfornecedor.getItems().clear();
        list_fornecedor = (ArrayList)new Fornecedor().get(filtro);
        Fornecedor fornec = null;
        DisplayComboBox obj = null;
        
        for(int i = 0; i < list_fornecedor.size(); i++)
        {
            fornec = list_fornecedor.get(i);
            
            obj = new DisplayComboBox("Código = " + fornec.getId() + ", CNPJ = " + fornec.getCnpj() + 
                    ", Descrição = " + fornec.getDescricao(), "" + fornec.getId());
            
            cbfornecedor.getItems().add(obj);
        }
    }
    
    public void carregarComboBoxParcelaFPagamento(ComboBox cbformapagamento)
    {
        cbformapagamento.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("À VISTA", "0");
        cbformapagamento.getItems().add(obj);
        obj = new DisplayComboBox("À PRAZO / PARCELADO", "1");
        cbformapagamento.getItems().add(obj);
    }
    
    public void carregarComboBoxParcelaQtdeParcelas(ComboBox cbqtdeparcelas)
    {
        cbqtdeparcelas.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("1", "1");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("2", "2");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("3", "3");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("4", "4");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("5", "5");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("6", "6");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("7", "7");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("8", "8");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("9", "9");
        cbqtdeparcelas.getItems().add(obj);
        obj = new DisplayComboBox("10", "10");
        cbqtdeparcelas.getItems().add(obj);
    }
    
    public void carregarComboBoxParcelaTipoGeracao(ComboBox cbtipogeracaoparcelas)
    {
        cbtipogeracaoparcelas.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("GERAÇÃO MANUAL", "0");
        cbtipogeracaoparcelas.getItems().add(obj);
        obj = new DisplayComboBox("GERAÇÃO AUTOMÁTICA", "1");
        cbtipogeracaoparcelas.getItems().add(obj);
    }
    
    public void carregarComboBoxFileira(ComboBox cbfileira)
    {
        cbfileira.getItems().clear();
        DisplayComboBox obj = null;
        
        list_fileira = (ArrayList<Fileira>) Fileira.get("");
        Fileira f = null;
        for(int i = 0; i < list_fileira.size(); i++)
        {
            f = list_fileira.get(i);
            obj = new DisplayComboBox("" + f.getDescricao(), "" + f.getId());
            cbfileira.getItems().add(obj);
        }
    }
    
    public void carregarComboBoxPrateleira(ComboBox cbprateleira, int pos_fileira)
    {
        cbprateleira.getItems().clear();
        DisplayComboBox obj = null;
        
        list_prateleira = (ArrayList<Prateleira>) Prateleira.buscaPrateleiraFileiraCodigo(list_fileira.get(pos_fileira).getId());
        Prateleira prat = null;
        for(int i = 0; i < list_prateleira.size(); i++)
        {
            prat = list_prateleira.get(i);
            obj = new DisplayComboBox("" + prat.getDescricao(), "" + prat.getId());
            cbprateleira.getItems().add(obj);
        }
    }
    
    public void inicializarTableViewComprarProduto(Object tabela)
    {
        try
        {
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(0)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(1)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(2)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(3)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(4)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(5)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(6)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(6)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(7)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(7)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(8)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(8)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(9)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(9)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(10)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(10)));
        }
        catch(Exception ex){ }
    }
    
    public void inicializarTableViewConsultarProduto(Object tabela)
    {
        try
        {
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(0)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(1)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(2)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(3)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(4)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(5)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(6)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(6)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(7)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(7)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(8)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(8)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(9)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(9)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(10)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(10)));
        }
        catch(Exception ex){ }
    }
    
    public void inicializarTableViewConsultarCompra(Object tabela)
    {
        try
        {
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(0)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(1)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(2)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(3)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(4)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(5)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));
        }
        catch(Exception ex){ }
    }
    
    public void inicializarTableViewParcela(Object tabela)
    {
        try
        {
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(0)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(1)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(2)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(3)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(4)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(5)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(6)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(6)));
        }
        catch(Exception ex){ }
    }
    
    public ArrayList<Produto> buscarProduto(String info, int flag)
    {
        ArrayList<Produto> list = null;
        Produto prod = new Produto();
        list = (ArrayList<Produto>) prod.buscarProdutoFiltro(info, flag);
        return list;
    }
    
    public void carregarTableViewConsultarProduto(TableView tabela, int filtro, String dado)
    {
        tabela.getItems().clear();
        list_prod_consultado = buscarProduto(dado, filtro);
        
        ArrayList<ArrayList<String>> list = new ArrayList();
        Produto prod = null;
        Lote l = null;
        for(int i = 0; i < list_prod_consultado.size(); i++)
        {
            prod = list_prod_consultado.get(i);
            
            ArrayList<String> linha = new ArrayList();
            linha.add("" + prod.getId());
            linha.add("" + prod.getDescricao());
            linha.add("" + prod.getVl_unit());
            linha.add("" + prod.getQtd());
            linha.add("" + prod.getTipo().getId());
            linha.add("" + prod.getTipo().getDescricao());
            
            l = getLoteMaiorDtVencProd(prod);
            if(l == null)
            {
                linha.add("" + "-");
                linha.add("" + "-");
                linha.add("" + "-");
            }
            else
            {
                linha.add("" + l.getId());
                linha.add("" + l.getDtfabric().toString());
                linha.add("" + l.getDtvenc().toString());
            }
            
            linha.add("" + "" + prod.getPrateleira().getFileira().getDescricao());
            linha.add("" + "" + prod.getPrateleira().getDescricao());
            
            list.add(linha);
        }
        
        tabela.setItems(FXCollections.observableArrayList(list));
    }
    
    public Lote getLoteMaiorDtVencProd(Produto prod)
    {
        ArrayList<Lote> list = prod.getLotes();
        Lote l = null;
        int pos = 0;
        
        if(!list.isEmpty())
            l = list.get(pos);
        
        for(int i = 1; i < list.size(); i++)
            if(list.get(pos).getDtvenc().isBefore(list.get(i).getDtvenc()))
            {
                pos = i;
                l = list.get(pos);
            }
        
        return l;
    }
    
    public int getCodigoProdConsultado(int pos)
    {
        if(pos >= 0 && pos < list_prod_consultado.size())
            return list_prod_consultado.get(pos).getId();
        return (-1);
    }
    
    public int getCodigoLoteProdConsultadoMaxDtVenc(int pos)
    {
        Lote l = null;
        if(pos >= 0 && pos < list_prod_consultado.size())
        {
            l = getLoteMaiorDtVencProd(list_prod_consultado.get(pos));
            
            if(l != null)
                return l.getId();
        }
        return (-1);
    }
    
    public LocalDate getDtFabricLoteProdConsultadoMaxDtVenc(int pos)
    {
        Lote l = null;
        if(pos >= 0 && pos < list_prod_consultado.size())
        {
            l = getLoteMaiorDtVencProd(list_prod_consultado.get(pos));
            
            if(l != null)
                return l.getDtfabric();
        }
        return null;
    }
    
    public LocalDate getDtVencLoteProdConsultadoMaxDtVenc(int pos)
    {
        Lote l = null;
        if(pos >= 0 && pos < list_prod_consultado.size())
        {
            l = getLoteMaiorDtVencProd(list_prod_consultado.get(pos));
            
            if(l != null)
                return l.getDtvenc();
        }
        return null;
    }
    
    public int getPosPrateleiraList(int prod_cod)
    {
        Produto prod = null;
        boolean achou = false;
        int pos = (-1);
        
        for(int i = 0; i < list_prod_consultado.size() && !achou; i++)
            if(list_prod_consultado.get(i).getId() == prod_cod)
            {
                prod = list_prod_consultado.get(i);
                achou = true;
            }
        
        if(prod != null)
        {
            achou = false;
            for(int i = 0; i < list_prateleira.size() && !achou; i++)
                if(list_prateleira.get(i).getId() == prod.getPrateleira().getId())
                {
                    pos = i;
                    achou = true;
                }
        }
        
        return pos;
    }
    
    public int getPosFileiraList(int prod_cod, int pos_prateleira)
    {
        Produto prod = null;
        boolean achou = false;
        int pos = (-1);
        
        for(int i = 0; i < list_prod_consultado.size() && !achou; i++)
            if(list_prod_consultado.get(i).getId() == prod_cod)
            {
                prod = list_prod_consultado.get(i);
                achou = true;
            }
        
        if(prod != null)
        {
            achou = false;
            Prateleira prat = prod.getPrateleira();
            
            for(int i = 0; i < list_fileira.size() && !achou; i++)
                if(list_fileira.get(i).getId() == prat.getId())
                {
                    pos = i;
                    achou = true;
                }
        }
        
        return pos;
    }
    
    public String getDescricaoProdConsultado(int pos)
    {
        if(pos >= 0 && pos < list_prod_consultado.size())
            return list_prod_consultado.get(pos).getDescricao();
        return "";
    }
    
    public int getQtdeProdConsultado(int pos)
    {
        if(pos >= 0 && pos < list_prod_consultado.size())
            return list_prod_consultado.get(pos).getQtd();
        return (-1);
    }
    
    public double getValorUnitarioProdConsultado(int pos)
    {
        if(pos >= 0 && pos < list_prod_consultado.size())
            return list_prod_consultado.get(pos).getVl_unit();
        return (-1);
    }
    
    public int buscaProdCompraAtual(int id, int lote, LocalDate dtfabric, LocalDate dtvenc)
    {
        int resp = (-1);
        boolean achou = false;
        Lote l = null;
        
        for(int i = 0; i < itens.size() && !achou; i++)
        {
            l = ((CompraItem)itens.get(i)).getProduto().getLotes().get(0);
            
            if((((CompraItem)itens.get(i)).getProduto().getId() == id) && (l.getId() == lote))
            {
                resp = i;
                achou = true;
            }
        }
        
        return resp;
    }
    
    public int getQtdeProdAdicionado(int codigo)
    {
        int qtde = (-1);
        
        if(!itens.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < itens.size() && !achou; i++)
                if(codigo == ((CompraItem)itens.get(i)).getProduto().getId())
                {
                    qtde = ((CompraItem)itens.get(i)).getProduto().getQtd();
                    achou = true;
                }
        }
        
        return qtde;
    }
    
    public double getValorUnitarioProdAdicionado(int codigo)
    {
       double valor = (-1);
        
        if(!itens.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < itens.size() && !achou; i++)
                if(codigo == ((CompraItem)itens.get(i)).getProduto().getId())
                {
                    valor = ((CompraItem)itens.get(i)).getProduto().getVl_unit();
                    achou = true;
                }
        }
        
        return valor;
    }
    
    public int getQtdeProdAdicionadoPosLista(int pos)
    {
        int qtde = (-1);
        
        if(pos >= 0 && pos < itens.size())
            qtde = ((CompraItem)itens.get(pos)).getProduto().getQtd();
        
        return qtde;
    }
    
    public double getValorUnitarioProdAdicionadoPosLista(int pos)
    {
       double valor = (-1);
        
        if(pos >= 0 && pos < itens.size())
            valor = ((CompraItem)itens.get(pos)).getProduto().getVl_unit();
        
        return valor;
    }
    
    public int validaProdutoCompraAtual(int id, int qtde, double valor, int lote, LocalDate dtfabric, LocalDate dtvenc, 
            int pos_fileira, int pos_prateleira)
    {
        int pos = buscaProdCompraAtual(id, lote, dtfabric, dtvenc), resp = 0;
        Produto p = null;
        ArrayList<Produto> list = null;
        
        list = (ArrayList<Produto>) new Produto().get("prod_cod = " + id);
        
        if(!list.isEmpty())
        {
            if((pos_fileira >= 0 && pos_fileira < list_fileira.size()) && 
                    (pos_prateleira >= 0 && pos_prateleira < list_prateleira.size()))
            {
                boolean executar = true;
                
                if(pos != (-1))
                {
                    p = ((CompraItem)itens.get(pos)).getProduto();
                    Lote l = p.getLotes().get(0);
                    
                    if(!l.getDtfabric().equals(dtfabric) || !l.getDtvenc().equals(dtvenc))
                        executar = false;
                }
                
                if(executar)
                {
                    if(dtfabric != null && dtvenc != null)
                    {
                        if(dtfabric.isAfter(LocalDate.now()))
                            resp = -3;
                        else
                        {
                            if(dtfabric.isBefore(dtvenc) || dtfabric.isEqual(dtvenc))
                            {
                                if(lote > 0)
                                {
                                    if(pos != (-1))
                                    {
                                        p = ((CompraItem)itens.get(pos)).getProduto();
                                        qtde += p.getQtd();
                                    }

                                    if(qtde > 0)
                                    {
                                        if(valor >= 0.0)
                                            resp = 1;
                                        else
                                            resp = -2;
                                    }
                                    else
                                        resp = 0;
                                }
                                else
                                    resp = -4;
                            }
                            else
                                resp = -3;
                        }
                    }
                    else
                        resp = -3;
                }
                else
                    resp = -6;
            }
            else
                resp = -5;
        }
        else
            resp = -1;
        return resp;
    }
    
    public int addProdutoCompraAtual(int pos_list_prod_consultado, int qtde, double valor, 
            int lote, LocalDate dtfabric, LocalDate dtvenc, int pos_fileira, int pos_prateleira)
    {
        if(pos_list_prod_consultado >= 0 && pos_list_prod_consultado < list_prod_consultado.size())
        {
            Produto prod = list_prod_consultado.get(pos_list_prod_consultado);
            int id = prod.getId();
            String desc = prod.getDescricao();
            int id_tipo = prod.getTipo().getId();
            String desc_tipo = prod.getTipo().getDescricao();

            int pos = buscaProdCompraAtual(id, lote, dtfabric, dtvenc);
            TipoProduto tp = null;
            Produto p = null;
            Lote l = null;
            Prateleira prat = null;
            int resp = validaProdutoCompraAtual(id, qtde, valor, lote, dtfabric, dtvenc, 
                    pos_fileira, pos_prateleira);

            if(resp == 1)
            {
                if(pos != (-1))
                {
                    p = ((CompraItem)itens.get(pos)).getProduto();
                    qtde += p.getQtd();
                }

                prat = list_prateleira.get(pos_prateleira);
                tp = new TipoProduto(id_tipo, desc_tipo);
                p = new Produto(id, qtde, desc, valor, tp);
                l = new Lote(lote, qtde, dtfabric, dtvenc);
                p.addLotes(l);
                p.setPrateleira(prat);

                if(pos >= 0)
                {
                    ((CompraItem)itens.get(pos)).setProduto(p);
                    ((CompraItem)itens.get(pos)).setQtde(p.getQtd());
                    ((CompraItem)itens.get(pos)).setValor(p.getVl_unit());
                    ((CompraItem)itens.get(pos)).setLote(p.getLotes().get(0));
                }
                else
                    itens.add(new CompraItem(null, p, p.getQtd(), p.getVl_unit(), 0, p.getLotes().get(0)));
                
                resp = 1;
            }

            return resp;
        }
        return (-1);
    }
    
    public void carregarTableViewComprarProduto(TableView tabela)
    {
        tabela.getItems().clear();
        ArrayList<ArrayList<String>> list = new ArrayList();
        Produto prod = null;
        Lote l = null;
        
        for(int i = 0; i < itens.size(); i++)
        {
            prod = ((CompraItem)itens.get(i)).getProduto();
            
            ArrayList<String> linha = new ArrayList();
            linha.add("" + prod.getId());
            linha.add("" + prod.getDescricao());
            linha.add("" + prod.getVl_unit());
            linha.add("" + prod.getQtd());
            linha.add("" + prod.getTipo().getId());
            linha.add("" + prod.getTipo().getDescricao());
            
            l = prod.getLotes().get(0);
            
            linha.add("" + l.getId());
            linha.add("" + l.getDtfabric().toString());
            linha.add("" + l.getDtvenc().toString());
            linha.add("" + prod.getPrateleira().getFileira().getDescricao());
            linha.add("" + prod.getPrateleira().getDescricao());
            
            list.add(linha);
        }
        
        tabela.setItems(FXCollections.observableArrayList(list));
    }
    
    public boolean delProdutoCompraAtual(int pos_list_prod)
    {
        boolean deletou = false;
        
        if(pos_list_prod >= 0 && pos_list_prod < itens.size())
        {
            itens.remove(pos_list_prod);
            deletou = true;
        }
        
        return deletou;
    }
    
    public boolean validaParcelaCompraAtual(int id, double valor, double valor_pgto, double juros, LocalDate dt_vencto, 
            LocalDate dt_pgto, String descricao)
    {
        boolean validou = false;
        
        if(id >= 0) // 0 == NOVA COMPRA
        {
            if(valor >= 0)
            {
                if(valor_pgto == 0 || valor_pgto == valor || valor_pgto == (valor + juros))
                {
                    if(juros >= 0)
                    {
                        if(dt_vencto != null && !dt_vencto.isBefore(LocalDate.now()) && !buscarMesParcelaTabela(dt_vencto))
                        {
                            if(dt_pgto == null || (dt_vencto.equals(dt_pgto) || !dt_pgto.isAfter(LocalDate.now())))
                            {
                                if(descricao.length() > 0)
                                    validou = true;
                            }
                        }
                    }
                }
            }
        }
        
        return validou;
    }
    
    public boolean buscarMesParcelaTabela(LocalDate data)
    {
        boolean achou = false;
        int m1, m2;
        m1 = data.getMonthValue();
        
        for(int i = 0; i < parcelas.size() && !achou; i++)
        {
            m2 = ((Parcela)parcelas.get(i)).getDt_vencto().getMonthValue();
            
            if(m1 == m2)
                achou = true;
        }
        
        return achou;
    }
    
    public boolean addParcelaCompraAtual(int id, double valor, double valor_pgto, double juros, LocalDate dt_vencto, 
            LocalDate dt_pgto, String descricao)
    {
        boolean adicionou = false;
        
        if(validaParcelaCompraAtual(id, valor, valor_pgto, juros, dt_vencto, dt_pgto, descricao))
        {
            Parcela parc = new Parcela(id, valor, valor_pgto, juros, dt_vencto, dt_pgto, descricao, fun_atual, caixa_atual);
            parcelas.add(parc);
            adicionou = true;
        }
        
        return adicionou;
    }
    
    public boolean delParcelaCompraAtual(int pos)
    {
        boolean deletou =  false;
        
        if(pos >= 0 && pos < parcelas.size())
        {
            parcelas.remove(pos);
            deletou = true;
        }
        
        return deletou;
    }
    
    public int getCodigoParcelaAdicionadaPosLista(int pos)
    {
       int codigo = (-1);
        
        if(pos >= 0 && pos < parcelas.size())
            codigo = ((Parcela)parcelas.get(pos)).getId();
        
        return codigo;
    }
    
    public double getValorParcelaAdicionadaPosLista(int pos)
    {
       double valor = (-1);
        
        if(pos >= 0 && pos < parcelas.size())
            valor = ((Parcela)parcelas.get(pos)).getValor();
        
        return valor;
    }
    
    public double getValorPgtoParcelaAdicionadaPosLista(int pos)
    {
       double valor = (-1);
        
        if(pos >= 0 && pos < parcelas.size())
            valor = ((Parcela)parcelas.get(pos)).getValor_pgto();
        
        return valor;
    }
    
    public double getJurosParcelaAdicionadaPosLista(int pos)
    {
       double juros = (-1);
        
        if(pos >= 0 && pos < parcelas.size())
            juros = ((Parcela)parcelas.get(pos)).getJuros();
        
        return juros;
    }
    
    public String getDescricaoParcelaAdicionadaPosLista(int pos)
    {
       String resp = "";
        
        if(pos >= 0 && pos < parcelas.size())
            resp = ((Parcela)parcelas.get(pos)).getDescricao();
        
        return resp;
    }
    
    public LocalDate getDataParcelaAdicionadaPosLista(int pos)
    {
        if(pos >= 0 && pos < parcelas.size())
            return ((Parcela)parcelas.get(pos)).getDt_vencto();
        
        return null;
    }
    
    public LocalDate getDataPagamentoParcelaAdicionadaPosLista(int pos)
    {
        if(pos >= 0 && pos < parcelas.size())
            return ((Parcela)parcelas.get(pos)).getDt_pgto();
        
        return null;
    }
    
    public void carregarTableViewParcela(TableView tabela)
    {
        tabela.getItems().clear();
        ArrayList<ArrayList<String>> list = new ArrayList();
        Parcela parc = null;
        
        for(int i = 0; i < parcelas.size(); i++)
        {
            parc = ((Parcela)parcelas.get(i));
            
            ArrayList<String> linha = new ArrayList();
            linha.add("" + parc.getId());
            linha.add("" + parc.getDt_vencto());
            linha.add("" + parc.getValor());
            linha.add("" + parc.getDescricao());
            linha.add("" + parc.getJuros());
            linha.add("" + parc.getValor_pgto());
            linha.add("" + parc.getDt_pgto());
            
            list.add(linha);
        }
        
        tabela.setItems(FXCollections.observableArrayList(list));
    }
    
    public double getSaldoCaixaAtual()
    {
        if(caixa_atual != null)
            return ((caixa_atual.getValorini() + caixa_atual.getTotalentradas()) - caixa_atual.getTotalsaidas());
        return (-1);
    }
    
    public boolean buscarParcelasPagasCompraAtual()
    {
        boolean achou = false;
        
        Parcela p = null;
        for(int i = 0; i < parcelas.size() && !achou; i++)
        {
            p = ((Parcela)parcelas.get(i));
            if((p.getDt_pgto() != null && p.getDt_vencto().equals(p.getDt_pgto())) || p.getDt_pgto() != null)
                achou = true;
        }
        
        return achou;
    }
    
    public double sumParcelasPagasCompraAtual()
    {
        double soma = 0.0;
        
        Parcela p = null;
        for(int i = 0; i < parcelas.size(); i++)
        {
            p = ((Parcela)parcelas.get(i));
            if(p.getDt_vencto().equals(p.getDt_pgto()) || p.getDt_pgto() != null);
                soma += p.getValor_pgto();
        }
        
        return soma;
    }
    
    public boolean validaDadosCompraAtual(LocalDate data, double valor_total, int codigo_fornec, boolean atualiza_caixa)
    {
        boolean validou = false;
        
        if(!data.isBefore(LocalDate.now()) && !data.isAfter(LocalDate.now()))
        {
            if(valor_total >= 0)
            {
                boolean achou = false;
                for(int i = 0; i < list_fornecedor.size() && !achou; i++)
                    if(codigo_fornec == list_fornecedor.get(i).getId())
                        achou = true;
                
                if(achou)
                {
                    if(!atualiza_caixa)
                        return true;
                    else
                    {
                        double v_tot_pago = sumParcelasPagasCompraAtual();
                        if(v_tot_pago <= getSaldoCaixaAtual())
                            return true;
                        return false;
                    }
                }
            }
        }
        
        return validou;
    }
    
    public ArrayList buscarCompra(String info, int flag)
    {
        ArrayList list = CompraProduto.buscarCompraFiltro(info, flag);
        return list;
    }
    
    public void carregarTableViewConsultarCompra(TableView tabela, String info, int flag)
    {
        tabela.getItems().clear();
        list_cp = buscarCompra(info, flag);
        
        ArrayList<ArrayList<String>> list = new ArrayList();
        CompraProduto cp = null;
        for(int i = 0; i < list_cp.size(); i++)
        {
            cp = list_cp.get(i);
            
            ArrayList<String> linha = new ArrayList();
            linha.add("" + cp.getId());
            linha.add("" + cp.getData());
            linha.add("" + cp.getValor_total());
            linha.add("" + cp.getFornecedor().getId());
            linha.add("" + cp.getFornecedor().getDescricao());
            linha.add("" + cp.getFornecedor().getCnpj());
            
            list.add(linha);
        }
        
        tabela.setItems(FXCollections.observableArrayList(list));
    }
    
    public void selecionarCompra(int posLista, TableView tabelaProd, TableView tabelaParc)
    {
        tabelaProd.getItems().clear();
        tabelaParc.getItems().clear();
        clearCompraAtual();
        
        if(!list_cp.isEmpty() && (posLista >= 0 && posLista < list_cp.size()))
        {
            compra_atual = list_cp.get(posLista);
            
            ArrayList<Parcela> list_parcelas = compra_atual.getParcelas();
            for(int i = 0; i < list_parcelas.size(); i++)
                parcelas.add(list_parcelas.get(i));
            
            CompraItem ci = null;
            ArrayList <Produto> list_aux = null;
            Produto p = null;
            
            for(int i = 0; i < compra_atual.getItens().size(); i++)
            {
                ci = compra_atual.getItens().get(i);
                list_aux = buscarProduto("" + ci.getProduto().getId(), 1);
                
                if(!list_aux.isEmpty())
                {
                    p = list_aux.get(0);
                    p.setQtd(ci.getQtde());
                    p.setVl_unit(ci.getValor());
                    
                    p.clearLotes();
                    p.addLotes(ci.getLote());
                    
                    itens.add(new CompraItem(compra_atual, p, p.getQtd(), p.getVl_unit(), 0, p.getLotes().get(0)));
                }
            }
            
            carregarTableViewComprarProduto(tabelaProd);
            carregarTableViewParcela(tabelaParc);
        }
    }
    
    public int getCodigoCompraAtual()
    {
        if(compra_atual != null)
            return compra_atual.getId();
        return (-1);
    }
    
    public double getValorTotalCompraAtual()
    {
        if(compra_atual != null)
            return compra_atual.getValor_total();
        return (-1);
    }
    
    public int getCodigoFornecedorCompraAtual()
    {
        if(compra_atual != null)
            return compra_atual.getFornecedor().getId();
        return (-1);
    }
    
    public LocalDate getDataCompraAtual()
    {
        if(compra_atual != null)
            return compra_atual.getData();
        return null;
    }
    
    public void carregarDadosEssenciaisCompraAtual(LocalDate data, double valor_total, int codigo_fornec, boolean atualiza_caixa)
    {
        if(validaDadosCompraAtual(data, valor_total, codigo_fornec, atualiza_caixa))
        {
            compra_atual.setId(0);
            compra_atual.setData(data);
            compra_atual.setValor_total(valor_total);
            
            Fornecedor fornec = null;
            boolean achou = false;
            for(int i = 0; i < list_fornecedor.size() && !achou; i++)
                if(codigo_fornec == list_fornecedor.get(i).getId())
                {
                    fornec = list_fornecedor.get(i);
                    achou = true;
                }
            
            compra_atual.setFornecedor(fornec);
            compra_atual.getParcelas().clear();
            for(int i = 0; i < parcelas.size(); i++)
                compra_atual.addParcela((Parcela)parcelas.get(i));
            
            compra_atual.clearItens();
            for(int i = 0; i < itens.size(); i++)
                compra_atual.addItem((CompraItem)itens.get(i));
        }
    }
    
    @Override
    public boolean gravarObjeto()
    {
        boolean executou = false;
        
        try
        {
            if(compra_atual.getId() == 0 && compra_atual.getValor_total() >= 0 && 
                    compra_atual.getData() != null && compra_atual.getFornecedor() != null)
            {
                executou = CompraProduto.salvar(compra_atual);

                if(executou)
                {
                    for(int i = 0; i < itens.size(); i++) // compra_atual.getItens().size()
                        ((CompraItem)itens.get(i)).setCompra(new CompraProduto(compra_atual.getId(), compra_atual.getData(), 
                                compra_atual.getValor_total(), compra_atual.getFornecedor()));
                }
            }
        }catch(Exception e){ }
        
        return executou;
    }

    @Override
    public boolean gravarItem(int indice)
    {
        boolean executou = false;
        
        try
        {
            int erros = 0, num_lote = 0;

            // CRIANDO OU ATUALIZANDO O LOTE DO PRODUTO
            ArrayList<Lote> lotes = null;
            Lote lote = ((CompraItem)itens.get(indice)).getProduto().getLotes().get(0);

            try
            {
                lotes = (ArrayList<Lote>) Lote.buscaLoteCodigoProdCodigo(lote.getId(), 
                    ((CompraItem)itens.get(indice)).getProduto().getId());
            }
            catch(Exception e){ lotes = new ArrayList(); }

            if(!lotes.isEmpty()) // ATUALIZANDO O LOTE
            {
               lote.setQtde(lote.getQtde() + lotes.get(0).getQtde());
               if(!Lote.alterar(lote, ((CompraItem)itens.get(indice)).getProduto().getId()))
                   erros++;
            }
            else // SALVANDO O LOTE
            {
                if(!Lote.salvar(lote, ((CompraItem)itens.get(indice)).getProduto().getId()))
                    erros++;

                try{ num_lote = Lote.getMaxPKLoteProd(((CompraItem)itens.get(indice)).getProduto().getId()); }
                catch(Exception e2){ num_lote = 0; }

                lote.setId(num_lote);
            }

            // SALVANDO O ITEM DA COMPRA
            ((CompraItem)itens.get(indice)).setLote(lote);

            if(erros == 0)
                if(CompraItem.salvar(((CompraItem)itens.get(indice))))
                { executou = true; }
        }catch(Exception e){ }
        
        return executou;
    }

    @Override
    public boolean atualizarEstoqueProduto(int indice, boolean remover)
    {
        boolean executou = false;
        int erros = 0;
        
        try
        {
            if(!remover)
            {
                // DECREMENTANDO O ESTOQUE DO PRODUTO
                ArrayList<Produto> list_aux = (ArrayList) ((CompraItem)itens.get(indice)).getProduto().buscarProdutoFiltro("" + 
                        ((CompraItem)itens.get(indice)).getProduto().getId(), 1);
                int est;
                double v;
                Produto p_aux = null;

                if(!list_aux.isEmpty())
                {
                    p_aux = ((CompraItem)itens.get(indice)).getProduto();

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
                CompraItem ci = ((CompraItem)itens.get(indice));
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

    @Override
    public boolean gravarParcelas(int indice)
    {
        boolean executou = true;
        
        try
        {
            int erros = 0;

            Parcela p = ((Parcela)parcelas.get(indice));
            Caixa caixa = p.getCaixa();

            // VERIFICANDO SE SERÁ PRECISO ATUALIZAR O CAIXA
            if(p.getValor_pgto() >= 0 && p.getDt_pgto() != null)
            {
                caixa.setTotalsaidas(caixa.getTotalsaidas() + p.getValor_pgto());

                if(!Caixa.alterar(caixa))
                    erros++;
            }

            // SALVANDO A PARCELA
            if(!Parcela.salvar(p, compra_atual.getId()))
                erros++;

            if(erros == 0)
                executou = true;
        }catch(Exception e){ }
        
        return executou;
    }

    @Override
    public boolean removerObjeto()
    {
        boolean executou = false;
        
        try
        {
            if(compra_atual != null && !compra_atual.getItens().isEmpty() && !compra_atual.getParcelas().isEmpty())
                if(!buscarParcelasPagasCompraAtual())
                { executou = CompraProduto.apagar(compra_atual); }
        }catch(Exception e){ }
        
        return executou;
    }

    @Override
    public boolean removerItem(int indice)
    {
        boolean executou = false;
        int erros = 0;
        
        try
        {
            CompraItem ci = ((CompraItem)itens.get(indice));
            
            // ATUALIZANDO OU REMOVENDO O LOTE ENVOLVIDO NA COMPRA
            Lote lote = null;
            lote = ci.getLote();

            ArrayList<CompraItem>list = (ArrayList<CompraItem>) CompraItem.getItensCompraPorLoteEPorProd(lote.getId(), 
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
            if(!CompraItem.apagar(ci.getCompra().getId(), ci.getProduto().getId(), ci.getLote().getId()))
                erros++;
            
            if(erros == 0)
                executou = true;
        }catch(Exception e){ }
        
        return executou;
    }

    @Override
    public boolean removerParcelas(int indice)
    {
        boolean executou = false;
        
        try{ executou = Parcela.apagar(((Parcela)parcelas.get(indice))); }catch(Exception e){ }
        
        return executou;
    }
    // ------------------------------------------------------------------------------------------------------------------
}