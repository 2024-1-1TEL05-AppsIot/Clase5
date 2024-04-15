package com.example.clase5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.clase5.databinding.ActivitySensorBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class SensorActivity extends AppCompatActivity {

    ActivitySensorBinding binding;
    SensorManager mSensorManager;
    SensorAccListener listener = new SensorAccListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(mSensorManager != null){ //validar si tengo sensores

            Sensor acelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if(acelerometer != null){ //validar un sensor en particular
                Toast.makeText(this, "Sí tiene acelerómetro", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Su equipo no dispone de acelerómetro",Toast.LENGTH_SHORT).show();
            }

            List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
            for(Sensor sensor : sensorList){
                Log.d("msg-test-sensorList","sensorName: " + sensor.getName());
            }

        }else{
            Toast.makeText(this, "Su dispositivo no posee sensores :(", Toast.LENGTH_SHORT).show();
        }

        //location services
        mostrarUbicacion(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(listener,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        //mSensorManager.registerListener(listener,mAcc,20000000);

        //Sensor mGyr = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //mSensorManager.registerListener(listener,mGyr,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSensorManager.unregisterListener(listener);
    }

    public void mostrarUbicacion(View view) {

        int selfPermissionFineLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int selfPermissionCoarseLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        if (selfPermissionFineLocation == PackageManager.PERMISSION_GRANTED &&
                selfPermissionCoarseLocation == PackageManager.PERMISSION_GRANTED) {

            //tenemos permisos
            FusedLocationProviderClient providerClient = LocationServices.getFusedLocationProviderClient(this);
            providerClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    Log.d("msg-test-location", "latitud: " + location.getLatitude());
                    Log.d("msg-test-location", "longitud: " + location.getLongitude());
                }
            });

        } else {
            //no tenemos permisos, se deben solicitar
            locationPermissionLauncher.launch(new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            });

        }

    }

    ActivityResultLauncher<String[]> locationPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                Boolean fineLocationGranted = result.get(android.Manifest.permission.ACCESS_FINE_LOCATION);
                Boolean coarseLocationGranted = result.get(android.Manifest.permission.ACCESS_COARSE_LOCATION);
                if (fineLocationGranted != null && fineLocationGranted) {
                    Log.d("msg-test-locationPermissionLauncher", "Permiso de ubicación precisa concedido");
                    mostrarUbicacion(binding.getRoot());
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    Log.d("msg-test-locationPermissionLauncher", "Permiso de ubicación aproximada concedido");
                } else {
                    Log.d("msg-test-locationPermissionLauncher", "Ningún permiso concedido");
                }
            }
    );

}