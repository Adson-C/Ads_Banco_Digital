package com.example.adsbancodigital.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adsbancodigital.R;
import com.example.adsbancodigital.helper.FirebaseHelper;
import com.example.adsbancodigital.model.Usuario;

public class RecuperarContaActivity extends AppCompatActivity {


    private EditText edtEmail;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);

        iniciaComponetes();
    }
    private void iniciaComponetes() {
        edtEmail = findViewById(R.id.edtEmailRec);
        progressBar = findViewById(R.id.progressReceber);
    }

    public void validaDados(View view){
        String email = edtEmail.getText().toString();

            if (!email.isEmpty()){
                progressBar.setVisibility(View.VISIBLE);
                recuperarConta(email);

            }else {
                edtEmail.requestFocus();
                edtEmail.setError("informe seu email.");
            }
    }

    private void recuperarConta(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Acabamos de te enviar um link via e-mail.", Toast.LENGTH_SHORT).show();
                
            }else {

                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });
    }
}