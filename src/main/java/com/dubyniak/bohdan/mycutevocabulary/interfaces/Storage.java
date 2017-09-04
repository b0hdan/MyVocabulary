package com.dubyniak.bohdan.mycutevocabulary.interfaces;

import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.util.List;

public interface Storage {

    void create(VocabularyRecord record);

    void create(String vocabulary);

    List<VocabularyRecord> read();

    void update(VocabularyRecord oldRecord, VocabularyRecord newRecord);

    void update(String oldVocabulary, String newVocabulary);

    void delete(VocabularyRecord record);

    void delete(String vocabulary);

    void saveVocabulary(String vocabularyName);

    int sizeOfShownRecords();

    void loadVocabulary(String vocabularyName);

    void saveVocabularies();

    List<String> getVocabularies();

    String getLastVocabularyName();
}
