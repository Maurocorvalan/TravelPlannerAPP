package com.example.aplicaciondeviajes.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Viaje;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Viaje> viajes;
    private Context context;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public GalleryAdapter(List<Viaje> viajes, Context context) {
        this.viajes = viajes;
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viaje, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Viaje viaje = viajes.get(position);

        holder.textNombre.setText(viaje.getNombre());
        holder.textDescripcion.setText(viaje.getDescripcion());
        holder.textFechaInicio.setText(formatDate(viaje.getFechaInicio()));
        holder.textFechaFin.setText(formatDate(viaje.getFechaFin()));

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("viajeId", viaje.getIdViaje());
            Navigation.findNavController(v).navigate(R.id.verFotosViajeFragment, bundle);
        });


        if (viaje.getFotoUrl() != null && !viaje.getFotoUrl().isEmpty()) {
            String fotoUrl = viaje.getFotoUrl();
            if (!fotoUrl.startsWith("http")) {
                fotoUrl = "http://10.0.2.2:5173/" + fotoUrl;
            }
            Picasso.get().load(fotoUrl).into(holder.imageFoto);
        } else {
            holder.imageFoto.setImageResource(R.drawable.placeholder_image);
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

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFoto;
        TextView textNombre;
        TextView textDescripcion;
        TextView textFechaInicio;
        TextView textFechaFin;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFoto = itemView.findViewById(R.id.image_foto);
            textNombre = itemView.findViewById(R.id.text_nombre);
            textDescripcion = itemView.findViewById(R.id.text_descripcion);
            textFechaInicio = itemView.findViewById(R.id.text_fecha_inicio);
            textFechaFin = itemView.findViewById(R.id.text_fecha_fin);
        }
    }
    public void updateViajes(List<Viaje> nuevosViajes) {
        this.viajes = nuevosViajes;
        notifyDataSetChanged();
    }

}
