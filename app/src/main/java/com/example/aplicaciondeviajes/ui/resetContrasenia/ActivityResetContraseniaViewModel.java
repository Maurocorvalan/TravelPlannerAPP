package com.example.aplicaciondeviajes.ui.resetContrasenia;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.aplicaciondeviajes.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import okhttp3.ResponseBody;

public class ActivityResetContraseniaViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensajeRespuesta;

    public ActivityResetContraseniaViewModel(@NonNull Application application) {
        super(application);
        mensajeRespuesta = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void resetearContraseña(String email) {
        ApiClient.MisEndPoints apiService = ApiClient.getEndPoints();
        Call<ResponseBody> call = apiService.resetearContraseña(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseString = response.body().string();
                        Toast.makeText(getApplication(), "Contraseña cambiada con Exito, revisa tu bandeja de entrada.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mensajeRespuesta.setValue("Error al leer la respuesta.");
                    }
                } else {
                    Toast.makeText(getApplication(), "Correo no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ResetContraseña", "Error: " + t.getMessage());
                Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
                mensajeRespuesta.setValue("Error al restablecer la contraseña. Por favor, inténtelo de nuevo más tarde.");
            }
        });
    }
}
