package com.example.aplicaciondeviajes.ui.slideshow;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViajeAdapterGasto extends RecyclerView.Adapter<ViajeAdapterGasto.ViajeViewHolder> {

    private List<Viaje> viajes;
    private Context context;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ViajeAdapterGasto(List<Viaje> viajes, Context context) {
        this.viajes = viajes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viaje2, parent, false);
        return new ViajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {
        Viaje viaje = viajes.get(position);

        holder.textNombre.setText(viaje.getNombre());
        holder.textDescripcion.setText(viaje.getDescripcion());
        holder.textFechaInicio.setText(formatDate(viaje.getFechaInicio()));
        holder.textFechaFin.setText(formatDate(viaje.getFechaFin()));

        holder.btnVerGastos.setOnClickListener(v -> {
            GastoFragment gastoFragment = new GastoFragment();
            Bundle args = new Bundle();
            args.putInt("idViaje", viaje.getIdViaje());
            gastoFragment.setArguments(args);

            NavController navController = Navigation.findNavController(v);

            navController.navigate(R.id.action_nav_slideshow_to_gastos, args);
        });


    }

    @Override
    public int getItemCount() {
        return viajes.size();
    }

    private String formatDate(String dateStr) {
        try {
            Date date = inputDateFormat.parse(dateStr);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr; // Retorna la cadena original si hay un error
        }
    }

    public static class ViajeViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre;
        TextView textDescripcion;
        TextView textFechaInicio;
        TextView textFechaFin;
        Button btnVerGastos;

        public ViajeViewHolder(@NonNull View itemView) {
            super(itemView);
            // Referenciar las vistas del layout item_viaje2
            textNombre = itemView.findViewById(R.id.text_nombre);
            textDescripcion = itemView.findViewById(R.id.text_descripcion);
            textFechaInicio = itemView.findViewById(R.id.text_fecha_inicio);
            textFechaFin = itemView.findViewById(R.id.text_fecha_fin);
            btnVerGastos = itemView.findViewById(R.id.btn_verGastos);
        }
    }

    public void updateViajes(List<Viaje> nuevosViajes) {
        this.viajes = nuevosViajes;
        notifyDataSetChanged();
    }
}
