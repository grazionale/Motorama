package com.example.gabri.modelos;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Moto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String modelo;

    @NonNull
    private String placa;

    @NonNull
    private String marca;

    @NonNull
    private int ano;


    public Moto(int id, @NonNull String modelo, @NonNull String placa, @NonNull String marca, @NonNull int ano) {
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        this.marca = marca;
        this.ano = ano;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getModelo() {
        return modelo;
    }

    public void setModelo(@NonNull String modelo) {
        this.modelo = modelo;
    }

    @NonNull
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(@NonNull String placa) {
        this.placa = placa;
    }

    @NonNull
    public String getMarca() {
        return marca;
    }

    public void setMarca(@NonNull String marca) {
        this.marca = marca;
    }

    @NonNull
    public int getAno() {
        return ano;
    }

    public void setAno(@NonNull int ano) {
        this.ano = ano;
    }


    @Override
    public String toString() {
        return getModelo();
    }
}
