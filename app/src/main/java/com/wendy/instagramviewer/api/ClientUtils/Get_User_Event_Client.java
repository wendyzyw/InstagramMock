package com.wendy.instagramviewer.api.ClientUtils;
import com.wendy.instagramviewer.api.ClientNewBasic.HttpInvoker;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class Get_User_Event_Client extends Client
{
    public Get_User_Event_Client(String server_ip,
                                   String server_port,
                                   String user_name,
                                   String user_password)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.GET_USER_EVENTS +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password;
        hi = new HttpInvoker(req_url);
    }
    //[{CMTOR_NAME: cmtor_name, CMT_TEXT: cmt_text, CMT_TIME: cmt_time, CMT_LOC: cmt_loc, AT_NAMES: at_name_list}]
    public String perform()
    {
        return hi.getRespond();
    }
}
