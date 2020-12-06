package prjmercadocaiçara.db.persistencia;

import prjmercadocaiçara.db.modelos.Funcionario;

public class Banco
{
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO SINGLETON:
    // ------------------------------------------------------------------------------------------------------------------
    static private Conexao con = null;
    
    private Banco(){ }
    static private int acesso;
    static private Funcionario funcionario;
    static int id_fun;
    
    static public boolean conectar()
    {
        id_fun = (-1);
        acesso = 2; // 'DEFAULT' PARA O CONTROLE DO LOGIN REALIZADO PELO USUÁRIO!
        if(con == null)
        {
            con = new Conexao();
            return con.conectar("jdbc:postgresql://localhost/", "mercadocaicara", "postgres", "postgres123");
        }
        return false;
    }
    
    static public Conexao getCon(){ return con; }
    // ------------------------------------------------------------------------------------------------------------------

    public static int getAcesso()
    { return acesso; }

    public static void setAcesso(int acesso1)
    { acesso = acesso1; }
    
    public static int getId_fun()
    { return id_fun; }

    public static void setId_fun(int id_fun)
    { Banco.id_fun = id_fun; }
    
    // ------------------------------------------------------------------------------------------------------------------
    // NÃO UTILIZAR [SERÁ REMOVIDO]:
    // ------------------------------------------------------------------------------------------------------------------
    public static Funcionario getFuncionario()
    { return funcionario; }

    public static void setFuncionario(Funcionario funcionario2)
    { Banco.funcionario = funcionario2; }
    // ------------------------------------------------------------------------------------------------------------------
}