
package prjmercadocaiçara.db.modelos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import prjmercadocaiçara.db.persistencia.FrequenciaBD;

public class Frequencia {
    private int id;
    private LocalDate data;
    private LocalTime hrinicial,hrfinal;

    public Frequencia()
    {
        
    }
    
    public Frequencia(int id, LocalDate data, LocalTime hrinicial, LocalTime hrfinal) {
        this.id = id;
        this.data = data;
        if(hrinicial!=null)
            this.hrinicial = LocalTime.parse(hrinicial.format(DateTimeFormatter.ofPattern("HH:mm")));
        if(hrfinal!=null)
            this.hrfinal = LocalTime.parse(hrfinal.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHrinicial() {
        return hrinicial;
    }

    public void setHrinicial(LocalTime hrinicial) {
        if(hrinicial!=null)
            this.hrinicial = LocalTime.parse(hrinicial.format(DateTimeFormatter.ofPattern("HH:mm")));
        else
            this.hrinicial = null;
    }

    public LocalTime getHrfinal() {
        return hrfinal;
    }

    public void setHrfinal(LocalTime hrfinal) {
        if(hrfinal!=null)
            this.hrfinal = LocalTime.parse(hrfinal.format(DateTimeFormatter.ofPattern("HH:mm")));
        else
            this.hrfinal = null;
    }
    
    public static boolean salvar(int func_cod, Frequencia freq)
    {
        return FrequenciaBD.salvar(func_cod, freq);
    }
    
    public static boolean alterar(int func_cod, Frequencia freq)
    {
        return FrequenciaBD.alterar(func_cod, freq);
    }
    
    public static List<Frequencia> buscar(int func_cod)
    {
        return FrequenciaBD.buscar(func_cod);
    }
    
    public static boolean deletar(int func_cod, int freq_cod)
    {
        return FrequenciaBD.deletar(func_cod, freq_cod);
    }
}
