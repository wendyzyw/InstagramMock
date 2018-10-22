package ClientUtils;

public class ClientMain {
    public static void main(String[] args) {
//        Register_Client rc = new Register_Client("10.96.231.69", "5000", "ammy", "666666", "ammy@gmail.com");
//        System.out.println(rc.perform());

//        Search_Client sc = new Search_Client("10.96.231.69", "5000", "tommy", "123456", "o");
//        System.out.println(sc.perform());

//        Put_Photo_Client upc = new Put_Photo_Client(  "10.96.231.69",
//                                                            "5000",
//                                                            "wanaii",
//                                                            "?:P)9ol.",
//                                                            "melbourne",
//                                                            "24.jpg",
//                                                            "C:\\Users\\wanai\\Desktop");
//        System.out.println(upc.perform());

//        Media_Comments_Client mcc = new Media_Comments_Client("10.96.231.69",
//                                                            "5000",
//                                                            "bobby",
//                                                            "111111",
//                                                            "bobby",
//                                                            "0.jpg",
//                                                            "C:\\Users\\wanai\\Desktop\\recv");
//        System.out.println(mcc.perform());
        // commenting on a specific targeted user media
//        Comment_Media_Client cmc = new Comment_Media_Client("10.96.231.69",
//                                                            "5000",
//                                                            "tom",
//                                                            "123456",
//                                                            "ammy",
//                                                            "24.jpg",
//                                                            "that_is_the_most_funniest_pic",
//                                                            "wanaii",
//                                                            "melbourne");
//        cmc.perform();

//        Authenticate_Client ac = new Authenticate_Client("10.96.231.69", "5000", "tom", "123456", "tom@gmail.com");
//        System.out.println(ac.perform());

//        Get_Recommend_User_I_Client ric = new Get_Recommend_User_I_Client("10.96.231.69", "5000", "bobby", "111111");
//        System.out.println(ric.perform());

//        Get_Recommend_User_C_Client rcc = new Get_Recommend_User_C_Client("10.96.231.69", "5000", "wanaii", "?:P)9ol.");
//        System.out.println(rcc.perform());

//        Get_Media_Client dmc = new Get_Media_Client(  "10.96.231.69",
//                                                        "5000",
//                                                        "bobby",
//                                                        "111111",
//                                                        "bobby",
//                                                        "1.jpg",
//                                                        "C:\\Users\\wanai\\Desktop\\recv");
//        dmc.perform();
        Get_Recommend_User_I_Client ric = new Get_Recommend_User_I_Client("10.96.231.69", "5000", "bobby", "111111");
        System.out.println(ric.perform());
    }
}