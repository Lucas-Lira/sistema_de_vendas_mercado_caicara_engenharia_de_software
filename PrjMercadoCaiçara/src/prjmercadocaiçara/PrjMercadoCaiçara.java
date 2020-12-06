package prjmercadocaiçara;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import prjmercadocaiçara.db.controladoras.CtrlFuncionario;
import prjmercadocaiçara.db.persistencia.Banco;

public class PrjMercadoCaiçara extends Application
{    
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = null;
        boolean primeiro_acesso = false;
        
        if(Banco.conectar())
        {
            CtrlFuncionario controladora_fun = CtrlFuncionario.instanciarCtrlFun();
            int qtde_fun_adm;
            
            qtde_fun_adm = controladora_fun.buscaFunAdm();
            if(qtde_fun_adm > 0)
                primeiro_acesso = false;
            else
                primeiro_acesso = true;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Erro: " + Banco.getCon().getMensagemErro());
            System.exit(0);
        }
        
        if(primeiro_acesso)
            root = FXMLLoader.load(getClass().getResource("ui/TelaPrimeiroAcesso.fxml"));
        else
            root = FXMLLoader.load(getClass().getResource("ui/TelaLogin.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Mercado Caiçara");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
	stage.setOnCloseRequest(e->{ System.exit(0); });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}