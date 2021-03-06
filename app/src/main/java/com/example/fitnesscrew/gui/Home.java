package com.example.fitnesscrew.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.fitnesscrew.R;
import com.example.fitnesscrew.bean.User;
import com.example.fitnesscrew.util.RetrofitConfig;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Activity {

    private Vibrator vibrator;

    private boolean backPressed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


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

    @Override
    public void onBackPressed() {
        vibrator.vibrate(30);

        if(backPressed){
            super.onBackPressed();
            onDestroy();
            return;
        }

        this.backPressed = true;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.Home_RelativeLayout_Principal),"Pressione novamente para sair", Snackbar.LENGTH_LONG);
        snackbar.show();

        new Handler().postDelayed(() -> backPressed = false,3000);
    }

    public void cadastrarParticipante(View view) {
        vibrator.vibrate(30);

        Intent intent = new Intent(Home.this, CadastrarUsuario.class);
        startActivity(intent);

    }

    public void verificarResultados(View view) {
        vibrator.vibrate(30);

        Intent intent = new Intent(this, ResultadosUsuario.class);
        startActivity(intent);
    }

    public void configuracoes(View view) {
        vibrator.vibrate(30);

        //Intent intent = new Intent(this, Configuracao.class);
        //startActivity(intent);


        RetrofitConfig retrofitConfig = new RetrofitConfig();
        Call<String> call = retrofitConfig.getTest().findTest();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Deu ruim");
            }
        });






    }




}
