package com.example.ecosystem_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.ecosystem_v2.Activity_Perfil.ID_POST;

public class Activity_Login extends AppCompatActivity {

    EditText et_usuario_l;
    EditText et_password_l;

    private ArrayAdapter adapter;
    private String url = "https://webserviceedgar.herokuapp.com/api_post?user_hash=12345&action=get";
    public static final String ID_USUARIOL ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);

        et_usuario_l = findViewById(R.id.et_usuario_l);
        et_password_l = findViewById(R.id.et_password_l);
        Button boton =(Button) findViewById(R.id.bttn_iniciar_l);

        Intent intent = getIntent();
        //Se almacena el id_cliente enviado
        String id_usuario = intent.getStringExtra(Activity_Login.ID_USUARIOL);

        url+="1";
        Log.e("url_usuario",url);
        webServiceRest(url);

                //Log.e("ITEM", lv_post.getItemAtPosition(position).toString());
                //String datos_post[] =
                //        lv_post.getItemAtPosition(position).toString().split(":");
                //String id_post = datos_post[0];
                //Log.e("ID_POST",id_post);
                //Intent i = new Intent(Activity_Perfil.this, Activity_Show_Post.class);
                //i.putExtra(ID_POST,id_post);
                //startActivity(i);
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
        String status;
        String usuario;
        String password;
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
                usuario = jsonObject.getString("nombre");
                password = jsonObject.getString("password");
                Log.e("STATUS",status);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }
    public void iniciar_sesion(String jsonResult){
        try{
            parseInformation(jsonResult);
            webServiceRest(url);
            String existe ="existe";
            System.out.println(existe + " "+ url);
            int Existe=Integer.parseInt(existe);

            if(Existe==1){

                System.out.println("iniciando sesion");
                Intent i = new Intent(Activity_Login.this, MainActivity.class);
                //i.putExtra(ID_POST,id_post);
                startActivity(i);

            }

            else{

            }

        }
        catch (Exception e) {
            Log.e("Error 100",e.getMessage());
        }
    }

}
