package com.example.ecosystem_v2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_Show_Post_Carton extends AppCompatActivity implements View.OnClickListener {

    TextView tv_titulo_sp_c;
    TextView tv_link_sp_c;
    EditText et_descripcion_sp_c;
    EditText et_procedimiento_sp_c;
    String direccion;

    private String url = "https://webserviceedgar.herokuapp.com/api_carton_post?user_hash=12345&action=get&id_carton_post=";
    private String url_put = "https://webserviceedgar.herokuapp.com/api_guardado?user_hash=12345&action=put&";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__show__post__carton);

        //inicialización de EditText de la vista
        tv_titulo_sp_c = findViewById(R.id.tv_titulo_sp_c);
        et_descripcion_sp_c = findViewById(R.id.et_descripcion_sp_c);
        et_procedimiento_sp_c = findViewById(R.id.et_procedimiento_sp_c);
        tv_link_sp_c = findViewById(R.id.tv_link_sp_c);

        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_post enviado
        String id_post = intent.getStringExtra(MainActivity.ID_POST);
        //Se cocnatena la url con el id_post para obtener los datos
        url+=id_post;
        webServiceRest(url);
        Log.e("url",url);

        direccion="";
        tv_link_sp_c.setOnClickListener(this);
    }
    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_post enviado
        String id_post = intent.getStringExtra(MainActivity.ID_POST);
        //Se cocnatena la url con el id_post para obtener los datos
        //url+=id_post;
        webServicePut(url_put);

        sb.append(url_put);
        sb.append("id_usuario_eco="+"1");
        sb.append("&");
        sb.append("id_carton_post="+id_post);
        webServicePut(sb.toString());
        Log.e("URL",sb.toString());

    }
    private void webServicePut(String requestURL){
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
            parseInformationPut(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }
    private void parseInformationPut(String jsonResult){
        JSONArray jsonArray = null;
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("STATUS",status);
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
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
                id_post = jsonObject.getString("id_carton_post");
                titulo = jsonObject.getString("titulo");
                descripcion = jsonObject.getString("descripcion");
                procedimiento = jsonObject.getString("procedimiento");
                link_video = jsonObject.getString("link_video");
                id_usuario_eco = jsonObject.getString("id_usuario_eco");

                //Se muestran los datos del post en su respectivo EditText
                tv_titulo_sp_c.setText(titulo);
                et_descripcion_sp_c.setText(descripcion);
                et_procedimiento_sp_c.setText(procedimiento);
                tv_link_sp_c.setText(link_video);
                direccion=link_video;
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
    }
    public void onClick(View v){
        direccion="https://www.youtube.com/watch?v=LB2AhSKg1OE";
        ir_youtube(direccion);
    }
    public void ir_youtube(String d){
        Uri uri=Uri.parse(d);
        Intent intentNav= new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intentNav);
    }
    public void comentarios(View view){
        Intent intent = new Intent(getApplicationContext(), Activity_Comentarios.class);
        startActivity(intent);
    }
}
