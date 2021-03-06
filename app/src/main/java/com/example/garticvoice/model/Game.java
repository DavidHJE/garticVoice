package com.example.garticvoice.model;

import androidx.annotation.NonNull;

import com.example.garticvoice.enums.State;

import java.util.List;


public class Game {
    private String uuid;
    private Integer maxCapacity;
    private List<Player> listPlayer;
    private State state;

    public Game() {

    }

    public Game(Integer maxCapacity, List<Player> listPlayer, State state) {
        this.maxCapacity = maxCapacity;
        this.listPlayer = listPlayer;
        this.state = state;
    }

    public void addPlayer(Player p) {
        this.listPlayer.add(p);
    }

    // TODO : remove selon l'uuid
    public void removePlayer(Player p) {
        this.listPlayer.remove(p);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<Player> getListPlayer() {
        return listPlayer;
    }

    public void setListPlayer(List<Player> listPlayer) {
        this.listPlayer = listPlayer;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @NonNull
    @Override
    public String toString() {
        return "Game{" +
                "uuid='" + uuid + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", listPlayer=" + listPlayer.toString() +
                ", state=" + state +
                '}';
    }
}
