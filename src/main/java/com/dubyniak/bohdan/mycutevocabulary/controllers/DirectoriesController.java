package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class DirectoriesController {
    private Stage newDirectoryDialog;
    private static Storage storage;
    private ObservableList<String> list;
    private TextField txtDirectoryName;
    private Stage vocabularyDialog;
    static Parent allWordsDialogRoot;
    private VocabularyController vocabularyController;
    private Stage loadingDataDialog;

    @FXML
    ListView<String> lvAllDirectories;

    @FXML
    private void initialize() {
        list = FXCollections.observableList(storage.getVocabularies());
        lvAllDirectories.setItems(list);
        Collections.sort(list, String::compareTo);
    }

    static void setStorage(Storage storage) {
        DirectoriesController.storage = storage;
    }

    void setLoadingDataDialog(Stage loadingDataDialog) {
        this.loadingDataDialog = loadingDataDialog;
    }

    public void plusButtonClicked(ActionEvent actionEvent) throws IOException {
        if (newDirectoryDialog == null)
            initializeNewDirectoryDialog((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        newDirectoryDialog.setTitle("New directory");
        newDirectoryDialog.showAndWait();
        refreshList();
    }

    public void minusButtonClicked(ActionEvent actionEvent) {
        if (lvAllDirectories.getSelectionModel().getSelectedItem() == null)
            return;
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this directory?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (result.isPresent() && result.get() == ButtonType.NO) return;
        storage.delete(lvAllDirectories.getSelectionModel().getSelectedItem());
        refreshList();
    }

    public void openButtonClicked(ActionEvent actionEvent) throws IOException {
        if (lvAllDirectories.getSelectionModel().getSelectedItem() == null)
            return;
        String vocabularyName = lvAllDirectories.getSelectionModel().getSelectedItem();
        storage.loadVocabulary(vocabularyName);

        openLoadingDataDialog(actionEvent);

        if (vocabularyDialog == null) {
            vocabularyDialog = new Stage();
            FXMLLoader vocabularyFXMLLoader = new FXMLLoader(getClass().getResource("/my-vocabulary.fxml"));
            allWordsDialogRoot = vocabularyFXMLLoader.load();
            vocabularyController = vocabularyFXMLLoader.getController();
            vocabularyDialog.setTitle("All words");
            vocabularyDialog.setResizable(false);
            vocabularyDialog.initModality(Modality.APPLICATION_MODAL);
            vocabularyDialog.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            vocabularyDialog.setScene(new Scene(allWordsDialogRoot));
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
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this directory?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (result.isPresent() && result.get() == ButtonType.NO) return;
            storage.delete(lvAllDirectories.getSelectionModel().getSelectedItem());
            refreshList();
        }
    }

    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (lvAllDirectories.getSelectionModel().getSelectedItem() == null)
            return;
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
        Collections.sort(list, String::compareTo);
        if (lvAllDirectories.getSelectionModel().getSelectedItem() == null) {
            lvAllDirectories.setItems(null);
            lvAllDirectories.setItems(list);
            lvAllDirectories.getSelectionModel().select(lvAllDirectories.getItems().size() - 1);
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
        Parent root = FXMLLoader.load(getClass().getResource("/new-directory-dialog.fxml"));
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

    private void openLoadingDataDialog(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/loading-data.fxml"));
        loadingDataDialog.setTitle("Loading");
        loadingDataDialog.setResizable(false);
        loadingDataDialog.setScene(new Scene(root));
        loadingDataDialog.setOnCloseRequest(Event::consume);
        loadingDataDialog.showAndWait();
    }
}
