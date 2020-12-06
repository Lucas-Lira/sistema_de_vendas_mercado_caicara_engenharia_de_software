package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;
import prjmercadocaiçara.db.persistencia.ClienteBD;
import prjmercadocaiçara.util.observer.Observador;
import prjmercadocaiçara.util.observer.Sujeito;

public class Produto implements Sujeito
{
    private int id, qtd;
    private String descricao;
    private double vl_unit;
    private TipoProduto tipo;
    private Prateleira prateleira;
    private ArrayList<Lote> lotes;
    private ArrayList<Observador> observadores;

    public Produto()
    { this(0, 0, "", 0, null); }

    public Produto(int id, int qtd, String descricao, double vl_unit, TipoProduto tipo, Prateleira prateleira)
    {
        this.id = id;
        this.qtd = qtd;
        this.descricao = descricao;
        this.vl_unit = vl_unit;
        this.tipo = tipo;
        
        this.prateleira = prateleira;
        this.lotes = new ArrayList();
        this.observadores = new ArrayList();
    }

    public Produto(int qtd, String descricao, double vl_unit, TipoProduto tipo)
    {
        this(0, qtd, descricao, vl_unit, tipo);
    }

    public Produto(int id, int qtd, String descricao, double vl_unit, TipoProduto tipo)
    {
        this.id = id;
        this.qtd = qtd;
        this.descricao = descricao;
        this.vl_unit = vl_unit;
        this.tipo = tipo;
        
        this.prateleira = null;
        this.lotes = new ArrayList();
        this.observadores = new ArrayList();
    }
    
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

    public TipoProduto getTipo()
    { return tipo; }

    public void setTipo(TipoProduto tipo)
    { this.tipo = tipo; }

    public Prateleira getPrateleira()
    { return prateleira; }

    public void setPrateleira(Prateleira prateleira)
    { this.prateleira = prateleira; }
    
    public void clearLotes()
    { lotes = new ArrayList(); }
    
    public void addLotes(Lote lote)
    { lotes.add(lote); }
    
    public void setLote(List<Lote> lote)
    {
        this.lotes = (ArrayList<Lote>) lote;
    }
    
