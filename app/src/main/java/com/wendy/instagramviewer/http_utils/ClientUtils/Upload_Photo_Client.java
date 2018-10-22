package com.wendy.instagramviewer.http_utils.ClientUtils;
import com.wendy.instagramviewer.http_utils.ClientNewBasic.HttpInvoker;
import com.wendy.instagramviewer.http_utils.StaticTags.API_Tags;

public class Upload_Photo_Client
{
    private HttpInvoker hi;
    private String req_url, file_name, file_path;

    public Upload_Photo_Client(String server_ip,
                               String server_port,
                               String user_name,
                               String user_password,
                               String media_location,
                               String local_media_name,
                               String local_media_path)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.UPLOAD_USER_MEDIA +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.MED_LOC + media_location;
        hi = new HttpInvoker(req_url);
        file_name = local_media_name;
        file_path = local_media_path;
    }

    public String perform()
    {
        return hi.postRespond(file_name, file_path);
    }
}