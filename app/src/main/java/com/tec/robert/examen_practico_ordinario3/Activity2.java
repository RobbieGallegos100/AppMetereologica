package com.tec.robert.examen_practico_ordinario3;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Activity2 extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "Activity2";

    private SensorManager sensorManager;
    private Sensor acelerometro, temperatura, humedad;

    TextView textX, textY, textZ, textProx, txtXGiro;
    EditText grad, hume;
    Button gradosB, humedadB, btnJason;

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

        grad =(EditText) findViewById(R.id.txtGrados);
        hume =(EditText) findViewById(R.id.txtHumedad);
        gradosB =(Button) findViewById(R.id.btnGrados);
        humedadB =(Button) findViewById(R.id.btnHumedad);
        btnJason =(Button) findViewById(R.id.btnJason);

        textX=(TextView) findViewById(R.id.txtX);
        textY=(TextView) findViewById(R.id.txtY);
        textZ=(TextView) findViewById(R.id.txtZ);

        textProx=(TextView) findViewById(R.id.txtXProx);

        txtXGiro=(TextView) findViewById(R.id.txtXGiro);

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

}
