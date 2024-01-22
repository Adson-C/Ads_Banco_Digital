package com.example.adsbancodigital.deposito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adsbancodigital.R;

public class DepositoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        
        configToobar();

    }

    private void configToobar() {
        TextView txtTitulo = findViewById(R.id.txtTitulo);
        txtTitulo.setText("Depositar");
        findViewById(R.id.ibVoltar).setOnClickListener(v -> finish());
    }

}