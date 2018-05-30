package com.example.gabri.motorama;

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.drm.DrmStore;
import android.provider.ContactsContract;
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

import java.security.Principal;

public class CadastrarMoto extends AppCompatActivity {

    private EditText editTextModelo, editTextPlaca, editTextAno, editTextMarca;
    private Button btnCadastrar;

    private int modo;
    private Moto moto;

    public static final String MODO    = "MODO";
    public static final String ID      = "ID";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;

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
        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cadastrarMoto();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        modo = bundle.getInt(MODO);

        if(modo == ALTERAR){
            System.out.println("ABRIU IFFFF");
            int id = bundle.getInt(ID);
            System.out.println("ID: " + id);
            MotoramaDatabase database = MotoramaDatabase.getDatabase(this);
            moto = database.motoDao().queryForId(id);
            System.out.println("Moto: " + moto);

            editTextMarca.setText(moto.getMarca());
            editTextModelo.setText(moto.getModelo());
            editTextAno.setText(moto.getAno() + "");
            editTextPlaca.setText(moto.getPlaca());
            btnCadastrar.setText("Alterar");
            setTitle("Alterar Moto");

        } else{
            System.out.println("ABRIU ELSE");
            moto = new Moto();
            setTitle(getString(R.string.nova_moto));
        }



    }

    public void cadastrarMoto(){
       // Toast.makeText(this, "PEGOU", Toast.LENGTH_SHORT).show();
        database = MotoramaDatabase.getDatabase(this);

        int ano = Integer.parseInt(editTextAno.getText().toString());
        String modelo = editTextModelo.getText().toString();
        String marca = editTextMarca.getText().toString();
        String placa = editTextPlaca.getText().toString();

        moto.setModelo(modelo);
        moto.setMarca(marca);
        moto.setPlaca(placa);
        moto.setAno(ano);
        System.out.println("Moto Update: " + moto);
        if(modo == NOVO){
            System.out.println("Entrou SAVE");
            database.motoDao().insert(moto);
        } else {
            System.out.println("Entrou UPDATE");
            database.motoDao().update(moto);
        }

        setResult(Activity.RESULT_OK);
        finish();
//        try {
//
//            database.motoDao().insert(moto);
//            setResult(Activity.RESULT_OK);
//            //Toast.makeText(this, "Moto inserida com Sucesso !!", Toast.LENGTH_SHORT).show();
//            finish();
//
//        } catch(Exception e){
//            System.out.println("GetMessage: " + e.getMessage());
//        }


    }

    public static void novaMoto(Activity activity, int requestCode){

        Intent intent = new Intent(activity, CadastrarMoto.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarMoto(Activity activity, int requestCode, Moto moto){

        Intent intent = new Intent(activity, CadastrarMoto.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, moto.getId());

        activity.startActivityForResult(intent, ALTERAR);
    }
}
