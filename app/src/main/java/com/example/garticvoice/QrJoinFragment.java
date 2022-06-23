package com.example.garticvoice;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.garticvoice.dao.DAOGame;

/**
 * A simple {@link Fragment} subclass.
 */
public class QrJoinFragment extends Fragment {

    public static String ARG_GAME_ID;

    public QrJoinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {
            Log.d("BARCODE", "Arguments non trouvé");
            return;
        }

        String gameId = getArguments().getString(ARG_GAME_ID);

        DAOGame daoGame = new DAOGame();
        daoGame.waitGameStart(gameId, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_join, container, false);
    }

    public void startGame() {
        NavHostFragment.findNavController(QrJoinFragment.this)
                .navigate(R.id.action_qrJoinFragment_to_roundFragment);
    }
}