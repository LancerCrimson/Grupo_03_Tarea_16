package com.example.grupo_03_tarea_16.modelo;

public class Agente {
    private int idAgente;
    private String cedulaA;
    private String nombre;
    private int idPuestoControl; // FK hacia PuestoDeControl
    private String rango;

    public Agente() {}

    public Agente(int idgente,int idPuestoControl, String nombre) {
        this.idAgente = idgente;
        this.idPuestoControl = idPuestoControl;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public Agente(String cedulaA, String nombre, int idPuestoControl, String rango) {
        this.cedulaA = cedulaA;
        this.nombre = nombre;
        this.idPuestoControl = idPuestoControl;
        this.rango = rango;
    }

    public Agente(int idAgente, String cedulaA, String nombre, int idPuestoControl, String rango) {
        this.idAgente = idAgente;
        this.cedulaA = cedulaA;
        this.nombre = nombre;
        this.idPuestoControl = idPuestoControl;
        this.rango = rango;
    }

    public Agente(int idAgente, String nombre) {
        this.idAgente = idAgente;
        this.nombre = nombre;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }

    public String getCedulaA() {
        return cedulaA;
    }

    public void setCedulaA(String cedulaA) {
        this.cedulaA = cedulaA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPuestoControl() {
        return idPuestoControl;
    }

    public void setIdPuestoControl(int idPuestoControl) {
        this.idPuestoControl = idPuestoControl;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }
}
