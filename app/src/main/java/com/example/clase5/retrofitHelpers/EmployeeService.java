package com.example.clase5.retrofitHelpers;

import com.example.clase5.entity.EmployeeDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeService {

    @GET("/")
    Call<EmployeeDto> obtenerLista();
}
