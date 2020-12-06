package prjmercadocaiçara.db.modelos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import javax.imageio.ImageIO;
import prjmercadocaiçara.db.persistencia.Banco;

public class Funcionario
{
    private int id;
    private String nome;
    private int nivel;
    private String cpf;
    private LocalDate dt_nasc;
    private String telefone, email;
    private Image imagem;
    private Endereco endereco;
    private String login, senha;
    private ArrayList <Horario> horarios;
    private ArrayList <Caixa> caixas;

    public Funcionario(int id, int nivel, Image imagem, LocalDate dt_nasc, Endereco endereco, String email, String login, String senha, String nome, String cpf, String telefone)
    {
        this.id = id;
        this.nivel = nivel;
        this.imagem = imagem;
        this.dt_nasc = dt_nasc;
        this.endereco = endereco;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        
        horarios = new ArrayList();
        caixas = new ArrayList();
    }
    
    public Funcionario()
    { this(0, 0, null, LocalDate.now(), null, "", "", "", "", "", ""); }
    
    public Funcionario(int nivel, Image imagem, LocalDate dt_nasc, Endereco endereco, String email, String login, String senha, String nome, String cpf, String telefone)
    { this(0, nivel, imagem, dt_nasc, endereco, email, login, senha, nome, cpf, telefone); }

    public Funcionario(int nivel, LocalDate dt_nasc, Endereco endereco, String email, String login, String senha, String nome, String cpf, String telefone)
    { this(0, nivel, null, dt_nasc, endereco, email, login, senha, nome, cpf, telefone); }
     
    public int getId()
    { return id; }

    public void setId(int id)
    { this.id = id; }

    public int getNivel()
    { return nivel; }

    public void setNivel(int nivel)
    { this.nivel = nivel; }

    public Image getImagem()
    { return imagem; }

    public void setImagem(Image imagem)
    { this.imagem = imagem; }

    public LocalDate getDt_nasc()
    { return dt_nasc; }

    public void setDt_nasc(LocalDate dt_nasc)
    { this.dt_nasc = dt_nasc; }

    public Endereco getEndereco()
    { return endereco; }

    public void setEndereco(Endereco endereco)
    { this.endereco = endereco; }

    public String getEmail()
    { return email; }

    public void setEmail(String email)
    { this.email = email; }

    public String getLogin()
    { return login; }

    public void setLogin(String login)
    { this.login = login; }

    public String getSenha()
    { return senha; }

    public void setSenha(String senha)
    { this.senha = senha; }

    public String getNome()
    { return nome; }
    
    public void setNome(String nome)
    { this.nome = nome; }

    public String getCpf()
    { return cpf; }

    public void setCpf(String cpf)
    { this.cpf = cpf; }

    public String getTelefone()
    { return telefone; }

    public void setTelefone(String telefone)
    { this.telefone = telefone; }

    public ArrayList<Horario> getHorarios()
    { return horarios; }
    
    public void clearHorarios()
    { horarios = new ArrayList(); }
    
    public void addHorario(Horario hor)
    { horarios.add(hor); }
    
    public int buscaHorario(Horario hor)
    {
        int pos= -1;
        if(!horarios.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < horarios.size() && !achou; i++)
                if(horarios.get(i) == hor)
                {
                    pos= i;
                    achou= true;
                }
        }
        
