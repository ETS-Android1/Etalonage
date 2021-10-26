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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import java.net.Socket;

public class CreerCompte3Activity extends AppCompatActivity {
    private TextInputEditText adresse, numero;
    private TextView erreurAdresse, erreurNumero;
    private AppCompatButton suivant3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte3);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        adresse = (TextInputEditText) findViewById(R.id.adresse_creer_compte3);
        numero = (TextInputEditText) findViewById(R.id.numero_creer_compte3);
        erreurAdresse = (TextView) findViewById(R.id.erreur_adresse_creer_compte3);
        erreurNumero = (TextView) findViewById(R.id.erreur_numero_creer_compte3);
        suivant3 = (AppCompatButton) findViewById(R.id.suivant_creer_compte3);

        adresse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurAdresse.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        numero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurNumero.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        suivant3.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                validerFormulaireCreerCompte3();
            }
        });
    }

    public boolean inputObligatoire(TextInputEditText input){
        String inputString = input.getText().toString().trim();

        if (inputString.isEmpty()) {
            return false;
        }

        else {
            return true;
        }
    }

    private boolean numeroLength(){
        String numeroString = numero.getText().toString().trim();
        if (numeroString.length() != 8){
            return false;
        }

        else{
            return true;
        }
    }

    public boolean inputChaine(TextInputEditText input){
        boolean test = false;
        String inputString = input.getText().toString().toLowerCase();
        char[] charArray = inputString.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!(ch >= 'a' && ch <= 'z')) {
                test = false;
            }

            else{
                test = true;
            }
        }
        return test;
    }

    public void validerFormulaireCreerCompte3(){
        if(!inputObligatoire(adresse)){
            erreurAdresse.setText("L'adresse est obligatoire..");
        }

        else if(!inputChaine(adresse)){
            erreurAdresse.setText("L'adresse n'est pas valide..");
        }

        else if(!inputObligatoire(numero)){
            erreurNumero.setText("Le numéro mobile est obligatoire..");
        }

        else if(!numeroLength()){
            erreurNumero.setText("Le numéro mobile doit être de 8 chiffres..");
        }

        else{
            erreurAdresse.setText(null);
            erreurNumero.setError(null);

            verifierConnexionWifiEtServeur();
        }
    }

    public void verifierConnexionWifiEtServeur(){
        final ProgressDialog progressDialog = new ProgressDialog(CreerCompte3Activity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(CreerCompte1Activity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(CreerCompte1Activity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirCreerCompte4Activity();
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
        Intent intent = new Intent(CreerCompte3Activity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(CreerCompte3Activity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirCreerCompte4Activity(){
        Intent intent = new Intent(CreerCompte3Activity.this, CreerCompte4Activity.class);
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("password", getIntent().getStringExtra("password"));
        intent.putExtra("nom", getIntent().getStringExtra("nom"));
        intent.putExtra("prenom", getIntent().getStringExtra("prenom"));
        intent.putExtra("adresse", adresse.getText().toString());
        intent.putExtra("numero", numero.getText().toString());
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreerCompte3Activity.this, R.style.alerte);
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