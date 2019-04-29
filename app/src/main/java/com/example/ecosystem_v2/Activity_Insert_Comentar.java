package com.example.ecosystem_v2;

import android.content.Intent;
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

public class Activity_Insert_Comentar extends AppCompatActivity {

    EditText et_comantar_c3;

    String comentario_c3;

    private String webservice_url = "https://webserviceedgar.herokuapp.com/api_comentarios?user_hash=12345&action=put&";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__insert__comentar);

        //inicialización de EditText de la vista
        et_comantar_c3 = findViewById(R.id.et_comentar_c3);
    }

    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        //webServicePut(webservice_url);

        comentario_c3 = (et_comantar_c3.getText().toString());

        String comentario= comentario_c3.replace(" ","%20");

        sb.append(webservice_url);
        sb.append("id_post="+"1");
        sb.append("&");
        sb.append("id_aluminio_post="+"1");
        sb.append("&");
        sb.append("id_pet_post="+"1");
        sb.append("&");
        sb.append("id_carton_post="+"1");
        sb.append("&");
        sb.append("comentario="+comentario);
        sb.append("&");
        sb.append("categoria="+"Pet");
        sb.append("&");
        sb.append("id_usuario_eco="+"Alejandro%20Lora");
        webServicePut(sb.toString());
        Log.e("URL",sb.toString());

        Toast toast = Toast.makeText(this, R.string.toast_comentar,
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
