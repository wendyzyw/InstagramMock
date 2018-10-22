package ClientBasics;

import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ClientRequester
{
    private String request_url;

    public ClientRequester(String req_url)
    {
        request_url = req_url;
    }

    public String sendRequest()
    {
        return RequestToSever();
    }

    private String RequestToSever()
    {
        String rtn_str = "";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try
        {
            HttpGet httpget = new HttpGet(request_url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            //System.out.println(response.getEntity().getContentLength());
            InputStream is = response.getEntity().getContent();
            //Scanner scan = new Scanner(is);
            rtn_str = (new Scanner(is)).nextLine();
            //System.out.println(scan.next());
        }
        catch (Exception e)
        {
            System.out.println("connection to server with error: " + e);
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (Exception e)
            {
                System.out.println("connection closed with error: " + e);
            }
        }
        return rtn_str;
    }
}
