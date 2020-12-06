
package prjmercadocaiçara.db.modelos;

import java.util.List;
import prjmercadocaiçara.db.persistencia.DespesaBD;

public class TipoDespesa {
    
    private int id;
    private String descricao;

    public TipoDespesa(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public TipoDespesa(String descricao) {
        this(0, descricao);
    }
    
    public TipoDespesa()
    {
        this(0,"");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public String toString()
    {
        return descricao;
    }
    
    static public boolean salvar(TipoDespesa tpdesp)
    {
        return DespesaBD.salvarTipoDespesa(tpdesp);
    }
    
    static public boolean deletar(TipoDespesa tpdesp)
    {
        return DespesaBD.deletarTipoDespesa(tpdesp.getId());
    }
    
    static public boolean alterar(TipoDespesa tpdesp)
    {
        return DespesaBD.alterarTipoDespesa(tpdesp);
    }
    
    static public List<TipoDespesa> buscar(String pesquisa)
    {
        return DespesaBD.buscarTipoDespesa(pesquisa);
    }
    
    static public List<TipoDespesa> buscarTodas()
    {
        return DespesaBD.buscarTipoDespesa("");
    }
}
