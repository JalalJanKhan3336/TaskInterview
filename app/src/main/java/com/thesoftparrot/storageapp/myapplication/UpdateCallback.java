package com.thesoftparrot.storageapp.myapplication;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface UpdateCallback {
    void onUpdateSuccess(String msg);
    void onUpdateFailure(String error);
}
