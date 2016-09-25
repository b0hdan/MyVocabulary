package com.dubyniak.bohdan.mycutevocabulary.interfaces;

import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.util.List;

public interface Storage {

    void create(VocabularyRecord record);

    List<VocabularyRecord> read();

    void update(VocabularyRecord oldRecord, VocabularyRecord newRecord);

    void delete(VocabularyRecord record);

}
