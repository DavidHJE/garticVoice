package com.example.garticvoice.dao;

import com.example.garticvoice.StartGameFragment;
import com.example.garticvoice.model.Player;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DAOPlayer {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAOPlayer() {

    }

    public Player create(Player player, StartGameFragment fragment) {
        Map<String, Object> newPlayer = new HashMap<>();
        newPlayer.put("pseudo", player.getPseudo());
        db.collection("players")
                .add(newPlayer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        player.setUuid(documentReference.getId());
                        fragment.addPlayer(player);
                    }
                });
        return player;
    }

}
