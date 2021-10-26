package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import java.io.IOException;
import java.net.Socket;

public class NombreLimiteDepasserActivity extends AppCompatActivity {
    private TextView ressayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_limite_depasser);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ressayer = (TextView) findViewById(R.id.ressayer_ici_nombre_essaie);
        ressayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur();
            }
        });
    }

    public void verifierConnexionWifiEtServeur() {
        final ProgressDialog progressDialog = new ProgressDialog(NombreLimiteDepasserActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(PasswordOublie1Activity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionDesactiverActivity(PasswordOublie1Activity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirPasswordOublie1Activity();
                    }
                }
            }
        },3000);
    }

    public boolean verifierConnexionWifi(){
        if(testConnexionOuvert() == true){
            return true;
        }

        else{
            return false;
        }
    }

    public boolean verifierConnexionServeur(){
        if(testConnexionServeur() == true){
            return true;
        }

        else{
            return false;
        }
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(NombreLimiteDepasserActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirPasswordOublie1Activity() {
        Intent intent = new Intent(NombreLimiteDepasserActivity.this, PasswordOublie1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public boolean testConnexionOuvert(){
        boolean wifiConnected;
        boolean internet = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;

            if (wifiConnected == true){
                internet = true;
            }

            else{
                internet = false;
            }
        }
        return internet;
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

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(NombreLimiteDepasserActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }
}