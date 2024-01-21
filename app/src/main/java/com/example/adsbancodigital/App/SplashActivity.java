package com.example.adsbancodigital.App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.adsbancodigital.MainActivity;
import com.example.adsbancodigital.R;
import com.example.adsbancodigital.autenticacao.LoginActivity;
import com.example.adsbancodigital.helper.FirebaseHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::getAutenticacao, 4000);
    }

    private void getAutenticacao(){
        if (FirebaseHelper.getAutenticao()){
            startActivity(new Intent(this, MainActivity.class));
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}