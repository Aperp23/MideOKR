package com.example.mideokr;

import java.io.Serializable;

public class TareasModel implements Serializable {

    private String nombre;
    private String ptosHistoria;

    private String keyTarea;

    public TareasModel() {
    }

    public TareasModel(String nombre, String ptosHistoria) {
        this.nombre = nombre;
        this.ptosHistoria = ptosHistoria;
    }

    public TareasModel(String nombre, String ptosHistoria, String keyTarea) {
        this.nombre = nombre;
        this.ptosHistoria = ptosHistoria;
        this.keyTarea = keyTarea;
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

    public String getKeyTarea() {
        return keyTarea;
    }

    public void setKeyTarea(String keyTarea) {
        this.keyTarea = keyTarea;
    }
}
