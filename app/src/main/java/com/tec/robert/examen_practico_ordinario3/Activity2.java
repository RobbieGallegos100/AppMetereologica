package com.tec.robert.examen_practico_ordinario3;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Activity2 extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "Activity2";

    private SensorManager sensorManager;
    private Sensor acelerometro, temperatura, humedad;

    TextView textX, textY, textZ, textProx, txtXGiro;
    EditText grad, hume;
    Button gradosB, humedadB, btnJason, btn_pasar_json;

    @Override
    protected void onPause() {
            super.onPause();
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            sensorManager.registerListener(Activity2.this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
            textX.setText("Valor en X: " + sensorEvent.values[0]);
            textY.setText("Valor en Y: " + sensorEvent.values[1]);
            textZ.setText("Valor en Z: " + sensorEvent.values[2]);
        }
        else if(sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            sensorManager.registerListener(Activity2.this, temperatura, SensorManager.SENSOR_DELAY_NORMAL);
            gradosB.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textProx.setText(sensorEvent.values[0]+ "C°");
                }
            }, 3666666);
        }
        else if(sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            sensorManager.registerListener(Activity2.this, humedad, SensorManager.SENSOR_DELAY_NORMAL);
            humedadB.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtXGiro.setText(sensorEvent.values[0]+ "%");
                }
            }, 3666666);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        btn_pasar_json = findViewById(R.id.btnMeterJson);
        grad = findViewById(R.id.txtGrados);
        hume = findViewById(R.id.txtHumedad);
        gradosB = findViewById(R.id.btnGrados);
        humedadB = findViewById(R.id.btnHumedad);
        btnJason = findViewById(R.id.btnJason);

        textX= findViewById(R.id.txtX);
        textY= findViewById(R.id.txtY);
        textZ= findViewById(R.id.txtZ);

        textProx= findViewById(R.id.txtXProx);

        txtXGiro= findViewById(R.id.txtXGiro);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(acelerometro != null) {
            sensorManager.registerListener(Activity2.this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            textX.setText("No disponible.");
            textY.setText("No disponible.");
            textZ.setText("No disponible.");
        }

        temperatura = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(temperatura != null) {
            sensorManager.registerListener(Activity2.this, temperatura, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            textProx.setText("No disponible.");
        }

        humedad = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if(humedad != null) {
            sensorManager.registerListener(Activity2.this, humedad, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            txtXGiro.setText("No disponible.");
        }


        gradosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String grados;
                    grados = grad.getText().toString();

                    Integer.parseInt(grados);
                    if (isInteger(grados) == true) {
                        textProx.setText(grados + "C°");

                    } else {
                        Toast.makeText(getApplicationContext(), "Intente de nuevo", Toast.LENGTH_SHORT);
                    }
            }
        });

        humedadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String humedad;
                    humedad = hume.getText().toString();

                    Integer.parseInt(humedad);
                    if (isInteger(humedad) == true) {
                        txtXGiro.setText(humedad + "%");

                    } else {
                        Toast.makeText(getApplicationContext(), "Intente de nuevo", Toast.LENGTH_SHORT);
                    }
            }
        });

        btnJason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();
            }
        });

        btn_pasar_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasarAJson();
            }
        });


    }

    public boolean isInteger(String numero){
        try{
            Integer.parseInt(numero);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public void openActivity3(){
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    public void PasarAJson(){

            String sql = "https://api.jsonbin.io/b/5b01e1fec2e3344ccd96bcea/11";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = null;
            HttpURLConnection conn;

            try{
                url = new URL(sql);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("INSERT");

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
                //mTextViewResult.setText(hora+humedad+grados);
                //mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

}
