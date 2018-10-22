package ClientBasics;

public class client_test_main
{
    private static String test_url = "http://10.96.231.69:5000/UploadUserMedia?u_name=tommy&u_pwd=123456&m_loc=Brisbane";
    private static String test_filename = "4.jpg";
    private static String test_filepath = "C:\\Users\\wanai\\Desktop";

    public static void main(String[] args)
    {
        //System.out.println("123");
        //ClientUploader cu = new ClientUploader(test_url, test_filename, test_filepath);
        //cu.postFile();

        //ClientDownloader cd = new ClientDownloader("http://10.96.231.69:5000/RemoveUserMedia?u_name=tommy&u_pwd=123456&m_name=1.jpg", "", "");
        ///cd.getFile();

        //ClientDownloader cd = new ClientDownloader("http://10.96.231.69:5000/GetUserMedia?u_name=tommy&u_pwd=123456&m_name=1.jpg", "C:\\Users\\wanai\\Desktop", "2.jpg");
        //cd.getFile();

        //ClientRequester cr = new ClientRequester("http://10.96.231.69:5000/GetUserMediaComments?u_name=tommy&u_pwd=123456&m_id=0");
        //ClientRequester cr = new ClientRequester("http://10.96.231.69:5000/GetUserMediaLikers?u_name=tommy&u_pwd=123456&m_id=0");

        //ClientRequester cr = new ClientRequester("http://10.96.231.69:5000/GetUserMediaDateSort?u_name=tommy&u_pwd=123456");
        //ClientRequester cr = new ClientRequester("http://10.96.231.69:5000/GetUserMediaLocationSort?u_name=tommy&u_pwd=123456");
        ClientRequester cr = new ClientRequester("http://10.96.231.69:5000/SearchUsers?u_name=tommy&u_pwd=123456&k_word=o");
        System.out.println(cr.sendRequest());
    }
}
