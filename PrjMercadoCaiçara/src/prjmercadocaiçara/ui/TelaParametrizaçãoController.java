
package prjmercadocaiçara.ui;

import prjmercadocaiçara.ui.util.MaskFieldUtil;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import prjmercadocaiçara.db.controladoras.CtrlEndereco;
import prjmercadocaiçara.db.controladoras.CtrlParametrizacao;
import prjmercadocaiçara.util.AcessoWS;

public class TelaParametrizaçãoController implements Initializable {

    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXColorPicker cpcorfundo;
    @FXML
    private JFXTextField txrazao;
    @FXML
    private JFXComboBox<String> cbfonte;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txsite;
    @FXML
    private JFXTextField txrua;
    @FXML
    private JFXComboBox<Map> cbcidade;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txnumero;
    @FXML
    private ImageView imvlogogrande;
    @FXML
    private ImageView imvlogopequeno;
    private CtrlParametrizacao ctrlpar;
    private boolean primeiroacesso;

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
    public void initialize(URL url, ResourceBundle rb) 
    {
        ctrlpar = CtrlParametrizacao.obterInstancia();
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.numericField(txnumero);
        MaskFieldUtil.maxField(txnumero, 6);
        cbfonte.getItems().addAll((Collection)Font.getFamilies());
        //cbcidade.setItems(CtrlEndereco.obterInstancia().carregarCidades());
        cbfonte.getSelectionModel().selectedItemProperty().addListener((e)->{
            cbfonte.setStyle("-fx-font-family: "+cbfonte.getSelectionModel().getSelectedItem());
            
        });
        cbcidade.setItems(CtrlEndereco.obterInstancia().carregarCidades());
        cbcidade.setConverter(toShowCityInComboBox());
        primeiroacesso = !ctrlpar.buscar();
        if(!primeiroacesso)
        {
            Map parametrizacao = ctrlpar.obterParametrizacao();
            txnome.setText(parametrizacao.get("nome").toString());
            txrazao.setText(parametrizacao.get("razao").toString());
            txtelefone.setText(parametrizacao.get("telefone").toString());
            txemail.setText(parametrizacao.get("email").toString());
            txsite.setText(parametrizacao.get("site").toString());
            txcep.setText(parametrizacao.get("cep").toString());
            txrua.setText(parametrizacao.get("rua").toString());
            txbairro.setText(parametrizacao.get("bairro").toString());
            Platform.runLater(new Thread(()->{
                int i=0;
                while(i<cbcidade.getItems().size() && !(cbcidade.getItems().get(i).get("nome").toString()+"/"+cbcidade.getItems().get(i).get("uf").toString()).equals(parametrizacao.get("cidade").toString()))
                    i++;
                if(i<cbcidade.getItems().size())
                    cbcidade.getSelectionModel().select(i);
            }));
            Platform.runLater(new Thread(()->{
                int i=0;
                while(i<cbfonte.getItems().size() && !cbfonte.getItems().get(i).equals(parametrizacao.get("fonte").toString()))
                    i++;
                if(i<cbfonte.getItems().size())
                    cbfonte.getSelectionModel().select(i);
            }));
            cpcorfundo.setValue(Color.web(parametrizacao.get("cor").toString()));
            txnumero.setText(parametrizacao.get("numero").toString());
            imvlogogrande.setImage(ctrlpar.obterLogoGrande());
            imvlogopequeno.setImage(ctrlpar.obterLogoPequeno());
        }
            
        
        txcep.textProperty().addListener((g)->{
            if(txcep.getText().length()==9)
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
                                while(i<cbcidade.getItems().size() && !cbcidade.getItems().get(i).toString().toUpperCase().equals(cid))
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
        
        /*txcep.textProperty().addListener(t->{
            if(txcep.getText().length() > 8)
                txcep.setText(txcep.getText().substring(0,8));
        });
        
        txnumero.textProperty().addListener(t->{
            if(txnumero.getText().length() > 8)
                txnumero.setText(txnumero.getText().substring(0,8));
        });*/
    }    

    @FXML
    private void clkBtConfirmar(ActionEvent event) 
    {
        if(!txnome.getText().trim().isEmpty())
        {
            if(!txrazao.getText().trim().isEmpty())
            {
                if(!txtelefone.getText().isEmpty())
                {
                    if(!txemail.getText().trim().isEmpty())
                    {
                        //if(!txsite.getText().trim().isEmpty())
                        //{
                            if(!cbcidade.getSelectionModel().isEmpty())
                            {
                                String cep = txcep.getText().replace("-", "");
                                if(cep.length() == 8)
                                {
                                    if(!txbairro.getText().trim().isEmpty())
                                    {
                                        if(!txrua.getText().trim().isEmpty())
                                        {
                                            if(!txnumero.getText().isEmpty())
                                            {
                                                if(!cbfonte.getSelectionModel().isEmpty())
                                                {
                                                    Map cid = cbcidade.getSelectionModel().getSelectedItem();
                                                    boolean result = false;
                                                    if(primeiroacesso)
                                                        result = ctrlpar.salvar(txnome.getText(), txrazao.getText(), txtelefone.getText(), txemail.getText(), 
                                                                txsite.getText(), Integer.parseInt(txnumero.getText()),txrua.getText(),cep,txbairro.getText(),Integer.parseInt(cid.get("codigo").toString()),cid.get("nome").toString(), cid.get("uf").toString(), 
                                                                cbfonte.getSelectionModel().getSelectedItem(), "#"+cpcorfundo.getValue().toString().substring(2,8), imvlogogrande.getImage(), imvlogopequeno.getImage());
                                                    else
                                                        result = ctrlpar.alterar(txnome.getText(), txrazao.getText(), txtelefone.getText(), txemail.getText(), 
                                                                txsite.getText(), Integer.parseInt(txnumero.getText()),txrua.getText(),cep,txbairro.getText(),Integer.parseInt(cid.get("codigo").toString()),cid.get("nome").toString(), cid.get("uf").toString(), 
                                                                cbfonte.getSelectionModel().getSelectedItem(), "#"+cpcorfundo.getValue().toString().substring(2,8), imvlogogrande.getImage(), imvlogopequeno.getImage());
                                                    if(result)
                                                        txbairro.getScene().getRoot().setStyle("-fx-font-family: "+cbfonte.getSelectionModel().getSelectedItem()+"; -fx-background-color: #"+cpcorfundo.getValue().toString().substring(2,8));
                                                }
                                                else
                                                    JOptionPane.showMessageDialog(null, "Informe a fonte");
                                            }
                                            else
                                                JOptionPane.showMessageDialog(null, "Informe o numero");
                                        }
                                        else
                                            JOptionPane.showMessageDialog(null, "Informe a rua");
                                    }
                                    else
                                        JOptionPane.showMessageDialog(null, "Informe o bairro");
                                }
                                else
                                    JOptionPane.showMessageDialog(null, "CEP inválido");
                            }
                            else
                                JOptionPane.showMessageDialog(null, "Informe a cidade");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Informe o email");
                }
                else
                    JOptionPane.showMessageDialog(null, "Telefone inválido");
            }
            else
                JOptionPane.showMessageDialog(null, "Informe a razão social");
        }
        else
            JOptionPane.showMessageDialog(null, "Informe o nome da empresa");
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) 
    {
        ((ScrollPane)((BorderPane)txbairro.getScene().getRoot()).getCenter()).setContent(null);
        //((ScrollPane)txbairro.getParent().getParent().getParent()).setContent(null);
    }

    @FXML
    private void clkBtLogoGrande(ActionEvent event) 
    {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Carregar imagem Logo Grande");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagem (*.jpg)", "*.jpg"), new FileChooser.ExtensionFilter("Imagem (*.png)", "*.png"));
            File arq = fc.showOpenDialog(null);
            if (arq != null) {
                System.out.println(arq.getPath().toString());
                imvlogogrande.setImage(new Image(arq.toURI().toString()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void clkBtLogoPequeno(ActionEvent event) 
    {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Carregar imagem Logo Grande");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagem (*.jpg)", "*.jpg"), new FileChooser.ExtensionFilter("Imagem (*.png)", "*.png"));
            File arq = fc.showOpenDialog(null);
            if (arq != null) {
                imvlogopequeno.setImage(new Image(arq.toURI().toString()));
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void clkBtVerificar(ActionEvent event) 
    {
        
    }
    
}