    public int buscaLote(Lote lote)
    {
        int pos= -1;
        if(!lotes.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < lotes.size() && !achou; i++)
                if(lotes.get(i) == lote)
                {
                    pos= i;
                    achou= true;
                }
        }
        return pos;
    }
        
    public boolean removeLote(Lote lote)
    {
        int indice= buscaLote(lote);
        if(indice != (-1))
        {
            lotes.remove(indice);
            return true;
        }
        
        return false;
    }

    public ArrayList<Lote> getLotes()
    { return lotes; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO OBSERVER:
    // ------------------------------------------------------------------------------------------------------------------
    public void clearObservadores()
    { observadores = new ArrayList(); }
    
    public int buscaObservador(Object o)
    {
        int pos= -1;
        if(!observadores.isEmpty())
            pos = observadores.indexOf(o);
        return pos;
    }

    public ArrayList<Observador> getObservadores()
    { return observadores; }
    
    @Override
    public void register(Observador o)
    {
        observadores.add(o);
    }

    @Override
    public void remove(Observador o)
    {
        int indice= buscaObservador(o);
        if(indice != (-1))
        {
            apagarObservador(observadores.get(indice));
            observadores.remove(indice);
        }
    }

    @Override
    public void notify(String msg)
    {
        msg = "\n\nTemos o prazer em informar que o Produto [cod.: " + id + "] - " + "'" + descricao.toUpperCase()+ "' "
                + "possui o estoque atual de " + qtd + " unidade(s). Como cadastrado em nosso sistema, estamos informando-o. "
                + "Caso não possua o interesse, desconsiderar este aviso. Se houver, corra para o estabelecimento!!!\n\n";
        
        for(int i = 0; i < observadores.size(); i++)
        {
            observadores.get(i).update(msg);
            remove(observadores.get(i));
        }
    }
    
    public boolean salvarObservadorPorVariaveis(int idproduto, int idcliente)
    {
        if(idproduto > 0 && idcliente > 0)
        {
            String sql="insert into observer_produto (prod_cod, cli_cod) values ('$1', '$2')";
            sql = sql.replace("$1", "" + idproduto);
            sql = sql.replace("$2", "" + idcliente);
            return Banco.getCon().manipular(sql);
        }
        return false;
    }
    
    public boolean salvarObservador(Observador o)
    {
        if(o != null && (o instanceof Cliente))
        {
            String sql="insert into observer_produto (prod_cod, cli_cod) values ('$1', '$2')";
            sql = sql.replace("$1", "" + id);
            sql = sql.replace("$2", "" + ((Cliente)o).getId());
            
            return Banco.getCon().manipular(sql);
        }
        return false;
    }
    
    public boolean apagarObservador(Observador o)
    {
        if(o != null && (o instanceof Cliente))
        {
            String sql="delete from observer_produto where prod_cod = " + id + 
                    " and cli_cod = " + ((Cliente)o).getId();
            return Banco.getCon().manipular(sql);
        }
        return false;
    }
    
    public static List getObservadorCliente(String filtro)
    {
        List <Observador> listobservador = new ArrayList();
        String query = "select * from observer_produto";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList<Cliente> list = null;
            while(rs.next())
            {
                list = (ArrayList<Cliente>) new ClienteBD().buscaPorCodigoF(rs.getInt("cli_cod"));
                if(!list.isEmpty())
                    listobservador.add(list.get(0));
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listobservador;
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public boolean salvar()
    {
        String sql="insert into produto (prod_descricao, prod_valor_unit, prod_qtd, tp_cod) values ('$1', '$2', '$3', '$4')";
        sql = sql.replace("$1", descricao);
        sql = sql.replace("$2", ""+vl_unit);
        sql = sql.replace("$3", ""+qtd);
        sql = sql.replace("$4", String.valueOf(tipo.getId()));
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar()
    {
        String sql = "update produto set prod_descricao = '$1', prod_valor_unit = '$2', prod_qtd = '$3', tp_cod = '$4', prat_cod = '$5' where prod_cod = "+id;
        sql = sql.replace("$1", descricao);
        sql = sql.replace("$2", ""+vl_unit);
        sql = sql.replace("$3", ""+qtd);
        sql = sql.replace("$4", ""+tipo.getId());
        sql = sql.replace("$5", ""+prateleira.getId());
        
        boolean estado = Banco.getCon().manipular(sql);
        if(estado)
        {
            if(qtd > 0)
            {
                ArrayList <Observador> list_observador = (ArrayList <Observador>) getObservadorCliente("prod_cod = " + id);
                if(!list_observador.isEmpty())
                {
                    for(int i = 0; i < list_observador.size(); i++)
                        register(list_observador.get(i));
                    notify("");
                }
            }
        }
        return estado;
    }
    
    public boolean apagar(){
        return Banco.getCon().manipular("delete from produto where prod_cod = "+id);
    }
   
    public List get(String filtro)
    {
        List<Produto> p = new ArrayList();
        String sql = "select * from produto";
        TipoProduto tipo = new TipoProduto();
        List<TipoProduto> t = null;
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            ArrayList list_prateleira = null;
            ArrayList list_lotes = null;
            ArrayList <Observador> list_observador = null;
            Produto prod = null;
            while(rs.next())
            {
                t = tipo.get("tp_cod = "+rs.getInt("tp_cod"));
                list_lotes = (ArrayList<Lote>) Lote.buscaLoteCodigoProduto(rs.getInt("prod_cod"));
                list_observador = (ArrayList<Observador>) getObservadorCliente("prod_cod = " + rs.getInt("prod_cod"));
                
                if(!t.isEmpty())
                {
                    list_prateleira = (ArrayList) Prateleira.buscaPrateleiraCodigo(rs.getInt("prat_cod"));
                    if(!list_prateleira.isEmpty())
                    {
                        prod = new Produto(rs.getInt("prod_cod"), rs.getInt("prod_qtd"), rs.getString("prod_descricao"), 
                                rs.getDouble("prod_valor_unit"), t.get(0), (Prateleira) list_prateleira.get(0));
                        
                        t.remove(0);
                        list_prateleira.remove(0);
                        
                        for(int i = 0; i < list_lotes.size(); i++)
                            prod.addLotes((Lote) list_lotes.get(i));
                        
                        for(int i = 0; i < list_observador.size(); i++)
                            prod.register(list_observador.get(i));
                        
                        p.add(prod);
                    }
                }
            }  
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return p;
    }
    
    public List getComTipo(String filtro)
    {
        List<Produto> p = new ArrayList();
        String sql = "select * from produto inner join tipo_produto on produto.tp_cod = tipo_produto.tp_cod";
        TipoProduto tipo = new TipoProduto();
        List<TipoProduto> t = null;
        
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try
        {
            ArrayList list_prateleira = null;
            ArrayList list_lotes = null;
            ArrayList <Observador> list_observador = null;
            Produto prod = null;
            while(rs.next())
            {
                t = tipo.get("tp_cod = "+rs.getInt("tp_cod"));
                list_lotes = (ArrayList<Lote>) Lote.buscaLoteCodigoProduto(rs.getInt("prod_cod"));
                list_observador = (ArrayList<Observador>) getObservadorCliente("prod_cod = " + rs.getInt("prod_cod"));
                
                if(!t.isEmpty())
                {
                    list_prateleira = (ArrayList) Prateleira.buscaPrateleiraCodigo(rs.getInt("prat_cod"));
                    if(!list_prateleira.isEmpty())
                    {
                        prod = new Produto(rs.getInt("prod_cod"), rs.getInt("prod_qtd"), rs.getString("prod_descricao"), 
                                rs.getDouble("prod_valor_unit"), t.get(0), (Prateleira) list_prateleira.get(0));
                        
                        t.remove(0);
                        list_prateleira.remove(0);
                        
                        for(int i = 0; i < list_lotes.size(); i++)
                            prod.addLotes((Lote) list_lotes.get(i));
                        
                        for(int i = 0; i < list_observador.size(); i++)
                            prod.register(list_observador.get(i));
                        
                        p.add(prod);
                    }
                }
            }  
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return p;
    }
    
    public List getComFileira(int fil_cod)
    {
        List<Produto> p = new ArrayList();
        String sql = "select * from produto inner join (select * from fileira inner join prateleira on "
                + "fileira.fil_cod = prateleira.fil_cod where fileira.fil_cod = " + fil_cod + ") as T "
                + "on produto.prat_cod = T.prat_cod";
        TipoProduto tipo = new TipoProduto();
        List<TipoProduto> t = null;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        
        try
        {
            ArrayList list_prateleira = null;
            ArrayList list_lotes = null;
            ArrayList <Observador> list_observador = null;
            Produto prod = null;
            while(rs.next())
            {
                t = tipo.get("tp_cod = "+rs.getInt("tp_cod"));
                list_lotes = (ArrayList<Lote>) Lote.buscaLoteCodigoProduto(rs.getInt("prod_cod"));
                list_observador = (ArrayList<Observador>) getObservadorCliente("prod_cod = " + rs.getInt("prod_cod"));
                
                if(!t.isEmpty())
                {
                    list_prateleira = (ArrayList) Prateleira.buscaPrateleiraCodigo(rs.getInt("prat_cod"));
                    if(!list_prateleira.isEmpty())
                    {
                        prod = new Produto(rs.getInt("prod_cod"), rs.getInt("prod_qtd"), rs.getString("prod_descricao"), 
                                rs.getDouble("prod_valor_unit"), t.get(0), (Prateleira) list_prateleira.get(0));
                        
                        t.remove(0);
                        list_prateleira.remove(0);
                        
                        for(int i = 0; i < list_lotes.size(); i++)
                            prod.addLotes((Lote) list_lotes.get(i));
                        
                        for(int i = 0; i < list_observador.size(); i++)
                            prod.register(list_observador.get(i));
                        
                        p.add(prod);
                    }
                }
            }  
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return p;
    }
    
    public List getComLote(int lote)
    {
        List<Produto> p = new ArrayList();
        String sql = "select * from produto inner join lote on produto.prod_cod = lote.prod_cod where lote.num_lot = " + lote;
        TipoProduto tipo = new TipoProduto();
        List<TipoProduto> t = null;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            ArrayList list_prateleira = null;
            ArrayList list_lotes = null;
            ArrayList <Observador> list_observador = null;
            Produto prod = null;
            while(rs.next())
            {
                t = tipo.get("tp_cod = "+rs.getInt("tp_cod"));
                list_lotes = (ArrayList<Lote>) Lote.buscaLoteCodigoProduto(rs.getInt("prod_cod"));
                list_observador = (ArrayList<Observador>) getObservadorCliente("prod_cod = " + rs.getInt("prod_cod"));
                
                if(!t.isEmpty())
                {
                    list_prateleira = (ArrayList) Prateleira.buscaPrateleiraCodigo(rs.getInt("prat_cod"));
                    if(!list_prateleira.isEmpty())
                    {
                        prod = new Produto(rs.getInt("prod_cod"), rs.getInt("prod_qtd"), rs.getString("prod_descricao"), 
                                rs.getDouble("prod_valor_unit"), t.get(0), (Prateleira) list_prateleira.get(0));
                        
                        t.remove(0);
                        list_prateleira.remove(0);
                        
                        for(int i = 0; i < list_lotes.size(); i++)
                            prod.addLotes((Lote) list_lotes.get(i));
                        
                        for(int i = 0; i < list_observador.size(); i++)
                            prod.register(list_observador.get(i));
                        
                        p.add(prod);
                    }
                }
            }  
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return p;
    }
    
    public List buscarProdutoFiltro(String info, int flag)
    {
        List<Produto> list = new ArrayList();
        int cod = 0;
        
        switch (flag)
        {
            case 1: list = (ArrayList) get("prod_cod = " + info); break;
            case 2:
                if(!info.isEmpty())
                    list = (ArrayList) get("prod_descricao like '" + info + "'");
                else
                    list = (ArrayList) get("");
            break;
            case 3: list = (ArrayList) get("prod_valor_unit = " + info); break;
            case 4: list = (ArrayList) getComTipo("produto.tp_cod = " + info); break;
            case 5:
                if(!info.isEmpty())
                    list = (ArrayList) getComTipo("tipo_produto.tp_descricao like '" + info + "'");
                else
                    list = (ArrayList) getComTipo("");
            break;
            case 6:
                try
                { cod = Integer.parseInt(info); } catch(Exception e){ cod = 0; }
                list = (ArrayList) getComLote(cod);
            break;
            case 7:
                try
                { cod = Integer.parseInt(info); } catch(Exception e){ cod = 0; }
                list = (ArrayList) getComFileira(cod);
            break;
            case 8:
                list = (ArrayList) get("prat_cod = " + info);
            break;
        }
        
        return list;
    }
    
    public int buscaCodLoteID(int id)
    {
        String query = "select num_lot from lote where prod_cod = "+id;
        
        int num_lote=0;
        //List list = new ArrayList();
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                num_lote = rs.getInt("num_lot");
                //list.add(rs.getInt("num_lot"));
            }
                
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return num_lote;//list.get(0);
    }
    
    public int buscaCodPrateleiraID(int id)
    {
        String query = "select prat_cod from produto where prod_cod = "+id;
        
        int prat_cod=0;
        //List list = new ArrayList();
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                prat_cod = rs.getInt("prat_cod");
                //list.add(rs.getInt("num_lot"));
            }
                
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return prat_cod;//list.get(0);
    }
    
    public boolean atualizaEstoqueLote(int idproduto, int idlote, int valor)
    {
        String query = "update lote set qtde = '$1' where num_lot = "+idlote+" and prod_cod = "+idproduto;
        query = query.replace("$1", ""+valor);
        boolean result = false;
        try
        {   
            result =  Banco.getCon().manipular(query);
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.toString()); }
        return result;
    }
    
    
    // ------------------------------------------------------------------------------------------------------------------
}