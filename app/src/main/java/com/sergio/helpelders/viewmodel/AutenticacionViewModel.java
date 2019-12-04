package com.sergio.helpelders.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sergio.helpelders.db.AppDao;
import com.sergio.helpelders.db.AppDatabase;
import com.sergio.helpelders.model.Usuario;

public class AutenticacionViewModel extends AndroidViewModel {

    public enum EstadoDeLaAutenticacion {
        NO_AUTENTICADO,
        AUTENTICADO,
        AUTENTICACION_INVALIDA
    }

    public enum EstadoDelRegistro {
        INICIO_DEL_REGISTRO,
        NOMBRE_NO_DISPONIBLE,
        REGISTRO_COMPLETADO
    }

    private AppDao appDao;

    public Usuario usuarioLogeado;

    public MutableLiveData<EstadoDeLaAutenticacion> estadoDeLaAutenticacion = new MutableLiveData<>(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    public MutableLiveData<EstadoDelRegistro> estadoDelRegistro = new MutableLiveData<>(EstadoDelRegistro.INICIO_DEL_REGISTRO);

    public AutenticacionViewModel(@NonNull Application application) {
        super(application);
        appDao = AppDatabase.getInstance(application).dao();
    }

    public void iniciarRegistro(){
        estadoDelRegistro.postValue(EstadoDelRegistro.INICIO_DEL_REGISTRO);
    }

    public void crearCuentaEIniciarSesion(final String nombre, final String apellidos,
                                          final String fechaNac, final String nTelf, final String pass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = appDao.comprobarNombreDisponible(nTelf);
                if(usuario == null){
                    appDao.insertarUsuario(new Usuario(nombre, apellidos, fechaNac, nTelf, pass));
                    estadoDelRegistro.postValue(EstadoDelRegistro.REGISTRO_COMPLETADO);
                    iniciarSesion(nTelf, pass);
                } else {
                    estadoDelRegistro.postValue(EstadoDelRegistro.NOMBRE_NO_DISPONIBLE);
                }
            }
        });
    }

    public void iniciarSesion(final String nTelf, final String pass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = appDao.autenticar(nTelf, pass);
                if(usuario != null){
                    usuarioLogeado = usuario;
                    estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICADO);
                } else {
                    estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICACION_INVALIDA);
                }
            }
        });
    }

    public void cerrarSesion() {
        usuarioLogeado = null;
        estadoDeLaAutenticacion.setValue(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    }

    public String mostrarUsuarios() {
        Usuario usuario = appDao.mostrarTodosLosUsuarios();
        return usuario.toString();
    }
}
