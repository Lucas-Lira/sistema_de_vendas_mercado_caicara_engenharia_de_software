
package prjmercadocaiçara.db.controladoras;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import prjmercadocaiçara.db.modelos.Cidade;

public class CtrlEndereco {
    
    private static CtrlEndereco instancia;
    
    private CtrlEndereco(){}
    
    public static CtrlEndereco obterInstancia()
    {
        if(instancia == null)
            instancia = new CtrlEndereco();
        return instancia;
    }
    
    
    public ObservableList<Map> carregarCidades()
    {
        List<Cidade> list;
        ObservableList<Map> allData = FXCollections.observableArrayList();
        list = Cidade.buscarTodas();
        list.forEach((c) -> {
            HashMap<String, String> data = new HashMap<>();
            data.put("codigo", ""+c.getId());
            data.put("nome", c.getNome());
            data.put("uf", c.getEstado().getSigla());
            allData.add(data);
        });
        return allData;
    }
    
    public String buscarCidade(String nome, String uf)
    {
        return Cidade.buscar(nome, uf).toString();
    }
    
}
