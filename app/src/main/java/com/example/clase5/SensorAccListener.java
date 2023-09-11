package com.example.clase5;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class SensorAccListener implements SensorEventListener {
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();

        if(sensorType == Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            Log.d("msg-test","x: " + x + " | y: " + y + " | z: " + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
