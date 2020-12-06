package prjmercadocaiçara.ui;

import prjmercadocaiçara.ui.util.MaskFieldUtil;
import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Dimension;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import prjmercadocaiçara.db.controladoras.CtrlFuncionario;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import java.time.LocalDate;
import java.util.InputMismatchException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.stage.FileChooser;
import org.json.JSONObject;
import prjmercadocaiçara.util.AcessoWS;
import prjmercadocaiçara.util.facade.DisplayComboBox;

public class TelaFuncionarioController implements Initializable
{
    /* --------------------------------------------------------------------------------------------------------------- */
    // Tela [1]:
    /* --------------------------------------------------------------------------------------------------------------- */
    @FXML
    private TabPane tabprincipal;
    @FXML
    private Tab tabCadastroFuncionario;
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
    private VBox pnpesquisa2;
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
    /* --------------------------------------------------------------------------------------------------------------- */
    // Tela [2]:
    /* --------------------------------------------------------------------------------------------------------------- */
    @FXML
    private Tab tabConsultaFuncionario;
    @FXML
    private Label tituloprincipal2;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private JFXTextField txinformacao;
    @FXML
    private JFXComboBox<Object> cbfiltro;
    @FXML
    private JFXButton bconsultar;
    @FXML
    private JFXButton bselecionar;
    @FXML
    private VBox pndados2;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacodigo;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutanome;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutanivel;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutacpf;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutadtnasc;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutatelefone;
    @FXML
    private TableColumn<ArrayList<String>, String> colconsutaemail;
    @FXML
    private JFXButton bcancelarconsultafunc;
    @FXML
    private TableView<Object> tabelaconsultfunc;
    /* --------------------------------------------------------------------------------------------------------------- */
    // [X] - Todas:
    /* --------------------------------------------------------------------------------------------------------------- */
    CtrlFuncionario controladora_funcionario = null;
    private Webcam webcam;
    private boolean camera_status;
    private boolean flag_ultimo_adm;
    private String login_aux, senha_aux;
            
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        balterar.setTooltip(geraMsgB("Alterar"));
        bapagar.setTooltip(geraMsgB("Apagar"));
        bcancelar.setTooltip(geraMsgB("Cancelar/Sair"));
        bconfirmar.setTooltip(geraMsgB("Confirmar"));
        bconsultar.setTooltip(geraMsgB("Consultar"));
        bnovo.setTooltip(geraMsgB("Novo"));
        bselecionar.setTooltip(geraMsgB("Selecionar"));
        baddhorario.setTooltip(geraMsgB("Adicionar Horário"));
        bcapturarimagem.setTooltip(geraMsgB("Capturar Imagem"));
        bcep.setTooltip(geraMsgB("Buscar CEP"));
        bdelhorario.setTooltip(geraMsgB("Deletar Horário"));
        buploadimagem.setTooltip(geraMsgB("Upload de Imagem"));
        bcancelarconsultafunc.setTooltip(geraMsgB("Cancelar/Sair"));
        
        MaskFieldUtil.numericField(txcodigo);
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.numericField(txendnumero);
        MaskFieldUtil.cepField(txcep);
        
        controladora_funcionario = CtrlFuncionario.instanciarCtrlFun();
        
        controladora_funcionario.inicializarTableViewFuncionario(tabelaconsultfunc);
        controladora_funcionario.inicializarTableViewHorariosFuncionario(tabela_horarios);
        
        carregaComBoxCidade();
        carregaComBoxTurno();
        carregaComBoxDiaSemana();
        carregaComBoxNivel();
        carregaComBoxFiltro();
        
        estadoInicial();
        webcam =  null;
        camera_status = false;
        inicializaCamera();
        flag_ultimo_adm = false;
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
    
    /* --------------------------------------------------------------------------------------------------------------- */
    // [1] TELA CADASTRAR FUNCIONÁRIO:
    /* --------------------------------------------------------------------------------------------------------------- */
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
        clearFiltro();
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
    
    public void clearFiltro()
    {
        txinformacao.clear();
        cbfiltro.getSelectionModel().select(0);
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
        balterar.setDisable(true);
        bapagar.setDisable(true);
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
        flag_ultimo_adm = false;
        
        bcancelarconsultafunc.setDisable(false);
        bconsultar.setDisable(false);
        bselecionar.setDisable(true);
        txinformacao.setDisable(false);
        lbdtnascimento.setDisable(true);
        login_aux = ""; senha_aux = "";
        
        camera_status = false;
        controladora_funcionario.clearFunAtual();
    }
    
