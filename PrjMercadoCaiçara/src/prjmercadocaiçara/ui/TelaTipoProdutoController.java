
package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.VBox;
import prjmercadocaiçara.db.controladoras.CtrlTipoProduto;
import prjmercadocaiçara.db.modelos.TipoProduto;

public class TelaTipoProdutoController implements Initializable {

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
    private TableView<Object> tabela;
    @FXML
    private TableColumn<?, ?> colcodigo;
    @FXML
    private TableColumn<?, ?> coldescricao;

    private CtrlTipoProduto tipo;
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
        flag = 2;
        tipo = CtrlTipoProduto.instanciar();
        
        colcodigo.setCellValueFactory(new MapValueFactory("id"));
        coldescricao.setCellValueFactory(new MapValueFactory("descricao"));
        
        //tipo.inicializaTabelaTipoProduto(tabela);
        
        carregaComboboxFiltro();
        estadoInicial();
    }    

    public void estadoInicial()
    {
        bnovo.setDisable(false);
        balterar.setDisable(true);
        bapagar.setDisable(true);
        bconfirmar.setDisable(true);
        txcodigo.setDisable(true);
        txdescricao.setDisable(true);
        txcodigo.clear();
        txdescricao.clear();
        bselecionar.setDisable(false);
        bconsultar.setDisable(false);
        tabela.getItems().clear();
        cbfiltro.setDisable(false);
        txinfo.setDisable(false);
        bcancelar.setDisable(true);
    }
    
    public void carregaComboboxFiltro()
    {
        cbfiltro.getItems().add("Código");
        cbfiltro.getItems().add("Descrição");
        cbfiltro.getSelectionModel().select(0);
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
        tabela.getItems().clear();
        flag = 0;
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
         flag = 1;
         txdescricao.setDisable(false);
         bconsultar.setDisable(true);
         bselecionar.setDisable(true);
         bapagar.setDisable(true);
         bconfirmar.setDisable(false);
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        if(!txcodigo.getText().isEmpty())
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("CONFIRMAÇÃO");
            alert.setContentText("Deseja realmente excluir este cadastro?");
            ButtonType buttonTypeOne = new ButtonType("Sim");
            ButtonType buttonTypeTwo = new ButtonType("Não");
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne)
            {
                if(tipo.getInstancia().apagarTipoProduto(Integer.parseInt(txcodigo.getText())) == true)
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
    
    public boolean validaDados()
    {
        if(txdescricao.getText().length() > 5)
            return true;
        else
            return false;
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        if(flag==0)
        {
            if(validaDados())
            {
                if(tipo.getInstancia().gravarTipoProduto(txdescricao.getText()) == true)
                {
                    informa("INFORMAÇÃO", "Cadastro realizado com sucesso!");
                    estadoInicial();
                } 
                else
                    informa("ERRO", "Não foi possivel realizar o cadastro!");
            }
            else
                informa("ERRO", "O campo descrição é invalido!");
        }
        else
        {
            if(flag == 1)
            {
                if(!txcodigo.getText().isEmpty())
                {
                    if(validaDados())
                    {
                        if(tipo.getInstancia().alterarTipoProduto(Integer.parseInt(txcodigo.getText()), txdescricao.getText()) == true)
                        {
                            informa("INFORMAÇÃO", "Alteração realizada com sucesso!");
                            estadoInicial();
                        }
                        else
                            informa("INFORMAÇÃO", "Não foi possivel realizar a alteração!");
                    }
                    else
                       informa("ERRO", "O campo descrição é invalido!"); 
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
        List<TipoProduto> l = new ArrayList();
        tabela.getItems().clear();
        if(txinfo.getText().isEmpty())
            tipo.getInstancia().getTipoProduto("", tabela);
        else
            if(cbfiltro.getSelectionModel().getSelectedIndex() == 0)
            {
                if(isDigit(txinfo.getText()))
                    tipo.getInstancia().getTipoProduto("tp_cod = "+txinfo.getText(), tabela);
                else
                    informa("ERRO", "A informação digitada é inválida para o filtro selecionado!");
            }
            else
                if(cbfiltro.getSelectionModel().getSelectedIndex() == 1)
                    tipo.getInstancia().getTipoProduto("tp_descricao LIKE '"+txinfo.getText()+"%'", tabela);
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

    // VERIFICAR ESSE TRECHO ACESSO DIRETO A INSTÂNCIA NÃO É PERMITIDO
    @FXML
    private void evtSelecionar(ActionEvent event) {
        int posi = tabela.getSelectionModel().getSelectedIndex();
        if(posi >= 0)
        {
            Map<String, String> dataRow = (Map<String, String>)tabela.getItems().get(posi);
            txcodigo.setText(""+dataRow.get("id"));
            txdescricao.setText(""+dataRow.get("descricao"));
            estadoSelecao();
        }
        else
            informa("ERRO", "Item não selecionado!");
    }
}
