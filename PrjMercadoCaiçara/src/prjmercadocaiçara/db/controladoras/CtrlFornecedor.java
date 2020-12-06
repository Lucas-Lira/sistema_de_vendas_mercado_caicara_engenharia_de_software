
package prjmercadocaiçara.db.controladoras;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import prjmercadocaiçara.db.modelos.Fornecedor;

public class CtrlFornecedor {
    
    //private FornecedorBD fBD=null;
    private Fornecedor atual;
    
    // ========================================================
    // Padrão SINGLETON
    // ========================================================
    private static CtrlFornecedor instancia=null;
    
    private CtrlFornecedor()
    { atual = new Fornecedor(); }
   
    public static CtrlFornecedor instanciar()
    {
        if(instancia == null)
            instancia = new CtrlFornecedor();
        return instancia;
    }
    // ======================================================== 
    
    public boolean gravarFornecedor(Date vinculo/*, Date desvinculo*/, String cnpj, String descricao, String telefone, String email)
    {
        atual = new Fornecedor(vinculo, /*desvinculo,*/ cnpj, descricao, telefone, email);
        return atual.salvar();
    }
    
    public boolean alterarFornecedor(int id, Date vinculo, Date desvinculo, String cnpj, String descricao, String telefone, String email)
    {
        if(id > 0)
        {
            atual = new Fornecedor(id, vinculo, desvinculo, cnpj, descricao, telefone, email);
            return atual.alterar();
        }
        else
            return false;
    }
    
    public boolean apagarFornecedor(int id)
    {
        if(id > 0)
        {
            Fornecedor f = new Fornecedor();
            f.setId(id);
            return f.apagar();
        }
        else
            return false;
    }
    
    public void getFornecedorTabelas(String filtro, TableView tabela)
    {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        Fornecedor f = new Fornecedor();
        List<Fornecedor> l = f.get(filtro);
        
        if(!l.isEmpty())
        {
            for(int i = 0; i < l.size(); i++)
            {
                Map<String, String> dataRow = new HashMap<>();
                dataRow.put("id", ""+l.get(i).getId());
                dataRow.put("descricao", l.get(i).getDescricao());
                dataRow.put("cnpj", ""+l.get(i).getCnpj());
                dataRow.put("vinculo", ""+l.get(i).getVinculo().toString());
                if(l.get(i).getDesvinculo() != null)
                    dataRow.put("desvinculo", ""+l.get(i).getDesvinculo().toString());
                else
                    dataRow.put("desvinculo", "");
                dataRow.put("telefone", ""+l.get(i).getTelefone());
                dataRow.put("email", ""+l.get(i).getEmail());
                allData.add(dataRow);
            }
            tabela.setItems(allData);
        }
    }
    
    public static boolean validaCNPJ(String CNPJ)
    { return Fornecedor.validaCnpj(CNPJ); }
}
