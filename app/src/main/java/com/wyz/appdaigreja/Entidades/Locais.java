package com.wyz.appdaigreja.Entidades;

/**
 * Created by W€µÐr€Y™ on 17/11/2017.
 */

public class Locais {

    private String id_local;
    private String nome_local;
    private String rua_local;
    private String bairro_local;
    private String cidade_local;

    public Locais() {
    }

    public String getId_local() {
        return id_local;
    }

    public void setId_local(String id_local) {
        this.id_local = id_local;
    }

    public String getNome_local() {
        return nome_local;
    }

    public void setNome_local(String nome_local) {
        this.nome_local = nome_local;
    }

    public String getRua_local() {
        return rua_local;
    }

    public void setRua_local(String rua_local) {
        this.rua_local = rua_local;
    }

    public String getBairro_local() {
        return bairro_local;
    }

    public void setBairro_local(String bairro_local) {
        this.bairro_local = bairro_local;
    }

    public String getCidade_local() {
        return cidade_local;
    }

    public void setCidade_local(String cidade_local) {
        this.cidade_local = cidade_local;
    }
}
