package com.thesoftparrot.storageapp.myapplication.apiwork;

import org.json.JSONArray;

public interface JsonArrayResponseCallback {
    void onJsonArrayResponseSuccess(JSONArray jsonArray);
    void onJsonArrayResponseFailed(String error);
}
