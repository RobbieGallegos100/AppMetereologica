package com.tec.robert.examen_practico_ordinario3;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listSens;
    Button btnSens, btnApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSens=(Button)findViewById(R.id.botonSensores);
        listSens=(ListView)findViewById(R.id.listSensor);

        // Porción de los sensores.

        SensorManager sManager = (SensorManager) this
                .getSystemService(this.SENSOR_SERVICE);
        List<Sensor> sensorList = sManager.getSensorList(Sensor.TYPE_ALL);

        List<String> sensorNames = new ArrayList();
        for (int i = 0; i < sensorList.size(); i++) {
            sensorNames.add(((Sensor) sensorList.get(i)).getName());
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sensorNames);

        listSens.setAdapter(itemsAdapter);

        // Porción de los sensores.

        // Porción de los botones.

        btnSens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

    }

    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    // Porción de los botones.



}
