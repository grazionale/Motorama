package com.example.gabri.motorama;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.gabri.modelos.Moto;
import com.example.gabri.persistencia.MotoDao;
import com.example.gabri.persistencia.MotoramaDatabase;

import java.util.ArrayList;
import java.util.List;

public class CadastrarGasto extends AppCompatActivity {

    Spinner spinner;
    Button btnAdcGasto;

    //String[] items = new String[]{"1", "2", "3"};
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_gasto);

        // Aparentemente apenas o manifest é suficiente para fazer o botão up de voltar
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }


        spinner = (Spinner) findViewById(R.id.spinnerMotos);
        btnAdcGasto = (Button) findViewById(R.id.btnCadastrarGasto);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listarNomeMotos());
        spinner.setAdapter(adapter);


    }

    public List listarNomeMotos(){
        MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

        List<Moto> lista = database.motoDao().queryAll();

        List<String> modelos = new ArrayList<>();

        for(int i = 0 ; i < lista.size() ; i++){
            modelos.add(lista.get(i).getModelo().toString());
        }

        return modelos;
    }
}
