package com.example.aplicaciondeviajes;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aplicaciondeviajes.request.ApiClient;

import org.jspecify.annotations.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> estadoM;
    private MutableLiveData<String> estadoMensaje = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        estadoM = new MutableLiveData<>();
    }
    public LiveData<Boolean> getEstadoM() {
        if (estadoM == null) {
            estadoM = new MutableLiveData<>();
        }
        return estadoM;
    }
    public LiveData<String> getEstadoMensaje() {
        return estadoMensaje;
    }

    public void logeo(String correo, String contrasena) {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
        Call<String> call = api.login(correo, contrasena);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("salida", response.body());
                    String token = response.body();
                    guardarToken("Bearer " + token);
                    estadoM.setValue(true);
                    Log.d("salida", "inicio exitoso");
                    Intent intent = new Intent(getApplication(), navigation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                } else {
                    estadoM.setValue(false);
                    estadoMensaje.setValue("Usuario o contraseña incorrectos");
                    Log.d("salida", "incorrectsssso");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

                Log.d("salida error", throwable.getMessage());
            }
        });
    }

    private void guardarToken(String token) {
        ApiClient.guardarToken(getApplication(), token);
    }
}
