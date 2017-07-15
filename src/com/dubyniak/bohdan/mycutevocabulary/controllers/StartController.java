package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.interfaces.impls.FileStorage;
import com.dubyniak.bohdan.mycutevocabulary.objects.TestMaker;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    private Storage storage;
    private Stage allWordsDialog;
    private Stage flashcardsDialog;
    private Stage testDialog;
    static Parent allWordsDialogRoot;
    private Label lblQuestion;
    private Label lblCount;
    private ProgressBar pb;
    private VocabularyController vocabularyController;
    private FlashcardsController flashcardsController;

    public StartController() {
        storage = new FileStorage();
        VocabularyController.setStorage(storage);
        NewWordDialogController.setStorage(storage);
        FlashcardsController.setStorage(storage);
        TestMaker.setStorage(storage);
//        fillTestData();
    }

    public void myVocabularyButtonClicked(ActionEvent actionEvent) throws IOException {
        if (allWordsDialog == null) {
            allWordsDialog = new Stage();
            FXMLLoader vocabularyFXMLLoader = new FXMLLoader(getClass().getResource("../fxml/my-vocabulary.fxml"));
            allWordsDialogRoot = vocabularyFXMLLoader.load();
            vocabularyController = vocabularyFXMLLoader.getController();
            allWordsDialog.setTitle("All words");
            allWordsDialog.setResizable(false);
            allWordsDialog.initModality(Modality.APPLICATION_MODAL);
            allWordsDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            allWordsDialog.setScene(new Scene(allWordsDialogRoot, 450, 400));
            allWordsDialog.setOnCloseRequest(event -> vocabularyController.btnShowHide.setText("Hide"));
        }
        vocabularyController.refreshList();
        vocabularyController.lvAllWords.getSelectionModel().select(null);
        allWordsDialog.show();
    }

    public void flashcardsButtonClicked(ActionEvent actionEvent) throws IOException {
        if (flashcardsDialog == null) {
            flashcardsDialog = new Stage();
            FXMLLoader flashcardsFXMLLoader = new FXMLLoader(getClass().getResource("../fxml/flashcards.fxml"));
            Parent root = flashcardsFXMLLoader.load();
            flashcardsController = flashcardsFXMLLoader.getController();
            flashcardsDialog.setTitle("Flashcards");
            flashcardsDialog.setResizable(false);
            flashcardsDialog.initModality(Modality.APPLICATION_MODAL);
            flashcardsDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            flashcardsDialog.setScene(new Scene((root)));
        }
        flashcardsController.btnPositive.requestFocus();
        flashcardsController.start();
        flashcardsDialog.show();
    }

    public void testButtonClicked(ActionEvent actionEvent) throws IOException {
        if (storage.read().size() == 0) {
            new Alert(Alert.AlertType.ERROR, "Cannot start the test! Storage is empty.", ButtonType.OK).show();
            return;
        }
        else if (testDialog == null) {
            testDialog = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/test.fxml"));
            testDialog.setTitle("Test");
            testDialog.setResizable(false);
            testDialog.initModality(Modality.APPLICATION_MODAL);
            testDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            testDialog.setScene(new Scene(root, 321, 191));
            lblQuestion = (Label) root.lookup("#lblQuestion");
            lblCount = (Label) root.lookup("#lblCount");
            pb = (ProgressBar) root.lookup("#progressBar");
        }
        TestMaker.startTest();
        pb.setProgress(0);
        lblCount.setText("0/" + TestMaker.getRecords().size());
        lblQuestion.setText(TestMaker.getRecords().get(0).getForeignWord());
        testDialog.show();
    }

    public void closeButtonClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        save();
    }

    public void save() {
        storage.saveVocabulary();
    }

    private void fillTestData() {
        storage.create(new VocabularyRecord("an apple", "яблуко"));
        storage.create(new VocabularyRecord("a table", "стіл"));
        storage.create(new VocabularyRecord("to write", "писати"));
        storage.create(new VocabularyRecord("to walk", "гуляти"));
        storage.create(new VocabularyRecord("a walk", "прогулянка"));
        storage.create(new VocabularyRecord("sugar", "цукор"));
        storage.create(new VocabularyRecord("salt", "сіль"));
        storage.create(new VocabularyRecord("a program", "програма"));
        storage.create(new VocabularyRecord("to work", "працювати"));
        storage.create(new VocabularyRecord("a pen", "ручка"));
        storage.create(new VocabularyRecord("a pencil", "олівець"));
    }
}
