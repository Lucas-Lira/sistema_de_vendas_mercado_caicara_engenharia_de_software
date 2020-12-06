package prjmercadocaiçara.db.controladoras;

import java.awt.Image;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import prjmercadocaiçara.db.modelos.Cidade;
import prjmercadocaiçara.db.modelos.Endereco;
import prjmercadocaiçara.db.modelos.Estado;
import prjmercadocaiçara.db.modelos.Funcionario;
import prjmercadocaiçara.db.modelos.Horario;
import prjmercadocaiçara.db.modelos.Turno;
import prjmercadocaiçara.db.persistencia.Banco;
import prjmercadocaiçara.util.HorarioAux;
import prjmercadocaiçara.util.facade.DisplayComboBox;

public class CtrlFuncionario
{
    private Funcionario fun_atual = null;
    private Funcionario fun_atual_aux = null;
    private ArrayList<Funcionario> list_fun = null;
    private ArrayList<Funcionario> list_fun2 = null;
    private ArrayList<Cidade> list_cid = null;
    private ArrayList<Turno> list_turno = null;
    
    // ------------------------------------------------------------------------------------------------------------------
    // PARA A REALIZAÇÃO DA REVALIDAÇÃO DOS DADOS QUE SERÃO EFETIVADOS:
    // ------------------------------------------------------------------------------------------------------------------
    private String cpf = "", login = "", senha = "";
    private int nivel = 2; // DEFAULT!
    // ------------------------------------------------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO SINGLETON:
    // ------------------------------------------------------------------------------------------------------------------
    private static CtrlFuncionario ctrl_fun = null;
    public static CtrlFuncionario instanciarCtrlFun()
    {
        if(ctrl_fun == null)
            ctrl_fun = new CtrlFuncionario();
        
        return ctrl_fun;
    }
    
    private CtrlFuncionario()
    {
        clearFunAtual();
        list_fun = new ArrayList();
        list_cid = new ArrayList();
        list_turno = new ArrayList();
        
        list_fun2 = (ArrayList<Funcionario>) Funcionario.get("");
    }
    // ------------------------------------------------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------------------------------------------------
    // PADRÃO FAÇADE:
    // ------------------------------------------------------------------------------------------------------------------
    public void clearFunAtual()
    {
        fun_atual = new Funcionario();
        fun_atual_aux = new Funcionario();
        login = "";
        senha = "";
        cpf = "";
        nivel = 2;
    }
    
    public void carregarComboBoxCidade(ComboBox cbcidade, String filtro)
    {
        cbcidade.getItems().clear();
        Cidade cid = null;
        DisplayComboBox obj = null;
        
        list_cid = (ArrayList<Cidade>) Cidade.buscarTodas();
        for(int i = 0; i < list_cid.size(); i++)
        {
            cid = list_cid.get(i);
            obj = new DisplayComboBox(cid.getNome() + "/" + cid.getEstado().getSigla(), "" + cid.getId());
            
            cbcidade.getItems().add(obj);
        }
    }
    
    public void carregarComboBoxDiaSemana(ComboBox cbdiasemana)
    {
        cbdiasemana.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("Segunda - Feira", "0");
        cbdiasemana.getItems().add(obj);
        obj = new DisplayComboBox("Terça - Feira", "1");
        cbdiasemana.getItems().add(obj);
        obj = new DisplayComboBox("Quarta - Feira", "2");
        cbdiasemana.getItems().add(obj);
        obj = new DisplayComboBox("Quinta - Feira", "3");
        cbdiasemana.getItems().add(obj);
        obj = new DisplayComboBox("Sexta - Feira", "4");
        cbdiasemana.getItems().add(obj);
        obj = new DisplayComboBox("Sábado", "5");
        cbdiasemana.getItems().add(obj);
    }
    
    public void carregarComboBoxNivel(ComboBox cbnivel)
    {
        cbnivel.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("ADMINISTRADOR", "1");
        cbnivel.getItems().add(obj);
        obj = new DisplayComboBox("USUÁRIO", "2");
        cbnivel.getItems().add(obj);
    }
    
