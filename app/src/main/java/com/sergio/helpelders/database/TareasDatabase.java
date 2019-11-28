package com.sergio.helpelders.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Usuario.class}, version = 1)
public abstract class TareasDatabase extends RoomDatabase {
    private static TareasDatabase INSTANCE;

    public abstract UsuarioDAO usuarioDAO();

    public static TareasDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, TareasDatabase.class, "app.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            insertarUsuario(getInstance(context).usuarioDAO());
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }

    static void insertarUsuario(final UsuarioDAO usuarioDAO){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                usuarioDAO.insertarUsuario(new Usuario("633221044", "12345"));
            }
        });
    }
}
