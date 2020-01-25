package com.sergio.helpelders.login_register_forgot.register;

public class Usuario {
    /**
     * Atributos
     */
    private String nombre, apellidos, fechaNac, sexo, rolUsuario, direccion, localidad, codPost, email;

    /**
     *
     * Constructor
     */
    public Usuario() {}

    public Usuario(String nombre, String apellidos, String fechaNac, String sexo,
                   String rolUsuario, String direccion, String localidad, String codPost, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.rolUsuario = rolUsuario;
        this.direccion = direccion;
        this.localidad = localidad;
        this.codPost = codPost;
        this.email = email;
    }

    /**
     *
     * Setters
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCp(String codPost) {
        this.codPost = codPost;
    }

    /**
     * Getters
     */
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getEmail() {
        return email;
    }

    public String getCp() {
        return codPost;
    }
}
