package com.example.aplicaciondeviajes.ui.Itinerario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;

import java.util.List;

public class ItinerarioFragment extends Fragment {

    private ItinerarioViewModel itinerarioViewModel;
    private RecyclerView recyclerView;
    private ItinerarioAdapter itinerarioAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        itinerarioViewModel = new ViewModelProvider(this).get(ItinerarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_itinerario, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewItinerario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itinerarioViewModel.getViajes().observe(getViewLifecycleOwner(), new Observer<List<Viaje>>() {
            @Override
            public void onChanged(List<Viaje> viajes) {
                if (viajes != null) {
                    itinerarioAdapter = new ItinerarioAdapter(viajes, getContext());
                    recyclerView.setAdapter(itinerarioAdapter);
                }
            }
        });

        itinerarioViewModel.cargarViajes(getContext());

        return root;
    }
}
