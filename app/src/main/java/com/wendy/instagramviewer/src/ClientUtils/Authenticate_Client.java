package ClientUtils;
import ClientNewBasic.HttpInvoker;
import StaticTags.API_Tags;

public class Authenticate_Client
{
    private HttpInvoker hi;
    private String req_url;
    public Authenticate_Client( String server_ip,
                                String server_port,
                                String user_name,
                                String user_password,
                                String user_email)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.AUTH_USER +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password + "&" +
                API_Tags.USR_EMAIL + user_email;
        hi = new HttpInvoker(req_url);
    }

    public String perform()
    {
        return hi.getRespond();
    }
}
