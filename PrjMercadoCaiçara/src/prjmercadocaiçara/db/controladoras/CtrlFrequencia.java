
package prjmercadocaiçara.db.controladoras;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import prjmercadocaiçara.db.modelos.Frequencia;
import prjmercadocaiçara.db.modelos.Funcionario;
import prjmercadocaiçara.db.persistencia.Banco;

public class CtrlFrequencia {
    
    private static CtrlFrequencia instancia;
    private Frequencia frequenciaatual;
            
    private CtrlFrequencia(){}
    public static CtrlFrequencia obterInstancia()
    {
        if(instancia == null)
            instancia = new CtrlFrequencia();
        return instancia;
    }
    
    public boolean salvar(int fun_cod,int freq_cod, LocalDate data, LocalTime hrini, LocalTime hrfim)
    {
        return Frequencia.salvar(fun_cod, new Frequencia(freq_cod,data,hrini,hrfim));
    }
    
    public boolean deletar(int fun_cod, int cod_frequencia)
    {
        return Frequencia.deletar(fun_cod,cod_frequencia);
    }
    
    public ObservableList<Map> buscar(int fun_cod)
    {
        ObservableList<Map> alldata = FXCollections.observableArrayList();
        List<Frequencia> lista = Frequencia.buscar(fun_cod);
        lista.forEach((f)->{
            HashMap freq = new HashMap();
            freq.put("codigo", ""+f.getId());
            freq.put("data", f.getData().toString());
            freq.put("hrinicial", f.getHrinicial());
            freq.put("hrfinal", f.getHrfinal());
            alldata.add(freq);
        });
        return alldata;
    }
    
    public boolean alterar(int func_cod,int freq_cod, LocalDate data, LocalTime hrini, LocalTime hrfim)
    {
        return Frequencia.alterar(func_cod,new Frequencia(freq_cod,data,hrini,hrfim));
    }
}
