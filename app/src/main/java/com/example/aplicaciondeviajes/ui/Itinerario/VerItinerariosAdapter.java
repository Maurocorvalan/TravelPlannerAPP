package com.example.aplicaciondeviajes.ui.Itinerario;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Itinerario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class VerItinerariosAdapter extends RecyclerView.Adapter<VerItinerariosAdapter.ItinerarioViewHolder> {

    private List<Itinerario> itinerarios;
    private Context context;
    private VerItinerariosViewModel mViewModel;


    public VerItinerariosAdapter(Context context, VerItinerariosViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }

    public void setItinerarios(List<Itinerario> itinerarios) {
        this.itinerarios = itinerarios;
        notifyDataSetChanged();
    }

    @Override
    public ItinerarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_itinerario, parent, false);
        return new ItinerarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItinerarioViewHolder holder, int position) {
        Itinerario itinerario = itinerarios.get(position);
        holder.actividadTextView.setText(itinerario.getActividad());
        holder.ubicacionTextView.setText(itinerario.getUbicacion());
         context = holder.itemView.getContext();
        holder.btnEliminar.setOnClickListener(v -> {
            if (context != null) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Eliminar itinerario")
                        .setMessage("¿Estás seguro de que quieres eliminar este itinerario?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            mViewModel.eliminarItinerario(itinerario.getIdViaje(), itinerario.getIdItinerario());
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                Log.e("VerItinerariosAdapter", "Contexto es nulo");
            }
        });
        holder.btnEditarItinerario.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idViaje", itinerario.getIdViaje());
            bundle.putInt("idItinerario", itinerario.getIdItinerario());

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_verItinerario_to_editarItinerario, bundle);
        });

    }


    @Override
    public int getItemCount() {
        return itinerarios != null ? itinerarios.size() : 0;
    }

    public static class ItinerarioViewHolder extends RecyclerView.ViewHolder {

        public TextView actividadTextView;
        public TextView ubicacionTextView;
        public Button btnEliminar;

        public Button btnEditarItinerario;

        public ItinerarioViewHolder(View itemView) {
            super(itemView);
            actividadTextView = itemView.findViewById(R.id.text_actividad);
            ubicacionTextView = itemView.findViewById(R.id.text_ubicacion);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_itinerario);
            btnEditarItinerario = itemView.findViewById(R.id.btn_editar_itinerario);
        }
    }

}
