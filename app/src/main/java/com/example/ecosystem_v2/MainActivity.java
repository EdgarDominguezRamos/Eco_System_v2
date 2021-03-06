package com.example.ecosystem_v2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
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
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String titulo= "Eco System";

    private ListView lv_post_index;
    private ArrayAdapter adapter;
    private String url = "https://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=get";

    public static final String ID_POST = "1";
    public static final String ID_USUARIO = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(R.string.app_name);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_post_index = (ListView)findViewById(R.id.lv_post_index);
        adapter = new ArrayAdapter(this, R.layout.post_item);
        lv_post_index.setAdapter(adapter);

        webServiceRest(url);

        //ACCION DE LIST VIEW
        lv_post_index.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lv_post_index.getItemAtPosition(position).toString());
                String datos_post[] =
                        lv_post_index.getItemAtPosition(position).toString().split(":");
                String id_post = datos_post[0];
                Log.e("ID_POST",id_post);
                Intent i = new Intent(MainActivity.this, Activity_Show_Post.class);
                i.putExtra(ID_POST,id_post);
                startActivity(i);
            }
        });


        //Navegacion para la app
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_post;
        String titulo;

        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_post = jsonObject.getString("id_post");
                titulo = jsonObject.getString("Titulo");

                adapter.add(id_post +": " + titulo);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

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

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setOnQueryTextListener(this);
        return true;

    }

    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button

        return false;
    }
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Intent intent = new Intent(getApplicationContext(), Activity_Perfil.class);
            startActivity(intent);
        } else if (id == R.id.nav_Guardados) {
            Intent intent = new Intent(getApplicationContext(), Activity_Guardados.class);
            startActivity(intent);
        } else if (id == R.id.nav_Nuevo) {
            Intent intent = new Intent(getApplicationContext(), Activity_Menu_Insertar.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        //Log.d("Valor", String.valueOf(url));
        return true;
    }

    //categorias
    public void carton(View view){
        Intent intent = new Intent(getApplicationContext(), Activity_Carton.class);
        startActivity(intent);
    }
    public void aluminio(View view) {
        Intent intent = new Intent(getApplicationContext(), Activity_Aluminio.class);
        startActivity(intent);
    }
    public void pet(View view) {
        Intent intent = new Intent(getApplicationContext(), Activity_Pet.class);
        startActivity(intent);
    }

}
