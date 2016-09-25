package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewWordDialogController {
    private static Storage storage;

    @FXML
    TextField txtEnglishWord;

    @FXML
    TextField txtUkrainianWord;

    static void setStorage(Storage storage) {
        NewWordDialogController.storage = storage;
    }

    public void putInButtonClicked(ActionEvent actionEvent) {
        if (txtEnglishWord.getText().equals("") || txtUkrainianWord.getText().equals(""))
            return;
        if (((Stage)((Node) actionEvent.getSource()).getScene().getWindow()).getTitle().equals("New word")) {
            VocabularyRecord temp = new VocabularyRecord(txtEnglishWord.getText(), txtUkrainianWord.getText());
            if (!storage.read().contains(temp))
                storage.create(temp);
        }
        else {
            ListView<VocabularyRecord> lv =
                    (ListView<VocabularyRecord>) StartController.allWordsDialogRoot.lookup("#lvAllWords");
            storage.update(lv.getSelectionModel().getSelectedItem(),
                    new VocabularyRecord(txtEnglishWord.getText(), txtUkrainianWord.getText()));
        }
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        clearDialogFields();
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        clearDialogFields();
    }

    private void clearDialogFields() {
        txtEnglishWord.clear();
        txtUkrainianWord.clear();
        txtEnglishWord.requestFocus();
    }

}