    public void carregarComboBoxFiltroConsulta(ComboBox cbfiltro)
    {
        cbfiltro.getItems().clear();
        DisplayComboBox obj = null;
        
        obj = new DisplayComboBox("Código", "0");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("Nome", "1");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("Nível", "2");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("CPF", "3");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("Dt. Nascimento", "4");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("Telefone", "5");
        cbfiltro.getItems().add(obj);
        obj = new DisplayComboBox("E-mail", "6");
        cbfiltro.getItems().add(obj);
    }
    
    public void carregarComboBoxTurno(ComboBox cbturno, String filtro)
    {
        cbturno.getItems().clear();
        Turno turno = null;
        DisplayComboBox obj = null;
        
        list_turno = (ArrayList<Turno>) Turno.buscaTurnoPeriodo(filtro);
        for(int i = 0; i < list_turno.size(); i++)
        {
            turno = list_turno.get(i);
            obj = new DisplayComboBox(turno.getPeriodo(), "" + turno.getId());
            
            cbturno.getItems().add(obj);
        }
    }
    
    public static int getUltimoIdFuncionarioSalvo()
    {
        return Funcionario.getMaxPKFuncionario();
    }
    
    public String getHorarioInicioListTurno(int pos)
    {
        if(pos >= 0 && pos < list_turno.size())
            return list_turno.get(pos).getHor_inicio();
        return "";
    }
    
    public String getHorarioFimListTurno(int pos)
    {
        if(pos >= 0 && pos < list_turno.size())
            return list_turno.get(pos).getHor_fim();
        return "";
    }
    
    public int buscaCodigoFunLoginSenha(String login, String senha)
    {
        list_fun = (ArrayList<Funcionario>) fun_atual.buscaFunLoginSenha(login, senha);
        if(!list_fun.isEmpty())
        {
            fun_atual = list_fun.get(0);
            return fun_atual.getId();
        }
        return (-1);
    }
    
