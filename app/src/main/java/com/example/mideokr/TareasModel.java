package com.example.mideokr;

import java.io.Serializable;

public class TareasModel implements Serializable {

    private String nombre;
    private String ptosHistoria;

    public TareasModel() {
    }

    public TareasModel(String nombre, String ptosHistoria) {
        this.nombre = nombre;
        this.ptosHistoria = ptosHistoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPtosHistoria() {
        return ptosHistoria;
    }

    public void setPtosHistoria(String ptosHistoria) {
        this.ptosHistoria = ptosHistoria;
    }
}
