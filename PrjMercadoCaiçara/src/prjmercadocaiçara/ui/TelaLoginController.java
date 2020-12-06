package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prjmercadocaiçara.db.controladoras.CtrlFuncionario;
import prjmercadocaiçara.db.persistencia.Banco;

public class TelaLoginController implements Initializable
{
    @FXML
    private JFXButton bentrar;
    @FXML
    private JFXButton bsair;
    @FXML
    private JFXTextField txlogin;
    @FXML
    private JFXPasswordField txsenha;
    @FXML
    private Label tituloprincipal;
    @FXML
    private ImageView imagemlogo;
    
    private CtrlFuncionario controladora_fun = null;
    private int fun_cod;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        clearDados();
    }
    
    public void clearDados()
    {
        controladora_fun = CtrlFuncionario.instanciarCtrlFun();
        fun_cod = (-1);
        
        txlogin.clear();
        txsenha.clear();
        
        // ---------------------------------------------------------------------------------------------------------------
        // PARA LOGIN DE DESENVOLVIMENTO:
        // ---------------------------------------------------------------------------------------------------------------
        txlogin.setText("TESTE");
        txsenha.setText("123");
        // ---------------------------------------------------------------------------------------------------------------
    }
    
    public boolean validaDados()
    {
        boolean valida = false;
        if(!txlogin.getText().isEmpty())
        {
            if(!txsenha.getText().isEmpty())
            {
                String nome = txlogin.getText().toUpperCase(), senha = txsenha.getText();
                fun_cod = controladora_fun.buscaCodigoFunLoginSenha(txlogin.getText().toUpperCase(), txsenha.getText());
                
                if(fun_cod != (-1))
                    valida = true;
                else
                {
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Nenhum funcionário com este 'Login' e 'Senha' foi encontrado!");
                    dialogoInfo.showAndWait();
                }
            }
            else
            {
                txsenha.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Senha' é Inválido!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            txlogin.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Login' é Inválido!");
            dialogoInfo.showAndWait();
        }
        return valida;
    }
    
    @FXML
    private void clkEntrar(ActionEvent event)
    {
        try
        {
            if(validaDados())
            {
                Banco.setId_fun(fun_cod);
                int nivel = controladora_fun.getNivelFunAtual();
                if(nivel == (-1))
                    nivel = 2; // DEFAULT
                Banco.setAcesso(nivel);
                //Banco.setFuncionario(fun);
                clearDados();
                
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TelaMenu.fxml")));
                stage.setScene(scene);
                stage.setTitle("Mercado Caiçara");
                stage.setMaximized(true);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                stage.setOnCloseRequest(e->{ System.exit(0); });
            }
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkSair(ActionEvent event)
    { System.exit(0); }
}