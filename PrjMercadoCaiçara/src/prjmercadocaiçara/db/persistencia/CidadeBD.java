package prjmercadocaiçara.db.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.modelos.Cidade;
import prjmercadocaiçara.db.modelos.Estado;

public class CidadeBD
{
    public static List buscaCidadeNome(String nome)
    {
        List <Cidade> listcidade = new ArrayList();
        List <Estado> listestado = null;
        EstadoBD estbd = new EstadoBD();
        String query = "select * from cidade where cid_nome like '" + nome + "%'" + " order by cid_nome";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listestado = estbd.buscaEstadoCodigo(rs.getInt("est_cod"));
                
                if(!listestado.isEmpty())
                {
                    listcidade.add(new Cidade(rs.getInt("cid_cod"),
                    rs.getString("cid_nome").toUpperCase(), listestado.get(0)));

                    listestado.remove(0);
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listcidade;
    }
    
    public static List buscaCidadeCodigo(int cod)
    {
        List <Cidade> listcidade = new ArrayList();
        List <Estado> listestado = null;
        EstadoBD estbd = new EstadoBD();
        String query = "select * from cidade where cid_cod = " + cod;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listestado = estbd.buscaEstadoCodigo(rs.getInt("est_cod"));
                
                if(!listestado.isEmpty())
                {
                    listcidade.add(new Cidade(rs.getInt("cid_cod"),
                    rs.getString("cid_nome").toUpperCase(), listestado.get(0)));

                    listestado.remove(0);
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listcidade;
    }
    
    public static List get()
    {
        List <Cidade> listcidade = new ArrayList();
        String query = "select * from cidade inner join estado on cidade.est_cod = estado.est_cod";
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
                listcidade.add(new Cidade(rs.getInt("cid_cod"),rs.getString("cid_nome"),new Estado(rs.getInt("est_cod"),rs.getString("est_nome"),rs.getString("est_sgl"))));
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listcidade;
    }
    
    public static Cidade buscarCidade(String nome, String uf)
    {
        Cidade cid=null;
        String query = "select * from cidade inner join estado on cidade.est_cod = estado.est_cod where cid_nome='$1' and est_sgl='$2'";
        query = query.replace("$1", nome);
        query = query.replace("$2", uf);
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            if(rs.next())
            {
                cid = new Cidade(rs.getInt("cid_cod"),rs.getString("cid_nome").toUpperCase(), 
                        new Estado(rs.getInt("est_cod"),rs.getString("est_nome"),rs.getString("est_sgl")));

            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return cid;
    }
}