
package prjmercadocaiçara.db.controladoras;

import java.time.LocalDate;
import java.time.Month;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import prjmercadocaiçara.db.modelos.Caixa;
import prjmercadocaiçara.db.modelos.Despesa;
import prjmercadocaiçara.db.modelos.Parcela;
import prjmercadocaiçara.db.modelos.TipoDespesa;
import prjmercadocaiçara.db.persistencia.Banco;
import prjmercadocaiçara.db.persistencia.DespesaBD;
import prjmercadocaiçara.db.persistencia.ParcelaBD;

public class CtrlDespesa {
    
    private static CtrlDespesa instancia;
    private Parcela parcelaatual;
    private CtrlDespesa()
    {}
    
    public static CtrlDespesa obterInstancia()
    {
        if(instancia == null)
            instancia = new CtrlDespesa();
        return instancia;
    }
    
    
    
    //---------EM RELAÇÃO A TIPO DE DESPESAS------------------------
    
    public boolean salvarTipoDespesa(int id, String descricao)
    {
        return TipoDespesa.salvar(new TipoDespesa(id,descricao));
    }
    
    public boolean alterarTipoDespesa(int id, String descricao)
    {
        return TipoDespesa.alterar(new TipoDespesa(id,descricao));
    }
    
    public ObservableList<Map> carregarTiposDespesas()
    {
        ObservableList<Map> data = FXCollections.observableArrayList();
        List<TipoDespesa> list = TipoDespesa.buscarTodas();
        list.forEach((tp)->{
            
            HashMap<String, String> dataRow = new HashMap<>();
            dataRow.put("codigo",""+tp.getId());
            dataRow.put("descricao",tp.getDescricao());
            data.add(dataRow);
        });
        return data;
    }
    
    public ObservableList<Map> buscarTipoDespesa(String pesquisa)
    {
        List<TipoDespesa> list;
        ObservableList<Map> alldata = FXCollections.observableArrayList();
        list = TipoDespesa.buscar(pesquisa);
        for(TipoDespesa tp : list)
        {
            Map<String, String> dataRow = new HashMap<>();
            dataRow.put("codigo",""+tp.getId());
            dataRow.put("descricao",tp.getDescricao());
            alldata.add(dataRow);
        }
        return alldata;
    }
    
    public boolean deletarTipoDespesa(int cod)
    {
        return DespesaBD.deletarTipoDespesa(cod);
    }
    
    //---------EM RELAÇÃO A DESPESAS------------------------
    
    public boolean salvarDespesa(int id, String descricao,double valor, int diavenc, int nparcelas, LocalDate dtemissao, char status, int tpdespcodigo, String tpdespdescricao)
    {
        Despesa d = new Despesa(id,diavenc,descricao,dtemissao,status,new TipoDespesa(tpdespcodigo,tpdespdescricao));
        LocalDate aux = LocalDate.now();
        if(diavenc < aux.getDayOfMonth())
            aux = aux.plusMonths(1);
        for(int i=1; i<=nparcelas; i++)
        {
            try
            {
                aux = aux.withDayOfMonth(diavenc);
            }
            catch(Exception e)
            {
                try
                {
                    aux = aux.withDayOfMonth(aux.getMonth().maxLength());
                }
                catch(Exception r)
                {
                    aux = aux.withDayOfMonth(28);
                }
            }
            d.addParcela(new Parcela(valor, 0, 0, aux, null, descricao, null, null));
            aux = aux.plusMonths(1);
        }
        return Despesa.salvar(d);
    }
    
    
    public ObservableList<Map> buscarDespesa(int tpdespcod)
    {
        ObservableList<Map> alldata = FXCollections.observableArrayList();
        List<Despesa> list = Despesa.buscarDespesa(tpdespcod);
        list.forEach((d) -> {
            HashMap<String, String> data = new HashMap<>();
            data.put("codigo", ""+d.getId());
            data.put("descricao", d.getDescricao());
            alldata.add(data);
        });
        return alldata;
    }
    
    
    public boolean quitarDespesa(int id)
    {
        return Despesa.quitar(id);
    }
    
    /*public boolean deletarDespesa(int id)
    {
        return Despesa.deletar(int id);
    }*/
    
    
    //--------------EM RELAÇÃO A PARCELA--------------
    
