
package prjmercadocaiçara.db.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.modelos.Despesa;
import prjmercadocaiçara.db.modelos.Parcela;
import prjmercadocaiçara.db.modelos.TipoDespesa;

public class DespesaBD {
    
    static public boolean salvarTipoDespesa(TipoDespesa tpdesp)
    {
        return Banco.getCon().manipular("insert into tipo_despesa (tdesp_descricao) values('"+tpdesp.getDescricao()+"')");
    }
    
    static public boolean alterarTipoDespesa(TipoDespesa tpdesp)
    {
        return Banco.getCon().manipular("update tipo_despesa set tdesp_descricao='"+tpdesp.getDescricao()+"' where tdesp_cod="+tpdesp.getId());
    }
    
    static public boolean salvarDespesa(Despesa desp)
    {
        boolean result=false;
        String sql = "insert into despesa (desp_descricao,desp_diavenc,desp_dtemissao,desp_status,tdesp_cod) "
                + "values('$1',$2,'$3','$4',$5)";
        sql = sql.replace("$1", desp.getDescricao());
        sql = sql.replace("$2", ""+desp.getDiavencto());
        sql = sql.replace("$3", desp.getDtemissao().toString());
        sql = sql.replace("$4", ""+desp.getStatus());
        sql = sql.replace("$5", ""+desp.getTpdespesa().getId());
        try
        {
            Banco.getCon().getConnect().setAutoCommit(false);
        }
        catch(SQLException t)
        {}
        if(Banco.getCon().manipular(sql))
        {
            int desp_cod = Banco.getCon().getMaxPK("despesa", "desp_cod");
            Parcela p;
            for(int i=0;i<desp.getParcelas().size();i++)
            {
                p = desp.getParcelas().get(i);
                sql = "insert into parcela (desp_cod,parc_descricao,parc_valor,parc_dtvencto)"
                    + " values ($2,'$3',$4,'$5')";
                sql = sql.replace("$2", ""+desp_cod);
                sql = sql.replace("$3", p.getDescricao());
                sql = sql.replace("$4", ""+p.getValor());
                sql = sql.replace("$5", p.getDt_vencto().toString());
                result=Banco.getCon().manipular(sql);
            }
        }
        else
            result = false;
        try
        {
            if(result)
                Banco.getCon().getConnect().commit();
            else
                Banco.getCon().getConnect().rollback();
            Banco.getCon().getConnect().setAutoCommit(true);
        }
        catch(SQLException s)
        {}
        return result;
    }
    
    static public boolean quitarDespesa(int desp)
    {
        return Banco.getCon().manipular("update despesa set desp_status='P' where desp_cod="+desp);
    }
    
    static public boolean deletarTipoDespesa(int cod)
    {
        return Banco.getCon().manipular("delete from tipo_despesa where tdesp_cod="+cod);
    }
    
    static public List<TipoDespesa> buscarTipoDespesa(String filtro)
    {
        List<TipoDespesa> lista = new ArrayList();
        String query = "select * from tipo_despesa where tdesp_descricao like '"+filtro+"%' order by tdesp_descricao";
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
                lista.add(new TipoDespesa(rs.getInt("tdesp_cod"),rs.getString("tdesp_descricao")));
        }
        catch(SQLException e){}
        return lista;
    }
    
    static public List<Despesa> buscarDespesa(int codigo)
    {
        return buscarDespesa("despesa.tdesp_cod="+codigo);
    }
    
    static private List<Despesa> buscarDespesa(String filtro)
    {
        List<Despesa> lista = new ArrayList();
        String query = "select * from despesa inner join tipo_despesa on despesa.tdesp_cod=tipo_despesa.tdesp_cod";
        if(!filtro.isEmpty())
            query+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                Despesa d;
                d = new Despesa();
                d.setId(rs.getInt("desp_cod"));
                d.setDescricao(rs.getString("desp_descricao"));
                d.setDtemissao(rs.getDate("desp_dtemissao").toLocalDate());
                d.setDiavencto(rs.getInt("desp_diavenc"));
                d.setStatus(rs.getString("tdesp_cod").charAt(0));
                d.setTpdespesa(new TipoDespesa(rs.getInt("tdesp_cod"),rs.getString("tdesp_descricao")));
                lista.add(d);
            }
        }
        catch(SQLException e)
        {}
        return lista;
    }
    
    public static boolean verificarExistenciaParcelaNaoPaga(int codigodespesa)
    {
        return buscarDespesa("desp_cod="+codigodespesa+" and parc_dtpgto is null").size()>0;
    }
    
}
