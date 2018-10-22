package ClientUtils;
import ClientBasics.ClientDownloader;
import ClientBasics.ClientRequester;
import StaticTags.API_Tags;

public class Media_Comments_Client
{
    private ClientDownloader cd;
    private ClientRequester cr;
    private String req_url;

    public Media_Comments_Client(String server_ip,
                                 String server_port,
                                 String user_name,
                                 String user_password,
                                 String target_user,
                                 String media_name,
                                 String img_local_saved_path)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.GET_USER_MEDIA +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.MED_NAME + media_name;
        cd = new ClientDownloader(req_url, img_local_saved_path, media_name);

        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.GET_USER_MEDIA_COMMENTS +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.TGT_USR + target_user + "&" +
                    API_Tags.MED_NAME + media_name;
        cr = new ClientRequester(req_url);
    }

    public String perform()
    {
        cd.getFile();
        return cr.sendRequest();
    }
}
