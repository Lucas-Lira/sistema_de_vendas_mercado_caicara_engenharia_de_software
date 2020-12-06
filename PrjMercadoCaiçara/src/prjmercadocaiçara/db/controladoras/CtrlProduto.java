
package prjmercadocaiçara.db.controladoras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import prjmercadocaiçara.db.modelos.Produto;
import prjmercadocaiçara.db.modelos.TipoProduto;
import prjmercadocaiçara.util.ProdutoAux;

public class CtrlProduto {
    // ========================================================
    // Padrão SINGLETON
    // ========================================================
    private static CtrlProduto instancia=null;
    private ArrayList<Produto> list;
    private Produto atual;
    
    private CtrlProduto(){
        list = new ArrayList();
        Produto p = new Produto();
        list = (ArrayList<Produto>) p.get("");
    }

    public static CtrlProduto instanciar()
    {
        if(instancia == null)
            instancia = new CtrlProduto();
        return instancia;
    }
    
    public CtrlProduto getInstancia(){
        if(instancia == null)
            instancia = new CtrlProduto();
        return instancia;
    }
    // ========================================================
    
    public boolean gravarProduto(int qtd, String descricao, double vl_unit, TipoProduto tipo)
    {
        atual = new Produto(qtd, descricao, vl_unit, tipo);
        if(atual.salvar())
        {
            list = (ArrayList<Produto>) atual.get("");
            return true;
        }
        else
            return false;
    }
    
    public boolean alterarProduto(int id, int qtd, String descricao, double vl_unit, TipoProduto tipo)
    {
        atual = new Produto(id, qtd, descricao, vl_unit, tipo);
        if(atual.alterar())
        {
            list = (ArrayList<Produto>) atual.get("");
            return true;
        }
        else
            return false;
    }
    
    public boolean apagarProduto(int id)
    {
        Produto p = new Produto();
        p.setId(id);
        
        if(p.apagar())
        {
            list = (ArrayList<Produto>) atual.get("");
            return true;
        }
        else
            return false;
    }
    
    public void getProdutoCarregaTabelaClasseAuxiliar(String filtro, TableView tabela)
    {
        Produto p = new Produto();
        List<Produto> l = p.get(filtro);
        if(!l.isEmpty())
        {
            List<ProdutoAux> l2 = new ArrayList();
            for (int i = 0; i < l.size(); i++)
                l2.add(new ProdutoAux(l.get(i).getId(), l.get(i).getQtd(), l.get(i).getDescricao(), l.get(i).getVl_unit(), l.get(i).getTipo().getId(), l.get(i).getTipo().getDescricao()));
            tabela.setItems(FXCollections.observableArrayList(l2));
        }
    }
    
    public void getProdutoCarregaTabelaClasseProduto(String filtro, TableView tabela)
    {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        
        if(!list.isEmpty())
        {
            for(int i = 0; i < list.size(); i++)
            {
                Map<String, String> dataRow = new HashMap<>();
                dataRow.put("id", ""+list.get(i).getId());
                dataRow.put("descricao", ""+list.get(i).getDescricao());
                dataRow.put("vl_unit", ""+list.get(i).getVl_unit());
                dataRow.put("qtd", ""+list.get(i).getQtd());
                dataRow.put("idtipo", ""+list.get(i).getTipo().getId());
                allData.add(dataRow);
            }
            tabela.setItems(allData);
        }
    }
    
    public List<Produto> getProduto(String filtro)
    {
        return null;
    }
}
