package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import prjmercadocaiçara.db.controladoras.CtrlParametrizacao;
import prjmercadocaiçara.db.modelos.Parametrizacao;
import prjmercadocaiçara.db.persistencia.Banco;

public class TelaMenuController implements Initializable
{
    @FXML
    private Label tituloprincipal;
    @FXML
    private TitledPane titconfiguracoes;
    @FXML
    private Label itcliente;
    @FXML
    private Label itfornecedor;
    @FXML
    private Label itdespesa;
    @FXML
    private Label ittipodedespesa;
    @FXML
    private TitledPane titprodutos;
    @FXML
    private Label itproduto;
    @FXML
    private Label ittipodeproduto;
    @FXML
    private Label itcomprarproduto;
    @FXML
    private Label itvenderproduto;
    @FXML
    private Label itrecebervenda;
    @FXML
    private TitledPane titrelatorios;
    @FXML
    private Label itrelatorioestoque;
    @FXML
    private Label itrelatoriovendasdiarias;
    @FXML
    private Label itrelatoriovencimentodespesa;
    @FXML
    private Label itrelatoriofluxocaixa;
    @FXML
    private Label itrelatoriofaturamento;
    @FXML
    private TitledPane titutilitarios;
    @FXML
    private Label itfuncionario;
    @FXML
    private Label itlancarfrequenciafunc;
    @FXML
    private Label itabrircaixa;
    @FXML
    private Label itfecharcaixa;
    @FXML
    private Label itbackup;
    @FXML
    private Label itrestore;
    @FXML
    private JFXButton btelainicial;
    @FXML
    private JFXButton bsair;
    @FXML
    private ScrollPane pncentral;
    @FXML
    private Label itparametrizacao;
    @FXML
    private Label itconsultarcompra;
    
    public static ScrollPane painel;
    private static ScrollPane auxpn;
    public static int flag;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        auxpn = pncentral;
        painel = pncentral;
        btelainicial.setTooltip(geraMsgB("Voltar para Tela Inicial"));
        bsair.setTooltip(geraMsgB("Sair do Sistema"));
        carregaTelaDescanso();
        
        flag = (-1);
        inicializaTela();
        
