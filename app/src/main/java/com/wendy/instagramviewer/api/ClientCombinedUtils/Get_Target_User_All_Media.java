package com.wendy.instagramviewer.api.ClientCombinedUtils;

import com.wendy.instagramviewer.api.ClientUtils.Get_All_User_Media_Names_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_All_User_Names_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Target_Media_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class Get_Target_User_All_Media
{
    String server_ip;
    String server_port;
    String user_name;
    String user_password;
    String target_user;
    String media_name;
    String img_local_saved_path;

    public Get_Target_User_All_Media(String server_ip,
                                     String server_port,
                                     String user_name,
                                     String user_password,
                                     String target_user,
                                     String img_local_saved_path)
    {
        this.server_ip = server_ip;
        this.server_port = server_port;
        this.user_name = user_name;
        this.user_password = user_password;
        this.target_user = target_user;
        this.img_local_saved_path = img_local_saved_path;
    }

    public void perform()
    {
        // get all user names
        Get_All_User_Media_Names_Client g1 = new Get_All_User_Media_Names_Client(server_ip, server_port, user_name, user_password);
        API_AGENT a1 = new API_AGENT(g1);
        String rtn_str = a1.perform();
        if (! rtn_str.equals(API_Tags.RTN_FAIL))
        {
            for(String m_name: rtn_str.split("#"))
            {
                // save all user media to local path
                Get_Target_Media_Client t_g = new Get_Target_Media_Client(server_ip, server_port, user_name, user_password, target_user, m_name, img_local_saved_path);
                API_AGENT t_a = new API_AGENT(t_g);
                t_a.perform();
            }
        }
    }
}
