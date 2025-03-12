package com.example.aplicaciondeviajes.ui.Itinerario;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Itinerario;

public class EditarItinerarioFragment extends Fragment {

    private EditarItinerarioViewModel mViewModel;
    private EditText etActividad, etUbicacion;
    private Button btnGuardar;

    public static EditarItinerarioFragment newInstance() {
        return new EditarItinerarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_itinerario, container, false);
        etActividad = view.findViewById(R.id.etActividad);
        etUbicacion = view.findViewById(R.id.etUbicacion);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditarItinerarioViewModel.class);

        if (getArguments() != null) {
            int idViaje = getArguments().getInt("idViaje");
            int idItinerario = getArguments().getInt("idItinerario");

            mViewModel.cargarItinerario(idViaje, idItinerario);

            mViewModel.getItinerario().observe(getViewLifecycleOwner(), new Observer<Itinerario>() {
                @Override
                public void onChanged(Itinerario itinerario) {
                    if (itinerario != null) {
                        etActividad.setText(itinerario.actividad);
                        etUbicacion.setText(itinerario.ubicacion);
                    }
                }
            });
            btnGuardar.setOnClickListener(v -> {
                String actividad = etActividad.getText().toString();
                String ubicacion = etUbicacion.getText().toString();

                mViewModel.guardarItinerario(idViaje, idItinerario, actividad, ubicacion, v);
            });
        }
    }
}
