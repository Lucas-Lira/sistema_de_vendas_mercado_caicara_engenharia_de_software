
package prjmercadocaiçara.ui;

import prjmercadocaiçara.ui.util.MaskFieldUtil;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import prjmercadocaiçara.db.controladoras.CtrlFornecedor;
import prjmercadocaiçara.db.modelos.Fornecedor;

public class TelaFornecedorController implements Initializable {

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
    private JFXTextField txcnpj;
    @FXML
    private JFXDatePicker dtpvinculo;
    @FXML
    private JFXDatePicker dtpdesvinculo;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txtelefone;
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
    @FXML
    private TableColumn<?, ?> colcnpj;
    @FXML
    private TableColumn<?, ?> colvinculo;
    @FXML
    private TableColumn<?, ?> coldesvinculo;
    @FXML
    private TableColumn<?, ?> coltelefone;
    @FXML
    private TableColumn<?, ?> colemail;

    private CtrlFornecedor controladora;
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
        controladora = CtrlFornecedor.instanciar();
        
        flag = 2;
        
        colcodigo.setCellValueFactory(new MapValueFactory("id"));
        coldescricao.setCellValueFactory(new MapValueFactory("descricao"));
        colcnpj.setCellValueFactory(new MapValueFactory("cnpj"));
        colvinculo.setCellValueFactory(new MapValueFactory("vinculo"));
        coldesvinculo.setCellValueFactory(new MapValueFactory("desvinculo"));
        coltelefone.setCellValueFactory(new MapValueFactory("telefone"));
        colemail.setCellValueFactory(new MapValueFactory("email"));
        
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.cnpjField(txcnpj);
        
        carregaComboboxFiltro();
        estadoInicial();
    }    

    public void carregaComboboxFiltro()
    {
        cbfiltro.getItems().add("Código");
        cbfiltro.getItems().add("Descrição");
        cbfiltro.getItems().add("CNPJ");
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
        //Adicionados Nesta Tela
        //===========================
        txcnpj.setDisable(false);
        txemail.setDisable(false);
        txtelefone.setDisable(false);
        dtpvinculo.setDisable(false);
        dtpdesvinculo.setDisable(true);
        
        dtpvinculo.setValue(LocalDate.now());
        dtpdesvinculo.setValue(LocalDate.now());
        
        txcnpj.clear();
        txemail.clear();
        txtelefone.clear();
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
        //Adicionados Nesta Tela
        //===========================
        txcnpj.setDisable(false);
        txemail.setDisable(false);
        txtelefone.setDisable(false);
        dtpvinculo.setDisable(false);
        dtpdesvinculo.setDisable(false);
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
                if(controladora.apagarFornecedor(Integer.parseInt(txcodigo.getText())) == true)
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
        if(txdescricao.getText().length() >= 5)
        {
            if(controladora.validaCNPJ(txcnpj.getText()))
            {
                LocalDate data = dtpvinculo.getValue();
                if(data.isBefore(LocalDate.now()) || data.isEqual(LocalDate.now()))
                {
                    LocalDate data2 = dtpdesvinculo.getValue();
                    if(data2.isAfter(data) && data2.isBefore(LocalDate.now()) || data2.isEqual(LocalDate.now()))
                    {
                        if(txemail.getText().length() >= 8)
                        {
                            if(txtelefone.getText().length() == 14)
                                return true;
                            else
                            {
                                informa("ERRO", "O campo telefone é inválido");
                                return false;
                            }
                        } 
                        else
                        {
                            informa("ERRO", "O campo e-mail é inválido");
                            return false;
                        }
                    }
                    else
                    {
                        informa("ERRO", "As datas informadas são inconsistentes!");
                        return false;
                    }    
                }
                else
                {
                    informa("ERRO", "As datas informadas são inconsistentes!");
                    return false;
                }
            }
            else
            {
                informa("ERRO", "O campo CNPJ é invalido!");
                return false;
            }
        }
        else
        {
           informa("ERRO", "O campo descrição é invalido!");
           return false;
        }
    }

    @FXML
    private void evtConfirmar(ActionEvent event) throws ParseException {
        if(flag==0)
        {
            if(validaDados())
            {
                Date datavinculo  = Date.from(dtpvinculo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                //Date datadesvinculo = Date.from(LocalDate.parse("00/00/0000").atStartOfDay(ZoneId.systemDefault()).toInstant()/*dtpdesvinculo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()*/);

                if(controladora.gravarFornecedor(datavinculo/*, nulldatadesvinculo*/, txcnpj.getText(), txdescricao.getText(), txtelefone.getText(), txemail.getText()) == true)
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
                        Date datavinculo  = Date.from(dtpvinculo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date datadesvinculo = Date.from(dtpdesvinculo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        if(controladora.alterarFornecedor(Integer.parseInt(txcodigo.getText()), datavinculo, datadesvinculo, txcnpj.getText(), txdescricao.getText(), txtelefone.getText(), txemail.getText()) == true)
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
        txcnpj.setDisable(true);
        txemail.setDisable(true);
        txtelefone.setDisable(true);
        dtpvinculo.setDisable(true);
        dtpdesvinculo.setDisable(true);
        
        dtpvinculo.setValue(LocalDate.now());
        dtpdesvinculo.setValue(LocalDate.now());
        
        txcnpj.clear();
        txemail.clear();
        txtelefone.clear();
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

    @FXML
    private void evtCancelar(ActionEvent event) {
        estadoInicial();
        flag = 2;
    }

    @FXML
    private void evtConsultar(ActionEvent event) {
        List<Fornecedor> l = new ArrayList();
        tabela.getItems().clear();
        if(txinfo.getText().isEmpty())
            controladora.getFornecedorTabelas("", tabela);
        else
            if(cbfiltro.getSelectionModel().getSelectedIndex() == 0)
            {
                if(isDigit(txinfo.getText()))
                    controladora.getFornecedorTabelas("f_cod = "+txinfo.getText(), tabela);
                else
                    informa("ERRO", "A informação digitada é inválida para o filtro selecionado!");
            }
            else
                if(cbfiltro.getSelectionModel().getSelectedIndex() == 1)
                    controladora.getFornecedorTabelas("f_descricao LIKE '"+txinfo.getText()+"%'", tabela);
                else
                    if(cbfiltro.getSelectionModel().getSelectedIndex() == 2)
                        controladora.getFornecedorTabelas("f_cnpj = '"+txinfo.getText()+"'", tabela);
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
            Map<String, String> dataRow = (Map<String, String>)tabela.getItems().get(posi);
            
            txcodigo.setText(""+dataRow.get("id")/*""+aux.getId()*/);
            txdescricao.setText(""+dataRow.get("descricao")/*aux.getDescricao()*/);
            txcnpj.setText(""+dataRow.get("cnpj")/*aux.getCnpj()*/);
            dtpvinculo.setValue(LocalDate.parse(""+dataRow.get("vinculo").toString()/*aux.getVinculo().toString()*/));
            if(dataRow.get("desvinculo").toString() != "")
                dtpdesvinculo.setValue(LocalDate.parse(""+dataRow.get("desvinculo").toString()/*aux.getDesvinculo().toString()*/));
            
            txemail.setText(""+dataRow.get("email")/*aux.getEmail()*/);
            txtelefone.setText(""+dataRow.get("telefone")/*aux.getTelefone()*/);
            estadoSelecao();
        }
        else
            informa("ERRO", "Item não selecionado!");
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
}
