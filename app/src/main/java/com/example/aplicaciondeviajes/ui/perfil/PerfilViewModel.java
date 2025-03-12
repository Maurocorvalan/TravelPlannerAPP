package com.example.aplicaciondeviajes.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicaciondeviajes.models.ChangePasswordRequest;
import com.example.aplicaciondeviajes.models.Usuario;
import com.example.aplicaciondeviajes.request.ApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Usuario> mUsuario;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuario> getMUsuario() {
        if (mUsuario == null) {
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public void llenarPerfil() {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
        String token = ApiClient.leerToken(getApplication());
        Call<Usuario> call = api.miPerfil(token);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuario = response.body();
                    if (usuario != null) {
                        mUsuario.postValue(usuario);
                        Log.d("usuario", usuario.toString());  // Verifica el valor aquí
                    } else {
                        Log.d("usuario", "Respuesta vacía");
                        Toast.makeText(getApplication(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("usuario", "Error en la respuesta: " + response.message());
                    Toast.makeText(getApplication(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }



   public void editarPerfil(Usuario u) {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
      String token = ApiClient.leerToken(getApplication());
    Call<Usuario> call = api.actualizarUsuario(token, u);
     call.enqueue(new Callback<Usuario>() {
          @Override
          public void onResponse(Call<Usuario> call, Response<Usuario> response) {
              if (response.isSuccessful()) {
                  Log.d("carajo", "response");
                  mUsuario.postValue(response.body());
                  Toast.makeText(getApplication(), "Usuario Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                  llenarPerfil();
                } else {
                    Log.d("carajo", "Error: " + response.message());
                    Toast.makeText(getApplication(), "No se pudo actualizar el usauurio", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Log.d("carajo", "response");
                Toast.makeText(getApplication(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });


   }
    public void cambiarContraseña(String contraseñaActual, String nuevaContraseña, String confirmarContraseña) {
        if (!nuevaContraseña.equals(confirmarContraseña)) {
            Toast.makeText(getApplication(), "La nueva contraseña y la confirmación no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();

        ChangePasswordRequest request = new ChangePasswordRequest(contraseñaActual, nuevaContraseña, confirmarContraseña);
        Call<ResponseBody> call = api.cambiarContraseña(token, request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("carajo", response.toString());
                    Toast.makeText(getApplication(), "Error al cambiar la contraseña: " , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }






}
