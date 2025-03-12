package com.example.aplicaciondeviajes.ui.Itinerario;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Itinerario;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.List;

public class VerItinerariosFragment extends Fragment {

    private VerItinerariosViewModel mViewModel;
    private RecyclerView recyclerView;
    private VerItinerariosAdapter verItinerariosAdapter;

    public static VerItinerariosFragment newInstance() {
        return new VerItinerariosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ver_itinerarios, container, false);

        Button btnAñadirItinerario = root.findViewById(R.id.btnAñadirItinerario);
        btnAñadirItinerario.setOnClickListener(v -> {
            if (getArguments() != null) {
                int idViaje = getArguments().getInt("idViaje", -1);

                if (idViaje != -1) {
                    Bundle args = new Bundle();
                    args.putInt("idViaje", idViaje);
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_verItinerario_to_anadirItinerario, args);
                } else {
                    Log.e("VerItinerariosFragment", "Error: idViaje no encontrado.");
                }
            } else {
                Log.e("VerItinerariosFragment", "Error: No hay argumentos en el fragmento.");
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(VerItinerariosViewModel.class);

        recyclerView = getView().findViewById(R.id.recyclerViewItinerarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        verItinerariosAdapter = new VerItinerariosAdapter(getContext(), mViewModel);
        recyclerView.setAdapter(verItinerariosAdapter);

        mViewModel.getItinerarios().observe(getViewLifecycleOwner(), itinerarios -> {
            if (itinerarios != null && !itinerarios.isEmpty()) {
                verItinerariosAdapter.setItinerarios(itinerarios);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setAdapter(null);
                getView().findViewById(R.id.noItinerariosTextView).setVisibility(View.VISIBLE);
            }
        });

        if (getArguments() != null) {
            int idViaje = getArguments().getInt("idViaje");
            String token = ApiClient.leerToken(getContext());
            mViewModel.cargarItinerarios(idViaje, token);
        }
    }
}
