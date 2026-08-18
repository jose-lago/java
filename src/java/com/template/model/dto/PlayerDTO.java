package com.template.model.dto;

public class PlayerDTO {

    private int id;
    private String nome;
    private String nick;
    private int idade;
    private String timequejoga;

    public PlayerDTO() {
    }

    public PlayerDTO(int id, String nome, String nick, int idade, String timequejoga) {
        this.id = id;
        this.nome = nome;
        this.nick = nick;
        this.idade = idade;
        this.timequejoga = timequejoga;
    }

    public PlayerDTO(String nome, String nick, int idade, String timequejoga) {
        this.nome = nome;
        this.nick = nick;
        this.idade = idade;
        this.timequejoga = timequejoga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getTimequejoga() {
        return timequejoga;
    }

    public void setTimequejoga(String timequejoga) {
        this.timequejoga = timequejoga;
    }
}