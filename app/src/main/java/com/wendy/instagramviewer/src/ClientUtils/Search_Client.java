package ClientUtils;
import ClientNewBasic.HttpInvoker;
import StaticTags.API_Tags;

public class Search_Client
{
    private HttpInvoker hi;
    private String req_url;
    public Search_Client(String server_ip,
                         String server_port,
                         String user_name,
                         String user_password,
                         String key_word)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.SEARCH_USER +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.KEY_WORD + key_word;
        hi = new HttpInvoker(req_url);
    }

    public String perform()
    {
        return hi.getRespond();
    }
}
