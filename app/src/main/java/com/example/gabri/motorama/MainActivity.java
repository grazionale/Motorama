package com.example.gabri.motorama;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabri.modelos.Moto;
import com.example.gabri.persistencia.MotoramaDatabase;
import com.example.gabri.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.gabri.motorama.Configurações.ARQUIVO;
import static com.example.gabri.motorama.Configurações.IDIOMA;
import static com.example.gabri.motorama.Configurações.OPCAO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*****/
    private ArrayAdapter<Moto> listaAdapterMotos;
    private ListView listViewMotos;
    private TextView emptyText;
    /******/

    private static final int REQUEST_NOVA_MOTO    = 1;
    private static final int REQUEST_ALTERAR_MOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.meus_veiculos));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getPreferenceLocale();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                ChamaTelaCadastrarMoto();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ***** //

        listViewMotos = findViewById(R.id.listViewMotos);
        emptyText = (TextView) findViewById(android.R.id.empty);
        listViewMotos.setEmptyView(emptyText);

        listarMotos();

        listViewMotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Moto moto = (Moto) parent.getItemAtPosition(position);

                CadastrarMoto.alterarMoto(MainActivity.this, REQUEST_ALTERAR_MOTO, moto);
            }
        });

        registerForContextMenu(listViewMotos);
    }

    //******************///

    private void listarMotos() {
        MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

        List<Moto> lista = database.motoDao().queryAll();

        listaAdapterMotos = new ArrayAdapter<Moto>(this, android.R.layout.simple_list_item_1, lista);

        listViewMotos.setAdapter(listaAdapterMotos);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == REQUEST_NOVA_MOTO || requestCode == REQUEST_ALTERAR_MOTO)
                && resultCode == Activity.RESULT_OK){
            listarMotos();
        }
    }


    public void ChamaTelaCadastrarMoto() {
//        Intent intent = new Intent(this, CadastrarMoto.class);
//
//        intent.putExtra(MODO, NOVO);
//
//        startActivityForResult(intent, NOVO);
        CadastrarMoto.novaMoto(this, REQUEST_NOVA_MOTO);
    }

    private void ChamaTelaSobre() {
        Intent intent = new Intent(this, Sobre.class);
        startActivity(intent);
    }

    private void ChamaTelaMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void ChamaTelaDetalhes() {
        Intent intent = new Intent(this, Configurações.class);
        startActivity(intent);
    }


    public void ChamaTelaMeusGastos() {
        Intent intent = new Intent(this, MeusGastos.class);
        startActivity(intent);
    }

    public void ChamaTelaConfiguracoes() {
        Intent intent = new Intent(this, Configurações.class);
        startActivity(intent);
    }

    private void excluirMoto(final Moto moto){
        String mensagem = "Deseja realmente apagar: " + "\n" + moto.getModelo();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MotoramaDatabase database = MotoramaDatabase.getDatabase(MainActivity.this);
                                database.motoDao().delete(moto);
                                listaAdapterMotos.remove(moto);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

        Utils.confirmaAcao(this, mensagem, listener);
    }

    // ****************//
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ChamaTelaConfiguracoes();
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_meus_veiculos) {
            ChamaTelaMainActivity();
        } else if (id == R.id.nav_informacoes) {
            ChamaTelaSobre();
        } else if (id == R.id.nav_detalhes) {
            ChamaTelaDetalhes();
        } else if (id == R.id.nav_meus_gastos) {
            ChamaTelaMeusGastos();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_moto_selecionada, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Moto moto = (Moto) listViewMotos.getItemAtPosition(info.position);

        switch (item.getItemId()){

            case R.id.menu_moto_editar: {
                CadastrarMoto.alterarMoto(this, REQUEST_ALTERAR_MOTO, moto);
                return true;
            }

            case R.id.menu_moto_apagar: {
                excluirMoto(moto);
                return true;
            }

            default:
                return super.onContextItemSelected(item);
        }
    }



    private void getPreferenceLocale() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
        System.out.println("IDIOMA: " + IDIOMA);
        System.out.println("OPCAO: " + OPCAO);
        OPCAO = shared.getString(IDIOMA, OPCAO);
        System.out.println("IDIOMA 2: " + IDIOMA);
        System.out.println("OPCAO 2: " + OPCAO);
        setLocale(OPCAO);
    }

    private void setLocale(String localeName) {
        Locale locale = new Locale(localeName);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
