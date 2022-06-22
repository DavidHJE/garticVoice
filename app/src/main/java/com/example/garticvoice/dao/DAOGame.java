package com.example.garticvoice.dao;

import com.example.garticvoice.model.Game;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DAOGame {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAOGame() {

    }

    public Task<DocumentReference> create(Game game) throws Exception {
        Map<String, Object> newGame = new HashMap<>();
        newGame.put("maxCapacity", game.getMaxCapacity());
        newGame.put("listPlayer", game.getListPlayer());
        newGame.put("state", game.getState());

        if (game == null) {
            throw new Exception("Game empty");
        }

        return db.collection("games")
                .add(newGame);
    }

}
