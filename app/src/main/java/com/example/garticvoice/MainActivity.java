package com.example.garticvoice;

import android.os.Bundle;

import com.example.garticvoice.dao.DAOPlayer;
import com.example.garticvoice.model.Player;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.garticvoice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TRUC", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TRUC", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TRUC", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TRUC", "onDestroy");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void clickOnBtnCreateGame(View v){
        TextView pseudoTextField = this.findViewById(R.id.PseudoTextField);
        Player player = new Player(pseudoTextField.getText().toString());
        DAOPlayer daoPlayer = new DAOPlayer();
        try {
            Player dbPlayer = daoPlayer.create(player);
            Toast.makeText(this,"Ouverture QR", Toast.LENGTH_SHORT).show();
        }catch(Exception e){

        }
    }
}