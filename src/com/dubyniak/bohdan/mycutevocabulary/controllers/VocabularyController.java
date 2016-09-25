package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class VocabularyController {
    private Stage newWordDialog;
    private static Storage storage;
    private ObservableList<VocabularyRecord> list;
    private TextField txtEN;
    private TextField txtUA;

    @FXML
    ListView<VocabularyRecord> lvAllWords;

    @FXML
    private void initialize() {
        list = FXCollections.observableList(storage.read());
        lvAllWords.setItems(list);
    }

    static void setStorage(Storage storage) {
        VocabularyController.storage = storage;
    }

    public void plusButtonClicked(ActionEvent actionEvent) throws IOException {
        if (newWordDialog == null) {
            newWordDialog = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/new-word-dialog.fxml"));
            newWordDialog.setResizable(false);
            newWordDialog.initModality(Modality.APPLICATION_MODAL);
            newWordDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            newWordDialog.setScene(new Scene(root, 284, 144));
            txtEN = (TextField) newWordDialog.getScene().lookup("#txtEnglishWord");
            txtUA = (TextField) newWordDialog.getScene().lookup("#txtUkrainianWord");
            newWordDialog.setOnCloseRequest(event -> {
                txtEN.clear();
                txtUA.clear();
                txtEN.requestFocus();
            });
        }
        newWordDialog.setTitle("New word");
        newWordDialog.showAndWait();
        lvAllWords.setItems(null);
        lvAllWords.setItems(list);
    }

    public void minusButtonClicked(ActionEvent actionEvent) {
        storage.delete(lvAllWords.getSelectionModel().getSelectedItem());
        lvAllWords.setItems(null);
        lvAllWords.setItems(list);
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            storage.delete(lvAllWords.getSelectionModel().getSelectedItem());
            lvAllWords.setItems(null);
            lvAllWords.setItems(list);
        }
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            if (lvAllWords.getSelectionModel().getSelectedItem() == null)
                return;
            txtEN.setText(storage.read().get(lvAllWords.getSelectionModel().getSelectedIndex()).getEnglishWord());
            txtUA.setText(storage.read().get(lvAllWords.getSelectionModel().getSelectedIndex()).getUkrainianWord());
            newWordDialog.setTitle("Edit record");
            txtEN.selectAll();
            newWordDialog.showAndWait();
            lvAllWords.setItems(null);
            lvAllWords.setItems(list);
        }
    }

}
