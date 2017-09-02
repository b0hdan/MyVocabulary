package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.StatisticRecord;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;


public class StatisticController {
    private ObservableList<StatisticRecord> list = FXCollections.observableArrayList();
    private static Storage storage;

    @FXML
    TableView<StatisticRecord> tableView;

    @FXML
    private TableColumn<StatisticRecord, String> directory;

    @FXML
    private TableColumn<StatisticRecord, Integer> numberOfNewWords;

    @FXML
    private TableColumn<StatisticRecord, Integer> numberOfNeedRepeatingWords;

    @FXML
    private TableColumn<StatisticRecord, Integer> numberOfAllWords;

    @FXML
    private void initialize() {
        directory.setCellValueFactory(new PropertyValueFactory<>("directory"));
        numberOfNewWords.setCellValueFactory(new PropertyValueFactory<>("numberOfNewWords"));
        numberOfNeedRepeatingWords.setCellValueFactory(new PropertyValueFactory<>("numberOfNeedRepeatingWords"));
        numberOfAllWords.setCellValueFactory(new PropertyValueFactory<>("numberOfAllWords"));
        tableView.setItems(list);
    }

    public static void setStorage(Storage storage) {
        StatisticController.storage = storage;
    }

    void fillList() {
        list.clear();
        int allWords;
        int allNewWords = 0;
        int allNeedRepeatingWords = 0;
        int allAllWords = 0;
        for (String vocabulary : storage.getVocabularies()) {
            int newWords = 0;
            int needRememberingWords = 0;
            allWords = 0;
            storage.loadVocabulary(vocabulary);
            for (VocabularyRecord vocabularyRecord : storage.read()) {
                if ((vocabularyRecord.getShowDate() == null))
                    newWords++;
                else if (!vocabularyRecord.getShowDate().after(new Date()))
                    needRememberingWords++;
                allWords++;
            }
            allNewWords += newWords;
            allNeedRepeatingWords += needRememberingWords;
            allAllWords += allWords;
            list.add(new StatisticRecord(vocabulary, newWords, needRememberingWords, allWords));
        }
        list.add((new StatisticRecord("Summary", allNewWords, allNeedRepeatingWords, allAllWords)));
    }
}
