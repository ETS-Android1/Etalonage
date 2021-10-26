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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ResultatQrCodeScannerActivity extends AppCompatActivity {
    private TextView code, description, emplacement, numeroSerie, dateVisite, societe, technicien, remarque, categorie, exigence, lieu;
    private AppCompatButton envoyerRapport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_qr_code_scanner);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        code = (TextView) findViewById(R.id.code_equipement_resutat_qr_code);
        description = (TextView) findViewById(R.id.description_equipement_resutat_qr_code);
        emplacement = (TextView) findViewById(R.id.emplacement_equipement_resutat_qr_code);
        numeroSerie = (TextView) findViewById(R.id.numero_serie_equipement_resutat_qr_code);
        dateVisite = (TextView) findViewById(R.id.date_visite_equipement_resutat_qr_code);
        societe = (TextView) findViewById(R.id.societe_resutat_qr_code);
        technicien = (TextView) findViewById(R.id.technicien_resutat_qr_code);
        remarque = (TextView) findViewById(R.id.remarque_equipement_resutat_qr_code);
        categorie = (TextView) findViewById(R.id.category_equipement_resutat_qr_code);
        exigence = (TextView) findViewById(R.id.exigence_equipement_resutat_qr_code);
        lieu = (TextView) findViewById(R.id.lieu_equipement_resutat_qr_code);
        setInformationsCodeEquipement();

        envoyerRapport = (AppCompatButton) findViewById(R.id.envoyer_rapport_qr_code_correcte);
        envoyerRapport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantEnvoyerRapport();
            }
        });
    }

    public void setInformationsCodeEquipement(){
        code.setText(getIntent().getStringExtra("code"));
        description.setText(getIntent().getStringExtra("description"));
        emplacement.setText(getIntent().getStringExtra("emplacement"));
        numeroSerie.setText(getIntent().getStringExtra("numeroSerie"));
        dateVisite.setText(getIntent().getStringExtra("dateVisite"));
        societe.setText(getIntent().getStringExtra("societe"));
        technicien.setText(getIntent().getStringExtra("technicien"));
        remarque.setText(getIntent().getStringExtra("remarque"));
        categorie.setText(getIntent().getStringExtra("categorie"));
        exigence.setText(getIntent().getStringExtra("exigence"));
        lieu.setText(getIntent().getStringExtra("lieu"));
    }

    public void verifierConnexionWifiEtServeurAvantEnvoyerRapport(){
        final ProgressDialog progressDialog = new ProgressDialog(ResultatQrCodeScannerActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(VisitesActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        changerEtatEtEnvoyerRapport();
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
        Intent intent = new Intent(ResultatQrCodeScannerActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(ResultatQrCodeScannerActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void changerEtatEtEnvoyerRapport(){
        ProgressDialog progressDialog = new ProgressDialog(ResultatQrCodeScannerActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();

        Thread t = new Thread(new Runnable() {
            public void run() {
               changerEtatVisite();
               envoyerRapportVisite(progressDialog);
            }
        });
        t.start();
    }

    public void changerEtatVisite(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "UpdateEtatVisite.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionDesactiverActivity(VisitesActivity.class);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", getIntent().getStringExtra("code"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void envoyerRapportVisite(ProgressDialog progressDialog){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "CreerRapport.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseServeur(response.trim(), progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("description", getIntent().getStringExtra("description"));
                params.put("emplacement", getIntent().getStringExtra("emplacement"));
                params.put("numeroSerie", getIntent().getStringExtra("numeroSerie"));
                params.put("technicien", getIntent().getStringExtra("technicien"));
                params.put("societe", getIntent().getStringExtra("societe"));
                params.put("remarque", getIntent().getStringExtra("remarque"));
                params.put("codeEquipement", getIntent().getStringExtra("code"));
                params.put("categorie", getIntent().getStringExtra("categorie"));
                params.put("exigence", getIntent().getStringExtra("exigence"));
                params.put("lieu", getIntent().getStringExtra("lieu"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void gestionReponseServeur(String reponse, ProgressDialog progressDialog){
        if(reponse.equals("Rapport crée")){
            progressDialog.dismiss();
            afficherRapportEnvoyer();
        }

        else{
            progressDialog.dismiss();
            afficherRapportNonEnvoyer();
        }
    }

    public void afficherRapportEnvoyer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultatQrCodeScannerActivity.this, R.style.alerte);
        builder.setMessage("Vous avez envoyé un rapport de visite avec succés. Merci de vérifier si le rapport est bien envoyé à partir de votre base de donné");
        builder.setTitle("Rapport de visite");
        builder.setIcon(R.drawable.welcome);
        builder.setNegativeButton("D'accord", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ouvrirVisitesActivity();
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

    public void ouvrirVisitesActivity(){
        Intent intent = new Intent(ResultatQrCodeScannerActivity.this, VisitesActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void afficherRapportNonEnvoyer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultatQrCodeScannerActivity.this, R.style.alerte);
        builder.setMessage("Nous avons trouvés des erreurs lors de l'envoi de ce rapport.. Merci de ressayer ultérieurement..");
        builder.setTitle("Rapport de visite");
        builder.setIcon(R.drawable.info);
        builder.setNegativeButton("D'accord", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ouvrirVisitesActivity();
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultatQrCodeScannerActivity.this, R.style.alerte);
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