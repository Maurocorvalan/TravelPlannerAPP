package com.example.aplicaciondeviajes.ui.slideshow;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Gasto;
import com.example.aplicaciondeviajes.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnadirGastoViewModel extends ViewModel {

    private MutableLiveData<Boolean> gastoCreadoExitoso = new MutableLiveData<>();


    public LiveData<Boolean> getGastoCreadoExitoso() {
        return gastoCreadoExitoso;
    }

    public void crearGasto(Context context, Gasto gasto) {
        ApiClient.getEndPoints().crearGasto(ApiClient.leerToken(context), gasto)
                .enqueue(new Callback<Gasto>() {
                    @Override
                    public void onResponse(Call<Gasto> call, Response<Gasto> response) {
                        if (response.isSuccessful()) {
                            gastoCreadoExitoso.setValue(true);

                            Log.d("anadirgasto", response.toString());
                            Toast.makeText(context, "Gasto creado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            gastoCreadoExitoso.setValue(false);
                            Log.d("anadirgasto", "error "+ gasto.getIdViaje());

                            Log.d("anadirgasto", "error "+response.toString());

                            Toast.makeText(context, "Error al crear gasto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Gasto> call, Throwable t) {
                        gastoCreadoExitoso.setValue(false);
                        Log.d("anadirgasto", "Error: " + t.toString());
                        t.printStackTrace();
                        Toast.makeText(context, "Error en la conexión", Toast.LENGTH_SHORT).show(); }
                });
    }
}
