
package prjmercadocaiçara.db.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prjmercadocaiçara.db.modelos.Cliente;
import prjmercadocaiçara.db.modelos.Endereco;

public class ClienteBD 
{
    public static boolean salvar(Cliente cli)
    {
        boolean result = false;
        String sqlfiltroend = "end_rua = '@1' and end_cep=@2 and end_num=@3";
        sqlfiltroend = sqlfiltroend.replace("@1", cli.getEndereco().getRua());
        sqlfiltroend = sqlfiltroend.replace("@2", cli.getEndereco().getCep());
        sqlfiltroend = sqlfiltroend.replace("@3", ""+cli.getEndereco().getNumero());
        //Banco.getCon().setAutoCommit(false);
        Endereco end = EnderecoBD.buscarEndereco(sqlfiltroend);
        if(end != null)
        {
            cli.setEndereco(end);
            result = true;
        }
        else
        {
            result = EnderecoBD.salvar(cli.getEndereco());
            if(result)
                cli.getEndereco().setId(Banco.getCon().getMaxPK("endereco", "end_cod"));
        }
        if(result)
        {
            String sql = "insert into cliente(cli_nome,cli_cpf,cli_dtnasc,cli_telefone,cli_email,end_cod)"
                + " values('$1','$2','$3','$4','$5',$6)";
            sql = sql.replace("$1",cli.getNome());
            sql = sql.replace("$2",cli.getCpf());
            sql = sql.replace("$3",cli.getDtnasc().toString());
            sql = sql.replace("$4",cli.getEmail());
            sql = sql.replace("$5",cli.getTelefone());
            sql = sql.replace("$6",""+cli.getEndereco().getId());
            result = Banco.getCon().manipular(sql);
        }
        //Banco.getCon().setAutoCommit(true);
        return result;
    }
    
    public static boolean alterar(Cliente cli)
    {
        boolean result = false;
        String sqlfiltroend = "end_rua = '@1' and end_cep=@2 and end_num=@3";
        sqlfiltroend = sqlfiltroend.replace("@1", cli.getEndereco().getRua());
        sqlfiltroend = sqlfiltroend.replace("@2", cli.getEndereco().getCep());
        sqlfiltroend = sqlfiltroend.replace("@3", ""+cli.getEndereco().getNumero());
        //Banco.getCon().setAutoCommit(false);
        Endereco end = EnderecoBD.buscarEndereco(sqlfiltroend);
        if(end != null)
        {
            cli.setEndereco(end);
            result = true;
        }
        else
        {
            result = EnderecoBD.salvar(cli.getEndereco());
            if(result)
                cli.getEndereco().setId(Banco.getCon().getMaxPK("endereco", "end_cod"));
        }
        if(result)
        {
            String sql = "update cliente set cli_nome='$1',cli_cpf='$2',cli_dtnasc='$3',cli_telefone='$4',cli_email='$5',end_cod=$6 where cli_cod="+cli.getId();
            sql = sql.replace("$1",cli.getNome());
            sql = sql.replace("$2",cli.getCpf());
            sql = sql.replace("$3",cli.getDtnasc().toString());
            sql = sql.replace("$4",cli.getTelefone());
            sql = sql.replace("$5",cli.getEmail());
            sql = sql.replace("$6",""+cli.getEndereco().getId());
            result = Banco.getCon().manipular(sql);
        }
        //Banco.getCon().setAutoCommit(true);
        return result;
    }
    
    public static boolean deletar(int id)
    {
        return Banco.getCon().manipular("delete from cliente where cli_cod="+id);
    }
    
    public static List<Cliente> buscaPorNome(String nome)
    {
        String query = "select * from cliente where cli_nome like '"+nome+"%'";
        return busca(query);
    }
    
    public static List<Cliente> buscaPorCPF(String cpf)
    {
        String query = "select * from cliente where cli_cpf='"+cpf+"'";
        return busca(query);
    }
    
    private static List<Cliente> busca(String query)
    {
        List<Cliente> lista = new ArrayList();
        ResultSet rs = Banco.getCon().consultar(query);
        try
        {
            while(rs.next())
            {
                Cliente cli = new Cliente();
                cli.setId(rs.getInt("cli_cod"));
                cli.setNome(rs.getString("cli_nome"));
                cli.setCpf(rs.getString("cli_cpf"));
                cli.setDtnasc(rs.getDate("cli_dtnasc").toLocalDate());
                cli.setEmail(rs.getString("cli_email"));
                cli.setTelefone(rs.getString("cli_telefone"));
                cli.setEndereco(EnderecoBD.buscarEndereco("end_cod="+rs.getInt("end_cod")));
                lista.add(cli);
            }
        }
        catch(SQLException e){}
        return lista;
    }
    
    public List<Cliente> buscaPorCodigoF(int codigo)
    {
        String query = "select * from cliente where cli_cod = " + codigo;
        return busca(query);
    }
}
