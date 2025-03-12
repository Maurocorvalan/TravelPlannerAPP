package com.example.aplicaciondeviajes.ui.gallery;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Foto;
import com.squareup.picasso.Picasso;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.FotoViewHolder> {

    private List<Foto> fotos;
    private VerFotosViajeViewModel viewModel;
    private int viajeId;
    private String token;

    public FotosAdapter(List<Foto> fotos, VerFotosViajeViewModel viewModel, int viajeId, String token) {
        this.fotos = fotos;
        this.viewModel = viewModel;
        this.viajeId = viajeId;
        this.token = token;
    }

    @NonNull
    @Override
    public FotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foto, parent, false);
        return new FotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FotoViewHolder holder, int position) {
        Foto foto = fotos.get(position);
        String baseUrl = "http://10.0.2.2:5173/";
        String fullImageUrl = baseUrl + foto.getUrl();

        Log.d("verfotoviaje", "Descripción de la foto: " + foto.getDescripcion());

        holder.descripcionFoto.setText(foto.getDescripcion());

        Picasso.get().load(fullImageUrl).into(holder.imageFoto);

        holder.btnEliminarFoto.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setMessage("¿Estás seguro de eliminar esta foto?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        viewModel.eliminarFoto(foto.getIdFoto(), token, viajeId);

                        Toast.makeText(holder.itemView.getContext(), "Foto eliminada", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }



    @Override
    public int getItemCount() {
        return fotos.size();
    }

    public void updateFotos(List<Foto> fotos) {
        this.fotos = fotos;
        notifyDataSetChanged();
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFoto;
        Button btnEliminarFoto;

        TextView descripcionFoto;

        public FotoViewHolder(View itemView) {
            super(itemView);
            imageFoto = itemView.findViewById(R.id.image_foto);
            descripcionFoto = itemView.findViewById(R.id.descripcion_foto);
            btnEliminarFoto = itemView.findViewById(R.id.btn_eliminar_foto);
        }
    }
}
