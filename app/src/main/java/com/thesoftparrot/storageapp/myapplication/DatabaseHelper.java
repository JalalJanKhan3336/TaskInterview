package com.thesoftparrot.storageapp.myapplication;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.common.RequestBuilder;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.thesoftparrot.storageapp.myapplication.apiwork.JsonArrayResponseCallback;
import com.thesoftparrot.storageapp.myapplication.apiwork.JsonObjectResponseCallback;
import com.thesoftparrot.storageapp.myapplication.apiwork.KeyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class DatabaseHelper {
    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance() {
        if(instance == null)
            instance = new DatabaseHelper();
        return instance;
    }

    private FirebaseFirestore mRootRef;

    private DatabaseHelper() {
        mRootRef = FirebaseFirestore.getInstance();
        AndroidNetworking.initialize(MyApp.getInstance().getApplicationContext());
    }

    public void fetchAll(String collection, final FetchAllCallback listener){

        if(listener == null)
            throw new NullPointerException("FetchAllCallback can not be NULL");

        mRootRef
                .collection(collection)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        listener.onAllFetchedSuccess(documentSnapshots.getDocuments());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onAllFetchedFailure(e.getMessage());
                    }
                });

    }

    public void update(String collection, String document, Object value, final UpdateCallback listener) {

        if(listener == null)
            throw new NullPointerException("UpdateCallback can not be NULL");

        mRootRef
                .collection(collection)
                .document(document)
                .set(value)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onUpdateSuccess("Added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onUpdateFailure(e.getMessage());
                    }
                });

    }

    public void getProductList(String url, final JsonArrayResponseCallback listener){

        if(listener == null)
            throw new NullPointerException("JsonArrayResponseCallback can not be NULL");

        OkHttpClient.Builder builder = getClientBuilder();

        AndroidNetworking
                .get(url)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(builder.build())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onJsonArrayResponseSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        listener.onJsonArrayResponseFailed(anError.getErrorBody());
                    }
                });

    }

    private OkHttpClient.Builder getClientBuilder() {

        return new OkHttpClient().newBuilder().authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                if(response.request().header("Authorization") != null)
                    return null;
                else {
                    String credential = Credentials.basic(KeyUtils.BASIC_AUTH_USERNAME, KeyUtils.BASIC_AUTH_PASSWORD);

                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                }
            }
        });
    }

}

