package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.TestMaker;
import com.dubyniak.bohdan.mycutevocabulary.services.FirebaseService;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    private Stage directoriesDialog;
    private Stage directoryChooserDialog;
    private Stage flashcardsDialog;
    private Stage statisticDialog;
    private Stage testDialog;
    private Stage loadingDataDialog;
    static Parent directoriesDialogRoot;
    private Label lblQuestion;
    private Label lblCount;
    private ProgressBar pb;
    private DirectoriesController directoriesController;
    private DirectoryChooserController directoryChooserController;
    private FlashcardsController flashcardsController;
    private StatisticController statisticController;

    public StartController() {
        storage = new FirebaseService();
        DirectoriesController.setStorage(storage);
        NewDirectoryDialogController.setStorage(storage);
        VocabularyController.setStorage(storage);
        NewWordDialogController.setStorage(storage);
        DirectoryChooserController.setStorage(storage);
        FlashcardsController.setStorage(storage);
        TestMaker.setStorage(storage);
        StatisticController.setStorage(storage);
    }

    public void directoriesButtonClicked(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (storage.getVocabularies() == null)
            openLoadingDataDialog(actionEvent);

        if (directoriesDialog == null) {
            directoriesDialog = new Stage();
            FXMLLoader directoriesFXMLLoader = new FXMLLoader(getClass().getResource("/directories.fxml"));
            directoriesDialogRoot = directoriesFXMLLoader.load();
            directoriesController = directoriesFXMLLoader.getController();
            Stage temp = new Stage();
            ((FirebaseService) storage).setLoadingDataDialog(temp);
            directoriesController.setLoadingDataDialog(temp);
            directoriesDialog.setTitle("All directories");
            directoriesDialog.setResizable(false);
            directoriesDialog.initModality(Modality.APPLICATION_MODAL);
            directoriesDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            directoriesDialog.setScene(new Scene(directoriesDialogRoot));
        }
        directoriesController.refreshList();
        directoriesController.lvAllDirectories.getSelectionModel().clearSelection();
        directoriesDialog.showAndWait();
        storage.saveVocabularies();
    }

    public void flashcardsButtonClicked(ActionEvent actionEvent) throws IOException {
        if (storage.getVocabularies() == null)
            openLoadingDataDialog(actionEvent);

        if (openDirectoryChooser(actionEvent)) return;

        if (flashcardsDialog == null) {
            flashcardsDialog = new Stage();
            FXMLLoader flashcardsFXMLLoader = new FXMLLoader(getClass().getResource("/flashcards.fxml"));
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
        flashcardsDialog.showAndWait();
        storage.saveVocabulary(storage.getLastVocabularyName());
    }

    public void testButtonClicked(ActionEvent actionEvent) throws IOException {
        if (storage.getVocabularies() == null)
            openLoadingDataDialog(actionEvent);

        if (openDirectoryChooser(actionEvent)) return;

        if (storage.read().size() == 0) {
            new Alert(Alert.AlertType.ERROR, "Cannot start the test! Storage is empty.", ButtonType.OK).show();
            return;
        } else if (testDialog == null) {
            testDialog = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/test.fxml"));
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

    public void statisticButtonClicked(ActionEvent actionEvent) throws IOException {
        if (storage.getVocabularies() == null)
            openLoadingDataDialog(actionEvent);

        if (statisticDialog == null) {
            statisticDialog = new Stage();
            FXMLLoader statisticFXMLLoader = new FXMLLoader(getClass().getResource("/statistic.fxml"));
            Parent root = statisticFXMLLoader.load();
            statisticController = statisticFXMLLoader.getController();
            statisticDialog.setTitle("Statistic");
            statisticDialog.setResizable(false);
            statisticDialog.initModality(Modality.APPLICATION_MODAL);
            statisticDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            statisticDialog.setScene(new Scene((root)));
        }
        statisticController.fillList();
        statisticController.tableView.getSelectionModel().clearSelection();
        statisticDialog.showAndWait();
    }

    public void closeButtonClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    private boolean openDirectoryChooser(ActionEvent actionEvent) throws IOException {
        if (storage.getVocabularies() == null) {
            openLoadingDataDialog(actionEvent);
        }

        if (directoryChooserDialog == null) {
            directoryChooserDialog = new Stage();
            FXMLLoader directoryChooserFXMLLoader = new FXMLLoader(getClass().getResource("/directory-chooser.fxml"));
            Parent root = directoryChooserFXMLLoader.load();
            directoryChooserController = directoryChooserFXMLLoader.getController();
            Stage temp = new Stage();
            ((FirebaseService) storage).setLoadingDataDialog(temp);
            directoryChooserController.setLoadingDataDialog(temp);
            directoryChooserDialog.setTitle("Choose a directory");
            directoryChooserDialog.setResizable(false);
            directoryChooserDialog.initModality(Modality.APPLICATION_MODAL);
            directoryChooserDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            directoryChooserDialog.setScene(new Scene(root));
            directoryChooserDialog.setOnCloseRequest(event -> {
                directoryChooserController.setCloseButtonPressed(true);
                directoryChooserController.choiceBox.getSelectionModel().select(null);
            });
        }
        directoryChooserDialog.showAndWait();
        if (directoryChooserController.isCloseButtonPressed()) {
            directoryChooserController.setCloseButtonPressed(false);
            return true;
        }
        return false;
    }

    private void openLoadingDataDialog(ActionEvent actionEvent) throws IOException {
        if (loadingDataDialog == null) {
            loadingDataDialog = new Stage();
            ((FirebaseService) storage).setLoadingDataDialog(loadingDataDialog);
            Parent root = FXMLLoader.load(getClass().getResource("/loading-data.fxml"));
            loadingDataDialog.setTitle("Loading");
            loadingDataDialog.setResizable(false);
            loadingDataDialog.initModality(Modality.APPLICATION_MODAL);
            loadingDataDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            loadingDataDialog.setScene(new Scene(root));
            loadingDataDialog.setOnCloseRequest(Event::consume);
        }
        if (directoriesController != null)
            directoriesController.setLoadingDataDialog(loadingDataDialog);
        if (directoryChooserController != null)
            directoryChooserController.setLoadingDataDialog(loadingDataDialog);
        loadingDataDialog.showAndWait();
    }
}
