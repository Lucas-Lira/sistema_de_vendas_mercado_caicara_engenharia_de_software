package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import prjmercadocaiçara.db.controladoras.CtrlComprarProdutos;
import prjmercadocaiçara.db.persistencia.Banco;
import prjmercadocaiçara.ui.util.MaskFieldUtil;
import prjmercadocaiçara.util.facade.DisplayComboBox;

public class TelaComprarProdutosController implements Initializable
{
    @FXML
    private TabPane tabprincipal;
    @FXML
    private VBox pndados;
    @FXML
    private JFXButton bconfirmar;
    @FXML
    private JFXButton bcancelar;
    @FXML
    private JFXTextField txcodigo;
    @FXML
    private Label tituloprincipal;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private Tab tabComprarProdutos;
    @FXML
    private Label lbdtoperacao;
    @FXML
    private JFXDatePicker dtoperacao;
    @FXML
    private JFXComboBox<Object> cbfornecedor;
    @FXML
    private JFXComboBox<Object> cbformapagamento;
    @FXML
    private JFXComboBox<Object> cbqtdeparcelas;
    @FXML
    private JFXTextField txdiapagamento;
    @FXML
    private JFXTextField txvalortotal;
    @FXML
    private JFXTextField txdescricaoproduto;
    @FXML
    private JFXButton bconsultarproduto;
    @FXML
    private JFXTextField txqtdeproduto;
    @FXML
    private JFXTextField txvalorunitproduto;
    @FXML
    private JFXButton baddproduto;
    @FXML
    private JFXButton bdelproduto;
    @FXML
    private TableView<Object> tabelaprod;
    @FXML
    private TableColumn<ArrayList<String>, String> colcodigo;
    @FXML
    private TableColumn<ArrayList<String>, String> coldescricao;
    @FXML
    private TableColumn<ArrayList<String>, String> colvalorunit;
    @FXML
    private TableColumn<ArrayList<String>, String> colqtde;
    @FXML
    private TableColumn<ArrayList<String>, String> colcodigotipo;
    @FXML
    private TableColumn<ArrayList<String>, String> coldescricaotipo;
    @FXML
    private TableColumn<ArrayList<String>, String> collote;
    @FXML
    private TableColumn<ArrayList<String>, String> colfileira;
    @FXML
    private TableColumn<ArrayList<String>, String> colprateleira;
    @FXML
    private JFXDatePicker dtfabricacao;
    @FXML
    private JFXDatePicker dtvencimento;
    @FXML
    private TableColumn<ArrayList<String>, String> collotedtfabric;
    @FXML
    private TableColumn<ArrayList<String>, String> collotedtvenc;
    @FXML
    private JFXComboBox<Object> cbfileira;
    @FXML
    private JFXComboBox<Object> cbprateleira;
    @FXML
    private JFXTextField txlote;
    @FXML
    private VBox pndados2;
    @FXML
    private Label tituloprincipal2;
    @FXML
    private VBox pnpesquisa2;
    @FXML
    private JFXButton bselecionar;
    @FXML
    private Tab tabConsultaProduto;
    @FXML
    private JFXButton bcancelarconsultaprod;
    @FXML
    private TableView<Object> tabelaconsultprod;
    @FXML
    private JFXButton bapagar;
    @FXML
    private JFXButton bgerarparcelas;
    @FXML
    private JFXTextField txqtdeprodutoestoque;
    @FXML
    private JFXTextField txvalorunitprodutocadastrado;
    @FXML
    private JFXTextField txinformacaoconsultprod;
    @FXML
    private JFXComboBox<Object> cbfiltroconsultprod;
    @FXML
    private Tab tabConsultaCompra;
    @FXML
    private Label tituloprincipal4;
    @FXML
    private JFXTextField txinformacaoconsultcompra;
    @FXML
    private JFXComboBox<Object> cbfiltroconsultcompra;
    @FXML
    private JFXButton bconsultarcompra;
    @FXML
    private JFXButton bselecionarcompra;
    @FXML
    private JFXButton bcancelarconsultacompra;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutocodigo;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutodescricao;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutovalorunit;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutoqtde;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutocodigotipo;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutodescricaotipo;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutolote;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutofileira;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutoprateleira;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutolotedtfabric;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaprodutolotedtvenc;
    @FXML
    private TableView<Object> tabelaconsultacompra;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacodigocompra;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacompradata;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacompravalortotal;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacompracodigofornecedor;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacompranomefornecedor;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacompracnpjfornecedor;
    @FXML
    private Label tituloprincipal3;
    @FXML
    private JFXButton bconfirmarparcelas;
    @FXML
    private JFXButton bcancelarparcelas;
    @FXML
    private TableView<Object> tabelageraparcelas;
    @FXML
    private TableColumn<ArrayList<String>, String> colgeraparcelascodigo;
    @FXML
    private TableColumn<ArrayList<String>, String> colgeraparcelasdatavencimento;
    @FXML
    private TableColumn<ArrayList<String>, String> colgeraparcelasvalor;
    @FXML
    private TableColumn<ArrayList<String>, String> colgeraparcelasdescrição;
    @FXML
    private TableColumn<ArrayList<String>, String> colgeraparcelasvalorjuros;
    @FXML
    private TableColumn<ArrayList<String>, String> colgeraparcelasvalorpagamento;
    @FXML
    private TableColumn<ArrayList<String>, String> colgeraparcelasdatapagamento;
    @FXML
    private VBox pndados3;
    @FXML
    private VBox pnpesquisa3;
    @FXML
    private Label tituloprincipal3_2;
    @FXML
    private JFXTextField txparceladescricao;
    @FXML
    private JFXTextField txparcelavalor;
    @FXML
    private JFXDatePicker dtvencimentoparcela;
    @FXML
    private JFXTextField txparcelajuros;
    @FXML
    private JFXTextField txparcelavalorpagto;
    @FXML
    private JFXDatePicker dtpagamentoparcela;
    @FXML
    private JFXButton baddparcela;
    @FXML
    private JFXButton balterarparcela;
    @FXML
    private JFXButton bdelparcela;
    @FXML
    private VBox pndados4;
    @FXML
    private VBox pnpesquisa4;
    @FXML
    private JFXComboBox<Object> cbtipogeracaoparcelas;
    @FXML
    private Tab tabGerarParcelas;
    @FXML
    private JFXTextField txparcelavalortotal;
    @FXML
    private JFXButton bpesquisarproduto;
    
