package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import prjmercadocaiçara.db.controladoras.CtrlCaixa;
import prjmercadocaiçara.db.controladoras.CtrlCliente;
import prjmercadocaiçara.db.controladoras.CtrlFuncionario;
import prjmercadocaiçara.db.controladoras.CtrlProduto;
import prjmercadocaiçara.db.controladoras.CtrlVenderProduto;
import prjmercadocaiçara.db.modelos.Caixa;
import prjmercadocaiçara.db.modelos.Produto;
import prjmercadocaiçara.db.modelos.Venda;

public class TelaVenderProdutosController implements Initializable
{
    @FXML
    private TabPane tabprincipal;
    @FXML
    private VBox pndados;
    @FXML
    private JFXButton bnovavenda;
    @FXML
    private JFXButton bcancelarvenda;
    @FXML
    private JFXButton bconsultarprodutovenda;
    @FXML
    private JFXTextField txcodigovenda;
    @FXML
    private JFXComboBox<String> cbfuncionariovenda;
    @FXML
    private JFXDatePicker dtpdatavenda;
    @FXML
    private JFXTextField txvalortotalvenda;
    @FXML
    private JFXComboBox<String> cbformaspagamentovendas;
    @FXML
    private JFXButton bestornovenda;
    @FXML
    private Label tituloprincipal;
    @FXML
    private JFXTextField txnumparcelasvendas;
    @FXML
    private JFXComboBox<String> cbclientesvenda;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private JFXTextField txcodigoprodutovenda;
    @FXML
    private JFXTextField txdescricaoprodutovenda;
    @FXML
    private JFXTextField txvalorunidadeprodutovenda;
    @FXML
    private JFXTextField txestoquevenda;
    @FXML
    private JFXTextField txqtdprodutovenda;
    @FXML
    private JFXButton baddprodutovenda;
    @FXML
    private JFXButton bdelprodutovenda;
    @FXML
    private TableView<Object> tabela_vender_produtos;
    @FXML
    private TableColumn<?, ?> colcodprodvenda;
    @FXML
    private TableColumn<?, ?> colcoddescricaovenda;
    @FXML
    private TableColumn<?, ?> colvalorunidadevenda;
    @FXML
    private JFXButton bconfirmarvenda;
    @FXML
    private JFXButton bsairvenda;
    @FXML
    private VBox pndados2;
    @FXML
    private Label tituloprincipal2;
    @FXML
    private VBox pnpesquisa2;
    @FXML
    private JFXTextField txinformacaoproduto;
    @FXML
    private JFXComboBox<String> cbfiltroproduto;
    @FXML
    private JFXButton bconsultarproduto;
    @FXML
    private JFXButton bselecionarproduto;
    @FXML
    private JFXButton bcancelarproduto;
    @FXML
    private TableColumn<?, ?> colcodigoproduto;
    @FXML
    private TableColumn<?, ?> coldescricaoproduto;
    @FXML
    private TableColumn<?, ?> colvalorunidadeproduto;
    @FXML
    private TableColumn<?, ?> colquantidadeestoque;
    @FXML
    private TableColumn<?, ?> colcodigotipoproduto;
    @FXML
    private Tab tabconsultarvenda;
    @FXML
    private VBox pndados21;
    @FXML
    private Label tituloprincipal21;
    @FXML
    private VBox pnpesquisa21;
    @FXML
    private JFXTextField txinformacaovenda;
    @FXML
    private JFXComboBox<String> cbfiltrovenda;
    @FXML
    private JFXButton bconsultarvenda;
    @FXML
    private JFXButton bselecionarvenda;
    @FXML
    private TableView<Object> tabelaconsultarvenda;
    @FXML
    private TableColumn<?, ?> colcodvendacosultavenda;
    @FXML
    private TableColumn<?, ?> colcodclienteconsultavenda;
    @FXML
    private TableColumn<?, ?> colcodigofuncionarioconsultavenda;
    @FXML
    private TableColumn<?, ?> colformadepagamentoconsultarvenda;
    @FXML
    private TableColumn<?, ?> colvalortotalconsultarvenda;
    @FXML
    private TableView<Object> tabelaparcelasvenderprodutos;
    @FXML
    private TableColumn<?, ?> colcodparcelavender;
    @FXML
    private TableColumn<?, ?> colparceladescricaovender;
    @FXML
    private TableColumn<?, ?> colvalorparcelavender;
    @FXML
    private TableColumn<?, ?> coldatavenctovender;
    @FXML
    private TableColumn<?, ?> colcodfuncionariovender;
    @FXML
    private JFXButton bgerarparcelasvenda;
    @FXML
    private JFXButton bselecionarvendavenda;
    @FXML
    private TableColumn<?, ?> colqtdvenda;
    @FXML
    private TableColumn<?, ?> coldatacaixavender;
    @FXML
    private JFXButton balterartelavenda;
    @FXML
    private TableView<Object> tabelaconsultaprodutovender;
    @FXML
    private Tab tabvenderprodutos;
    @FXML
    private Tab tabconsultarproduto;
    @FXML
    private JFXButton bcancelarvendavender;
    
    // Controladoras Utilizadas
    private CtrlVenderProduto dalvender = null;
    private CtrlFuncionario dalfuncionario = CtrlFuncionario.instanciarCtrlFun();
    private CtrlCliente dalcliente = CtrlCliente.obterInstancia();
    private CtrlProduto dalproduto = null;
    private CtrlCaixa dalcaixa = CtrlCaixa.instanciarCtrlCaixa();
    
    private String auxidtipo;
    private boolean flag;
    private int controle;
    private List listqtd;
    private List flagsobserver;
    private List<LocalDate> auxDatasParecelas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dalvender = CtrlVenderProduto.instanciar();
        
        dalproduto = CtrlProduto.instanciar();
        auxDatasParecelas = new ArrayList();
        flagsobserver = new ArrayList();
        
        controle = 0;
        limpaTodosCampos();
        desabilitaTodosCampos2();
        
        listqtd = new ArrayList();
        
