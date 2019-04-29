package com.example.ecosystem_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_Insert_Aluminio extends AppCompatActivity {

    EditText et_titulo_a;
    EditText et_descripcion_a;
    EditText et_procedimiento_a;
    EditText et_link_a;

    String titulo_a;
    String descripcion_a;
    String procedimiento_a;

    private String webservice_url = "https://webserviceedgar.herokuapp.com/api_aluminio_post?user_hash=12345&action=put&";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__insert__aluminio);

        //inicialización de EditText de la vista
        et_titulo_a = findViewById(R.id.et_titulo_a);
        et_descripcion_a = findViewById(R.id.et_descripcion_a);
        et_procedimiento_a = findViewById(R.id.et_procedimiento_a);
        et_link_a = findViewById(R.id.et_link_a);

    }

    //%20 es igual a espacio
    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        sb.append(webservice_url);

        titulo_a = (et_titulo_a.getText().toString());
        descripcion_a = (et_descripcion_a.getText().toString());
        procedimiento_a = (et_procedimiento_a.getText().toString());

        String titulo= titulo_a.replace(" ","%20");
        String descripcion= descripcion_a.replace(" ","%20");
        String procedimiento= procedimiento_a.replace(" ","%20");
        Log.e("titulo",titulo);

        sb.append("titulo="+titulo);
        sb.append("&");
        sb.append("descripcion="+descripcion);
        sb.append("&");
        sb.append("procedimiento="+procedimiento);
        sb.append("&");
        sb.append("link_video="+et_link_a.getText());
        sb.append("&");
        sb.append("id_usuario_eco="+"1");
        webServicePut(sb.toString());
        Log.e("URL",sb.toString());

        Toast toast = Toast.makeText(this, R.string.toast_pubicar,
                Toast.LENGTH_SHORT);
        toast.show();
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
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
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
}
