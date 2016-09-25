package com.dubyniak.bohdan.mycutevocabulary.interfaces.impls;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.util.ArrayList;
import java.util.List;

public class StorageImpl implements Storage {
    private List<VocabularyRecord> vocabulary;

    public StorageImpl() {
        vocabulary = new ArrayList<>();
    }

    public StorageImpl(List<VocabularyRecord> vocabulary) {
        this.vocabulary = vocabulary;
    }

    @Override
    public void create(VocabularyRecord record) {
        vocabulary.add(record);
    }

    @Override
    public List<VocabularyRecord> read() {
        return vocabulary;
    }

    @Override
    public void update(VocabularyRecord oldRecord, VocabularyRecord newRecord) {
        vocabulary.set(vocabulary.indexOf(oldRecord), newRecord);
    }

    @Override
    public void delete(VocabularyRecord record) {
        vocabulary.remove(record);
    }

}
