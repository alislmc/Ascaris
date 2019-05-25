package monkeyman.home.blog;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.*;
import java.io.FileOutputStream;

public class GUI extends Application{
    public GUI(){}

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();

        CornerRadii radii = new CornerRadii(0,0,0,0,false);
        Insets insets = new Insets(0,0,0,0);
        BackgroundFill fill = new BackgroundFill(Paint.valueOf("#555753"), radii, insets);
        Background background = new Background(fill);
        root.setBackground(background);

        root.setMaxSize(360,140);


        Scene scene = new Scene(root);
        scene.setFill(Paint.valueOf("TRANSPARENT"));




        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResource(StaticString.Defaults.ICON).toString()));
        primaryStage.setResizable(false);
        primaryStage.setTitle(StaticString.Defaults.AppName);
        primaryStage.setWidth(400);
        primaryStage.setHeight(142);


        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);



        this.Desigen_MainStage(root, primaryStage);
        primaryStage.show();
    }

    private void Desigen_MainStage(Pane root, Stage primary){
        HBox hBox_main = new HBox();


        Pane pane_left = new Pane();
        VBox box_left = new VBox();
        Button[] buttons = new Button[4];
        buttons[0] = new Button(StaticString.Views.button_view_api_token);
        buttons[1] = new Button(StaticString.Views.button_view_save_and_apply);
        buttons[2] = new Button(StaticString.Views.button_view_minimize);
        buttons[3] = new Button(StaticString.Views.button_view_exit);

        buttons[0].setStyle("-fx-border-color:TRANSPARENT; -fx-background-color:TRANSPARENT; -fx-border-color:TRANSPARENT;");
        buttons[0].setTextFill(Color.valueOf("#8AC2FF"));
        buttons[0].setMinWidth(140);
        buttons[0].setEffect(new Bloom());

        buttons[1].setStyle("-fx-color:#4A4F49; -fx-border-color:#888A85;");
        buttons[1].setEffect(new InnerShadow());
        buttons[1].setMinWidth(140);
        buttons[1].setTextFill(Color.valueOf("#BABDB6"));

        buttons[2].setStyle("-fx-color:#414540; -fx-border-color:#888A85;");
        buttons[2].setEffect(new InnerShadow());
        buttons[2].setMinWidth(140);
        buttons[2].setTextFill(Color.valueOf("#BABDB6"));

        buttons[3].setStyle("-fx-color:#383B37; -fx-border-color:#888A85;");
        buttons[3].setMinWidth(140);
        buttons[3].setEffect(new InnerShadow());
        buttons[3].setTextFill(Color.valueOf("#BABDB6"));
        box_left.getChildren().addAll(buttons);
        pane_left.getChildren().add(box_left);

        pane_left.setLayoutX(0);
        pane_left.setLayoutY(0);
        hBox_main.getChildren().add(pane_left);






        Pane pane_right = new Pane();
        VBox vBox_right_1 = new VBox();
        TextField textField_token = new TextField();
        textField_token.setMinWidth(250);

        textField_token.setStyle("-fx-background-color:#555753; -fx-border-color:#729FCF; -fx-text-inner-color:#B7D2EE;");

        textField_token.setEffect(new Lighting(new Light.Distant(70,70,Color.DARKGRAY)));
        vBox_right_1.getChildren().add(textField_token);
        pane_right.getChildren().add(vBox_right_1);
        hBox_main.getChildren().add(pane_right);



        root.getChildren().add(hBox_main);












        AnchorPane anchorPane = new AnchorPane();
        vBox_right_1.getChildren().add(anchorPane);
        ImageView imageView = new ImageView();


        try {
            Image image = new Image(getClass().getResource(StaticString.Defaults.ImageLogo).toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView.setLayoutX(40);



        anchorPane.getChildren().add(imageView);




        buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });



        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    if(textField_token.getText().length() > 40){
                        FileOutputStream stream = new FileOutputStream(StaticString.Defaults.ConfigFile);
                        stream.write(Crypt.Encrypt(textField_token.getText()).getBytes());
                        stream.flush();
                        stream.close();

                        //start bot now
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                new BotManager(textField_token.getText());
                            }
                        }).start();

                        buttons[1].setText("Done");
                    }
                    else {
                        new MessageDialog().setText(StaticString.Messages.title_info,
                                StaticString.Messages.msg_err_short_len_token )
                                .setType(MessageDialog.WindowType.ReadOnly).start();

                    }
                }
                catch (Exception e){e.printStackTrace();}
            }
        });

        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.setImplicitExit(false);
                primary.hide();

            }
        });


    }
}
