package monkeyman.home.blog;




import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


public class WorkFlow {

    public static TelegramLongPollingBot bot = null;
    public WorkFlow(){}

    public void WhatIsTheMessage(Update update, TelegramLongPollingBot BOT) throws TelegramApiException, IOException {


        bot = BOT;


        if(update.getMessage().getText().equals(StaticString.BotStatic.StringCommands.Start)){
            bot.execute(new SendMessage(update.getMessage().getChatId(),"Hello am on."));
            Function.StartCommand(update);
        }

        if(update.getMessage().getText().equals(StaticString.BotStatic.StringCommands.Donate)){
            bot.execute(new SendMessage(update.getMessage().getChatId()
                    ,StaticString.BotStatic.Messages.Donate_message).setParseMode("HTML"));
        }

        if(update.getMessage().getText().equals(StaticString.BotStatic.StringCommands.Help)){
            Function.help(update);
        }

        if (update.getMessage().getText().equals(StaticString.BotStatic.StringCommands.GetIP)){
            Function.GetIP(update);
        }

        if (update.getMessage().getText().equals(StaticString.BotStatic.StringCommands.ScreenShot)){
            Function.ScreenShot(update);
        }

        if(update.getMessage().getText().split(":")[0].equals(StaticString.BotStatic.StringCommands.ScriptCommand)){
            Function.CommandScript(update);
        }


        if(update.getMessage().getText().split(":")[0].equals(StaticString.BotStatic.StringCommands.Copy)){
            Function.CopyFile(update);
        }

        if(update.getMessage().getText().split(":")[0].equals(StaticString.BotStatic.StringCommands.Move)){
            Function.MoveFile(update);
        }

        if(update.getMessage().getText().split(":")[0].equals(StaticString.BotStatic.StringCommands.Delete_dir)){
            Function.DeleteAllFile_Dir(update);
        }

        if(update.getMessage().getText().split(":")[0].equals(StaticString.BotStatic.StringCommands.Delete_file)){
            Function.DeleteFile(update);
        }

        if(update.getMessage().getText().split(":")[0].equals(StaticString.BotStatic.StringCommands.Dir)){
            Function.Dir(update);
        }

        if(update.getMessage().getText().split(":")[0].equals(StaticString.BotStatic.StringCommands.Getfile)){
            Function.GetFile(update);
        }







    }





}
