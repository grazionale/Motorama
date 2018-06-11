package com.example.gabri.motorama;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Configurações extends AppCompatActivity {
    public static final String ARQUIVO = "preferencias_idioma";
    public static final String IDIOMA = "pt";
    public static String OPCAO = "pt";

    Button btnSalvarConfiguracoes;
    RadioGroup radioGroupConfiguracoes;
    RadioButton radioButtonPt, radioButtonEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        setTitle(getString(R.string.configuracoes));

        radioButtonPt = (RadioButton) findViewById(R.id.radioButtonPt);
        radioButtonEn = (RadioButton) findViewById(R.id.radioButtonEn);

        btnSalvarConfiguracoes = (Button) findViewById(R.id.btnSalvarConfiguracoe);

        prepararRadioGroup();

        btnSalvarConfiguracoes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                System.out.println("Entrou BtnSalvarConfigurações");
                //setLocale(getPreferenceLocale());
                setIdioma();
            }
        });

    }

    private void prepararRadioGroup(){
        switch (OPCAO){
            case "pt":
                radioButtonPt.setChecked(true);
                radioButtonEn.setChecked(false);
                break;
            case "en":
                radioButtonEn.setChecked(true);
                radioButtonPt.setChecked(false);
                break;
        }
    }
    private void setIdioma(){
        radioGroupConfiguracoes = (RadioGroup) findViewById(R.id.radioGroupIdiomas);

        switch (radioGroupConfiguracoes.getCheckedRadioButtonId()){
            case R.id.radioButtonPt:
                setPreferenceLocale("pt");
                Toast.makeText(this, "PT-BR", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButtonEn:
                setPreferenceLocale("en");
                Toast.makeText(this, "EN-US", Toast.LENGTH_SHORT).show();
                break;
        }

        finish();
    }


    private void setPreferenceLocale(String localeName) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(IDIOMA, localeName);
        editor.commit();

        OPCAO = localeName;
    }

}
