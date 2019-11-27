package com.example.fitnesscrew.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.fitnesscrew.R;
import com.google.android.material.button.MaterialButton;

public class Configuracao extends Activity {

    private Vibrator vibrator;
    private MaterialButton buttonRabbitMq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        buttonRabbitMq = (MaterialButton) findViewById(R.id.Configuracao_ButtonRabbitMq);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void servicosRabbitMq(View view) {
        vibrator.vibrate(30);

        new AsyncTaskRabbitMQ(this);

    }





}
