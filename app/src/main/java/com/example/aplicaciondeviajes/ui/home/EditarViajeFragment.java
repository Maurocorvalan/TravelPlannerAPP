package com.example.aplicaciondeviajes.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;

import java.util.Calendar;

public class EditarViajeFragment extends Fragment {

    private EditarViajeViewModel viewModel;
    private Viaje viaje;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viaje = (Viaje) getArguments().getSerializable("viaje");
        }

        viewModel = new ViewModelProvider(this).get(EditarViajeViewModel.class);
        if (viaje != null) {
            viewModel.setViaje(viaje);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editar_viaje, container, false);

        EditText editNombre = root.findViewById(R.id.edit_nombre);
        EditText editDescripcion = root.findViewById(R.id.edit_descripcion);
        EditText editFechaInicio = root.findViewById(R.id.edit_fecha_inicio);
        EditText editFechaFin = root.findViewById(R.id.edit_fecha_fin);
        Button btnEditar = root.findViewById(R.id.btn_editar_viaje);
        Button btnEliminar = root.findViewById(R.id.btn_eliminar_viaje);

        viewModel.getNombre().observe(getViewLifecycleOwner(), nombre -> editNombre.setText(nombre));
        viewModel.getDescripcion().observe(getViewLifecycleOwner(), descripcion -> editDescripcion.setText(descripcion));
        viewModel.getFechaInicio().observe(getViewLifecycleOwner(), fechaInicio -> editFechaInicio.setText(fechaInicio));
        viewModel.getFechaFin().observe(getViewLifecycleOwner(), fechaFin -> editFechaFin.setText(fechaFin));

        editFechaInicio.setOnClickListener(v -> showDatePickerDialog(editFechaInicio));

        editFechaFin.setOnClickListener(v -> showDatePickerDialog(editFechaFin));

        btnEditar.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString();
            String descripcion = editDescripcion.getText().toString();
            String fechaInicio = editFechaInicio.getText().toString();
            String fechaFin = editFechaFin.getText().toString();
            viewModel.actualizarViaje(getContext(), viaje, nombre, descripcion, fechaInicio, fechaFin);
            requireActivity().onBackPressed();
        });
        btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Eliminar Viaje")
                    .setMessage("¿Estás seguro de que deseas eliminar este viaje?")
                    .setPositiveButton("Sí", (dialog, which) -> viewModel.eliminarViaje(getContext(), viaje.idViaje, ()->{
                        requireActivity().onBackPressed();
                    }))
                    .setNegativeButton("No", null)
                    .show();

        });

        return root;
    }

    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                    editText.setText(date);
                }, year, month, day);

        datePickerDialog.show();
    }
}
