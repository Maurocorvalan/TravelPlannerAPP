package com.example.aplicaciondeviajes.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;

import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        galleryViewModel.obtenerViajes(requireContext());
        galleryViewModel.getViajes().observe(getViewLifecycleOwner(), new Observer<List<Viaje>>() {
            @Override
            public void onChanged(List<Viaje> viajes) {
                galleryAdapter = new GalleryAdapter(viajes, requireContext());
                recyclerView.setAdapter(galleryAdapter);
                for (Viaje viaje : viajes) {
                    if (viaje.getFotoUrl() == null) {
                        galleryViewModel.obtenerFoto(requireContext(), viaje);
                    }
                }
            }
        });

        return root;
    }
}
