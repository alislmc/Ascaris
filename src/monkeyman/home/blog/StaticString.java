package monkeyman.home.blog;

public class StaticString {

    public static class Defaults{
         public  static String AppName = "Ascaris";
         public  static String ImageLogo = "/main.png";
         public  static String ICON = "/icon.png";
         public  static String ConfigFile = "config.xz";
    }



    public static class Views{
        public static String button_view_api_token="Set API Token";
        public static String button_view_save_and_apply="Save And Apply";
        public static String button_view_minimize="Minimize";
        public static String button_view_exit="Exit";
    }


    public static class Messages{
        public static String msg_err_short_len_token = "The length of characters entered must be more than 40 characters\nPlease pay more attention!";
        public static String title_info="Info";
    }



    public static class BotStatic{
        public static int maximum_characters = 4096;
        public static class StringCommands{
            public static String Start = "/start";
            public static String GetIP = "Get IP";
            public static String ScreenShot = "ScreenShot";
            public static String ScriptCommand="cmd";
            public static String Copy="copy_file";
            public static String Move="move_file";
            public static String Delete_file="delete_file";
            public static String Delete_dir="delete_dir";
            public static String Dir="dir";
            public static String Getfile="get_file";
            public static String Help="Help And More Options";
            public static String Donate="Donate 🧡";
        }

        public static class Messages{
            public static String File_not_exists="File Not Exists!";
            public static String Directory_not_found="Directory not found!";
            public static String Done="Done.";
            public static String Error="Error!";
            public static String is_not_file="Is Not File!";
            public static String Received_and_save="Received and Save Document";
            public static String wait_send_doc="Please wait while the bot is sending the document";

            public static String see_below_panel="See Below Panel For More option";
            public static String Donate_message="<b>If you like  Donate us:</b> 🎁 \n\n<b>btc:</b><i> 1BrwdgYcdyiDCQhmuucpD9jx9rKpAsUPm5</i>\n\n\n<b>xmr:</b> <i>4BrL51JCc9NGQ71kWhnYoDRffsDZy7m1HUU7MRU4nUMXAHNFBEJhkTZV9HdaL4gfuNBxLPc3BeMkLGaPbF5vWtANQsNvwTYECWbPtNsSen</i>";
        }
    }
}
