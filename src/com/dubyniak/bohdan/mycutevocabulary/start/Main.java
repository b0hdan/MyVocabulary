package com.dubyniak.bohdan.mycutevocabulary.start;

import com.dubyniak.bohdan.mycutevocabulary.controllers.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/start-window.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setOnCloseRequest(event -> ((StartController) fxmlLoader.getController()).save());
        primaryStage.setTitle("My cute vocabulary");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 624, 416));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
