package com.example.aplicaciondeviajes.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;
import com.example.aplicaciondeviajes.ui.home.ViajeAdapter;

import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private RecyclerView recyclerView;
    private ViajeAdapterGasto viajeAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewViajes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        slideshowViewModel.getViajes().observe(getViewLifecycleOwner(), new Observer<List<Viaje>>() {
            @Override
            public void onChanged(List<Viaje> viajes) {
                if (viajes != null) {
                    viajeAdapter = new ViajeAdapterGasto(viajes, getContext());
                    recyclerView.setAdapter(viajeAdapter);
                }
            }
        });

        slideshowViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
            }
        });

        slideshowViewModel.cargarViajes(getContext());

        return root;
    }
}
