package org.dev.frontend.CAP;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static Stage mainStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("controller/mainWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle(StringConstants.programName);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        mainStage = primaryStage;
    }

    public static void hideMainWindow() {
        mainStage.hide();
    }

    public static void showMainWindow() {
        mainStage.show();
    }
}
