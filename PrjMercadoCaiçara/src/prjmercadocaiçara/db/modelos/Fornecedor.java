
package prjmercadocaiçara.db.modelos;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import prjmercadocaiçara.db.persistencia.Banco;

public class Fornecedor {
    private int id;
    private Date vinculo, desvinculo;
    private String cnpj, descricao, telefone, email;

    public Fornecedor(Date vinculo, String cnpj, String descricao, String telefone, String email) {
        this.id = 0;
        this.vinculo = vinculo;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.telefone = telefone;
        this.email = email;
    }

    public Fornecedor() {
        this(0, null, null, "", "", "", "");
    }

    public Fornecedor(Date vinculo, Date desvinculo, String cnpj, String descricao, String telefone, String email) {
        this(0, vinculo, desvinculo, cnpj, descricao, telefone, email);
    }

    public Fornecedor(int id, Date vinculo, Date desvinculo, String cnpj, String descricao, String telefone, String email) {
        this.id = id;
        this.vinculo = vinculo;
        this.desvinculo = desvinculo;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.telefone = telefone;
        this.email = email;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getVinculo() {
        return vinculo;
    }

    public void setVinculo(Date vinculo) {
        this.vinculo = vinculo;
    }

    public Date getDesvinculo() {
        return desvinculo;
    }

    public void setDesvinculo(Date desvinculo) {
        this.desvinculo = desvinculo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString()
    {
        return "Código = " + id + ", CNPJ = " + cnpj + ", Descrição = " + descricao.toUpperCase();
    }
    
    public static String retiraMascara(String c)
    {
        c = c.replace("/", "");
        c = c.replace(".", "");
        c = c.replace("-", "");
        return c;
    }
    
    // ==================================================================================================
    // Métodos de Validação
    // ==================================================================================================
    public static boolean validaCnpj(String CNPJ)
    {
        CNPJ = retiraMascara(CNPJ);
        
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
            CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
            CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
            CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
            CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
           (CNPJ.length() != 14))
           return(false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            
          sm = 0;
          peso = 2;
          for (i=11; i>=0; i--) {
        // converte o i-ésimo caractere do CNPJ em um número:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posição de '0' na tabela ASCII)
            num = (int)(CNPJ.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
               peso = 2;
          }

          r = sm % 11;
          if ((r == 0) || (r == 1))
             dig13 = '0';
          else dig13 = (char)((11-r) + 48);

        // Calculo do 2o. Digito Verificador
          sm = 0;
          peso = 2;
          for (i=12; i>=0; i--) {
            num = (int)(CNPJ.charAt(i)- 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
               peso = 2;
          }

          r = sm % 11;
          if ((r == 0) || (r == 1))
             dig14 = '0';
          else dig14 = (char)((11-r) + 48);

        // Verifica se os dígitos calculados conferem com os dígitos informados.
          if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
             return(true);
          else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
    
    // ==================================================================================================
    // Métodos para acesso ao banco
    // ===================================================================================================
    public boolean salvar()
    {
        String sql = "insert into fornecedor (f_dtvinculo, f_cnpj, f_descricao, f_telefone, f_email) values ('$1',  '$3', '$4', '$5', '$6')";
        sql = sql.replace("$1", ""+vinculo);
        //sql = sql.replace("$2", ""+f.getDesvinculo());
        sql = sql.replace("$3", cnpj);
        sql = sql.replace("$4", descricao);
        sql = sql.replace("$5", telefone);
        sql = sql.replace("$6", email);
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar()
    {
        String sql = "update fornecedor set f_dtvinculo = '$1', f_dtdesvinculo = '$2', f_cnpj = '$3', f_descricao = '$4', f_telefone = '$5', f_email = '$6' where f_cod = "+id;
        sql = sql.replace("$1", ""+vinculo);
        sql = sql.replace("$2", ""+desvinculo);
        sql = sql.replace("$3", cnpj);
        sql = sql.replace("$4", descricao);
        sql = sql.replace("$5", telefone);
        sql = sql.replace("$6", email);
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar()
    {
        return Banco.getCon().manipular("delete from fornecedor where f_cod = "+id);
    }
    
    public List get(String filtro)
    {
        List<Fornecedor> list = new ArrayList();
        String sql = "select * from fornecedor";
        
        if(!filtro.isEmpty())
            sql += " where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
            while(rs.next())
            {
                list.add(new Fornecedor(rs.getInt("f_cod"), rs.getDate("f_dtvinculo"),
                        rs.getDate("f_dtdesvinculo"), rs.getString("f_cnpj"), rs.getString("f_descricao"),
                            rs.getString("f_telefone"), rs.getString("f_email")));
            }
        }
        catch(Exception e)
        {   System.out.println("Erro"+e.getMessage()); }
        return list;
    }
}
