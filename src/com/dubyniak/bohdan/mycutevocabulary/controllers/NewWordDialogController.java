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
            for (VocabularyRecord record : storage.read()) {
                if (record.getEnglishWord().equalsIgnoreCase(temp.getEnglishWord()) ||
                        record.getUkrainianWord().equalsIgnoreCase(temp.getUkrainianWord()))
                    return;
            }
            storage.create(temp);
        }
        else {
            ListView<VocabularyRecord> lv =
                    (ListView<VocabularyRecord>) StartController.allWordsDialogRoot.lookup("#lvAllWords");
            for (VocabularyRecord record : storage.read())
                if (!record.equals(lv.getSelectionModel().getSelectedItem()) &&
                        (record.getEnglishWord().equalsIgnoreCase(txtEnglishWord.getText()) ||
                        record.getUkrainianWord().equalsIgnoreCase(txtUkrainianWord.getText())))
                    return;
            storage.update(lv.getSelectionModel().getSelectedItem(),
                    new VocabularyRecord(txtEnglishWord.getText(), txtUkrainianWord.getText(),
                            lv.getSelectionModel().getSelectedItem().isShown()));
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
