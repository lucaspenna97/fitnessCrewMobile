package com.example.fitnesscrew.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.fitnesscrew.R;
import com.example.fitnesscrew.bean.User;
import com.example.fitnesscrew.dao.UserDAO;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CadastrarUsuario extends Activity {

    private Vibrator vibrator;

    private TextInputEditText editTextCpf;
    private TextInputEditText editTextNome;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextIdade;
    private TextInputEditText editTextPeso;
    private TextInputEditText editTextAltura;
    private ImageButton imageButtonBuscar;
    private Spinner spinnerSexo;
    private MaterialButton buttonSalvar;

    private List<String> strings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        editTextCpf = (TextInputEditText) findViewById(R.id.CadastroUsuario_EditTextCpf);
        editTextNome = (TextInputEditText) findViewById(R.id.CadastroUsuario_EditTextNome);
        editTextEmail = (TextInputEditText) findViewById(R.id.CadastroUsuario_EditTextEmail);
        editTextIdade = (TextInputEditText) findViewById(R.id.CadastroUsuario_EditTextIdade);
        editTextPeso = (TextInputEditText) findViewById(R.id.CadastroUsuario_EditTextPeso);
        editTextAltura = (TextInputEditText) findViewById(R.id.CadastroUsuario_EditTextAltura);
        imageButtonBuscar = (ImageButton) findViewById(R.id.CadastroUsuario_ImageButtonBuscar);

        strings = new ArrayList<>();
        strings.add("Selecione");
        strings.add("Masculino");
        strings.add("Feminino");

        spinnerSexo = (Spinner) findViewById(R.id.CadastroUsuario_SpinnerSexo);
        buttonSalvar = (MaterialButton) findViewById(R.id.CadastroUsuario_ButtonSalvar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, strings);
        spinnerSexo.setAdapter(adapter);

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

            editTextCpf.setEnabled(false);
            buttonSalvar.setText("Atualizar");

            editTextNome.setText(user.getNome());
            editTextEmail.setText(user.getEmail());
            editTextAltura.setText(user.getAltura());
            editTextPeso.setText(user.getPeso());
            editTextIdade.setText(user.getIdade());

            if (user.getSexo().equalsIgnoreCase("Masculino")) spinnerSexo.setSelection(1);
            if (user.getSexo().equalsIgnoreCase("Feminino")) spinnerSexo.setSelection(2);



        } else {

            AlertDialog.Builder alert =  new AlertDialog.Builder(this);
            alert.setMessage(Html.fromHtml("CPF <b>inválido</b> para consulta."));
            alert.setNeutralButton("OK", null);
            alert.create();
            alert.show();

        }

    }

    public void inserirUsuario (View view) {
        vibrator.vibrate(30);

        String nome = Objects.requireNonNull(editTextNome.getText()).toString();
        String cpf = Objects.requireNonNull(editTextCpf.getText()).toString();
        String email = Objects.requireNonNull(editTextEmail.getText()).toString();
        String altura = Objects.requireNonNull(editTextAltura.getText()).toString();
        String peso = Objects.requireNonNull(editTextPeso.getText()).toString();
        String idade = Objects.requireNonNull(editTextIdade.getText()).toString();
        String sexo = spinnerSexo.getSelectedItem().toString();


        StringBuilder stringBuilder = new StringBuilder();

        if (cpf.isEmpty()) {
            stringBuilder.append("• O CPF não foi inserido.").append(System.lineSeparator());
        }
        if (nome.isEmpty()){
            stringBuilder.append("• O nome não foi inserido.").append(System.lineSeparator());
        }
        if (email.isEmpty()) {
            stringBuilder.append("• O e-mail não foi inserido.").append(System.lineSeparator());
        }
        if (altura.isEmpty()) {
            stringBuilder.append("• A altura não foi inserida.").append(System.lineSeparator());
        }
        if (peso.isEmpty()) {
            stringBuilder.append("• O peso não foi inserido.").append(System.lineSeparator());
        }
        if (idade.isEmpty()) {
            stringBuilder.append("• A idade nome não foi inserida.").append(System.lineSeparator());
        }
        if (sexo.isEmpty() || sexo.equalsIgnoreCase("Selecione")) {
            stringBuilder.append("• O sexo não foi selecionado.").append(System.lineSeparator());
        }

        if (!stringBuilder.toString().isEmpty()) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Atenção");
            dialog.setMessage("Os seguintes problemas foram encontrados: " + System.lineSeparator() +
                    System.lineSeparator() + stringBuilder.toString());
            dialog.setNeutralButton("OK", null);
            dialog.create();
            dialog.show();

            return;
        }


        User user = new User();
        user.setCpf(cpf);
        user.setNome(nome);
        user.setEmail(email);
        user.setAltura(altura);
        user.setPeso(peso);
        user.setIdade(idade);
        user.setSexo(sexo);


        UserDAO userDAO = new UserDAO(this);
        User exists = userDAO.getUser(user.getCpf());

        if (exists == null) userDAO.insertPedido(user);
        else userDAO.updateUser(user);

        modifyView();

        clearFields();
    }


    private void modifyView() {

        String nameButton = buttonSalvar.getText().toString();

        if (nameButton.equalsIgnoreCase("Atualizar")) {
            editTextCpf.setEnabled(true);
            buttonSalvar.setText("Salvar");
        }

    }

    private void clearFields() {

        editTextCpf.setText("");
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextAltura.setText("");
        editTextPeso.setText("");
        editTextIdade.setText("");
        spinnerSexo.setSelection(0);

        editTextCpf.requestFocus();

    }


}
