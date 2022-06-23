package com.example.garticvoice.model;

import androidx.annotation.NonNull;

public class Player {
    private String uuid;
    private String pseudo;


    public Player() {

    }

    public Player(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @NonNull
    @Override
    public String toString() {
        return "Player{" +
                "uuid='" + uuid + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
}
