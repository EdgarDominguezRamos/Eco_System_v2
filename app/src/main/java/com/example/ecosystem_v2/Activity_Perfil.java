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

    private ListView lv_post;
    private ArrayAdapter adapter;

    TextView tv_nombre_usuario;
    EditText et_descripcion;

    private String url = "https://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=get&id_usurio_eco=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__perfil);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_post = findViewById(R.id.lv_post);
        adapter = new ArrayAdapter(this, R.layout.activity__new__post);
        lv_post.setAdapter(adapter);

        webServiceRest(url);

        tv_nombre_usuario = findViewById(R.id.tv_nombre_usuario);
        et_descripcion = findViewById(R.id.et_descripcion);
        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_cliente enviado
        //String id_usuario_eco = intent.getStringExtra(Activity_Perfil.ID_USUARIO_ECO);
        //Se cocnatena la url con el id_cliente para obtener los datos el cliente
        //url+=id_usuario_eco;
        //webServiceRest(url);
        
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
        String procedimiento;
        String link_video;
        String imagen;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_post = jsonObject.getString("id_post");
                titulo = jsonObject.getString("titulo");
                procedimiento = jsonObject.getString("procedimiento");
                link_video = jsonObject.getString("link_video");
                imagen = jsonObject.getString("imagen");
                adapter.add(titulo );

                tv_nombre_usuario.setText((CharSequence) tv_nombre_usuario);
                et_descripcion.setText((CharSequence) et_descripcion);
                URL newurl = new URL(url+imagen);
                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                //iv_imagen.setImageBitmap(mIcon_val);

            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            } catch (MalformedURLException e) {
                Log.e("Error 103",e.getMessage());
            } catch (IOException e) {
                Log.e("Error 104",e.getMessage());
            }
        }
    }
}
