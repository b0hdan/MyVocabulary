package com.dubyniak.bohdan.mycutevocabulary.interfaces.impls;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;

import java.util.ArrayList;
import java.util.List;

public class FileStorage implements Storage {
    private List<VocabularyRecord> vocabulary;
    private List<String> vocabularies;
    private String lastVocabularyName;

    public FileStorage() {
        vocabularies = loadVocabularies();
    }

    @Override
    public void create(VocabularyRecord record) {
        vocabulary.add(record);
    }

    @Override
    public void createVocabulary(String vocabulary) {
        vocabularies.add(vocabulary);
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
    public void updateVocabulary(String oldVocabularyName, String newVocabularyName) {
        vocabularies.set(vocabularies.indexOf(oldVocabularyName), newVocabularyName);
        if (!new File(oldVocabularyName + ".dat").renameTo(new File(newVocabularyName + ".dat")))
            System.out.println("Не вдалося перейменувати файл.");
    }

    @Override
    public void deleteVocabulary(VocabularyRecord record) {
        vocabulary.remove(record);
    }

    @Override
    public void deleteVocabulary(String vocabularyName) {
        if (vocabularyName != null) {
            vocabularies.remove(vocabularyName);
            if (!new File(vocabularyName += ".dat").delete()) System.out.println("Не вдалося видалити файл " + vocabularyName);;
        }
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
        if (!new File(vocabularyName + ".dat").exists())
            vocabulary = new ArrayList<>();
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
        if (new File(vocabularyName + ".dat").exists()) {
            try (FileInputStream fis = new FileInputStream(vocabularyName + ".dat");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                vocabulary = (List<VocabularyRecord>) ois.readObject();
            } catch (Exception ex) {
                System.out.println("Помилка при завантаженні об'єкта!");
            }
        }
    }

    @Override
    public void saveVocabularies() {
        try (FileOutputStream fos = new FileOutputStream("vocabularies.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(vocabularies);
            oos.flush();
        } catch (Exception ex) {
            System.out.println("Помилка при збереженні об'єкта!");
        }
    }

    private List<String> loadVocabularies() {
        List<String> vocabularies = new ArrayList<>();
        if (new File("vocabularies.dat").exists())
            try (FileInputStream fis = new FileInputStream("vocabularies.dat");
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                vocabularies = (List<String>) ois.readObject();
            } catch (Exception ex) {
                System.out.println("Помилка при завантаженні об'єкта!");
            }
        return vocabularies;
    }

    @Override
    public List<String> getVocabularies() {
        return vocabularies;
    }

    @Override
    public String getLastVocabularyName() {
        return lastVocabularyName;
    }
}
