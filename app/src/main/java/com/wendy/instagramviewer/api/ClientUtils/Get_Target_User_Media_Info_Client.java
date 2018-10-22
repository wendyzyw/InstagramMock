package com.wendy.instagramviewer.api.ClientUtils;
import com.wendy.instagramviewer.api.ClientNewBasic.HttpInvoker;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class Get_Target_User_Media_Info_Client extends Client
{
    public Get_Target_User_Media_Info_Client(String server_ip,
                                          String server_port,
                                          String user_name,
                                          String user_password,
                                             String target_user)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.GET_TARGET_USER_MEDIA_INFO +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password + "&" +
                API_Tags.TGT_USR + target_user;
        hi = new HttpInvoker(req_url);
    }

    //return json object
    //[{USR_NAME: u_name, MED_NAME: m_name, MED_TIME: m_time, MED_LOC: m_loc, MED_DES: m_des}
    public String perform()
    {
        return hi.getRespond();
    }
}
