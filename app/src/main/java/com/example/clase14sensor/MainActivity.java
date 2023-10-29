package com.example.clase14sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText pos;
    private SensorManager sm;
    private Sensor s;
    private SensorEventListener sel;
    private int latigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pos = findViewById(R.id.etPos);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (s == null){
            Toast.makeText(this, "No hay sensor",Toast.LENGTH_SHORT).show();
        }else {
            sel = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    float x = sensorEvent.values[0];
                    pos.setText(Float.toString(x));
                    if (x > -5 && latigo == 0){
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    }else if (x > 5 && latigo == 1){
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.RED);
                    }
                    if (latigo == 2){
                        sonido();
                        latigo = 0;
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };
        }
    }
    private void sonido(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.latigo);
        mp.start();
    }
    public void iniciar(){
        sm.registerListener(sel,s,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onResume(){
        iniciar();
        super.onResume();
    }
    public void detener(){
        sm.unregisterListener(sel);
    }
    @Override
    protected void onStop(){
        detener();
        super.onStop();
    }
}