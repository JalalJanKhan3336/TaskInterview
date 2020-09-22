package com.thesoftparrot.storageapp.myapplication;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface FetchAllCallback {
    void onAllFetchedSuccess(List<DocumentSnapshot> snapshot);
    void onAllFetchedFailure(String error);
}
