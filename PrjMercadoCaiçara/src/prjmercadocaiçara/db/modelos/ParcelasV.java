
package prjmercadocaiçara.db.modelos;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import prjmercadocaiçara.db.persistencia.Banco;

public class ParcelasV {
    private int id, idvenda, idfuncionario;
    private Double valorpago, valorparcela, parcjuros;
    private Date datapagamento, datavencimento, datacaixa;
    private String descricao;

    public ParcelasV() {
        this(0, 0, 0, 0.0, 0.0, 0.0, null, null, null, "");
    }

    public ParcelasV(Double valorpago, Double valorparcela, Double parcjuros, Date datapagamento, Date datavencimento, Date datacaixa, String descricao) {
        this.valorpago = valorpago;
        this.valorparcela = valorparcela;
        this.parcjuros = parcjuros;
        this.datapagamento = datapagamento;
        this.datavencimento = datavencimento;
        this.datacaixa = datacaixa;
        this.descricao = descricao;
    }

    public ParcelasV(int idfuncionario, Double valorpago, Double valorparcela, Double parcjuros, Date datapagamento, Date datavencimento, Date datacaixa, String descricao) {
        this.idfuncionario = idfuncionario;
        this.valorpago = valorpago;
        this.valorparcela = valorparcela;
        this.parcjuros = parcjuros;
        this.datapagamento = datapagamento;
        this.datavencimento = datavencimento;
        this.datacaixa = datacaixa;
        this.descricao = descricao;
    }

    public ParcelasV(int id, int idfuncionario, Double valorpago, Double valorparcela, Double parcjuros, Date datapagamento, Date datavencimento, Date datacaixa, String descricao) {
        this.idvenda = 0;
        this.idfuncionario = idfuncionario;
        this.valorpago = valorpago;
        this.valorparcela = valorparcela;
        this.parcjuros = parcjuros;
        this.datapagamento = datapagamento;
        this.datavencimento = datavencimento;
        this.datacaixa = datacaixa;
        this.descricao = descricao;
        this.id = id;
    }

    public ParcelasV(int id, int idvenda, int idfuncionario, Double valorpago, Double valorparcela, Double parcjuros, Date datapagamento, Date datavencimento, Date datacaixa, String descricao) {
        this.id = id;
        this.idvenda = idvenda;
        this.idfuncionario = idfuncionario;
        this.valorpago = valorpago;
        this.valorparcela = valorparcela;
        this.parcjuros = parcjuros;
        this.datapagamento = datapagamento;
        this.datavencimento = datavencimento;
        this.datacaixa = datacaixa;
        this.descricao = descricao;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(int idvenda) {
        this.idvenda = idvenda;
    }

    public int getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(int idfuncionario) {
        this.idfuncionario = idfuncionario;
    }

    public Double getValorpago() {
        return valorpago;
    }

    public void setValorpago(Double valorpago) {
        this.valorpago = valorpago;
    }

    public Double getValorparcela() {
        return valorparcela;
    }

    public void setValorparcela(Double valorparcela) {
        this.valorparcela = valorparcela;
    }

    public Double getParcjuros() {
        return parcjuros;
    }

    public void setParcjuros(Double parcjuros) {
        this.parcjuros = parcjuros;
    }

    public Date getDatapagamento() {
        return datapagamento;
    }

    public void setDatapagamento(Date datapagamento) {
        this.datapagamento = datapagamento;
    }

    public Date getDatavencimento() {
        return datavencimento;
    }

    public void setDatavencimento(Date datavencimento) {
        this.datavencimento = datavencimento;
    }

    public Date getDatacaixa() {
        return datacaixa;
    }

    public void setDatacaixa(Date datacaixa) {
        this.datacaixa = datacaixa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    // Métodos de acesso e manipulação de tabelas no banco de dados relacionadas as parcelas
    // ===========================================================================================
    
    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    
    public static boolean salvar(ParcelasV parcela, String formapagamento, double valor_total, Date datavenda)
    {
        String sql="";
        // Inserir Parcelas da Venda
        if(!formapagamento.equals("À Vista"))
        {
            sql= "insert into parcelav (parc_cod, ven_cod, parc_valorpagto, parc_juros, parc_descricao, parc_valor, parc_dtvencto, cai_data, fun_cod) "
                    + "values ('$1', '$2', '$3', '$4', '$5', '$6', '$7', '$8', '$9')";
            sql = sql.replace("$1", ""+parcela.getId()); 
            sql = sql.replace("$2", ""+parcela.getIdvenda());
            sql = sql.replace("$3", ""+parcela.getValorpago());
            sql = sql.replace("$4", ""+parcela.getParcjuros());
            sql = sql.replace("$5", ""+parcela.getDescricao());
            sql = sql.replace("$6", ""+parcela.getValorparcela());
            sql = sql.replace("$7", ""+parcela.getDatavencimento());
            sql = sql.replace("$8", ""+parcela.getDatacaixa());
            sql = sql.replace("$9", ""+parcela.getIdfuncionario());
        }
        else
        {
            sql= "insert into parcelav (parc_cod, ven_cod, parc_valorpagto, parc_juros, parc_descricao,"
                    + " parc_valor, parc_dtvencto, cai_data, fun_cod, parc_dtpagto) "
                    + "values ('$1', '$2', '$3', '$4', '$5', '$6', '$7', '$8', '$9', '$0')";
            sql = sql.replace("$1", ""+parcela.getId()); 
            sql = sql.replace("$2", ""+parcela.getIdvenda());
            sql = sql.replace("$3", ""+valor_total);
            sql = sql.replace("$4", ""+parcela.getParcjuros());
            sql = sql.replace("$5", ""+parcela.getDescricao());
            sql = sql.replace("$6", ""+parcela.getValorparcela());
            sql = sql.replace("$7", ""+parcela.getDatavencimento());
            sql = sql.replace("$8", ""+parcela.getDatacaixa());
            sql = sql.replace("$9", ""+parcela.getIdfuncionario());
            sql = sql.replace("$0", toLocalDate(datavenda).toString());
               
        }
        return Banco.getCon().manipular(sql);    
    }
    
    public static boolean apagar(ParcelasV p)
    {
        String sql = "delete from parcelav where parc_cod = " + p.getId();
        return Banco.getCon().manipular(sql);
    }
}
