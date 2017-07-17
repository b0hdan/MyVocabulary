package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

public class DirectoryChooserController {
    private static Storage storage;
    private boolean closeButtonPressed;

    public boolean isCloseButtonPressed() {
        return closeButtonPressed;
    }

    public void setCloseButtonPressed(boolean closeButtonPressed) {
        this.closeButtonPressed = closeButtonPressed;
    }

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private void initialize() {
        choiceBox.setItems(FXCollections.observableList(storage.getVocabularies()));
    }

    static void setStorage(Storage storage) {
        DirectoryChooserController.storage = storage;
    }

    public void chooseButtonClicked(ActionEvent actionEvent) {
        if (choiceBox.getSelectionModel().getSelectedItem() != null) {
            storage.loadVocabulary(choiceBox.getSelectionModel().getSelectedItem());
            choiceBox.getSelectionModel().select(null);
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        }
    }
}
