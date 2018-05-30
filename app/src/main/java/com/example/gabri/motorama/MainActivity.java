package com.example.gabri.motorama;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabri.modelos.Moto;
import com.example.gabri.motorama.CadastrarMoto;
import com.example.gabri.motorama.Detalhes;
import com.example.gabri.motorama.MeusGastos;
import com.example.gabri.motorama.MeusVeiculos;
import com.example.gabri.motorama.R;
import com.example.gabri.motorama.Sobre;
import com.example.gabri.persistencia.MotoramaDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*****/
    private ArrayAdapter<Moto> listaAdapterMotos;
    private ArrayAdapter<String> listaAdapterMotosEstatico;
    private ListView listViewMotos;
    private TextView emptyText;
    /******/

    public static final String MODO = "MODO";
    public static final String ID = "ID";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //listarMotosEstatico();

        listarMotos();

        listViewMotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Moto moto = (Moto) parent.getItemAtPosition(position);

                abrirMotoSelecionada(moto);
            }
        });

    }

    //******************///
    private void listarMotosEstatico() {
        ArrayList<String> lista = new ArrayList<>();

        lista.add("Moto1");
        lista.add("Moto2");

        listaAdapterMotosEstatico = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        listViewMotos.setAdapter(listaAdapterMotos);
    }

    private void listarMotos() {
        MotoramaDatabase database = MotoramaDatabase.getDatabase(this);

        List<Moto> lista = database.motoDao().queryAll();

        listaAdapterMotos = new ArrayAdapter<Moto>(this, android.R.layout.simple_list_item_1, lista);

        listViewMotos.setAdapter(listaAdapterMotos);

    }

    private void abrirMotoSelecionada(Moto moto) {
        Intent intent = new Intent(this, CadastrarMoto.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, moto.getId());

        startActivityForResult(intent, ALTERAR);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_meus_veiculos) {
            ChamaTelaMeusVeiculos();
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

    public void ChamaTelaCadastrarMoto() {
        Intent intent = new Intent(this, CadastrarMoto.class);
        startActivity(intent);
    }

    private void ChamaTelaSobre() {
        Intent intent = new Intent(this, Sobre.class);
        startActivity(intent);
    }

    private void ChamaTelaMeusVeiculos() {
        Intent intent = new Intent(this, MeusVeiculos.class);
        startActivity(intent);
    }

    private void ChamaTelaDetalhes() {
        Intent intent = new Intent(this, Detalhes.class);
        startActivity(intent);
    }


    public void ChamaTelaMeusGastos() {
        Intent intent = new Intent(this, MeusGastos.class);
        startActivity(intent);
    }

}
