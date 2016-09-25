package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.interfaces.impls.StorageImpl;
import com.dubyniak.bohdan.mycutevocabulary.objects.TestMaker;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    private Storage storage;
    private Stage allWordsDialog;
    private Stage testDialog;
    private Parent testDialogRoot;
    static Parent allWordsDialogRoot;

    public StartController() {
        storage = new StorageImpl();
        VocabularyController.setStorage(storage);
        NewWordDialogController.setStorage(storage);
        TestMaker.setStorage(storage);
        fillTestData();
    }

    public void myVocabularyButtonClicked(ActionEvent actionEvent) throws IOException {
        if (allWordsDialog == null) {
            allWordsDialog = new Stage();
            allWordsDialogRoot = FXMLLoader.load(getClass().getResource("../fxml/my-vocabulary.fxml"));
            allWordsDialog.setTitle("All words");
            allWordsDialog.setResizable(false);
            allWordsDialog.initModality(Modality.APPLICATION_MODAL);
            allWordsDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            allWordsDialog.setScene(new Scene(allWordsDialogRoot, 450, 400));
        }
        allWordsDialog.showAndWait();
    }

    public void testButtonClicked(ActionEvent actionEvent) throws IOException {
        if (storage.read().size() == 0)
            return;
        if (testDialog == null) {
            testDialog = new Stage();
            testDialogRoot = FXMLLoader.load(getClass().getResource("../fxml/test.fxml"));
            testDialog.setTitle("Test");
            testDialog.setResizable(false);
            testDialog.initModality(Modality.APPLICATION_MODAL);
            testDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            testDialog.setScene(new Scene(testDialogRoot, 324, 180));
            testDialog.show();
            Label lblQuestion = (Label) testDialogRoot.lookup("#lblQuestion");
            Label lblCount = (Label) testDialogRoot.lookup("#lblCount");
            ProgressBar pb = (ProgressBar) testDialogRoot.lookup("#progressBar");
            TestMaker.startTest();
            pb.setProgress(0);
            lblCount.setText("0/" + TestMaker.getRecords().size());
            lblQuestion.setText(TestMaker.getRecords().get(0).getEnglishWord());
        }
        else
            testDialog.show();
    }

    public void closeButtonClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    private void fillTestData() {
        storage.create(new VocabularyRecord("an apple", "яблуко"));
        storage.create(new VocabularyRecord("a table", "стіл"));
        storage.create(new VocabularyRecord("to write", "писати"));
        storage.create(new VocabularyRecord("to walk", "гулляти"));
        storage.create(new VocabularyRecord("a walk", "прогулянка"));
        storage.create(new VocabularyRecord("sugar", "цукор"));
        storage.create(new VocabularyRecord("salt", "сіль"));
        storage.create(new VocabularyRecord("a program", "програма"));
        storage.create(new VocabularyRecord("to work", "працювати"));
        storage.create(new VocabularyRecord("a pen", "ручка"));
        storage.create(new VocabularyRecord("a pencil", "олівець"));
    }

}
