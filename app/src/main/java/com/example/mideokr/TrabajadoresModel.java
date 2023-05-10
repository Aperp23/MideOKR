package com.example.mideokr;

public class TrabajadoresModel {

    private String tipo;
    private String wip;

    private String keyTrabajadores;

    public TrabajadoresModel() {
    }

    public TrabajadoresModel(String tipo, String wip) {
        this.tipo = tipo;
        this.wip = wip;
    }

    public TrabajadoresModel(String tipo, String wip, String keyTrabajadores) {
        this.tipo = tipo;
        this.wip = wip;
        this.keyTrabajadores = keyTrabajadores;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getWip() {
        return wip;
    }

    public void setWip(String wip) {
        this.wip = wip;
    }

    public String getKeyTrabajadores() {
        return keyTrabajadores;
    }

    public void setKeyTrabajadores(String keyTrabajadores) {
        this.keyTrabajadores = keyTrabajadores;
    }
}
