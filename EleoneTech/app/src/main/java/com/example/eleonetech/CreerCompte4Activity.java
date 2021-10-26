package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreerCompte4Activity extends AppCompatActivity {
    private TextInputEditText naissance, poste;
    private TextView erreurNaissance, erreurPoste;
    private AppCompatButton suivant4;
    private DatePickerDialog.OnDateSetListener date;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte4);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        naissance = (TextInputEditText) findViewById(R.id.naissance_creer_compte4);
        poste = (TextInputEditText) findViewById(R.id.poste_creer_compte4);
        erreurNaissance = (TextView) findViewById(R.id.erreur_naissance_creer_compte4);
        erreurPoste = (TextView) findViewById(R.id.erreur_poste_creer_compte4);
        suivant4 = (AppCompatButton) findViewById(R.id.suivant_creer_compte4);

        naissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurerCalandrier();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                actionSurCalandrier(year, month, dayOfMonth);
            }
        };

        naissance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurNaissance.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        poste.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurPoste.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        suivant4.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                validerFormulaireCreerCompte4();
            }
        });
    }

    public void configurerCalandrier(){
        Calendar agenda = Calendar.getInstance();
        int year = agenda.get(Calendar.YEAR);
        int month = agenda.get(Calendar.MONTH);
        int day = agenda.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(CreerCompte4Activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, date, year , month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void actionSurCalandrier(int year, int month, int day){
        month = month + 1;
        String chaine = year + "-" + month + "-" + day;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(chaine);
            naissance.setText(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public void validerFormulaireCreerCompte4(){
        if(!inputObligatoire(naissance)){
            erreurNaissance.setText("La date de naissance est obligatoire..");
        }

        else if(!inputObligatoire(poste)){
            erreurPoste.setText("Le poste est obligatoire..");
        }

        else if(!inputChaine(poste)){
            erreurPoste.setText("Le poste n'est pas valide..");
        }

        else{
            erreurNaissance.setText(null);
            erreurPoste.setError(null);
            verifierConnexionWifiEtServeur();
        }
    }

    public void verifierConnexionWifiEtServeur(){
        final ProgressDialog progressDialog = new ProgressDialog(CreerCompte4Activity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(CreerCompte4Activity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(CreerCompte4Activity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        creerNouveauCompte();
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
        Intent intent = new Intent(CreerCompte4Activity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(CreerCompte4Activity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void creerNouveauCompte(){
        ProgressDialog progressDialog = new ProgressDialog(CreerCompte4Activity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Création de compte..");
        progressDialog.show();

        Thread t = new Thread(new Runnable() {
            public void run() {
                String pagesPhp = new AdresseIp().urlPagesPhp();
                String url = pagesPhp + "CreerCompte.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gestionReponseServeur(response.trim());
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ouvrirConnexionServeurDesactiverActivity(CreerCompte1Activity.class);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", getIntent().getStringExtra("email"));
                        params.put("password", getIntent().getStringExtra("password"));
                        params.put("nom", getIntent().getStringExtra("nom"));
                        params.put("prenom", getIntent().getStringExtra("prenom"));
                        params.put("poste", poste.getText().toString());
                        params.put("numero", getIntent().getStringExtra("numero"));
                        params.put("adresse", getIntent().getStringExtra("adresse"));
                        params.put("naissance", naissance.getText().toString());
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

    public void gestionReponseServeur(String reponse){
        if(reponse.equals("Compte crée")){
            afficherCompteCreer();
        }

        else{
            ouvrirConnexionServeurDesactiverActivity(CreerCompte1Activity.class);
        }
    }

    public void afficherCompteCreer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreerCompte4Activity.this, R.style.alerte);
        builder.setMessage("Bienvenue sur l'application Eleonetech. Votre compte a été créé avec succés. Vous pouvez maintenant utiliser votre compte");
        builder.setTitle("Eleonetech");
        builder.setIcon(R.drawable.welcome);
        builder.setNegativeButton("Commencer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                envoyerMailConfirmation();
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

    public void envoyerMailConfirmation(){
        ProgressDialog progressDialog = new ProgressDialog(CreerCompte4Activity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();

        Thread t = new Thread(new Runnable() {
            public void run() {
                String pagesPhp = new AdresseIp().urlPagesPhp();
                String url = pagesPhp + "EnvoyerConfirmationMail.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gestionReponseServeurEnvoieMail(response.trim());
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ouvrirConnexionServeurDesactiverActivity(CreerCompte1Activity.class);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email",getIntent().getStringExtra("email"));
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

    public void gestionReponseServeurEnvoieMail(String reponse){
        if(reponse.trim().equals("Email envoyé")){
            creerSession();
            ouvrirHomeActivity();
        }

        else{
            ouvrirConnexionServeurDesactiverActivity(CreerCompte1Activity.class);
        }
    }

    public void creerSession(){
        pref = getSharedPreferences("utilisateur",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email",getIntent().getStringExtra("email"));
        editor.putString("password",getIntent().getStringExtra("password"));
        editor.commit();
    }

    public void ouvrirHomeActivity(){
        creerSession();
        Intent intent = new Intent(CreerCompte4Activity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreerCompte4Activity.this, R.style.alerte);
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