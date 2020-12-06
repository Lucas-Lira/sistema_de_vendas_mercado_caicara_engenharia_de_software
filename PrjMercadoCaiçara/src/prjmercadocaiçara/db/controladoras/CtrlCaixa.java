package prjmercadocaiçara.db.controladoras;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import prjmercadocaiçara.db.modelos.Caixa;
import prjmercadocaiçara.db.modelos.Funcionario;
import prjmercadocaiçara.util.facade.DisplayComboBox;

public class CtrlCaixa
{
    private Caixa caixa_atual = null;
    private ArrayList<Caixa> list_caixa = null;
    private ArrayList<Funcionario> list_fun = null;
    private Funcionario fun_atual = null;
    
    // ------------------------------------------------------------------------------------------------------------------
    // PARA A REALIZAÇÃO DA REVALIDAÇÃO DOS DADOS QUE SERÃO EFETIVADOS:
    // ------------------------------------------------------------------------------------------------------------------
    private int codigo_f;
    // ------------------------------------------------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO SINGLETON:
    // ------------------------------------------------------------------------------------------------------------------
    private static CtrlCaixa ctrl_caixa = null;
    
    public static CtrlCaixa instanciarCtrlCaixa()
    {
        if(ctrl_caixa == null)
            ctrl_caixa = new CtrlCaixa();
        
        return ctrl_caixa;
    }
    
    private CtrlCaixa()
    {
        list_caixa =  new ArrayList();
        list_fun = new ArrayList();
        clearDadosAtuais();
        codigo_f = (-1);
    }
    // ------------------------------------------------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public void clearDadosAtuais()
    {
        clearCaixaAtual();
        fun_atual = new Funcionario();
    }
    
    public void clearCaixaAtual()
    {
        caixa_atual = new Caixa();
    }
    
    public void carregarFuncionarioAtual(int id)
    {
        ArrayList<Funcionario> list = (ArrayList<Funcionario>) Funcionario.buscaFuncionarioFiltro(1, "" + id);
        if(!list.isEmpty())
            fun_atual = list.get(0);
    }
    
    public void carregarFuncionarioAtualIdList(int id)
    {
        boolean achou = false;
        for(int i = 0; i < list_fun.size() && !achou; i++)
            if(list_fun.get(i).getId() == id)
            {
                fun_atual = list_fun.get(i);
                achou = true;
            }
    }
    
    public void carregarComboBoxFuncionario(ComboBox cbfuncionario, int filtro, String dado)
    {
        cbfuncionario.getItems().clear();
        Funcionario fun = null;
        DisplayComboBox obj = null;
        
        list_fun = (ArrayList<Funcionario>) Funcionario.buscaFuncionarioFiltro(filtro, dado);
        for(int i = 0; i < list_fun.size(); i++)
        {
            fun = list_fun.get(i);
            obj = new DisplayComboBox("Nome: " + fun.getNome() + "; CPF: " + fun.getCpf(), "" + fun.getId());
            
            cbfuncionario.getItems().add(obj);
        }
    }
    
    public void carregarComboBoxOperacao(ComboBox cboperacao)
    {
        cboperacao.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("ABRIR CAIXA", "0");    
        cboperacao.getItems().add(obj);
        
        obj = new DisplayComboBox("FECHAR CAIXA", "1");    
        cboperacao.getItems().add(obj);
    }
    
    public int getCodigoFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getId();
        return (-1);
    }
    
    public int getNivelFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getNivel();
        return (-1);
    }
    
    public boolean validaCaixa(LocalDate data, int fun_cod, double vinicial, double vtotentradas, double vtotsaidas, 
            LocalDate data_f, boolean fechamento)
    {
        boolean validou =  false;
        
        if(data != null && !data.isBefore(LocalDate.now()) && !data.isAfter(LocalDate.now()))
        {
            if(fun_cod > 0)
            {
                if(vinicial >= 0)
                {
                    if(vtotentradas >= 0)
                    {
                        if(vtotsaidas >= 0)
                        {
                            if((vinicial + vtotentradas) >= vtotsaidas)
                            {
                                if((!fechamento && data_f == null) || (fechamento && data_f != null && 
                                        data_f.isEqual(LocalDate.now())))
                                    validou = true;
                            }
                        }
                    }
                }
            }
        }
        
        return validou;
    }
    
    public void carregarCaixaAtual(LocalDate data, int fun_cod, double vinicial, double vtotentradas, double vtotsaidas, LocalDate datafechamento)
    {
        caixa_atual = new Caixa(data, fun_cod, vinicial, vtotentradas, vtotsaidas, datafechamento);
    }
    
    public boolean abrirCaixa()
    {
        if(caixa_atual.getId() > 0)
        {
            if(validaCaixa(caixa_atual.getData(), caixa_atual.getId(), caixa_atual.getValorini(), caixa_atual.getTotalentradas(), 
                    caixa_atual.getTotalsaidas(), caixa_atual.getDatafechamento(), false))
                return Caixa.salvar(caixa_atual);
        }
        return false;
    }
    
    public boolean reabrirCaixa()
    {
        if(caixa_atual.getId() > 0)
        {
            if(validaCaixa(caixa_atual.getData(), caixa_atual.getId(), caixa_atual.getValorini(), caixa_atual.getTotalentradas(), 
                    caixa_atual.getTotalsaidas(), caixa_atual.getDatafechamento(), true))
                return Caixa.alterar(caixa_atual);
        }
        return false;
    }
    
    public boolean carregarCaixaEFuncionarioAtuais(int id, LocalDate data)
    {
        list_caixa = (ArrayList<Caixa>) Caixa.buscaCaixaFuncionarioEData(id, data);
        if(!list_caixa.isEmpty())
        {
            caixa_atual = list_caixa.get(0);
            
            codigo_f = caixa_atual.getId(); // CARREGAMENTO DO FUN. ATUAL DE MANEIRA LÓGICA APENAS!
            
            return true;
        }
        else
        {
            codigo_f = (-1);
            return false;
        }
    }
    
    public LocalDate getDataAnterior()
    {
        return Caixa.buscaDataAnterior();
    }
    
    public LocalDate getDataCaixaAtual()
    {
        if(caixa_atual != null)
            return caixa_atual.getData();
        return null;
    }
    
    public LocalDate getDataFechamentoCaixaAtual()
    {
        if(caixa_atual != null)
            return caixa_atual.getDatafechamento();
        return null;
    }
    
    public int getIdCaixaAtual()
    {
        if(caixa_atual != null)
            return caixa_atual.getId();
        return (-1);
    }
    
    public double getValorInicialCaixaAtual()
    {
        if(caixa_atual != null)
            return caixa_atual.getValorini();
        return (-1);
    }
    
    public double getValorTotEntradasCaixaAtual()
    {
        if(caixa_atual != null)
            return caixa_atual.getTotalentradas();
        return (-1);
    }
    
    public double getValorTotSaidasCaixaAtual()
    {
        if(caixa_atual != null)
            return caixa_atual.getTotalsaidas();
        return (-1);
    }
    // ------------------------------------------------------------------------------------------------------------------
    
    public ArrayList buscaCaixaFuncionarioEData(int fun_cod, LocalDate data)
    {
       return (ArrayList) Caixa.buscaCaixaFuncionarioEData(fun_cod, data);
    }
    
    public boolean alterarCaixa(Caixa c)
    { return Caixa.alterar(c); }
    
    public void setCaixa_atual(Caixa c)
    { caixa_atual = c; }
}