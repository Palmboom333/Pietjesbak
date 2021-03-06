package com.example.android.pietjesbak;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.pietjesbak.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText userMail, userPassword;
    private Button btnLogin;
    private Button btnLogin2;
    private FirebaseAuth mAuth;
    private Intent PlayernamesActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.loginBtn);
        btnLogin2 = findViewById(R.id.loginBtn2);
        mAuth = FirebaseAuth.getInstance();
        PlayernamesActivity = new Intent(this, com.example.android.pietjesbak.MainActivity.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if (mail.isEmpty() || password.isEmpty()) {
                    showMessage("Please verify all fields");
                } else {

                    signIn(mail, password);

                }

            }
        });

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(), com.example.android.pietjesbak.RegisterActivity.class);
                startActivity(registerActivity);
                finish();

            }
        });

    }

    private void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    updateUI();

                } else {

                    showMessage(task.getException().getMessage());

                }

            }
        });

    }

    private void updateUI() {

        startActivity(PlayernamesActivity);
        finish();

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            // user already connected so user can go to next page
            updateUI();

        }
    }
}
