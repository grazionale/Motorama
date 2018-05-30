package com.example.gabri.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.gabri.modelos.Gasto;
import com.example.gabri.modelos.Moto;

import java.util.List;

@Dao
public interface GastoDao {

    @Insert
    public void insert(Gasto gasto);

    @Delete
    public void delete(Gasto gasto);

    @Update
    public void update(Gasto gasto);

    @Query("SELECT * FROM gasto WHERE id = :id")
    public Gasto queryForId(long id);

    @Query("SELECT * FROM gasto ORDER BY id ASC")
    public List<Gasto> queryAll();
}
