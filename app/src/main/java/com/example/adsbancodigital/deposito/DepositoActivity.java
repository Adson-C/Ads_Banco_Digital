package com.example.adsbancodigital.deposito;

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

import java.util.Locale;

public class DepositoActivity extends AppCompatActivity {

    private EditText edValor;
    private AlertDialog dialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        
        configToobar();

        inciaComponente();

    }

    /*public void validaDeposito(View view){
        double valorDeposito =  edValor.getText() / 100;

        if (valorDeposito > 0 ){

            ocultarTecaldo();
            progressBar.setVisibility(View.VISIBLE);
            // Salvar Extrato

        }else {
            showDialog();
        }
    }*/

    // Ocultar o teclado do dispositivo
    private void ocultarTecaldo(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(edValor.getWindowToken(),
                inputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.Base_Theme_customAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_info, null);

        TextView textTitulo = view.findViewById(R.id.textTitulo);
        textTitulo.setText("Atenção");


        TextView messagem = view.findViewById(R.id.textMessagem);
        messagem.setText("Digite um valor maior que 0.");

        Button btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> dialog.dismiss());

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
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