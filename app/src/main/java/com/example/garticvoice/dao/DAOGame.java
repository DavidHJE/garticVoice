package com.example.garticvoice.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.garticvoice.QrJoinFragment;
import com.example.garticvoice.enums.State;
import com.example.garticvoice.model.Game;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DAOGame {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAOGame() {

    }

    public Task<DocumentReference> create(Game game) {
        Map<String, Object> newGame = new HashMap<>();
        newGame.put("maxCapacity", game.getMaxCapacity());
        newGame.put("listPlayer", game.getListPlayer());
        newGame.put("state", game.getState());

        return db.collection("games")
                .add(newGame);
    }

    public Task<DocumentSnapshot> find(String uuid) {
        return db.collection("games").document(uuid).get();
    }

    public Task<Void> update(@NonNull Game game) {
        Map<String, Object> newGame = new HashMap<>();
        newGame.put("maxCapacity", game.getMaxCapacity());
        newGame.put("listPlayer", game.getListPlayer());
        newGame.put("state", game.getState());

        return db.collection("games").document(game.getUuid()).update(newGame);
    }

    public void waitGameStart(String uuid, QrJoinFragment fragment) {
        db.collection("games").document(uuid).addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w("BARCODE", "Listen failed.", e);
                return;
            }

            if (snapshot != null && snapshot.exists() && State.valueOf((String) snapshot.get("state")) == State.STARTED) {
                Log.d("BARCODE", "Current data: " + snapshot.getData());
                fragment.startGame();
            } else {
                Log.d("BARCODE", "Current data: null");
            }
        });
    }

    public Task<Void> startRoundGame(String gameId) {
        return db.collection("games").document(gameId).update("state", State.STARTED);
    }
}
