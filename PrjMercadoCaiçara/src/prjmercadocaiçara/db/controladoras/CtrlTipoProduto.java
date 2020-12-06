
package prjmercadocaiçara.db.controladoras;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import prjmercadocaiçara.db.modelos.TipoProduto;

public class CtrlTipoProduto {
    //private static TipoProdutoBD tipoBD;
    
    // ========================================================
    // Padrão SINGLETON
    // ========================================================
    private static CtrlTipoProduto instancia;
    private TipoProduto atual;
    
    private CtrlTipoProduto()
    {}
    
    public static CtrlTipoProduto instanciar(){
        if(instancia == null)
            instancia = new CtrlTipoProduto();
        return instancia;
    }
    
    public CtrlTipoProduto getInstancia(){
        if(instancia == null)
            instancia = new CtrlTipoProduto();
        return instancia;
    }
    
    // ========================================================
    public boolean gravarTipoProduto(String descricao)
    {
        atual = new TipoProduto(descricao);
        return atual.salvar();
    }
    
    public boolean alterarTipoProduto(int id, String descricao)
    {
        atual = new TipoProduto(id, descricao);
        return atual.alterar();
    }
    
    public boolean apagarTipoProduto(int id)
    {
        TipoProduto t = new TipoProduto(id, "");
        return t.apagar();
    }
    
    public void getTipoProduto(String filtro, TableView tabela)
    { 
        ObservableList<Map> allData = FXCollections.observableArrayList();
        TipoProduto t = new TipoProduto();
        List<TipoProduto> l = t.get(filtro);
        
        if(!l.isEmpty())
        {
            for(int i = 0; i < l.size(); i++)
            {
                Map<String, String> dataRow = new HashMap<>();
                dataRow.put("id", ""+l.get(i).getId());
                dataRow.put("descricao", l.get(i).getDescricao());
                allData.add(dataRow);
            }
            tabela.setItems(allData);
        }
    }
   
    public void carregaComboboxTipoProdutos(ComboBox c)
    {
        TipoProduto t = new TipoProduto();
        List<TipoProduto> l = t.get("");
        if(!l.isEmpty())
            c.setItems(FXCollections.observableArrayList(l));
    }
}