    public void estadoEdicao()
    {
        bconfirmar.requestFocus();
        bnovo.setDisable(true);
        balterar.setDisable(true);
        bapagar.setDisable(true);
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
        cbnivel.setDisable(false);
        cbdiasemana.setDisable(false);
        cbturno.setDisable(false);
        txhorini.setDisable(false);
        txhorfim.setDisable(false);
        imagem.setDisable(false);
        dtnascimento.setDisable(false);
        lbdtnascimento.setDisable(false);
        
        bcancelarconsultafunc.setDisable(false);
        bconsultar.setDisable(false);
        bselecionar.setDisable(true);
        txinformacao.setDisable(false);
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
        if(!camera_status)
        {
            camera_status = true;
            atualizaImageView();
        }
    }

    @FXML
    private void clkAlterar(ActionEvent event)
    {
        estadoEdicao();
        txcpf.setDisable(true);
    }

    @FXML
    private void clkApagar(ActionEvent event)
    {
        if(!txcodigo.getText().isEmpty())
        {
            if(flag_ultimo_adm)
            {
                Alert dialogoInfo2 = new Alert(Alert.AlertType.ERROR);
                dialogoInfo2.setContentText("[ ERRO ]: Não é permitido deletar o último Funcionário Administrador!");
                dialogoInfo2.showAndWait();
            }
            else
            {
                if(controladora_funcionario.deletarFuncionario())
                    estadoInicial();
                else
                {
                    Alert dialogoInfo2 = new Alert(Alert.AlertType.ERROR);
                    dialogoInfo2.setContentText("[ ERRO ]: Não foi possível remover o Funcionário!");
                    dialogoInfo2.showAndWait();
                }
            }
        }
        else
        {
            txcodigo.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Não há um código para o Funcionário!");
            dialogoInfo.showAndWait();
        }
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

                    if(flag_ultimo_adm)
                    {
                        if(cbnivel.getSelectionModel().getSelectedIndex() != 0)
                        {
                            Alert dialogoInfo2 = new Alert(Alert.AlertType.ERROR);
                            dialogoInfo2.setContentText("[ ERRO ]: É preciso ter pelo menos um Funcionário Administrador!");
                            dialogoInfo2.showAndWait();
                        }
                        else
                        {
                            if(txcodigo.getText().isEmpty())
                            {
                                if(controladora_funcionario.salvarFun())
                                {
                                    Alert dialogoInfo2 = new Alert(Alert.AlertType.INFORMATION);
                                    dialogoInfo2.setContentText("[ OK ]: O cadastro deste Funcionário foi realizado com Sucesso!");
                                    dialogoInfo2.showAndWait();
                                    estadoInicial();
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
                                if(controladora_funcionario.alterarFun())
                                {
                                    Alert dialogoInfo2 = new Alert(Alert.AlertType.INFORMATION);
                                    dialogoInfo2.setContentText("[ OK ]: O cadastro deste Funcionário foi alterado com Sucesso!");
                                    dialogoInfo2.showAndWait();
                                    estadoInicial();
                                }
                                else
                                {
                                    Alert dialogoInfo2 = new Alert(Alert.AlertType.ERROR);
                                    dialogoInfo2.setContentText("[ ERRO ]: Não foi possível realizar a alteração do cadastro deste Funcionário!");
                                    dialogoInfo2.showAndWait();
                                }
                            }
                        }
                    }
                    else
                    {
                        if(txcodigo.getText().isEmpty())
                        {
                            if(controladora_funcionario.salvarFun())
                            {
                                Alert dialogoInfo2 = new Alert(Alert.AlertType.INFORMATION);
                                dialogoInfo2.setContentText("[ OK ]: O cadastro deste Funcionário foi realizado com Sucesso!");
                                dialogoInfo2.showAndWait();
                                estadoInicial();
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
                            if(controladora_funcionario.alterarFun())
                            {
                                Alert dialogoInfo2 = new Alert(Alert.AlertType.INFORMATION);
                                dialogoInfo2.setContentText("[ OK ]: O cadastro deste Funcionário foi alterado com Sucesso!");
                                dialogoInfo2.showAndWait();
                                estadoInicial();
                            }
                            else
                            {
                                Alert dialogoInfo2 = new Alert(Alert.AlertType.ERROR);
                                dialogoInfo2.setContentText("[ ERRO ]: Não foi possível realizar a alteração do cadastro deste Funcionário!");
                                dialogoInfo2.showAndWait();
                            }
                        }
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
        if(bnovo.isDisable()) //Está em estado de edição
            estadoInicial();
        else
        {
            if(webcam != null && webcam.isOpen())
            	webcam.close();
            camera_status = false;
            estadoInicial();
            rewindTela();
        }
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
    
    /* --------------------------------------------------------------------------------------------------------------- */
    // [1] TELA CONSULTAR FUNCIONÁRIO:
    /* --------------------------------------------------------------------------------------------------------------- */
    public void carregaComBoxFiltro()
    {
        controladora_funcionario.carregarComboBoxFiltroConsulta(cbfiltro);
        cbfiltro.getSelectionModel().select(0);
    }
    
    @FXML
    private void clkConsultar(ActionEvent event)
    {
        int pos = cbfiltro.getSelectionModel().getSelectedIndex();
        String info = txinformacao.getText().toUpperCase();
        boolean vazio = true;
        tabelaconsultfunc.getItems().clear();
        
        if(info.isEmpty())
        {
            if(pos == 1)
            {
                controladora_funcionario.carregarTableViewFuncionario(tabelaconsultfunc, 2, "");
                if(!tabelaconsultfunc.getItems().isEmpty())
                    vazio = false;
            }
        }
        else
            {
                if(pos == (-1))
                {
                    Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                    dialogoInfo.setContentText("[ ERRO ]: Nenhum filtro foi Selecionado!");
                    dialogoInfo.showAndWait();
                }
                else
                {
                    controladora_funcionario.carregarTableViewFuncionario(tabelaconsultfunc, (pos + 1), info);
                    if(!tabelaconsultfunc.getItems().isEmpty())
                        vazio = false;
                }
            }
        
        if(vazio)
        {
            bselecionar.setDisable(true);
            if(pos != -1)
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: Não há dados para esta Consulta!");
                dialogoInfo.showAndWait();
            }
        }
        else
            bselecionar.setDisable(false);
    }

    @FXML
    private void clkSelecionar(ActionEvent event)
    {
        if(tabelaconsultfunc.getItems().size() > 0)
        {
            int pos = tabelaconsultfunc.getSelectionModel().getSelectedIndex();
            if(pos >= 0)
            {
                try
                {
                    int nivel = 2;
                    String login = "", senha = "";
                    clearDados();
                    controladora_funcionario.selecionaFuncionarioLista(pos);
                    clearFiltro();
                    tabelaconsultfunc.getItems().clear();

                    txcodigo.setText("" + controladora_funcionario.getCodigoFunAtual());
                    txnome.setText(controladora_funcionario.getNomeFunAtual());
                    cbnivel.getSelectionModel().select((controladora_funcionario.getNivelFunAtual() - 1));
                    txcpf.setText(controladora_funcionario.getCPFFunAtual());
                    txemail.setText(controladora_funcionario.getEmailFunAtual());
                    txtelefone.setText(controladora_funcionario.getTelefoneFunAtual());
                    txlogin.setText(controladora_funcionario.getLoginFunAtual());
                    login = txlogin.getText();
                    txsenha.setText(controladora_funcionario.getSenhaFunAtual());
                    senha = txsenha.getText();

                    LocalDate data = controladora_funcionario.getDtNascimentoFunAtual();
                    if(data != null)
                        dtnascimento.setValue(data);

                    cbcidade.getSelectionModel().select(controladora_funcionario.getIndiceSelecaoComboBoxCidadesFunAtual());
                    txlogradouro.setText(controladora_funcionario.getLogradouroFunAtual());
                    txbairro.setText(controladora_funcionario.getBairroFunAtual());
                    txendnumero.setText("" + controladora_funcionario.getEnderecoNumFunAtual());
                    txcep.setText(controladora_funcionario.getCEPFunAtual());

                    java.awt.Image img = controladora_funcionario.recuperarImagemFun(Integer.parseInt(txcodigo.getText()));
                    if(img != null)
                    {
                        controladora_funcionario.setImagemFunAtual(img);
                        imagem.setImage(SwingFXUtils.toFXImage((BufferedImage) img, null));
                    }

                    controladora_funcionario.carregarTableViewHorariosFuncionario(tabela_horarios);

                    if(nivel == 1) // É UM ADMINISTRADOR
                    {
                        int qtde = controladora_funcionario.buscaFunAdm();
                        if(qtde == 1)
                            flag_ultimo_adm = true;
                    }

                    login_aux = login; senha_aux = senha;
                    bselecionar.setDisable(true);
                    bnovo.setDisable(true);
                    balterar.setDisable(false);
                    bapagar.setDisable(false);
                    tabprincipal.getSelectionModel().select(0);
                }
                catch(Exception ex)
                {
                    Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível selecionar este Funcionário!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setContentText("[ ERRO ]: Nenhum funcionário foi selecionado!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setContentText("[ ERRO ]: Não há Funcionários consultados!");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void clkCancelarConsultaFunc(ActionEvent event)
    {
        clearFiltro();
        if(webcam != null && webcam.isOpen())
        {
            camera_status = false;
            webcam.close();
        }
        estadoInicial();
        rewindTela();
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
    
    public void desativarCamera()
    {
        if(webcam != null && webcam.isOpen())
            webcam.close();
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
    
    public JFXButton getBtSelecionar()
    {
        return bselecionar;
    }
    public int getIndexFuncionarioSelecionado()
    {
        return tabelaconsultfunc.getSelectionModel().getSelectedIndex();
    }
}