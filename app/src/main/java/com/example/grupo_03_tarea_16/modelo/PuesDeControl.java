package com.example.grupo_03_tarea_16.modelo;

public class PuesDeControl {
    private int idPuestoControl;
    private int idZona;         // FK hacia Zona
    private String ubicacion;

    public PuesDeControl() {}

    @Override
    public String toString() {
        return ubicacion;
    }

    public String getNombrePuestoControl(){
        return ubicacion;
    }

    public PuesDeControl(int idZona, String ubicacion) {
        this.idZona = idZona;
        this.ubicacion = ubicacion;
    }

    public PuesDeControl(int idPuestoControl, int idZona, String ubicacion) {
        this.idPuestoControl = idPuestoControl;
        this.idZona = idZona;
        this.ubicacion = ubicacion;
    }

    public int getIdPuestoControl() {
        return idPuestoControl;
    }

    public void setIdPuestoControl(int idPuestoControl) {
        this.idPuestoControl = idPuestoControl;
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
