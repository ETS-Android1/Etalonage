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

public class PasswordOublie2Activity extends AppCompatActivity {
    private TextInputEditText code;
    private TextView erreurCode, passwordOubliePage;
    private AppCompatButton verifierCode;
    private int nbrEssaie = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_oublie2);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        code = (TextInputEditText) findViewById(R.id.code_securite_password_oublie2);
        erreurCode = (TextView) findViewById(R.id.erreur_code_securite_password_oublie2);
        passwordOubliePage = (TextView) findViewById(R.id.email_incorrecte_password_oublie2);
        verifierCode = (AppCompatButton) findViewById(R.id.verifier_code_password_oublie2);

        passwordOubliePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur();
            }
        });

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurCode.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        verifierCode.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                validerFormulairePassworOublie2();
            }
        });
    }

    public void verifierConnexionWifiEtServeur(){
        final ProgressDialog progressDialog = new ProgressDialog(PasswordOublie2Activity.this, R.style.progress_dialog);
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
                        ouvrirConnexionServeurDesactiverActivity(PasswordOublie1Activity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvriPasswordOublie1Activity();
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

    public void ouvriPasswordOublie1Activity(){
        Intent intent = new Intent(PasswordOublie2Activity.this, PasswordOublie1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(PasswordOublie2Activity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(PasswordOublie2Activity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
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

    public boolean codeLangueur(){
        String codelString = code.getText().toString().trim();

        if (codelString.length() != 6) {
            return false;
        }

        else {
            return true;
        }
    }

    public void validerFormulairePassworOublie2(){
        if (!inputObligatoire(code)) {
            erreurCode.setText("Le code de sécurité est obligatoire..");
        }

        else if (!codeLangueur()) {
            erreurCode.setText("Le code de sécurité doit être de 6 chiffres au minimum..");
        }

        else {
            erreurCode.setText(null);
            verifierConnexionWifiEtServeurAvantVerificerCodeSecurite();
        }
    }

    public void verifierConnexionWifiEtServeurAvantVerificerCodeSecurite(){
        final ProgressDialog progressDialog = new ProgressDialog(PasswordOublie2Activity.this, R.style.progress_dialog);
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
                        ouvrirConnexionServeurDesactiverActivity(PasswordOublie1Activity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        verifierCodeSecuriteEgalite();
                    }
                }
            }
        },3000);
    }

    public void verifierCodeSecuriteEgalite(){
        Boolean verif = verifierCodeSecurite();

        if(verif == true){
            nbrEssaie = 0;
            afficherCodeSecuriteCorrecte();
        }

        else{
            nbrEssaie++;
            if(nbrEssaie > 3){
                erreurCode.setText("");
                ouvrirNombreLimiteDepasserActivity();
            }

            else{
                erreurCode.setText("Vous avez saisie un code de sécurité incorrecte..");
            }
        }
    }

    public boolean verifierCodeSecurite(){
        int codeSaisie = Integer.parseInt(code.getText().toString());
        int codeEnvoyer = Integer.parseInt(getIntent().getStringExtra("codeSecurite"));
        boolean test = false;

        if(codeSaisie == codeEnvoyer){
            test = true;
        }

        else{
            test = false;
        }
        return test;
    }

    public void afficherCodeSecuriteCorrecte(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordOublie2Activity.this, R.style.alerte_bleu_ciel);
        builder.setMessage("Le code de sécurité saisie est bien saisi. Modifier votre mot de passe pour sécurisé votre compte.");
        builder.setTitle("Eleonetech");
        builder.setIcon(R.drawable.welcome);
        builder.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ouvriPasswordOublie3Activity();
                dialog.cancel();
            }
        });
        AlertDialog alerte = builder.create();
        alerte.show();
        TextView msgTxt = (TextView) alerte.findViewById(android.R.id.message);
        msgTxt.setTextSize(14);
        msgTxt.setTextColor(getResources().getColor(R.color.black));

        Button btnTxt = alerte.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnTxt.setTextColor(getResources().getColor(R.color.bleu_ciel));
    }

    public void ouvriPasswordOublie3Activity(){
        Intent intent = new Intent(PasswordOublie2Activity.this, PasswordOublie3Activity.class);
        intent.putExtra("email",getIntent().getStringExtra("email"));
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirNombreLimiteDepasserActivity(){
        Intent intent = new Intent(PasswordOublie2Activity.this, NombreLimiteDepasserActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordOublie2Activity.this, R.style.alerte_bleu_ciel);
        builder.setMessage("Vous ne pouvez pas retourner à la page précédente.");
        builder.setTitle("Retour");
        builder.setIcon(R.drawable.info_bleu_ciel);
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
        btnTxt.setTextColor(getResources().getColor(R.color.bleu_ciel));
    }
}