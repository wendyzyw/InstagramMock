package ClientUtils;

import StaticTags.API_Tags;
import ClientNewBasic.HttpInvoker;

public class Get_Recommend_User_C_Client
{
    private HttpInvoker hi;
    private String req_url;
    public Get_Recommend_User_C_Client(String server_ip,
                                       String server_port,
                                       String user_name,
                                       String user_password)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.RCMD_CMT_USER +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password;
        hi = new HttpInvoker(req_url);
    }

    public String perform()
    {
        return hi.getRespond();
    }
}
