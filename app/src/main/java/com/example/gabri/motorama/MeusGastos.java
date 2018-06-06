package com.example.gabri.motorama;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabri.modelos.Gasto;
import com.example.gabri.modelos.Moto;
import com.example.gabri.persistencia.MotoramaDatabase;
import com.example.gabri.utils.Utils;

import java.util.List;

public class MeusGastos extends AppCompatActivity {

    private ArrayAdapter<Gasto> listaAdapterGastos;
    private ListView listViewGastos;
    private TextView emptyText;

    private static final int REQUEST_NOVO_GASTO   = 1;
    private static final int REQUEST_ALTERAR_GASTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_gastos);
        setTitle(getString(R.string.meus_gastos));

        listViewGastos = findViewById(R.id.listViewGastos);

        listViewGastos.setEmptyView(emptyText);

        listarGastos();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fapGastos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaTelaCadastrarGastos();
            }
        });


        listViewGastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Gasto gasto = (Gasto) parent.getItemAtPosition(position);

                CadastrarGasto.alterarGasto(MeusGastos.this, REQUEST_ALTERAR_GASTO, gasto);
                //abrirMotoSelecionada(moto);
            }
        });

        registerForContextMenu(listViewGastos);
    }

    private void listarGastos(){
        MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

        List<Gasto> lista = database.gastoDao().queryAll();

        listaAdapterGastos = new ArrayAdapter<Gasto>(this, android.R.layout.simple_list_item_1, lista);

        listViewGastos.setAdapter(listaAdapterGastos);

    }

    public void ChamaTelaCadastrarGastos(){
//        Intent intent = new Intent(this, CadastrarGasto.class);
//        startActivity(intent);
        CadastrarGasto.novoGasto(this, REQUEST_NOVO_GASTO);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_gasto_selecionado, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Gasto gasto = (Gasto) listViewGastos.getItemAtPosition(info.position);

        switch (item.getItemId()){

            case R.id.menu_gasto_editar: {
                CadastrarGasto.alterarGasto(this, REQUEST_ALTERAR_GASTO, gasto);
                return true;
            }

            case R.id.menu_gasto_apagar: {
                excluirGasto(gasto);
                return true;
            }

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void excluirGasto(final Gasto gasto){
        String mensagem = "Deseja realmente apagar: " + "\n" + gasto.getDescricao();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MotoramaDatabase database = MotoramaDatabase.getDatabase(MeusGastos.this);
                                database.gastoDao().delete(gasto);
                                listaAdapterGastos.remove(gasto);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

        Utils.confirmaAcao(this, mensagem, listener);
    }
}
