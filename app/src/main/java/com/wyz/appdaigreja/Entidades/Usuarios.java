package com.wyz.appdaigreja.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.wyz.appdaigreja.DAO.ConfigurationFireBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by W€µÐr€Y™ on 15/11/2017.
 */

public class Usuarios {

    private String id;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private String sexo;

    public Usuarios() {
    }

    public void salvar() {
        DatabaseReference referenciaFirebase = ConfigurationFireBase.getFirebase();
        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("senha", getSenha());
        hashMapUsuario.put("nome", getNome());
        hashMapUsuario.put("sobrenome", getSobrenome());
        hashMapUsuario.put("aniversario", getDataNascimento());
        hashMapUsuario.put("sexo", getSexo());

        return hashMapUsuario;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
