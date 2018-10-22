package ClientUtils;
import ClientNewBasic.HttpInvoker;
import StaticTags.API_Tags;

public class Put_Icon_Client
{
    private HttpInvoker hi;
    private String req_url, file_name, file_path;

    public Put_Icon_Client(String server_ip,
                           String server_port,
                           String user_name,
                           String user_password,
                           String local_media_name,
                           String local_media_path)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.UPLOAD_USER_ICON +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password;
        hi = new HttpInvoker(req_url);
        file_name = local_media_name;
        file_path = local_media_path;
    }

    public String perform()
    {
        return hi.postRespond(file_name, file_path);
    }
}