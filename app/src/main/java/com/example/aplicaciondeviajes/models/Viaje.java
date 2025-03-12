package com.example.aplicaciondeviajes.models;

import java.io.Serializable;

public class Viaje implements Serializable {
    public int idViaje;
    public int idUsuario;
    public String nombre;
    public String descripcion;
    public String fechaInicio;
    public String fechaFin;

    public String fotoUrl;

    public double totalGastado;

    public Viaje() {
    }

    public Viaje(String nombre, String descripcion, String fechaInicio, String fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Viaje(int idViaje, int idUsuario, String nombre, String descripcion, String fechaInicio, String fechaFin, String fotoUrl) {
        this.idViaje = idViaje;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fotoUrl = fotoUrl;
    }

    public Viaje(int idViaje, int idUsuario, String nombre, String descripcion, String fechaInicio, String fechaFin, String fotoUrl, double totalGastado) {
        this.idViaje = idViaje;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fotoUrl = fotoUrl;
        this.totalGastado = totalGastado;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(double totalGastado) {
        this.totalGastado = totalGastado;
    }
}