        balterartelavenda.setDisable(true);
        bestornovenda.setDisable(true);
        bcancelarvendavender.setDisable(true);
        bnovavenda.setTooltip(geraMsgB("Nova Venda"));
        bestornovenda.setTooltip(geraMsgB("Estornar"));
        bcancelarvenda.setTooltip(geraMsgB("Cancelar"));
        bselecionarvendavenda.setTooltip(geraMsgB("Selecionar Venda"));
        bgerarparcelasvenda.setTooltip(geraMsgB("Gerar Parcelas"));
        bconsultarprodutovenda.setTooltip(geraMsgB("Consultar Produto"));
        baddprodutovenda.setTooltip(geraMsgB("Adicionar Produto"));
        bdelprodutovenda.setTooltip(geraMsgB("Remover Produto"));
        bconfirmarvenda.setTooltip(geraMsgB("Confirmar Venda"));
        bsairvenda.setTooltip(geraMsgB("Retornar ao Inicio"));
        bconsultarproduto.setTooltip(geraMsgB("Consultar Produto"));
        bselecionarproduto.setTooltip(geraMsgB("Selecionar Produto"));
        bcancelarproduto.setTooltip(geraMsgB("Cancelar"));
        bconsultarvenda.setTooltip(geraMsgB("Consultar Venda"));
        bselecionarvenda.setTooltip(geraMsgB("Selecionar Venda"));
        bcancelarvenda.setTooltip(geraMsgB("Cancelar"));
        
        // Primeira Tabela Alterada
        // ==============================================================================
        colcodvendacosultavenda.setCellValueFactory(new MapValueFactory("id"));
        colcodclienteconsultavenda.setCellValueFactory(new MapValueFactory("cliente"));
        colcodigofuncionarioconsultavenda.setCellValueFactory(new MapValueFactory("funcionario"));
        colformadepagamentoconsultarvenda.setCellValueFactory(new MapValueFactory("formapagamento"));
        colvalortotalconsultarvenda.setCellValueFactory(new MapValueFactory("valortotal"));
      
        /* Relacionando as colunas da tabela de busca de produtos */
        colcodigoproduto.setCellValueFactory(new MapValueFactory("id"));
        coldescricaoproduto.setCellValueFactory(new MapValueFactory("descricao"));
        colvalorunidadeproduto.setCellValueFactory(new MapValueFactory("vl_unit"));
        colquantidadeestoque.setCellValueFactory(new MapValueFactory("qtd"));
        colcodigotipoproduto.setCellValueFactory(new MapValueFactory("idtipo"));
        
        // Atribuição das colunas da tabela de Produtos da Venda
        colcodprodvenda.setCellValueFactory(new MapValueFactory("id"));
        colcoddescricaovenda.setCellValueFactory(new MapValueFactory("descricao"));
        colvalorunidadevenda.setCellValueFactory(new MapValueFactory("vl_unit"));
        colqtdvenda.setCellValueFactory(new MapValueFactory("qtd"));
        
        // Atribuição das colunas da tabela de Parcelas da Venda
        colcodparcelavender.setCellValueFactory(new MapValueFactory("id"));
        colparceladescricaovender.setCellValueFactory(new MapValueFactory("descricao"));
        colvalorparcelavender.setCellValueFactory(new MapValueFactory("valorparcela"));
        coldatavenctovender.setCellValueFactory(new MapValueFactory("datavencimento"));
        coldatacaixavender.setCellValueFactory(new MapValueFactory("datacaixa"));
        colcodfuncionariovender.setCellValueFactory(new MapValueFactory("idfuncionario"));
        
        carregaComboboxFiltrosProduto();
        carregaComboboxFiltrosVenda();
        carregaComboboxFormasDePagamento();
        carregaComboboxFuncionario();
        
