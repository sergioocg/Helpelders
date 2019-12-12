package com.sergio.helpelders.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sergio.helpelders.model.Usuario;

import java.util.List;

@Dao
public abstract class AppDao {
    @Insert
    public abstract void insertarUsuario(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE nTelf = :nTelf AND password = :password")
    public abstract Usuario autenticar(String nTelf, String password);

    // Si está registrado con un nTelf, no deje registrarse otra vez.
    @Query("SELECT * FROM Usuario WHERE nTelf = :nTelf")
    public abstract Usuario comprobarNombreDisponible(String nTelf);

    @Query("SELECT * FROM Usuario")
    public abstract List<Usuario> mostrarTodosLosUsuarios();
}
