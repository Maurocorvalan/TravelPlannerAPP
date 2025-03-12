package com.example.aplicaciondeviajes.ui.slideshow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Gasto;
public class EditarGastoFragment extends Fragment {
    private EditText editTextMonto, editTextDescripcion;
    private Spinner spinnerCategoria;
    private Button btnGuardar;
    private EditarGastoViewModel editarGastoViewModel;
    private Gasto gasto;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editar_gasto, container, false);

        editTextMonto = root.findViewById(R.id.edit_text_monto);
        editTextDescripcion = root.findViewById(R.id.edit_text_descripcion);
        spinnerCategoria = root.findViewById(R.id.spinner_categoria);
        btnGuardar = root.findViewById(R.id.btn_guardar);

        editarGastoViewModel = new ViewModelProvider(this).get(EditarGastoViewModel.class);

        if (getArguments() != null) {
            int idGasto = getArguments().getInt("idGasto");
            String categoria = getArguments().getString("categoria");
            double monto = getArguments().getDouble("monto");
            String descripcion = getArguments().getString("descripcion");

            gasto = new Gasto();
            gasto.setIdGasto(idGasto);
            gasto.setCategoria(categoria);
            gasto.setMonto(monto);
            gasto.setDescripcion(descripcion);

            editTextMonto.setText(String.valueOf(monto));
            editTextDescripcion.setText(descripcion);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.categorias_array,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoria.setAdapter(adapter);
            int categoriaIndex = obtenerIndiceCategoria(categoria);
            if (categoriaIndex >= 0) {
                spinnerCategoria.setSelection(categoriaIndex);
            }


        }

        btnGuardar.setOnClickListener(v -> {
            gasto.setMonto(Double.parseDouble(editTextMonto.getText().toString()));
            gasto.setDescripcion(editTextDescripcion.getText().toString());
            String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString();
            Log.d("editargasto", categoriaSeleccionada);
            gasto.setCategoria(categoriaSeleccionada);

            // Llamar al ViewModel para hacer el update
            editarGastoViewModel.actualizarGasto(getContext(), gasto);
        });

        return root;
    }
    private int obtenerIndiceCategoria(String categoria) {
        String[] categorias = getResources().getStringArray(R.array.categorias_array);

        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].equals(categoria)) {
                return i;
            }
        }

        return -1;
    }
}
