package com.example.aplicaciondeviajes.ui.RegistroUsuario;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aplicaciondeviajes.models.Usuario;
import com.example.aplicaciondeviajes.request.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegistroUsuarioViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensaje;

    public ActivityRegistroUsuarioViewModel(@NonNull Application application) {
        super(application);
        mensaje = new MutableLiveData<>();
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void registrarUsuario(String nombre, String correo, String contrasena) {
        Usuario usuario = new Usuario(nombre, correo, contrasena);

        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
        Call<ResponseBody> call = api.register(usuario);

        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String respuesta = response.body().string();
                        Log.d("registro", response.toString());

                        JSONObject jsonObject = new JSONObject(respuesta);
                        String mensajeRespuesta = jsonObject.getString("mensaje");

                        mensaje.postValue(mensajeRespuesta);
                    } catch (IOException | JSONException e) {
                        Log.d("registro", response.toString());

                        mensaje.postValue("Error al registrar el usuario");
                    }
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.d("registro", response.toString());
                        mensaje.postValue("Error: " + error);
                    } catch (IOException e) {
                        Log.d("registro", response.toString());

                        mensaje.postValue("Error en la solicitud");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mensaje.postValue("Error en la solicitud: " + t.getMessage());
            }
        });
    }

}