        carregaComboboxClientes();
        atualizaData();
    }
    
    public void atualizaData()
    {
        new Thread() {
            @Override
            public void run() {
                while(true)
                {
                    dtpdatavenda.setValue(LocalDate.now());
                    try{ Thread.sleep(100000000); }catch(Exception ex){}
                }
            }
        }.start();
    }
    
    public void carregaComboboxFiltrosProduto()
    {
        cbfiltroproduto.getItems().add("Código");
        cbfiltroproduto.getItems().add("Descrição");
        cbfiltroproduto.getSelectionModel().select(0);
    }
    
    public void carregaComboboxFiltrosVenda()
    {
        cbfiltrovenda.getItems().add("Código");
        cbfiltrovenda.getItems().add("Valor");
        cbfiltrovenda.getSelectionModel().select(0);
    }
    
    public void carregaComboboxFormasDePagamento()
    {
        cbformaspagamentovendas.getItems().add("À Vista");
        cbformaspagamentovendas.getItems().add("Cartão de Crédito ou Debito");
        cbformaspagamentovendas.getItems().add("Cheque");
        cbformaspagamentovendas.getItems().add("Parcelado");
        cbformaspagamentovendas.getSelectionModel().select(0);
        txnumparcelasvendas.setText("1");
        txnumparcelasvendas.setDisable(true);
    }
    
    public void carregaComboboxFuncionario()
    {
        dalfuncionario.carregarComboBoxFuncionario(cbfuncionariovenda);
        cbfuncionariovenda.getSelectionModel().select(0);
    }
    
    public void carregaComboboxClientes()
    {
        dalcliente.carregaComboboxCliente(cbclientesvenda);
        cbclientesvenda.getSelectionModel().select(0);
    }
    
    public static Tooltip geraMsgB(String msg)
    {
        Tooltip tt = new Tooltip();
        tt.setText(msg);
        tt.setStyle("-fx-font: normal bold 10 Langdon; "
            + "-fx-text-fill: white;"+"-fx-background-color:   #607D8B;");
        return tt;
    }
    
    boolean isDigit(String s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            char ch = s.charAt(i);
            if (ch < 48 || ch > 57)
                 return false;
        }
        return true;
    }

    public boolean isDecimal(String str)
    {
        try
        { Double.parseDouble(str); return true; }
        catch(Exception ex)
        { return false; }
    }
    
    public void limpaTabelaConsultaVenda()
    { tabelaconsultarvenda.getItems().clear(); } 
    
    // FUNÇÕES DE BUSCA ALTERADAS PARA O PADRÃO FAÇADE PORÉM NÃO FORAM TESTADAS
    // ========================================================================
    @FXML
    private void evtConsultarVenda(ActionEvent event) {
        if(txinformacaovenda.getText().isEmpty())
        {
            dalvender.getVendaCarregaTabelaConsultarVenda("", tabelaconsultarvenda); 
            bselecionarvenda.setDisable(false);
        }
        else
            if(cbfiltrovenda.getSelectionModel().getSelectedIndex() == 0) // Busca por código
            {
                if(isDigit(txinformacaovenda.getText()))
                {
                    dalvender.getVendaCarregaTabelaConsultarVenda("ven_cod = "+txinformacaovenda.getText(), tabelaconsultarvenda);
                    bselecionarvenda.setDisable(false);
                }
                else
                {
                    informa("DADOS INVÁLIDOS", "A informação digitada é inválida para o filtro selecionado!");
                    limpaTabelaConsultaVenda();
                }  
            }
           else
                if(cbfiltrovenda.getSelectionModel().getSelectedIndex() == 1) // Busca Por Valor
                {
                    if(isDecimal(txinformacaovenda.getText()))
                    {
                        dalvender.getVendaCarregaTabelaConsultarVenda("ven_valortotal >= "+txinformacaovenda.getText(), tabelaconsultarvenda); 
                        bselecionarvenda.setDisable(false);
                    }
                    else
                    {
                        informa("DADOS INVÁLIDOS", "A informação digitada é inválida para o filtro selecionado!");
                        limpaTabelaConsultaVenda();
                    }
                }
        txinformacaovenda.clear();
    }
    // =================================================================================================
    
    public void buscaClienteCombobox(int id)
    { dalcliente.selecionaClienteComboboxId(id, cbclientesvenda); }
    
    
    // VERIFICAR (É POSSÍVEL QUE PELO PADRÃO ESSA BUSCA NÃO POSSA SER NA TABELA)
    public void buscaFuncionarioCombobox(int id)
    { dalfuncionario.selecionaFuncionarioComboboxId(id, cbclientesvenda); }
    
    public void buscaFormaPagamentoCombobox(String str)
    {
        int i = 0;
        while(i < cbformaspagamentovendas.getItems().size() && !cbformaspagamentovendas.getItems().get(i).toUpperCase().equals(str.toUpperCase()))
            i++;
        cbformaspagamentovendas.getSelectionModel().select(i);
    }
    
    public void desabilitaTodosCampos2()
    {
        // Desabilitando campos após ter selecionado
            txcodigovenda.setDisable(true);
            cbclientesvenda.setDisable(true);
            cbfuncionariovenda.setDisable(true);
            cbformaspagamentovendas.setDisable(true);
            txvalortotalvenda.setDisable(true);
            txnumparcelasvendas.setDisable(true);
            bgerarparcelasvenda.setDisable(true);
            bconsultarprodutovenda.setDisable(true);
            baddprodutovenda.setDisable(true);
            bdelprodutovenda.setDisable(true);
            txqtdprodutovenda.setDisable(true);
            txqtdprodutovenda.setDisable(true);
            txestoquevenda.setDisable(true);
            txvalorunidadeprodutovenda.setDisable(true);
            txdescricaoprodutovenda.setDisable(true);
            txcodigoprodutovenda.setDisable(true);
            bconfirmarvenda.setDisable(true);
            // Desabilitando os Tabs
            tabconsultarproduto.setDisable(true);
            tabconsultarvenda.setDisable(true);
    }

    
    // Alterar o selecionar
    // Retornar apenas os valores dos atributos necessários
    // ==================================================================
    @FXML
    private void evtSelecionarVenda(ActionEvent event) {
        Map<String, String> aux;
        int n = tabelaconsultarvenda.getSelectionModel().getSelectedIndex(); 
        if(n >= 0)
        {
            limpaTodosCampos();
            tabela_vender_produtos.getItems().clear();
            tabelaparcelasvenderprodutos.getItems().clear();
            
            aux = (Map<String, String>) tabelaconsultarvenda.getItems().get(n);
            
            
            txcodigovenda.setText(""+aux.get("id")/*aux.getId()*/);
            buscaClienteCombobox(Integer.parseInt(aux.get("cliente"))/*aux.getCliente()*/);
            buscaFuncionarioCombobox(Integer.parseInt(aux.get("funcionario")));
            
            
            dtpdatavenda.setValue(LocalDate.parse(dalvender.buscarDataDaVendaID(Integer.parseInt(aux.get("id"))).toString()  /*aux.getDatavenda().toString()*/));
            
            
            txvalortotalvenda.setText(""+aux.get("valortotal"));
            
            buscaFormaPagamentoCombobox(aux.get("formapagamento"));
            
            
            txnumparcelasvendas.setText(""+dalvender.buscarQtdParcelasDaVendaID(Integer.parseInt(aux.get("id")))     /* aux.getParcelas().size()*/);
            
            //tabela_vender_produtos.setItems(FXCollections.observableArrayList(aux.getItensvenda()));
            dalvender.getVendaCarregaTabelaVenderProdutosID(Integer.parseInt(aux.get("id")), tabela_vender_produtos);
            
            
            dalvender.getVendaCarregaTabelaParcelasDaVendaID(Integer.parseInt(aux.get("id")), tabelaparcelasvenderprodutos);
            
            //tabelaparcelasvenderprodutos.setItems(FXCollections.observableArrayList(aux.getParcelas()));
            
            desabilitaTodosCampos2();
            // Limpando Campos
            limpaTabelaConsultaVenda();
            txinformacaovenda.clear();
            cbfiltrovenda.getSelectionModel().select(0);
            // Selecionando o tab Principal Vender
            selecionarTab(3);
            // Controle de habilitar botões
            bnovavenda.setDisable(true);
            balterartelavenda.setDisable(false);
            bestornovenda.setDisable(false);
            bcancelarvendavender.setDisable(false);
        } 
        else
            informa("Linha não Selecionada!", "Para efetuar a seleção é necessário selecionar uma das linhas da tabela!");
    }
    
    public void informa(String str1, String str2)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(str1);
        alert.setContentText(str2);
        alert.showAndWait();
    }
    
    public void limpaTabelaConsultaProduto()
    { tabelaconsultaprodutovender.getItems().clear(); }

    @FXML
    private void evtConsultarProduto(ActionEvent event) {
        if(txinformacaoproduto.getText().isEmpty())
        {
            dalproduto.getInstancia().getProdutoCarregaTabelaClasseProduto("", tabelaconsultaprodutovender);
            bselecionarproduto.setDisable(false);
        }
        else
            if(cbfiltroproduto.getSelectionModel().getSelectedIndex() == 0) // Busca por código
            {
                if(isDigit(txinformacaoproduto.getText()))
                {
                    dalproduto.getInstancia().getProdutoCarregaTabelaClasseProduto("prod_cod = "+txinformacaoproduto.getText(), tabelaconsultaprodutovender);
                    bselecionarproduto.setDisable(false);
                }
                else
                {
                    informa("DADOS INVÁLIDOS", "A informação digitada é inválida para o filtro selecionado!");
                    limpaTabelaConsultaProduto();
                }  
            }
            else
                if(cbfiltroproduto.getSelectionModel().getSelectedIndex() == 1) // Busca Descrição
                {
                    if(txinformacaoproduto.getText().length() > 2)
                    {
                       dalproduto.getInstancia().getProdutoCarregaTabelaClasseProduto("prod_descricao like '"+ txinformacaoproduto.getText()+ "%'", tabelaconsultaprodutovender);
                       bselecionarproduto.setDisable(false);
                    }
                    else
                    {
                        informa("DADOS INVÁLIDOS", "A informação digitada é inválida para o filtro selecionado!");
                        limpaTabelaConsultaProduto();
                    }
                }
        txinformacaoproduto.clear();
    }
    
    private int buscarProdutoTabela(int id)
    {
        if(tabela_vender_produtos.getItems().size() > 0)
        {
            Map<String, String> dataRow;
            int i = 0;
            dataRow = (Map<String, String>) tabela_vender_produtos.getItems().get(i);
            while(i < tabela_vender_produtos.getItems().size() && Integer.parseInt(dataRow.get("id")) != id)
            {
                i++;
                if(i < tabela_vender_produtos.getItems().size())
                    dataRow = (Map<String, String>) tabela_vender_produtos.getItems().get(i);
            }
            if(i == tabela_vender_produtos.getItems().size())
                return -1;
            else
                return i;
        }
        else
            return -1;
    }

    @FXML
    private void evtSelecionarProduto(ActionEvent event) {
        int n = tabelaconsultaprodutovender.getSelectionModel().getSelectedIndex(); 
        if(n >= 0)
        {
            Map<String, String> dataRow = (Map<String, String>) tabelaconsultaprodutovender.getItems().get(n);
            Map<String, String> row;
            auxidtipo = ""+dataRow.get("idtipo"); 
            txcodigoprodutovenda.setText(""+dataRow.get("id"));
            txdescricaoprodutovenda.setText(""+dataRow.get("descricao"));
            txvalorunidadeprodutovenda.setText(""+dataRow.get("vl_unit"));
            
            if(controle == 1)
            {
                listqtd.clear();
                for (int k = 0; k < tabela_vender_produtos.getItems().size(); k++)
                {
                    row = (Map<String, String>) tabela_vender_produtos.getItems().get(k);
                    listqtd.add(Integer.parseInt(row.get("qtd")));
                }
                controle = 0;
            }
            
            if(flag)
            {
                int qtd2=0;
                int pos = buscarProdutoTabela(Integer.parseInt(dataRow.get("id")));
                if(pos != -1)
                {
                    row = (Map<String, String>) tabela_vender_produtos.getItems().get(pos);
                    txestoquevenda.setText(""+((Integer.parseInt(dataRow.get("qtd")))-Integer.parseInt(row.get("qtd"))));
                }
                else
                    txestoquevenda.setText(""+dataRow.get("qtd"));
            }
            else
            {
                int qtd2=0;
                int pos = buscarProdutoTabela(Integer.parseInt(dataRow.get("id")));
                if(pos != -1)
                {
                    if(pos >= 0 && pos < listqtd.size())
                    {
                        row = (Map<String, String>) tabela_vender_produtos.getItems().get(pos);
                        txestoquevenda.setText(""+((Integer.parseInt(dataRow.get("qtd")))-Integer.parseInt(row.get("qtd"))+(int)listqtd.get(pos)));
                    }
                    else
                    {
                        row = (Map<String, String>) tabela_vender_produtos.getItems().get(pos);
                        txestoquevenda.setText(""+((Integer.parseInt(dataRow.get("qtd")))-Integer.parseInt(row.get("qtd")))); 
                    } 
                }
                else
                    txestoquevenda.setText(""+dataRow.get("qtd"));
            }
            // Limpando Campos
            limpaTabelaConsultaProduto();
            txinformacaoproduto.clear();
            cbfiltroproduto.getSelectionModel().select(0);
            // Selecionando o Tab Principal
            selecionarTab(5);
        } 
        else
            informa("Linha não Selecionada!", "Para efetuar a seleção é necessário selecionar uma das linhas da tabela!");
    }
    
    public void selecionarTab(int flag)
    {
        switch(flag)
        {
            case 1: // Seleciona Tab Consultar Venda
                tabconsultarproduto.setDisable(true);
                tabvenderprodutos.setDisable(true);
                tabconsultarvenda.setDisable(false);
                tabprincipal.getSelectionModel().select(2);
            break;    
            
            case 2: // Cancelar consulta (Retorna da tela consultar venda para a tela principal de venda)
                tabconsultarproduto.setDisable(true);
                tabvenderprodutos.setDisable(false);
                tabconsultarvenda.setDisable(true);
                tabprincipal.getSelectionModel().select(0);
            break;
            
            case 3: // Selecionando o Tab principal (Ponto de partida Consultar Venda - Botão selecionar venda)
                tabconsultarproduto.setDisable(true);
                tabvenderprodutos.setDisable(false);
                tabconsultarvenda.setDisable(true);
                tabprincipal.getSelectionModel().select(0);
            break;
            
            case 4: // Selecionando o Tab Consulta de Produtos 
                tabconsultarproduto.setDisable(false);
                tabvenderprodutos.setDisable(true);
                tabconsultarvenda.setDisable(true);
                tabprincipal.getSelectionModel().select(1);
            break;
            
            case 5: // Selecionando o Tab principal (Ponto de partida Consultar Produtos - Botão selecionar Produtos)
                tabconsultarproduto.setDisable(true);
                tabvenderprodutos.setDisable(false);
                tabconsultarvenda.setDisable(true);
                tabprincipal.getSelectionModel().select(0);
            break;
            
            case 6: // // Cancelar consulta (Retorna da tela consultar Produtos para a tela principal de venda)
                tabconsultarproduto.setDisable(true);
                tabvenderprodutos.setDisable(false);
                tabconsultarvenda.setDisable(true);
                tabprincipal.getSelectionModel().select(0);
            break;
        }
    }
    
    @FXML
    private void evtBuscarVenda(ActionEvent event) {
        limpaTodosCampos();
        bselecionarvenda.setDisable(true);
        selecionarTab(1);
    }

    @FXML
    private void evtCancelarConsultaVenda(ActionEvent event) {
        selecionarTab(2);
        txnumparcelasvendas.setText("1");
    }

    @FXML
    private void evtBuscarProduto(ActionEvent event) {
        txcodigoprodutovenda.clear();
        txqtdprodutovenda.clear();
        txvalorunidadeprodutovenda.clear();
        txdescricaoprodutovenda.clear();
        txestoquevenda.clear();
        bselecionarproduto.setDisable(true);
        selecionarTab(4);
    }

    @FXML
    private void evtCancelarConsultaProduto(ActionEvent event) {
        selecionarTab(6);
        txnumparcelasvendas.setText("1");
    }

    public boolean validaCampos()
    {
        if(!txcodigoprodutovenda.getText().isEmpty() && isDigit(txcodigoprodutovenda.getText()))
            if(!txdescricaoprodutovenda.getText().isEmpty())
                if(!txvalorunidadeprodutovenda.getText().isEmpty() && isDecimal(txvalorunidadeprodutovenda.getText()))
                    if(!txestoquevenda.getText().isEmpty() && isDigit(txestoquevenda.getText()))
                        if(!txqtdprodutovenda.getText().isEmpty() && isDigit(txqtdprodutovenda.getText()))
                            if(Integer.parseInt(txestoquevenda.getText()) >= Integer.parseInt(txqtdprodutovenda.getText()))
                                return true;
                            else
                                informa("ERRO", "A quantidade do produto em estoque é insuficiente para o pedido requerido!");
                        else
                            informa("ERRO", "O campo 'Quantidade' é inválido!");
                    else
                       informa("ERRO", "O campo 'Qtde. Estoque' é inválido!");
                else
                   informa("ERRO", "O campo 'Valor Unidade' é inválido!");
            else
                informa("ERRO", "O campo 'Descrição' é inválido!");
        else
            informa("ERRO", "O campo 'Código Produto' é inválido!");
        return false;
    }
    
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    @FXML
    private void evtAdicionarProdutoLista(ActionEvent event) {
        boolean resp = false;
        if(validaCampos())
        {
            int id = Integer.parseInt(txcodigoprodutovenda.getText());
            int qtd = Integer.parseInt(txqtdprodutovenda.getText());
            double vl = Double.parseDouble(txvalorunidadeprodutovenda.getText());
            double vlatual=0;
            // Atualizando o valor do campo Valor Total
            if(txvalortotalvenda.getText().isEmpty())
                txvalortotalvenda.setText(""+(qtd*vl));
            else
            {
                vlatual = Double.parseDouble(txvalortotalvenda.getText());
                vlatual += qtd*vl;
                txvalortotalvenda.setText(""+vlatual);
            }

            int pos = buscarProdutoTabela(id);

            if(pos != -1) // Já existe
            {
               Map<String, String> p = (Map<String, String>) tabela_vender_produtos.getItems().get(pos);
               int qt = Integer.parseInt(p.get("qtd"));
               qt+=qtd;
               p.put("qtd", ""+qt);
               tabela_vender_produtos.getItems().set(pos, p);
               resp = true;
            }
            else // Não existe
            {
                String result="";
                result = JOptionPane.showInputDialog(null, "Deseja Receber informações sobre esse produto [S/N]?");
                
                if(result.equals("S"))
                {
                    flagsobserver.add(1);
                    resp = true;
                }
                else
                    if(result.equals("N"))
                    {
                        flagsobserver.add(0);
                        resp = true;
                    }
                    
                if(resp)
                {
                    Map<String, String> dataRow = new HashMap<>();
                    dataRow.put("id", ""+id);
                    dataRow.put("descricao", txdescricaoprodutovenda.getText());
                    dataRow.put("vl_unit", ""+vl);
                    dataRow.put("qtd", ""+qtd);
                    dataRow.put("idtipo", ""+auxidtipo);
                    tabela_vender_produtos.getItems().add(dataRow);//new Produto(id, qtd, txdescricaoprodutovenda.getText(), vl, /*new TipoProduto()*/ /*auxprod.getTipo()*/));
                }
                else
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Mercado Caiçara");
                    alert.setHeaderText("Information");
                    alert.setContentText("Caracter Inválido!");
                    alert.show();
                }
            }
            
            if(resp)
            {
                txcodigoprodutovenda.clear();
                txqtdprodutovenda.clear();
                txvalorunidadeprodutovenda.clear();
                txdescricaoprodutovenda.clear();
                txestoquevenda.clear();
            }
        }
    }

    @FXML
    private void evtRemoverProdutoLista(ActionEvent event) {
        int n = tabela_vender_produtos.getSelectionModel().getSelectedIndex();
        if(n >= 0)
        {
            flagsobserver.remove(n);
            List<Map<String, String>> list = new ArrayList();
            for (int i = 0; i < tabela_vender_produtos.getItems().size(); i++) 
                if(i != n)
                    list.add((Map<String, String>) tabela_vender_produtos.getItems().get(i));
            
            
            Map<String, String> aux = (Map<String, String>) tabela_vender_produtos.getItems().get(n);
            
            double vlatual = Double.parseDouble(txvalortotalvenda.getText());
            
            vlatual -= ((Integer.parseInt(aux.get("qtd")))*(Double.parseDouble(aux.get("vl_unit"))));
            txvalortotalvenda.setText(""+vlatual);
            tabela_vender_produtos.getItems().clear();
            tabela_vender_produtos.getItems().addAll(list);
        }
        else
            informa("INFORMAÇÃO", "Selecione um produto da tabela!");
    }

    @FXML
    private void evtComboboxFormaPagamento(ActionEvent event) {
        int selecionado = cbformaspagamentovendas.getSelectionModel().getSelectedIndex();
        if(selecionado == 0 || selecionado == 1 || selecionado == 2)
        {
            txnumparcelasvendas.setText("1");
            txnumparcelasvendas.setDisable(true);
        }
        else
        {
            txnumparcelasvendas.clear();
            txnumparcelasvendas.setDisable(false);
        }
    }
    
    double arredondar(double valor, int casas, int ceilOrFloor) {
        double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        if (ceilOrFloor == 0) {
            arredondado = Math.ceil(arredondado);           
        } else {
            arredondado = Math.floor(arredondado);
        }
        arredondado /= (Math.pow(10, casas));
        return arredondado;
    }

    /* Analisar Este ponto */
    
    
    @FXML
    private void evtGerarParcelasvendas(ActionEvent event) {
        
        double valortotal=0, valorparcela=0;
        int numparcelas=0;
        
        tabelaparcelasvenderprodutos.getItems().clear();
        try { valortotal = Double.parseDouble(txvalortotalvenda.getText()); } catch(Exception e){ valortotal = 0; }
        
        if(!bgerarparcelasvenda.getText().isEmpty() && valortotal > 0)
        {
           if(isDigit(txnumparcelasvendas.getText()) && !txnumparcelasvendas.getText().isEmpty())
           {
               numparcelas = Integer.parseInt(txnumparcelasvendas.getText());
               valorparcela = valortotal/numparcelas;
               
               valorparcela = arredondar(valorparcela, 2, 1);
               
               //Funcionario f = cbfuncionariovenda.getItems().get(cbfuncionariovenda.getSelectionModel().getSelectedIndex());
               
               CtrlFuncionario auxdal = CtrlFuncionario.instanciarCtrlFun();
               auxdal.selecionaFuncionarioListaNome(cbfuncionariovenda.getItems().get(cbfuncionariovenda.getSelectionModel().getSelectedIndex()));
               
               
               LocalDate dataatual = dtpdatavenda.getValue();
               List<Caixa> caixa;// = new ArrayList();
               
                caixa = dalcaixa.buscaCaixaFuncionarioEData(auxdal.getCodigoFunAtual(), dtpdatavenda.getValue());

                
                
                if(cbformaspagamentovendas.getSelectionModel().getSelectedIndex() == 3)
                {
                    dataatual = dataatual.plusMonths(1);
                    if(caixa.size() > 0)
                    {
                        LocalDate cai_data = caixa.get(0).getData();
                        caixa.clear();
                        
                        // Obtendo as datas relacionadas as parcelas no List
                        auxDatasParecelas.clear();
                        auxDatasParecelas.add(dataatual);
                        auxDatasParecelas.add(cai_data);
                        
                        
                        
                        // Nesse Ponto Gera percelas
                        int par_cod = 1;
                        for (int i = 0; i < numparcelas-1; i++) {
                            Map<String, String> dataRow = new HashMap<>();
                            dataRow.put("id", ""+par_cod++);
                            dataRow.put("descricao", "Compra de Produtos");
                            dataRow.put("valorparcela", ""+valorparcela);
                            dataRow.put("datavencimento", ""+(asDate(dataatual).toString()));
                            dataRow.put("datacaixa", ""+(asDate(cai_data).toString()));
                            dataRow.put("idfuncionario", ""+auxdal.getCodigoFunAtual());  
                            
                            // Campos adicionados a mais
                            // =======================================================
                            //dataRow.put("valorpago", ""+0.0);
                            //dataRow.put("parcjuros", ""+0.0);
                            // =======================================================
                            tabelaparcelasvenderprodutos.getItems().add(dataRow/*new ParcelasV(par_cod++, auxdal.getCodigoFunAtual(), 0.0, valorparcela, 0.0, null, asDate(dataatual), asDate(cai_data), "Compra de Produtos")*/);
                            dataatual = dataatual.plusMonths(1);
                        }

                        double diferenca = valortotal - (valorparcela*numparcelas);
                        // Nesse Ponto Gera percelas (Calcula diferenças de valores)
                        if(diferenca > 0)
                        {
                            Map<String, String> dataRow = new HashMap<>();
                            dataRow.put("id", ""+par_cod++);
                            dataRow.put("descricao", "Compra de Produtos");
                            dataRow.put("valorparcela", ""+arredondar(valorparcela+diferenca, 2, 1));
                            dataRow.put("datavencimento", ""+(asDate(dataatual).toString()));
                            dataRow.put("datacaixa", ""+(asDate(cai_data).toString()));
                            dataRow.put("idfuncionario", ""+auxdal.getCodigoFunAtual());
                            
                            tabelaparcelasvenderprodutos.getItems().add(dataRow/*new ParcelasV(par_cod++, auxdal.getCodigoFunAtual(), 0.0, arredondar(valorparcela+diferenca, 2, 1), 0.0, null, asDate(dataatual), asDate(cai_data), "Compra de Produtos")*/);
                        }
                        else
                        {
                            Map<String, String> dataRow = new HashMap<>();
                            dataRow.put("id", ""+par_cod++);
                            dataRow.put("descricao", "Compra de Produtos");
                            dataRow.put("valorparcela", ""+valorparcela);
                            dataRow.put("datavencimento", ""+(asDate(dataatual).toString()));
                            dataRow.put("datacaixa", ""+(asDate(cai_data).toString()));
                            dataRow.put("idfuncionario", ""+auxdal.getCodigoFunAtual());
                            tabelaparcelasvenderprodutos.getItems().add(dataRow/*new ParcelasV(par_cod++, auxdal.getCodigoFunAtual(), 0.0, valorparcela, 0.0, null, asDate(dataatual), asDate(cai_data), "Compra de Produtos")*/);
                        }
                            
                    }
                    else
                        informa("ERRO", "Não foi possível gerar as parcelas, verifique se o funcionário selecionado realizou a abertura do caixa na presente data!");
                }
                else
                {
                    if(caixa.size() > 0)
                    {
                        LocalDate cai_data = caixa.get(0).getData();
                        caixa.clear();
                        
                        auxDatasParecelas.clear();
                        auxDatasParecelas.add(dataatual);
                        auxDatasParecelas.add(cai_data);
                        
                        // Nesse Ponto Gera percelas
                        int par_cod = 1;
                        for (int i = 1; i <= numparcelas; i++) {
                            
                            Map<String, String> dataRow = new HashMap<>();
                            dataRow.put("id", ""+par_cod++);
                            dataRow.put("descricao", "Compra de Produtos");
                            dataRow.put("valorparcela", ""+valorparcela);
                            dataRow.put("datavencimento", ""+(asDate(dataatual).toString()));
                            dataRow.put("datacaixa", ""+(asDate(cai_data).toString()));
                            dataRow.put("idfuncionario", ""+auxdal.getCodigoFunAtual()); 
                            
                            tabelaparcelasvenderprodutos.getItems().add(dataRow/*new ParcelasV(par_cod++, auxdal.getCodigoFunAtual(), 0.0, valorparcela, 0.0, null, asDate(dataatual), asDate(cai_data), "Compra de Produtos")*/);
                            dataatual = dataatual.plusMonths(1);
                        }
                    }
                    else
                        informa("ERRO", "Não foi possível gerar as parcelas, verifique se o funcionário selecionado realizou a abertura do caixa na presente data!"); 
                }
           }
           else
               informa("ERRO", "O número de parcelas digitado é inválido!");
        }
        else
            informa("ERRO", "Não foi possível gerar as parcelas, verifique os dados informados!");
    }
    
    public void limpaTodosCampos()
    {
        txcodigoprodutovenda.clear();
        txcodigovenda.clear();
        txdescricaoprodutovenda.clear();
        txestoquevenda.clear();
        txinformacaoproduto.clear();
        txinformacaovenda.clear();
        txnumparcelasvendas.clear();
        txqtdprodutovenda.clear();
        txvalortotalvenda.clear();
        txvalorunidadeprodutovenda.clear();
        tabela_vender_produtos.getItems().clear();
        flagsobserver = new ArrayList();
        tabelaparcelasvenderprodutos.getItems().clear();
        cbclientesvenda.getSelectionModel().select(0);
        cbformaspagamentovendas.getSelectionModel().select(0);
        cbfuncionariovenda.getSelectionModel().select(0);
    }

    @FXML
    private void evtCancelarVenderProdutos(ActionEvent event) {
        limpaTodosCampos();
        desabilitaTodosCampos2();
        bselecionarvendavenda.setDisable(false);
        bnovavenda.setDisable(false);
        balterartelavenda.setDisable(true);
        bestornovenda.setDisable(true);
        bcancelarvendavender.setDisable(true);
        txnumparcelasvendas.setText("1");
    }

    @FXML
    private void evtRetornarInicio(ActionEvent event) {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaFundo.fxml"));
            TelaMenuController.getPanel().setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(TelaMenuController.getPanel().getViewportBounds(), tela);
            TelaMenuController.getPanel().viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
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
    }

    @FXML
    private void evtCadastrarNovaVenda(ActionEvent event) {
        bselecionarvendavenda.setDisable(true);
        cbclientesvenda.setDisable(false);
        cbformaspagamentovendas.setDisable(false);
        cbfuncionariovenda.setDisable(false);
        txvalortotalvenda.setDisable(false);
        txnumparcelasvendas.setDisable(true);
        bgerarparcelasvenda.setDisable(false);
        txcodigoprodutovenda.setDisable(false);
        txdescricaoprodutovenda.setDisable(false);
        txvalorunidadeprodutovenda.setDisable(false);
        txestoquevenda.setDisable(false);
        txqtdprodutovenda.setDisable(false);
        bconsultarprodutovenda.setDisable(false);
        baddprodutovenda.setDisable(false);
        bdelprodutovenda.setDisable(false);
        bconfirmarvenda.setDisable(false);
        bcancelarvendavender.setDisable(false);
        flag = true; // Habilitado para gravar uma nova venda
    }

    public void habilitaCamposAlterar()
    {
        cbclientesvenda.setDisable(false);
        cbfuncionariovenda.setDisable(false);
        cbformaspagamentovendas.setDisable(false);
        txvalortotalvenda.setDisable(false);
        txnumparcelasvendas.setDisable(false);
        bgerarparcelasvenda.setDisable(false);
        txcodigoprodutovenda.setDisable(false);
        txvalorunidadeprodutovenda.setDisable(false);
        txestoquevenda.setDisable(false);
        txqtdprodutovenda.setDisable(false);
        bconsultarprodutovenda.setDisable(false);
        baddprodutovenda.setDisable(false);
        bdelprodutovenda.setDisable(false);
        bconfirmarvenda.setDisable(false);
        txdescricaoprodutovenda.setDisable(false);
    }
    
    @FXML
    private void evtAlterarVendaProduto(ActionEvent event) {
        habilitaCamposAlterar();
        //txnumparcelasvendas.setDisable(false);
        flag = false; // Habilita para alterar uma venda já cadastrada
        controle = 1;
    }

    @FXML
    private void evtConfirmarVendaProduto(ActionEvent event) {
        if(flag) // Cadastrar uma nova venda
        {
            List<Map<String, String>> listprodutos = new ArrayList();
            for (int i = 0; i < tabela_vender_produtos.getItems().size(); i++)
                listprodutos.add((Map<String, String>) tabela_vender_produtos.getItems().get(i));
            
            if(listprodutos.size() > 0)
            {
                List<Map<String, String>> listparcelas = new ArrayList();
                for (int i = 0; i < tabelaparcelasvenderprodutos.getItems().size(); i++)
                    listparcelas.add((Map<String, String>) tabelaparcelasvenderprodutos.getItems().get(i));
                
                if(listparcelas.size() > 0)
                {
                    CtrlFuncionario auxdal = CtrlFuncionario.instanciarCtrlFun();
                    
                    List<Caixa> caixa;// = new ArrayList();
                    caixa = dalcaixa.buscaCaixaFuncionarioEData(auxdal.buscaIdFundionarioPorNome(cbfuncionariovenda.getItems().get(cbfuncionariovenda.getSelectionModel().getSelectedIndex())), dtpdatavenda.getValue());
                    if(caixa.size() > 0 && caixa.get(0).getDatafechamento() == null)
                    {
                        if(txvalortotalvenda.getText().length() > 0 && isDecimal(txvalortotalvenda.getText()))
                        {
                            dalvender.CarregaDadosEssenciaisVendaAtual(asDate(dtpdatavenda.getValue()), Double.parseDouble(txvalortotalvenda.getText()), cbformaspagamentovendas.getItems().get(cbformaspagamentovendas.getSelectionModel().getSelectedIndex()), listparcelas, listprodutos, dalcliente.buscaIdClientePorNome(cbclientesvenda.getItems().get(cbclientesvenda.getSelectionModel().getSelectedIndex())), auxdal.buscaIdFundionarioPorNome(cbfuncionariovenda.getItems().get(cbfuncionariovenda.getSelectionModel().getSelectedIndex())), auxDatasParecelas, flagsobserver);
                            if(dalvender.gravar()/*.salvarVenda(asDate(dtpdatavenda.getValue()), Double.parseDouble(txvalortotalvenda.getText()), cbformaspagamentovendas.getItems().get(cbformaspagamentovendas.getSelectionModel().getSelectedIndex()), listparcelas, listprodutos, dalcliente.buscaIdClientePorNome(cbclientesvenda.getItems().get(cbclientesvenda.getSelectionModel().getSelectedIndex())), auxdal.buscaIdFundionarioPorNome(cbfuncionariovenda.getItems().get(cbfuncionariovenda.getSelectionModel().getSelectedIndex())), auxDatasParecelas, flagsobserver)*/)
                                informa("CONCLUIDO", "O cadastro da venda foi realizado com sucesso!");
                            else
                                informa("ERRO","Não foi possível concluir o cadastro da venda!");
                        }
                        else
                            informa("ERRO", "O campo 'Ven. Valor Total' é  inválido!");
                    }
                    else
                        informa("ERRO", "Verifique se o funcionário selecionado realizou a abertura do caixa na presente data!"); 
                }
                else
                    informa("ERRO", "Não foram geradas as parcelas da venda!");
            }
            else
                informa("ERRO", "Não foram selecionados produtos para a venda!");
        }
        else // Alterar uma Venda
        {
            List<Map<String, String>> listprodutos = new ArrayList();
            for (int i = 0; i < tabela_vender_produtos.getItems().size(); i++)
                listprodutos.add((Map<String, String>) tabela_vender_produtos.getItems().get(i));
            
            if(listprodutos.size() > 0)
            {
                CtrlFuncionario auxdal = CtrlFuncionario.instanciarCtrlFun();
                
                List<Map<String, String>> listparcelas = new ArrayList();
                for (int i = 0; i < tabelaparcelasvenderprodutos.getItems().size(); i++)
                    listparcelas.add((Map<String, String>) tabelaparcelasvenderprodutos.getItems().get(i));
                
                if(listparcelas.size() > 0)
                {
                    List<Caixa> caixa = new ArrayList();
                    caixa = dalcaixa.buscaCaixaFuncionarioEData(auxdal.buscaIdFundionarioPorNome(cbfuncionariovenda.getItems().get(cbfuncionariovenda.getSelectionModel().getSelectedIndex())), dtpdatavenda.getValue());
                    if(caixa.size() > 0)
                    {
                        if(txvalortotalvenda.getText().length() > 0 && isDecimal(txvalortotalvenda.getText()))
                        {
                            if(!txcodigovenda.getText().isEmpty())
                            {
                                if(dalvender.alterarVenda(Integer.parseInt(txcodigovenda.getText()), asDate(dtpdatavenda.getValue()), Double.parseDouble(txvalortotalvenda.getText()), cbformaspagamentovendas.getItems().get(cbformaspagamentovendas.getSelectionModel().getSelectedIndex()), listparcelas, listprodutos, dalcliente.buscaIdClientePorNome(cbclientesvenda.getItems().get(cbclientesvenda.getSelectionModel().getSelectedIndex())), auxdal.buscaIdFundionarioPorNome(cbfuncionariovenda.getItems().get(cbfuncionariovenda.getSelectionModel().getSelectedIndex())), auxDatasParecelas, flagsobserver))
                                    informa("CONCLUIDO", "Alteração realizada com sucesso!");
                                else
                                    informa("ERRO","Não foi possível concluir a alteração da venda!");
                            }
                            else
                                informa("ERRO", "Selecione a venda que deve ser alterada!");
                        }
                        else
                            informa("ERRO", "O campo 'Ven. Valor Total' é  inválido!");
                    }
                    else
                        informa("ERRO", "Verifique se o funcionário selecionado realizou a abertura do caixa na presente data!"); 
                }
                else
                    informa("ERRO", "Não foram geradas as parcelas da venda!");
            }
            else
                informa("ERRO", "Não foram selecionados produtos para a venda!");
        }
        
        limpaTodosCampos();
        desabilitaTodosCampos2();
        bselecionarvendavenda.setDisable(false);
    }

    public boolean verifica(List<Venda> list)
    {
        for (int i = 0; i < list.get(0).getParcelas().size(); i++)
           if(list.get(0).getParcelas().get(i).getDatapagamento() != null)
               return false;
        return true;
    }
    
    // VERIFICAR (DEVE-SE REALIZAR AS ALTERAÇÕES PARA O PADRÃO FAÇADE - NÃO PODE RETORNAR INSTÂNCIAS PARA ESSA PARTE DO PROJETO)
    @FXML
    private void evtEstornarVenda(ActionEvent event) {
        List<Venda> listvenda = dalvender.buscar("ven_cod = "+txcodigovenda.getText());
        if(listvenda.size() > 0)
        {
            if(verifica(listvenda))
            {
                try
                {
                    Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
                    dialogoInfo.setContentText("Confirma a realização do estorno?");
                    if(dialogoInfo.showAndWait().get() == ButtonType.OK)
                    {
                        // Atualizar estoque é para todos os casos de estorno tanto parcelado quanto à vista
                        Produto p;
                        List<Produto> aux;
                        for(int i = 0; i < listvenda.get(0).getItensvenda().size(); i++)
                        {
                            p = listvenda.get(0).getItensvenda().get(i).getProduto(); // Item de venda

                            aux = dalproduto.getProduto("prod_cod = "+p.getId());
                            if(aux.size() > 0)
                            {
                                dalproduto.alterarProduto(aux.get(0).getId(), aux.get(0).getQtd()+p.getQtd(), aux.get(0).getDescricao(), aux.get(0).getVl_unit(), aux.get(0).getTipo());
                                aux.clear();
                            }
                            else
                                aux.clear();
                        }

                        // Atualizar o caixa (Duas possibilidades - Pagamento À vista/Parcelado)
                        List<Caixa> listcaixa = dalcaixa.buscaCaixaFuncionarioEData(listvenda.get(0).getFuncionario(), LocalDate.parse(listvenda.get(0).getDatavenda().toString()));
                        if(listvenda.get(0).getFormapagamento().equals("À Vista")) // Tratamento para pagamento à vista
                        {
                            if(listcaixa.size() > 0)
                            {
                                listcaixa.get(0).setTotalentradas(listcaixa.get(0).getTotalentradas()-listvenda.get(0).getValortotal());
                                dalcaixa.alterarCaixa(listcaixa.get(0));
                            }
                        }
                        else
                            if(listvenda.get(0).getFormapagamento().equals("Parcelado")) // Tratamento para pagamento parcelado
                            {
                                if(listcaixa.size() > 0)
                                {
                                    for (int i = 0; i < listvenda.get(0).getParcelas().size(); i++)
                                    {
                                        if(listvenda.get(0).getParcelas().get(i).getDatapagamento() != null)
                                            listcaixa.get(0).setTotalentradas(listcaixa.get(0).getTotalentradas()-listvenda.get(0).getParcelas().get(i).getValorpago());
                                    }

                                    dalcaixa.alterarCaixa(listcaixa.get(0));
                                }
                            }
                        // Deletar a venda no final
                        dalvender.setIdVendaAtual(listvenda.get(0).getId());
                        dalvender.apagar();
                    }
                }
                catch(Exception ex){}
            }
            else
                informa("ERRO", "Não foi possível realizar o estorno a venda realizada já foi paga ou possui parcelas quitadas!");
        }
        else
            informa("ERRO", "Falha ao realizar o estorno!");
        limpaTodosCampos();
        desabilitaTodosCampos2();
        bselecionarvendavenda.setDisable(false);
    }
}