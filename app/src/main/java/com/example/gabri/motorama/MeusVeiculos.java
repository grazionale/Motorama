package com.example.gabri.motorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabri.modelos.Moto;
import com.example.gabri.motorama.R;
import com.example.gabri.persistencia.MotoramaDatabase;

import java.util.List;

public class MeusVeiculos extends AppCompatActivity {

    private ArrayAdapter<Moto> listaAdapterMotos;
    private ArrayAdapter<String> listaAdapterMotosEstatico;
    private ListView listViewMotos;
    private TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_veiculos);

        setTitle(getString(R.string.meus_veiculos));

        listViewMotos = findViewById(R.id.listViewMotos);
        emptyText = (TextView)findViewById(android.R.id.empty);
        listViewMotos.setEmptyView(emptyText);

        listarMotos();
    }


    private void listarMotos(){
        MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

        List<Moto> lista = database.motoDao().queryAll();

        listaAdapterMotos = new ArrayAdapter<Moto>(this, android.R.layout.simple_list_item_1, lista);

        listViewMotos.setAdapter(listaAdapterMotos);

    }
}
