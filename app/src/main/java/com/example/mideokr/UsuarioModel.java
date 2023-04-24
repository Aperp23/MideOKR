package com.example.mideokr;

public class UsuarioModel {

    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String dni;

    public UsuarioModel() {
    }

    public UsuarioModel(String dni) {
        this.dni = dni;
    }

    public UsuarioModel(String nombre, String apellidos, String email, String password, String dni) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.dni = dni;
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
}
