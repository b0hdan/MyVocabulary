package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewDirectoryDialogController {
    private static Storage storage;

    @FXML
    private TextField txtDirectoryName;

    static void setStorage(Storage storage) {
        NewDirectoryDialogController.storage = storage;
    }

    public void createButtonClicked(ActionEvent actionEvent) {
        if (txtDirectoryName.getText().equals(""))
            return;
        if (((Stage)((Node) actionEvent.getSource()).getScene().getWindow()).getTitle().equals("New directory")) {
            String temp = txtDirectoryName.getText();
            for (String directory : storage.getVocabularies()) {
                if (directory.equalsIgnoreCase(temp))
                    return;
            }
            storage.createVocabulary(temp);
        }
        else {
            ListView<String> lv =
                    (ListView<String>) StartController.directoriesDialogRoot.lookup("#lvAllDirectories");
            for (String directory : storage.getVocabularies())
                if (!directory.equals(lv.getSelectionModel().getSelectedItem()) &&
                        (directory.equalsIgnoreCase(txtDirectoryName.getText())))
                    return;
            storage.updateVocabulary(lv.getSelectionModel().getSelectedItem(), txtDirectoryName.getText());
        }
        storage.saveVocabulary(txtDirectoryName.getText());
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        clearDialogFields();
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        clearDialogFields();
    }

    private void clearDialogFields() {
        txtDirectoryName.clear();
        txtDirectoryName.requestFocus();
    }
}
