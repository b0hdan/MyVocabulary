package com.dubyniak.bohdan.mycutevocabulary.services;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirebaseService implements Storage {
    private DatabaseReference dbReference;
    private DatabaseReference userReference;
    private DatabaseReference vocabularyReference;
    private List<VocabularyRecord> vocabulary;
    private List<String> vocabularies;
    private String lastVocabularyName;
    private String currentUser = "someone";
    private Stage loadingDataDialog;

    public FirebaseService() {
        connectToDatabase();

        dbReference = FirebaseDatabase
                .getInstance()
                .getReference("vocabulary/users");
        userReference = dbReference.child(currentUser);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vocabularies = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren())
                    vocabularies.add(child.getKey());
                Platform.runLater(loadingDataDialog::close);
                userReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setLoadingDataDialog(Stage loadingDataDialog) {
        this.loadingDataDialog = loadingDataDialog;
    }

    private void connectToDatabase() {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("mycutevocabulary-firebase-adminsdk-pnrc5-c1871563cc.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://mycutevocabulary.firebaseio.com/")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
    }

    @Override
    public void create(VocabularyRecord record) {
        vocabularyReference.child(record.getForeignWord()).setValue(record);
        vocabulary.add(record);
    }

    @Override
    public void create(String vocabulary) {
        userReference.child(vocabulary).setValue(0);
        vocabularies.add(vocabulary);
    }

    @Override
    public List<VocabularyRecord> read() {
        return vocabulary;
    }

    @Override
    public void update(VocabularyRecord oldRecord, VocabularyRecord newRecord) {
        vocabularyReference.child(oldRecord.getForeignWord()).setValue(null);
        vocabularyReference.child(newRecord.getForeignWord()).setValue(newRecord);
        vocabulary.set(vocabulary.indexOf(oldRecord), newRecord);
    }

    @Override
    public void update(String oldVocabulary, String newVocabulary) {
        userReference.child(oldVocabulary).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userReference.child(newVocabulary).setValue(dataSnapshot.getValue());
                userReference.child(oldVocabulary).setValue(null);
                vocabularies.set(vocabularies.indexOf(oldVocabulary), newVocabulary);
                userReference.child(oldVocabulary).removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void delete(VocabularyRecord record) {
        vocabularyReference.child(record.getForeignWord()).setValue(null);
        vocabulary.remove(record);
    }

    @Override
    public void delete(String vocabulary) {
        userReference.child(vocabulary).setValue(null);
        vocabularies.remove(vocabulary);
    }

    @Override
    public void saveVocabulary(String vocabularyName) {
    }

    @Override
    public int sizeOfShownRecords() {
        return (int) vocabulary.stream().filter(VocabularyRecord::isShown).count();
    }

    @Override
    public void loadVocabulary(String vocabularyName) {
        lastVocabularyName = vocabularyName;
        vocabularyReference = userReference.child(vocabularyName);
        vocabulary = null;
        vocabularyReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vocabulary = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren())
                    vocabulary.add(child.getValue(VocabularyRecord.class));
                Platform.runLater(loadingDataDialog::close);
                vocabularyReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void saveVocabularies() {

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
