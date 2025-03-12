package com.example.aplicaciondeviajes.ui.slideshow;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Viaje;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<List<Viaje>> viajes;
    private final MutableLiveData<String> errorMessage;

    public SlideshowViewModel() {
        viajes = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<List<Viaje>> getViajes() {
        return viajes;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void cargarViajes(Context context) {
        String token = ApiClient.leerToken(context);
        ApiClient.getEndPoints().obtenerViajes( token).enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                if (response.isSuccessful()) {
                    viajes.setValue(response.body());
                    Log.d("SlideshowViewModel", "Viajes cargados: " + response.body().size());
                } else {
                    errorMessage.setValue("Error al cargar los viajes: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                errorMessage.setValue("Error de conexión: " + t.getMessage());
                Log.e("SlideshowViewModel", "Error de conexión: " + t.getMessage());
            }
        });
    }
}
