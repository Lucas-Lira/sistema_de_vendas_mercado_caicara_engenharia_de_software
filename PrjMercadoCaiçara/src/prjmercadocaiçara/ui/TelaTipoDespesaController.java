
package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;
import prjmercadocaiçara.db.controladoras.CtrlDespesa;

/**
 * FXML Controller class
 *
 * @author Alexandre LM
 */
public class TelaTipoDespesaController implements Initializable {

    @FXML
    private JFXButton btnovo;
    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btconfirmar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private JFXTextField txcodigo;
    @FXML
    private JFXTextField txdescricao;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private TableColumn<?, ?> colcodigo;
    @FXML
    private TableColumn<?, ?> coldescricao;
    @FXML
    private JFXButton btselecionar;
    @FXML
    private TableView<Map> tvtpdesp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //MaskFieldUtil.cpfCnpjField(txdescricao);
        
        txcodigo.setDisable(true);
        colcodigo.setCellValueFactory(new MapValueFactory("codigo"));
        coldescricao.setCellValueFactory(new MapValueFactory("descricao"));
        
        habilita("n");
    }    

    private void limparTela()
    {
        txcodigo.clear();
        txdescricao.clear();
        tvtpdesp.getItems().clear();
    }
    
    private void habilita(String caso)
    {
        switch(caso)
        {
            case "n":   ((BorderPane)btalterar.getParent().getParent().getParent()).getCenter().setDisable(true);
                        limparTela();
                        btnovo.setDisable(false);
                        btalterar.setDisable(true);
                        btselecionar.setDisable(true);
                        btconfirmar.setDisable(true);
                        btapagar.setDisable(true);
                        btcancelar.setDisable(true);
            break;
            case "e":   ((BorderPane)btalterar.getParent().getParent().getParent()).getCenter().setDisable(false);
                        btnovo.setDisable(true);
                        btalterar.setDisable(true);
                        btselecionar.setDisable(true);
                        btconfirmar.setDisable(false);
                        btapagar.setDisable(true);
                        btcancelar.setDisable(false);
            break;
            case "v":   ((BorderPane)btalterar.getParent().getParent().getParent()).getCenter().setDisable(true);
                        btnovo.setDisable(false);
                        btselecionar.setDisable(true);
                        btalterar.setDisable(false);
                        btconfirmar.setDisable(true);
                        btapagar.setDisable(false);
                        btcancelar.setDisable(false);
        }
    }
    
    @FXML
    private void clkBtNovo(ActionEvent event) {
        limparTela();
        habilita("e");
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        habilita("e");
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir?") == JOptionPane.YES_OPTION)
        {
            CtrlDespesa ctrldesp = CtrlDespesa.obterInstancia();
            if(!ctrldesp.deletarTipoDespesa(Integer.parseInt(txcodigo.getText())))
                JOptionPane.showMessageDialog(null, "Falha na exclusão");
            habilita("n");
        }
    }

    @FXML
    private void clkBtConfirmar(ActionEvent event) {
        CtrlDespesa ctrldesp = CtrlDespesa.obterInstancia();
        if(txcodigo.getText().isEmpty())
        {
            if(!txdescricao.getText().isEmpty())
            {
                if(!ctrldesp.salvarTipoDespesa(0, txdescricao.getText()))
                    JOptionPane.showMessageDialog(null, "Falha na tentativa de salvar tipo de despesa");
                habilita("n");
            }
        }
        else
        {
            if(!txdescricao.getText().isEmpty())
            {
                if(!ctrldesp.alterarTipoDespesa(Integer.parseInt(txcodigo.getText()),txdescricao.getText()))
                    JOptionPane.showMessageDialog(null, "Falha na tentativa de salvar tipo de despesa");
                habilita("n");
            }
        }
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        habilita("n");
    }

    @FXML
    private void clkBtPesquisar(ActionEvent event) {
        
        CtrlDespesa ctrldesp = CtrlDespesa.obterInstancia();
        tvtpdesp.setItems(ctrldesp.buscarTipoDespesa(txpesquisar.getText()));
        btselecionar.setDisable(tvtpdesp.getItems().isEmpty());
    }

    @FXML
    private void clkBtSelecionar(ActionEvent event) 
    {
        if(!tvtpdesp.getSelectionModel().isEmpty())
        {
            
            txcodigo.setText(tvtpdesp.getSelectionModel().getSelectedItem().get("codigo").toString());
            txdescricao.setText(tvtpdesp.getSelectionModel().getSelectedItem().get("descricao").toString());
            tvtpdesp.getItems().clear();
            habilita("v");
        }
    }
    
}
