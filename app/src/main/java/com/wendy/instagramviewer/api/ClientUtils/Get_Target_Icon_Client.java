package com.wendy.instagramviewer.api.ClientUtils;
import com.wendy.instagramviewer.api.ClientNewBasic.HttpInvoker;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class Get_Target_Icon_Client extends Client
{
    public Get_Target_Icon_Client(String server_ip,
                                  String server_port,
                                  String user_name,
                                  String user_password,
                                  String target_user,
                                  String img_local_saved_path)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.GET_USER_ICON +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password + "&" +
                API_Tags.TGT_USR + target_user;
        hi = new HttpInvoker(req_url);
        file_name = user_name + "_icon.jpg";
        file_path = img_local_saved_path;
    }

    public String perform()
    {
        if(hi.getFile(file_name, file_path).equals(API_Tags.RTN_SUCC))
            return file_path + "\\" + file_name;
        else
            return API_Tags.RTN_FAIL;
    }
}
