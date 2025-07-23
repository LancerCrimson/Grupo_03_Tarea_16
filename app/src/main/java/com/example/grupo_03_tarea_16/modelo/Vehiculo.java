package com.example.grupo_03_tarea_16.modelo;

public class Vehiculo {
    private String numPlaca;
    private String marca;
    private String modelo;
    private String motor;
    private int year;
    private String media;
    private String cedulaP; // FK hacia Propietario

    public Vehiculo() {}

    public Vehiculo(String numPlaca, String marca, String modelo, String motor, int year,
                    String media, String cedulaP) {
        this.numPlaca = numPlaca;
        this.marca = marca;
        this.modelo = modelo;
        this.motor = motor;
        this.year = year;
        this.media = media;
        this.cedulaP = cedulaP;
    }

    public String getNumPlaca() {
        return numPlaca;
    }

    public void setNumPlaca(String numPlaca) {
        this.numPlaca = numPlaca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCedulaP() {
        return cedulaP;
    }

    public void setCedulaP(String cedulaP) {
        this.cedulaP = cedulaP;
    }
}
