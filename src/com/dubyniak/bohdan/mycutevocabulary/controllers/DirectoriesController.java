package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
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

public class DirectoriesController {
    private Stage newDirectoryDialog;
    private static Storage storage;
    private ObservableList<String> list;
    private TextField txtDirectoryName;
    private Stage vocabularyDialog;
    static Parent allWordsDialogRoot;
    private VocabularyController vocabularyController;

    @FXML
    ListView<String> lvAllDirectories;

    @FXML
    private void initialize() {
        list = FXCollections.observableList(storage.getVocabularies());
        lvAllDirectories.setItems(list);
    }

    static void setStorage(Storage storage) {
        DirectoriesController.storage = storage;
    }

    public void plusButtonClicked(ActionEvent actionEvent) throws IOException {
        if (newDirectoryDialog == null)
            initializeNewDirectoryDialog((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        newDirectoryDialog.setTitle("New directory");
        newDirectoryDialog.showAndWait();
        refreshList();
    }

    public void minusButtonClicked(ActionEvent actionEvent) {
        storage.deleteVocabulary(lvAllDirectories.getSelectionModel().getSelectedItem());
        refreshList();
    }

    public void openButtonClicked(ActionEvent actionEvent) throws IOException {
        if (lvAllDirectories.getSelectionModel().getSelectedItem() == null)
            return;
        String vocabularyName = lvAllDirectories.getSelectionModel().getSelectedItem();
        storage.loadVocabulary(vocabularyName);
        if (vocabularyDialog == null) {
            vocabularyDialog = new Stage();
            FXMLLoader vocabularyFXMLLoader = new FXMLLoader(getClass().getResource("../fxml/my-vocabulary.fxml"));
            allWordsDialogRoot = vocabularyFXMLLoader.load();
            vocabularyController = vocabularyFXMLLoader.getController();
            vocabularyDialog.setTitle("All words");
            vocabularyDialog.setResizable(false);
            vocabularyDialog.initModality(Modality.APPLICATION_MODAL);
            vocabularyDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            vocabularyDialog.setScene(new Scene(allWordsDialogRoot, 450, 400));
        }
        vocabularyController.initializeNewList(vocabularyName);
        vocabularyController.refreshList();
        vocabularyController.lvAllWords.getSelectionModel().select(null);
        vocabularyDialog.showAndWait();
        vocabularyController.btnShowHide.setText("Hide");
        storage.saveVocabulary(vocabularyName);
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            storage.deleteVocabulary(lvAllDirectories.getSelectionModel().getSelectedItem());
            refreshList();
        }
    }

    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            if (newDirectoryDialog == null)
                initializeNewDirectoryDialog((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
            txtDirectoryName.setText(storage.getVocabularies().get(lvAllDirectories.getSelectionModel().getSelectedIndex()));
            newDirectoryDialog.setTitle("Edit directory");
            txtDirectoryName.selectAll();
            newDirectoryDialog.showAndWait();
            refreshList();
        }
    }

    void refreshList() {
        if (lvAllDirectories.getSelectionModel().getSelectedItem() == null) {
            lvAllDirectories.setItems(null);
            lvAllDirectories.setItems(list);
            return;
        }
        int selectedIndex = lvAllDirectories.getSelectionModel().getSelectedIndex();
        lvAllDirectories.setItems(null);
        lvAllDirectories.setItems(list);
        if (lvAllDirectories.getItems().size() == 0)
            return;
        if (selectedIndex == lvAllDirectories.getItems().size())
            selectedIndex--;
        lvAllDirectories.getSelectionModel().select(selectedIndex);
    }

    private void initializeNewDirectoryDialog(Stage owner) throws IOException {
        newDirectoryDialog = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/new-directory-dialog.fxml"));
        newDirectoryDialog.setResizable(false);
        newDirectoryDialog.initModality(Modality.APPLICATION_MODAL);
        newDirectoryDialog.initOwner(owner);
        newDirectoryDialog.setScene(new Scene(root, 282, 102));
        txtDirectoryName = (TextField) newDirectoryDialog.getScene().lookup("#txtDirectoryName");
        newDirectoryDialog.setOnCloseRequest(event -> {
            txtDirectoryName.clear();
            txtDirectoryName.requestFocus();
        });
    }
}
