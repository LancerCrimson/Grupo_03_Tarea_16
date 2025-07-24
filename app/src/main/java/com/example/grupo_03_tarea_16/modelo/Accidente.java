package com.example.grupo_03_tarea_16.modelo;

public class Accidente {
    private int idAccidente;
    private String numPlaca;
    private int idAgente;
    private String hora;
    private String fecha;
    private String descripcion;
    private double latitud;
    private double longitud;
    private byte[] media;

    public Accidente() {
    }



    public Accidente(String numPlaca, int idAgente, String hora, String fecha,
                     String descripcion, double latitud, double longitud, byte[] media) {
        this.numPlaca = numPlaca;
        this.idAgente = idAgente;
        this.hora = hora;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.media = media;
    }

    public Accidente(int idAccidente, String numPlaca, int idAgente, String hora, String fecha,
                     String descripcion, double latitud, double longitud, byte[] media) {
        this.idAccidente = idAccidente;
        this.numPlaca = numPlaca;
        this.idAgente = idAgente;
        this.hora = hora;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.media = media;
    }

    public Accidente(int idAccidente, String descripcion) {
        this.idAccidente = idAccidente;
        this.descripcion = descripcion;
    }




    public int getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(int idAccidente) {
        this.idAccidente = idAccidente;
    }

    public String getNumPlaca() {
        return numPlaca;
    }

    public void setNumPlaca(String numPlaca) {
        this.numPlaca = numPlaca;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public byte[] getMedia() {
        return media;
    }

    public void setMedia(byte[] media) {
        this.media = media;
    }
}
