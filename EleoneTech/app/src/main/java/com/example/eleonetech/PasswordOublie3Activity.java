package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PasswordOublie3Activity extends AppCompatActivity {
    private TextInputEditText password, confirmerPassword;
    private TextView erreurPassword, erreurConfirmerPassword;
    private AppCompatButton changer;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_oublie3);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        password = (TextInputEditText) findViewById(R.id.password_changer_password_oublie3);
        confirmerPassword = (TextInputEditText) findViewById(R.id.repeter_changer_password_password_oublie3);
        erreurPassword = (TextView) findViewById(R.id.erreur_changer_password_password_oublie3);
        erreurConfirmerPassword = (TextView) findViewById(R.id.erreur_repeter_changer_password_password_oublie3);
        changer = (AppCompatButton) findViewById(R.id.modifier_password_password_oublie3);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurPassword.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        confirmerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurConfirmerPassword.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        changer.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                validerPasswordOublie3();
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

    public boolean inputLength(TextInputEditText input){
        String inputString = input.getText().toString().trim();

        if (inputString.length() < 4) {
            return false;
        }

        else {
            return true;
        }
    }

    public boolean verifierEgalitePassword(){
        String passwordString = password.getText().toString().trim();
        String confirmerPasswordString = confirmerPassword.getText().toString().trim();

        if(!passwordString.equals(confirmerPasswordString)){
            return false;
        }

        else{
            return true;
        }
    }

    public void validerPasswordOublie3(){
        if(!inputObligatoire( password)){
            erreurPassword.setText("Le mot de passe est obligatoire..");
        }

        else if(!inputLength(password)){
            erreurPassword.setText("Le mot de passe doit être de 4 catactéres au minimum..");
        }

        else if(!inputObligatoire(confirmerPassword)){
            erreurConfirmerPassword.setText("Le mot de passe est obligatoire..");
        }

        else if(!inputLength(confirmerPassword)){
            erreurConfirmerPassword.setText("Le mot de passe doit être de 4 catactéres au minimum..");
        }

        else if(!verifierEgalitePassword()){
            erreurPassword.setText("Les mots de passes ne sont pas identiques..");
            erreurConfirmerPassword.setText("Les mots de passes ne sont pas identiques..");
        }

        else{
            erreurPassword.setText("");
            erreurConfirmerPassword.setText("");
            verifierConnexionWifiEtServeurAvantModification();
        }
    }

    public void verifierConnexionWifiEtServeurAvantModification(){
        final ProgressDialog progressDialog = new ProgressDialog(PasswordOublie3Activity.this, R.style.progress_dialog);
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
                        updatePasswordCompte();
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
        Intent intent = new Intent(PasswordOublie3Activity.this, ConnexionDesactiverActivity.class);
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
        Intent intent = new Intent(PasswordOublie3Activity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void updatePasswordCompte(){
        ProgressDialog progressDialog = new ProgressDialog(PasswordOublie3Activity.this, R.style.progress_dialog_bleu_ciel);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification de mot de passe..");
        progressDialog.show();

        Thread t = new Thread(new Runnable() {
            public void run() {
                String pagesPhp = new AdresseIp().urlPagesPhp();
                String url = pagesPhp + "UpdatePassword.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gestionReponseApresModificationPassword(response.trim());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ouvrirConnexionServeurDesactiverActivity(PasswordOublie1Activity.class);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", getIntent().getStringExtra("email"));
                        params.put("password", password.getText().toString());
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
        });
        t.start();
    }

    public void gestionReponseApresModificationPassword(String reponse){
        if(reponse.equals("Password modifié")){
            afficherPasswordModifier();
        }

        else{
            ouvrirConnexionServeurDesactiverActivity(PasswordOublie1Activity.class);
        }
    }

    public void afficherPasswordModifier(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordOublie3Activity.this, R.style.alerte_bleu_ciel);
        builder.setMessage("Nous sommes trés heureux de vous revoir içi une autre fois. Essayer de n'oublier pas votre mot de passe encore une autre fois");
        builder.setTitle("Eleonetech");
        builder.setIcon(R.drawable.welcome_bleu_ciel);
        builder.setNegativeButton("D'accord", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                creerSession();
                ouvrirHomeActivity();
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

    public void creerSession(){
        pref = getSharedPreferences("utilisateur",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email",getIntent().getStringExtra("email"));
        editor.putString("password",password.getText().toString());
        editor.commit();
    }

    public void ouvrirHomeActivity(){
        Intent intent = new Intent(PasswordOublie3Activity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordOublie3Activity.this, R.style.alerte_bleu_ciel);
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