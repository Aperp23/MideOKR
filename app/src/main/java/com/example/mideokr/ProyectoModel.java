package com.example.mideokr;

import java.io.Serializable;

public class ProyectoModel implements Serializable {

   private String nombreProyecto;
   private String numSprints;
   private String numSemanas;
   private String numHoras;
   private String numTrabajadores;
   private String ptosHistoria;
   private String keyProyecto;
   private TareasModel tam;
   private TrabajadoresModel trm;

    public ProyectoModel() {
    }

    public ProyectoModel(String nombreProyecto, String numSprints, String numSemanas, String numHoras, String numTrabajadores, String ptosHistoria, String keyProyecto) {
        this.nombreProyecto = nombreProyecto;
        this.numSprints = numSprints;
        this.numSemanas = numSemanas;
        this.numHoras = numHoras;
        this.numTrabajadores = numTrabajadores;
        this.ptosHistoria = ptosHistoria;
        this.keyProyecto = keyProyecto;
    }

    public ProyectoModel(String nombreProyecto, String numSprints, String numSemanas, String numHoras, String numTrabajadores, String ptosHistoria) {
        this.nombreProyecto = nombreProyecto;
        this.numSprints = numSprints;
        this.numSemanas = numSemanas;
        this.numHoras = numHoras;
        this.numTrabajadores = numTrabajadores;
        this.ptosHistoria = ptosHistoria;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getNumSprints() {
        return numSprints;
    }

    public void setNumSprints(String numSprints) {
        this.numSprints = numSprints;
    }

    public String getNumSemanas() {
        return numSemanas;
    }

    public void setNumSemanas(String numSemanas) {
        this.numSemanas = numSemanas;
    }

    public String getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(String numHoras) {
        this.numHoras = numHoras;
    }

    public String getNumTrabajadores() {
        return numTrabajadores;
    }

    public void setNumTrabajadores(String numTrabajadores) {
        this.numTrabajadores = numTrabajadores;
    }

    public String getPtosHistoria() {
        return ptosHistoria;
    }

    public void setPtosHistoria(String ptosHistoria) {
        this.ptosHistoria = ptosHistoria;
    }

    public TareasModel getTam() {
        return tam;
    }

    public void setTam(TareasModel tam) {
        this.tam = tam;
    }

    public TrabajadoresModel getTrm() {
        return trm;
    }

    public void setTrm(TrabajadoresModel trm) {
        this.trm = trm;
    }

    public String getKeyProyecto() {
        return keyProyecto;
    }

    public void setKeyProyecto(String keyProyecto) {
        this.keyProyecto = keyProyecto;
    }
}
