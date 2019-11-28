package com.sergio.helpelders.database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public abstract class UsuarioDAO {
    @Insert
    public abstract void insertarUsuario(Usuario u);
}
