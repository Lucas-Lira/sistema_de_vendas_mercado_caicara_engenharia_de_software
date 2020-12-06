package prjmercadocaiçara.ui.template;

import java.sql.SQLException;
import java.util.ArrayList;
import prjmercadocaiçara.db.persistencia.Banco;

public abstract class CtrlMovimentacao
{
    public static ArrayList<Object> itens;
    public static ArrayList<Object> parcelas;
    
    public final boolean gravar()
    {
        boolean executou = false;
        int erros = 0;
        try
        {
            Banco.getCon().getConnect().setAutoCommit(false);
            
            if(!gravarObjeto())
                erros++;
            
            for(int i = 0; i < itens.size(); i++)
            {
                if(!gravarItem(i))
                    erros++;
                if(!atualizarEstoqueProduto(i, false))
                    erros++;
            }
            
            for(int i = 0; i < parcelas.size(); i++)
                if(!gravarParcelas(i))
                    erros++;
            
            if(erros == 0)
            {
                executou = true;
                Banco.getCon().getConnect().commit();
            }
            else
                Banco.getCon().getConnect().rollback();
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
            try{ Banco.getCon().getConnect().rollback(); }catch(SQLException ex2){ }
        }
        finally
        { try{ Banco.getCon().getConnect().setAutoCommit(true); }catch(SQLException ex3){ } }
        
        return executou;
    }
    
    public final boolean apagar()
    {
        boolean executou = false;
        int erros = 0;
        try
        {
            Banco.getCon().getConnect().setAutoCommit(false);
            
            for(int i = 0; i < parcelas.size(); i++)
                if(!removerParcelas(i))
                    erros++;
            
            for(int i = 0; i < itens.size(); i++)
            {
                if(!removerItem(i))
                    erros++;
                if(!atualizarEstoqueProduto(i, true))
                    erros++;
            }
            
            if(!removerObjeto())
                erros++;
            
            if(erros == 0)
            {
                executou = true;
                Banco.getCon().getConnect().commit();
            }
            else
                Banco.getCon().getConnect().rollback();
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
            try{ Banco.getCon().getConnect().rollback(); }catch(SQLException ex2){ }
        }
        finally
        { try{ Banco.getCon().getConnect().setAutoCommit(true); }catch(SQLException ex3){ } }
        
        return executou;
    }
    
    public abstract boolean gravarObjeto();
    public abstract boolean gravarItem(int indice);
    
    // ESTOQUE DO PRODUTO E A QTDE DO LOTE
    public abstract boolean atualizarEstoqueProduto(int indice, boolean remover);
    public abstract boolean gravarParcelas(int indice);
    
    public abstract boolean removerObjeto();
    public abstract boolean removerItem(int indice);
    public abstract boolean removerParcelas(int indice);
}