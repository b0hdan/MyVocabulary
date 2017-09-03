package com.dubyniak.bohdan.mycutevocabulary.start;

import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import com.dubyniak.bohdan.mycutevocabulary.services.FirebaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/start-window.fxml"));
        primaryStage.setTitle("My cute vocabulary");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 624, 416));
        primaryStage.show();
    }


    public static void main(String[] args) {
        FirebaseService fbService = new FirebaseService();
        fbService.loadVocabulary("en");
        fbService.create(new VocabularyRecord("tt", "ggggg"));
        fbService.loadVocabulary("fr");
        VocabularyRecord aaa = new VocabularyRecord("aaa", "bbbbbb", false);
        fbService.create(aaa);
        System.out.println("count = " + fbService.sizeOfActive());
        fbService.deleteVocabulary("en");
        VocabularyRecord bbb = new VocabularyRecord("ccc", "dddd", false);
        fbService.update(aaa,bbb);
        fbService.updateVocabulary("fr","uk");
        launch(args);
    }
}
