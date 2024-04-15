package com.example.clase5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.clase5.databinding.ActivityEmployeeDetailBinding;
import com.example.clase5.databinding.ActivityMainBinding;
import com.example.clase5.entity.Employee;

public class EmployeeDetailActivity extends AppCompatActivity {

    ActivityEmployeeDetailBinding binding;

    private static String TAG = "msg-test-EmployeeDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();

        Employee employee = (Employee) intent.getSerializableExtra("employee");
        binding.textViewDetailFirstName.setText("  " + employee.getFirstName());
        binding.textViewDetailLastName.setText("  " + employee.getLastName());
        binding.textViewDetailSalary.setText("  " + employee.getSalary());
        binding.textViewDetailEmail.setText("  " + employee.getEmail());
        binding.textViewDetailPhoneNumber.setText("  " + employee.getPhoneNumber());

        Log.d(TAG, "Presionando el empleado con id: " + employee.getId());

    }
}