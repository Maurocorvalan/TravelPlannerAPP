package com.example.aplicaciondeviajes.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.MainActivity;
import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class ViajeAdapter extends RecyclerView.Adapter<ViajeAdapter.ViajeViewHolder> {

    private List<Viaje> viajes;
    private Context context;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ViajeAdapter(List<Viaje> viajes, Context context) {
        this.viajes = viajes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viaje, parent, false);
        return new ViajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {
        Viaje viaje = viajes.get(position);
        Log.d("ViajeAdapter", "Mostrando viaje en posición: " + position);

        holder.textNombre.setText(viaje.getNombre());
        holder.textDescripcion.setText(viaje.getDescripcion());
        holder.textFechaInicio.setText(formatDate(viaje.getFechaInicio()));
        holder.textFechaFin.setText(formatDate(viaje.getFechaFin()));

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("viaje", viaje);
            Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment_content_navigation).navigate(R.id.editarViajeFragment, bundle);
        });

        if (viaje.getFotoUrl() != null && !viaje.getFotoUrl().isEmpty()) {
            String fotoUrl = viaje.getFotoUrl();
            if (!fotoUrl.startsWith("http")) {
                fotoUrl = "http://10.0.2.2:5173/" + fotoUrl;
            }
            Picasso.get().load(fotoUrl).into(holder.imageFoto);
            Log.d("ViajeAdapter", "Foto cargada: " + fotoUrl);
        } else {
            holder.imageFoto.setImageResource(R.drawable.placeholder_image); // Imagen por defecto
            Log.d("ViajeAdapter", "No hay foto disponible para este viaje");
        }
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
            return dateStr;
        }
    }

    public static class ViajeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFoto;
        TextView textNombre;
        TextView textDescripcion;
        TextView textFechaInicio;
        TextView textFechaFin;
        Button btnEditar;

        public ViajeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFoto = itemView.findViewById(R.id.image_foto);
            textNombre = itemView.findViewById(R.id.text_nombre);
            textDescripcion = itemView.findViewById(R.id.text_descripcion);
            textFechaInicio = itemView.findViewById(R.id.text_fecha_inicio);
            textFechaFin = itemView.findViewById(R.id.text_fecha_fin);
            btnEditar = itemView.findViewById(R.id.btn_editar_viaje);
        }
    }
    public void updateViajes(List<Viaje> nuevosViajes) {
        this.viajes = nuevosViajes;
        notifyDataSetChanged();
    }

}
