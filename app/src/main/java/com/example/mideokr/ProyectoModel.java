package com.example.mideokr;

public class ProyectoModel {

   private String nombreProyecto;
   private String numSprints;
   private String numSemanas;
   private String numHoras;
   private String numTrabajadores;
   private String ptosHistoria;

    public ProyectoModel() {
    }

    public ProyectoModel(String nombreProyecto, String numSprints, String numSemanas, String numHoras, String numTrabajadores, String ptosHistoria) {
        this.nombreProyecto = nombreProyecto;
        this.numSprints = numSprints;
        this.numSemanas = numSemanas;
        this.numHoras = numHoras;
        this.numTrabajadores = numTrabajadores;
        this.ptosHistoria = ptosHistoria;
    }
}
