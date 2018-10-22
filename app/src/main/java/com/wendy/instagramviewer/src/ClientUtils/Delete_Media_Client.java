package ClientUtils;
import ClientNewBasic.HttpInvoker;
import StaticTags.API_Tags;

public class Delete_Media_Client
{
    private HttpInvoker hi;
    private String req_url;
    public Delete_Media_Client(String server_ip,
                         String server_port,
                         String user_name,
                         String user_password,
                        String media_name)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.DEL_MEDIA +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password + "&" +
                API_Tags.MED_NAME + media_name;
        hi = new HttpInvoker(req_url);
    }

    public String perform()
    {
        return hi.getRespond();
    }
}
