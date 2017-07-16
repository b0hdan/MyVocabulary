package com.dubyniak.bohdan.mycutevocabulary.interfaces;

import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.util.List;

public interface Storage {

    void create(VocabularyRecord record);

    void create(String directory);

    List<VocabularyRecord> read();

    void update(VocabularyRecord oldRecord, VocabularyRecord newRecord);

    void updateDirectory(String oldDirectory, String newDirectory);

    void deleteDirectory(VocabularyRecord record);

    void deleteDirectory(String directory);

    void saveVocabulary(String vocabularyName);

    int sizeOfActive();

    void loadVocabulary(String name);

    void saveDirectories();

    List<String> getDirectories();

    String getLastVocabularyName();
}
