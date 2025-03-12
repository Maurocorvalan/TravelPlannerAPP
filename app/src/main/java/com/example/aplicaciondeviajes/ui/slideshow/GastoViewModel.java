package com.example.aplicaciondeviajes.ui.slideshow;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.Gasto;
import com.example.aplicaciondeviajes.request.ApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GastoViewModel extends ViewModel {

    private final MutableLiveData<List<Gasto>> gastos;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<Boolean> eliminarGastoExitoso;
    private final MutableLiveData<Double> totalGastado;


    public GastoViewModel() {
        gastos = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        eliminarGastoExitoso = new MutableLiveData<>();
        totalGastado = new MutableLiveData<>(0.0);
    }

    public LiveData<List<Gasto>> getGastos() {
        return gastos;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getEliminarGastoExitoso() {
        return eliminarGastoExitoso;
    }
    public LiveData<Double> getTotalGastado() {
        return totalGastado;
    }

    public void cargarGastos(Context context, int idViaje) {
        String token = ApiClient.leerToken(context);

        ApiClient.getEndPoints().obtenerGastos(token, idViaje).enqueue(new Callback<List<Gasto>>() {
            @Override
            public void onResponse(Call<List<Gasto>> call, Response<List<Gasto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Gasto> gastosList = response.body();
                    calcularTotal(gastosList);

                    gastos.setValue(gastosList);
                } else {
                    Log.e("Gastos", "No se encontraron gastos o respuesta incorrecta.");

                    errorMessage.setValue("No se encontraron gastos.");
                }
            }

            @Override
            public void onFailure(Call<List<Gasto>> call, Throwable t) {
                errorMessage.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void eliminarGasto(Context context, int idGasto) {
        List<Gasto> currentGastos = gastos.getValue();

        String token = ApiClient.leerToken(context);

        ApiClient.getEndPoints().eliminarGasto(token, idGasto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    List<Gasto> gastosList = gastos.getValue();
                    if (gastosList != null) {
                        gastosList.removeIf(gasto -> gasto.getIdGasto() == idGasto);
                        gastos.setValue(gastosList);
                        calcularTotal(gastosList);

                        eliminarGastoExitoso.setValue(true);
                    }
                } else {
                    errorMessage.setValue("Error al eliminar el gasto.");
                    eliminarGastoExitoso.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorMessage.setValue("Error de conexión: " + t.getMessage());
                eliminarGastoExitoso.setValue(false);
            }
        });
    }

    private void calcularTotal(List<Gasto> gastosList) {
        double total = 0;
        if (gastosList != null) {
            for (Gasto gasto : gastosList) {
                total += gasto.getMonto();
            }
        }
        totalGastado.setValue(total);
    }

}