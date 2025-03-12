package com.example.aplicaciondeviajes.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Gasto;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class GastoAdapter extends RecyclerView.Adapter<GastoAdapter.GastoViewHolder> {

    private List<Gasto> gastos;
    private Context context;
    private GastoViewModel gastoViewModel;

    public GastoAdapter(List<Gasto> gastos, Context context, GastoViewModel gastoViewModel) {
        this.gastos = gastos;
        this.context = context;
        this.gastoViewModel = gastoViewModel;
    }

    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gasto, parent, false);
        return new GastoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        Gasto gasto = gastos.get(position);
        holder.textCategoria.setText(gasto.getCategoria());
        holder.textMonto.setText("$" + gasto.getMonto());
        holder.textDescripcion.setText(gasto.getDescripcion());

        holder.btnEditar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idGasto", gasto.getIdGasto());
            bundle.putString("categoria", gasto.getCategoria());
            bundle.putDouble("monto", gasto.getMonto());
            bundle.putString("descripcion", gasto.getDescripcion());

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_gastos_to_editarGastoFragment, bundle);
        });
        holder.btnEliminar.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(context)
                    .setTitle("Eliminar gasto")
                    .setMessage("¿Estás seguro de eliminar este gasto?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Llamar al ViewModel para eliminar el gasto
                        gastoViewModel.eliminarGasto(context, gasto.getIdGasto());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }

    public static class GastoViewHolder extends RecyclerView.ViewHolder {
        TextView textCategoria;
        TextView textMonto;
        TextView textDescripcion;
        Button btnEliminar;
        Button btnEditar;

        public GastoViewHolder(View itemView) {
            super(itemView);
            textCategoria = itemView.findViewById(R.id.text_categoria);
            textMonto = itemView.findViewById(R.id.text_monto);
            textDescripcion = itemView.findViewById(R.id.text_descripcion);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
            btnEditar = itemView.findViewById(R.id.btn_editar);
        }
    }
}
