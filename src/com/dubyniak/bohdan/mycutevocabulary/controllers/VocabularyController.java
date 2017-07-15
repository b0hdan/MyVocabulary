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
import javafx.scene.control.Button;
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
    Button btnShowHide;

    @FXML
    private void initialize() {
        list = FXCollections.observableList(storage.read());
        lvAllWords.setItems(list);
    }

    static void setStorage(Storage storage) {
        VocabularyController.storage = storage;
    }

    public void plusButtonClicked(ActionEvent actionEvent) throws IOException {
        if (newWordDialog == null)
            initializeNewWordDialog((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        newWordDialog.setTitle("New word");
        newWordDialog.showAndWait();
        refreshList();
    }

    public void minusButtonClicked(ActionEvent actionEvent) {
        storage.delete(lvAllWords.getSelectionModel().getSelectedItem());
        refreshList();
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            storage.delete(lvAllWords.getSelectionModel().getSelectedItem());
            refreshList();
        }
        else if (keyEvent.getCode().isArrowKey())
            if (lvAllWords.getSelectionModel().getSelectedItem() == null)
                btnShowHide.setText("Hide");
            else if (lvAllWords.getSelectionModel().getSelectedItem().isShown())
                btnShowHide.setText("Hide");
            else
                btnShowHide.setText("Show");
    }

    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (lvAllWords.getSelectionModel().getSelectedItem() == null)
            btnShowHide.setText("Hide");
        else if (mouseEvent.getClickCount() == 2) {
            if (newWordDialog == null)
                initializeNewWordDialog((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
            txtEN.setText(storage.read().get(lvAllWords.getSelectionModel().getSelectedIndex()).getForeignWord());
            txtUA.setText(storage.read().get(lvAllWords.getSelectionModel().getSelectedIndex()).getDefinition());
            newWordDialog.setTitle("Edit record");
            txtEN.selectAll();
            newWordDialog.showAndWait();
            refreshList();
        }
        else if (lvAllWords.getSelectionModel().getSelectedItem().isShown())
            btnShowHide.setText("Hide");
        else
            btnShowHide.setText("Show");
    }

    public void showHideButtonClicked(ActionEvent actionEvent) {
        VocabularyRecord selectedItem = lvAllWords.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.isShown()) {
            selectedItem.hide();
            btnShowHide.setText("Show");
            refreshList();
        }
        else if (selectedItem != null) {
            selectedItem.show();
            btnShowHide.setText("Hide");
            refreshList();
        }
    }

    void refreshList() {
        if (lvAllWords.getSelectionModel().getSelectedItem() == null) {
            lvAllWords.setItems(null);
            lvAllWords.setItems(list);
            return;
        }
        int selectedIndex = lvAllWords.getSelectionModel().getSelectedIndex();
        lvAllWords.setItems(null);
        lvAllWords.setItems(list);
        if (lvAllWords.getItems().size() == 0)
            return;
        if (selectedIndex == lvAllWords.getItems().size())
            selectedIndex--;
        lvAllWords.getSelectionModel().select(selectedIndex);
    }

    private void initializeNewWordDialog(Stage owner) throws IOException {
        newWordDialog = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/new-word-dialog.fxml"));
        newWordDialog.setResizable(false);
        newWordDialog.initModality(Modality.APPLICATION_MODAL);
        newWordDialog.initOwner(owner);
        newWordDialog.setScene(new Scene(root, 272, 144));
        txtEN = (TextField) newWordDialog.getScene().lookup("#txtEnglishWord");
        txtUA = (TextField) newWordDialog.getScene().lookup("#txtUkrainianWord");
        newWordDialog.setOnCloseRequest(event -> {
            txtEN.clear();
            txtUA.clear();
            txtEN.requestFocus();
        });
    }

}
