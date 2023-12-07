package com.example.myhealthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myhealthtrainer.adapter.FirestoreAdapter;
import com.example.myhealthtrainer.util.FirebaseUtil;
import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnCreateAccount;

    private static final int RC_SIGN_IN = 9001;
    private FirestoreAdapter mAdapter;
    private MainActivityViewModel mViewModel;
    public FirebaseAuth mAuth;
    public FirebaseUser user;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance(); //db
        mAuth = FirebaseAuth.getInstance(); //authenticate
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        FirebaseFirestore.setLoggingEnabled(true);

        init();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!(email.isEmpty()) && !(password.isEmpty()))
                {
                    signInFB(email, password);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Enter all information", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

    }

    private void signInFB(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            user = mAuth.getCurrentUser();

                            if (user != null) {
                                // User is signed in and email is verified
                                Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                                MainActivityViewModel.setUser(user);
                                setUserDocument();
                                startActivity(new Intent(MainActivity.this, DashboardActivity.class));

                            } else {
                                // Email not verified or user not signed in
                                Toast.makeText(MainActivity.this, "Email not verified or user not signed in", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void setUserDocument()
    {
        DocumentReference userRef = db.collection("users").document(user.getUid());

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if ( !(documentSnapshot.exists()) )
                {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("email", user.getEmail());
                    userData.put("currentCalories", 0);  // Set initial values
                    userData.put("goalCalories", 0);
                    userData.put("currentFat", 0);
                    userData.put("goalFat", 0);
                    userData.put("currentSodium", 0);
                    userData.put("goalSodium", 0);
                    userData.put("currentCarbs", 0);
                    userData.put("goalCarbs", 0);
                    userData.put("currentSugar", 0);
                    userData.put("goalSugar", 0);
                    userData.put("currentProtein", 0);
                    userData.put("goalProtein", 0);

                    userRef.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "User data added", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "FAILURE ON DATA SET", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {

                }
            }
        });

    }
    private void init()
    {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
    }
}
