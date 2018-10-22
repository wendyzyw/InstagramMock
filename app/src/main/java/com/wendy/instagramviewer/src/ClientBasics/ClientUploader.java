package ClientBasics;

import java.io.File;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ClientUploader
{
    private String post_url;
    private String local_filename;
    private String local_file_path;

    public ClientUploader(String post_url, String local_filename, String local_file_path)
    {
        this.post_url = post_url;
        this.local_filename = local_filename;
        this.local_file_path = local_file_path;
    }

    public void postFile()
    {
        PostToServer();
    }

    public void postFile(String post_url, String local_filename, String local_file_path)
    {
        this.post_url = post_url;
        this.local_filename = local_filename;
        this.local_file_path = local_file_path;
        PostToServer();
    }

    private void PostToServer()
    {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try
        {
            HttpPost httppost = new HttpPost(post_url);
            FileBody bin = new FileBody(new File(local_file_path + File.separator + local_filename));
            MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
            reqEntity.addPart("file", bin);
            httppost.setEntity(reqEntity.build());
            httpclient.execute(httppost);
            System.out.println("client post sent successfully");
        }
        catch (Exception e)
        {
            System.out.println("client post sent with error: " + e);
        }
        finally
        {
            try
            {
                httpclient.close();
                System.out.println("client connection closed successfully");
            }
            catch (Exception e)
            {
                System.out.println("client connection closed with error: " + e);
            }
        }
    }
}
