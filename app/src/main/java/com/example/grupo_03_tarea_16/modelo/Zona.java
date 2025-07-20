package com.example.grupo_03_tarea_16_ejercicio_01.modelos;

public class Zona {
    private int idZona;
    private String ubicacion;

    public Zona() {}

    public Zona(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Zona(int idZona, String ubicacion) {
        this.idZona = idZona;
        this.ubicacion = ubicacion;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