        pncentral.contentProperty().addListener((n)->{
            if(pncentral.getContent()==null)
            {
                try
                {
                    Parent tela = FXMLLoader.load(getClass().getResource("TelaFundo.fxml"));
                    pncentral.setContent(tela);
                    this.center(pncentral.getViewportBounds(), tela);
                    pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
                    this.center(newValue, tela);
                    });
                }
                catch(Exception e){}
            }
        });
        
        /*Parametrizacao p = CtrlParametrizacao.buscar();
        if(p != null)
        {
            try
            {
                pncentral.getParent().setStyle("-fx-background-color:"+p.getCor()+"; -fx-font-family: "+p.getFonte()+";");
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else
        {
            pncentral.getParent().setStyle("-fx-background-color: #000000;");
            try
            {
                Parent tela = FXMLLoader.load(getClass().getResource("TelaParametrização.fxml"));
                pncentral.setContent(tela);
                this.center(pncentral.getViewportBounds(), tela);
                pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
                this.center(newValue, tela);
            });
            }
            catch(Exception e)
            {
                
            }
        }*/
    }
    
    public static Tooltip geraMsgB(String msg)
    {
        Tooltip tt = new Tooltip();
        tt.setText(msg);
        tt.setStyle("-fx-font: normal bold 10 Langdon; "
            + "-fx-text-fill: white;"+"-fx-background-color:   #607D8B;");
        return tt;
    }
    
    public void inicializaTela()
    {
        boolean inativo = false;
        /* --------------------------------------------------------------------------------------------------------------- */
        // ATIVIDADES COMUNS:
        /* --------------------------------------------------------------------------------------------------------------- */
        itvenderproduto.setDisable(inativo);
        itrecebervenda.setDisable(inativo);
        itabrircaixa.setDisable(inativo);
        itfecharcaixa.setDisable(inativo);
        
        itvenderproduto.setVisible(!inativo);
        itrecebervenda.setVisible(!inativo);
        itabrircaixa.setVisible(!inativo);
        itfecharcaixa.setVisible(!inativo);
        /* --------------------------------------------------------------------------------------------------------------- */
        // RELATÓRIOS:
        /* --------------------------------------------------------------------------------------------------------------- */
        itrelatorioestoque.setDisable(inativo);
        itrelatoriofaturamento.setDisable(inativo);
        itrelatoriofluxocaixa.setDisable(inativo);
        itrelatoriovencimentodespesa.setDisable(inativo);
        itrelatoriovendasdiarias.setDisable(inativo);
        
        itrelatorioestoque.setVisible(!inativo);
        itrelatoriofaturamento.setVisible(!inativo);
        itrelatoriofluxocaixa.setVisible(!inativo);
        itrelatoriovencimentodespesa.setVisible(!inativo);
        itrelatoriovendasdiarias.setVisible(!inativo);
        /* --------------------------------------------------------------------------------------------------------------- */
        // CUSTOMIZAÇÃO:
        /* --------------------------------------------------------------------------------------------------------------- */
        itparametrizacao.setDisable(inativo);
        
        itparametrizacao.setVisible(!inativo);
        
        if(Banco.getAcesso() == 1)
            inativo = false;
        else
            inativo = true;
        /* --------------------------------------------------------------------------------------------------------------- */
        // CRUD's:
        /* --------------------------------------------------------------------------------------------------------------- */
        itcliente.setDisable(inativo);
        itfornecedor.setDisable(inativo);
        itdespesa.setDisable(inativo);
        ittipodedespesa.setDisable(inativo);
        itproduto.setDisable(inativo);
        ittipodeproduto.setDisable(inativo);
        itfuncionario.setDisable(inativo);
        
        itcliente.setVisible(!inativo);
        itfornecedor.setVisible(!inativo);
        itdespesa.setVisible(!inativo);
        ittipodedespesa.setVisible(!inativo);
        itproduto.setVisible(!inativo);
        ittipodeproduto.setVisible(!inativo);
        itfuncionario.setVisible(!inativo);
        /* --------------------------------------------------------------------------------------------------------------- */
        // ATIVIDADES DO ADMINISTRADOR:
        /* --------------------------------------------------------------------------------------------------------------- */
        itlancarfrequenciafunc.setDisable(inativo);
        itbackup.setDisable(inativo);
        itrestore.setDisable(inativo);
        itcomprarproduto.setDisable(inativo);
        itconsultarcompra.setDisable(inativo);
        
        itlancarfrequenciafunc.setVisible(!inativo);
        itbackup.setVisible(!inativo);
        itrestore.setVisible(!inativo);
        itcomprarproduto.setVisible(!inativo);
        itconsultarcompra.setVisible(!inativo);
    }
    
    public void carregaTelaDescanso()
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaFundo.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
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

    @FXML
    private void clkItemCliente(MouseEvent event)
    {
         try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaCliente.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemFornecedor(MouseEvent event)
    {
         try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaFornecedor.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemDespesa(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaLancarPagarDespesa.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemTipoDespesa(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaTipoDespesa.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemProduto(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaProduto.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemTipoProduto(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaTipoProduto.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemComprarProduto(MouseEvent event)
    {
        flag = 0;
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaComprarProdutos.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemVenderProduto(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaVenderProdutos.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }
    
    public static ScrollPane getPanel()
    {
        return auxpn;
    }

    @FXML
    private void clkItemReceberVenda(MouseEvent event)
    {
    }

    @FXML
    private void clkItemRelEstoque(MouseEvent event)
    {
    }

    @FXML
    private void clkItemRelVendasDiarias(MouseEvent event)
    {
    }

    @FXML
    private void clkItemRelVencimentoDespesas(MouseEvent event)
    {
    }

    @FXML
    private void clkItemRelFluxoCaixa(MouseEvent event)
    {
    }

    @FXML
    private void clkItemRelFaturamento(MouseEvent event)
    {
    }

    @FXML
    private void clkItemFuncionario(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaFuncionario.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemLancarFrequenciaFunc(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaRegistrarFrequencia.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemAbrirCaixa(MouseEvent event)
    {
        flag = 0;
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaAbrirEFecharCaixa.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemFecharCaixa(MouseEvent event)
    {
        flag = 1;
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaAbrirEFecharCaixa.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemBackup(MouseEvent event)
    {
    }

    @FXML
    private void clkItemRestore(MouseEvent event)
    {
    }

    @FXML
    private void clkTelaInicial(ActionEvent event)
    { carregaTelaDescanso(); }

    @FXML
    private void clkSair(ActionEvent event)
    { ((Stage)((Node)event.getSource()).getScene().getWindow()).close(); }   

    @FXML
    private void clkItemParametrizacao(MouseEvent event)
    {
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaParametrização.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    @FXML
    private void clkItemConsultarCompra(MouseEvent event)
    {
        flag = 1;
        try
        {
            Parent tela = FXMLLoader.load(getClass().getResource("TelaComprarProdutos.fxml"));
            pncentral.setContent(tela);  // é um scrollpane

            // centralizando a tela no scroolpane
            this.center(pncentral.getViewportBounds(), tela);
            pncentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ System.out.println(e); }
    }

    public static int getFlag()
    { return flag; }
}