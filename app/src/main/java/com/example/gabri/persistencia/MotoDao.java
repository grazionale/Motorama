package com.example.gabri.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.gabri.modelos.Moto;

import java.util.List;

@Dao
public interface MotoDao {

    @Insert
    public long insert(Moto moto);

    @Delete
    public void delete(Moto moto);

    @Update
    public void update(Moto moto);

    @Query("SELECT * FROM moto WHERE id = :id")
    public Moto queryForId(long id);

    @Query("SELECT * FROM moto ORDER BY id ASC")
    public List<Moto> queryAll();
}
