package com.example.aplicaciondeviajes.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Foto;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.List;

public class VerFotosViajeFragment extends Fragment {

    private VerFotosViajeViewModel viewModel;
    private RecyclerView recyclerViewFotos;
    private Button btnAñadirFoto;
    private FotosAdapter fotoAdapter;
    private String token;

    public static VerFotosViajeFragment newInstance(int viajeId) {
        VerFotosViajeFragment fragment = new VerFotosViajeFragment();
        Bundle args = new Bundle();
        args.putInt("viajeId", viajeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ver_fotos_viaje, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(VerFotosViajeViewModel.class);

        int viajeId = getArguments().getInt("viajeId");
        Log.d("verfotoviaje", String.valueOf(viajeId));  // Convertir el int a String
        token = ApiClient.leerToken(getContext());

        viewModel.obtenerFotos(viajeId, token);

        recyclerViewFotos = getView().findViewById(R.id.recycler_view_fotos);
        recyclerViewFotos.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAñadirFoto = getView().findViewById(R.id.btn_agregar_foto);
        btnAñadirFoto.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("viajeId", viajeId);
            Log.d("verfotoviaje", "aca estoy en verfotos"+String.valueOf(viajeId));
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_verFotosViajeFragment_to_anadirFotosFragment, bundle);  // Pasar el Bundle
        });


        viewModel.getFotos().observe(getViewLifecycleOwner(), new Observer<List<Foto>>() {
            @Override
            public void onChanged(List<Foto> fotos) {
                if (fotos != null && !fotos.isEmpty()) {

                    fotoAdapter = new FotosAdapter(fotos, viewModel, viajeId, token);
                    recyclerViewFotos.setAdapter(fotoAdapter);
                } else {
                    Log.d("verfotoviaje", "No se recibieron fotos o la lista está vacía.");
                }
            }
        });
    }
}
