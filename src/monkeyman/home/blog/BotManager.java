package monkeyman.home.blog;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotManager {
    public static TelegramBotsApi bot = null;

    private TelegramBotsApi BotApi=null;
    public static String Token=null;
    private String UserName=null;
    public BotManager(String Token)
    {

        this.Token = Token;
        ApiContextInitializer.init();
        this.BotApi = new TelegramBotsApi();


        try {
            bot = this.BotApi;

            MessageReceiver messageReceiver = new MessageReceiver();


            this.BotApi.registerBot(messageReceiver);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }



    //----------------------------------------------------------------------------------------------------
    public class MessageReceiver extends TelegramLongPollingBot
    {

        private WorkFlow workFlow = new WorkFlow();
        private TelegramLongPollingBot bot = null;

        public MessageReceiver(){
            this.bot = this;
        }

        @Override
        public String getBotUsername() {
            // TODO Auto-generated method stub
            return UserName;
        }


        @Override
        public void onUpdateReceived(Update arg0) {
            try {

                UserName = this.getMe().getUserName();
            } catch (TelegramApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // TODO Auto-generated method stub
            if (arg0.hasMessage() && arg0.getMessage().hasText()) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            workFlow.WhatIsTheMessage(arg0, bot);

                        } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

            }
            //-------------------------


            if (arg0.hasMessage() && arg0.getMessage().hasDocument()){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Function.getFile(arg0, bot);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }

        }

        @Override
        public String getBotToken() {
            // TODO Auto-generated method stub
            return Token;
        }

    }

}
