package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.net.Socket;

public class QrCodeIncorrecteActivity extends AppCompatActivity {
    private AppCompatButton retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_incorrecte);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        retour = (AppCompatButton) findViewById(R.id.retour_visites_qr_code_incorrecte);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantRetourAuxVisites();
            }
        });
    }

    public void verifierConnexionWifiEtServeurAvantRetourAuxVisites(){
        final ProgressDialog progressDialog = new ProgressDialog(QrCodeIncorrecteActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(QrCodeIncorrecteActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(QrCodeIncorrecteActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirVisitesActivity();
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

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(QrCodeIncorrecteActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(QrCodeIncorrecteActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirVisitesActivity(){
        Intent intent = new Intent(QrCodeIncorrecteActivity.this, VisitesActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QrCodeIncorrecteActivity.this, R.style.alerte);
        builder.setMessage("Vous ne pouvez pas retourner à la page précédente.");
        builder.setTitle("Retour");
        builder.setIcon(R.drawable.info);
        builder.setNegativeButton("D'accord", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alerte = builder.create();
        alerte.show();
        TextView msgTxt = (TextView) alerte.findViewById(android.R.id.message);
        msgTxt.setTextSize(14);
        msgTxt.setTextColor(getResources().getColor(R.color.black));

        Button btnTxt = alerte.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnTxt.setTextColor(getResources().getColor(R.color.bleu));
    }
}