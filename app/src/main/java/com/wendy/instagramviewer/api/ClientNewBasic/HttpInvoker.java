package com.wendy.instagramviewer.api.ClientNewBasic;

import com.wendy.instagramviewer.api.StaticTags.API_Tags;

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
            System.out.println("HTTP_INVOKER: " + file_path + File.separator + file_name);
            FileInputStream fStream = new FileInputStream(file_path + File.separator + file_name);
            System.out.println("-----output stream "+fStream);
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1){
                System.out.println("writing");
                ds.write(buffer, 0, length);
                System.out.println("writing 2");
            }

            ds.writeBytes(end);
            System.out.println("ds writebyte");
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            System.out.println("2");
            fStream.close();
            System.out.println("3");
            ds.flush();
            System.out.println("4");

            System.out.println("stats: "+connection.getResponseCode());
            InputStream connectionStream = connection.getInputStream();

            System.out.println("5: "+connectionStream);
            InputStreamReader inputStream = new InputStreamReader(connectionStream);
            System.out.println("6");
            BufferedReader reader = new BufferedReader(inputStream);
            System.out.println("7 buffere reader"+reader);
            res_line = reader.readLine();
            reader.close();
            connection.disconnect();
            return res_line;
        }
        catch (Exception e)
        {
            System.out.println("Exception Found: " + e);
            return API_Tags.RTN_FAIL;
        }
    }

    public String getFile(String file_name, String file_path)
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
            int total_len = 0;
            while ((len = is.read(buffer)) > 0)
            {
                total_len += len;
                output.write(buffer, 0, len);
            }

            if (total_len == API_Tags.RTN_FAIL.length() && output.toString().equals(API_Tags.RTN_FAIL))
                return API_Tags.RTN_FAIL;
//            FileOutputStream fos = new FileOutputStream(file_path + File.separator + file_name);
            FileOutputStream fos = new FileOutputStream(file_path + "\\" + file_name);
//            System.out.println(file_path + File.separator + file_name);
            output.writeTo(fos);
            output.flush();
            output.close();
            fos.close();
            is.close();
            return API_Tags.RTN_SUCC;
        }
        catch (Exception e)
        {
            System.out.println("Exception Found: " + e);
        }
        return API_Tags.RTN_FAIL;
    }
}