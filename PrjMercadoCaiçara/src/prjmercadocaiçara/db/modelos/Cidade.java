package prjmercadocaiçara.db.modelos;

import java.util.List;
import prjmercadocaiçara.db.persistencia.CidadeBD;

public class Cidade
{
    private int id;
    private String nome;
    private Estado estado;
    static private CidadeBD cbd;

    public Cidade()
    { this(0, "", null); }

    public Cidade(String nome, Estado estado)
    { this(0, nome, estado); }

    public Cidade(int id, String nome, Estado estado)
    {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        cbd = new CidadeBD();
    }

    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public String getNome()
    { return nome; }

    public void setNome(String nome)
    { this.nome = nome; }

    public Estado getEstado()
    { return estado; }

    public void setEstado(Estado estado)
    { this.estado = estado; }

    @Override
    public String toString()
    { return nome + "/" + estado.getSigla(); }
    
    static public List<Cidade> buscarTodas()
    {
        return cbd.get();
    }
    
    static public Cidade buscar(String nome, String uf)
    {
        return cbd.buscarCidade(nome, uf);
    }
}