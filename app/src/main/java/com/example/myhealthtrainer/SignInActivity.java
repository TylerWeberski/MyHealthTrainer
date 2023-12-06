package com.example.myhealthtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        init();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Create sign in checks, this is a template to get the app working

                if ( !(etEmail.getText().toString().isEmpty()) && !(etPassword.getText().toString().isEmpty()))
                {
                    startActivity(new Intent(SignInActivity.this, DashboardActivity.class));
                }
                else
                {
                    Toast.makeText(SignInActivity.this, "Not enough Information", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_create_account);
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
