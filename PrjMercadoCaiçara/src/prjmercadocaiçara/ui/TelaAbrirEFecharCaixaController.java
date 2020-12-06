package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import prjmercadocaiçara.db.controladoras.CtrlCaixa;
import prjmercadocaiçara.db.persistencia.Banco;
import prjmercadocaiçara.ui.util.MaskFieldUtil;
import prjmercadocaiçara.util.facade.DisplayComboBox;

public class TelaAbrirEFecharCaixaController implements Initializable
{
    @FXML
    private TabPane tabprincipal;
    @FXML
    private Tab tabAbrirEFecharCaixa;
    @FXML
    private VBox pndados;
    @FXML
    private JFXButton bconfirmar;
    @FXML
    private JFXButton bcancelar;
    @FXML
    private JFXTextField txcodigo;
    @FXML
    private JFXComboBox<Object> cbfuncionario;
    @FXML
    private Label tituloprincipal;
    @FXML
    private JFXTextField txvalorinicial;
    @FXML
    private JFXComboBox<Object> cboperacao;
    @FXML
    private Label tituloprincipal2;
    @FXML
    private JFXTextField txvaloratualizado;
    @FXML
    private JFXDatePicker dtdata;
    @FXML
    private Label lbdata;
    @FXML
    private JFXTextField txtotalentradas;
    @FXML
    private JFXTextField txtotalsaidas;
    @FXML
    private JFXTextField txvalorentrada;
    @FXML
    private JFXTextField txvalorretirada;
    @FXML
    private JFXTextField txstatus;
    
    private CtrlCaixa controladora_caixa = null;
    private int flag, codigo_fun;
    boolean flag_fechamento, flag_inicializacao;
    boolean flag_atualiza;
    private Thread thAtualiza = null;
    private LocalDate data_caixa;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        flag_inicializacao = false;
        bcancelar.setTooltip(geraMsgB("Cancelar/Sair"));
        bconfirmar.setTooltip(geraMsgB("Confirmar"));
        
        MaskFieldUtil.numericField(txcodigo);
        MaskFieldUtil.monetaryField(txvalorinicial);
        MaskFieldUtil.monetaryField(txtotalentradas);
        MaskFieldUtil.monetaryField(txtotalsaidas);
        MaskFieldUtil.monetaryField(txvaloratualizado);
        MaskFieldUtil.monetaryField(txvalorentrada);
        MaskFieldUtil.monetaryField(txvalorretirada);
        
