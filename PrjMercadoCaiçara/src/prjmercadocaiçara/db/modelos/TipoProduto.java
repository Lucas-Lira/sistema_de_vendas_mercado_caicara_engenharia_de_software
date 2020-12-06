
package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class TipoProduto {
    private int id;
    private String descricao;

    public TipoProduto()
    { this(0, ""); }
    
    public TipoProduto(String descricao)
    { this(0, descricao); }
    
    public TipoProduto(int id, String descricao)
    {
        this.id = id;
        this.descricao = descricao;
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
    public String toString() {
        return ""+descricao;
    }
    
    // Métodos de acesso ao banco
    public boolean salvar()
    {
        String sql = "insert into tipo_produto (tp_descricao) values ('$1')";
        sql = sql.replace("$1", descricao);
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar()
    {
        String sql = "update tipo_produto set tp_descricao = '$1' where tp_cod = "+id;
        sql = sql.replace("$1", descricao);
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(){
        return Banco.getCon().manipular("delete from tipo_produto where tp_cod = "+id);
    }
    
    public List get(String filtro)
    {
        List<TipoProduto> tipo = new ArrayList();
        String sql = "select * from tipo_produto";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
                tipo.add(new TipoProduto(rs.getInt("tp_cod"), rs.getString("tp_descricao")));
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return tipo;
    }
}
