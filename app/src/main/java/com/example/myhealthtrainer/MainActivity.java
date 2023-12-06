package com.example.myhealthtrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myhealthtrainer.adapter.FirestoreAdapter;
import com.example.myhealthtrainer.util.FirebaseUtil;
import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.content.Intent;
import android.os.Bundle;

import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private FirestoreAdapter mAdapter;
    private MainActivityViewModel mViewModel;
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        FirebaseFirestore.setLoggingEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn();
            return;
        }

        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }
    private boolean shouldStartSignIn() {
        return (!mViewModel.getIsSigningIn() && FirebaseUtil.getAuth().getCurrentUser() == null);
    }
    private void startSignIn() {
        // Sign in with FirebaseUI
        Intent intent = FirebaseUtil.getAuthUI()
                .createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build()))
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
        mViewModel.setIsSigningIn(true);
    }
}