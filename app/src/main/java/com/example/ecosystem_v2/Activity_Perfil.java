package com.example.ecosystem_v2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Activity_Perfil extends AppCompatActivity {

    TextView tv_nombre_usuario_p;
    EditText et_descripcion_p;

    private ListView lv_post;
    private ArrayAdapter adapter;
    private String url = "https://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=get&id_usurio_eco=1";
    private String url_user = "https://webserviceedgar.herokuapp.com/api_usuarios_eco?user_hash=12345&action=get&id_usuario_eco=1";

    public static final String ID_POST = "1";
    public static final String ID_USUARIO ="1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__perfil);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lv_post = (ListView)findViewById(R.id.lv_post);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_post.setAdapter(adapter);

        //inicialización de EditText de la vista
        tv_nombre_usuario_p = findViewById(R.id.tv_nombre_usuario_p);
        et_descripcion_p = findViewById(R.id.et_descripcion_p);
        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_cliente enviado
        String id_usuario = intent.getStringExtra(Activity_Perfil.ID_USUARIO);

        url_user+="1";
        //Log.e("url_usuario",url_user);
        webServiceRest(url);

        lv_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lv_post.getItemAtPosition(position).toString());
                String datos_post[] =
                        lv_post.getItemAtPosition(position).toString().split(":");
                String id_post = datos_post[0];
                Log.e("ID_POST",id_post);
                Intent i = new Intent(Activity_Perfil.this, Activity_Show_Post.class);
                i.putExtra(ID_POST,id_post);
                startActivity(i);
            }
        });
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
            parseInformationUsuario(webServiceResult);
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

                adapter.add(id_post + ": " + titulo);

            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }

    private void parseInformationUsuario(String jsonResult){
        JSONArray jsonArray = null;
        String descripcion;
        String nombre;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                nombre = jsonObject.getString("nombre");
                descripcion = jsonObject.getString("descripcion");
                //Se muestran los datos del cliente en su respectivo EditText
                tv_nombre_usuario_p.setText(nombre);
                et_descripcion_p.setText(descripcion);
                Log.e("nombre",nombre);
                Log.e("desc",descripcion);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }


}
