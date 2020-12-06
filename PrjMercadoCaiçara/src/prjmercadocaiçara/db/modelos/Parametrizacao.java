
package prjmercadocaiçara.db.modelos;

import javafx.scene.image.Image;
import prjmercadocaiçara.db.persistencia.ParametrizacaoBD;

public class Parametrizacao 
{
    private String nome;
    private String razao;
    private String telefone;
    private String email;
    private String site;
    private String fonte;
    private String cor;
    private Image logogrande;
    private Image logopequeno;
    private Endereco endereco;

    public Parametrizacao(String nome, String razao, String telefone, String email, String site, String fonte, String cor, Image logogrande, Image logopequeno, Endereco endereco) {
        this.nome = nome;
        this.razao = razao;
        this.telefone = telefone;
        this.email = email;
        this.site = site;
        this.fonte = fonte;
        this.cor = cor;
        this.logogrande = logogrande;
        this.logopequeno = logopequeno;
        this.endereco = endereco;
    }

    public Parametrizacao() {
        this("","","","","","","",null,null,null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Image getLogogrande() {
        return logogrande;
    }

    public void setLogogrande(Image logogrande) {
        this.logogrande = logogrande;
    }

    public Image getLogopequeno() {
        return logopequeno;
    }

    public void setLogopequeno(Image logopequeno) {
        this.logopequeno = logopequeno;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    static public boolean salvar(Parametrizacao p)
    {
        return ParametrizacaoBD.salvar(p);
    }
    
    static public boolean alterar(Parametrizacao p)
    {
        return ParametrizacaoBD.alterar(p);
    }
    
    static public Parametrizacao buscar()
    {
        return ParametrizacaoBD.buscar();
    }
}
