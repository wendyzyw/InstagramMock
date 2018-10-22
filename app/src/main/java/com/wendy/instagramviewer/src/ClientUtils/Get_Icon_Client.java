package ClientUtils;
import ClientNewBasic.HttpInvoker;
import StaticTags.API_Tags;

public class Get_Icon_Client
{
    private HttpInvoker hi;
    private String req_url, file_name, file_path;
    public Get_Icon_Client(String server_ip,
                           String server_port,
                           String user_name,
                           String user_password,
                           String img_local_saved_path)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                API_Tags.GET_USER_ICON +
                API_Tags.USR_NAME + user_name + "&" +
                API_Tags.USR_PWD + user_password;
        hi = new HttpInvoker(req_url);
        file_name = user_name + "_icon.jpg";
        file_path = img_local_saved_path;
    }

    public void perform()
    {
        hi.getFile(file_name, file_path);
    }
}
