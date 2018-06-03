package com.example.gabri.motorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Configurações extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        setTitle(getString(R.string.configuracoes));
    }
}
