package prjmercadocaiçara.ui;

import prjmercadocaiçara.ui.util.MaskFieldUtil;
import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;
import prjmercadocaiçara.db.controladoras.CtrlFuncionario;
import prjmercadocaiçara.db.persistencia.Banco;
import prjmercadocaiçara.util.AcessoWS;
import prjmercadocaiçara.util.facade.DisplayComboBox;

public class TelaPrimeiroAcessoController implements Initializable
{
    @FXML
    private TabPane tabprincipal;
    @FXML
    private Tab tabCadastroFuncionario;
    @FXML
    private VBox pndados;
    @FXML
    private JFXButton bnovo;
    @FXML
    private JFXButton bconfirmar;
    @FXML
    private JFXButton bcancelar;
    @FXML
    private JFXTextField txcodigo;
    @FXML
    private Label tituloprincipal;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXDatePicker dtnascimento;
    @FXML
    private JFXComboBox<Object> cbnivel;
    @FXML
    private JFXTextField txcpf;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txlogin;
    @FXML
    private ImageView imagem;
    @FXML
    private JFXTextField txlogradouro;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txendnumero;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXComboBox<Object> cbcidade;
    @FXML
    private Label labelimagem;
    @FXML
    private JFXButton buploadimagem;
    @FXML
    private JFXButton bcapturarimagem;
    @FXML
    private JFXButton bcep;
    @FXML
    private Label tituloprincipal1;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private JFXComboBox<Object> cbdiasemana;
    @FXML
    private JFXTextField txhorini;
    @FXML
    private JFXTextField txhorfim;
    @FXML
    private JFXComboBox<Object> cbturno;
    @FXML
    private JFXButton baddhorario;
    @FXML
    private JFXButton bdelhorario;
    @FXML
    private TableView<Object> tabela_horarios;
    @FXML
    private TableColumn<ArrayList<String>, String> coldiasemana;
    @FXML
    private TableColumn<ArrayList<String>, String> colturno;
    @FXML
    private TableColumn<ArrayList<String>, String> colhorini;
    @FXML
    private TableColumn<ArrayList<String>, String> colhorfim;
    @FXML
    private JFXPasswordField txsenha;
    @FXML
    private Label lbdtnascimento;
    
    CtrlFuncionario controladora_funcionario = null;
    private Webcam webcam;
    private boolean camera_status;
    private String login_aux, senha_aux;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        bcancelar.setTooltip(geraMsgB("Cancelar/Sair"));
        bconfirmar.setTooltip(geraMsgB("Confirmar"));
        bnovo.setTooltip(geraMsgB("Novo"));
        baddhorario.setTooltip(geraMsgB("Adicionar Horário"));
        bcapturarimagem.setTooltip(geraMsgB("Capturar Imagem"));
        bcep.setTooltip(geraMsgB("Buscar CEP"));
        bdelhorario.setTooltip(geraMsgB("Deletar Horário"));
        buploadimagem.setTooltip(geraMsgB("Upload de Imagem"));
        
        MaskFieldUtil.numericField(txcodigo);
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.numericField(txendnumero);
        MaskFieldUtil.cepField(txcep);
        
        controladora_funcionario = CtrlFuncionario.instanciarCtrlFun();
        
        controladora_funcionario.inicializarTableViewHorariosFuncionario(tabela_horarios);
        
        carregaComBoxCidade();
        carregaComBoxTurno();
        carregaComBoxDiaSemana();
        carregaComBoxNivel();
        
        estadoEdicao();
        dtnascimento.setValue(LocalDate.now());
        
