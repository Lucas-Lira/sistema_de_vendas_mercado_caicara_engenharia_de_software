package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import prjmercadocaiçara.db.controladoras.CtrlDespesa;
import prjmercadocaiçara.ui.util.MaskFieldUtil;

public class TelaLancarPagarDespesaController implements Initializable {

    @FXML
    private TabPane tpane;
    @FXML
    private Tab tabpagar;
    @FXML
    private TableView<Map> tvdespesas;
    @FXML
    private JFXComboBox<Map> cbtipodespp;
    @FXML
    private Label lvalor;
    @FXML
    private Label ldescricao;
    @FXML
    private Label lvencto;
    @FXML
    private Label lstatus;
    @FXML
    private JFXTextField txjuros;
    @FXML
    private JFXComboBox<String> cboperacao;
    @FXML
    private Tab tablancar;
    @FXML
    private JFXComboBox<Map> cbtipodespl;
    @FXML
    private JFXTextField txdescricao;
    @FXML
    private JFXComboBox<Integer> cbdiavencto;
    @FXML
    private JFXTextField txvalor;
    @FXML
    private JFXTextField txnparcelas;
    @FXML
    private TableColumn<?, ?> colparcela;
    @FXML
    private TableColumn<?, ?> coldtvenc;
    @FXML
    private TableColumn<?, ?> colvalor;
    @FXML
    private TableColumn<?, ?> coldtpgto;
    @FXML
    private JFXComboBox<Map> cbdescricao;
    @FXML
    private JFXComboBox<Object> cbmes;
    @FXML
    private JFXButton bconfop;
    
    private CtrlDespesa ctrldesp;
    //private int totalparcelas;
    //private Parcela parcelaatual;
    //private Despesa despesaatual;
    @FXML
    private JFXButton bconfop1;
    
    /*private ListCell<Map> formatCell() {
        return new ListCell<Map>() {
            @Override
            protected void updateItem(Map item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.get("descricao").toString());
                }
            }        
        };
    }*/
    
