package ClientNewBasic;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpInvoker
{
    String req_url;
    public HttpInvoker(String req_url)
    {
        this.req_url = req_url;
    }

    public String getRespond()
    {
        try
        {
            String res_line = "";
            URL url = new URL(req_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            res_line = reader.readLine();
            reader.close();
            connection.disconnect();
            return res_line;
        }
        catch (Exception e)
        {
            System.out.println("Exception Found: " + e);
            return "";
        }
    }

    public String postRespond(String file_name, String file_path)
    {
        try
        {
            String res_line = "";
            String twoHyphens = "--";
            String boundary = "*****";
            String end = "\r\n";

            URL url = new URL(req_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.connect();

            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + file_name + "\"" + end);
            ds.writeBytes(end);

            FileInputStream fStream = new FileInputStream(file_path + "\\" + file_name);
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1)
                ds.write(buffer, 0, length);
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            fStream.close();
            ds.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            res_line = reader.readLine();
            reader.close();
            connection.disconnect();
            return res_line;
        }
        catch (Exception e)
        {
            System.out.println("Exception Found: " + e);
            return "";
        }
    }

    public void getFile(String file_name, String file_path)
    {
        try
        {
            URL url = new URL(req_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.connect();

            InputStream is = connection.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192]; // read in size of 4kB
            int len;
            while ((len = is.read(buffer)) > 0)
                output.write(buffer, 0, len);
            FileOutputStream fos = new FileOutputStream(file_path + "\\" + file_name);
            output.writeTo(fos);
            output.flush();
            output.close();
            fos.close();
            is.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception Found: " + e);
        }
    }
}