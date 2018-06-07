package com.example.gabri.motorama;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gabri.modelos.Gasto;
import com.example.gabri.persistencia.MotoramaDatabase;
import com.example.gabri.utils.Utils;

import java.util.List;

public class TelaGastosDaMoto extends AppCompatActivity {

    private ArrayAdapter<Gasto> listaAdapterGastosDaMoto;
    private ListView listViewGastosDaMoto;

    private static final int REQUEST_NOVO_GASTO   = 1;
    private static final int REQUEST_ALTERAR_GASTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gastos_da_moto);

        setTitle(getString(R.string.gastos_da_moto));

        listViewGastosDaMoto = findViewById(R.id.listViewGastosDaMoto);

        listarGastosDaMoto();

        FloatingActionButton fapGastosDaMoto = (FloatingActionButton) findViewById(R.id.fapGastosDaMoto);
        fapGastosDaMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaTelaCadastrarGastos();
            }
        });

        listViewGastosDaMoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Gasto gasto = (Gasto) parent.getItemAtPosition(position);

                CadastrarGasto.alterarGasto(TelaGastosDaMoto.this, REQUEST_ALTERAR_GASTO, gasto);
            }
        });

        registerForContextMenu(listViewGastosDaMoto);
    }

    private void listarGastosDaMoto(){
        Bundle extras = getIntent().getExtras();

        if(extras == null){
        } else {
            MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

            List<Gasto> lista = database.gastoDao().queryForMotoId(extras.getInt("ID_MOTO_SELECIONADA"));

            listaAdapterGastosDaMoto = new ArrayAdapter<Gasto>(this, android.R.layout.simple_list_item_1, lista);

            listViewGastosDaMoto.setAdapter(listaAdapterGastosDaMoto);
        }

    }

    public void ChamaTelaCadastrarGastos(){
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

        Gasto gasto = (Gasto) listViewGastosDaMoto.getItemAtPosition(info.position);

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
                                MotoramaDatabase database = MotoramaDatabase.getDatabase(TelaGastosDaMoto.this);
                                database.gastoDao().delete(gasto);
                                listaAdapterGastosDaMoto.remove(gasto);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

        Utils.confirmaAcao(this, mensagem, listener);
    }
}
