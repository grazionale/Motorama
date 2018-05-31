package com.example.gabri.motorama;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gabri.modelos.Gasto;
import com.example.gabri.modelos.Moto;
import com.example.gabri.persistencia.MotoramaDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastrarGasto extends AppCompatActivity {

    Spinner spinnerMotos;
    EditText editTextDescricao, editTextComentario, editTextKm, editTextValor, editTextData;
    Spinner spinnerOcorrencia;
    Button btnAdcGasto;

    String[] itemsOcorrencia = new String[]{"Bateria", "Cambio", "Direção", "Elétrica", "Filtros", "Iluminação", "Motor", "Pneus", "Radiador", "Óleo", "Outros"};

    Calendar mCurrentDate;
    int dia, mes, ano;

    public MotoramaDatabase database;

    private int modo;
    private Gasto gasto;

    public static final String MODO    = "MODO";
    public static final String ID      = "ID";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;

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
        editTextValor = (EditText) findViewById(R.id.editTextValor);
        editTextData = (EditText) findViewById(R.id.editTextData);

        spinnerMotos = (Spinner) findViewById(R.id.spinnerMotos);
        spinnerOcorrencia = (Spinner) findViewById(R.id.spinnerOcorrencia);

        btnAdcGasto = (Button) findViewById(R.id.btnCadastrarGasto);

        ArrayAdapter<String> adapterMotos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listarNomeMotos());
        spinnerMotos.setAdapter(adapterMotos);

        ArrayAdapter<String> adapterOcorrencia = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsOcorrencia);
        spinnerOcorrencia.setAdapter(adapterOcorrencia);

        btnAdcGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarGasto();
            }
        });

        //////////////// Date Pickker////////////////

        mCurrentDate = Calendar.getInstance();

        ano = mCurrentDate.get(Calendar.YEAR);
        mes = mCurrentDate.get(Calendar.MONTH);
        dia = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mes = mes + 1;
        editTextData.setText(dia + "/" + mes + "/" + ano);
        editTextData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CadastrarGasto.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        editTextData.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });

        ///////////////////////////////
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        modo = bundle.getInt(MODO);

        if(modo == ALTERAR){
            System.out.println("ENTROU ALTERAR GASTO");
            int id = bundle.getInt(ID);
            System.out.println("ID GASTO: " + id);
            MotoramaDatabase database = MotoramaDatabase.getDatabase(this);
            gasto = database.gastoDao().queryForId(id);
            System.out.println("GASTO: " + gasto);

            editTextDescricao.setText(gasto.getDescricao());
            editTextComentario.setText(gasto.getComentario());
            editTextData.setText(gasto.getData());
            editTextKm.setText(gasto.getKm() + "");
            editTextValor.setText(gasto.getValor() + "");
            btnAdcGasto.setText("Alterar");

            setTitle(getString(R.string.alterar_despesa));

        } else {
            System.out.println("ENTROU ADICIONAR GASTO");
            gasto = new Gasto();
            setTitle(getString(R.string.novo_gasto));
        }

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

    public void cadastrarGasto(){
        database = MotoramaDatabase.getDatabase(this);

        String moto = spinnerMotos.getSelectedItem().toString();
        String descricao = editTextDescricao.getText().toString();
        String comentario = editTextComentario.getText().toString();
        int km = Integer.parseInt(editTextKm.getText().toString());
        String ocorrencia = spinnerOcorrencia.getSelectedItem().toString();
        double valor = Double.parseDouble(editTextValor.getText().toString());
        String data = editTextData.getText().toString();

        gasto.setMoto(moto);
        gasto.setDescricao(descricao);
        gasto.setComentario(comentario);
        gasto.setKm(km);
        gasto.setOcorrencia(ocorrencia);
        gasto.setValor(valor);
        gasto.setData(data);

        if(modo == NOVO){
            database.gastoDao().insert(gasto);
            Toast.makeText(this, "Gasto inserido com Sucesso !!", Toast.LENGTH_SHORT).show();
        } else {
            database.gastoDao().update(gasto);
            Toast.makeText(this, "Gasto alteardo com Sucesso !!", Toast.LENGTH_SHORT).show();
        }
        setResult(Activity.RESULT_OK);
        finish();



    }

    public static void novoGasto(Activity activity, int requestCode){
        Intent intent = new Intent(activity, CadastrarGasto.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarGasto(Activity activity, int requestCode, Gasto gasto){
        Intent intent = new Intent(activity, CadastrarGasto.class);
        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, gasto.getId());
        activity.startActivityForResult(intent, NOVO);
    }
}
