package com.wendy.instagramviewer.http_utils.ClientUtils;
import com.wendy.instagramviewer.http_utils.ClientNewBasic.HttpInvoker;
import com.wendy.instagramviewer.http_utils.StaticTags.API_Tags;

public class Down_Media_Client
{
    private HttpInvoker hi;
    private String req_url, file_name, file_path;
    public Down_Media_Client(String server_ip,
                             String server_port,
                             String user_name,
                             String user_password,
                             String target_user,
                             String media_name,
                             String img_local_saved_path)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.GET_USER_MEDIA +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.MED_NAME + media_name;
        hi = new HttpInvoker(req_url);
        file_name = media_name;
        file_path = img_local_saved_path;
    }

    public void perform()
    {
        hi.getFile(file_name, file_path);
    }
}
