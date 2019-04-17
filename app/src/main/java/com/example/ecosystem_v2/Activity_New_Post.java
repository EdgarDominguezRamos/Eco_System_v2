package com.example.ecosystem_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_New_Post extends AppCompatActivity {

    EditText et_titulo_np;
    EditText et_link_np;
    EditText iv_foto_np;
    EditText et_procedimiento_np;
    EditText et_descripcion_np;

    private String url = "http://webserviceedgar.herokuapp"+"api_post?user_hash=12345&action=put&";
    private String id = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new__post);

        et_titulo_np = findViewById(R.id.et_titulo_np);
        et_link_np = findViewById(R.id.et_link_np);
        //iv_foto = findViewById(R.id.iv_foto);
        et_procedimiento_np = findViewById(R.id.et_procedimiento_np);
        et_descripcion_np = findViewById(R.id.et_descripcion_np);
    }
    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("Titulo="+et_titulo_np.getText());
        sb.append("&");
        sb.append("descripcion="+et_descripcion_np.getText());
        sb.append("&");
        sb.append("procedimineto="+et_procedimiento_np.getText());
        sb.append("&");
        sb.append("link_video="+et_link_np.getText());
        sb.append("&");
        sb.append("id_usuario_eco="+1);
        sb.append("&");
        sb.append("categoria"+"");


        webServicePut(sb.toString());
        Log.e("URL",sb.toString());
        Toast toast = Toast.makeText(this, R.string.toast_mesage,
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
