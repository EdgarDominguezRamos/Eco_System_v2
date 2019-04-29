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

public class Activity_Aluminio extends AppCompatActivity {

    private ListView lv_aluminio_list;
    private ArrayAdapter adapter;
    private String url = "https://webserviceedgar.herokuapp.com/api_aluminio_post?user_hash=12345&action=get";

    public static final String ID_POSTA = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluminio);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_aluminio_list = findViewById(R.id.lv_aluminio_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_aluminio_list.setAdapter(adapter);
        webServiceRest(url);


        lv_aluminio_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ITEM", lv_aluminio_list.getItemAtPosition(position).toString());
                String datos_post[] =
                        lv_aluminio_list.getItemAtPosition(position).toString().split(":");
                String id_post = datos_post[0];
                Log.e("ID_POSTA",id_post);
                Intent i = new Intent(Activity_Aluminio.this, Activity_Show_Post_Aluminio.class);
                i.putExtra(ID_POSTA,id_post);
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
                id_post = jsonObject.getString("id_aluminio_post");
                titulo = jsonObject.getString("titulo");

                adapter.add(id_post +": " + titulo);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}

