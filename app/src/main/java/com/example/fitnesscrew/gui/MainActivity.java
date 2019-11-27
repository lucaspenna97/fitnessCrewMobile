package com.example.fitnesscrew.gui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fitnesscrew.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(() -> {

            Intent intent =  new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();

        }, 2000);

    }
}
