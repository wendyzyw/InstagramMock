package ClientBasics;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public class ClientDownloader
{
    private String get_url;
    private String get_local_path;
    private String get_local_name;
    private static final int chunk_size = 8192;

    public ClientDownloader(String get_url, String get_local_path, String get_local_name)
    {
        this.get_url = get_url;
        this.get_local_path = get_local_path;
        this.get_local_name = get_local_name;
    }

    public void getFile()
    {
        GetFromServer();
    }

    private void GetFromServer()
    {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpGet httpget = new HttpGet(get_url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try
            {
                System.out.println(response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                System.out.println(httpEntity.getContentLength());
                InputStream is = httpEntity.getContent();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[chunk_size]; // read in size of 4kB
                int len;
                while ((len = is.read(buffer)) > 0)
                    output.write(buffer, 0, len);
                FileOutputStream fos = new FileOutputStream(get_local_path + "\\" + get_local_name);
                output.writeTo(fos);
                output.flush();
                output.close();
                fos.close();
                EntityUtils.consume(httpEntity);
            }
            catch (Exception e)
            {
                System.out.println("client get content with error: " + e);
            }
            finally
            {
                response.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("client get sent with error: " + e);
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (Exception e)
            {
                System.out.println("client connection closed with error: " + e);
            }
        }
    }
}