        webcam =  null;
        camera_status = false;
        inicializaCamera();
        login_aux = ""; senha_aux = "";
    }
    
    public static Tooltip geraMsgB(String msg)
    {
        Tooltip tt = new Tooltip();
        tt.setText(msg);
        tt.setStyle("-fx-font: normal bold 10 Langdon; "
            + "-fx-text-fill: white;"+"-fx-background-color:   #607D8B;");
        return tt;
    }
    
    public void carregaComBoxCidade()
    {
        controladora_funcionario.carregarComboBoxCidade(cbcidade, "");
        if(cbcidade.getItems().isEmpty())
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setContentText("[ ERRO ]: Não foi possível carregar as Cidades!");
            dialogoInfo.showAndWait();
        }
        else
        { cbcidade.getSelectionModel().select(0); }
    }
    
    public void carregaComBoxTurno()
    {
        controladora_funcionario.carregarComboBoxTurno(cbturno, "");
        if(cbturno.getItems().isEmpty())
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setContentText("[ ERRO ]: Não foi possível carregar os Turnos!");
            dialogoInfo.showAndWait();
        }
        else
        {
            cbturno.getSelectionModel().select(0);
            txhorini.setText(controladora_funcionario.getHorarioInicioListTurno(0));
            txhorfim.setText(controladora_funcionario.getHorarioFimListTurno(0));
        }
    }
    
    public void carregaComBoxDiaSemana()
    {
        controladora_funcionario.carregarComboBoxDiaSemana(cbdiasemana);
        cbdiasemana.getSelectionModel().select(0);
    }
    
    public void carregaComBoxNivel()
    {
        controladora_funcionario.carregarComboBoxNivel(cbnivel);
        cbnivel.getSelectionModel().select(0);
    }
    
    public void clearDados()
    {
        txcodigo.clear();
        txnome.clear();
        txcpf.clear();
        txemail.clear();
        txlogin.clear();
        txtelefone.clear();
        txsenha.clear();
        cbnivel.getSelectionModel().select(0);
        clearEndereco();
        clearHorario();
        dtnascimento.setValue(LocalDate.now());
        tabela_horarios.getItems().clear();
        imagem.setImage(null);
    }
    
    public void clearEndereco()
    {
        txlogradouro.clear();
        txbairro.clear();
        txendnumero.clear();
        txcep.clear();
        cbcidade.getSelectionModel().select(0);
    }
    
    public void clearHorario()
    {
        cbdiasemana.getSelectionModel().select(0);
        cbturno.getSelectionModel().select(0);
        txhorini.setText(controladora_funcionario.getHorarioInicioListTurno(0));
        txhorfim.setText(controladora_funcionario.getHorarioFimListTurno(0));
    }
    
    public void estadoInicial()
    {
        clearDados();
        bnovo.requestFocus();
        bnovo.setDisable(false);
        bcancelar.setDisable(false);
        bconfirmar.setDisable(true);
        baddhorario.setDisable(true);
        bdelhorario.setDisable(true);
        bcapturarimagem.setDisable(true);
        bcep.setDisable(true);
        buploadimagem.setDisable(true);
        txcodigo.setDisable(true);
        txnome.setDisable(true);
        txcpf.setDisable(true);
        txemail.setDisable(true);
        txlogin.setDisable(true);
        txtelefone.setDisable(true);
        txsenha.setDisable(true);
        txlogradouro.setDisable(true);
        txbairro.setDisable(true);
        txendnumero.setDisable(true);
        txcep.setDisable(true);
        cbcidade.setDisable(true);
        cbnivel.setDisable(true);
        cbdiasemana.setDisable(true);
        cbturno.setDisable(true);
        txhorini.setDisable(true);
        txhorfim.setDisable(true);
        imagem.setDisable(true);
        dtnascimento.setDisable(true);
        lbdtnascimento.setDisable(true);
        login_aux = ""; senha_aux = "";
        
        camera_status = false;
        controladora_funcionario.clearFunAtual();
    }
    
    public void estadoEdicao()
    {
        bconfirmar.requestFocus();
        bnovo.setDisable(true);
        bcancelar.setDisable(false);
        bconfirmar.setDisable(false);
        baddhorario.setDisable(false);
        bdelhorario.setDisable(false);
        bcapturarimagem.setDisable(false);
        bcep.setDisable(false);
        buploadimagem.setDisable(false);
        txcodigo.setDisable(true);
        txnome.setDisable(false);
        txcpf.setDisable(false);
        txemail.setDisable(false);
        txlogin.setDisable(false);
        txtelefone.setDisable(false);
        txsenha.setDisable(false);
        txlogradouro.setDisable(false);
        txbairro.setDisable(false);
        txendnumero.setDisable(false);
        txcep.setDisable(false);
        cbcidade.setDisable(false);
        cbnivel.setDisable(true);
        cbdiasemana.setDisable(false);
        cbturno.setDisable(false);
        txhorini.setDisable(false);
        txhorfim.setDisable(false);
        imagem.setDisable(false);
        dtnascimento.setDisable(false);
        lbdtnascimento.setDisable(false);
        
        if(!camera_status)
        {
            camera_status = true;
            atualizaImageView();
        }
    }
    
    public boolean validaDados()
    {
        boolean validou = false;
        int pos;
        
        if(txnome.getText().length() >= 3)
        {
            pos = cbnivel.getSelectionModel().getSelectedIndex();
            if(pos >= 0)
            {
                if(txcpf.getText().length() == 14)
                {
                    if(controladora_funcionario.validaCPF(txcpf.getText()))
                    {
                        boolean busca = true;
                        if(!txcpf.isDisable())
                        { busca = !controladora_funcionario.buscaCPFFun(txcpf.getText()); }
                        
                        if(busca)
                        {
                            if(/*txemail.getText().length() > 5*/true)
                            {
                                if(txtelefone.getText().length() >= 13 && txtelefone.getText().length() <= 14)
                                {
                                    if(txlogin.getText().length() > 0)
                                    {
                                        if(txsenha.getText().length() > 0)
                                        {
                                            busca = true;
                                            if(login_aux.equals(txlogin.getText().toUpperCase()) && senha_aux.equals(txsenha.getText())){ }
                                            else
                                            {
                                                int fun_cod = controladora_funcionario.buscaCodigoFunLoginSenha(txlogin.getText().toUpperCase(), txsenha.getText());
                                                if(fun_cod != (-1))
                                                    busca = false;
                                            }
                                            
                                            if(busca)
                                            {
                                                if(dtnascimento.getValue() != null)
                                                {
                                                    LocalDate data = dtnascimento.getValue();
                                                    if(data.isBefore(LocalDate.now()))
                                                        validou = true;
                                                    else
                                                    {
                                                        dtnascimento.requestFocus();
                                                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                                        dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Data Nasc.' é inválido!");
                                                        dialogoInfo.showAndWait();
                                                    }
                                                }
                                                else
                                                {
                                                    dtnascimento.requestFocus();
                                                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                                    dialogoInfo.setContentText("[ ERRO ]: Nenhuma data de nascimento foi selecionada!");
                                                    dialogoInfo.showAndWait();
                                                }
                                            }
                                            else
                                            {
                                                txlogin.requestFocus();
                                                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                                dialogoInfo.setContentText("[ ERRO ]: Este 'Login' e esta 'Senha' já estão em uso no Sistema!");
                                                dialogoInfo.showAndWait();
                                            }
                                        }
                                        else
                                        {
                                            txsenha.requestFocus();
                                            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                            dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Senha' é inválido!");
                                            dialogoInfo.showAndWait();
                                        }
                                    }
                                    else
                                    {
                                        txlogin.requestFocus();
                                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                        dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Login' é inválido!");
                                        dialogoInfo.showAndWait();
                                    }
                                }
                                else
                                {
                                    txtelefone.requestFocus();
                                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                    dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Telefone' é inválido!");
                                    dialogoInfo.showAndWait();
                                }
                            }
                            else
                            {
                                txemail.requestFocus();
                                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'E-mail' é inválido!");
                                dialogoInfo.showAndWait();
                            }
                        }
                        else
                        {
                            txcpf.requestFocus();
                            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                            dialogoInfo.setContentText("[ ERRO ]: Este 'CPF' já está cadastrado no Sistema!");
                            dialogoInfo.showAndWait();
                        }
                    }
                    else
                    {
                        txcpf.requestFocus();
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: Este 'CPF' não é válido pela Rec. Federal!");
                        dialogoInfo.showAndWait();
                    }
                }
                else
                {
                    txcpf.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'CPF' é inválido!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                cbnivel.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Nível' é inválido!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            txnome.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Nome' é inválido!");
            dialogoInfo.showAndWait();
        }
        return validou;
    }
    
    public boolean validaEndereco()
    {
        boolean validou = false;
        int pos = cbcidade.getSelectionModel().getSelectedIndex();
        
        if(pos >= 0)
        {
            if(txlogradouro.getText().length() >= 5)
            {
                if(txbairro.getText().length() >= 5)
                {
                    if(txendnumero.getText().length() > 0 && !txendnumero.getText().equals("0"))
                    {
                        if(txcep.getText().length() == 9)
                            validou = true;
                        else
                        {
                            txcep.requestFocus();
                            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                            dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'CEP' é inválido!");
                            dialogoInfo.showAndWait();
                        }
                    }
                    else
                    {
                        txendnumero.requestFocus();
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'End. Número' é inválido!");
                        dialogoInfo.showAndWait();
                    }
                }
                else
                {
                    txbairro.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Bairro' é inválido!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                txlogradouro.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Logradouro' é inválido!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            cbcidade.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma cidade foi selecionada!");
            dialogoInfo.showAndWait();
        }
        return validou;
    }
    
    public boolean validaHorario()
    {
        boolean validou = false;
        int pos = cbdiasemana.getSelectionModel().getSelectedIndex();
        
        if(pos >= 0)
        {
            pos = cbturno.getSelectionModel().getSelectedIndex();
            if(pos >= 0)
                validou = true;
            else
            {
                cbturno.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Nenhum turno foi selecionado!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            cbdiasemana.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhum dia da semana foi selecionado!");
            dialogoInfo.showAndWait();
        }
        return validou;
    }

    @FXML
    private void clkNovo(ActionEvent event)
    {
        estadoEdicao();
        txnome.requestFocus();
    }
    
    @FXML
    private void clkConfirmar(ActionEvent event)
    {
        if(validaDados())
        {
            if(validaEndereco())
            {
                if(tabela_horarios.getItems().size() > 0)
                {
                    int cid_cod, pos = cbcidade.getSelectionModel().getSelectedIndex();
                    cid_cod = Integer.parseInt(((DisplayComboBox)(cbcidade.getItems().get(pos))).getValueMember());
                    int codend;
                    String cod = "";
                    
                    if(txcodigo.getText().isEmpty())
                    {
                       cod = "0" ;
                       codend = 0;
                    }
                    else
                    {
                        cod = txcodigo.getText();
                        codend = controladora_funcionario.getCodigoEnderecoFunAtual();
                    }

                    int pos2 = cbnivel.getSelectionModel().getSelectedIndex();
                    int nivel = Integer.parseInt(((DisplayComboBox)(cbnivel.getItems().get(pos2))).getValueMember());
                    controladora_funcionario.carregaFunAtual(Integer.parseInt(cod), txnome.getText(), 
                            nivel, txcpf.getText(), txemail.getText(), txtelefone.getText(), txlogin.getText(), 
                            txsenha.getText(), dtnascimento.getValue(), codend, txlogradouro.getText(),  txbairro.getText(), 
                            Integer.parseInt(txendnumero.getText()), controladora_funcionario.removeMascaraCEP(txcep.getText()), 
                            cid_cod);
                    
                    if(controladora_funcionario.salvarFun())
                    {
                        Banco.setId_fun(controladora_funcionario.getUltimoIdFuncionarioSalvo());
                        Banco.setAcesso(1);

                        Alert dialogoInfo2 = new Alert(Alert.AlertType.INFORMATION);
                        dialogoInfo2.setContentText("[ OK ]: O cadastro deste Funcionário foi realizado com Sucesso!");
                        dialogoInfo2.showAndWait();
                        estadoInicial();

                        dialogoInfo2.setContentText("[ AVISO ]: Você será redirecionado para a Tela de Preferencias!");
                        dialogoInfo2.showAndWait();
                        estadoInicial();

                        camera_status = false;
                        if(webcam != null && webcam.isOpen())
                            webcam.close();

                        try
                        {
                            Stage stage = new Stage();
                            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TelaMenu.fxml"))); // TelaParametrizacao
                            stage.setScene(scene);
                            stage.setTitle("Mercado Caiçara");
                            stage.setMaximized(true);
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.showAndWait();
                            stage.setOnCloseRequest(e->{ System.exit(0); });
                            System.exit(0);
                        }
                        catch(Exception e){ System.out.println(e); }
                    }
                    else
                    {
                        Alert dialogoInfo2 = new Alert(Alert.AlertType.ERROR);
                        dialogoInfo2.setContentText("[ ERRO ]: Não foi possível realizar o Cadastro do Funcionário!");
                        dialogoInfo2.showAndWait();
                    }
                }
                else
                {
                    baddhorario.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Nenhum horário foi inserido!");
                    dialogoInfo.showAndWait();
                }
            }
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event)
    {
        camera_status = false;
        if(webcam != null && webcam.isOpen())
            webcam.close();
        System.exit(0);
    }
    
    public void inicializaCamera()
    {
        if(webcam == null)
        {
            new Thread(new Task<Void>()
            {
                @Override
                protected Void call() throws Exception
                {
                    ativarCamera();
                    return null;
                }
            }).start();
        }
    }
    
    public void atualizaImageView()
    {
        if(webcam != null)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    if(webcam.isOpen())
                    {
                        while(camera_status)
                        {
                            try
                            {
                                BufferedImage bimage;
                                bimage = webcam.getImage();
                                WritableImage wimg;
                                wimg = SwingFXUtils.toFXImage(bimage, null);

                                imagem.setImage(wimg);
                                try{ Thread.sleep(150); }catch(Exception ex){ }
                            }
                            catch(Exception e){ }
                        }
                    }
                }
            }.start();
        }
    }

    @FXML
    private void clkUploadImagem(ActionEvent event)
    {
        File arq;
        Image image;
        FileChooser fc;
        fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG", "*.jpeg"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("TODOS", "*.jpeg", "*.jpg", "*.png"));
        fc.setTitle("Abrir Imagem");
        arq = fc.showOpenDialog(null);

        if(arq != null)
        {
            camera_status = false;
            image = new Image(arq.toURI().toString());
            imagem.setImage(image);
		
	    if(imagem != null)
                controladora_funcionario.setImagemFunAtual(SwingFXUtils.fromFXImage(imagem.getImage(), null));
        }
    }

    @FXML
    private void clkCapturarImagem(ActionEvent event)
    {
        if(webcam.isOpen())
        {
           BufferedImage bimage;
           bimage = webcam.getImage();
           WritableImage wimg;
           wimg = SwingFXUtils.toFXImage(bimage, null);
           // aplicar no componente ImageView
           imagem.setImage(wimg);
           camera_status = false;
           
           if(imagem != null)
               controladora_funcionario.setImagemFunAtual(SwingFXUtils.fromFXImage(imagem.getImage(), null));
        }
    }
    
    public void ativarCamera() 
    {
        Platform.runLater(()->{ bcapturarimagem.getScene().setCursor(Cursor.WAIT); });
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320, 240));
        webcam.open();
        Platform.runLater(()->{
            bcapturarimagem.getScene().setCursor(Cursor.DEFAULT);
            if(!bconfirmar.isDisable())
            {
                camera_status = true;
                atualizaImageView();
            }
        });
    }

    @FXML
    private void clkConsultarCep(ActionEvent event)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String ret = AcessoWS.consultaCep(txcep.getText(), "json");
                    JSONObject my_obj = new JSONObject(ret);
                    
                    String str1 = "", str2 = "";
                    str1 = my_obj.getString("bairro");
                    str2 = my_obj.getString("logradouro");
                    
                    txbairro.setText(str1);
                    if(!str2.isEmpty())
                        txlogradouro.setText(my_obj.getString("tipo_logradouro") + ": "+str2);
                    else
                        txlogradouro.setText("");
                    
                    boolean achou = false;
                    boolean sair;
                    for(int i = 0; i < cbcidade.getItems().size() && !achou; i++)
                    {
                        String str = ((DisplayComboBox)(cbcidade.getItems().get(i))).getDisplayMember();
                        
                        sair = false;
                        for(int j = 0; j < str.length() && !sair; )
                        {
                            if(str.charAt(j) == '/')
                            {
                                str = "" + str.subSequence(0, j);
                                sair = true;
                            }
                            else
                                j++;
                        }
                        
                        if(str.toUpperCase().equals(my_obj.getString("cidade").toUpperCase()))
                        {
                            cbcidade.getSelectionModel().select(i);
                            achou = true;
                        }
                    }
                }
                catch(Exception e)
                {
                    bcep.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível pesquisar o CEP!");
                    dialogoInfo.showAndWait();
                }
            }
        });
        Thread.interrupted();
    }

    @FXML
    private void clkAddHorario(ActionEvent event)
    {
        if(validaHorario())
        {
            int dia = cbdiasemana.getSelectionModel().getSelectedIndex();
            int pos = cbturno.getSelectionModel().getSelectedIndex();
            
            if(!controladora_funcionario.addHorario((dia + 1), cbturno.getSelectionModel().getSelectedIndex()))
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Já há um Horário inserido para este dia da Semana!");
                dialogoInfo.showAndWait();
            }
            else
            {
                controladora_funcionario.carregarTableViewHorariosFuncionario(tabela_horarios);
                clearHorario();
            }
        }
    }

    @FXML
    private void clkDelHorario(ActionEvent event)
    {
        if(tabela_horarios.getItems().size() > 0)
        {
            if(tabela_horarios.getSelectionModel().getSelectedIndex() >= 0)
            {
                int pos = tabela_horarios.getSelectionModel().getSelectedIndex();
                if(!controladora_funcionario.delHorario(pos))
                {
                    Alert dialogoInfo2 = new Alert(Alert.AlertType.ERROR);
                    dialogoInfo2.setContentText("[ ERRO ]: Não foi possível remover este Horário!");
                    dialogoInfo2.showAndWait();
                }
                else
                {
                    //tabela_horarios.getItems().remove(pos);
                    controladora_funcionario.carregarTableViewHorariosFuncionario(tabela_horarios);
                }
            }
            else
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setContentText("[ ERRO ]: Nenhum Horário foi selecionado!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setContentText("[ ERRO ]: Não há Horários inseridos!");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void evtTurno(ActionEvent event)
    {
        if(cbturno.getItems().size() > 0)
        {
            int pos = cbturno.getSelectionModel().getSelectedIndex();
            
            txhorini.setText(controladora_funcionario.getHorarioInicioListTurno(pos));
            txhorfim.setText(controladora_funcionario.getHorarioFimListTurno(pos));
        }
    }
}