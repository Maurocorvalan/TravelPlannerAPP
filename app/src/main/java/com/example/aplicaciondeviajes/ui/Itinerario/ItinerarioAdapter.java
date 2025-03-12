package com.example.aplicaciondeviajes.ui.Itinerario;

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
import com.example.aplicaciondeviajes.models.Viaje;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ItinerarioAdapter extends RecyclerView.Adapter<ItinerarioAdapter.ViewHolder> {

    private List<Viaje> viajes;
    private Context context;

    public ItinerarioAdapter(List<Viaje> viajes, Context context) {
        this.viajes = viajes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_viaje3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Viaje viaje = viajes.get(position);

        holder.nombre.setText(viaje.getNombre());
        holder.descripcion.setText(viaje.getDescripcion());

        String fechaInicioFormateada = formatearFecha(viaje.getFechaInicio());
        String fechaFinFormateada = formatearFecha(viaje.getFechaFin());

        holder.fechaInicio.setText("Inicio: " + fechaInicioFormateada);
        holder.fechaFin.setText("Fin: " + fechaFinFormateada);

        holder.verItinerario.setOnClickListener(v -> {
            VerItinerariosFragment verItinerariosFragment = new VerItinerariosFragment();

            Bundle args = new Bundle();
            args.putInt("idViaje", viaje.getIdViaje()); // Asegúrate de que Viaje tenga el método getIdViaje()
            verItinerariosFragment.setArguments(args);

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_Itinerario_to_verItinerario, args);
        });
    }

    @Override
    public int getItemCount() {
        return viajes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, fechaInicio, fechaFin;
        Button verItinerario;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreViaje);
            descripcion = itemView.findViewById(R.id.descripcionViaje);
            fechaInicio = itemView.findViewById(R.id.fechaInicioViaje);
            fechaFin = itemView.findViewById(R.id.fechaFinViaje);
            verItinerario = itemView.findViewById(R.id.btnVerItinerario);
        }
    }

    private String formatearFecha(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return "Fecha no disponible";
        }

        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Ajusta si el formato original es diferente
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            Date date = formatoEntrada.parse(fecha);
            return formatoSalida.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Formato incorrecto";
        }
    }
}