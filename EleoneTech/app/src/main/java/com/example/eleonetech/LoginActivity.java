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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
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

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email, password;
    private TextView creerComptePage, passwordOubliePage, erreurEmail, erreurPassword;
    private AppCompatButton connexion;
    private SharedPreferences pref;
    private String emailSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        pref = getSharedPreferences("utilisateur",MODE_PRIVATE);
        emailSession = pref.getString("email",null);
        testSession(emailSession);

        email = (TextInputEditText) findViewById(R.id.email_login);
        password = (TextInputEditText) findViewById(R.id.password_login);
        erreurEmail = (TextView) findViewById(R.id.erreur_email_login);
        erreurPassword = (TextView) findViewById(R.id.erreur_password_login);
        creerComptePage = (TextView) findViewById(R.id.creer_compte_login);
        passwordOubliePage = (TextView) findViewById(R.id.password_oublie_login);
        connexion = (AppCompatButton) findViewById(R.id.connexion_login);

        creerComptePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur("CreerCompte");
            }
        });

        passwordOubliePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur("PasswordOublie");
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurEmail.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

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

        connexion.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                validerFormulaireConnexion();
            }
        });
    }

    public void testSession(String email){
        if(email != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void verifierConnexionWifiEtServeur(String page){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    if(page.equals("CreerCompte")){
                        ouvrirConnexionDesactiverActivity(CreerCompte1Activity.class);
                    }

                    else if(page.equals("PasswordOublie")){
                        ouvrirConnexionDesactiverActivity(PasswordOublie1Activity.class);
                    }

                    else if(page.equals("Login")){
                        ouvrirConnexionDesactiverActivity(LoginActivity.class);
                    }
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(LoginActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        if(page.equals("CreerCompte")){
                            ouvrirCreerCompte1Activity();
                        }

                        else if(page.equals("PasswordOublie")){
                            ouvrirPasswordOublie1Activity();
                        }
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
        Intent intent = new Intent(LoginActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirCreerCompte1Activity(){
        Intent intent = new Intent(LoginActivity.this, CreerCompte1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirPasswordOublie1Activity(){
        Intent intent = new Intent(LoginActivity.this, PasswordOublie1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public static boolean formatEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean emailNonValide(){
        String emailString = email.getText().toString().trim();

        if (!formatEmail(emailString)){
            return false;
        }

        else {
            return true;
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

    public boolean passwordLength(){
        String passwordString = password.getText().toString().trim();

        if (passwordString.length() < 4) {
            return false;
        }

        else {
            return true;
        }
    }

    public void validerFormulaireConnexion(){
        if(!inputObligatoire(email)){
            erreurEmail.setText("L'adresse e-mail est obligatoire..");
        }

        else if(!emailNonValide()){
            erreurEmail.setText("L'adresse e-mail n'est pas valide..");
        }

        else if(!inputObligatoire(password)){
            erreurPassword.setText("Le mot de passe est obligatoire..");
        }

        else if(!passwordLength()){
            erreurPassword.setText("Le mot de passe doit être de 4 caractéres au minimum..");
        }

        else{
            erreurEmail.setText("");
            erreurPassword.setText("");
            verifierConnexionWifiEtServeurAvantAuthentification();
        }
    }

    public void verifierConnexionWifiEtServeurAvantAuthentification(){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(LoginActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(LoginActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        testCompteExiste();
                    }
                }
            }
        },3000);
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(LoginActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void testCompteExiste(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "VerifEmailExist.php?email="+email.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseServeurVerificationEmail(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(LoginActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseServeurVerificationEmail(String reponse){
        if(reponse.trim().equals("Email trouvé")){
            testParametresConnexion();
        }
        else{
            erreurEmail.setText("Aucun compte trouvé avec cette adresse e-mail..");
        }
    }

    public void testParametresConnexion(){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connexion..");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String pagesPhp = new AdresseIp().urlPagesPhp();
                String url = pagesPhp + "TestParametresConnexion.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gestionReponseServeurVerificationAuthentification(response.trim());
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ouvrirConnexionServeurDesactiverActivity(LoginActivity.class);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email.getText().toString());
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
        },3000);
    }

    public void gestionReponseServeurVerificationAuthentification(String reponse){
        if(reponse.trim().equals("Compte trouvé")){
            creerSession();
            ouvrirHomeActivity();
        }

        else {
            afficherCompteNonTrouve();
        }
    }

    public void creerSession(){
        pref = getSharedPreferences("utilisateur",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email",email.getText().toString());
        editor.putString("password",password.getText().toString());
        editor.commit();
    }

    public void ouvrirHomeActivity(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void afficherCompteNonTrouve(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.alerte);
        builder.setMessage("Aucun compte trouvé avec ces informations. Vérifier vos paramétres de connexion et ressayer une autre fois");
        builder.setTitle("Compte non trouvé");
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.alerte);
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