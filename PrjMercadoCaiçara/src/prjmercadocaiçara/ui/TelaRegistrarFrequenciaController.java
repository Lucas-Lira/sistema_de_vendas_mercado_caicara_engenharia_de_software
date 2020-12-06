package prjmercadocaiçara.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;
import prjmercadocaiçara.db.controladoras.CtrlFrequencia;
import prjmercadocaiçara.db.controladoras.CtrlFuncionario;

public class TelaRegistrarFrequenciaController implements Initializable {

    @FXML
    private JFXButton btbuscafunc;
    @FXML
    private JFXTextField txfuncionario;
    @FXML
    private ImageView imgvfunc;
    @FXML
    private JFXTextField txcpf;
    @FXML
    private Label ldia;
    @FXML
    private Label lhora;
    @FXML
    private TableView<Map> tvfrequencias;
    @FXML
    private TableColumn<?, ?> coldata;
    @FXML
    private TableColumn<?, ?> colhrentrada;
    @FXML
    private TableColumn<?, ?> colhrsaida;
    @FXML
    private Label lhrinicio;
    @FXML
    private Label lhrfim;
    @FXML
    private JFXButton btregistrar;
    @FXML
    private Label lstatus;
    @FXML
    private Label ldiasemana;
    
    // ------------------------------------------------------------------------------------------------------------------
    private CtrlFuncionario ctrlfun = null;
    // ------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ------------------------------------------------------------------------------------------------------------------
        