    private int flag;
    private CtrlComprarProdutos ctrl_comprar_produtos = null;
    private boolean atualiza_total = false;
    private Thread thAtualiza = null;
    private boolean gerou_parcelas = false;
    private boolean atualiza_total2 = false;
    private Thread thAtualiza2 = null;
    private boolean compra_a_vista = false;
    private int codigo_fun;
    private int pos_prod_selecionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        flag = TelaMenuController.getFlag();
        
        ctrl_comprar_produtos = CtrlComprarProdutos.instanciarCtrlCP();
        codigo_fun = Banco.getId_fun();
        
        if(ctrl_comprar_produtos.carregarFuncionarioAtual(codigo_fun))
        {
            configuraComponentes();
            
            ctrl_comprar_produtos.inicializarTableViewComprarProduto(tabelaprod);
            ctrl_comprar_produtos.inicializarTableViewConsultarProduto(tabelaconsultprod);
            ctrl_comprar_produtos.inicializarTableViewParcela(tabelageraparcelas);
            ctrl_comprar_produtos.inicializarTableViewConsultarCompra(tabelaconsultacompra);
            
            inicializaTelas();
            
            if(!ctrl_comprar_produtos.carregarCaixaEFuncionarioAtuais(codigo_fun, LocalDate.now()))
            {
                String nome_fun = ctrl_comprar_produtos.getNomeFunAtual();
                
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: O caixa desta data (hoje) e deste Funcionário (" + nome_fun + ") "
                        + "encontra-se fechado!" + "\nAcesse a opção Abrir Caixa para solucionar o problema");
                dialogoInfo.showAndWait();
                
                rewindTela();
            }
        }
        else
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Não foi possível recuperar os dados do Funcionário logado!" + 
                    "\nFeche e abra novamente o sistema para solucionar o problema");
            dialogoInfo.showAndWait();
        }
    }
    
    public void inicializaTelas()
    {
        if(thAtualiza != null && thAtualiza.isAlive())
            thAtualiza.interrupt();
        thAtualiza = null;
        if(thAtualiza2 != null && thAtualiza2.isAlive())
            thAtualiza2.interrupt();
        thAtualiza2 = null;
        
        tabConsultaProduto.setDisable(true);
        tabGerarParcelas.setDisable(true);
        dtoperacao.setDisable(true);
        
        if(flag == 0)
        {
            bapagar.setDisable(true);
            tabComprarProdutos.setDisable(false);
            tabConsultaCompra.setDisable(true);
            
            clearComprarProduto();
            atualizaTXValorTotal();
        }
        else
        {
            baddproduto.setDisable(true);
            bdelproduto.setDisable(true);
            bconfirmar.setDisable(true);
            bpesquisarproduto.setDisable(true);
            bselecionarcompra.setDisable(true);
            tabConsultaCompra.setDisable(false);
            tabComprarProdutos.setDisable(true);
            atualiza_total = false;
            clearConsultaCompra();
            tabprincipal.getSelectionModel().select(3);
        }
    }
    
    public static Tooltip geraMsgB(String msg)
    {
        Tooltip tt = new Tooltip();
        tt.setText(msg);
        tt.setStyle("-fx-font: normal bold 10 Langdon; "
            + "-fx-text-fill: white;"+"-fx-background-color:   #607D8B;");
        return tt;
    }
    
    public void configuraComponentes()
    {
        MaskFieldUtil.numericField(txcodigo);
        MaskFieldUtil.numericField(txqtdeprodutoestoque);
        MaskFieldUtil.numericField(txqtdeproduto);
        MaskFieldUtil.numericField(txdiapagamento);
        
        MaskFieldUtil.monetaryField(txvalortotal);
        MaskFieldUtil.monetaryField(txvalorunitprodutocadastrado);
        MaskFieldUtil.monetaryField(txvalorunitproduto);
        MaskFieldUtil.monetaryField(txparcelajuros);
        MaskFieldUtil.monetaryField(txparcelavalorpagto);
        MaskFieldUtil.monetaryField(txparcelavalor);
        MaskFieldUtil.monetaryField(txparcelavalortotal);
        
        MaskFieldUtil.numericField(txlote);
        
        bconfirmar.setTooltip(geraMsgB("Confirmar"));
        bapagar.setTooltip(geraMsgB("Apagar Compra"));
        bcancelar.setTooltip(geraMsgB("Cancelar/Sair"));
        bgerarparcelas.setTooltip(geraMsgB("Gerar as Parcelas"));
        bpesquisarproduto.setTooltip(geraMsgB("Pesquisar Produtos"));
        baddproduto.setTooltip(geraMsgB("Adicionar Produto"));
        bdelproduto.setTooltip(geraMsgB("Remover Produto"));
        
        bconsultarproduto.setTooltip(geraMsgB("Consultar Produtos"));
        bselecionar.setTooltip(geraMsgB("Selecionar Produto"));
        bcancelarconsultaprod.setTooltip(geraMsgB("Cancelar/Sair"));
        
        bconfirmarparcelas.setTooltip(geraMsgB("Confirmar geração de Parcelas"));
        bcancelarparcelas.setTooltip(geraMsgB("Cancelar/Sair"));
        baddparcela.setTooltip(geraMsgB("Adicionar Parcela"));
        balterarparcela.setTooltip(geraMsgB("Alterar Parcela"));
        bdelparcela.setTooltip(geraMsgB("Remover Parcela"));
        
        bconsultarcompra.setTooltip(geraMsgB("Consultar Compras"));
        bselecionarcompra.setTooltip(geraMsgB("Selecionar Compra"));
        bcancelarconsultacompra.setTooltip(geraMsgB("Cancelar/Sair"));
        
        carregaComboBoxFiltroConsultaCompra();
        carregaComboBoxFiltroConsultaProduto();
        carregaComboBoxFornecedor();
        carregaComboBoxParcelaFPagamento();
        carregaComboBoxParcelaQtdeParcelas();
        carregaComboBoxParcelaTipoGeracao();
        carregaComboBoxFileira();
    }
    
    public void carregaComboBoxFileira()
    {
        ctrl_comprar_produtos.carregarComboBoxFileira(cbfileira);
        cbfileira.getSelectionModel().select(0);
        
        ctrl_comprar_produtos.carregarComboBoxPrateleira(cbprateleira, 0);
        cbprateleira.getSelectionModel().select(0);
    }
    
    @FXML
    private void clkSelecionaFileira(ActionEvent event)
    {
        int pos = cbfileira.getSelectionModel().getSelectedIndex();
        ctrl_comprar_produtos.carregarComboBoxPrateleira(cbprateleira, pos);
        cbprateleira.getSelectionModel().select(0);
    }
    
    public String corrigeMaskaraDinheiro(String dado)
    {
        String ret = "";
        boolean decimal = false;
        for(int i = dado.length() - 1; i >= 0; i--)
        {
            if(dado.charAt(i) != ',' && dado.charAt(i) != '.')
                ret += dado.charAt(i);
            else
                if(!decimal)
                {
                    ret += '.';
                    decimal = !decimal;
                }
        }
        dado =  "";
        for(int i = ret.length() - 1; i >= 0; i --)
            dado += ret.charAt(i);
        return dado;
    }

    /* --------------------------------------------------------------------------------------------------------------- */
    // [1] TELA COMPRAR PRODUTOS:
    /* --------------------------------------------------------------------------------------------------------------- */
    @FXML
    private void clkConfirmar(ActionEvent event)
    {
        if(!tabelaprod.getItems().isEmpty() && !tabelageraparcelas.getItems().isEmpty())
        {
            double v_parc_pagas = 0.0, v_disponivel = 0.0;
            int pos = cbfornecedor.getSelectionModel().getSelectedIndex();
            int codigo_fornecedor = Integer.parseInt(((DisplayComboBox)(cbfornecedor.getItems().get(pos))).getValueMember());
            String str = "";
            double v_total = 0.0;

            str = corrigeMaskaraDinheiro(txvalortotal.getText());
            try{ v_total = Double.parseDouble(str); }catch(Exception ex){ v_total = 0.0; }

            boolean at_caixa = false, executar = true;

            if(compra_a_vista || ctrl_comprar_produtos.buscarParcelasPagasCompraAtual())
            {
                at_caixa = true;
                v_parc_pagas = ctrl_comprar_produtos.sumParcelasPagasCompraAtual();
                v_disponivel = ctrl_comprar_produtos.getSaldoCaixaAtual();

                if(v_parc_pagas > v_disponivel)
                    executar = false;
            }
            
            if(executar)
            {
                ctrl_comprar_produtos.carregarDadosEssenciaisCompraAtual(dtoperacao.getValue(), v_total, 
                        codigo_fornecedor, at_caixa);
                
                if(ctrl_comprar_produtos.gravar())
                { clearComprarProduto(); }
                else
                {
                    bconfirmar.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível efetuar a Compra!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Não há saldo suficiente disponível neste caixa para efetuar a Compra!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: A Compra não contém itens ou as parcelas não foram geradas!");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event)
    { rewindTela(); }
    
    @FXML
    private void clkPesquisarProduto(ActionEvent event)
    {
        clearConsultaProduto();
        tabComprarProdutos.setDisable(true);
        tabConsultaProduto.setDisable(false);
        tabprincipal.getSelectionModel().select(1);
    }
    
    public void atualizaTXValorTotal()
    {
        if(thAtualiza == null || thAtualiza.isInterrupted())
        {
            thAtualiza = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while(atualiza_total)
                    {
                        Platform.runLater(()->{
                            
                            double valor = 0.0;
                            for(int i = 0; i < tabelaprod.getItems().size(); i++)
                            {
                                valor += (ctrl_comprar_produtos.getQtdeProdAdicionadoPosLista(i) * 
                                        ctrl_comprar_produtos.getValorUnitarioProdAdicionadoPosLista(i));
                            }
                            
                            txvalortotal.setText("" + valor + "0");
                        });

                        try{ Thread.sleep(500); }catch(Exception ex){ }
                    }
                }
            });
            
            thAtualiza.start();
        }
    }
    
    public boolean validaDadosProduto()
    {
        boolean validou = false;
        
        if(txdescricaoproduto.getText().isEmpty() || txqtdeprodutoestoque.getText().isEmpty() ||
                txvalorunitprodutocadastrado.getText().isEmpty())
        {
            bconsultarproduto.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhum produto foi consultado / selecionado!");
            dialogoInfo.showAndWait();
        }
        else
        {
            int lote;
            try{ lote = Integer.parseInt(txlote.getText()); }
            catch(Exception ex){ lote = 0; }
            
            if(lote <= 0)
            {
                txlote.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Lote inválido!");
                dialogoInfo.showAndWait();
            }
            else
            {
                if(cbfileira.getItems().isEmpty() || cbfileira.getSelectionModel().getSelectedIndex() < 0)
                {
                    cbfileira.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: A fileira de armazenamento do Produto não foi selecionada!");
                    dialogoInfo.showAndWait();
                }
                else
                {
                    if(cbprateleira.getItems().isEmpty() || cbprateleira.getSelectionModel().getSelectedIndex() < 0)
                    {
                        cbprateleira.requestFocus();
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: A prateleira de armazenamento do Produto não foi selecionada!");
                        dialogoInfo.showAndWait();
                    }
                    else
                    {
                        if(dtfabricacao.getValue() != null)
                        {
                            if(dtvencimento.getValue() != null)
                            {
                                LocalDate d = dtfabricacao.getValue();
                                LocalDate d2 = dtvencimento.getValue();
                                
                                if(d.isBefore(d2) || d.isEqual(d2))
                                {
                                    if(d.isAfter(LocalDate.now()))
                                    {
                                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                        dialogoInfo.setContentText("[ ERRO ]: A Dt. de Frabricação é inválida!");
                                        dialogoInfo.showAndWait();
                                    }
                                    else
                                        validou = true;
                                }
                                else
                                {
                                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                    dialogoInfo.setContentText("[ ERRO ]: A Dt. de Frabricação não pode ser posterior a Dt. de Vencimento!");
                                    dialogoInfo.showAndWait();
                                }
                            }
                            else
                            {
                                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                dialogoInfo.setContentText("[ ERRO ]: A Data de Vencimento não foi Selecionada!");
                                dialogoInfo.showAndWait();
                            }
                        }
                        else
                        {
                            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                            dialogoInfo.setContentText("[ ERRO ]: A Data de Fabricação não foi Selecionada!");
                            dialogoInfo.showAndWait();
                        }
                    }
                }
            }
        }
        
        return validou;
    }
    
    @FXML
    private void clkAddProduto(ActionEvent event)
    {
        if(validaDadosProduto())
        {
            int resp, qtde, lote;
            double valor;
            
            try{ qtde = Integer.parseInt(txqtdeproduto.getText()); }
            catch(Exception ex){ qtde = 0; }
            String v = corrigeMaskaraDinheiro(txvalorunitproduto.getText());
            try{ valor = Double.parseDouble(v); }
            catch(Exception ex){ valor = 0.0; }
            
            LocalDate dfabric, dvenc;
            dfabric = dtfabricacao.getValue();
            dvenc = dtvencimento.getValue();
            try{ lote = Integer.parseInt(txlote.getText()); }
            catch(Exception ex){ lote = 0; }
            
            int fileira = cbfileira.getSelectionModel().getSelectedIndex();
            int prateleira = cbprateleira.getSelectionModel().getSelectedIndex();
            resp = ctrl_comprar_produtos.addProdutoCompraAtual(pos_prod_selecionado, qtde, valor, lote, dfabric, 
                    dvenc, fileira, prateleira);
            
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            switch(resp)
            {
                case (-6):
                    txvalorunitproduto.requestFocus();
                    dialogoInfo.setContentText("[ ERRO ]: Produto com Lote já inserido e datas diferentes!");
                    dialogoInfo.showAndWait();
                break;
                case (-5):
                    txvalorunitproduto.requestFocus();
                    dialogoInfo.setContentText("[ ERRO ]: A Fileira ou a Prateleira é inválida!");
                    dialogoInfo.showAndWait();
                break;
                case (-4):
                    txvalorunitproduto.requestFocus();
                    dialogoInfo.setContentText("[ ERRO ]: Lote inválido!");
                    dialogoInfo.showAndWait();
                break;
                case (-3):
                    txvalorunitproduto.requestFocus();
                    dialogoInfo.setContentText("[ ERRO ]: Há data(s) inválida(s) no Lote!");
                    dialogoInfo.showAndWait();
                break;
                case (-2):
                    txvalorunitproduto.requestFocus();
                    dialogoInfo.setContentText("[ ERRO ]: Valor inválido (inferior a R$0,00)!");
                    dialogoInfo.showAndWait();
                break;
                case (-1):
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível recuperar este produto no banco de dados!");
                    dialogoInfo.showAndWait();
                break;
                case 0:
                    txqtdeproduto.requestFocus();
                    dialogoInfo.setContentText("[ ERRO ]: Produto com a quantidade inferior ou igual a zero!");
                    dialogoInfo.showAndWait();
                break;
                case 1:
                    ctrl_comprar_produtos.carregarTableViewComprarProduto(tabelaprod);
                    clearComprarProduto_Itens();
                break;
            }
        }
    }

    @FXML
    private void clkDelProduto(ActionEvent event)
    {
        if(!tabelaprod.getItems().isEmpty())
        {
            int pos = tabelaprod.getSelectionModel().getSelectedIndex();

            if(pos >= 0 && pos < tabelaprod.getItems().size())
            {
                if(ctrl_comprar_produtos.delProdutoCompraAtual(pos))
                {
                    ctrl_comprar_produtos.carregarTableViewComprarProduto(tabelaprod);
                    pos_prod_selecionado = (-1);
                }
            }
            else
            {
                baddproduto.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Nenhum produto foi selecionado!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhum produto foi adicionado a Lista!");
            dialogoInfo.showAndWait();
        }
    }
    
    @FXML
    private void clkApagar(ActionEvent event)
    {
        if(ctrl_comprar_produtos.buscarParcelasPagasCompraAtual())
        {
            bcancelar.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Esta compra já contém parcelas pagas!");
            dialogoInfo.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("CONFIRMAÇÃO");
            alert.setContentText("Deseja realmente excluir esta Compra?");
            ButtonType buttonTypeOne = new ButtonType("Sim");
            ButtonType buttonTypeTwo = new ButtonType("Não");
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonTypeOne)
            {
                if(ctrl_comprar_produtos.apagar())
                { clearComprarProduto(); }
                else
                {
                    bapagar.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível remover a Compra!");
                    dialogoInfo.showAndWait();
                }
            }
        }
    }
    
    @FXML
    private void clkGerarParcelas(ActionEvent event)
    {
        if(!tabelaprod.getItems().isEmpty())
        {
            if(!gerou_parcelas)
                clearGerarParcelas();
            tabComprarProdutos.setDisable(true);
            tabGerarParcelas.setDisable(false);
            
            atualiza_total2 = true;
            if(thAtualiza2 != null && thAtualiza2.isAlive())
                thAtualiza2.interrupt();
            thAtualiza2 = null;
            
            estadoTelaGerarParcelas(true);
            selecionaItemComboBoxTGeracao();

            tabprincipal.getSelectionModel().select(2);
        }
        else
        {
            baddproduto.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Compra sem produtos adicionados!");
            dialogoInfo.showAndWait();
        }
    }
    
    public void clearComprarProduto()
    {
        txcodigo.clear();
        txcodigo.setText("0");
        txvalortotal.clear();
        dtoperacao.setValue(LocalDate.now());
        if(cbfornecedor.getItems().size() > 0)
            cbfornecedor.getSelectionModel().select(0);
        
        atualiza_total = true;
        gerou_parcelas = false;
        
        clearComprarProduto_Itens();
        tabelaprod.getItems().clear();
        
        ctrl_comprar_produtos.clearCompraAtual();
        
        cbfileira.getSelectionModel().select(0);
    }
    
    public void carregaComboBoxFornecedor()
    {
        ctrl_comprar_produtos.carregarComboBoxFornecedor(cbfornecedor, "");
        
        if(cbfornecedor.getItems().isEmpty())
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setContentText("[ ERRO ]: Não foi possível carregar a lista dos Fornecedores!");
            dialogoInfo.showAndWait();
            
            rewindTela();
        }
    }
    
    public void clearComprarProduto_Itens()
    {
        txdescricaoproduto.clear();
        txqtdeproduto.clear();
        txqtdeprodutoestoque.clear();
        txvalorunitproduto.clear();
        txvalorunitprodutocadastrado.clear();
        
        txlote.clear();
        cbfileira.getSelectionModel().select(0);
        pos_prod_selecionado = (-1);
        
        dtfabricacao.setValue(LocalDate.now());
        dtvencimento.setValue(LocalDate.now());
    }
    
    /* --------------------------------------------------------------------------------------------------------------- */
    // [2] TELA CONSULTAR PRODUTOS:
    /* --------------------------------------------------------------------------------------------------------------- */
    @FXML
    private void clkConsultarProduto(ActionEvent event)
    {
        tabelaconsultprod.getItems().clear();
        String info = txinformacaoconsultprod.getText();
        int pos = Integer.parseInt(((DisplayComboBox)(cbfiltroconsultprod.getItems().get(cbfiltroconsultprod.
                getSelectionModel().getSelectedIndex()))).getValueMember());
        
        if(!info.isEmpty() || pos == 2 || pos == 5 || pos == 7 || pos == 8)
        {
            if(pos >= 0)
            {
                ctrl_comprar_produtos.carregarTableViewConsultarProduto(tabelaconsultprod, pos, info);
                
                if(tabelaconsultprod.getItems().isEmpty())
                {
                    bselecionar.setDisable(true);
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Nenhum dado correspondente à pesquisa foi encontrado!");
                    dialogoInfo.showAndWait();
                }
                else
                    bselecionar.setDisable(false);
            }
            else
            {
                cbfiltroconsultprod.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Nenhum filtro foi selecionado!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            txinformacaoconsultprod.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma informação para a pesquisa foi inserida!");
            dialogoInfo.showAndWait();
        }
    }
    
    @FXML
    private void clkSelecionarProduto(ActionEvent event)
    {
        int pos = tabelaconsultprod.getSelectionModel().getSelectedIndex();
        if(pos >= 0 && pos < tabelaconsultprod.getItems().size())
        {
            int qtde = 0;
            double v = 0.0;
            int codigo_prod = ctrl_comprar_produtos.getCodigoProdConsultado(pos);
            int codigo_lote = ctrl_comprar_produtos.getCodigoLoteProdConsultadoMaxDtVenc(pos);
            
            int posi = ctrl_comprar_produtos.buscaProdCompraAtual(codigo_prod, codigo_lote, 
                    LocalDate.now(), LocalDate.now());
            
            if(codigo_lote <= 0)
                codigo_lote = 0;
            txlote.setText("" + codigo_lote);
            
            int pos_prateleira = ctrl_comprar_produtos.getPosPrateleiraList(codigo_prod);
            cbprateleira.getSelectionModel().select(pos_prateleira);
            
            int pos_fileira = ctrl_comprar_produtos.getPosFileiraList(codigo_prod, pos_prateleira);
            cbfileira.getSelectionModel().select(pos_fileira);
            
            if(codigo_lote != 0)
            {
                dtfabricacao.setValue(ctrl_comprar_produtos.getDtFabricLoteProdConsultadoMaxDtVenc(pos));
                dtvencimento.setValue(ctrl_comprar_produtos.getDtVencLoteProdConsultadoMaxDtVenc(pos));
            }
            
            if(posi == (-1))
            {
                qtde = ctrl_comprar_produtos.getQtdeProdConsultado(pos);
                v = ctrl_comprar_produtos.getValorUnitarioProdConsultado(pos);
            }
            else
            {
                qtde = ctrl_comprar_produtos.getQtdeProdConsultado(pos) + 
                        ctrl_comprar_produtos.getQtdeProdAdicionado(codigo_prod);
                v = ctrl_comprar_produtos.getValorUnitarioProdAdicionado(codigo_prod);
            }
            
            pos_prod_selecionado = pos;
            
            txqtdeprodutoestoque.setText("" + qtde);
            txdescricaoproduto.setText(ctrl_comprar_produtos.getDescricaoProdConsultado(pos));
            txvalorunitprodutocadastrado.setText("" + v + "0");
            
            clearConsultaProduto();
            tabConsultaProduto.setDisable(true);
            tabComprarProdutos.setDisable(false);
            tabprincipal.getSelectionModel().select(0);
        }
        else
        {
            bconsultarproduto.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Não há produtos Pesquisados!");
            dialogoInfo.showAndWait();
        }
    }
    
    @FXML
    private void clkCancelarConsultaProd(ActionEvent event)
    {
        clearConsultaProduto();
        tabConsultaProduto.setDisable(true);
        tabComprarProdutos.setDisable(false);
        tabprincipal.getSelectionModel().select(0);
    }
    
    public void clearConsultaProduto()
    {
        txinformacaoconsultprod.clear();
        cbfiltroconsultprod.getSelectionModel().select(0);
        tabelaconsultprod.getItems().clear();
        bselecionar.setDisable(true);
    }
    
    public void carregaComboBoxFiltroConsultaProduto()
    {
        ctrl_comprar_produtos.carregarComboBoxFiltroConsultaProduto(cbfiltroconsultprod);
        cbfiltroconsultprod.getSelectionModel().select(0);
    }
    
    /* --------------------------------------------------------------------------------------------------------------- */
    // [3] TELA GERAR PARCELAS:
    /* --------------------------------------------------------------------------------------------------------------- */
    public boolean geraParcelasAutomaticas()
    {
        boolean executou = false;
        
        if(validaDadosParcelasAutomaticas())
        {
            int pos = cbformapagamento.getSelectionModel().getSelectedIndex();
            int cont_p = 1, qtde;
            int dia, mes, ano;
            int erros = 0;

            try{ dia = Integer.parseInt(txdiapagamento.getText()); }catch(Exception e){ dia = 1; }
            mes = LocalDate.now().getMonthValue();
            ano = LocalDate.now().getYear();
            
            if(dia < LocalDate.now().getDayOfMonth())
            {
                if(mes == 12)
                {
                    mes = 1;
                    ano++;
                }
                else
                    mes++;
            }

            LocalDate data = null;
            String str = "", desc = "";
            double v_total, sum = 0.0, v;

            qtde = (cbqtdeparcelas.getSelectionModel().getSelectedIndex() + 1);
            str = corrigeMaskaraDinheiro(txparcelavalortotal.getText());
            try{ v_total = Double.parseDouble(str); }catch(Exception e2){ v_total = 0.0; }

            v = v_total/qtde;
            v = Double.parseDouble("" + truncateDecimal(v, 2));

            LocalDate d2 = LocalDate.now();
            if(dtoperacao.getValue() != null)
                d2 = dtoperacao.getValue();

            for(int i = 0; i < qtde; i++, cont_p++)
            {
                data = LocalDate.of(ano, mes, dia);

                desc = "Parcela número " + cont_p;
                if(i == (qtde - 1))
                {
                    v = v_total - sum;
                    v = Double.parseDouble("" + truncateDecimal(v, 2));
                }

                sum += v;
                if(pos == 1) // VENDA PARCELADA
                {
                    if(!ctrl_comprar_produtos.addParcelaCompraAtual(0, v, 0.0, 0.0, data, null, desc))
                        erros++;
                }
                else
                {
                    if(!ctrl_comprar_produtos.addParcelaCompraAtual(0, v, v, 0.0, data, d2, desc))
                        erros++;
                }
                
                if(mes == 12)
                {
                    mes = 1;
                    ano++;
                }
                else
                    mes++;
            }
            
            if(erros == 0)
            {
                ctrl_comprar_produtos.carregarTableViewParcela(tabelageraparcelas);

                estadoTelaGerarParcelasSelecaoComboBox(true);
                txdiapagamento.clear();
                executou = true;
            }
            else
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Não foi possível gerar estas Parcelas!");
                dialogoInfo.showAndWait();
            }
        }
        
        return executou;
    }
    
    @FXML
    private void clkConfirmarParcelas(ActionEvent event)
    {
        double v_total = 0.0;
        
        int pos = cbtipogeracaoparcelas.getSelectionModel().getSelectedIndex();
        if(pos == 1 && tabelageraparcelas.getItems().isEmpty())
        {
            if(geraParcelasAutomaticas())
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setContentText("[ Aviso ]: As parcelas foram geradas com sucesso.\nClique novamente para retornar a tela anterior!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            if(!tabelageraparcelas.getItems().isEmpty())
            {
                String str = corrigeMaskaraDinheiro(txparcelavalortotal.getText());
                try{ v_total = Double.parseDouble(str); }catch(Exception ex){ v_total = 0.0; }

                if(v_total == 0)
                {
                    gerou_parcelas = true;

                    pos = cbformapagamento.getSelectionModel().getSelectedIndex();
                    if(pos == 0)
                        compra_a_vista = true;
                    
                    txdiapagamento.clear();
                    cbqtdeparcelas.getSelectionModel().select(0);
                    
                    tabGerarParcelas.setDisable(true);
                    tabComprarProdutos.setDisable(false);
                    atualiza_total2 = false;
                    tabprincipal.getSelectionModel().select(0);
                }
                else
                {
                    txparcelavalortotal.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                    dialogoInfo.setContentText("[ ERRO ]: O total da compra ainda não foi atingido!");
                    dialogoInfo.showAndWait();
                }
            }
        }
    }

    @FXML
    private void clkCancelarParcelas(ActionEvent event)
    {
        if(!gerou_parcelas)
        {
            ctrl_comprar_produtos.clearParcelasCompraAtual();
            clearGerarParcelas();
        }
        
        tabGerarParcelas.setDisable(true);
        tabComprarProdutos.setDisable(false);
        atualiza_total2 = false;
        tabprincipal.getSelectionModel().select(0);
    }
    
    public void estadoTelaGerarParcelas(boolean lock)
    {
        txparceladescricao.setDisable(lock);
        txparcelavalor.setDisable(lock);
        dtvencimentoparcela.setDisable(lock);
        txparcelajuros.setDisable(lock);
        txparcelavalorpagto.setDisable(lock);
        dtpagamentoparcela.setDisable(lock);
        
        baddparcela.setDisable(lock);
        balterarparcela.setDisable(lock);
        bdelparcela.setDisable(lock);
    }
    
    public boolean validaDadosParcelasManuais()
    {
        boolean validou = false;
        String str = "";
        double v, v_total;
        
        if(true/*!txparceladescricao.getText().isEmpty()*/)
        {
            str = corrigeMaskaraDinheiro(txparcelavalortotal.getText());
            try{ v_total = Double.parseDouble(str); }catch(Exception ex){ v_total = 0.0; }
            
            str = corrigeMaskaraDinheiro(txparcelavalor.getText());
            try{ v = Double.parseDouble(str); }catch(Exception ex){ v = 0.0; }
            
            if(v >= 0 && v <= v_total)
            {
                if(dtvencimentoparcela.getValue() != null)
                {
                    LocalDate d1 = dtvencimentoparcela.getValue();
                    LocalDate d2 = LocalDate.now();
                    if(dtoperacao.getValue() != null)
                        d2 = dtoperacao.getValue();
                    
                    LocalDate data = dtvencimentoparcela.getValue();
                    if(!d1.isBefore(d2) && !ctrl_comprar_produtos.buscarMesParcelaTabela(data))
                    {
                        str = corrigeMaskaraDinheiro(txparcelajuros.getText());
                        try{ v = Double.parseDouble(str); }catch(Exception ex){ v = 0.0; }
                        
                        if(v >= 0)
                        {
                            str = corrigeMaskaraDinheiro(txparcelavalor.getText());
                            try{ v_total = Double.parseDouble(str); }catch(Exception ex){ v_total = 0.0; }
                            
                            str = corrigeMaskaraDinheiro(txparcelavalorpagto.getText());
                            try{ v = Double.parseDouble(str); }catch(Exception ex){ v = 0.0; }
                            
                            int bandeira = cbformapagamento.getSelectionModel().getSelectedIndex();
                            
                            double juros;
                            str = corrigeMaskaraDinheiro(txparcelajuros.getText());
                            try{ juros = Double.parseDouble(str); }catch(Exception ex){ juros = 0.0; }
                            
                            if(bandeira == 0)
                            {
                                if(v == v_total || v == (v_total + juros))
                                {
                                    LocalDate data_atual = dtoperacao.getValue();
                                    d1 = dtvencimentoparcela.getValue();
                                    
                                    if(d1.equals(dtpagamentoparcela.getValue()) || !dtpagamentoparcela.getValue().isAfter(data_atual))
                                        validou = true;
                                    else
                                    {
                                        dtpagamentoparcela.requestFocus();
                                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                        dialogoInfo.setContentText("[ ERRO ]: Data de pagamento inválida (compra à vista)!");
                                        dialogoInfo.showAndWait();
                                    }
                                }
                                else
                                {
                                    txparcelavalorpagto.requestFocus();
                                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                    dialogoInfo.setContentText("[ ERRO ]: Valor de pgto. inválido!");
                                    dialogoInfo.showAndWait();
                                }
                            }
                            else
                            {
                                if(v >= 0 && v <= v_total)
                                {
                                    d1 = dtvencimentoparcela.getValue();
                                    if(dtpagamentoparcela.getValue() == null || d1.isBefore(dtpagamentoparcela.getValue()) || 
                                            d1.equals(dtpagamentoparcela.getValue()))
                                    { validou = true; }
                                    else
                                    {
                                        dtpagamentoparcela.requestFocus();
                                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                        dialogoInfo.setContentText("[ ERRO ]: Data de pagamento inválida!");
                                        dialogoInfo.showAndWait();
                                    }
                                }
                                else
                                {
                                    txparcelavalorpagto.requestFocus();
                                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                    dialogoInfo.setContentText("[ ERRO ]: Valor de pgto. inválido ou excedente ao valor da Parcela!");
                                    dialogoInfo.showAndWait();
                                }
                            }
                        }
                        else
                        {
                            txparcelajuros.requestFocus();
                            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                            dialogoInfo.setContentText("[ ERRO ]: Valor de juros inválido!");
                            dialogoInfo.showAndWait();
                        }
                    }
                    else
                    {
                        dtvencimentoparcela.requestFocus();
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: Data inválida ou data com mês já existente na tabela!");
                        dialogoInfo.showAndWait();
                    }
                }
                else
                {
                    dtvencimentoparcela.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Nenhuma data para o vencimento foi selecionada!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                txparcelavalor.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Valor inválido ou valor excedente ao restante do Total da Compra!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            txparceladescricao.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma descrição foi inserida!");
            dialogoInfo.showAndWait();
        }
        
        return validou;
    }
    
    @FXML
    private void clkAddParcela(ActionEvent event)
    {
        if(validaDadosParcelasManuais())
        {
            double v, v_pgto, v_juros;
            LocalDate d1, d2;
            String str = "";

            str = corrigeMaskaraDinheiro(txparcelavalor.getText());
            try{ v = Double.parseDouble(str); }catch(Exception ex){ v = 0.0; }
            str = corrigeMaskaraDinheiro(txparcelajuros.getText());
            try{ v_juros = Double.parseDouble(str); }catch(Exception ex){ v_juros = 0.0; }
            str = corrigeMaskaraDinheiro(txparcelavalorpagto.getText());
            try{ v_pgto = Double.parseDouble(str); }catch(Exception ex){ v_pgto = 0.0; }
            d1 = dtvencimentoparcela.getValue();
            d2 = dtpagamentoparcela.getValue();
            str = txparceladescricao.getText();

            if(str.isEmpty())
                str += "Parcela número " + (tabelageraparcelas.getItems().size() + 1);

            if(ctrl_comprar_produtos.addParcelaCompraAtual(0, v, v_pgto, v_juros, d1, d2, str))
            {
                ctrl_comprar_produtos.carregarTableViewParcela(tabelageraparcelas);
                clearGerarParcelasManuais();
            }
            else
            {
                baddparcela.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Não foi possível adicionar esta Parcela!");
                dialogoInfo.showAndWait();
            }
        }
    }

    @FXML
    private void clkAlterarParcela(ActionEvent event)
    {
        if(tabelageraparcelas.getItems().size() > 0)
        {
            int pos = tabelageraparcelas.getSelectionModel().getSelectedIndex();

            if(pos >= 0 && pos < tabelageraparcelas.getItems().size())
            {
                LocalDate d = ctrl_comprar_produtos.getDataPagamentoParcelaAdicionadaPosLista(pos);   
                txparceladescricao.setText(ctrl_comprar_produtos.getDescricaoParcelaAdicionadaPosLista(pos));
                txparcelavalor.setText("" + ctrl_comprar_produtos.getValorParcelaAdicionadaPosLista(pos) + "0");
                txparcelajuros.setText("" + ctrl_comprar_produtos.getJurosParcelaAdicionadaPosLista(pos) + "0");
                txparcelavalorpagto.setText("" + ctrl_comprar_produtos.getValorPgtoParcelaAdicionadaPosLista(pos) + "0");
                dtvencimentoparcela.setValue(ctrl_comprar_produtos.getDataParcelaAdicionadaPosLista(pos));
                if(d != null)
                    dtpagamentoparcela.setValue(d);
                
                if(ctrl_comprar_produtos.delParcelaCompraAtual(pos))
                {
                    ctrl_comprar_produtos.carregarTableViewParcela(tabelageraparcelas);
                }
                else
                {
                    balterarparcela.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível selecionar a Parcela!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                baddparcela.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Nenhuma parcela foi selecionada!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma parcela foi adicionada a Lista!");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void clkDelParcela(ActionEvent event)
    {
        if(tabelageraparcelas.getItems().size() > 0)
        {
            int pos = tabelageraparcelas.getSelectionModel().getSelectedIndex();

            if(pos >= 0 && pos < tabelageraparcelas.getItems().size())
            {
                if(ctrl_comprar_produtos.delParcelaCompraAtual(pos))
                {
                    ctrl_comprar_produtos.carregarTableViewParcela(tabelageraparcelas);
                }
                else
                {
                    bdelparcela.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível remover a Parcela!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                baddparcela.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Nenhuma parcela foi selecionada!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma parcela foi adicionada a Lista!");
            dialogoInfo.showAndWait();
        }
    }
    
    public void clearGerarParcelasManuais()
    {
        txparceladescricao.clear();
        txparcelajuros.clear();
        txparcelavalor.clear();
        txparcelavalorpagto.clear();
        dtpagamentoparcela.setValue(null);
        dtvencimentoparcela.setValue(null);
    }
    
    public void clearGerarParcelas()
    {
        txdiapagamento.clear();
        clearGerarParcelasManuais();
        tabelageraparcelas.getItems().clear();
        gerou_parcelas = false;
        compra_a_vista = false;
    }
    
    public boolean validaDadosParcelasAutomaticas()
    {
        boolean validou = false;
        
        int pos = cbformapagamento.getSelectionModel().getSelectedIndex();
        if(pos >= 0)
        {
            pos = cbqtdeparcelas.getSelectionModel().getSelectedIndex();
            if(pos >= 0)
            {
                if(!txdiapagamento.getText().isEmpty())
                {
                    int dia;
                    try{ dia = Integer.parseInt(txdiapagamento.getText()); }catch(Exception ex){ dia = 0; }
                    if(dia >= 1 && dia <= 31)
                        validou = true;
                    else
                    {
                        txdiapagamento.requestFocus();
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: Dia inválido!");
                        dialogoInfo.showAndWait();
                    }
                }
                else
                {
                    txdiapagamento.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: O dia do vencimento não foi informado!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                cbqtdeparcelas.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: A quantidade de parcelas não foi selecionada!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            cbformapagamento.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma forma de pagamento foi selecionada!");
            dialogoInfo.showAndWait();
        }
        
        return validou;
    }
    
    public void estadoTelaGerarParcelasSelecaoComboBox(boolean lock)
    {
        cbformapagamento.setDisable(lock);
        cbqtdeparcelas.setDisable(lock);
        txdiapagamento.setDisable(lock);
    }
    
    public void selecionaItemComboBoxTGeracao()
    {
        atualizaTXValorTotal2();
        estadoTelaGerarParcelasSelecaoComboBox(true);
        
        cbtipogeracaoparcelas.getSelectionModel().selectedItemProperty().addListener(e->
        {
            clearGerarParcelas();
            estadoTelaGerarParcelasSelecaoComboBox(true);
            
            int pos = cbtipogeracaoparcelas.getSelectionModel().getSelectedIndex();
            if(pos == 0)
            {
                estadoTelaGerarParcelas(false);
            }
            else
            {
                estadoTelaGerarParcelas(true);
                if(pos == 1)
                    estadoTelaGerarParcelasSelecaoComboBox(false);
            }
        });
        
        cbformapagamento.getSelectionModel().selectedItemProperty().addListener(e->
        {
            cbqtdeparcelas.getSelectionModel().select(0);
            int pos = cbformapagamento.getSelectionModel().getSelectedIndex();
            if(pos == 0)
                cbqtdeparcelas.setDisable(true);
            else
                cbqtdeparcelas.setDisable(false);
        });
        
        cbqtdeparcelas.getSelectionModel().selectedItemProperty().addListener(e->
        { txdiapagamento.requestFocus(); });
    }
    
    private static BigDecimal truncateDecimal(double x, int numberofDecimals)
    {
        if ( x > 0)
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        else
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
    }
    
    public void atualizaTXValorTotal2()
    {
        if(thAtualiza2 == null || thAtualiza2.isInterrupted())
        {
            thAtualiza2 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while(atualiza_total2)
                    {
                        Platform.runLater(()->{
                            double v1, valor = 0.0;
                            String v = corrigeMaskaraDinheiro(txvalortotal.getText());
                            try{ v1 = Double.parseDouble(v); }catch(Exception ex){ v1 = 0.0; }
                            
                            for(int i = 0; i < tabelageraparcelas.getItems().size(); i++)
                            {
                                valor += ctrl_comprar_produtos.getValorParcelaAdicionadaPosLista(i);
                            }
                            
                            valor = Math.round(valor);
                            v1 -= valor;
                            
                            if(v1 < 0)
                                v1 = 0.0;
                            
                            txparcelavalortotal.setText("" + v1 + "0");
                        });

                        try{ Thread.sleep(250); }catch(Exception ex){ }
                    }
                }
            });
            
            thAtualiza2.start();
        }
    }
    
    public void carregaComboBoxParcelaTipoGeracao()
    {
        ctrl_comprar_produtos.carregarComboBoxParcelaTipoGeracao(cbtipogeracaoparcelas);
    }
    
    public void carregaComboBoxParcelaQtdeParcelas()
    {
        ctrl_comprar_produtos.carregarComboBoxParcelaQtdeParcelas(cbqtdeparcelas);
    }
    
    public void carregaComboBoxParcelaFPagamento()
    {
        ctrl_comprar_produtos.carregarComboBoxParcelaFPagamento(cbformapagamento);
    }
    
    /* --------------------------------------------------------------------------------------------------------------- */
    // [4] TELA CONSULTAR COMPRAS:
    /* --------------------------------------------------------------------------------------------------------------- */
    @FXML
    private void clkConsultarCompra(ActionEvent event)
    {
        tabelaconsultacompra.getItems().clear();
        String info = txinformacaoconsultcompra.getText();
        int pos = Integer.parseInt(((DisplayComboBox)(cbfiltroconsultcompra.getItems().get(cbfiltroconsultcompra.
                getSelectionModel().getSelectedIndex()))).getValueMember());
        
        if(!info.isEmpty() || pos == 3)
        {
            ctrl_comprar_produtos.carregarTableViewConsultarCompra(tabelaconsultacompra, info, pos);
            
            if(tabelaconsultacompra.getItems().isEmpty())
            {
                bselecionarcompra.setDisable(true);
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Nenhum dado correspondente à pesquisa foi encontrado!");
                dialogoInfo.showAndWait();
            }
            else
                bselecionarcompra.setDisable(false);
        }
        else
        {
            txinformacaoconsultprod.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma informação para a pesquisa foi inserida!");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void clkSelecionarCompra(ActionEvent event)
    {
        int pos = tabelaconsultacompra.getSelectionModel().getSelectedIndex();
        
        if(pos >= 0 && pos < tabelaconsultacompra.getItems().size())
        {
            clearComprarProduto();
            clearGerarParcelas();
            
            ctrl_comprar_produtos.selecionarCompra(pos, tabelaprod, tabelageraparcelas);
            
            gerou_parcelas = true;
            txcodigo.setText("" + ctrl_comprar_produtos.getCodigoCompraAtual());
            txvalortotal.setText("" + ctrl_comprar_produtos.getValorTotalCompraAtual() + "0");
            dtoperacao.setValue(ctrl_comprar_produtos.getDataCompraAtual());
            
            int codigo_fornec, cod = ctrl_comprar_produtos.getCodigoFornecedorCompraAtual();
            boolean achou = false;
            for(int i = 0; i < cbfornecedor.getItems().size() && !achou; i++)
            {
                codigo_fornec = Integer.parseInt(((DisplayComboBox)(cbfornecedor.getItems().get(i))).getValueMember());
                if(codigo_fornec == cod)
                {
                    achou = true;
                    cbfornecedor.getSelectionModel().select(i);
                }
            }
            
            clearConsultaCompra();
            tabConsultaCompra.setDisable(true);
            tabComprarProdutos.setDisable(false);
            tabprincipal.getSelectionModel().select(0);
        }
        else
        {
            bconsultarproduto.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Não há compras Pesquisadas!");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void clkCancelarConsultaCompra(ActionEvent event)
    { rewindTela(); }
    
    public void clearConsultaCompra()
    {
        txinformacaoconsultcompra.clear();
        cbfiltroconsultcompra.getSelectionModel().select(0);
        tabelaconsultacompra.getItems().clear();
        bselecionarcompra.setDisable(true);
    }
    
    public void carregaComboBoxFiltroConsultaCompra()
    {
        ctrl_comprar_produtos.carregarComboBoxFiltroConsultaCompra(cbfiltroconsultcompra);
        cbfiltroconsultcompra.getSelectionModel().select(0);
    }
    
    public void center(Bounds viewPortBounds, Parent centeredNode)
    {
        double width = viewPortBounds.getWidth();
        double height = viewPortBounds.getHeight();
        double pwidth = ((Region)centeredNode).getPrefWidth();
        double pheight = ((Region)centeredNode).getPrefHeight();
        
        if (width > pwidth)
        {  centeredNode.setTranslateX((width - pwidth) / 2); } 
        else
        {   centeredNode.setTranslateX(0); }
        if (height > pheight)
        {  centeredNode.setTranslateY((height - pheight) / 2); }
        else
        {   centeredNode.setTranslateY(0); }
        
        /*
        centeredNode.setTranslateX(0);
        centeredNode.setTranslateY(0);
        */
    }
    
    public void rewindTela()
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaFundo.fxml"));
            TelaMenuController.painel.setContent(tela);  // é um scrollpane
            // centralizando a tela no scroolpane
            this.center(TelaMenuController.painel.getViewportBounds(), tela);
            TelaMenuController.painel.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
                this.center(newValue, tela); });
        }
        catch(Exception e){ }
    }
}