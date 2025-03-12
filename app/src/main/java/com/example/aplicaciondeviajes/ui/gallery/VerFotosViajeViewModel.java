package com.example.aplicaciondeviajes.ui.gallery;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Foto;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerFotosViajeViewModel extends ViewModel {

    private final MutableLiveData<List<Foto>> fotos = new MutableLiveData<>();

    public LiveData<List<Foto>> getFotos() {
        return fotos;
    }

    public void obtenerFotos(int idViaje, String token) {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();


        Call<List<Foto>> call = api.obtenerFotos(token, idViaje);
        call.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if (response.isSuccessful()) {
                    List<Foto> fotosRecibidas = response.body();
                    fotos.setValue(fotosRecibidas);
                } else {
                    Log.d("verfotoviaje", "Error en la respuesta: " + response.toString());

                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {
                Log.d("verfotoviaje", "Error de conexión: " + t.getMessage());
            }
        });
    }


    public void eliminarFoto(int fotoId, String token, int viajeId) {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();

        Call<Void> call = api.eliminarFoto(token, fotoId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("verfotoviaje",response.toString());
                    obtenerFotos(viajeId, token);
                }else{
                    Log.d("verfotoviaje", "error"+response.toString());

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("verfotoviaje", "Error de conexión: " + t.getMessage());
            }
        });
    }


}
