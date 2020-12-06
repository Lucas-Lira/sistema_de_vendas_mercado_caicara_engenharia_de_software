package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import prjmercadocaiçara.db.controladoras.CtrlProduto;
import prjmercadocaiçara.db.controladoras.CtrlTipoProduto;
import prjmercadocaiçara.db.modelos.Produto;
import prjmercadocaiçara.util.ProdutoAux;
import prjmercadocaiçara.db.modelos.TipoProduto;

public class TelaProdutoController implements Initializable {

    @FXML
    private VBox pndados;
    @FXML
    private JFXButton bnovo;
    @FXML
    private JFXButton balterar;
    @FXML
    private JFXButton bapagar;
    @FXML
    private JFXButton bconfirmar;
    @FXML
    private JFXButton bcancelar;
    @FXML
    private JFXTextField txcodigo;
    @FXML
    private JFXTextField txdescricao;
    @FXML
    private JFXTextField txvalorunidade;
    @FXML
    private JFXTextField txqtd;
    @FXML
    private JFXComboBox<TipoProduto> cbtipo;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private JFXTextField txinfo;
    @FXML
    private JFXComboBox<String> cbfiltro;
    @FXML
    private JFXButton bconsultar;
    @FXML
    private JFXButton bselecionar;
    @FXML
    private TableView<ProdutoAux> tabela;
    @FXML
    private TableColumn<?, ?> colcodigo;
    @FXML
    private TableColumn<?, ?> coldescricao;
    @FXML
    private TableColumn<?, ?> colvalorunidade;
    @FXML
    private TableColumn<?, ?> colqtd;
    @FXML
    private TableColumn<?, ?> coltipocod;
    @FXML
    private TableColumn<?, ?> coltipodescricao;
    
    private CtrlProduto prod;
    private int flag;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        balterar.setTooltip(geraMsgB("Alterar"));
        bapagar.setTooltip(geraMsgB("Apagar"));
        bcancelar.setTooltip(geraMsgB("Cancelar"));
        bconfirmar.setTooltip(geraMsgB("Confirmar"));
        bconsultar.setTooltip(geraMsgB("Consultar"));
        bnovo.setTooltip(geraMsgB("Novo"));
        bselecionar.setTooltip(geraMsgB("Selecionar"));
        
        prod = CtrlProduto.instanciar();
        flag = 2;
        
        colcodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colvalorunidade.setCellValueFactory(new PropertyValueFactory<>("vl_unit"));
        colqtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        coltipocod.setCellValueFactory(new PropertyValueFactory<>("idtipo"));
        coltipodescricao.setCellValueFactory(new PropertyValueFactory<>("descricaotipo"));
        
