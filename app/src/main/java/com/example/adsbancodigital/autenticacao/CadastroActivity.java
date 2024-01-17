package com.example.adsbancodigital.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adsbancodigital.MainActivity;
import com.example.adsbancodigital.R;
import com.example.adsbancodigital.helper.FirebaseHelper;
import com.example.adsbancodigital.model.Usuario;
import com.google.firebase.database.DatabaseReference;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNome,edtEmail,edtTele,edtSenha, edtConfirma;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        iniciaComponetes();
    }

    private void iniciaComponetes() {
        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtTele = findViewById(R.id.edtTele);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirma = findViewById(R.id.edtConfirma);
        progressBar = findViewById(R.id.progressBar);
    }

    public void validaDados(View view){
        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String telefone = edtTele.getText().toString();
        String senha = edtSenha.getText().toString();
        String confirmaSenha = edtConfirma.getText().toString();

        if (!nome.isEmpty()){
            if (!email.isEmpty()){
                if (!telefone.isEmpty()){
                    if (!senha.isEmpty()){
                        if (!confirmaSenha.isEmpty()){
                            if (senha.equals(confirmaSenha)){

                                progressBar.setVisibility(View.VISIBLE);

                                Usuario usuario = new Usuario();

                                usuario.setNome(nome);
                                usuario.setEmail(email);
                                usuario.setTelefone(telefone);
                                usuario.setSenha(senha);
                                usuario.setSaldo(0);

                                cadastrarUsuario(usuario);

                            }else {
                                edtSenha.setError("Senhas divergentes, por favor tente de novo!");
                                edtConfirma.setError("Senhas divergentes, por favor tente de novo!");
                            }

                        }else {
                            edtConfirma.requestFocus();
                            edtConfirma.setError("Confirme sua senha.");
                        }

                    }else {
                        edtSenha.requestFocus();
                        edtSenha.setError("Informe sua senha.");
                    }

                }else {
                    edtTele.requestFocus();
                    edtTele.setError("Informe seu telefone.");
                }

            }else {
                edtEmail.requestFocus();
                edtEmail.setError("informe seu email.");
            }
        }else {
            edtNome.requestFocus();
            edtNome.setError("Informe seu Nome.");
        }
    }

	 private void cadastrarUsuario(Usuario usuario) {

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                String id  = task.getResult().getUser().getUid();
                usuario.setId(id);

                salvarDadosUsuario(usuario);


            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarDadosUsuario(Usuario usuario){

        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(usuario.getId());
        usuarioRef.setValue(usuario).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}