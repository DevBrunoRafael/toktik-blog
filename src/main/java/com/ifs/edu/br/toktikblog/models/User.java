package com.ifs.edu.br.toktikblog.models;

import java.util.LinkedList;
import java.util.UUID;

public class User {

    private String uuid;
    private String nome;
    private String email;
    private String senha;
    private final LinkedList<User> amigos = new LinkedList<>();

    public User() {
        super();
        this.uuid = UUID.randomUUID().toString();
    }

    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.uuid = UUID.randomUUID().toString();
    }

    public User(String nome, String email, String senha, String uuid) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.uuid = uuid;
    }

    public void adicionarAmigo(User user) {
        amigos.add(user);
    }

    public LinkedList<User> listaDeAmigos() {
        return this.amigos;
    }

    public void buscarAmigos(String text){
        // TODO
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    @Override
    public String toString() {
        return "{" +
                "uuid=" + uuid +
                ", email=" + email +
                ", senha=" + senha +
                ", nome=" + nome +
                "}";
    }
}
