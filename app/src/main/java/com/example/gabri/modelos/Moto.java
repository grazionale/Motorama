package com.example.gabri.modelos;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "moto")
public class Moto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "modelo")
    @NonNull
    private String modelo;

    @NonNull
    private String placa;

    @NonNull
    private String marca;

    private int ano;

    public Moto() {
    }
//    public Moto(String modelo, String placa, String marca, int ano) {
//        this.modelo = modelo;
//        this.placa = placa;
//        this.marca = marca;
//        this.ano = ano;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }


    @Override
    public String toString() {

        String moto = getId() + "  |  " + getModelo() + "  |  " + getMarca() + "  |  " + getAno() + "  |  " + getPlaca();

        return moto;
    }
}
