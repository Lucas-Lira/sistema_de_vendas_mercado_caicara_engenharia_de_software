package prjmercadocaiçara.util;

public class ProdutoAux
{
    private int id, qtd;
    private String descricao;
    private double vl_unit;
    private int idtipo;
    private String descricaotipo;
    private int lote;
    private String fileira, prateleira;

    public ProdutoAux(int id, int qtd, String descricao, double vl_unit, int idtipo, String descricaotipo, int lote, String fileira, String prateleira)
    {
        this.id = id;
        this.qtd = qtd;
        this.descricao = descricao;
        this.vl_unit = vl_unit;
        this.idtipo = idtipo;
        this.descricaotipo = descricaotipo;
        this.lote = lote;
        this.fileira = fileira;
        this.prateleira = prateleira;
    }
    
    public ProdutoAux(int id, int qtd, String descricao, double vl_unit, int idtipo, String descricaotipo)
    { this(id, qtd, descricao, vl_unit, idtipo, descricaotipo, 0, "", ""); }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public int getQtd()
    { return qtd; }

    public void setQtd(int qtd)
    { this.qtd = qtd; }

    public String getDescricao()
    { return descricao; }

    public void setDescricao(String descricao)
    { this.descricao = descricao; }

    public double getVl_unit()
    { return vl_unit; }

    public void setVl_unit(double vl_unit)
    { this.vl_unit = vl_unit; }

    public int getIdtipo()
    { return idtipo; }

    public void setIdtipo(int idtipo)
    { this.idtipo = idtipo; }

    public String getDescricaotipo()
    { return descricaotipo; }

    public void setDescricaotipo(String descricaotipo)
    { this.descricaotipo = descricaotipo; }

    public int getLote()
    { return lote; }

    public void setLote(int lote)
    { this.lote = lote; }

    public String getFileira()
    { return fileira; }

    public void setFileira(String fileira)
    { this.fileira = fileira; }

    public String getPrateleira()
    { return prateleira; }

    public void setPrateleira(String prateleira)
    { this.prateleira = prateleira; }
}