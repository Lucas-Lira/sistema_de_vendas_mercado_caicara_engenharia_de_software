/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prjmercadocaiçara.ui;

import prjmercadocaiçara.ui.util.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import prjmercadocaiçara.db.controladoras.CtrlCliente;
import prjmercadocaiçara.db.controladoras.CtrlEndereco;
import prjmercadocaiçara.util.AcessoWS;

/**
 * FXML Controller class
 *
 * @author Alexandre LM
 */
public class TelaClienteController implements Initializable {

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
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txrua;
    @FXML
    private JFXButton btcpf;
    @FXML
    private JFXComboBox<Map> cbcidade;
    @FXML
    private JFXDatePicker dpnasc;
    @FXML
    private JFXTextField txnumero;
    @FXML
    private JFXRadioButton rbtnome;
    @FXML
    private ToggleGroup unique;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btselecionar;
    @FXML
    private TableView<Map> tvcliente;
    @FXML
    private TableColumn<?, ?> colcodigo;
    @FXML
    private TableColumn<?, ?> colnome;
    @FXML
    private TableColumn<?, ?> colcpf;
    @FXML
    private TableColumn<?, ?> coltelefone;
    @FXML
    private TableColumn<?, ?> colemail;
    private TableColumn<?, ?> collogradouro;
    @FXML
    private TableColumn<?, LocalDate> coldtnasc;
    @FXML
    private TableColumn<?, ?> colendereco;
    @FXML
    private TableColumn<?, ?> colrua;
    @FXML
    private TableColumn<?, ?> colnum;
    @FXML
    private TableColumn<?, ?> colbairro;
    @FXML
    private TableColumn<?, ?> colcep;
    @FXML
    private TableColumn<?, ?> colcidade;
    