        inicializaTela();
        flag_inicializacao = true;
        flag_atualiza = true;
        atualizaTXValorAtualizado();
    }
    
    public void estadoCaixa(boolean desabilita)
    {
        txvalorentrada.setDisable(desabilita);
        txvalorretirada.setDisable(desabilita);
        bconfirmar.setDisable(desabilita);
    }
    
    public void labelStatusCaixa(boolean fechado)
    {
        if(fechado)
        {
            txstatus.setText("FECHADO");
            txstatus.setStyle("-fx-alignment: center; -fx-background-color: black; -fx-text-fill: rgb(255, 255, 255);");
        }
        else
        {
            txstatus.setText("ABERTO");
            txstatus.setStyle("-fx-alignment: center; -fx-background-color: black; -fx-text-fill: rgb(0, 255, 0);");
        }
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
        flag = TelaMenuController.getFlag();
        flag_fechamento = false;
        controladora_caixa = CtrlCaixa.instanciarCtrlCaixa();
        codigo_fun = Banco.getId_fun();
        controladora_caixa.carregarFuncionarioAtual(codigo_fun);
        estadoInicial();
        
        carregaComBoxFuncionario();
        carregaComBoxOperacao();
    }
    
    public void atualizaTXValorAtualizado()
    {
        if(thAtualiza == null || thAtualiza.isInterrupted())
        {
            thAtualiza = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while(flag_atualiza)
                    {
                        Platform.runLater(()->{
                            String valor = "";
                            double vinicial = 0.0, vtotentradas = 0.0, vtotsaidas = 0.0, vatualizado = 0.0, ventrada = 0.0, vsaida = 0.0;

                            valor = corrigeMaskaraDinheiro(txvalorinicial.getText());
                            try { vinicial = Double.parseDouble(valor); }
                            catch(Exception ex2)
                            { vinicial = 0.0; }

                            valor = corrigeMaskaraDinheiro(txtotalentradas.getText());
                            try { vtotentradas = Double.parseDouble(valor); }
                            catch(Exception ex2)
                            { vtotentradas = 0.0; }

                            valor = corrigeMaskaraDinheiro(txtotalsaidas.getText());
                            try { vtotsaidas = Double.parseDouble(valor); }
                            catch(Exception ex2)
                            { vtotsaidas = 0.0; }

                            valor = corrigeMaskaraDinheiro(txvaloratualizado.getText());
                            try { vatualizado = Double.parseDouble(valor); }
                            catch(Exception ex2)
                            { vatualizado = 0.0; }

                            valor = corrigeMaskaraDinheiro(txvalorentrada.getText());
                            try { ventrada = Double.parseDouble(valor); }
                            catch(Exception ex2)
                            { ventrada = 0.0; }

                            valor = corrigeMaskaraDinheiro(txvalorretirada.getText());
                            try { vsaida = Double.parseDouble(valor); }
                            catch(Exception ex2)
                            { vsaida = 0.0; }

                            vatualizado = (vinicial + vtotentradas + ventrada) - (vtotsaidas + vsaida);
                            txvaloratualizado.setText("" + vatualizado + "0");
                        });

                        try{ Thread.sleep(500); }catch(Exception ex){ }
                    }
                }
            });
            
            thAtualiza.start();
        }
    }
    
    public void carregaComBoxFuncionario()
    {
        controladora_caixa.carregarComboBoxFuncionario(cbfuncionario, 2, "");
        
        cbfuncionario.getSelectionModel().selectedItemProperty().addListener(e->
        {
            if(cbfuncionario.getItems().size() > 0)
            {
                int cod, pos_f = cbfuncionario.getSelectionModel().getSelectedIndex();
                cod = Integer.parseInt(((DisplayComboBox)(cbfuncionario.getItems().get(pos_f))).getValueMember());
                txcodigo.setText("" + cod);
            }
            else
                txcodigo.setText("0");
            
            if(flag_inicializacao)
                selecionaCaixa();
        });
        
        if(cbfuncionario.getItems().isEmpty())
        {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setContentText("[ ERRO ]: Não foi possível carregar os Funcionários!");
            dialogoInfo.showAndWait();
        }
        else
        {
            int i, pos = 0, cod = 0, id = controladora_caixa.getCodigoFunAtual();
            boolean achou = false;
            for(i = 0; i < cbfuncionario.getItems().size() && !achou; i++)
            {
                cod = Integer.parseInt(((DisplayComboBox)(cbfuncionario.getItems().get(i))).getValueMember());
                if(id == cod)
                {
                    achou = true;
                    pos = i;
                }
            }
            
            cbfuncionario.getSelectionModel().select(pos);
            txcodigo.setText("" + cod);
            selecionaCaixa();
        }
    }
    
    public void estadoInicial()
    {
        cboperacao.setDisable(true);
        txvalorinicial.setDisable(true);
        
        if(controladora_caixa.getCodigoFunAtual() > 0 && controladora_caixa.getNivelFunAtual() != 1)
        { cbfuncionario.setDisable(true); }
        
        dtdata.setValue(LocalDate.now());
        dtdata.setDisable(true);
        estadoCaixa(true);
        bconfirmar.setDisable(false);
        
        data_caixa = LocalDate.now();
    }
    
    public void clearDados()
    {
        txvalorinicial.setText("0");
        txtotalentradas.setText("0");
        txtotalsaidas.setText("0");
        txvaloratualizado.setText("0");
        txvalorentrada.setText("0");
        txvalorretirada.setText("0");
    }
    
    public void carregaComBoxOperacao()
    {
        controladora_caixa.carregarComboBoxOperacao(cboperacao);
        
        if(flag == 0)
        {
            cboperacao.getSelectionModel().select(0);
            lbdata.setText("Data de Abertura:");
        }
        else
        {
            cboperacao.getSelectionModel().select(1);
            lbdata.setText("Data de Fechamento:");
        }
    }
    
    public String corrigeMaskaraDinheiro(String dado)
    {
        String ret = "";
        boolean decimal = false;
        for(int i = dado.length() - 1; i >= 0; i--)
        {
            if(dado.charAt(i) != ',' && dado.charAt(i) != '.')
                ret += dado.charAt(i);
            else
                if(!decimal)
                {
                    ret += '.';
                    decimal = !decimal;
                }
        }
        dado =  "";
        for(int i = ret.length() - 1; i >= 0; i --)
            dado += ret.charAt(i);
        return dado;
    }
    
    public boolean validaDados()
    {
        boolean validou = false;
        String valor = "";
        Double valor_dentrada = 0.0, valor_dsaida = 0.0, valor_atualizado = 0.0;
        
        if(dtdata.getValue() != null)
        {
            LocalDate data = dtdata.getValue();
            if(!data.isBefore(LocalDate.now()) && !data.isAfter(LocalDate.now()))
            {
                boolean flag_aux = false;
                valor = corrigeMaskaraDinheiro(txvalorentrada.getText());
                try { valor_dentrada = Double.parseDouble(valor); }
                catch(Exception e)
                {
                    flag_aux = true;
                    valor_dentrada = 0.0;
                    txtotalentradas.requestFocus();
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível converter o dado presente no campo 'Valor de Entrada'!");
                    dialogoInfo.showAndWait();
                }
                
                if(!flag_aux)
                {
                    if(valor_dentrada >= 0.0)
                    {
                        valor = corrigeMaskaraDinheiro(txvalorretirada.getText());
                        try { valor_dsaida = Double.parseDouble(valor); }
                        catch(Exception ex)
                        {
                            valor_dsaida = 0.0;
                            txtotalsaidas.requestFocus();
                            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                            dialogoInfo.setContentText("[ ERRO ]: Não foi possível converter o dado presente no campo 'Valor de Retirada'!");
                            dialogoInfo.showAndWait();
                        }
                        
                        if(valor_dsaida >= 0.0)
                        {
                            valor = corrigeMaskaraDinheiro(txvaloratualizado.getText());
                            try { valor_atualizado = Double.parseDouble(valor); }
                            catch(Exception ex2)
                            { valor_atualizado = 0.0; }
                            
                            if((valor_atualizado + valor_dentrada) - valor_dsaida >= 0)
                                validou = true;
                            else
                            {
                                txtotalsaidas.requestFocus();
                                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                                dialogoInfo.setContentText("[ ERRO ]: O Valor de Retirada é maior do que o saldo disponível em Caixa!");
                                dialogoInfo.showAndWait();
                            }
                        }
                        else
                        {
                            txtotalsaidas.requestFocus();
                            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                            dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Valor de Retirada' é Inválido!");
                            dialogoInfo.showAndWait();
                        }
                    }
                    else
                    {
                        txvalorinicial.requestFocus();
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Valor de Entrada' é Inválido!");
                        dialogoInfo.showAndWait();
                    }
                }
            }
            else
            {
                dtdata.requestFocus();
                Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                dialogoInfo.setContentText("[ ERRO ]: O dado presente no campo 'Data' é Inválido!");
                dialogoInfo.showAndWait();
            }
        }
        else
        {
            dtdata.requestFocus();
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
            dialogoInfo.setContentText("[ ERRO ]: Nenhuma data foi selecionada!");
            dialogoInfo.showAndWait();
        }
        
        return validou;
    }

    @FXML
    private void clkConfirmar(ActionEvent event)
    {
        if(validaDados())
        {
            String valor = "";
            int fun = 0;
            double vinicial = 0.0, vtotentradas = 0.0, vtotsaidas = 0.0, vatualizado = 0.0, ventrada = 0.0, vsaida = 0.0;
            LocalDate datafechamento;

            try { fun = Integer.parseInt(txcodigo.getText()); }
            catch(Exception ex2){ fun = 0; }

            valor = corrigeMaskaraDinheiro(txvalorinicial.getText());
            try { vinicial = Double.parseDouble(valor); }
            catch(Exception ex2){ vinicial = 0.0; }

            valor = corrigeMaskaraDinheiro(txtotalentradas.getText());
            try { vtotentradas = Double.parseDouble(valor); }
            catch(Exception ex2){ vtotentradas = 0.0; }

            valor = corrigeMaskaraDinheiro(txtotalsaidas.getText());
            try { vtotsaidas = Double.parseDouble(valor); }
            catch(Exception ex2){ vtotsaidas = 0.0; }

            valor = corrigeMaskaraDinheiro(txvaloratualizado.getText());
            try { vatualizado = Double.parseDouble(valor); }
            catch(Exception ex2){ vatualizado = 0.0; }

            valor = corrigeMaskaraDinheiro(txvalorentrada.getText());
            try { ventrada = Double.parseDouble(valor); }
            catch(Exception ex2){ ventrada = 0.0; }

            valor = corrigeMaskaraDinheiro(txvalorretirada.getText());
            try { vsaida = Double.parseDouble(valor); }
            catch(Exception ex2){ vsaida = 0.0; }
            
            if(flag == 0)
            {
                /* --------------------------------------------------------------------------------------------------------------- */
                // ABERTURA DE CAIXA
                /* --------------------------------------------------------------------------------------------------------------- */
                if(!flag_fechamento)
                {
                    vinicial = (vinicial + vtotentradas + ventrada) - (vtotsaidas + vsaida);
                    controladora_caixa.carregarCaixaAtual(LocalDate.now(), fun, vinicial, 0.0, 0.0, null);
                    
                    if(!controladora_caixa.abrirCaixa())
                    {
                        labelStatusCaixa(true);
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: Não foi possível abrir este Caixa!");
                        dialogoInfo.showAndWait();
                    }
                    else
                    {
                        flag_atualiza = false;
                        estadoCaixa(true);
                        labelStatusCaixa(false);
                    }
                }
                else
                {
                    vtotentradas += ventrada;
                    vtotsaidas += vsaida;
                    controladora_caixa.carregarCaixaAtual(data_caixa, fun, vinicial, vtotentradas, vtotsaidas, null);
                    
                    if(!controladora_caixa.reabrirCaixa())
                    {
                        labelStatusCaixa(true);
                        Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                        dialogoInfo.setContentText("[ ERRO ]: Não foi possível reabrir este Caixa!");
                        dialogoInfo.showAndWait();
                    }
                    else
                    {
                        flag_atualiza = false;
                        estadoCaixa(true);
                        labelStatusCaixa(false);
                    }
                }
            }
            else
            {
                /* --------------------------------------------------------------------------------------------------------------- */
                // FECHAMENTO DE CAIXA
                /* --------------------------------------------------------------------------------------------------------------- */
                vtotentradas += ventrada;
                vtotsaidas += vsaida;
                controladora_caixa.carregarCaixaAtual(data_caixa, fun, vinicial, vtotentradas, vtotsaidas, LocalDate.now());
                    
                if(controladora_caixa.reabrirCaixa())
                {
                    estadoCaixa(true);
                    labelStatusCaixa(true);
                }
                else
                {
                    labelStatusCaixa(false);
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
                    dialogoInfo.setContentText("[ ERRO ]: Não foi possível fechar este Caixa!");
                    dialogoInfo.showAndWait();
                }
            }
        }
    }

    @FXML
    private void clkCancelar(ActionEvent event)
    {
        if(thAtualiza != null || flag_atualiza)
        {
            flag_atualiza = false;
            thAtualiza.interrupt();
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
            this.center(newValue, tela);
            });
        }
        catch(Exception e){ }
    } 

    public void carregaDados()
    {
        double vini = 0.0, vtotentradas = 0.0, vtotsaidas = 0.0, vatualizado = 0.0;
        
        vini = controladora_caixa.getValorInicialCaixaAtual();
        vtotentradas = controladora_caixa.getValorTotEntradasCaixaAtual();
        vtotsaidas = controladora_caixa.getValorTotSaidasCaixaAtual();
        vatualizado =  (vini + vtotentradas) - vtotsaidas;
        
        txvalorinicial.setText("" + vini + "0");
        txtotalentradas.setText("" + vtotentradas + "0");
        txtotalsaidas.setText("" + vtotsaidas + "0");
        txvaloratualizado.setText("" + vatualizado + "0");
        
        data_caixa = controladora_caixa.getDataCaixaAtual();
    }
    
    public void selecionaCaixa()
    {
        boolean executa = true, carregou = false;
        flag_fechamento = false;
        LocalDate data = dtdata.getValue(), data_anterior = null;
        clearDados();
        
        if(flag == 0)
        {
            /* --------------------------------------------------------------------------------------------------------------- */
            // ABERTURA DE CAIXA
            /* --------------------------------------------------------------------------------------------------------------- */
            labelStatusCaixa(executa);
            estadoCaixa(!executa);
            carregou = controladora_caixa.carregarCaixaEFuncionarioAtuais(Integer.parseInt(txcodigo.getText()), data);
            
            if(carregou)
            {
                if(controladora_caixa.getDataFechamentoCaixaAtual() != null)
                {
                    if(controladora_caixa.getCodigoFunAtual() > 0 && controladora_caixa.getNivelFunAtual() == 1)
                    { flag_fechamento = true; }
                    else
                    {
                        executa = false;
                        carregaDados();
                        estadoCaixa(!executa);
                    }
                }
                else
                {
                    executa = false;
                    carregaDados();
                    estadoCaixa(!executa);
                    labelStatusCaixa(executa);
                }
            }
            
            if(executa)
            {
                if(!carregou)
                {
                    data_anterior = controladora_caixa.getDataAnterior();
                    carregou = controladora_caixa.carregarCaixaEFuncionarioAtuais(Integer.parseInt(txcodigo.getText()), data_anterior);
                }
                
                if(!carregou)
                { controladora_caixa.clearCaixaAtual(); }
                
                carregaDados();
            }
        }
        else
        {
            /* --------------------------------------------------------------------------------------------------------------- */
            // FECHAMENTO DE CAIXA
            /* --------------------------------------------------------------------------------------------------------------- */
            labelStatusCaixa(!executa);
            estadoCaixa(!executa);
            
            if(!controladora_caixa.carregarCaixaEFuncionarioAtuais(Integer.parseInt(txcodigo.getText()), data))
            {
                executa = false;
                controladora_caixa.clearCaixaAtual();
                carregaDados();
                labelStatusCaixa(!executa);
                estadoCaixa(!executa);
            }
            else
            {
                executa = true;
                /* --------------------------------------------------------------------------------------------------------------- */
                txvalorentrada.setDisable(executa);
                /* --------------------------------------------------------------------------------------------------------------- */
                carregaDados();
                
                if(controladora_caixa.getDataFechamentoCaixaAtual() != null)
                {
                    executa = false;
                    carregaDados();
                    estadoCaixa(!executa);
                    labelStatusCaixa(!executa);
                }
            }
        }
    }
    
    @FXML
    private void clkSelecionaData(ActionEvent event)
    {
        if(flag_inicializacao)
            selecionaCaixa();
    }
}