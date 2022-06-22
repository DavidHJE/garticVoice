package com.example.garticvoice.dao;

import android.util.Log;

import com.example.garticvoice.model.Game;
import com.example.garticvoice.model.Player;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DAOGame {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAOGame() {

    }

    public Game create(Game game){
        Map<String, Object> newGame = new HashMap<>();
        newGame.put("maxCapacity", game.getMaxCapacity());
        newGame.put("listPlayer", game.getListPlayer());
        newGame.put("state", game.getState());

        if(game !=  null){
            db.collection("games")
                    .add(newGame)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            game.setUuid(documentReference.getId());
                        }
                    });
        }
        return game;
    }

}
