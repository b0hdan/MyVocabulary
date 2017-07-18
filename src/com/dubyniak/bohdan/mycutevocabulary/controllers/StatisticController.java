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
    private TableView<StatisticRecord> tableView;

    @FXML
    private TableColumn<StatisticRecord, String> directory;

    @FXML
    private TableColumn<StatisticRecord, Integer> numberOfNewWords;

    @FXML
    private TableColumn<StatisticRecord, Integer> numberOfNeedRepeatingWords;

    @FXML
    private void initialize() {
        directory.setCellValueFactory(new PropertyValueFactory<>("directory"));
        numberOfNewWords.setCellValueFactory(new PropertyValueFactory<>("numberOfNewWords"));
        numberOfNeedRepeatingWords.setCellValueFactory(new PropertyValueFactory<>("numberOfNeedRepeatingWords"));
        tableView.setItems(list);
    }

    public static void setStorage(Storage storage) {
        StatisticController.storage = storage;
    }

    void fillList() {
        list.clear();
        for (String vocabulary : storage.getVocabularies()) {
            int newWords = 0;
            int needRemeberingWords = 0;
            storage.loadVocabulary(vocabulary);
            for (VocabularyRecord vocabularyRecord : storage.read()) {
                if ((vocabularyRecord.getShowDate() == null))
                    newWords++;
                else if (!vocabularyRecord.getShowDate().after(new Date()))
                    needRemeberingWords++;
            }
            list.add(new StatisticRecord(vocabulary, newWords, needRemeberingWords));
        }
    }
}
