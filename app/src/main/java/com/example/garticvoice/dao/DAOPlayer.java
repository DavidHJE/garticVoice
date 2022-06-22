package com.example.garticvoice.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.garticvoice.StartGameFragment;
import com.example.garticvoice.model.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DAOPlayer {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAOPlayer() {

    }

    public Player create(Player player, StartGameFragment fragment){
        Map<String, Object> newPlayer = new HashMap<>();
        newPlayer.put("pseudo", player.getPseudo());
        if(player !=  null){
            db.collection("players")
                    .add(newPlayer)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            player.setUuid(documentReference.getId());
                            fragment.addPlayer(player);
                        }
                    });
        }
        return player;
    }

}
