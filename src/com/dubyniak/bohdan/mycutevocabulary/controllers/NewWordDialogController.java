package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Collections;

public class NewWordDialogController {
    private static Storage storage;

    @FXML
    private TextField txtForeignWord;

    @FXML
    private TextField txtDefinition;

    static void setStorage(Storage storage) {
        NewWordDialogController.storage = storage;
    }

    public void putInButtonClicked(ActionEvent actionEvent) {
        ListView<VocabularyRecord> lv =
                (ListView<VocabularyRecord>) DirectoriesController.allWordsDialogRoot.lookup("#lvAllWords");
        if (txtForeignWord.getText().equals("") || txtDefinition.getText().equals(""))
            return;
        if (((Stage)((Node) actionEvent.getSource()).getScene().getWindow()).getTitle().equals("New word")) {
            VocabularyRecord temp = new VocabularyRecord(txtForeignWord.getText(), txtDefinition.getText());
            for (VocabularyRecord record : storage.read()) {
                if (record.getForeignWord().equalsIgnoreCase(temp.getForeignWord()) ||
                        record.getDefinition().equalsIgnoreCase(temp.getDefinition()))
                    return;
            }
            storage.create(temp);
        }
        else {

            for (VocabularyRecord record : storage.read())
                if (!record.equals(lv.getSelectionModel().getSelectedItem()) &&
                        (record.getForeignWord().equalsIgnoreCase(txtForeignWord.getText()) ||
                        record.getDefinition().equalsIgnoreCase(txtDefinition.getText())))
                    return;
            storage.update(lv.getSelectionModel().getSelectedItem(),
                    new VocabularyRecord(txtForeignWord.getText(), txtDefinition.getText(),
                            lv.getSelectionModel().getSelectedItem().isShown()));
        }
        ObservableList<VocabularyRecord> list = FXCollections.observableList(storage.read());
        Collections.sort(list, (o1, o2) -> o1.getForeignWord().compareTo(o2.getForeignWord()));
        lv.setItems(null);
        lv.setItems(list);
        clearDialogFields();
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        clearDialogFields();
    }

    private void clearDialogFields() {
        txtForeignWord.clear();
        txtDefinition.clear();
        txtForeignWord.requestFocus();
    }
}
