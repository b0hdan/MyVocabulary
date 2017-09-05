package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DirectoryChooserController {
    private static Storage storage;
    private boolean closeButtonPressed;
    private Stage loadingDataDialog;

    boolean isCloseButtonPressed() {
        return closeButtonPressed;
    }

    void setCloseButtonPressed(boolean closeButtonPressed) {
        this.closeButtonPressed = closeButtonPressed;
    }

    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    private void initialize() {
        choiceBox.setItems(FXCollections.observableList(storage.getVocabularies()));
    }

    static void setStorage(Storage storage) {
        DirectoryChooserController.storage = storage;
    }

    void setLoadingDataDialog(Stage loadingDataDialog) {
        this.loadingDataDialog = loadingDataDialog;
    }

    public void chooseButtonClicked(ActionEvent actionEvent) throws IOException {
        if (choiceBox.getSelectionModel().getSelectedItem() != null) {
            storage.loadVocabulary(choiceBox.getSelectionModel().getSelectedItem());
            openLoadingDataDialog(actionEvent);
            choiceBox.getSelectionModel().select(null);
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }
    }

    private void openLoadingDataDialog(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/loading-data.fxml"));
        loadingDataDialog.setTitle("Loading");
        loadingDataDialog.setResizable(false);
        loadingDataDialog.setScene(new Scene(root));
        loadingDataDialog.setOnCloseRequest(Event::consume);
        loadingDataDialog.showAndWait();
    }
}
