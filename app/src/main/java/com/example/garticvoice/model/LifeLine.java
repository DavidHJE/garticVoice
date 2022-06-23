package com.example.garticvoice.model;

import java.util.List;

public class LifeLine {
    private String uuid;
    private List<Round> listRound;
    private String playerId;

    public LifeLine() {
    }

    public LifeLine(String uuid, List<Round> listRound, String playerId) {
        this.uuid = uuid;
        this.listRound = listRound;
        this.playerId = playerId;
    }

    public LifeLine(List<Round> listRound, String playerId) {
        this.listRound = listRound;
        this.playerId = playerId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Round> getListRound() {
        return listRound;
    }

    public void setListRound(List<Round> listRound) {
        this.listRound = listRound;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
