package com.example.aplicaciondeviajes.models;

public class ChangePasswordRequest {
    private String contrasenaActual;
    private String nuevaContrasena;
    private String confirmarContrasena;

    public ChangePasswordRequest(String contrasenaActual, String nuevaContrasena, String confirmarContrasena) {
        this.contrasenaActual = contrasenaActual;
        this.nuevaContrasena = nuevaContrasena;
        this.confirmarContrasena = confirmarContrasena;
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }

    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }
}
