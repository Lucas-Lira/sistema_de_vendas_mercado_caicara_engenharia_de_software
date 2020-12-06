
package prjmercadocaiçara.db.modelos;

import java.time.LocalDate;
import java.util.List;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import prjmercadocaiçara.db.persistencia.ClienteBD;
import prjmercadocaiçara.util.observer.Observador;

public class Cliente implements Observador{
    private int id;
    private LocalDate dtnasc;
    private String nome,cpf,email,telefone;
    private Endereco endereco;
    private ClienteBD cbd;

    public Cliente(int id, String nome, LocalDate dtnasc, String cpf, String telefone , String email, Endereco endereco) {
        this.id = id;
        this.dtnasc = dtnasc;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }
    
    public Cliente() {
        this(0,"",null,"","","",null);
    }

    public Cliente(String nome, LocalDate dtnasc, String cpf, String telefone, String email, Endereco endereco) {
        this(0, nome ,dtnasc, cpf, email, telefone, endereco);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDtnasc() {
        return dtnasc;
    }

    public void setDtnasc(LocalDate dtnasc) {
        this.dtnasc = dtnasc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    @Override
    public String toString()
    {
        return nome;
    }
    
    public static boolean salvar(Cliente cli)
    {
        return ClienteBD.salvar(cli);
    }
    
    public static boolean alterar(Cliente cli)
    {
        return ClienteBD.alterar(cli);
    }
    
    public static boolean deletar(int id)
    {
        return ClienteBD.deletar(id);
    }
    
    public static List<Cliente> buscarPorNome(String nome)
    {
        return ClienteBD.buscaPorNome(nome);
    }
    public static List<Cliente> buscarPorCPF(String cpf)
    {
        return ClienteBD.buscaPorCPF(cpf);
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO OBSERVER:
    // ------------------------------------------------------------------------------------------------------------------
    @Override
    public void update(String msg)
    {
        try
        {
            String rem = "projetocobra200@gmail.com";
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(rem, "cobra123bd"));
            email.setSSLOnConnect(true);
            email.setFrom(rem);
            email.setSubject("AVISO DE CHEGADA DE PRODUTO :)");
            
            LocalDate data = LocalDate.now();
            String texto = data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear() + " - "
                    + "Presidente Prudente/SP, Brasil\n\n" + nome.toUpperCase() + "," + msg + 
                    "\nAtenciosamente, Mercado Caiçara";
            email.setMsg(texto);
            email.addTo(this.email);
            
            email.send();
        }
        catch(Exception ex)
        { System.out.println("Erro: "+ex.getMessage()); }
    }
    // ------------------------------------------------------------------------------------------------------------------
}
