package com.wendy.instagramviewer.http_utils.ClientUtils;
import com.wendy.instagramviewer.http_utils.ClientNewBasic.HttpInvoker;
import com.wendy.instagramviewer.http_utils.StaticTags.API_Tags;

public class Recommend_Image_Client
{
    private HttpInvoker hi;
    private String req_url;
    public Recommend_Image_Client(  String server_ip,
                                    String server_port,
                                    String user_name,
                                    String user_password)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.RCMD_IMAGE_USER +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password;
        hi = new HttpInvoker(req_url);
    }

    public String perform()
    {
        return hi.getRespond();
    }
}
