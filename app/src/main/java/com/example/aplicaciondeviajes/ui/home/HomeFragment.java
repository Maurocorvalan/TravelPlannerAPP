package com.example.aplicaciondeviajes.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private AgregarViajeViewModel agregarViajeViewModel;

    private ViajeAdapter viajeAdapter;
    private Button btnAddTrip;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        agregarViajeViewModel = new ViewModelProvider(this).get(AgregarViajeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));

        recyclerView.addItemDecoration(dividerItemDecoration);
        btnAddTrip = root.findViewById(R.id.btn_add_trip);

        btnAddTrip.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_home_to_agregarViajeFragment);
        });

        Log.d("HomeFragment", "RecyclerView configurado");

        homeViewModel.obtenerViajes(requireContext());
        homeViewModel.getViajes().observe(getViewLifecycleOwner(), new Observer<List<Viaje>>() {
            @Override
            public void onChanged(List<Viaje> viajes) {
                Log.d("HomeFragment", "Viajes obtenidos: " + viajes.size());
                viajeAdapter = new ViajeAdapter(viajes, requireContext());
                recyclerView.setAdapter(viajeAdapter);
                for (Viaje viaje : viajes) {
                    if (viaje.getFotoUrl() == null) {
                        homeViewModel.obtenerFoto(requireContext(), viaje);
                    }
                }
            }
        });
        // Observando si un viaje se ha creado
        agregarViajeViewModel.getViajeCreado().observe(getViewLifecycleOwner(), viajeCreado -> {
            if (viajeCreado != null && viajeCreado) {
                // Si el viaje fue creado correctamente, actualizar la lista
                homeViewModel.obtenerViajes(requireContext());
            }
        });


        return root;
    }
}
