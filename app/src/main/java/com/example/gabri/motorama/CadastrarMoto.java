package com.example.gabri.motorama;

import android.app.Activity;
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
        MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

        Moto moto = new Moto();

        moto.setModelo(editTextModelo.getText().toString());
        moto.setMarca(editTextMarca.getText().toString());
        moto.setPlaca(editTextPlaca.getText().toString());
        moto.setAno(Integer.parseInt(editTextAno.getText().toString()));
        try {

            database.motoDao().insert(moto);
            setResult(Activity.RESULT_OK);
            finish();

        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
