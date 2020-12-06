
package prjmercadocaiçara.db.controladoras;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import prjmercadocaiçara.db.modelos.Cidade;
import prjmercadocaiçara.db.modelos.Cliente;
import prjmercadocaiçara.db.modelos.Endereco;
import prjmercadocaiçara.db.modelos.Estado;

public class CtrlCliente 
{
    private Cliente cli;
    private static CtrlCliente instancia;
    private List<Cliente> l;
    
    private CtrlCliente()
    { l = Cliente.buscarPorNome(""); }
    public static CtrlCliente obterInstancia()
    {
        if(instancia == null)
            instancia =  new CtrlCliente();
        return instancia;
    }
    public boolean salvarCliente(int idcidade, String nomecidade, String sigla, String nome, LocalDate dtnasc, String cpf, String telefone, 
            String email, String rua, int numero, String bairro, String cep)
    {
        if(Cliente.salvar(new Cliente(nome,dtnasc,cpf,telefone,email,new Endereco(numero,rua,cep,bairro,new Cidade(idcidade,nomecidade,new Estado(null,sigla))))))
        {
            l = Cliente.buscarPorNome("");
            return true;
        }
        else
            return false;
    }
    
    public boolean alterarCliente(int codigo, String nome, LocalDate dtnasc, String cpf, String telefone, 
            String email, String rua, int numero, String bairro, String cep, int idcidade, String nomecidade, String sigla)
    {
        if(Cliente.alterar(new Cliente(codigo,nome,dtnasc,cpf,telefone,email,new Endereco(numero,rua,cep,bairro,new Cidade(idcidade,nomecidade,new Estado(null,sigla))))))
            {
            l = Cliente.buscarPorNome("");
            return true;
        }
        else
            return false;
    }
    
    public boolean excluirCliente(int cod)
    {
        if(Cliente.deletar(cod))
        {
            l = Cliente.buscarPorNome("");
            return true;
        }
        else
            return false;
    }
    
    public ObservableList<Map> buscar(String nome, String cpf) {
        
        List<Cliente> list;
        ObservableList<Map> allData = FXCollections.observableArrayList();
        if(cpf.isEmpty())
            list = Cliente.buscarPorNome(nome);
        else
            list = Cliente.buscarPorCPF(cpf);
        for(Cliente c : list)
        {
            Map<String, String> dataRow = new HashMap<>();
            dataRow.put("codigo", ""+c.getId());
            dataRow.put("nome", c.getNome());
            dataRow.put("dtnasc", c.getDtnasc().toString());
            dataRow.put("cpf", c.getCpf());
            dataRow.put("telefone", c.getTelefone());
            dataRow.put("email", c.getEmail());
            dataRow.put("rua", c.getEndereco().getRua());
            dataRow.put("numero", ""+c.getEndereco().getNumero());
            dataRow.put("bairro", c.getEndereco().getBairro());
            dataRow.put("cep", c.getEndereco().getCep());
            dataRow.put("cidade", c.getEndereco().getCidade().toString());
            allData.add(dataRow);
        }
        return allData;
    }
    
    public void carregaComboboxCliente(ComboBox c)
    {
        if(!l.isEmpty())
            for (int i = 0; i < l.size(); i++)
                c.getItems().add(l.get(i).getNome());
    }
    
    public void selecionaClienteComboboxId(int id, ComboBox c)
    {
        int i = 0;
        while(i < l.size() && l.get(i).getId() != id)
            i++;
        c.getSelectionModel().select(i);
    }
    
    public int buscaIdClientePorNome(String nome)
    {
        int i = 0;
        
        while(i < l.size() && !l.get(i).getNome().equals(nome))
            i++;
        return l.get(i).getId();
    }
}
