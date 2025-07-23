package com.example.grupo_03_tarea_16.modelo;

public class Audiencia {
    private int idAudiencia;
    private String lugar;
    private String fecha;
    private String hora;

    public Audiencia() {}

    public Audiencia(String lugar, String fecha, String hora) {
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Audiencia(int idAudiencia, String lugar, String fecha, String hora) {
        this.idAudiencia = idAudiencia;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdAudiencia() {
        return idAudiencia;
    }

    public void setIdAudiencia(int idAudiencia) {
        this.idAudiencia = idAudiencia;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
