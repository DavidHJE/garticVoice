package com.example.garticvoice;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
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
                Player dbPlayer = new Player();
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
                Player dbPlayer = new Player();
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
            NavHostFragment.findNavController(StartGameFragment.this)
                    .navigate(R.id.action_startGameFragment_to_qrJoinFragment);
        }

    }
}