package com.wendy.instagramviewer.api.ClientCombinedUtils;

import com.wendy.instagramviewer.api.ClientUtils.Get_All_User_Names_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Target_Icon_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class Get_All_User_Icon
{
    private String server_ip;
    private String server_port;
    private String user_name;
    private String user_password;
    private String local_saved_path;
    public Get_All_User_Icon(String server_ip,
                             String server_port,
                             String user_name,
                             String user_password,
                             String local_saved_path)
    {
        this.server_ip = server_ip;
        this.server_port = server_port;
        this.user_name = user_name;
        this.user_password = user_password;
        this.local_saved_path = local_saved_path;
    }

    public void perform()
    {
        // get all user names
        Get_All_User_Names_Client g = new Get_All_User_Names_Client(server_ip, server_port, user_name, user_password);
        API_AGENT a = new API_AGENT(g);
        String rtn_str = a.perform();
        if (! rtn_str.equals(API_Tags.RTN_FAIL))
        {
            // get all user icon
            for(String u_name: rtn_str.split("#"))
            {
                Get_Target_Icon_Client t_g = new Get_Target_Icon_Client(server_ip, server_port, user_name, user_password, u_name, local_saved_path);
                API_AGENT t_a = new API_AGENT(t_g);
                t_a.perform();
            }
        }
    }
}
