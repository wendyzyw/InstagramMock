package com.wendy.instagramviewer.api.ClientUtils;
import com.wendy.instagramviewer.api.ClientNewBasic.HttpInvoker;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class Get_All_User_Media_Names_Client extends Client
{
    public Get_All_User_Media_Names_Client(String server_ip,
                           String server_port,
                           String user_name,
                           String user_password)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.GET_ALL_USER_MEDIA_NAMES +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password;
        hi = new HttpInvoker(req_url);
    }

    public String perform()
    {
        return hi.getRespond();
    }
}
