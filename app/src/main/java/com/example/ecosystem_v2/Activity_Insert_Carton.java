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

public class Activity_Insert_Carton extends AppCompatActivity {

    EditText et_titulo_c;
    EditText et_descripcion_c;
    EditText et_procedimiento_c;
    EditText et_link_c;

    private String webservice_url = "https://webserviceedgar.herokuapp.com/api_carton_post?user_hash=12345&action=put&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__insert__carton);

        //inicialización de EditText de la vista
        et_titulo_c = findViewById(R.id.et_titulo_c);
        et_descripcion_c = findViewById(R.id.et_descripcion_c);
        et_procedimiento_c = findViewById(R.id.et_procedimiento_c);
        et_link_c = findViewById(R.id.et_link_c);

    }

    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        sb.append(webservice_url);
        sb.append("titulo="+et_titulo_c.getText());
        sb.append("&");
        sb.append("descripcion="+et_descripcion_c.getText());
        sb.append("&");
        sb.append("procedimiento="+et_procedimiento_c.getText());
        sb.append("&");
        sb.append("link_video="+et_link_c.getText());
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
