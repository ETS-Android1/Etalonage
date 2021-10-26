package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ConnexionDesactiverActivity extends AppCompatActivity {
    private TextView ressayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_desactiver);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ressayer = (TextView) findViewById(R.id.ressayer_connexion_desactiver);
        ressayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(ConnexionDesactiverActivity.this, R.style.progress_dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Vérification de connexion..");
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

    public boolean verificationDeLaConnexion(){
        boolean wifi = testConnexionOuvert();
        if(wifi == true){
            return true;
        }

        else {
            return false;
        }
    }

    public void ouvrirInterfaceDemander(){
        Intent nouveauIntent = new Intent(ConnexionDesactiverActivity.this, (Class<?>) getIntent().getSerializableExtra("activity"));
        startActivity(nouveauIntent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}