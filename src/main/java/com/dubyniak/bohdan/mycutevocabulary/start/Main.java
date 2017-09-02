package com.dubyniak.bohdan.mycutevocabulary.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/start-window.fxml"));
        primaryStage.setTitle("My cute vocabulary");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 624, 416));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
