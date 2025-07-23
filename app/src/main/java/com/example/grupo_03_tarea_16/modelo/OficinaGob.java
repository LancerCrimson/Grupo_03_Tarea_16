package com.example.grupo_03_tarea_16_ejercicio_01.modelos;

public class OficinaGob {
    private int idOficinaGob;
    private double valorVehiculo;
    private String nPoliza;
    private String numPlaca;    // FK hacia Vehiculo
    private String ubicacion;

    public OficinaGob() {}

    public OficinaGob(double valorVehiculo, String nPoliza, String numPlaca, String ubicacion) {
        this.valorVehiculo = valorVehiculo;
        this.nPoliza = nPoliza;
        this.numPlaca = numPlaca;
        this.ubicacion = ubicacion;
    }

    public OficinaGob(int idOficinaGob, double valorVehiculo, String nPoliza, String numPlaca, String ubicacion) {
        this.idOficinaGob = idOficinaGob;
        this.valorVehiculo = valorVehiculo;
        this.nPoliza = nPoliza;
        this.numPlaca = numPlaca;
        this.ubicacion = ubicacion;
    }

    public int getIdOficinaGob() {
        return idOficinaGob;
    }

    public void setIdOficinaGob(int idOficinaGob) {
        this.idOficinaGob = idOficinaGob;
    }

    public double getValorVehiculo() {
        return valorVehiculo;
    }

    public void setValorVehiculo(double valorVehiculo) {
        this.valorVehiculo = valorVehiculo;
    }

    public String getnPoliza() {
        return nPoliza;
    }

    public void setnPoliza(String nPoliza) {
        this.nPoliza = nPoliza;
    }

    public String getNumPlaca() {
        return numPlaca;
    }

    public void setNumPlaca(String numPlaca) {
        this.numPlaca = numPlaca;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
