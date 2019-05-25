package monkeyman.home.blog;

import javafx.application.Application;
import java.awt.*;
import java.io.*;


public class Main {

    public static void main(String[] args) {
	// write your code here


        //System.setProperty("java.net.preferIPv4Stack" , "true");
        //System.setProperty("socksProxyHost", "127.0.0.1");
        //System.setProperty("socksProxyPort", "9050");



        try{
            if (new File(StaticString.Defaults.ConfigFile).exists()){
                FileInputStream stream = new FileInputStream(StaticString.Defaults.ConfigFile);
                byte[] buffer = new byte[1];
                StringBuilder str = new StringBuilder("");
                while (stream.read(buffer,0,buffer.length ) > -1){
                    str.append(new String(buffer));
                }
                stream.close();
                if(str.length() > 40){
                    String Token = Crypt.Decrypt(str.toString());
                    // start bot in background
                    new BotManager(Token);
                }
                else {
                    new MessageDialog().setText(StaticString.Messages.title_info,
                            StaticString.Messages.msg_err_short_len_token )
                            .setType(MessageDialog.WindowType.ReadOnly).start();
                    // show  main window
                    Main.ShowMainForm();
                }
            }
            else {
                //show main window for first time
               Main.ShowMainForm();

            }
        }
        catch (Exception e){e.printStackTrace();}


    }

    private static void ShowMainForm(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application.launch(GUI.class);
            }
        });
    }

}
