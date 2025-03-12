package com.example.aplicaciondeviajes.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Viaje;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarViajeViewModel extends ViewModel {
    private final MutableLiveData<Boolean> viajeCreado = new MutableLiveData<>();

    public LiveData<Boolean> getViajeCreado() {
        return viajeCreado;
    }
    public void agregarViaje(Viaje viaje, Context context) {
        String fechaInicioFormateada = formatDate(viaje.getFechaInicio());
        String fechaFinFormateada = formatDate(viaje.getFechaFin());

        if (fechaInicioFormateada == null || fechaFinFormateada == null) {
            Log.d("AgregarViajeViewModel", "Las fechas son inválidas.");
            return;
        }
        if (isFechaFinAnterior(fechaInicioFormateada, fechaFinFormateada)) {
            Log.d("AgregarViajeViewModel", "La fecha de fin no puede ser anterior a la fecha de inicio.");
            Toast.makeText(context, "La fecha de fin no puede ser anterior a la fecha de inicio.", Toast.LENGTH_SHORT).show();
            return;
        }
        viaje.setFechaInicio(fechaInicioFormateada);
        viaje.setFechaFin(fechaFinFormateada);

        String token = ApiClient.leerToken(context);

        if (token != null) {
            Call<Viaje> call = ApiClient.getEndPoints().crearViaje( token, viaje);
            call.enqueue(new Callback<Viaje>() {
                @Override
                public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                    if (response.isSuccessful()) {
                        viajeCreado.setValue(true);
                        Toast.makeText(context, "Viaje creado con exito", Toast.LENGTH_SHORT).show();
                        Log.d("AgregarViajeViewModel", "Viaje agregado con éxito: " + response.body());
                    } else {
                        Log.d("AgregarViajeViewModel", "Error al agregar el viaje: " + response.message());
                        viajeCreado.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<Viaje> call, Throwable t) {
                    Log.e("AgregarViajeViewModel", "Error de red: " + t.getMessage());
                }
            });
        } else {
            Log.d("AgregarViajeViewModel", "Token de autenticación no encontrado.");
        }
    }

    private String formatDate(String fecha) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(fecha);
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e("AgregarViajeViewModel", "Error al formatear la fecha: " + e.getMessage());
            return null;
        }
    }
    private boolean isFechaFinAnterior(String fechaInicio, String fechaFin) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicioDate = format.parse(fechaInicio);
            Date fechaFinDate = format.parse(fechaFin);
            return fechaFinDate.before(fechaInicioDate);
        } catch (ParseException e) {
            Log.e("AgregarViajeViewModel", "Error al comparar las fechas: " + e.getMessage());
            return false;
        }
    }
}
