package com.sergio.helpelders.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int idUsuario;

    public String nombre, apellidos, fechaNac, nTelf, password;

    public Usuario(String nombre, String apellidos, String fechaNac, String nTelf, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNac = fechaNac;
        this.nTelf = nTelf;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", nTelf='" + nTelf + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
