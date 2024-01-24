package com.example.adsbancodigital.deposito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.CurrencyAmount;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adsbancodigital.R;
import com.example.adsbancodigital.helper.FirebaseHelper;
import com.example.adsbancodigital.model.Deposito;
import com.example.adsbancodigital.model.Extrato;
import com.example.adsbancodigital.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class DepositoActivity extends AppCompatActivity {

    private EditText edValor;
    private AlertDialog dialog;
    private ProgressBar progressBar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        
        configToobar();
        recuperarUsuario();

        inciaComponente();

    }

    /*public void validaDeposito(View view){
        double valorDeposito =  edValor.getText() / 100;

        if (valorDeposito > 0 ){

            ocultarTecaldo();
            progressBar.setVisibility(View.VISIBLE);
            // Salvar Extrato
            salvarExtrato(valorDeposito);

        }else {

           showDialog("Digite um valor maior que 0.");

        }
    }*/

    // Ocultar o teclado do dispositivo
    private void ocultarTecaldo(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(edValor.getWindowToken(),
                inputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void showDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.Base_Theme_customAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_info, null);

        TextView textTitulo = view.findViewById(R.id.textTitulo);
        textTitulo.setText("Atenção");


        TextView messagem = view.findViewById(R.id.textMessagem);
        messagem.setText(msg);


        Button btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> dialog.dismiss());

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    // Salvar extratos
    private void salvarExtrato(double valorDeposito){
        Extrato extrato = new Extrato();
        extrato.setOperacao("DEPOSITO");
        extrato.setValor(valorDeposito);
        extrato.setOperacao("ENTRADA");

        DatabaseReference extratoReference = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(FirebaseHelper.getIdFirebase())
                .child(extrato.getId());
        extratoReference.setValue(extrato).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                DatabaseReference updateExtrato = extratoReference
                        .child("data");
                updateExtrato.setValue(ServerValue.TIMESTAMP);

                salvarDeposito(extrato);

            }else {

                showDialog("Não foi possével efetuar o deposito, tente mais tarde.");

            }
        });

    }

    private void recuperarUsuario(){
        DatabaseReference usuarioReference =  FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        usuarioReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usuario = snapshot.getValue(Usuario.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void salvarDeposito(Extrato extrato) {
        Deposito deposito = new Deposito();
        deposito.setId(extrato.getId());
        deposito.setValor(extrato.getValor());

        DatabaseReference deposiReference = FirebaseHelper.getDatabaseReference()
                .child("depositos")
                .child(deposito.getId());

        deposiReference.setValue(deposito).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                DatabaseReference updateDeposito = deposiReference
                        .child("data");
                updateDeposito.setValue(ServerValue.TIMESTAMP);

                usuario.setSaldo(usuario.getSaldo() + deposito.getValor());
                usuario.atualizarSaldo();

//                startActivity(new Intent(this, DepositoReciboActivity.class));

            }else {
                progressBar.setVisibility(View.GONE);
                showDialog("Não foi possével efetuar o deposito, tente mais tarde.");

            }
        });

    }

    private void inciaComponente() {

        edValor = findViewById(R.id.edtValor);
        edValor.setTextLocale(new Locale("PT", "br"));
        progressBar = findViewById(R.id.progressBarDe);


    }

    private void configToobar() {
        TextView txtTitulo = findViewById(R.id.txtTitulo);
        txtTitulo.setText("Depositar");
        findViewById(R.id.ibVoltar).setOnClickListener(v -> finish());
    }



}