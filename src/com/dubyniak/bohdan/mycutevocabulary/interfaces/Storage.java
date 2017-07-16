package com.dubyniak.bohdan.mycutevocabulary.interfaces;

import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.util.List;

public interface Storage {

    void create(VocabularyRecord record);

    void createVocabulary(String vocabulary);

    List<VocabularyRecord> read();

    void update(VocabularyRecord oldRecord, VocabularyRecord newRecord);

    void updateVocabulary(String oldVocabulary, String newVocabulary);

    void deleteVocabulary(VocabularyRecord record);

    void deleteVocabulary(String vocabulary);

    void saveVocabulary(String vocabularyName);

    int sizeOfActive();

    void loadVocabulary(String name);

    void saveVocabularies();

    List<String> getVocabularies();

    String getLastVocabularyName();
}
