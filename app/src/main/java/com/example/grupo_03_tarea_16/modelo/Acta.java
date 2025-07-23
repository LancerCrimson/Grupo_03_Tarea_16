package com.example.grupo_03_tarea_16.modelo;

public class Acta {
    private int idActa;
    private int idAccidente;
    private int idAudiencia;
    private String hora;
    private int idZona;
    private int idAgente;
    private String fecha;

    public Acta() {}

    public Acta(int idAccidente, int idAudiencia, String hora, int idZona, int idAgente,
                String fecha) {
        this.idAccidente = idAccidente;
        this.idAudiencia = idAudiencia;
        this.hora = hora;
        this.idZona = idZona;
        this.idAgente = idAgente;
        this.fecha = fecha;
    }

    public Acta(int idActa, int idAccidente, int idAudiencia, String hora, int idZona,
                int idAgente, String fecha) {
        this.idActa = idActa;
        this.idAccidente = idAccidente;
        this.idAudiencia = idAudiencia;
        this.hora = hora;
        this.idZona = idZona;
        this.idAgente = idAgente;
        this.fecha = fecha;
    }

    public int getIdActa() {
        return idActa;
    }

    public void setIdActa(int idActa) {
        this.idActa = idActa;
    }

    public int getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(int idAccidente) {
        this.idAccidente = idAccidente;
    }

    public int getIdAudiencia() {
        return idAudiencia;
    }

    public void setIdAudiencia(int idAudiencia) {
        this.idAudiencia = idAudiencia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
