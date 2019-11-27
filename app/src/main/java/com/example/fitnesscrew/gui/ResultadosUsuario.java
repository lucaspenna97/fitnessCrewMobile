package com.example.fitnesscrew.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.fitnesscrew.R;
import com.example.fitnesscrew.bean.User;
import com.example.fitnesscrew.dao.UserDAO;
import com.example.fitnesscrew.util.MailSender;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ResultadosUsuario extends Activity {


    private Vibrator vibrator;

    private TextInputEditText editTextCpf;
    private TextInputEditText editTextNome;
    private TextInputEditText editTextTmb;
    private TextInputEditText editTextImc;
    private TextInputEditText editTextImcGrau;
    private TextInputEditText editTextComentario;

    private ImageButton imageButtonBuscar;

    private MaterialButton buttonSalvar;

    private LinearLayout linearLayoutMetrica;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados_usuario);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        editTextCpf = (TextInputEditText) findViewById(R.id.ResultadosUsuario_EditTextCpf);
        editTextNome = (TextInputEditText) findViewById(R.id.ResultadosUsuario_EditTextNome);
        editTextTmb = (TextInputEditText) findViewById(R.id.ResultadosUsuario_EditTextTmb);
        editTextImc = (TextInputEditText) findViewById(R.id.ResultadosUsuario_EditTextImc);
        editTextImcGrau = (TextInputEditText) findViewById(R.id.ResultadosUsuario_EditTextGrauImc);
        editTextComentario = (TextInputEditText) findViewById(R.id.ResultadosUsuario_EditTextComentario);

        imageButtonBuscar = (ImageButton) findViewById(R.id.ResultadosUsuario_ImageButtonBuscar);
        buttonSalvar = (MaterialButton) findViewById(R.id.ResultadosUsuario_ButtonEnviarEmail);
        linearLayoutMetrica = (LinearLayout) findViewById(R.id.ResultadosUsuario_LinearLayoutMetrica);

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


    public void pesquisarUsuario (View view) {
        vibrator.vibrate(30);

        String cpf = Objects.requireNonNull(editTextCpf.getText()).toString();

        UserDAO userDAO = new UserDAO(this);
        User user = userDAO.getUser(cpf);

        if (user != null) {

            String imc = ProcessResults.calculateImc(user.getPeso(), user.getAltura());
            String grauImc = ProcessResults.calculateGrauImc(Integer.parseInt(imc));
            String comentario = ProcessResults.resumoDescricao(Integer.parseInt(imc));
            String taxaMetabolica = ProcessResults.calculateTmb(Integer.parseInt(user.getPeso()),
                    Integer.parseInt(user.getAltura()),
                    Integer.parseInt(user.getIdade()),
                    user.getSexo());

            editTextCpf.setText(user.getCpf());
            editTextNome.setText(user.getNome());
            editTextImc.setText(imc);
            editTextImcGrau.setText(grauImc);
            editTextComentario.setText(comentario);
            editTextTmb.setText(taxaMetabolica);

            if (grauImc.equalsIgnoreCase("Abaixo do peso")) linearLayoutMetrica.setBackgroundColor(Color.parseColor("#ff9933"));
            if (grauImc.equalsIgnoreCase("Peso normal")) linearLayoutMetrica.setBackgroundColor(Color.parseColor("#00e600"));
            if (grauImc.equalsIgnoreCase("Acima do peso")) linearLayoutMetrica.setBackgroundColor(Color.parseColor("#ff6600"));
            if (grauImc.equalsIgnoreCase("Obesidade grau I")) linearLayoutMetrica.setBackgroundColor(Color.parseColor("#ff1a1a"));
            if (grauImc.equalsIgnoreCase("Obesidade grau II")) linearLayoutMetrica.setBackgroundColor(Color.parseColor("#e60000"));
            if (grauImc.equalsIgnoreCase("Obesidade grau III")) linearLayoutMetrica.setBackgroundColor(Color.parseColor("#990000"));

        } else {

            AlertDialog.Builder alert =  new AlertDialog.Builder(this);
            alert.setMessage(Html.fromHtml("CPF <b>inválido</b> para consulta."));
            alert.setNeutralButton("OK", null);
            alert.create();
            alert.show();

        }

    }


    public void enviarEmail (View view) {

        vibrator.vibrate(30);

        String cpf = Objects.requireNonNull(editTextCpf.getText()).toString();

        UserDAO userDAO = new UserDAO(this);
        User user = userDAO.getUser(cpf);

        if (user != null) {

            new MailSender(this, user);

        } else {

            AlertDialog.Builder alert =  new AlertDialog.Builder(this);
            alert.setMessage(Html.fromHtml("CPF <b>inválido</b> para consulta."));
            alert.setNeutralButton("OK", null);
            alert.create();
            alert.show();

        }

    }


}