    //parcelaatual.get("codigo"), parcelaatual.get("descricao"), parcelaatual.get("valor"), 
    //parcelaatual.get("dtvencto"), parcelaatual.get("juros"), parcelaatual.get("dtpagto"), parcelaatual.get("valorpagto"))
    
    public void setParcelaAtual(int cogigo, String descricao, double valor, 
            LocalDate dtvento, double juros, LocalDate dtpagto, double valorpagto)
    {
        parcelaatual = new Parcela(cogigo,valor,valorpagto,juros,dtvento,dtpagto,descricao,null,null);
    }
    
    public ObservableList<Map> buscarParcela(int codigodespesa, int mes)
    {
        ObservableList<Map> alldata = FXCollections.observableArrayList();
        List<Parcela> listp = Parcela.buscar(codigodespesa,mes);
        listp.forEach((p) -> {
            Map<String, Object> datarow = new HashMap<>();
            datarow.put("codigo", ""+p.getId());
            datarow.put("dtvencto", p.getDt_vencto().toString());
            if(p.getDt_pgto()!=null)
                datarow.put("dtpagto", p.getDt_pgto().toString());
            datarow.put("valor", ""+p.getValor());
            datarow.put("juros", ""+p.getJuros());
            datarow.put("valorpagto", ""+p.getValor_pgto());
            datarow.put("descricao", p.getDescricao());
            alldata.add(datarow);
        });
        return alldata;
    }
    
    
    public boolean alterarParcela(int codigodespesa,int codigoparcela, String descricao, double valor, LocalDate dtvencto)
    {
        Parcela parc = new Parcela();
        parc.setDescricao(descricao);
        parc.setDt_vencto(dtvencto);
        parc.setValor(valor);
        parc.setId(codigoparcela);
        return Parcela.alterar(codigodespesa, parc);
    }
    
    public String pagarParcela(int codigodespesa, double valorpago, double juros)
    {
        String retorno = "";
        if(parcelaatual!=null)
        {
            CtrlCaixa ctrlc = CtrlCaixa.instanciarCtrlCaixa();
            Caixa c = (Caixa)ctrlc.buscaCaixaFuncionarioEData(Banco.getFuncionario().getId(), LocalDate.now()).get(0);
            parcelaatual.setJuros(juros/*txjuros.getText())/100*/);
            parcelaatual.setValor_pgto(parcelaatual.getValor()*(1 + juros));
            if(c.getValorini()+c.getTotalentradas()-c.getTotalsaidas() < parcelaatual.getValor_pgto())
                retorno = "Não há saldo disponível em caixa";
            else
            {
                parcelaatual.setDt_pgto(LocalDate.now());
                parcelaatual.setFuncionario(Banco.getFuncionario());
                parcelaatual.setCaixa(c);
                if(Parcela.pagar(codigodespesa, parcelaatual))
                {
                    c.setTotalsaidas(c.getTotalsaidas()+parcelaatual.getValor_pgto());
                    ctrlc.setCaixa_atual(c);
                    ctrlc.reabrirCaixa();
                }
                else
                    retorno = "Houve um problema na tentativa de efetuar pagamento da parcela";
            }
            if(!Despesa.possuiParcelaNaoPaga(codigodespesa))
                quitarDespesa(codigodespesa);
        }
        return retorno;
    }
    
    public String efetuarBaixa(int codigodespesa, double valorpago, double juros)
    {
        CtrlCaixa ctrlc = CtrlCaixa.instanciarCtrlCaixa();
        Caixa c = (Caixa)ctrlc.buscaCaixaFuncionarioEData(Banco.getFuncionario().getId(), LocalDate.now()).get(0);
        parcelaatual.setJuros(juros/*txjuros.getText())/100*/);
        parcelaatual.setValor_pgto(parcelaatual.getValor()*(1 + juros));
        parcelaatual.setDt_pgto(LocalDate.now());
        parcelaatual.setFuncionario(Banco.getFuncionario());
        parcelaatual.setCaixa(c);
        if(Parcela.pagar(codigodespesa, parcelaatual))
        {
            if(!Despesa.possuiParcelaNaoPaga(codigodespesa))
                quitarDespesa(codigodespesa);
            return "";
        }
        else
            return "Houve um problema na tentativa de efetuar baixa da parcela";
    }
}
