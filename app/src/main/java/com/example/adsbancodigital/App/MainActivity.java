package com.example.adsbancodigital.App;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adsbancodigital.R;
import com.example.adsbancodigital.autenticacao.CadastroActivity;
import com.example.adsbancodigital.autenticacao.LoginActivity;
import com.example.adsbancodigital.deposito.DepositoActivity;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configClique();

    }

    private void configClique() {
        findViewById(R.id.cardDeposito).setOnClickListener(v -> {
            startActivity(new Intent(this, DepositoActivity.class));
        });
    }
}
