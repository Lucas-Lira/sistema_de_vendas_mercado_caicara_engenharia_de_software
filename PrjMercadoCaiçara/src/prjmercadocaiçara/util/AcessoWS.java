package prjmercadocaiçara.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AcessoWS
{
    public static String consultaCep(String cep, String formato)
    {
        StringBuffer dados = new StringBuffer();
        URLConnection con;
        BufferedReader br = null;
        try {
            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato="+formato);
            con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine()))
                 dados.append(s);
            br.close();
        } catch (Exception ex)
        { System.out.println(ex); }
        finally
        { try{ br.close(); } catch(Exception e){ System.out.println(e); } }
        
        return dados.toString();
    }
}