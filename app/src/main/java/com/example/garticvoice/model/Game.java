package com.example.garticvoice.model;

import java.util.List;

public class Game {
    private String uuid;
    private Integer maxCapacity;
    private List<Player> listPlayer;
    private enum State {
        IN_PROGRESS,
        STARTED,
        COMPLETED
    }
}
