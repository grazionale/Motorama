package com.example.gabri.motorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gabri.modelos.Moto;
import com.example.gabri.persistencia.MotoramaDatabase;

import java.util.ArrayList;
import java.util.List;

public class CadastrarGasto extends AppCompatActivity {

    Spinner spinnerMotos;
    EditText editTextDescricao, editTextComentario, editTextKm;
    Spinner spinnerOcorrencia;
    Button btnAdcGasto;

    String[] itemsOcorrencia = new String[]{"Bateria", "Cambio", "Direção", "Elétrica", "Filtros", "Iluminação", "Motor", "Pneus", "Radiador", "Óleo", "Outros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_gasto);

        // Aparentemente apenas o manifest é suficiente para fazer o botão up de voltar
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }


        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);
        editTextComentario = (EditText) findViewById(R.id.editTextComentario);
        editTextKm = (EditText) findViewById(R.id.editTextKm);

        spinnerMotos = (Spinner) findViewById(R.id.spinnerMotos);
        spinnerOcorrencia = (Spinner) findViewById(R.id.spinnerOcorrencia);

        btnAdcGasto = (Button) findViewById(R.id.btnCadastrarGasto);

        ArrayAdapter<String> adapterMotos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listarNomeMotos());
        spinnerMotos.setAdapter(adapterMotos);

        ArrayAdapter<String> adapterOcorrencia = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsOcorrencia);
        spinnerOcorrencia.setAdapter(adapterOcorrencia);

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
