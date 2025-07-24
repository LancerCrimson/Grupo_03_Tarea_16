package com.example.grupo_03_tarea_16.modelo;

public class NormasDeT {
    private int idNorma;
    private String numNorma;
    private String descripcion;

    public NormasDeT() {}

    @Override
    public String toString() {
        return this.numNorma;
    }

    public NormasDeT(int idNorma, String numNorma) {
        this.idNorma = idNorma;
        this.numNorma = numNorma;
    }

    public NormasDeT(String numNorma, String descripcion) {
        this.numNorma = numNorma;
        this.descripcion = descripcion;
    }

    public NormasDeT(int idNorma, String numNorma, String descripcion) {
        this.idNorma = idNorma;
        this.numNorma = numNorma;
        this.descripcion = descripcion;
    }

    public int getIdNorma() {
        return idNorma;
    }

    public void setIdNorma(int idNorma) {
        this.idNorma = idNorma;
    }

    public String getNumNorma() {
        return numNorma;
    }

    public void setNumNorma(String numNorma) {
        this.numNorma = numNorma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