        ldia.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() 
                    {
                        Platform.runLater(new Runnable() 
                        {
                            @Override
                            public void run() {
                                LocalTime lt = LocalTime.now();
                                lhora.setText("" + lt.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                                if(lt.getHour() == 00)
                                    ldia.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd de MM de YYYY")));
                            }
                         });
                    }
                }, 0, 500);
        
        coldata.setCellValueFactory(new MapValueFactory("data"));
        colhrentrada.setCellValueFactory(new MapValueFactory("hrinicial"));
        colhrsaida.setCellValueFactory(new MapValueFactory("hrfinal"));
        
        ldiasemana.setText(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt","BR")));
    }    

    @FXML
    private void clkSalvar(ActionEvent event) {
        if(!txfuncionario.getText().isEmpty())
        {
            CtrlFrequencia ctrlfre = CtrlFrequencia.obterInstancia();
            int func = Integer.parseInt(ctrlfun.getDadosFuncionarioAtual().get("codigo").toString());
            if(!tvfrequencias.getItems().isEmpty())
            {
                Map ultimafreq = tvfrequencias.getItems().get(tvfrequencias.getItems().size()-1);
                if(ultimafreq.get("hrfinal") == null)
                {
                    LocalTime hrinicial = LocalTime.parse(ultimafreq.get("hrinicial").toString());
                    if(LocalTime.now().getHour() - hrinicial.getHour() == 0
                            && LocalTime.now().getMinute() - hrinicial.getMinute() < 5)
                    {
                        JOptionPane.showMessageDialog(null, "A frequencia foi descartada pois o funcionário permaneceu menos que 5 minutos.");
                        ctrlfre.deletar(func, tvfrequencias.getItems().size());
                        tvfrequencias.getItems().remove(tvfrequencias.getItems().size()-1);
                        tvfrequencias.refresh();
                    }
                    else
                    {
                        ultimafreq.replace("hrfinal", LocalTime.now().toString().substring(0,5));
                        tvfrequencias.refresh();
                        ctrlfre.alterar(func, tvfrequencias.getItems().size(), LocalDate.parse(ultimafreq.get("data").toString()), LocalTime.parse(ultimafreq.get("hrinicial").toString()), LocalTime.now());
                    }
                }
                else
                {
                    if(ctrlfre.salvar(func, tvfrequencias.getItems().size()+1, LocalDate.now(), LocalTime.now(), null))
                    {
                        HashMap novafreq = new HashMap();
                        novafreq.put("codigo", tvfrequencias.getItems().size());
                        novafreq.put("data", LocalDate.now().toString());
                        novafreq.put("hrinicial", LocalTime.now().toString().substring(0,5));
                        novafreq.put("hrfinal", null);
                        tvfrequencias.getItems().add(novafreq);
                        tvfrequencias.refresh();
                    }
                }
            }
            else
            {
                if(ctrlfre.salvar(func, 1, LocalDate.now(), LocalTime.now(), null))
                {
                    HashMap novafreq = new HashMap();
                    novafreq.put("codigo", 1);
                    novafreq.put("data", LocalDate.now().toString());
                    novafreq.put("hrinicial", LocalTime.now().toString().substring(0,5));
                    novafreq.put("hrfinal", null);
                    tvfrequencias.getItems().add(novafreq);
                    tvfrequencias.refresh();
                }
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Necessita seleção do funcionário");
    }

    @FXML
    private void clkCancelar(ActionEvent event) {
        ScrollPane pncentral =  (ScrollPane)((BorderPane)txcpf.getScene().getRoot()).getCenter();
        pncentral.setContent(null);
    }

    
    private void carregarFuncionario(Map func)
    {
        txfuncionario.setText(func.get("nome").toString());
        txcpf.setText(func.get("cpf").toString());
        imgvfunc.setImage(SwingFXUtils.toFXImage((BufferedImage) func.get("image"), null));
        System.out.println(LocalDate.now().getDayOfWeek().getValue());
        Map hr = ctrlfun.getHorarioFuncionarioAtual();
        if(hr.size()>0)
        {
            lhrinicio.setText(hr.get("hrinicio").toString());
            lhrfim.setText(hr.get("hrfim").toString());
        }
        else
        {
            lstatus.setText("O funcionario não está registrado para trabalhar neste horário");
            lhrinicio.setText("--:--");
            lhrfim.setText("--:--");
            lstatus.setVisible(true);
            btregistrar.setDisable(true);
        } 
        CtrlFrequencia ctrlfreq = CtrlFrequencia.obterInstancia();
        tvfrequencias.setItems(ctrlfreq.buscar((int)func.get("codigo")));
    }
    
    private void limparTela()
    {
        txcpf.clear();
        txfuncionario.clear();
        tvfrequencias.refresh();
        btregistrar.setDisable(false);
        lstatus.setVisible(false);
        lhrinicio.setText("--:--");
        lhrfim.setText("--:--");
        imgvfunc.imageProperty().setValue(null);
    }
    
    @FXML
    private void clkBuscarFuncionario(ActionEvent event) {
        limparTela();
        try
        {
            ScrollPane pncentral =  (ScrollPane)((BorderPane)txcpf.getScene().getRoot()).getCenter();
            Node tela = pncentral.getContent();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("TelaFuncionario.fxml"));
            Parent telafunc = fxml.load();
            TabPane tp = (TabPane)((SplitPane)telafunc).getItems().get(0);
            tp.getTabs().get(0).setDisable(true);
            tp.getSelectionModel().select(1);
            pncentral.setContent(telafunc);
            TelaFuncionarioController telafunctrl = fxml.getController();
            telafunctrl.getBtSelecionar().setOnAction(y->{
                    telafunctrl.desativarCamera();
                    // ------------------------------------------------------------------------------------------------------------------
                    ctrlfun = CtrlFuncionario.instanciarCtrlFun();
                    ctrlfun.selecionaFuncionarioLista(telafunctrl.getIndexFuncionarioSelecionado());
                    /*if(fun_v != null)
                        func = ctrlfun.getFuncionarioLoginSenha(fun_v.getLogin(), fun_v.getSenha());
                    else
                        func = null;*/
                    // ------------------------------------------------------------------------------------------------------------------
                    
                    carregarFuncionario(ctrlfun.getDadosFuncionarioAtual());
                    pncentral.setContent(tela);
            });
        }
        catch(Exception e){}
    }
}
