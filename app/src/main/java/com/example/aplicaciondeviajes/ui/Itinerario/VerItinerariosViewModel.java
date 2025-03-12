package com.example.aplicaciondeviajes.ui.Itinerario;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Itinerario;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerItinerariosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Itinerario>> itinerarios = new MutableLiveData<>();


    public VerItinerariosViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Itinerario>> getItinerarios() {
        return itinerarios;
    }



    public void cargarItinerarios(int idViaje, String token) {
        ApiClient.getEndPoints().obtenerItinerarios(token, idViaje).enqueue(new Callback<List<Itinerario>>() {
            @Override
            public void onResponse(Call<List<Itinerario>> call, Response<List<Itinerario>> response) {
                if (response.isSuccessful()) {
                    Log.d("veritinerarios", "exito " + response.body().toString());
                    List<Itinerario> itinerariosResponse = response.body();
                    if (itinerariosResponse != null && !itinerariosResponse.isEmpty()) {
                        itinerarios.setValue(itinerariosResponse);
                    } else {
                        Log.d("veritinerarios", "La lista de itinerarios está vacía.");
                    }
                } else {
                    Log.d("veritinerarios", "error " + response.toString());
                }
            }


            @Override
            public void onFailure(Call<List<Itinerario>> call, Throwable t) {
                Log.d("veritinerarios", "error"+ t.getMessage());
            }
        });
    }

    public void eliminarItinerario(int idViaje, int idItinerario) {
        String token = ApiClient.leerToken(getApplication().getApplicationContext());

        ApiClient.getEndPoints().eliminarItinerario(token, idViaje, idItinerario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    cargarItinerarios(idViaje, token);
                    Toast.makeText(getApplication().getApplicationContext(), "Itinerario eliminado con éxito.", Toast.LENGTH_SHORT).show();

                    Log.d("EliminarItinerario", "Itinerario eliminado con éxito.");
                } else {
                    Log.d("EliminarItinerario", "Error al eliminar itinerario: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("EliminarItinerario", "Error en la solicitud: " + t.getMessage());
            }
        });
    }
}




