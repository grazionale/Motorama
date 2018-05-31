package com.example.gabri.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "gasto")
public class Gasto {

    @ColumnInfo(name = "id")
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "moto")
    private String moto;

    @ColumnInfo(name = "descricao")
    private String descricao;

    @ColumnInfo(name = "comentario")
    private String comentario;

    @ColumnInfo(name = "km")
    private int km;

    @ColumnInfo(name = "valor")
    private double valor;

    @ColumnInfo(name = "data")
    private String data;

    @ColumnInfo(name = "ocorrencia")
    private String ocorrencia;

    public Gasto() { }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    @Override
    public String toString() {
        return id + " | " + moto + " | " + descricao  + " | " + comentario + " | " + km + " | " + valor + " | " + data + '\'' + " | " + ocorrencia;
    }
}
