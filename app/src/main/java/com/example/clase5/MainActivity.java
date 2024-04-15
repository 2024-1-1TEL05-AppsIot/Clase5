package com.example.clase5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.clase5.databinding.ActivityMainBinding;
import com.example.clase5.entity.Employee;
import com.example.clase5.entity.EmployeeDto;
import com.example.clase5.retrofitHelpers.EmployeeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    EmployeeService employeeService;
    private static String TAG = "msg-mainAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createRetrofitService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarListaWebService();
    }

    public void createRetrofitService() {
        employeeService = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EmployeeService.class);
    }

    public void cargarListaWebService() {
        employeeService.obtenerLista().enqueue(new Callback<EmployeeDto>() {
            @Override
            public void onResponse(Call<EmployeeDto> call, Response<EmployeeDto> response) {
                if (response.isSuccessful()) {
                    EmployeeDto body = response.body();
                    List<Employee> employeeList = body.getLista();
                    //tengo la lista -> ready!

                    EmployeeAdapter employeeAdapter = new EmployeeAdapter();
                    employeeAdapter.setListaEmpleados(employeeList);
                    employeeAdapter.setContext(MainActivity.this);

                    binding.recyclerView.setAdapter(employeeAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        /*for(Employee employee: employeeList){
                            Log.d(TAG,"first_name: " + employee.getFirstName());
                        }*/

                } else {
                    Log.d(TAG, "response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<EmployeeDto> call, Throwable t) {
                Log.d(TAG, "algo pasó!!!");
                Log.d(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }
}