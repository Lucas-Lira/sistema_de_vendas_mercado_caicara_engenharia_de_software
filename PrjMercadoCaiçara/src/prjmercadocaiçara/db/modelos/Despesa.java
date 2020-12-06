
package prjmercadocaiçara.db.modelos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.DespesaBD;

public class Despesa {
    
    private int id;
    private int diavencto;
    private String descricao;
    private LocalDate dtemissao;
    private char status;
    private TipoDespesa tpdespesa;
    private List<Parcela> parcelas;

    public Despesa(int id, int diavencto, String descricao, LocalDate dtemissao, char status, TipoDespesa tpdespesa) {
        this.id = id;
        this.diavencto = diavencto;
        this.descricao = descricao;
        this.dtemissao = dtemissao;
        this.status = status;
        this.tpdespesa = tpdespesa;
        parcelas = new ArrayList<>();
    }

    public Despesa(int diavencto, String descricao, LocalDate dtemissao, char status, TipoDespesa tpdespesa) {
        this(0,diavencto,descricao,dtemissao,status,tpdespesa);
    }
    
    public Despesa()
    {
        this(0,0,"",null,'a',null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiavencto() {
        return diavencto;
    }

    public void setDiavencto(int diavencto) {
        this.diavencto = diavencto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtemissao() {
        return dtemissao;
    }

    public void setDtemissao(LocalDate dtemissao) {
        this.dtemissao = dtemissao;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public TipoDespesa getTpdespesa() {
        return tpdespesa;
    }

    public void setTpdespesa(TipoDespesa tpdespesa) {
        this.tpdespesa = tpdespesa;
    }
    
    public List<Parcela> getParcelas()
    {
        return parcelas;
    }
    
    public void addParcela(Parcela parc)
    {
        parcelas.add(parc);
    }
    
    public void rmvParcela(int pos)
    {
        parcelas.remove(pos);
    }
    
    @Override
    public String toString()
    {
        return descricao;
    }
    
    static public boolean salvar(Despesa desp)
    {
        return DespesaBD.salvarDespesa(desp);
    }
    
    static public boolean quitar(int despcod)
    {
        return DespesaBD.quitarDespesa(despcod);
    }
    
    static public List<Despesa> buscarDespesa(int tpdespcod)
    {
        return DespesaBD.buscarDespesa(tpdespcod);
    }
    
    static public boolean possuiParcelaNaoPaga(int codigo)
    {
        return DespesaBD.verificarExistenciaParcelaNaoPaga(codigo);
    }
}