    private StringConverter toShowInComboBox()
    {
        return new StringConverter<Map>() {
            @Override
            public String toString(Map object) {
                if(object!=null)
                    return object.get("descricao").toString();
                else
                    return "";
            }

            @Override
            public Map fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //parcelaatual = null;
        ctrldesp = CtrlDespesa.obterInstancia();
        ObservableList<Map> tpdespesas = ctrldesp.carregarTiposDespesas();
        cbtipodespl.setItems(tpdespesas);
        cbtipodespp.setItems(tpdespesas);
      
        cbtipodespp.setConverter(toShowInComboBox());
        cbtipodespl.setConverter(toShowInComboBox());
        cbdescricao.setConverter(toShowInComboBox());
        
        for(int i=1;i<32;i++)
            cbdiavencto.getItems().add(i);
        cbmes.getItems().add("Mes");
        for(int i=1;i<13;i++)
            cbmes.getItems().add(i);
        
        cboperacao.getItems().add("Pagar");
        cboperacao.getItems().add("Efetuar Baixa");
        
        cbtipodespp.getSelectionModel().selectedItemProperty().addListener((s)->{
            cbdescricao.setItems(ctrldesp.buscarDespesa(Integer.parseInt(cbtipodespp.getSelectionModel().getSelectedItem().get("codigo").toString())));
        });
        
        
        
        tpane.getSelectionModel().selectedItemProperty().addListener((w)->{
            limparTela(tpane.getSelectionModel().getSelectedIndex());
        });
        cbmes.getSelectionModel().select(LocalDate.now().getMonthValue());
        
        coldtvenc.setCellValueFactory(new MapValueFactory("dtvencto"));
        colparcela.setCellValueFactory(new MapValueFactory("codigo"));
        colvalor.setCellValueFactory(new MapValueFactory("valor"));
        coldtpgto.setCellValueFactory(new MapValueFactory("dtpagto"));
        
        /*txjuros.textProperty().addListener((k)->{
            if(txjuros.getText().isEmpty())
                txjuros.setText("0");
        });*/
        
        //txjuros.setText("0");
        MaskFieldUtil.monetaryField(txvalor);
        MaskFieldUtil.onlyDigitsValue(txnparcelas);
        MaskFieldUtil.onlyDigitsValue(txjuros);
    }    

    public void limparTela(int t)
    {
        switch(t)
        {
            case 1: 
                txdescricao.clear();
                cbdiavencto.getSelectionModel().clearSelection();
                txnparcelas.clear();
                txvalor.clear();
                cbtipodespl.getSelectionModel().clearSelection();
                
                break;
            default:
                cbdescricao.getSelectionModel().clearSelection();
                cbmes.getSelectionModel().select(LocalDate.now().getMonthValue()-1);
                cbtipodespp.getSelectionModel().clearSelection();
                cboperacao.getSelectionModel().clearSelection();
                tvdespesas.refresh();
                txjuros.setText("0");
                ldescricao.setText("Descrição: ");
                //lparcela.setText("Parcela: ");
                lstatus.setText("Status: ");
                lvalor.setText("Valor: ");
                lvencto.setText("Data Vencimento: ");
        }
    }
    
    @FXML
    private void clkConsultar(ActionEvent event) {
        if(!cbtipodespp.getSelectionModel().isEmpty())
        {
            if(!cbdescricao.getSelectionModel().isEmpty())
            {
                tvdespesas.setItems(ctrldesp.buscarParcela(Integer.parseInt(cbdescricao.getSelectionModel().getSelectedItem().get("codigo").toString()), cbmes.getSelectionModel().getSelectedIndex()));
                
                tvdespesas.refresh();
            }
            else
                JOptionPane.showMessageDialog(null, "Selecione a despesa para consulta");
        }
        else
            JOptionPane.showMessageDialog(null, "Selecione o tipo de despesa para habilitar as despesas para consulta");
    }

    @FXML
    private void clkSelecionar(ActionEvent event) 
    {
        if(!tvdespesas.getSelectionModel().isEmpty())
        {
            Map parcelaatual = tvdespesas.getSelectionModel().getSelectedItem();
            txjuros.setText(""+Double.parseDouble(parcelaatual.get("juros").toString())*100);
            ldescricao.setText("Descrição:  "+parcelaatual.get("descricao"));
            //lparcela.setText("Parcela:  "+parcelaatual.get("codigo")+" de "+totalparcelas);
            ctrldesp.setParcelaAtual(
                Integer.parseInt(parcelaatual.get("codigo").toString()), 
                parcelaatual.get("descricao").toString(), 
                Double.parseDouble(parcelaatual.get("valor").toString()), 
                LocalDate.parse(parcelaatual.get("dtvencto").toString()), 
                Double.parseDouble(parcelaatual.get("juros").toString()), 
                (parcelaatual.get("dtpagto")!=null)?LocalDate.parse(parcelaatual.get("dtpagto").toString()):null, 
                Double.parseDouble(parcelaatual.get("valorpagto").toString())
            );
            if(LocalDate.parse(parcelaatual.get("dtvencto").toString()).isBefore(LocalDate.now()) && parcelaatual.get("dtpagto")==null)
                lstatus.setText("Status:  Vencida");
            else if(parcelaatual.get("dtpagto")==null)
                lstatus.setText("Status:  Pendente");
            else
                lstatus.setText("Status:  Paga");

            lvalor.setText("Valor:  "+parcelaatual.get("valor").toString());
            lvencto.setText("Data Vencimento:  "+parcelaatual.get("dtvencto").toString());
            bconfop.setDisable(parcelaatual.get("dtpagto")!=null);
        }
    }

    @FXML
    private void clkConfirmarPgto(ActionEvent event) {
     
        if(!cboperacao.getSelectionModel().isEmpty())
        {
            String msgretorno;
            double juros = (txjuros.getText().isEmpty())?0:Double.parseDouble(txjuros.getText());
            if(cboperacao.getSelectionModel().getSelectedIndex()==0)//Pagar
                msgretorno = ctrldesp.pagarParcela(Integer.parseInt(cbdescricao.getSelectionModel().getSelectedItem().get("codigo").toString()), Double.parseDouble(txvalor.getText()), juros);
            else
                msgretorno = ctrldesp.efetuarBaixa(Integer.parseInt(cbdescricao.getSelectionModel().getSelectedItem().get("codigo").toString()), Double.parseDouble(txvalor.getText()), juros);
            if(!msgretorno.isEmpty())
                JOptionPane.showMessageDialog(null, msgretorno);
        }   
        else
            JOptionPane.showMessageDialog(null, "Selecione um tipo de operação");
    }

    @FXML
    private void clkLancarDespesa(ActionEvent event) {
        if(!cbtipodespl.getSelectionModel().isEmpty())
        {
            if(!txdescricao.getText().isEmpty())
            {
                if(!cbdiavencto.getSelectionModel().isEmpty())
                {
                    if(!txvalor.getText().isEmpty())
                    {
                        if(!txnparcelas.getText().isEmpty())
                        {
                            ctrldesp = CtrlDespesa.obterInstancia();
                            if(!ctrldesp.salvarDespesa(0, txdescricao.getText(), Double.parseDouble(txvalor.getText().replace(",", ".")), cbdiavencto.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(txnparcelas.getText()), LocalDate.now(), 'N', (int)cbtipodespl.getSelectionModel().getSelectedItem().get("codigo"),cbtipodespl.getSelectionModel().getSelectedItem().get("descricao").toString()))
                                JOptionPane.showMessageDialog(null, "Erro inesperado durante a tentativa de salvar a despesa");
                            limparTela(1);
                        }
                        else
                        JOptionPane.showMessageDialog(null, "Informe o nº de parcelas");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Informe o valor da despesa");
                }
                else
                    JOptionPane.showMessageDialog(null, "Informe o dia de vencimento");
            }
            else
                JOptionPane.showMessageDialog(null, "Informe a descrição da despesa");
        }
        else
            JOptionPane.showMessageDialog(null, "Selecione o tipo de despesa");
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        ScrollPane pncentral =  (ScrollPane)((BorderPane)txdescricao.getScene().getRoot()).getCenter();
        pncentral.setContent(null);
    }
    
}
