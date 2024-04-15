package com.example.clase5;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clase5.entity.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> listaEmpleados;
    private Context context;

    private static String TAG = "msg-test-EmployeeViewHolder";

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.irv_employee, parent, false);
        return new EmployeeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = listaEmpleados.get(position);
        holder.employee = employee;

        TextView tvFirstName = holder.itemView.findViewById(R.id.textViewFirstName);
        TextView tvLastName = holder.itemView.findViewById(R.id.textViewLastName);
        TextView tvSalary = holder.itemView.findViewById(R.id.textViewSalary);

        tvFirstName.setText(employee.getFirstName());
        tvLastName.setText(employee.getLastName());
        tvSalary.setText(String.valueOf(employee.getSalary()));

        if (employee.getSalary() >= 10000) {
            tvSalary.setTextColor(Color.RED);
        } else {
            tvSalary.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.size();
    }

    public List<Employee> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Employee> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        Employee employee;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            Button button = itemView.findViewById(R.id.button2);
            button.setOnClickListener(view -> {
                String id = employee.getId();
                Log.d(TAG, "Presionando el empleado con id: " + id);

                Intent intent = new Intent(context,EmployeeDetailActivity.class);
                intent.putExtra("employee", employee);

                context.startActivity(intent);
            });
        }
    }
}
