package com.example.aplicaciondeviajes.ui.gallery;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.aplicaciondeviajes.request.ApiClient;
import com.example.aplicaciondeviajes.models.Foto;

import java.io.File;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AnadirFotoViajeViewModel extends ViewModel {

    public void subirFoto(Uri imageUri, String descripcion, String token, Context context, int idViaje, View view) {
        try {
            Log.d("verfotoviaje", String.valueOf(idViaje));
            ContentResolver contentResolver = context.getContentResolver();

            InputStream inputStream = contentResolver.openInputStream(imageUri);
            File imageFile = new File(context.getCacheDir(), "temp_image.jpg"); // Use context to get cacheDir

            // URI a un archivo temporal
            FileUtil.copyInputStreamToFile(inputStream, imageFile);

            // RequestBody para la imagen
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);

            // ultipartBody.Part para enviar la imagen
            MultipartBody.Part body = MultipartBody.Part.createFormData("archivo", imageFile.getName(), requestFile);

            // RequestBody para la descripción
            RequestBody descripcionBody = RequestBody.create(MediaType.parse("text/plain"), descripcion);

            ApiClient.MisEndPoints api = ApiClient.getEndPoints();

            api.subirFoto(token, body, descripcionBody, idViaje).enqueue(new Callback<Foto>() {
                @Override
                public void onResponse(Call<Foto> call, Response<Foto> response) {
                    if (response.isSuccessful()) {
                        Log.d("verfotoviaje", "bien " + response.toString());
                        NavController navController = Navigation.findNavController(view);
                        navController.popBackStack();
                        Toast.makeText(context, "Foto subida exitosamente", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("verfotoviaje", response.toString());
                        Toast.makeText(context, "Error al subir la foto", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Foto> call, Throwable t) {
                    Log.d("verfotoviaje", "throwable " + t.toString());
                    Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("verfotoviaje", "Error al procesar la imagen: " + e.toString());
            Toast.makeText(context, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
        }
    }
}
