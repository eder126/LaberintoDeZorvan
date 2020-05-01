package UI.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("../FXML/FXMLHistoria.fxml"));
        Scene scene = new Scene(fxml);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
