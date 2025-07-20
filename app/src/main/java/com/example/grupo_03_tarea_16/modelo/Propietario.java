package com.example.grupo_03_tarea_16_ejercicio_01.modelos;

public class Propietario {
    private String cedulaP;
    private String nombre;
    private String ciudad;

    public Propietario() {}

    public Propietario(String cedulaP, String nombre, String ciudad) {
        this.cedulaP = cedulaP;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public String getCedulaP() {
        return cedulaP;
    }

    public void setCedulaP(String cedulaP) {
        this.cedulaP = cedulaP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
