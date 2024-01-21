package com.example.adsbancodigital.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adsbancodigital.MainActivity;
import com.example.adsbancodigital.R;
import com.example.adsbancodigital.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private TextView txtConta, txtRecuperar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();

    }

    public void validaDadoss(View view){

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if (!email.isEmpty()){
            if (!senha.isEmpty()){

                progressBar.setVisibility(View.VISIBLE);

                logar(email, senha);

            }else {
                edtSenha.requestFocus();
                edtSenha.setError("Informe sua senha.");
            }
        }else {
            edtEmail.requestFocus();
            edtEmail.setError("Informe seu Email.");
        }

    }

    private void logar(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
           finish();
           startActivity(new Intent(this, MainActivity.class));
            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void criarConta(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void recuperarConta(View view){
        startActivity(new Intent(this, RecuperarContaActivity.class));
    }

    private void iniciaComponentes() {
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        progressBar = findViewById(R.id.progresse);
    }
}