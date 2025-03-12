package com.example.aplicaciondeviajes.ui.slideshow;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class AnadirGastoFragment extends Fragment {

    private AnadirGastoViewModel mViewModel;
    private EditText  editMonto, editDescripcion;
    private Spinner spinnerCategoria;
    private Button btnGuardar;

    public static AnadirGastoFragment newInstance() {
        return new AnadirGastoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anadir_gasto, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AnadirGastoViewModel.class);
        int idViaje = getArguments() != null ? getArguments().getInt("idViaje") : -1;

        spinnerCategoria = getView().findViewById(R.id.spinner_categorias);
        editMonto = getView().findViewById(R.id.edit_monto);
        editDescripcion = getView().findViewById(R.id.edit_descripcion);
        btnGuardar = getView().findViewById(R.id.btn_guardar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        btnGuardar.setOnClickListener(v -> {
            String categoria = spinnerCategoria.getSelectedItem().toString();
            double monto = Double.parseDouble(editMonto.getText().toString());
            String descripcion = editDescripcion.getText().toString();

            Log.d("anadirgasto", "afragment" + idViaje);
            Gasto nuevoGasto = new Gasto( categoria, monto, descripcion);
            nuevoGasto.setIdViaje(idViaje);
            mViewModel.crearGasto(getContext(), nuevoGasto);
            getActivity().setResult(Activity.RESULT_OK);
            requireActivity().onBackPressed();

        });
    }
}
