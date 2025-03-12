package com.example.aplicaciondeviajes.ui.Itinerario;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.aplicaciondeviajes.R;
import com.example.aplicaciondeviajes.models.Itinerario;
import com.example.aplicaciondeviajes.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnadirItinerarioViewModel extends AndroidViewModel {

    private Context context;

    public AnadirItinerarioViewModel(Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void guardarItinerario(int idViaje, String actividad, String ubicacion, View view) {
        String token = ApiClient.leerToken(context);
        if (token == null) {
            Log.e("guardarItinerario", "Token no encontrado.");
            return;
        }

        Itinerario itinerario = new Itinerario();
        itinerario.setActividad(actividad);
        itinerario.setUbicacion(ubicacion);

        Call<Itinerario> call = ApiClient.getEndPoints().crearItinerario(token, idViaje, itinerario);
        call.enqueue(new Callback<Itinerario>() {
            @Override
            public void onResponse(Call<Itinerario> call, Response<Itinerario> response) {

                if (response.isSuccessful()) {
                    NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                    Toast.makeText(context, "Itinerario añadido correctamente.", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("guardarItinerario", "Error al agregar: " + response.toString());
                    Toast.makeText(context, "Error al añadir itinerario.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Itinerario> call, Throwable t) {
                Log.e("guardarItinerario", "Error de conexión: " + t.getMessage());
                Toast.makeText(context, "Error de conexión.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
