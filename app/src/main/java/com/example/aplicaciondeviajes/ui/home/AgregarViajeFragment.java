package com.example.aplicaciondeviajes.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.Calendar;

public class AgregarViajeFragment extends Fragment {

    private AgregarViajeViewModel agregarViajeViewModel;
    private EditText etNombre, etDescripcion, etFechaInicio, etFechaFin;
    private Button btnGuardarViaje;

    public static AgregarViajeFragment newInstance() {
        return new AgregarViajeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agregar_viaje, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        agregarViajeViewModel = new ViewModelProvider(this).get(AgregarViajeViewModel.class);

        etNombre = getView().findViewById(R.id.et_nombre_viaje);
        etDescripcion = getView().findViewById(R.id.et_descripcion_viaje);
        etFechaInicio = getView().findViewById(R.id.et_fecha_inicio);
        etFechaFin = getView().findViewById(R.id.et_fecha_fin);
        btnGuardarViaje = getView().findViewById(R.id.btn_guardar_viaje);

        etFechaInicio.setOnClickListener(v -> showDatePickerDialog(etFechaInicio));

        etFechaFin.setOnClickListener(v -> showDatePickerDialog(etFechaFin));

        btnGuardarViaje.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String descripcion = etDescripcion.getText().toString();
            String fechaInicio = etFechaInicio.getText().toString();
            String fechaFin = etFechaFin.getText().toString();

            if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Viaje nuevoViaje = new Viaje(nombre, descripcion, fechaInicio, fechaFin);
                agregarViajeViewModel.agregarViaje(nuevoViaje, requireContext());

                requireActivity().onBackPressed();

            }
        });
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, month1, dayOfMonth1) -> {
                    // Formatear la fecha y ponerla en el EditText
                    String date = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                    editText.setText(date);
                },
                year, month, dayOfMonth
        );

        datePickerDialog.show();
    }
}
