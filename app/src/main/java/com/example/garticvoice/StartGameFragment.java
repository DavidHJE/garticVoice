package com.example.garticvoice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garticvoice.dao.DAOGame;
import com.example.garticvoice.dao.DAOPlayer;
import com.example.garticvoice.databinding.FragmentStartGameBinding;
import com.example.garticvoice.enums.State;
import com.example.garticvoice.model.Game;
import com.example.garticvoice.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentStartGameBinding binding;
    private List<Player> listPlayer;
    private boolean createGame;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StartGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartGameFragment newInstance(String param1, String param2) {
        StartGameFragment fragment = new StartGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_start_game, container, false);
        binding = FragmentStartGameBinding.inflate(inflater,container,false);
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
                    dbPlayer = daoPlayer.create(player,StartGameFragment.this);

                }catch(Exception e){

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
                    dbPlayer = daoPlayer.create(player,StartGameFragment.this);

                }catch(Exception e){

                }

            }
        });
    }

    public void addPlayer(Player p){
        if(createGame){
            DAOGame daoGame = new DAOGame();
            Game dbGame = new Game();

            listPlayer = new ArrayList();
            listPlayer.add(p);

            try {
                Game game = new Game(10,listPlayer, State.IN_PROGRESS);
                dbGame = daoGame.create(game);
                NavHostFragment.findNavController(StartGameFragment.this)
                        .navigate(R.id.action_startGameFragment_to_qrCodeFragment);
            }catch(Exception e){

            }
        }else{
            NavHostFragment.findNavController(StartGameFragment.this)
                    .navigate(R.id.action_startGameFragment_to_qrJoinFragment);
        }

    }
}