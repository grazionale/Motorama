package com.example.gabri.motorama;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabri.modelos.Moto;
import com.example.gabri.motorama.R;
import com.example.gabri.persistencia.MotoramaDatabase;

public class CadastrarMoto extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String ID = "ID";
    public static final int ALTERAR = 2;
    public static final int NOVO = 1;

    private EditText editTextModelo, editTextPlaca, editTextAno, editTextMarca;
    private Button btnCadastrar;

    private int modo;
    private Moto moto;


    public static MotoramaDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_moto);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextModelo = (EditText) findViewById(R.id.editTextModelo);
        editTextMarca = (EditText) findViewById(R.id.editTextMarca);
        editTextPlaca = (EditText) findViewById(R.id.editTextPlaca);
        editTextAno = (EditText) findViewById(R.id.editTextAno);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrarMoto);

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
////
//        //modo = bundle.getInt(MODO);
//
//        if(modo == ALTERAR){
////    IMPLEMENTAR
//        } else{
//            moto = new Moto();
//            setTitle(getString(R.string.nova_moto));
//        }

        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cadastrarMoto();
            }
        });
    }

    public void cadastrarMoto(){
       // Toast.makeText(this, "PEGOU", Toast.LENGTH_SHORT).show();
        database = MotoramaDatabase.getDatabase(this);

        int ano = Integer.parseInt(editTextAno.getText().toString());
        String modelo = editTextModelo.getText().toString();
        String marca = editTextMarca.getText().toString();
        String placa = editTextPlaca.getText().toString();

        Moto moto = new Moto();

        moto.setModelo(modelo);
        moto.setMarca(marca);
        moto.setPlaca(placa);
        moto.setAno(ano);
        try {

            database.motoDao().insert(moto);
            //setResult(Activity.RESULT_OK);
            Toast.makeText(this, "Moto inserida com Sucesso !!", Toast.LENGTH_SHORT).show();
            finish();

        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
