package com.example.gabri.motorama;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabri.modelos.Gasto;
import com.example.gabri.persistencia.MotoramaDatabase;

import java.util.List;

public class MeusGastos extends AppCompatActivity {

    private ArrayAdapter<Gasto> listaAdapterGastos;
    private ListView listViewGastos;
    private TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_gastos);

        listViewGastos = findViewById(R.id.listViewGastos);

        listViewGastos.setEmptyView(emptyText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fapGastos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaTelaCadastrarGastos();
            }
        });


        listarGastos();
    }

    private void listarGastos(){
        MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

        List<Gasto> lista = database.gastoDao().queryAll();

        listaAdapterGastos = new ArrayAdapter<Gasto>(this, android.R.layout.simple_list_item_1, lista);

        listViewGastos.setAdapter(listaAdapterGastos);

    }

    public void ChamaTelaCadastrarGastos(){
        Intent intent = new Intent(this, CadastrarGasto.class);
        startActivity(intent);
    }
}
