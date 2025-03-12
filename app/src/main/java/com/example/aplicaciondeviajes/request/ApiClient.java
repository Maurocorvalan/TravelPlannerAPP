package com.example.aplicaciondeviajes.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aplicaciondeviajes.models.ChangePasswordRequest;
import com.example.aplicaciondeviajes.models.Foto;
import com.example.aplicaciondeviajes.models.Gasto;
import com.example.aplicaciondeviajes.models.Itinerario;
import com.example.aplicaciondeviajes.models.Usuario;
import com.example.aplicaciondeviajes.models.Viaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    private static  String URL = "http://10.0.2.2:5173/";
    private static MisEndPoints mep;
    public static  String urls = "http://10.0.2.2:5173/";

    public static MisEndPoints getEndPoints() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mep = retrofit.create(MisEndPoints.class);
        return mep;
    }

    public interface MisEndPoints {
        @FormUrlEncoded
        @POST("api/auth/login")
        Call<String> login(@Field("Correo") String correo, @Field("Contrasena") String contrasena);
        @POST("/api/auth/register")
        Call<ResponseBody> register(@Body Usuario usuario);
        @GET("api/viajes/viajesuser")
        Call<List<Viaje>> obtenerViajes(@Header("Authorization") String token);


        @POST("api/viajes")
        Call<Viaje> crearViaje(@Header("Authorization") String token, @Body Viaje viaje);
        @GET("api/fotos/{id}/foto")
        Call<Foto> obtenerFoto(@Header("Authorization") String token, @Path("id") int idViaje);

        @PUT("api/viajes/{id}")
        Call<Viaje> updateViaje(@Header("Authorization") String token, @Path("id") int id, @Body Viaje viaje);
        @DELETE("api/viajes/{id}")
        Call<Void> deleteViaje(@Header("Authorization") String token, @Path("id") int viajeId);
        @GET("api/Gastos/{id}")
        Call<List<Gasto>> obtenerGastos(@Header("Authorization") String token, @Path("id") int idViaje);

        @DELETE("api/Gastos/{id}")
        Call<Void> eliminarGasto(@Header("Authorization") String token, @Path("id") int idGasto);

        @POST("api/Gastos")
        Call<Gasto> crearGasto(@Header("Authorization") String token, @Body Gasto gasto);
        @PUT("api/Gastos/{id}")
        Call<Gasto> updateGasto(@Header("Authorization") String token, @Path("id") int idGasto, @Body Gasto gasto);
        @GET("api/viajes/{idViaje}/itinerarios")
        Call<List<Itinerario>> obtenerItinerarios(@Header("Authorization") String token, @Path("idViaje") int idViaje);

        @DELETE("api/viajes/{idViaje}/itinerarios/{id}")
        Call<Void> eliminarItinerario(@Header("Authorization") String token, @Path("idViaje") int idViaje, @Path("id") int id);

        @GET("api/viajes/{idViaje}/itinerarios/{id}")
        Call<Itinerario> obtenerItinerario(@Header("Authorization") String token, @Path("idViaje") int idViaje, @Path("id") int idItinerario);


        @PUT("api/viajes/{idViaje}/itinerarios/{id}")
        Call<Itinerario> actualizarItinerario(@Header("Authorization") String token, @Path("idViaje") int idViaje, @Path("id") int idItinerario, @Body Itinerario itinerario);

        @POST("api/viajes/{idViaje}/itinerarios")
        Call<Itinerario> crearItinerario(@Header("Authorization") String token, @Path("idViaje") int idViaje, @Body Itinerario itinerario);


        @GET("api/fotos/{idViaje}")
        Call<List<Foto>> obtenerFotos(@Header("Authorization") String token, @Path("idViaje") int idViaje);

        @DELETE("api/fotos/{id}")
        Call<Void> eliminarFoto(@Header("Authorization") String token, @Path("id") int fotoId);

        @Multipart
        @POST("api/fotos/{idViaje}")
        Call<Foto> subirFoto(
                @Header("Authorization") String token,
                @Part MultipartBody.Part image,
                @Part("descripcion") RequestBody descripcion,
                @Path("idViaje") int idViaje
        );

        @FormUrlEncoded
        @POST("api/auth/reset-password")
        Call<ResponseBody> resetearContraseña(@Field("email") String email);

        @GET("api/usuario/me")
        Call<Usuario> miPerfil(@Header("Authorization") String token);
        @PUT("api/usuario/update")
        Call<Usuario> actualizarUsuario(@Header("Authorization") String token, @Body Usuario usuario);

        @POST("api/auth/change-password")
        Call<ResponseBody> cambiarContraseña(@Header("Authorization") String token, @Body ChangePasswordRequest request);

    }





    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public static void deslogeo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "");
        editor.apply();
        mep = null;

    }
}
