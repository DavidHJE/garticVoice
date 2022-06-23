package com.example.garticvoice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.garticvoice.dao.DAOGame;
import com.example.garticvoice.dao.DAOPlayer;
import com.example.garticvoice.databinding.FragmentStartGameBinding;
import com.example.garticvoice.enums.State;
import com.example.garticvoice.model.Game;
import com.example.garticvoice.model.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartGameFragment extends Fragment {
    private FragmentStartGameBinding binding;
    private List<Player> listPlayer;
    private boolean createGame;
    Player dbPlayer;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("BARCODE", "Cancelled scan");
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("BARCODE", "Cancelled scan due to missing camera permission");
                        Toast.makeText(getContext(), "Cancelled due to missing camera permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("BARCODE", "Scanned");
                    Toast.makeText(getContext(), "Scanned", Toast.LENGTH_LONG).show();
                    String gameUuid = result.getContents();

                    DAOGame daoGame = new DAOGame();
                    daoGame.find(gameUuid).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                Log.w("BARCODE", "successful");

                                if (dbPlayer == null) {
                                    Log.d("BARCODE", "Player null, not normal");
                                    return;
                                }

                                List<Player> players = new ArrayList();

                                DocumentSnapshot document = task.getResult();
                                List<HashMap> playersFirebase = (List<HashMap>) document.get("listPlayer");
                                for (HashMap p: playersFirebase) {
                                    Player player = new Player();
                                    player.setUuid((String) p.get("uuid"));
                                    player.setPseudo((String) p.get("pseudo"));
                                    players.add(player);
                                }
                                players.add(dbPlayer);
                                Log.w("BARCODE", "successful");
                                Log.d("BARCODE", players.toString());

                                Game game = new Game();
                                game.setUuid(task.getResult().getId());
                                game.setListPlayer(players);
                                game.setState(State.valueOf(task.getResult().getString("state")));
                                game.setMaxCapacity(Integer.valueOf(task.getResult().get("maxCapacity").toString()));

                                daoGame.update(game).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("BARCODE", "DocumentSnapshot successfully written!");
                                                Toast.makeText(getContext(), "Entrer dans la game", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("BARCODE", "Error writing document", e);
                                            }
                                        });

                            } else {
                                Log.d("BARCODE", "not successful");
                                Toast.makeText(getContext(), "Not game found", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    NavHostFragment.findNavController(StartGameFragment.this)
                            .navigate(R.id.action_startGameFragment_to_qrJoinFragment);
                }
            });

    public StartGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StartGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartGameFragment newInstance() {
        StartGameFragment fragment = new StartGameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_start_game, container, false);
        binding = FragmentStartGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGame = true;
                Player player = new Player(binding.pseudoTextField.getText().toString());
                DAOPlayer daoPlayer = new DAOPlayer();
                dbPlayer = new Player();
                try {
                    dbPlayer = daoPlayer.create(player, StartGameFragment.this);
                } catch (Exception e) {

                }

            }
        });

        binding.BtnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGame = false;
                Player player = new Player(binding.pseudoTextField.getText().toString());
                DAOPlayer daoPlayer = new DAOPlayer();
                dbPlayer = new Player();
                try {
                    dbPlayer = daoPlayer.create(player, StartGameFragment.this);
                } catch (Exception e) {

                }

            }
        });
    }

    public void addPlayer(Player p) {
        if (createGame) {
            Log.d("BARCODE", "Create game");
            DAOGame daoGame = new DAOGame();
            Task<DocumentReference> gameResult;

            listPlayer = new ArrayList();
            listPlayer.add(p);

            try {
                Game game = new Game(10, listPlayer, State.IN_PROGRESS);
                gameResult = daoGame.create(game);
                gameResult.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Bundle bundle = new Bundle();
                        bundle.putString(QrCodeFragment.ARG_GAME_ID, documentReference.getId());

                        NavHostFragment.findNavController(StartGameFragment.this)
                                .navigate(R.id.action_startGameFragment_to_qrCodeFragment, bundle);
                    }
                });

            } catch (Exception e) {
                Log.d("BARECODE", e.getMessage());
            }
        } else {
            Log.d("BARCODE", "Join game");

            ScanOptions options = new ScanOptions();
            options.setOrientationLocked(true);
            options.setBeepEnabled(true);
            options.setBarcodeImageEnabled(false);
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Scan QR Code");
            barcodeLauncher.launch(options);


        }

    }
}