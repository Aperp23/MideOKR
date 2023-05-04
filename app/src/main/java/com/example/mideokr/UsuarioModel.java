package com.example.mideokr;

import java.io.Serializable;

public class UsuarioModel implements Serializable {

    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String dni;

    private ProyectoModel pm;

    public UsuarioModel() {
    }

    public UsuarioModel(String dni) {
        this.dni = dni;
    }

    public UsuarioModel(String nombre, String apellidos, String email, String dni) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.dni = dni;
    }

    public UsuarioModel(String nombre, String apellidos, String email, String dni, ProyectoModel pm) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.dni = dni;
        this.pm = pm;
    }

    public UsuarioModel(ProyectoModel pm) {
        this.pm = pm;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public ProyectoModel getPm() {
        return pm;
    }

    public void setPm(ProyectoModel pm) {
        this.pm = pm;
    }
}
