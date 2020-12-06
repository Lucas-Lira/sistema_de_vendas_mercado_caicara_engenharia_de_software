package prjmercadocaiçara.db.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.modelos.Parcela;

public class ParcelaBD
{
        static public boolean salvar(int desp_cod,Parcela par)
    {
        String sql = "insert into parcela (parc_cod,desp_cod,parc_descricao,parc_valor,parc_dtvencto,parc_dtpgto,parc_valorpgto,parc_juros,cai_data,fun_cod)"
                + " values ($1,$2,'$3',$4,'$5','$6',$7,$8,'$9',#)";
        sql = sql.replace("$1", ""+par.getId());
        sql = sql.replace("$2", ""+desp_cod);
        sql = sql.replace("$3", ""+par.getDescricao());
        sql = sql.replace("$4", ""+par.getValor());
        sql = sql.replace("$5", ""+par.getDt_vencto());
        sql = sql.replace("$6", ""+par.getDt_pgto());
        sql = sql.replace("$7", ""+par.getValor_pgto());
        sql = sql.replace("$8", ""+par.getJuros());
        sql = sql.replace("$9", ""+par.getCaixa().getData());
        sql = sql.replace("#", ""+par.getCaixa().getId());
        return Banco.getCon().manipular(sql);
    }
    
    static public boolean alterar(int desp_cod, Parcela par)
    {
        String sql = "update parcela set parc_cod=$1,desp_cod=$2,parc_descricao='$3',parc_valor=$4,parc_dtvencto='$5'"
                + " where parc_cod="+par.getId()+" and desp_cod="+desp_cod;
        sql = sql.replace("$1", ""+par.getId());
        sql = sql.replace("$2", ""+desp_cod);
        sql = sql.replace("$3", ""+par.getDescricao());
        sql = sql.replace("$4", ""+par.getValor());
        sql = sql.replace("$5", ""+par.getDt_vencto());
        return Banco.getCon().manipular(sql);
    }
    
    static public boolean pagar(int desp_cod, Parcela par)
    {
        String sql = "update parcela set parc_dtpgto='$1',parc_valorpgto=$2,parc_juros=$3,cai_data='$4',fun_cod=$5"
                + " where parc_cod="+par.getId()+" and desp_cod="+desp_cod;
        sql = sql.replace("$1", ""+par.getDt_pgto());
        sql = sql.replace("$2", ""+par.getValor_pgto());
        sql = sql.replace("$3", ""+par.getJuros());
        sql = sql.replace("$4", ""+par.getCaixa().getData());
        sql = sql.replace("$5", ""+par.getCaixa().getId());
        return Banco.getCon().manipular(sql);
    }
    
    static private List<Parcela> buscar(String filtro)
    {
        List<Parcela> list = new ArrayList();
        String query = "select * from parcela";
        if(!filtro.isEmpty())
            query+= " where "+filtro;
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            Parcela p;
            while(rs.next())
            {
                p = new Parcela();
                p.setId(rs.getInt("parc_cod"));
                p.setDescricao(rs.getString("parc_descricao"));
                if(rs.getDate("parc_dtpgto")!=null)
                {
                    p.setDt_pgto(rs.getDate("parc_dtpgto").toLocalDate());
                    p.setValor_pgto(rs.getDouble("parc_valorpgto"));
                }
                p.setDt_vencto(rs.getDate("parc_dtvencto").toLocalDate());
                p.setValor(rs.getDouble("parc_valor"));
                list.add(p);
            }
        }
        catch(SQLException e){}
        return list;
    }
    
    static public List<Parcela> buscarPorMes(int desp_cod, int mes)
    {
        String filtro = "desp_cod="+desp_cod+" and EXTRACT(MONTH FROM parc_dtvencto)="+mes+" order by parc_cod";
        return buscar(filtro);
    }
    
    static public List<Parcela> buscar(int desp_cod)
    {
        String filtro = "desp_cod="+desp_cod+" order by parc_cod";
        return buscar(filtro);
    }
}