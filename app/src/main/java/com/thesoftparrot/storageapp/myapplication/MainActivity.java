package com.thesoftparrot.storageapp.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thesoftparrot.storageapp.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ListFragment.QuantityAddedListener {

    private ActivityMainBinding mBinding;
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initRef();
        authUser();
    }

    private void authUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInAnonymously()
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Auth Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initRef() {
        mNavController = Navigation.findNavController(this, R.id.host_fragment);
        NavigationUI.setupWithNavController(mBinding.bottomNavView,mNavController);

        ListFragment.setListener(this);
    }

    @Override
    public void onQuantityAdded(int quantity) {
        if(quantity > 0)
            mBinding.bottomNavView.getOrCreateBadge(R.id.cartFragment);
    }
}