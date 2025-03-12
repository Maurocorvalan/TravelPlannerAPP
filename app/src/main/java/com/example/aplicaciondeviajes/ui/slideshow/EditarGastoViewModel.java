package com.example.aplicaciondeviajes.ui.slideshow;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Gasto;
import com.example.aplicaciondeviajes.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarGastoViewModel extends ViewModel {
    private ApiClient.MisEndPoints apiService;

    public EditarGastoViewModel() {
        apiService = ApiClient.getEndPoints();
    }

    public void actualizarGasto(Context context, Gasto gasto) {
        String token = ApiClient.leerToken(context);
        Log.d("editargasto", "Categoria antes de la actualización: " + gasto.getCategoria());

        if (token != null) {
            apiService.updateGasto(token, gasto.getIdGasto(), gasto)
                    .enqueue(new Callback<Gasto>() {
                        @Override
                        public void onResponse(Call<Gasto> call, Response<Gasto> response) {
                            if (response.isSuccessful()) {
                                // Actualización exitosa
                                Log.d("editargasto", response.toString());
                                Log.d("editargasto",gasto.getCategoria());


                                Toast.makeText(context, "Gasto actualizado", Toast.LENGTH_SHORT).show();
                            } else {
                                // Error al actualizar
                                Log.d("editargasto", response.toString());
                                Toast.makeText(context, "Error al actualizar el gasto", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Gasto> call, Throwable t) {
                            // Error de red o de otro tipo
                            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
