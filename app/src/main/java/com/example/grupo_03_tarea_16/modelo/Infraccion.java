package com.example.grupo_03_tarea_16.modelo;

public class Infraccion {
    private int idInfraccion;
    private int idAgente;      // FK a Agente
    private String numPlaca;   // FK a Vehiculo
    private double valorMulta;
    private String fecha;
    private int idNorma;       // FK a NormasDeT
    private String hora;

    public Infraccion() {}

    public Infraccion(int idAgente, String numPlaca, double valorMulta, String fecha,
                      int idNorma, String hora) {
        this.idAgente = idAgente;
        this.numPlaca = numPlaca;
        this.valorMulta = valorMulta;
        this.fecha = fecha;
        this.idNorma = idNorma;
        this.hora = hora;
    }

    public Infraccion(int idInfraccion, int idAgente, String numPlaca, double valorMulta,
                      String fecha, int idNorma, String hora) {
        this.idInfraccion = idInfraccion;
        this.idAgente = idAgente;
        this.numPlaca = numPlaca;
        this.valorMulta = valorMulta;
        this.fecha = fecha;
        this.idNorma = idNorma;
        this.hora = hora;
    }

    public int getIdInfraccion() {
        return idInfraccion;
    }

    public void setIdInfraccion(int idInfraccion) {
        this.idInfraccion = idInfraccion;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }

    public String getNumPlaca() {
        return numPlaca;
    }

    public void setNumPlaca(String numPlaca) {
        this.numPlaca = numPlaca;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdNorma() {
        return idNorma;
    }

    public void setIdNorma(int idNorma) {
        this.idNorma = idNorma;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
