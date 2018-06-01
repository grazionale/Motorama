package com.example.gabri.persistencia;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.gabri.modelos.Gasto;
import com.example.gabri.modelos.Moto;

@Database(entities = {Moto.class, Gasto.class}, version = 15, exportSchema = false)
public abstract class MotoramaDatabase extends RoomDatabase {

    private static MotoramaDatabase instance;

    public abstract MotoDao motoDao();
    public abstract GastoDao gastoDao();

    public static MotoramaDatabase getDatabase(final Context context){

//        if(instance == null){
//            instance = Room.databaseBuilder(context, MotoramaDatabase.class, "motorama.db").allowMainThreadQueries().build();
//        }
//
//        return instance;

        if (instance == null) {

            synchronized (MotoramaDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            MotoramaDatabase.class,
                            "motorama.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
