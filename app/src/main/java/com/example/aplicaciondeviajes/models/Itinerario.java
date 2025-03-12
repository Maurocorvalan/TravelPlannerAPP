package com.example.aplicaciondeviajes.models;

import java.io.Serializable;

public class Itinerario implements Serializable {
    public int idItinerario;
    public int idViaje;

    public String actividad;
    public String ubicacion;

    public Itinerario() {
    }

    public Itinerario(String actividad, String ubicacion) {
        this.actividad = actividad;
        this.ubicacion = ubicacion;
    }

    public Itinerario(int idItinerario, int idViaje, String actividad, String ubicacion) {
        this.idItinerario = idItinerario;
        this.idViaje = idViaje;
        this.actividad = actividad;
        this.ubicacion = ubicacion;
    }

    public int getIdItinerario() {
        return idItinerario;
    }

    public void setIdItinerario(int idItinerario) {
        this.idItinerario = idItinerario;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }


    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Itinerario{" +
                "idItinerario=" + idItinerario +
                ", idViaje=" + idViaje +
                ", Actividad='" + actividad + '\'' +
                ", Ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
