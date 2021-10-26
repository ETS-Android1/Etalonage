package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import java.io.IOException;
import java.net.Socket;

public class ConnexionServeurDesactiverActivity extends AppCompatActivity {
    private TextView ressayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_serveur_desactiver);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ressayer = (TextView) findViewById(R.id.ressayer_connexion_desactiver);

        ressayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(ConnexionServeurDesactiverActivity.this, R.style.progress_dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Reconnexion au serveur..");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(verificationDeLaConnexion() == true){
                            progressDialog.dismiss();
                            ouvrirInterfaceDemander();
                        }

                        else{
                            progressDialog.dismiss();
                        }
                    }
                },3000);
            }
        });
    }

    public boolean testConnexionServeur(){
        boolean isUp = false;
        try {
            Socket socket = new Socket(new AdresseIp().getAdresse(), 80);
            isUp = true;
            socket.close();
        }

        catch (IOException e) {
            isUp = false;
        }
        return isUp;
    }

    public boolean verificationDeLaConnexion(){
        boolean serveur = testConnexionServeur();
        if(serveur == true){
            return true;
        }

        else {
            return false;
        }
    }

    public void ouvrirInterfaceDemander(){
        Intent nouveauIntent = new Intent(ConnexionServeurDesactiverActivity.this, (Class<?>) getIntent().getSerializableExtra("activity"));
        startActivity(nouveauIntent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}