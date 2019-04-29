package com.example.ecosystem_v2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_Comentarios extends AppCompatActivity {

    private ListView lv_comentarios_list;
    private ArrayAdapter adapter;
    private String url = "http://webserviceedgar.herokuapp.com/api_comentarios?user_hash=12345&action=get";
    public static final String ID_COMENTARIO = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__comentarios);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lv_comentarios_list= findViewById(R.id.lv_comentarios_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_comentarios_list.setAdapter(adapter);
        webServiceRest(url);

        lv_comentarios_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lv_comentarios_list.getItemAtPosition(position).toString());
                String datos_comentarios[] =
                        lv_comentarios_list.getItemAtPosition(position).toString().split(":");
                String id_comentario = datos_comentarios[0];
                Log.e("ID_COMENTARIO",id_comentario);
                Intent i = new Intent(Activity_Comentarios.this, Activity_Comentarios.class);
                i.putExtra(ID_COMENTARIO,id_comentario);
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
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }
    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_comentario;
        String id_post;
        String fecha_comentario;
        String comentario;
        String id_usuario_eco;

        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_comentario = jsonObject.getString("id_comentario");
                id_post = jsonObject.getString("id_post");
                //fecha_comentario = jsonObject.getString("fecha_comentario");
                comentario = jsonObject.getString("comentario");
                id_usuario_eco = jsonObject.getString("id_usuario_eco");


                adapter.add(comentario + " "+ "-"+id_usuario_eco);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }
    public void comentar(View view) {
        Intent intent = new Intent(getApplicationContext(),Activity_Insert_Comentar.class);
        startActivity(intent);
    }
}
