package com.example.aplicaciondeviajes.models;

import java.io.Serializable;

public class Foto  implements Serializable {
    private int idFoto;
    private int idViaje;
    private String url;
    private String descripcion;

    public Foto() {}

    public Foto(int idFoto, int idViaje, String url, String descripcion) {
        this.idFoto = idFoto;
        this.idViaje = idViaje;
        this.url = url;
        this.descripcion = descripcion;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
