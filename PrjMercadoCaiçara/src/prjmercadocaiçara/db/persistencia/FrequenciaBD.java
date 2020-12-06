
package prjmercadocaiçara.db.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.modelos.Frequencia;
import prjmercadocaiçara.db.modelos.Funcionario;

public class FrequenciaBD {
    public static boolean salvar(int cod_func, Frequencia freq)
    {
        String sql = "insert into frequencia(freq_cod,fun_cod,freq_data,freq_hrini) values($1,$2,'$3','$4')";
        sql = sql.replace("$1", ""+freq.getId());
        sql = sql.replace("$2", ""+cod_func);
        sql = sql.replace("$3", freq.getData().toString());
        sql = sql.replace("$4", freq.getHrinicial().format(DateTimeFormatter.ofPattern("HH:mm")));
        return Banco.getCon().manipular(sql);
    }
    
    public static boolean deletar(int cod_func, int cod_frequencia)
    {
        return Banco.getCon().manipular("delete from frequencia where fun_cod="+cod_func+" and freq_cod="+cod_frequencia);
    }
    
    public static List<Frequencia> buscar(int fun_cod)
    {
        List<Frequencia> lista = new ArrayList<>();
        ResultSet rs = Banco.getCon().consultar("select * from frequencia where fun_cod="+fun_cod+" order by freq_cod,freq_data");
        try
        {
            while(rs.next())
            {
                Frequencia fre = new Frequencia();
                fre.setId(rs.getInt("freq_cod"));
                fre.setData(rs.getDate("freq_data").toLocalDate());
                fre.setHrinicial(LocalTime.parse(rs.getString("freq_hrini")));
                if(rs.getString("freq_hrfim")!=null)
                    fre.setHrfinal(LocalTime.parse(rs.getString("freq_hrfim")));
                lista.add(fre);
            }
                
        }
        catch(SQLException e)
        {}
        return lista;
    }
    
    public static boolean alterar(int cod_func,Frequencia freq)
    {
        String sql = "update frequencia set freq_data='$1',freq_hrini='$2',freq_hrfim='$3' where fun_cod="+cod_func+" and freq_cod="+freq.getId();
        sql = sql.replace("$1", freq.getData().toString());
        sql = sql.replace("$2", freq.getHrinicial().format(DateTimeFormatter.ofPattern("HH:mm")));
        sql = sql.replace("$3", freq.getHrfinal().format(DateTimeFormatter.ofPattern("HH:mm")));
        return Banco.getCon().manipular(sql);
    }
}
