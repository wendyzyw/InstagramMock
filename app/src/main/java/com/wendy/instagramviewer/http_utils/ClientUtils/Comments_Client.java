package com.wendy.instagramviewer.http_utils.ClientUtils;
import com.wendy.instagramviewer.http_utils.StaticTags.API_Tags;
import com.wendy.instagramviewer.http_utils.ClientNewBasic.HttpInvoker;

public class Comments_Client
{
    private HttpInvoker hi;
    private String req_url;

    public Comments_Client( String server_ip,
                             String server_port,
                             String user_name,
                             String user_password,
                             String target_user,
                             String media_name)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.GET_USER_MEDIA_COMMENTS +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.TGT_USR + target_user + "&" +
                    API_Tags.MED_NAME + media_name;
        hi = new HttpInvoker(req_url);
    }

    public String perform()
    {
        return hi.getRespond();
    }
}
