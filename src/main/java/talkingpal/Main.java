package talkingpal;

import java.io.IOException;
import talkingpal.ui.MainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private TalkingPal talkingPal = new TalkingPal();


    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("TalkingPal: Your Best Pal");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTalkingPal(talkingPal);  // inject the TalkingPal instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