    public int getCodigoFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getId();
        return (-1);
    }
    
    public String getNomeFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getNome();
        return "";
    }
    
    public String getCPFFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getCpf();
        return "";
    }
    
    public String getEmailFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getEmail();
        return "";
    }
    
    public String getTelefoneFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getTelefone();
        return "";
    }
    
    public String getLoginFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getLogin();
        return "";
    }
    
    public String getSenhaFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getSenha();
        return "";
    }
    
    public LocalDate getDtNascimentoFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getDt_nasc();
        return null;
    }
    
    public int getIndiceSelecaoComboBoxCidadesFunAtual()
    {
        if(fun_atual != null)
        {
            int pos = 0;
            boolean achou = false;
            for(int i = 0; i < list_cid.size() && !achou; i++)
                if(fun_atual.getEndereco().getCidade().getId() == 
                        list_cid.get(i).getId())
                {
                    pos = i;
                    achou = true;
                }
            
            return pos;
        }
        return (-1);
    }
    
    public String getLogradouroFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getEndereco().getRua();
        return "";
    }
    
    public String getBairroFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getEndereco().getBairro();
        return "";
    }
    
    public int getEnderecoNumFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getEndereco().getNumero();
        return (-1);
    }
    
    public String getCEPFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getEndereco().getCep();
        return "";
    }
    
    public Image getImagemFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getImagem();
        return null;
    }
    
    public int getNivelFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getNivel();
        return (-1);
    }
    
    public boolean validaCPF(String Cpf)
    {
        return Funcionario.validaCPF(Cpf);
    }
    
    public boolean buscaCPFFun(String cpf)
    {
        if(!fun_atual.buscaCPFFun(cpf).isEmpty())
            return true;
        return false;
    }
    
    public boolean validaFun(int cod, String nome, int n, String cpf_aux, String email, String telefone, String login_aux, 
            String senha_aux, LocalDate data, int endcod, String logradouro, String bairro, int endnum, String cep, 
            int cidcod, boolean alterou_login_senha, ArrayList<Horario> list_hor)
    {
        boolean validou =  false;
        
        if(nome.length() >= 3)
        {
            if(n >= 1 && n <= 2)
            {
                int qtde_fun_adm =  Funcionario.buscaFunAdm().size();
                
                if(n == 1 || (n == nivel) || ((n == 2) && (n != nivel) && qtde_fun_adm > 1))
                {
                    if(cpf.length() == 14 && Funcionario.validaCPF(cpf))
                    {
                        if((cod == 0 && !buscaCPFFun(cpf)) || (cod > 0 && !buscaCPFFun(cpf)) || (cod > 0 && cpf_aux.equals(this.cpf)))
                        {
                            if(/*email.length() > 5*/true)
                            {
                                if(telefone.length() >= 13 && telefone.length() <= 14)
                                {
                                    if(login_aux.length() > 0 && senha_aux.length() > 0)
                                    {
                                        if(!alterou_login_senha || (alterou_login_senha && buscaCodigoFunLoginSenha(login_aux.toUpperCase(), senha_aux) == (-1)))
                                        {
                                            if(data != null && data.isBefore(LocalDate.now()))
                                            {
                                                if(cidcod > 0)
                                                {
                                                    if(logradouro.length() >= 5)
                                                    {
                                                        if(bairro.length() >= 5)
                                                        {
                                                            if(endnum > 0)
                                                            {
                                                                if(cep.length() == 8)
                                                                {
                                                                    if(list_hor.size() > 0)
                                                                        validou = true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return validou;
    }
    
    public String removeMascaraCEP(String cep)
    {
        return Endereco.retiraMascaraCEP(cep);
    }
    
    public void setImagemFunAtual(Image img)
    {
        fun_atual.setImagem(img);
    }
    
    public Image recuperarImagemFun(int id)
    {
        return Funcionario.recuperarImagem(id);
    }
    
    public int getCodigoEnderecoFunAtual()
    {
        if(fun_atual != null)
            return fun_atual.getEndereco().getId();
        return (-1);
    }
    
    public void inicializarTableViewFuncionario(Object tabela)
    {
        try
        {
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(0)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(1)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(2)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(3)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(4)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(4)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(5)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(5)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(6)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(6)));
        }
        catch(Exception ex){ }
    }
    
    public void inicializarTableViewHorariosFuncionario(Object tabela)
    {
        try
        {
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(0)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(1)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(2)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
            ((TableColumn <ArrayList<String>, String>)((TableView)tabela).getColumns().get(3)).setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(3)));
        }
        catch(Exception ex){ }
    }
    
    public void carregarTableViewFuncionario(TableView tabela, int filtro, String dado)
    {
        tabela.getItems().clear();
        ArrayList <Funcionario> list_aux = buscaFuncionarioFiltro(filtro, dado);
        
        ArrayList<ArrayList<String>> list = new ArrayList();
        Funcionario f = null;
        for(int i = 0; i < list_aux.size(); i++)
        {
            f = list_aux.get(i);
            
            ArrayList<String> linha = new ArrayList();
            linha.add("" + f.getId());
            linha.add("" + f.getNome());
            linha.add("" + f.getNivel());
            linha.add("" + f.getCpf());
            linha.add("" + f.getDt_nasc().toString());
            linha.add("" + f.getTelefone());
            linha.add("" + f.getEmail());
            
            list.add(linha);
        }
        
        tabela.setItems(FXCollections.observableArrayList(list));
    }
    
    public void carregarTableViewHorariosFuncionario(TableView tabela)
    {
        tabela.getItems().clear();
        ArrayList <HorarioAux>list_aux = getHorariosFuncionario();
        
        ArrayList<ArrayList<String>> list = new ArrayList();
        HorarioAux ha = null;
        for(int i = 0; i < list_aux.size(); i++)
        {
            ha = list_aux.get(i);
            
            ArrayList<String> linha = new ArrayList();
            linha.add("" + ha.getDiasemana());
            linha.add("" + ha.getTurno());
            linha.add("" + ha.getHorini());
            linha.add("" + ha.getHorfim());
            
            list.add(linha);
        }
        
        tabela.setItems(FXCollections.observableArrayList(list));
    }
    
    public ArrayList buscaFuncionarioFiltro(int flag, String dado)
    {
        list_fun = (ArrayList) Funcionario.buscaFuncionarioFiltro(flag, dado);
        return list_fun; // carregarFuncionarioVisao(list_fun);
    }
    
    public void selecionaFuncionarioLista(int pos)
    {
        if(pos >= 0 && pos < list_fun.size())
        {
            clearFunAtual();
            fun_atual = list_fun.get(pos);
            
            cpf = fun_atual.getCpf();
            login = fun_atual.getLogin();
            senha = fun_atual.getSenha();
            nivel = fun_atual.getNivel();
        }
    }
    
    public void carregaFunAtual(int cod, String nome, int nivel, String cpf, String email, String telefone, String login, 
            String senha, LocalDate data, int endcod, String logradouro, String bairro, int endnum, String cep, int cidcod)
    {
        Endereco end = new Endereco();
        Estado est = new Estado();
        Cidade cid = new Cidade();
        fun_atual.setId(cod);
        fun_atual.setNome(nome);
        fun_atual.setNivel(nivel);
        fun_atual.setCpf(cpf);
        fun_atual.setEmail(email);
        fun_atual.setTelefone(telefone);
        fun_atual.setLogin(login);
        fun_atual.setSenha(senha);
        fun_atual.setDt_nasc(data);
        
        boolean achou = false;
        int i = 0;
        for(; i < list_cid.size() && !achou; i++)
            if(list_cid.get(i).getId() == cidcod)
                achou = true;
        
        if(achou)
        {
            i -= 1;
            cid = list_cid.get(i);
        }
        
        end.setId(endcod);
        end.setRua(logradouro);
        end.setBairro(bairro);
        end.setNumero(endnum);
        end.setCep(cep);
        
        end.setCidade(cid);
        fun_atual.setEndereco(end);
    }
    
    public boolean addHorario(int dia, int pos_turno)
    {
        boolean inseriu = buscaDiaTrabalhoHorario(dia);
        
        if(!inseriu)
        {
            Horario h = new Horario(dia, list_turno.get(pos_turno));
            fun_atual.getHorarios().add(h);
            
            inseriu = true;
        }
        
        return inseriu;
    }
    
    public int buscaFunAdm()
    {
        return Funcionario.buscaFunAdm().size();
    }
    
    public boolean buscaDiaTrabalhoHorario(int dia)
    {
        boolean achou = false;
        
        for(int i = 0; i < fun_atual.getHorarios().size(); i++)
            if(dia == fun_atual.getHorarios().get(i).getDia_semana())
                achou = true;
        
        return achou;
    }
    
    public ArrayList<HorarioAux> getHorariosFuncionario()
    {
        ArrayList<HorarioAux> list = new ArrayList();
        Horario h = null;
        HorarioAux ha_v = null;
        String str = "";
        
        for(int i = 0; i < fun_atual.getHorarios().size(); i++)
        {
            h =  fun_atual.getHorarios().get(i);
            
            if(h.getDia_semana() == 1)
                str = "Segunda - Feira";
            else
            {
                if(h.getDia_semana() == 2)
                    str = "Terça - Feira";
                else
                {
                    if(h.getDia_semana() == 3)
                        str = "Quarta - Feira";
                    else
                    {
                        if(h.getDia_semana() == 4)
                            str = "Quinta - Feira";
                        else
                        {
                            if(h.getDia_semana() == 5)
                                str = "Sexta - Feira";
                            else
                                str = "Sábado";
                        }
                    }
                }
            }
            
            ha_v = new HorarioAux(str, h.getTurno().getPeriodo(), h.getTurno().getHor_inicio(), 
                h.getTurno().getHor_fim());
            
            list.add(ha_v);
        }
        
        return list;
    }
    
    public boolean delHorario(int pos)
    {
        if(pos >= 0 && pos < fun_atual.getHorarios().size())
        {
            fun_atual.getHorarios().remove(pos);
            return true;
        }
        return false;
    }
    
    public boolean deletarFuncionario()
    {
        boolean executou = false;
        
        if(fun_atual.getId() > 0)
        {
            int erros = 0;
            
            try
            {
                Banco.getCon().getConnect().setAutoCommit(false);
                
                if(fun_atual.getHorarios() != null && fun_atual.getHorarios().size() > 0)
                    if(!Horario.apagarHorFunCod(fun_atual.getId()))
                        erros++;
                
                if(!Funcionario.apagar(fun_atual))
                    erros++;
            
                if(!Endereco.apagar(fun_atual.getEndereco().getId()))
                    erros++;
                
                if(erros > 0)
                { Banco.getCon().getConnect().rollback(); }
                else
                {
                    executou = true;
                    list_fun2 = (ArrayList<Funcionario>) Funcionario.get("");
                    Banco.getCon().getConnect().commit();
                }
            }
            catch(SQLException e)
            { try{ Banco.getCon().getConnect().rollback(); }catch(SQLException ex){ } }
            finally
            { try{ Banco.getCon().getConnect().setAutoCommit(true); }catch(SQLException ex){ } }
        }
        
        return executou;
    }
    
    public boolean salvarFun()
    {
        boolean executou = false;
        
        if(validaFun(fun_atual.getId(), fun_atual.getNome(), fun_atual.getNivel(), fun_atual.getCpf(), fun_atual.getEmail(), 
                fun_atual.getTelefone(), fun_atual.getLogin(), fun_atual.getSenha(), fun_atual.getDt_nasc(), 
                fun_atual.getEndereco().getId(), fun_atual.getEndereco().getRua(), fun_atual.getEndereco().getBairro(), 
                fun_atual.getEndereco().getNumero(), fun_atual.getEndereco().getCep(), fun_atual.getEndereco().getCidade().getId(), 
                false, fun_atual.getHorarios()))
        {
            int erros = 0;
            
            try
            {
                Banco.getCon().getConnect().setAutoCommit(false);
            
                if(!Endereco.salvar(fun_atual.getEndereco()))
                    erros++;
                
                if(!Funcionario.salvar(fun_atual))
                    erros++;
                
                for(int i = 0; i < fun_atual.getHorarios().size(); i++)
                {
                    if(!Horario.salvar(fun_atual.getHorarios().get(i), fun_atual.getId()))
                        erros++;
                }
                
                if(erros > 0)
                { Banco.getCon().getConnect().rollback(); }
                else
                {
                    executou = true;
                    list_fun2 = (ArrayList<Funcionario>) Funcionario.get("");
                    Banco.getCon().getConnect().commit();
                }
            }
            catch(SQLException e)
            { try{ Banco.getCon().getConnect().rollback(); }catch(SQLException ex){ } }
            finally
            { try{ Banco.getCon().getConnect().setAutoCommit(true); }catch(SQLException ex){ } }
        }
        
        return executou;
    }
    
    public boolean alterarFun()
    {
        boolean executou = false, checar_cadastro = false;
        
        if(fun_atual.getId() > 0)
        {
            if(!login.equals(fun_atual.getLogin()) || !senha.equals(fun_atual.getSenha()))
                checar_cadastro = true;

            if(validaFun(fun_atual.getId(), fun_atual.getNome(), fun_atual.getNivel(), fun_atual.getCpf(), fun_atual.getEmail(), 
                    fun_atual.getTelefone(), fun_atual.getLogin(), fun_atual.getSenha(), fun_atual.getDt_nasc(), 
                    fun_atual.getEndereco().getId(), fun_atual.getEndereco().getRua(), fun_atual.getEndereco().getBairro(), 
                    fun_atual.getEndereco().getNumero(), fun_atual.getEndereco().getCep(), fun_atual.getEndereco().getCidade().getId(), 
                    checar_cadastro, fun_atual.getHorarios()))
            {
                int erros = 0;
            
                try
                {
                    Banco.getCon().getConnect().setAutoCommit(false);

                    if(!Endereco.alterar(fun_atual.getEndereco()))
                        erros++;

                    if(!Funcionario.alterar(fun_atual))
                        erros++;

                    // DELETANDO TODOS OS HORÁRIOS DESTE FUNCIONÁRIO PARA O RECADASTRO
                    if(!Horario.apagarHorFunCod(fun_atual.getId()))
                        erros++;
                    
                    for(int i = 0; i < fun_atual.getHorarios().size(); i++)
                    {
                        if(!Horario.salvar(fun_atual.getHorarios().get(i), fun_atual.getId()))
                            erros++;
                    }

                    if(erros > 0)
                    { Banco.getCon().getConnect().rollback(); }
                    else
                    {
                        executou = true;
                        list_fun2 = (ArrayList<Funcionario>) Funcionario.get("");
                        Banco.getCon().getConnect().commit();
                    }
                }
                catch(SQLException e)
                { try{ Banco.getCon().getConnect().rollback(); }catch(SQLException ex){ } }
                finally
                { try{ Banco.getCon().getConnect().setAutoCommit(true); }catch(SQLException ex){ } }
            }
        }
        
        return executou;
    }
    // ------------------------------------------------------------------------------------------------------------------
    
    public Funcionario getFuncionarioAtual()
    { return fun_atual; }
    
    // ========================================================================
    public void carregarComboBoxFuncionario(ComboBox c)
    {
        if(!list_fun2.isEmpty())
            for (int i = 0; i < list_fun2.size(); i++)
                c.getItems().add(list_fun2.get(i).getNome());
    }
    
    public void selecionaFuncionarioComboboxId(int id, ComboBox c)
    {
        int i = 0;
        while(i < list_fun2.size() && list_fun2.get(i).getId() != id)
            i++;
        c.getSelectionModel().select(i);
    }
    
    public void selecionaFuncionarioListaNome(String nome)
    {
        if(!list_fun2.isEmpty())
        {
            int i = 0;
            while(i < list_fun2.size() && !list_fun2.get(i).getNome().equals(nome))
                i++;
            
            if(i >= 0 && i < list_fun2.size())
            {
                clearFunAtual();
                fun_atual = list_fun2.get(i);

                cpf = fun_atual.getCpf();
                login = fun_atual.getLogin();
                senha = fun_atual.getSenha();
                nivel = fun_atual.getNivel();
            }
        }
    }
    
    public int buscaIdFundionarioPorNome(String nome)
    {
       int i = 0;
        while(i < list_fun2.size() && !list_fun2.get(i).getNome().equals(nome))
            i++;
        return list_fun2.get(i).getId();
    }
    
    
    public Map getDadosFuncionarioAtual()
    {
        Map func = new HashMap();
        func.put("codigo", fun_atual.getId());
        func.put("nome", fun_atual.getNome());
        func.put("cpf", fun_atual.getCpf());
        func.put("image", fun_atual.getImagem());
        return func;
    }
    
    public Map getHorarioFuncionarioAtual()
    {
        Map hr = new HashMap();
        fun_atual.getHorarios().forEach((y)->{
            if(y.getDia_semana() == LocalDate.now().getDayOfWeek().getValue())
                if(y.getTurno().getHor_inicio().compareTo(LocalTime.now().toString()) <= 0 
                    && y.getTurno().getHor_fim().compareTo(LocalTime.now().toString()) > 0)
                {
                    hr.put("hrinicio", y.getTurno().getHor_inicio());
                    hr.put("hrfim", y.getTurno().getHor_fim());
                }
        });
        return hr;
    }
}