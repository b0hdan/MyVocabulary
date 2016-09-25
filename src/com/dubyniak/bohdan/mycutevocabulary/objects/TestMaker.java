package com.dubyniak.bohdan.mycutevocabulary.objects;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMaker {
    private static Storage storage;
    private static Random random = new Random();
    private static List<VocabularyRecord> records = new ArrayList<>();

    public static void setStorage(Storage storage) {
        TestMaker.storage = storage;
    }

    public static void startTest() {
        records.clear();
        VocabularyRecord temp;
        for (int i = 0, n = storage.sizeOfActive(); i < n; i++) {
            while (records.contains(temp = storage.read().get(random.nextInt(storage.read().size()))) ||
                    !temp.isShown());
            records.add(temp);
        }
    }

    public static List<VocabularyRecord> getRecords() {
        return records;
    }

}
