package com.example.aplicaciondeviajes.ui.Itinerario;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicaciondeviajes.R;

public class AnadirItinerarioFragment extends Fragment {

    private AnadirItinerarioViewModel mViewModel;
    private EditText etActividad, etUbicacion;
    private Button btnGuardar;

    public static AnadirItinerarioFragment newInstance() {
        return new AnadirItinerarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_anadir_itinerario, container, false);
        etActividad = root.findViewById(R.id.edit_actividad);
        etUbicacion = root.findViewById(R.id.edit_ubicacion);
        btnGuardar = root.findViewById(R.id.btn_guardar);
        int idViaje = getArguments() != null ? getArguments().getInt("idViaje") : -1;

        btnGuardar.setOnClickListener(v -> {
            String actividad = etActividad.getText().toString();
            String ubicacion = etUbicacion.getText().toString();

            if (actividad.isEmpty() || ubicacion.isEmpty()) {
                Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                // Pasar el idViaje junto con los demás datos
                mViewModel.guardarItinerario(idViaje, actividad, ubicacion, v);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AnadirItinerarioViewModel.class);
    }

}
