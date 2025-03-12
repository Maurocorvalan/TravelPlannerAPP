package com.example.aplicaciondeviajes.ui.Itinerario;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.aplicaciondeviajes.models.Itinerario;
import com.example.aplicaciondeviajes.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarItinerarioViewModel extends AndroidViewModel {

    private MutableLiveData<Itinerario> itinerarioLiveData;
    private Context context;

    public EditarItinerarioViewModel(Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.itinerarioLiveData = new MutableLiveData<>();
    }

    public LiveData<Itinerario> getItinerario() {
        return itinerarioLiveData;
    }

    public void cargarItinerario(int idViaje, int idItinerario) {
        String token = ApiClient.leerToken(context);

        Call<Itinerario> call = ApiClient.getEndPoints().obtenerItinerario(token, idViaje, idItinerario);
        call.enqueue(new Callback<Itinerario>() {
            @Override
            public void onResponse(Call<Itinerario> call, Response<Itinerario> response) {
                if (response.isSuccessful()) {
                    Log.d("editaritinerario", response.body().toString());
                    itinerarioLiveData.setValue(response.body());
                }else{
                    Log.d("editaritinerario", response.toString());

                }
            }

            @Override
            public void onFailure(Call<Itinerario> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void guardarItinerario(int idViaje, int idItinerario, String actividad, String ubicacion, View view) {
        String token = ApiClient.leerToken(context);

        Itinerario itinerario = new Itinerario();
        itinerario.setActividad(actividad);
        itinerario.setUbicacion(ubicacion);

        Call<Itinerario> call = ApiClient.getEndPoints().actualizarItinerario(token, idViaje, idItinerario, itinerario);
        call.enqueue(new Callback<Itinerario>() {
            @Override
            public void onResponse(Call<Itinerario> call, Response<Itinerario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Itinerario actualizado correctamente.", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                    Log.d("guardarItinerario", "Itinerario actualizado correctamente.");
                } else {
                    Log.e("guardarItinerario", "Error al actualizar: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Itinerario> call, Throwable t) {
                Log.e("guardarItinerario", "Fallo en la petición", t);
            }
        });
    }

}