        return pos;
    }
        
    public boolean removeHorario(Horario hor)
    {
        int indice= buscaHorario(hor);
        if(indice != (-1))
        {
            horarios.remove(indice);
            return true;
        }
        
        return false;
    }

    public ArrayList<Caixa> getCaixas()
    { return caixas; }
    
    public void clearCaixas()
    { caixas = new ArrayList(); }
    
    public void addCaixa(Caixa c)
    { caixas.add(c); }
    
    public int buscaCaixa(Caixa c)
    {
        int pos= -1;
        if(!caixas.isEmpty())
        {
            boolean achou = false;
            for(int i = 0; i < caixas.size() && !achou; i++)
                if(caixas.get(i) == c)
                {
                    pos= i;
                    achou= true;
                }
        }
        
        return pos;
    }
        
    public boolean removeCaixa(Caixa c)
    {
        int indice= buscaCaixa(c);
        if(indice != (-1))
        {
            caixas.remove(indice);
            return true;
        }
        
        return false;
    }
    
    @Override
    public String toString()
    { return  nome + " - CPF : " + cpf; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public static int getMaxPKFuncionario()
    {
        int codfuncionario = 0;
        try
        { codfuncionario = (int) Banco.getCon().getMaxPK("funcionario", "fun_cod"); }
        catch(Exception ex) { codfuncionario = (-1); }
        
        return codfuncionario;
    }
    
    public static String removeCPFMascara(String CPF)
    {
        int i, j = 3; String numero = "";
        for (i = 0; i < CPF.length(); i++)
        {
            if (i != j)
            {
                //Removendo a máscara do CPF (recolhendo apenas os números):
                numero = numero.concat(""+CPF.charAt(i));
            }
            else
                j += 4;
        }
        return numero;
    }
    
    public static boolean validaCPF(String CPF)
    {
        if(CPF.length() == 14)
            CPF = removeCPFMascara(CPF);
        
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
           (CPF.length() != 11))
           return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;
        
        try
        {
            // Calculo do 1º Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++)
            { 
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0         
                // (48 eh a posicao de '0' na tabela ASCII)         
                num = (int)(CPF.charAt(i) - 48); 
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2º Digito Verificador
            sm = 0;
            peso = 11;
            for(i = 0; i < 10; i++)
            {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        }
        catch (InputMismatchException erro)
        { return(false); }
    }
    
    public static boolean salvar(Funcionario fun)
    {
        int codfuncionario = 0;
        boolean executou = false;
        
        String sql = "insert into funcionario (fun_dtnasc, fun_nivel, end_cod, fun_email, fun_login, fun_senha,"
                + "fun_nome, fun_cpf, fun_telefone) values ('$1', $2, $3, '$4', '$5', '$6', '$7', '$8', '$9')";

        sql = sql.replace("$1", fun.getDt_nasc().toString());
        sql = sql.replace("$2", "" + fun.getNivel());
        sql = sql.replace("$3", "" + fun.getEndereco().getId());
        sql = sql.replace("$4", fun.getEmail().toUpperCase());
        sql = sql.replace("$5", fun.getLogin().toUpperCase());
        sql = sql.replace("$6", fun.getSenha());
        sql = sql.replace("$7", fun.getNome().toUpperCase());
        sql = sql.replace("$8", fun.getCpf());
        sql = sql.replace("$9", fun.getTelefone());

        if(Banco.getCon().manipular(sql))
        {
            executou = true;
            
            try
            {
                codfuncionario = (int) Banco.getCon().getMaxPK("funcionario", "fun_cod");    

                if(fun.getImagem() != null)
                    salvarImagem(fun);
            }catch(Exception ex){ codfuncionario = 0; }
        }
        fun.setId(codfuncionario);
        
        return executou;
    }
    
    public static boolean alterar(Funcionario fun)
    {
        Boolean executou = false;
        
        String sql = "update funcionario set fun_dtnasc = '$1', fun_nivel = $2, end_cod = $3, fun_email = '$4', "
                + "fun_login = '$5', fun_senha = '$6', fun_nome = '$7', fun_cpf = '$8', "
                + "fun_telefone = '$9' where fun_cod = " + fun.getId();
            
        sql = sql.replace("$1", fun.getDt_nasc().toString());
        sql = sql.replace("$2", "" + fun.getNivel());
        sql = sql.replace("$3", "" + fun.getEndereco().getId());
        sql = sql.replace("$4", fun.getEmail().toUpperCase());
        sql = sql.replace("$5", fun.getLogin().toUpperCase());
        sql = sql.replace("$6", fun.getSenha());
        sql = sql.replace("$7", fun.getNome().toUpperCase());
        sql = sql.replace("$8", fun.getCpf());
        sql = sql.replace("$9", fun.getTelefone());

        executou = Banco.getCon().manipular(sql);

        if(executou)
            if(fun.getImagem() != null)
                salvarImagem(fun);
        
        return executou;
    }
    
    public static boolean apagar(Funcionario fun)
    {
        return Banco.getCon().manipular("delete from funcionario where fun_cod = " + fun.getId());
    }
    
    public static List buscaFuncionarioFiltro(int flag, String dado)
    {
        List <Funcionario> listfuncionario = new ArrayList();
        Funcionario fun = null;
        List <Endereco> listend = null;
        List <Horario> listhor = null;
        List <Caixa> listcaixa = null;
        String query = "select * from funcionario";
        
        switch (flag)
        {
            case 1: query += " where fun_cod = " + dado; break;
            case 2: query += " where fun_nome like '" + dado + "%'"; break;
            case 3: query += " where fun_nivel = " + dado; break;
            case 4: query += " where fun_cpf like '" + dado + "%'"; break;
            case 5: query += " where fun_dtnasc = '" + dado + "'"; break;
            case 6: query += " where fun_telefone like '" + dado + "%'"; break;
            case 7: query += " where fun_email like '" + dado + "%'"; break;
        }
        
        query += " order by fun_nome";
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listhor = new ArrayList();
                listhor = Horario.buscaHorarioFunCod(rs.getInt("fun_cod"));
                listend = new ArrayList();
                listend = Endereco.buscaEnderecoCodigo(rs.getInt("end_cod"));
                
                if(!listend.isEmpty())
                {
                    listcaixa = Caixa.buscaCaixaFuncionario(rs.getInt("fun_cod"));
                    
                    fun = new Funcionario(rs.getInt("fun_cod"), rs.getInt("fun_nivel"), null, rs.getDate("fun_dtnasc").toLocalDate(), 
                        listend.get(0), rs.getString("fun_email").toUpperCase(), rs.getString("fun_login").toUpperCase(), rs.getString("fun_senha"), 
                        rs.getString("fun_nome").toUpperCase(), rs.getString("fun_cpf"), rs.getString("fun_telefone"));
                    
                    if(!listhor.isEmpty())
                        for(int i = 0; i <listhor.size(); i++)
                            fun.addHorario(listhor.get(i));
                    
                    if(!listcaixa.isEmpty())
                        for(int i = 0; i < listcaixa.size(); i++)
                            fun.addCaixa(listcaixa.get(i));
                    
                    fun.setImagem(recuperarImagem(fun.getId()));
                    listfuncionario.add(fun);
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listfuncionario;
    }
    
    public static List buscaCPFFun(String cpf)
    {
        String aux = "";
        aux = "fun_cpf = '" + cpf + "'";
        return get(aux);
    }
    
    public static List buscaFunAdm()
    {
        String aux = "";
        aux = "fun_nivel =" + "1";
        return get(aux);
    }
    
    public static List buscaFunLoginSenha(String login, String senha)
    {
        String aux = "";
        aux = "fun_login = '" + login.toUpperCase() + "' AND fun_senha = '" + senha + "'";
        return get(aux);
    }
    
    public static List get(String filtro)
    {
        List <Funcionario> listfuncionario = new ArrayList();
        Funcionario fun = null;
        List <Endereco> listend = null;
        List <Horario> listhor = null;
        List <Caixa> listcaixa = null;
        String query = "select * from funcionario";
        if(!filtro.isEmpty())
            query += " where " + filtro;
        
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                listhor = new ArrayList();
                listhor = Horario.buscaHorarioFunCod(rs.getInt("fun_cod"));
                listend = new ArrayList();
                listend = Endereco.buscaEnderecoCodigo(rs.getInt("end_cod"));
                
                if(!listend.isEmpty())
                {
                    listcaixa = Caixa.buscaCaixaFuncionario(rs.getInt("fun_cod"));
                    
                    fun = new Funcionario(rs.getInt("fun_cod"), rs.getInt("fun_nivel"), null, rs.getDate("fun_dtnasc").toLocalDate(), 
                        listend.get(0), rs.getString("fun_email").toUpperCase(), rs.getString("fun_login").toUpperCase(), rs.getString("fun_senha"), 
                        rs.getString("fun_nome").toUpperCase(), rs.getString("fun_cpf"), rs.getString("fun_telefone"));
                    
                    if(!listhor.isEmpty())
                        for(int i = 0; i <listhor.size(); i++)
                            fun.addHorario(listhor.get(i));
                    
                    if(!listcaixa.isEmpty())
                        for(int i = 0; i < listcaixa.size(); i++)
                            fun.addCaixa(listcaixa.get(i));
                    
                    fun.setImagem(recuperarImagem(fun.getId()));
                    listfuncionario.add(fun);
                }
            }
        }
        catch(SQLException ex){ System.out.println("Erro: " + ex.toString()); }
        return listfuncionario;
    }
    
    public static boolean salvarImagem(Funcionario fun)
    {
        try
        {
           PreparedStatement st = Banco.getCon().getConnect().prepareStatement(
                "update funcionario set fun_foto = ? where fun_cod = " + fun.getId());
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ImageIO.write((RenderedImage) fun.getImagem(), "jpg", baos);
           InputStream is = new ByteArrayInputStream(baos.toByteArray());
           st.setBinaryStream(1, is, baos.toByteArray().length);
           st.executeUpdate();
           return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    public static Image recuperarImagem(int id/*Funcionario fun*/)
    {
        try
        {
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement("select fun_foto from funcionario where fun_cod = " + id);
            ResultSet rs = ps.executeQuery();
            
            BufferedImage bImageFromConvert = null;
            if (rs.next())
            {
                byte[] imgBytes = rs.getBytes("fun_foto");
                // transforma um byte[] em uma imagem  
                InputStream in = new ByteArrayInputStream(imgBytes);
                bImageFromConvert = ImageIO.read(in);
                BufferedImage bufferedImage;
                /*
                    SwingFXUtils.toFXImage(bufferedImage,null);
                    SwingFXUtils.fromFXImage(image,null);
                /*/
            }
                    
            rs.close();
            ps.close();
            
            if(bImageFromConvert != null)
            {
                //return Toolkit.getDefaultToolkit().createImage(bImageFromConvert.getSource());
                return bImageFromConvert;
            }
            else
                return null;
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            return null;
        }
    }
    // ------------------------------------------------------------------------------------------------------------------
}