        carregaComboboxFiltro();
        carregaComboboxTipoProduto();
        estadoInicial();
    }   

    public void carregaComboboxFiltro()
    {
        cbfiltro.getItems().add("Código");
        cbfiltro.getItems().add("Descrição");
        cbfiltro.getItems().add("Tipo Produto");
        cbfiltro.getSelectionModel().select(0);
    }
    
    public void carregaComboboxTipoProduto()
    {
        cbtipo.getItems().clear();
        CtrlTipoProduto.instanciar().carregaComboboxTipoProdutos(cbtipo);
        cbtipo.getSelectionModel().select(0);
    }
    
    public void estadoInicial()
    {
        bnovo.setDisable(false);
        balterar.setDisable(true);
        bapagar.setDisable(true);
        bconfirmar.setDisable(true);
        
        txcodigo.setDisable(true);
        txdescricao.setDisable(true);
        //Adicionados Nesta Tela
        //===========================
        cbtipo.setDisable(true);
        txvalorunidade.setDisable(true);
        txqtd.setDisable(true);
        
        txvalorunidade.clear();
        txqtd.clear();
        //=========================
        txcodigo.clear();
        txdescricao.clear();
        
        bselecionar.setDisable(false);
        bconsultar.setDisable(false);
        tabela.getItems().clear();
        cbfiltro.setDisable(false);
        txinfo.setDisable(false);
        bcancelar.setDisable(true);
    }
    
    public static Tooltip geraMsgB(String msg)
    {
        Tooltip tt = new Tooltip();
        tt.setText(msg);
        tt.setStyle("-fx-font: normal bold 10 Langdon; "
            + "-fx-text-fill: white;"+"-fx-background-color:   #607D8B;");
        return tt;
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        txdescricao.setDisable(false);
        //Adicionados Nesta Tela
        //===========================
        cbtipo.setDisable(false);
        txvalorunidade.setDisable(false);
        txqtd.setDisable(false);
        
        txvalorunidade.clear();
        txqtd.clear();
        cbtipo.getSelectionModel().select(0);
        //=========================
        tabela.getItems().clear();
        
        bconfirmar.setDisable(false);
        txdescricao.clear();
        txcodigo.clear();
        txdescricao.requestFocus();
        bconsultar.setDisable(true);
        bselecionar.setDisable(true);
        bnovo.setDisable(true);
        cbfiltro.setDisable(true);
        txinfo.setDisable(true);
        bcancelar.setDisable(false);
        balterar.setDisable(true);
        bapagar.setDisable(true);
        flag = 0;
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        flag = 1;
        txdescricao.setDisable(false);
        //===========================
        cbtipo.setDisable(false);
        txvalorunidade.setDisable(false);
        txqtd.setDisable(false);
        //=========================
        bconsultar.setDisable(true);
        bselecionar.setDisable(true);
        bapagar.setDisable(true);
        bconfirmar.setDisable(false);
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        if(!txcodigo.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("CONFIRMAÇÃO");
            alert.setContentText("Deseja realmente excluir este cadastro?");
            ButtonType buttonTypeOne = new ButtonType("Sim");
            ButtonType buttonTypeTwo = new ButtonType("Não");
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne)
            {
                if(prod.getInstancia().apagarProduto(Integer.parseInt(txcodigo.getText())) == true)
                {
                    informa("INFORMAÇÃO", "Exclusão realizada com sucesso!");
                    estadoInicial();
                }
                else
                    informa("INFORMAÇÃO", "Não foi possivel excluir este cadastro!");
            }
        }
        else
            informa("ERRO","Selecione o cadastro que deseja excluir!");
    }
    
    public static boolean isNumeric (String s) {
        try {
            Double.parseDouble (s); 
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public boolean validaDados()
    {
        if(txdescricao.getText().length() >= 5)
           if(isNumeric(txvalorunidade.getText()) && !txvalorunidade.getText().isEmpty())
               if(isDigit(txqtd.getText()) && !txqtd.getText().isEmpty())
                   return true;
               else
               {
                   informa("ERRO", "O campo quantidade é invalido!");
                   return false;
               }
           else
           {
               informa("ERRO", "O campo valor unidade é invalido!");
               return false;
           }
        else
        {
           informa("ERRO", "O campo descrição é invalido!");
           return false;
        }     
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        if(flag==0)
        {
            if(validaDados())
            {
                int index = cbtipo.getSelectionModel().getSelectedIndex();
                if(prod.getInstancia().gravarProduto(Integer.parseInt(txqtd.getText()), txdescricao.getText(), Double.parseDouble(txvalorunidade.getText()), (TipoProduto)cbtipo.getItems().get(index)) == true)
                {
                    informa("INFORMAÇÃO", "Cadastro realizado com sucesso!");
                    estadoInicial();
                } 
                else
                    informa("ERRO", "Não foi possivel realizar o cadastro!");
            }
        }
        else
        {
            if(flag == 1)
            {
                if(!txcodigo.getText().isEmpty())
                {
                    if(validaDados())
                    {
                        int index = cbtipo.getSelectionModel().getSelectedIndex();
                        if(prod.getInstancia().alterarProduto(Integer.parseInt(txcodigo.getText()),Integer.parseInt(txqtd.getText()), txdescricao.getText(), Double.parseDouble(txvalorunidade.getText()), (TipoProduto)cbtipo.getItems().get(index)) == true)
                        {
                            informa("INFORMAÇÃO", "Alteração realizada com sucesso!");
                            estadoInicial();
                        }
                        else
                            informa("INFORMAÇÃO", "Não foi possivel realizar a alteração!");
                    }
                }
                else
                   informa("ERRO","Selecione o cadastro que deseja alterar!"); 
            }
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        estadoInicial();
        flag = 2;
    }
    
    public void informa(String str1, String str2)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(str1);
        alert.setContentText(str2);
        alert.showAndWait();
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

    @FXML
    private void evtConsultar(ActionEvent event) {
        List<Produto> l = new ArrayList();
        tabela.getItems().clear();
        if(txinfo.getText().isEmpty())
            prod.getInstancia().getProdutoCarregaTabelaClasseAuxiliar("", tabela);
        else
            if(cbfiltro.getSelectionModel().getSelectedIndex() == 0)
            {
                if(isDigit(txinfo.getText()))
                    prod.getInstancia().getProdutoCarregaTabelaClasseAuxiliar("prod_cod = "+txinfo.getText(), tabela);
                else
                    informa("ERRO", "A informação digitada é inválida para o filtro selecionado!");
            }
            else
                if(cbfiltro.getSelectionModel().getSelectedIndex() == 1)
                    prod.getInstancia().getProdutoCarregaTabelaClasseAuxiliar("prod_descricao LIKE '"+txinfo.getText()+"%'", tabela);
                else
                    if(cbfiltro.getSelectionModel().getSelectedIndex() == 2)
                    {
                        if(isDigit(txinfo.getText()))
                            prod.getInstancia().getProdutoCarregaTabelaClasseAuxiliar("tp_cod = "+txinfo.getText(), tabela);
                        else
                            informa("ERRO", "A informação digitada é inválida para o filtro selecionado!");
                    }
        txinfo.clear();
    }
    
    public void estadoSelecao()
    {
        balterar.setDisable(false);
        bapagar.setDisable(false);
        bconfirmar.setDisable(true);
        bnovo.setDisable(true);
        bcancelar.setDisable(false);
    }

    @FXML
    private void evtSelecionar(ActionEvent event) {
        int posi = tabela.getSelectionModel().getSelectedIndex();
        if(posi >= 0)
        {
            ProdutoAux aux = tabela.getItems().get(posi);
            txcodigo.setText(""+aux.getId());
            txdescricao.setText(aux.getDescricao());
            txvalorunidade.setText(""+aux.getVl_unit());
            txqtd.setText(""+aux.getQtd());
            TipoProduto p = new TipoProduto(aux.getIdtipo(), aux.getDescricaotipo());
            cbtipo.getSelectionModel().select(p);
            estadoSelecao();
        }
        else
            informa("ERRO", "Item não selecionado!");
    }
}
