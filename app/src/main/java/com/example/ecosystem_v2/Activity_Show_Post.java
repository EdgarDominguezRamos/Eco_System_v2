package com.example.ecosystem_v2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_Show_Post extends AppCompatActivity {

    TextView tv_titulo_sp;
    EditText et_descripcion_sp;
    EditText et_procedimiento_sp;

    private String url = "https://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=get&id_post=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        setContentView(R.layout.activity__show__post);

        //inicialización de EditText de la vista
        tv_titulo_sp = findViewById(R.id.tv_titulo_sp);
        et_descripcion_sp = findViewById(R.id.et_descripcion_sp);
        et_procedimiento_sp = findViewById(R.id.et_procedimiento_sp);

        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_post enviado
        String id_post = intent.getStringExtra(MainActivity.ID_POST);
        //Se cocnatena la url con el id_post para obtener los datos
        url+=id_post;
        webServiceRest(url);
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
        String descripcion;
        String procedimiento;
        String link_video;
        String id_usuario_eco;

        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos post del webservice
                id_post = jsonObject.getString("id_post");
                titulo = jsonObject.getString("Titulo");
                descripcion = jsonObject.getString("descripcion");
                procedimiento = jsonObject.getString("procedimiento");
                link_video = jsonObject.getString("link_video");
                id_usuario_eco = jsonObject.getString("id_usuario_eco");

                //Se muestran los datos del post en su respectivo EditText
                tv_titulo_sp.setText(titulo);
                et_descripcion_sp.setText(descripcion);
                et_procedimiento_sp.setText(procedimiento);

            }catch (JSONException e){
                e.printStackTrace();
            }

        }
    }
}
