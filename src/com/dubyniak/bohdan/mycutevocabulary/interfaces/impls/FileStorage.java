package com.dubyniak.bohdan.mycutevocabulary.interfaces.impls;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements Storage {
    private List<VocabularyRecord> vocabulary;
    private List<String> directories;
    private String lastVocabularyName;

    public FileStorage() {
        directories = loadDirectories();
    }

    @Override
    public void create(VocabularyRecord record) {
        vocabulary.add(record);
    }

    @Override
    public void create(String directory) {
        directories.add(directory);
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
    public void updateDirectory(String oldDirectory, String newDirectory) {
        directories.set(directories.indexOf(oldDirectory), newDirectory);
    }

    @Override
    public void deleteDirectory(VocabularyRecord record) {
        vocabulary.remove(record);
    }

    @Override
    public void deleteDirectory(String directory) {
        directories.remove(directory);
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
    public void saveVocabulary(String vocabularyName) {
        try (FileOutputStream fos = new FileOutputStream(vocabularyName + ".dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(vocabulary);
            oos.flush();
        } catch (Exception ex) {
            System.out.println("Помилка при збереженні об'єкта!");
        }
    }

    @Override
    public void loadVocabulary(String vocabularyName) {
        lastVocabularyName = vocabularyName;
        vocabulary = new ArrayList<>();
        if (new File(vocabularyName + ".dat").exists())
            try (FileInputStream fis = new FileInputStream(vocabularyName + ".dat");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                vocabulary = (List<VocabularyRecord>) ois.readObject();
            } catch (Exception ex) {
                System.out.println("Помилка при завантаженні об'єкта!");
            }
    }

    @Override
    public void saveDirectories() {
        try (FileOutputStream fos = new FileOutputStream("directories.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(directories);
            oos.flush();
        } catch (Exception ex) {
            System.out.println("Помилка при збереженні об'єкта!");
        }
    }

    private List<String> loadDirectories() {
        List<String> directories = new ArrayList<>();
        if (new File("directories.dat").exists())
            try (FileInputStream fis = new FileInputStream("directories.dat");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                directories = (List<String>) ois.readObject();
            } catch (Exception ex) {
                System.out.println("Помилка при завантаженні об'єкта!");
            }
        return directories;
    }

    @Override
    public List<String> getDirectories() {
        return directories;
    }

    @Override
    public String getLastVocabularyName() {
        return lastVocabularyName;
    }
}
