package com.mridx.bookcab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mridx.bookcab.R;

public class SplashUI extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed((Runnable) () -> {
            startActivity(new Intent(this, SingupUI.class));
            finish();
        }, 1000 * 3);
    }
}
