package com.example.aplicaciondeviajes.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.aplicaciondeviajes.request.ApiClient;
import com.example.aplicaciondeviajes.models.Viaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarViajeViewModel extends ViewModel {

    private final MutableLiveData<String> nombre = new MutableLiveData<>();
    private final MutableLiveData<String> descripcion = new MutableLiveData<>();
    private final MutableLiveData<String> fechaInicio = new MutableLiveData<>();
    private final MutableLiveData<String> fechaFin = new MutableLiveData<>();

    public LiveData<String> getNombre() {
        return nombre;
    }

    public LiveData<String> getDescripcion() {
        return descripcion;
    }

    public LiveData<String> getFechaInicio() {
        return fechaInicio;
    }

    public LiveData<String> getFechaFin() {
        return fechaFin;
    }

    public void actualizarViaje(Context context, Viaje viaje, String nuevoNombre, String nuevaDescripcion, String nuevaFechaInicio, String nuevaFechaFin) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            Date dateInicio = originalFormat.parse(nuevaFechaInicio);
            Date dateFin = originalFormat.parse(nuevaFechaFin);

            if (dateFin.before(dateInicio)) {
                Toast.makeText(context, "La fecha de fin no puede ser anterior a la fecha de inicio.", Toast.LENGTH_SHORT).show();
                return;
            }

            String fechaInicioCorrecta = targetFormat.format(dateInicio);
            String fechaFinCorrecta = targetFormat.format(dateFin);

            viaje.setNombre(nuevoNombre);
            viaje.setDescripcion(nuevaDescripcion);
            viaje.setFechaInicio(fechaInicioCorrecta);
            viaje.setFechaFin(fechaFinCorrecta);

            String token = ApiClient.leerToken(context);
            if (token == null || token.isEmpty()) {
                Toast.makeText(context, "No hay token disponible", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiClient.MisEndPoints apiService = ApiClient.getEndPoints();
            Call<Viaje> call = apiService.updateViaje(token, viaje.getIdViaje(), viaje);
            call.enqueue(new Callback<Viaje>() {
                @Override
                public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Viaje actualizado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Viaje> call, Throwable t) {
                    Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("editarviaje", "Error al formatear las fechas: " + e.toString());
            Toast.makeText(context, "Error al formatear las fechas", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarViaje(Context context, int viajeId, Runnable onComplete) {
        String token = ApiClient.leerToken(context);
        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "No hay token disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.MisEndPoints apiService = ApiClient.getEndPoints();
        Call<Void> call = apiService.deleteViaje(token, viajeId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Viaje eliminado con éxito", Toast.LENGTH_SHORT).show();
                    // Notify completion
                    if (onComplete != null) {
                        onComplete.run();
                    }
                } else {
                    Toast.makeText(context, "Error al eliminar el viaje", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void setViaje(Viaje viaje) {
        if (viaje != null) {
            nombre.setValue(viaje.getNombre());
            descripcion.setValue(viaje.getDescripcion());

            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                Date dateInicio = originalFormat.parse(viaje.getFechaInicio());
                Date dateFin = originalFormat.parse(viaje.getFechaFin());

                String fechaInicioFormateada = targetFormat.format(dateInicio);
                String fechaFinFormateada = targetFormat.format(dateFin);

                fechaInicio.setValue(fechaInicioFormateada);
                fechaFin.setValue(fechaFinFormateada);
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("editarviaje", "Error al formatear las fechas: " + e.toString());
            }
        }
    }
}
