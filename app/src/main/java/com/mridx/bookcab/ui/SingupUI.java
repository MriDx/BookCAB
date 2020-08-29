package com.mridx.bookcab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.mridx.bookcab.R;

public class SingupUI extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputEditText emailField, passwordField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_ui);

        auth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        findViewById(R.id.enterApp).setOnClickListener(this::singupUser);
        findViewById(R.id.enterAsDriver).setOnClickListener(this::signupDriver);

    }

    private void signupDriver(View view) {
        if (auth.getCurrentUser() != null) {
            startInfoUI(0);
            return;
        }
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (email.length() == 0)
            return;
        else if (password.length() < 6)
            return;
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startInfoUI(0);
                        return;
                    }
                    Toast.makeText(this, "Failed ! " + task.getException(), Toast.LENGTH_SHORT).show();
                    create(email, password, 0);
                });

    }

    private void create(String email, String password, int i) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startInfoUI(i);
                        return;
                    }
                    Toast.makeText(this, "Failed ! " + task.getException(), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                });
    }

    private void singupUser(View view) {
        if (auth.getCurrentUser() != null) {
            startInfoUI(1);
            return;
        }
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (email.length() == 0)
            return;
        else if (password.length() < 6)
            return;
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startInfoUI(1);
                        return;
                    }
                    Toast.makeText(this, "Failed ! " + task.getException(), Toast.LENGTH_SHORT).show();
                    create(email, password, 1);
                });
    }


    private void startInfoUI(int i) {
        Intent intent = new Intent(this, InfoUI.class);
        intent.putExtra("IS_DRIVER", i == 0);
        startActivity(intent);
        finish();
    }

}
