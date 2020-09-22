package com.thesoftparrot.storageapp.myapplication.apiwork;

import org.json.JSONArray;
import org.json.JSONObject;

public interface JsonObjectResponseCallback {
    void onJsonObjectResponseSuccess(JSONObject jsonObject);
    void onJsonObjectResponseFailed(String error);
}
