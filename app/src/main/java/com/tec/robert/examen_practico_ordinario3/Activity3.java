package com.tec.robert.examen_practico_ordinario3;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity3 extends AppCompatActivity {

    private TextView mTextViewResult;
    //private RequestQueue mQueue;
    public Button button_parse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);

        mTextViewResult = findViewById(R.id.text_view_result);
        button_parse = findViewById(R.id.button_parse);


        /*
        mQueue = Volley.newRequestQueue(this);
        */

        button_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });


        /*
    }

    private void jsonParse() {

        String url = "https://api.myjson.com/bins/i8sg2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("AppMetereologica");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject info = jsonArray.getJSONObject(i);

                                int Grados = info.getInt("Grados");
                                int Humedad = info.getInt("Humedad");

                                mTextViewResult.setText(Grados + "°, " + Humedad + "% " + "\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

        */
    }

    public void getData(){

        String sql = "https://api.jsonbin.io/b/5b01e1fec2e3344ccd96bcea/11";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try{
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            JSONArray jsonArr = null;

            jsonArr = new JSONArray(json);
            String hora = "", humedad = "", grados = "";

            for (int i = 0;i < jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                jsonObject.optString("Hora de registro");
                hora += i+") Hora de registro: " + jsonObject.optString("Hora de registro") + "\n";

                jsonObject.optInt("Humedad");
                humedad += i+") Humedad: " + jsonObject.optInt("Humedad") + "%" + "\n";

                jsonObject.optInt("Grados");
                grados += i+") Grados: " + jsonObject.optInt("Grados") + "°C" + "\n";

            }
            mTextViewResult.setText(hora+humedad+grados);
            mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
