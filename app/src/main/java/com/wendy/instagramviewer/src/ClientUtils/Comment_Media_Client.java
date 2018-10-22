package ClientUtils;
import ClientBasics.ClientRequester;
import StaticTags.API_Tags;

public class Comment_Media_Client
{
    private ClientRequester cr;
    private String req_url;

    public Comment_Media_Client(String server_ip,
                                String server_port,
                                String user_name,
                                String user_password,
                                String target_user,
                                String media_name,
                                String comment_text,
                                String at_names,
                                String comment_location)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.COMMENT_USER_MEDIA +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.TGT_USR + target_user + "&" +
                    API_Tags.MED_NAME + media_name + "&" +
                    API_Tags.CMT_TEXT + comment_text + "&" +
                    API_Tags.AT_NAMES + at_names + "&" +
                    API_Tags.CMT_LOC + comment_location;
        cr = new ClientRequester(req_url);
    }

    public String perform()
    {
        return cr.sendRequest();
    }
}
