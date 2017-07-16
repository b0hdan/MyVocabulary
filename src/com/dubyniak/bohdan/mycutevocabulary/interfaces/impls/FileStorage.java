package com.dubyniak.bohdan.mycutevocabulary.interfaces.impls;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FileStorage implements Storage {
    private List<VocabularyRecord> vocabulary;

    public FileStorage() {
        vocabulary = loadVocabulary();
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

    @Override
    public int sizeOfActive() {
        int counter = 0;
        for (VocabularyRecord record : vocabulary)
            if (record.isShown())
                counter++;
        return counter;
    }

    @Override
    public void saveVocabulary() {
        try (FileOutputStream fos = new FileOutputStream("vocabulary.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(vocabulary);
            oos.flush();
        } catch (Exception ex) {
            System.out.println("Помилка при збереженні об'єкта!");
        }
    }

    private List<VocabularyRecord> loadVocabulary() {
        List<VocabularyRecord> vocabulary = null;
        try (FileInputStream fis = new FileInputStream("vocabulary.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            vocabulary = (List<VocabularyRecord>) ois.readObject();
        } catch (Exception ex) {
            System.out.println("Помилка при завантаженні об'єкта!");
        }
        return vocabulary;
    }
}