    private ListCell<Map> formatCell() {
    return new ListCell<Map>() {
        @Override
        protected void updateItem(Map item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.get("nome")+"/"+item.get("uf"));
            }
        }        
    };
    }
    
    private StringConverter toShowCityInComboBox() {
    return new StringConverter<Map>() {
            @Override
            public String toString(Map object) {
                if(object!=null)
                    return object.get("nome").toString()+"/"+object.get("uf").toString();
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
        
        CtrlEndereco ctrle = CtrlEndereco.obterInstancia();
        cbcidade.setItems(ctrle.carregarCidades());
        cbcidade.setConverter(toShowCityInComboBox());
        //cbcidade.setButtonCell(formatCell());
        
        
        /*txcep.textProperty().addListener(t->{
            if(txcep.getText().length() > 8)
                txcep.setText(txcep.getText().substring(0,8));
        });
        
        txnumero.textProperty().addListener(t->{
            if(txnumero.getText().length() > 8)
                txnumero.setText(txnumero.getText().substring(0,8));
        });
        */
        
        colcodigo.setCellValueFactory(new MapValueFactory("codigo"));
        colnome.setCellValueFactory(new MapValueFactory("nome"));
        coldtnasc.setCellValueFactory(new MapValueFactory("dtnasc"));
        colcpf.setCellValueFactory(new MapValueFactory("cpf"));
        coltelefone.setCellValueFactory(new MapValueFactory("telefone"));
        colemail.setCellValueFactory(new MapValueFactory("email"));
        colrua.setCellValueFactory(new MapValueFactory("rua"));
        colnum.setCellValueFactory(new MapValueFactory("numero"));
        colbairro.setCellValueFactory(new MapValueFactory("bairro"));
        colcep.setCellValueFactory(new MapValueFactory("cep"));
        colcidade.setCellValueFactory(new MapValueFactory("cidade"));
        
       
        ChangeListener cl = (observableValue, number, number2) -> {
            String value = txpesquisar.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
            try {
            txpesquisar.setText(value);
            }catch(Exception ex){}
        };
        rbtnome.selectedProperty().addListener(t ->{
            if(!rbtnome.isSelected())
            {
                txpesquisar.clear();
                txpesquisar.lengthProperty().addListener(cl);
                //MaskFieldUtil.cpfField(txpesquisar);
            }
            else
            {
                txpesquisar.clear();
                txpesquisar.lengthProperty().removeListener(cl);
            }
               
        });
        
        
        
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.numericField(txnumero);
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.maxField(txnumero, 6);
        
        txcep.textProperty().addListener((g)->{
            if(cbcidade.getSelectionModel().isEmpty() && txcep.getText().length()==9)
            {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            String ret = AcessoWS.consultaCep(txcep.getText(), "json");
                            JSONObject my_obj = new JSONObject(ret);
                            String cid = CtrlEndereco.obterInstancia().buscarCidade(my_obj.getString("cidade"),my_obj.getString("uf"));
                            Platform.runLater(new Thread(()->{
                                int i=0;
                                System.out.println(cbcidade.getItems().get(i));
                                while(i<cbcidade.getItems().size() && !(cbcidade.getItems().get(i).get("nome").toString()+"/"+cbcidade.getItems().get(i).get("uf").toString()).toUpperCase().equals(cid))
                                    i++;
                                if(i<cbcidade.getItems().size())
                                    cbcidade.getSelectionModel().select(i);
                                else
                                    cbcidade.getSelectionModel().clearSelection();
                            }));
                        }
                        catch(Exception e)
                        { 
                            System.out.println(e);
                        }
                    }
                });
            }
            else
                cbcidade.getSelectionModel().clearSelection();
        });
        
        habilita("n");
    }    

    private void limparTela()
    {
        txcodigo.clear();
        txnome.clear();
        txtelefone.clear();
        txemail.clear();
        txcpf.clear();
        txcep.clear();
        cbcidade.getSelectionModel().clearSelection();
        txbairro.clear();
        txrua.clear();
        txnumero.clear();
        tvcliente.getItems().clear();
    }
    
    private void habilita(String caso)
    {
        switch(caso)
        {
            case "n":   ((BorderPane)btalterar.getParent().getParent().getParent()).getCenter().setDisable(true);
                        limparTela();
                        btnovo.setDisable(false);
                        btalterar.setDisable(true);
                        btconfirmar.setDisable(true);
                        btapagar.setDisable(true);
                        btcancelar.setDisable(true);
            break;
            case "e":   ((BorderPane)btalterar.getParent().getParent().getParent()).getCenter().setDisable(false);
                        btnovo.setDisable(true);
                        btalterar.setDisable(true);
                        btconfirmar.setDisable(false);
                        btapagar.setDisable(true);
                        btcancelar.setDisable(false);
            break;
            case "v":   ((BorderPane)btalterar.getParent().getParent().getParent()).getCenter().setDisable(true);
                        tvcliente.getItems().clear();
                        btnovo.setDisable(false);
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
            CtrlCliente ctrlcli = CtrlCliente.obterInstancia();
            if(!ctrlcli.excluirCliente(Integer.parseInt(txcodigo.getText())))
                JOptionPane.showMessageDialog(null, "Falha na exclusão");
            habilita("n");
        }
    }

    private boolean validaCPF(String cpf)
    {
        String aux;
        int soma,resultado;
        char vet[];
        aux = cpf.replace(".","");
        aux = aux.replace("-","");
        if (aux.length()!=11)
            return false;
        vet = aux.toCharArray();
        int i = 0;
        while (i < 11 && vet[0] == vet[i])
            i++;
        if (i == 12)
            return false;
        else
        {
            soma = 0;
            int resto;
            for (i=0;   i<9;  i++)
                soma+=Integer.parseInt(""+vet[i])*(10-i);
            resto = soma%11;
            if(resto < 2)
                resultado = 0;
            else
                resultado = 11-resto;
            if (Integer.parseInt(""+vet[9]) == resultado)
            {
                soma = 0;
                for (i=0;   i<10;  i++)
                    soma += Integer.parseInt(""+vet[i])*(11-i);
                resto = soma%11;
                if(resto < 2)
                    resultado = 0;
                else
                    resultado = 11-resto;
                if (Integer.parseInt(""+vet[10]) ==  resultado)
                    return true;
                return false;
            }
            return false;
        }
    }
    
    @FXML
    private void clkBtConfirmar(ActionEvent event) 
    {
        if(txnome.getText().trim().length() > 3)
        {
            if(validaCPF(txcpf.getText()))
            {
                if(!txtelefone.getText().isEmpty())
                {
                    String cep = txcep.getText().replace("-", "");
                    if(cep.length()==8)
                    {
                        if(!cbcidade.getSelectionModel().isEmpty())
                        {
                            if(!txbairro.getText().trim().isEmpty())
                            {
                                if(!txrua.getText().trim().isEmpty())
                                {
                                    CtrlCliente ctrlcli = CtrlCliente.obterInstancia(); 
                                    Map cid = cbcidade.getSelectionModel().getSelectedItem();
                                    if(txcodigo.getText().isEmpty())
                                    {
                                        try {
                                            if (!ctrlcli.salvarCliente(Integer.parseInt(cid.get("codigo").toString()),cid.get("nome").toString(), cid.get("uf").toString(), txnome.getText(), dpnasc.getValue(), txcpf.getText(), txtelefone.getText(), txemail.getText(), txrua.getText(), Integer.parseInt(txnumero.getText()), txbairro.getText(), cep)) {
                                                JOptionPane.showMessageDialog(null, "Falha na tentativa de salvar cliente");
                                            }
                                            else
                                                habilita("n");
                                            
                                        } catch (Exception e) {System.out.println(e.getMessage());
                                        }
                                    }
                                    else
                                    {
                                        if(!ctrlcli.alterarCliente(Integer.parseInt(txcodigo.getText()),txnome.getText(), dpnasc.getValue(), txcpf.getText(), txtelefone.getText(), txemail.getText(), txrua.getText(), Integer.parseInt(txnumero.getText()), txbairro.getText(), cep,Integer.parseInt(cid.get("codigo").toString()),cid.get("nome").toString(), cid.get("uf").toString()))
                                            JOptionPane.showMessageDialog(null, "Falha na tentativa de salvar cliente");
                                        else
                                            habilita("n");
                                    }
                                    
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Informe o logradouro");
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Informe o bairro");
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Informe a cidade");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Informe o CEP");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Informe o telefone");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "CPF inválido");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Nome inválido");
        }
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) 
    {
        habilita("n");
    }

    @FXML
    private void clkBtVerificar(ActionEvent event)
    {
        
    }

    @FXML
    private void clkBtPesquisar(ActionEvent event) 
    {
        CtrlCliente ctrlcli = CtrlCliente.obterInstancia();
        if(rbtnome.isSelected())
            tvcliente.setItems(ctrlcli.buscar(txpesquisar.getText(),""));
        else
            ctrlcli.buscar("",txpesquisar.getText());
        if(!tvcliente.getItems().isEmpty())
            btalterar.setDisable(false);
    }

    @FXML
    private void clkBtSelecionar(ActionEvent event) 
    {
        if(!tvcliente.getSelectionModel().isEmpty())
        {
            txcodigo.setText(tvcliente.getSelectionModel().getSelectedItem().get("codigo").toString());
            txnome.setText(tvcliente.getSelectionModel().getSelectedItem().get("nome").toString());
            dpnasc.setValue(LocalDate.parse(tvcliente.getSelectionModel().getSelectedItem().get("dtnasc").toString()));
            txcpf.setText(tvcliente.getSelectionModel().getSelectedItem().get("cpf").toString());
            txtelefone.setText(tvcliente.getSelectionModel().getSelectedItem().get("telefone").toString());
            txemail.setText(tvcliente.getSelectionModel().getSelectedItem().get("email").toString());
            txcep.setText(tvcliente.getSelectionModel().getSelectedItem().get("cep").toString());
            String cidade = tvcliente.getSelectionModel().getSelectedItem().get("cidade").toString();
            Platform.runLater(new Thread(()->{
                int i=0;
                while(i<cbcidade.getItems().size() && !cbcidade.getItems().get(i).equals(cidade))
                    i++;
                if(i<cbcidade.getItems().size())
                    cbcidade.getSelectionModel().select(i);
                else
                    cbcidade.getSelectionModel().clearSelection();
            }));
            txbairro.setText(tvcliente.getSelectionModel().getSelectedItem().get("bairro").toString());
            txrua.setText(tvcliente.getSelectionModel().getSelectedItem().get("rua").toString());
            txnumero.setText(tvcliente.getSelectionModel().getSelectedItem().get("numero").toString());
            habilita("v");
            tvcliente.getItems().clear();
        }
    }
    
}
