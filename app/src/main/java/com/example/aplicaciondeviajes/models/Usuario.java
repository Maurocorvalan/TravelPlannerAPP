package com.example.aplicaciondeviajes.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario  implements Serializable {
    @SerializedName("idUsuario")

    public int IdUsuario;
    @SerializedName("nombre")

    public String Nombre;
    @SerializedName("correo")

    public String Correo;

    public String contrasena;
    public Usuario() {
    }

    public Usuario(String nombre, String correo) {
        Nombre = nombre;
        Correo = correo;
    }

    public Usuario(String nombre, String correo, String contrasena) {
        Nombre = nombre;
        Correo = correo;
        this.contrasena = contrasena;
    }

    public Usuario(int idUsuario, String nombre, String correo, String contrasena) {
        IdUsuario = idUsuario;
        Nombre = nombre;
        Correo = correo;
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }



    @Override
    public String toString() {
        return "Usuario{" +
                "IdUsuario=" + IdUsuario +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}

