package com.example.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashscreen extends AppCompatActivity {
 private static int TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenui);
        new Handler().postDelayed(new Runnable() {
            @Override
        public void run() {
           Intent homeintent= new Intent(splashscreen.this, logindoc.class);
           startActivity(homeintent);
           finish();

            }
            },TIME_OUT);
        }
    }

