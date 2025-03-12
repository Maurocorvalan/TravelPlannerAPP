package com.example.aplicaciondeviajes.models;

import java.io.Serializable;

public class Gasto implements Serializable {
    public int idGasto;
    public int idViaje;
    public String categoria;
    public double monto;
    public String descripcion;

    public Gasto() {
    }

    public Gasto(String categoria, double monto, String descripcion) {
        this.categoria = categoria;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public Gasto(int idGasto, int idViaje, String categoria, double monto, String descripcion) {
        this.idGasto = idGasto;
        this.idViaje = idViaje;
        this.categoria = categoria;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "idGasto=" + idGasto +
                ", idViaje=" + idViaje +
                ", categoria='" + categoria + '\'' +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}