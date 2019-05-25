package monkeyman.home.blog;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class MessageDialog {

    private String Title = "", Message = "";
    private WindowType windowType = null;
    enum WindowType{ReadOnly, Editable}

    public MessageDialog setText(String title, String message){
        this.Message = message;
        this.Title = title;
        return this;
    }

    public MessageDialog setType(WindowType type){
        this.windowType = type;
        return this;
    }


    public void start() {
        Stage primaryStage = new Stage();
        Pane root = new Pane();
        primaryStage.setScene(new Scene(root));
        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("#2E3436"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setStyle("-fx-border-color:#729FCF;");






        primaryStage.getIcons().add(new Image(getClass().getResource(StaticString.Defaults.ICON).toString()));
        primaryStage.setResizable(false);
        primaryStage.setTitle(this.Title);

        //primaryStage.setWidth(400);
        //primaryStage.setHeight(142);



        if (this.windowType == WindowType.ReadOnly)
        this.MessageMode(root, primaryStage);
        else this.CopyMode(root, primaryStage);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        primaryStage.setAlwaysOnTop(true);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

    }

    private void MessageMode(Pane root, Stage stage){
        HBox pane = new HBox(20);
        javafx.scene.control.Label label = new Label(this.Message);
        label.setTextFill(Color.WHITE);

        Button button = new Button("X");


        pane.getChildren().add(label);
        pane.getChildren().add(button);
        root.getChildren().add(pane);

        label.setStyle("-fx-padding: 15 15 15 15;");
        button.setStyle(" -fx-pref-height:25; -fx-pref-width:25; -fx-background-color:#888A85;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

    }


    private void CopyMode(Pane root, Stage stage){
        HBox pane = new HBox(20);
        TextArea label = new TextArea(this.Message);
        label.setEditable(false);

        Button button = new Button("X");


        pane.getChildren().add(label);
        pane.getChildren().add(button);
        root.getChildren().add(pane);

        label.setStyle("-fx-padding: 15 15 15 15; -fx-control-inner-background:#2E3436;");

        button.setStyle(" -fx-pref-height:25; -fx-pref-width:25; -fx-background-color:#888A85;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

    }

}
