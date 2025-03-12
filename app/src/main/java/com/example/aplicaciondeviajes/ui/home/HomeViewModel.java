package com.example.aplicaciondeviajes.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Foto;
import com.example.aplicaciondeviajes.models.Viaje;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Viaje>> listaViajes = new MutableLiveData<>();

    public HomeViewModel() {
    }

    public LiveData<List<Viaje>> getViajes() {
        return listaViajes;
    }

    public void obtenerViajes(Context context) {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
        String token = ApiClient.leerToken(context);
        Log.d("HomeViewModel", "Token guardado: " + token);

        Call<List<Viaje>> call = api.obtenerViajes(token);
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                if (response.isSuccessful()) {
                    listaViajes.setValue(response.body());
                } else {
                    Log.d("HomeViewModel", "Error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Log.e("HomeViewModel", "Error en la solicitud", t);
            }
        });
    }

    public void obtenerFoto(Context context, Viaje viaje) {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
        String token = ApiClient.leerToken(context);
        Call<Foto> call = api.obtenerFoto(token, viaje.getIdViaje());

        call.enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    viaje.setFotoUrl(response.body().getUrl());

                    // Crear una copia de la lista y actualizar el MutableLiveData
                    List<Viaje> viajesActualizados = new ArrayList<>(listaViajes.getValue());
                    listaViajes.postValue(viajesActualizados);


                    Log.d("HomeViewModel", "URL de la foto obtenida: " + response.body().getUrl());
                    Log.d("HomeViewModel", "Foto obtenida para viaje: " + viaje.getIdViaje());
                } else {
                    Log.d("HomeViewModel", "No se encontró foto para el viaje: " + viaje.getIdViaje());
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {
                Log.e("HomeViewModel", "Error al obtener foto", t);
            }
        });
    }



}
