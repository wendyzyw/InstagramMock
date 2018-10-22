package ClientUtils;
import ClientBasics.ClientUploader;
import StaticTags.API_Tags;

public class Upload_Photo_Client
{
    private ClientUploader cu;
    private String req_url;

    public Upload_Photo_Client(String server_ip,
                               String server_port,
                               String user_name,
                               String user_password,
                               String media_location,
                               String local_media_name,
                               String local_media_path)
    {
        req_url =   API_Tags.HTTP + server_ip + ":" + server_port +
                    API_Tags.UPLOAD_USER_MEDIA +
                    API_Tags.USR_NAME + user_name + "&" +
                    API_Tags.USR_PWD + user_password + "&" +
                    API_Tags.MED_LOC + media_location;
        cu = new ClientUploader(req_url, local_media_name, local_media_path);
    }

    public void perform()
    {
        cu.postFile();
    }
}