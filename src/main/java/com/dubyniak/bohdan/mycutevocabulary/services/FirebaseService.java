package com.dubyniak.bohdan.mycutevocabulary.services;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sdub on 03.09.2017.
 */
public class FirebaseService implements Storage {
    DatabaseReference ref;
    DatabaseReference usersRef;
    DatabaseReference vocabRef;
    List<VocabularyRecord> currentVocabulary = new ArrayList<>();
    List<String> vocabularyList = new ArrayList<>();
    String lastVocabularyName = "";
    String currentUser = " someone";

    public FirebaseService() {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream
                    ("U:/Java_Project_Folder_U_examles/BohdanVocabulary/vocabulary-trainer-77832-firebase-adminsdk-1cwf0-5825b5f577.json");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://vocabulary-trainer-77832.firebaseio.com/")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);

        ref = FirebaseDatabase
                .getInstance()
                .getReference("vocab/users");
        usersRef = ref.child(currentUser);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vocabularyList = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String vocabularyName = child.getValue(String.class);
                    vocabularyList.add(vocabularyName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                VocabularyRecord vr = dataSnapshot.getValue(VocabularyRecord.class);
//                VocabularyRecord newPost = dataSnapshot.getValue(VocabularyRecord.class);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map<String, VocabularyRecord> map = dataSnapshot.getValue(Map.class);
//                VocabularyRecord newPost = dataSnapshot.getValue(VocabularyRecord.class);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Map<String, VocabularyRecord> map = dataSnapshot.getValue(Map.class);
//                VocabularyRecord newPost = dataSnapshot.getValue(VocabularyRecord.class);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Map<String, VocabularyRecord> map = dataSnapshot.getValue(Map.class);
//                VocabularyRecord newPost = dataSnapshot.getValue(VocabularyRecord.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
//        usersRef.getDatabase()
//        currentVocabulary.add(new VocabularyRecord("apple", "яблуко"));
//        currentVocabulary.add(new VocabularyRecord("table", "стіл"));
//        currentVocabulary.add(new VocabularyRecord("car", "авто"));
//
//        usersRef.setValue(currentVocabulary);

        });*/
    }


    @Override
    public void create(VocabularyRecord record) {
        currentVocabulary.add(record);
        DatabaseReference dr = vocabRef.child(record.getForeignWord());
//        currentVocabulary.add(new VocabularyRecord("cccc", "dddd"));
        dr.setValue(record);
    }

    @Override
    public void createVocabulary(String vocabulary) {
        DatabaseReference dr = usersRef.push();
//        dr.setValue(vocabulary);
    }

    @Override
    public List<VocabularyRecord> read() {
        return currentVocabulary;
    }

    @Override
    public void update(VocabularyRecord oldRecord, VocabularyRecord newRecord) {
        vocabRef.child(oldRecord.getForeignWord()).setValue(null);
        vocabRef.child(newRecord.getForeignWord()).setValue(newRecord);
    }

    @Override
    public void updateVocabulary(String oldVocabulary, String newVocabulary) {

        usersRef.child(oldVocabulary).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersRef.child(newVocabulary).setValue(dataSnapshot.getValue());
                usersRef.child(oldVocabulary).setValue(null);
                usersRef.child(oldVocabulary).removeEventListener(this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void deleteVocabulary(VocabularyRecord record) {
        vocabRef.child(record.getForeignWord()).setValue(null);
    }

    @Override
    public void deleteVocabulary(String vocabulary) {
        usersRef.child(vocabulary).setValue(null);
    }

    @Override
    public void saveVocabulary(String vocabularyName) {
// do nothing
    }

    @Override
    public int sizeOfActive() {
        long s = currentVocabulary.stream().filter(VocabularyRecord::isShown).count();
        return (int) s;
    }

    @Override
    public void loadVocabulary(String name) {
        vocabRef = usersRef.child(name);
        lastVocabularyName = name;
        vocabRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentVocabulary = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    VocabularyRecord vocabularyRec = child.getValue(VocabularyRecord.class);
                    currentVocabulary.add(vocabularyRec);
                }
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
        return vocabularyList;
    }

    @Override
    public String getLastVocabularyName() {
        return lastVocabularyName;
    }
}
