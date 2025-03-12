package com.example.aplicaciondeviajes.ui.slideshow;

import static android.app.Activity.RESULT_OK;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.aplicaciondeviajes.models.Gasto;
import com.example.aplicaciondeviajes.ui.slideshow.GastoViewModel;

import java.util.List;

public class GastoFragment extends Fragment {

    private GastoViewModel mViewModel;
    private RecyclerView recyclerView;
    private GastoAdapter gastoAdapter;
    private TextView noGastosMessage;

    public static GastoFragment newInstance() {
        return new GastoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gasto, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GastoViewModel.class);

        recyclerView = getView().findViewById(R.id.recyclerViewGastos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noGastosMessage = getView().findViewById(R.id.noGastosMessage);
        TextView totalGastadoTextView = getView().findViewById(R.id.totalGastado);
        int idViaje = getArguments().getInt("idViaje");
        Button btnAnadirGasto = getView().findViewById(R.id.btnAnadirGasto);

        btnAnadirGasto.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idViaje", idViaje);
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_gastos_to_AnadirGasto, bundle);
        });

        mViewModel.getGastos().observe(getViewLifecycleOwner(), new Observer<List<Gasto>>() {
            @Override
            public void onChanged(List<Gasto> gastos) {
                if (gastos != null && !gastos.isEmpty()) {
                    // Hay gastos, mostramos el RecyclerView y ocultamos el mensaje
                    gastoAdapter = new GastoAdapter(gastos, getContext(), mViewModel);
                    recyclerView.setAdapter(gastoAdapter);
                    noGastosMessage.setVisibility(View.GONE);
                } else {
                    // No hay gastos, mostramos el mensaje y ocultamos el RecyclerView
                    noGastosMessage.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(null);
                }
            }
        });
        mViewModel.getTotalGastado().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double total) {
                Log.d("total", total.toString());
                totalGastadoTextView.setText("Total gastado: $" + total);
            }
        });
        mViewModel.getEliminarGastoExitoso().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exito) {
                if (exito != null && exito) {
                    Toast.makeText(getContext(), "Gasto eliminado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Recargar los gastos al volver
        if (savedInstanceState == null || getActivity().getIntent().getIntExtra("requestCode", -1) == RESULT_OK) {
            mViewModel.cargarGastos(getContext(), idViaje);
        }
        mViewModel.cargarGastos(getContext(), idViaje);
    }
}
