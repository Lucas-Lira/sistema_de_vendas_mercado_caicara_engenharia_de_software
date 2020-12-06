package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;
import prjmercadocaiçara.db.persistencia.ParcelaBD;

public class Parcela
{
    private int id;
    private double valor, valor_pgto, juros;
    private LocalDate dt_vencto, dt_pgto;
    private String descricao;
    private Funcionario funcionario;
    private Caixa caixa;

    public Parcela(int id, double valor, double valor_pgto, double juros, LocalDate dt_vencto, LocalDate dt_pgto, 
            String descricao, Funcionario funcionario, Caixa caixa)
    {
        this.id = id;
        this.valor = valor;
        this.valor_pgto = valor_pgto;
        this.juros = juros;
        this.dt_vencto = dt_vencto;
        this.dt_pgto = dt_pgto;
        this.descricao = descricao;
        this.funcionario = funcionario;
        this.caixa = caixa;
    }
    
    public Parcela(double valor, double valor_pgto, double juros, LocalDate dt_vencto, LocalDate dt_pagto, 
            String descricao, Funcionario funcionario, Caixa caixa)
    { this(0, valor, valor_pgto, juros, dt_vencto, dt_pagto, descricao, funcionario, caixa); }

    public Parcela()
    { this(0, 0, 0, 0, null, null, "", null, null); }
    
    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public double getValor()
    { return valor; }

    public void setValor(double valor)
    { this.valor = valor; }

    public double getValor_pgto()
    { return valor_pgto; }

    public void setValor_pgto(double valor_pgto)
    { this.valor_pgto = valor_pgto; }

    public double getJuros()
    { return juros; }

    public void setJuros(double juros)
    { this.juros = juros; }

    public LocalDate getDt_vencto()
    { return dt_vencto; }

    public void setDt_vencto(LocalDate dt_vencto)
    { this.dt_vencto = dt_vencto; }

    public LocalDate getDt_pgto()
    { return dt_pgto; }

    public void setDt_pgto(LocalDate dt_pgto)
    { this.dt_pgto = dt_pgto; }

    public String getDescricao()
    { return descricao; }

    public void setDescricao(String descricao)
    { this.descricao = descricao; }

    public Funcionario getFuncionario()
    { return funcionario; }

    public void setFuncionario(Funcionario funcionario)
    { this.funcionario = funcionario; }

    public Caixa getCaixa()
    { return caixa; }

    public void setCaixa(Caixa caixa)
    { this.caixa = caixa; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static boolean salvar(Parcela p, int cod_compra)
    {
        int erros = 0;
        String sql = "";
        
        if(p.getDt_pgto() != null)
        {
            sql = "insert into parcela (com_cod, parc_valorpgto, parc_dtpgto, parc_juros, "
                    + "parc_descricao, parc_valor, parc_dtvencto, cai_data, fun_cod) "
                    + "values($1, $2, '$3', $4, '$5', $6, '$7', '$8', $9)";

            sql = sql.replace("$1", "" + cod_compra);
            sql = sql.replace("$2", "" + p.getValor_pgto());
            sql = sql.replace("$3", p.getDt_pgto().toString());
            sql = sql.replace("$4", "" + p.getJuros());
            sql = sql.replace("$5", p.getDescricao());
            sql = sql.replace("$6", "" + p.getValor());
            sql = sql.replace("$7", p.getDt_vencto().toString());
            sql = sql.replace("$8", p.getCaixa().getData().toString());
            sql = sql.replace("$9", "" + p.getFuncionario().getId());
        }
        else
        {
            sql = "insert into parcela (com_cod, parc_juros, "
                    + "parc_descricao, parc_valor, parc_dtvencto, cai_data, fun_cod) "
                    + "values($1, $2, '$3', $4, '$5', '$6', $7)";

            sql = sql.replace("$1", "" + cod_compra);
            sql = sql.replace("$2", "" + p.getJuros());
            sql = sql.replace("$3", p.getDescricao());
            sql = sql.replace("$4", "" + p.getValor());
            sql = sql.replace("$5", p.getDt_vencto().toString());
            sql = sql.replace("$6", p.getCaixa().getData().toString());
            sql = sql.replace("$7", "" + p.getFuncionario().getId());
        }

        return Banco.getCon().manipular(sql);
    }
    
    public static boolean apagar(Parcela p)
    {
        String sql = "delete from parcela where parc_cod = " + p.getId();

        return Banco.getCon().manipular(sql);
    }
    
    public static List getParcelas(String filtro)
    {
        List<Parcela> list = new ArrayList();
        Parcela p = null;
        String query = "select * from parcela";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        query += " order by parc_dtvencto";
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            ArrayList aux = null;
            
            while(rs.next())
            {
                LocalDate data = rs.getDate("cai_data").toLocalDate();
                aux = (ArrayList) Caixa.buscaCaixaFuncionarioEData(rs.getInt("fun_cod"), data);
                
                if(rs.getDate("parc_dtpgto") != null)
                {
                    p = new Parcela(rs.getInt("parc_cod"), rs.getDouble("parc_valor"), rs.getDouble("parc_valorpgto"), 
                        rs.getDouble("parc_juros"), rs.getDate("parc_dtvencto").toLocalDate(), rs.getDate("parc_dtpgto").toLocalDate(), 
                        rs.getString("parc_descricao"), null, null);
                }
                else
                {
                    p = new Parcela(rs.getInt("parc_cod"), rs.getDouble("parc_valor"), 0.0, 
                        0.0, rs.getDate("parc_dtvencto").toLocalDate(), null, rs.getString("parc_descricao"), null, null);
                }
                
                if(!aux.isEmpty())
                {
                    p.setCaixa((Caixa) aux.get(0));
                    aux = (ArrayList) Funcionario.get("fun_cod = " + rs.getInt("fun_cod"));
                    if(!aux.isEmpty())
                        p.setFuncionario((Funcionario) aux.get(0));
                }
                
                list.add(p);
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return list;
    }
    // ------------------------------------------------------------------------------------------------------------------
    
    public static boolean salvar(int desp_cod,Parcela parc)
    {
        return ParcelaBD.salvar(desp_cod, parc);
    }
    
    public static boolean alterar(int desp_cod, Parcela parc)
    {
        return ParcelaBD.alterar(desp_cod, parc);
    }
    
    public static boolean pagar(int desp_cod, Parcela parc)
    {
        return ParcelaBD.pagar(desp_cod, parc);
    }
    
    public static List<Parcela> buscar(int desp_cod, int mes)
    {
        List<Parcela> list;
        if(mes!=0)
            list = ParcelaBD.buscarPorMes(desp_cod,mes);
        else
            list = ParcelaBD.buscar(desp_cod);
        return list;
    }
}