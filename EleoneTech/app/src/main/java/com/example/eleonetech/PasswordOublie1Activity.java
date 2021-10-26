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
import java.util.Random;

public class PasswordOublie1Activity extends AppCompatActivity {
    private TextInputEditText email;
    private TextView erreurEmail, loginPage;
    private AppCompatButton envoyerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_oublie1);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        email = (TextInputEditText) findViewById(R.id.email_password_oublie1);
        erreurEmail = (TextView) findViewById(R.id.erreur_email_password_oublie1);
        loginPage = (TextView) findViewById(R.id.login_password_oublie1);
        envoyerCode = (AppCompatButton) findViewById(R.id.envoyer_code_password_oublie1);

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur();
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

        envoyerCode.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                validerFormulairePasswordOublie1();
            }
        });
    }

    public void verifierConnexionWifiEtServeur(){
        final ProgressDialog progressDialog = new ProgressDialog(PasswordOublie1Activity.this, R.style.progress_dialog);
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
                        ouvrirLoginActivity();
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

    public void ouvrirLoginActivity(){
        Intent intent = new Intent(PasswordOublie1Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(PasswordOublie1Activity.this, ConnexionDesactiverActivity.class);
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
        Intent intent = new Intent(PasswordOublie1Activity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
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

    public void validerFormulairePasswordOublie1() {
        if (!inputObligatoire(email)) {
            erreurEmail.setText("L'adresse e-mail est obligatoire..");
        }

        else if (!emailNonValide()) {
            erreurEmail.setText("L'adresse e-mail n'est pas valide..");
        }

        else {
            erreurEmail.setText(null);
            verifierConnexionWifiEtServeurAvantRecuperation();
        }
    }

    public void verifierConnexionWifiEtServeurAvantRecuperation(){
        final ProgressDialog progressDialog = new ProgressDialog(PasswordOublie1Activity.this, R.style.progress_dialog);
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
                        testEmailExiste();
                    }
                }
            }
        },3000);
    }

    public void testEmailExiste(){
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
                ouvrirConnexionServeurDesactiverActivity(PasswordOublie1Activity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseServeurVerificationEmail(String responce){
        if(responce.equals("Email trouvé")){
            erreurEmail.setText("");
            envoieCodeSecurite();
        }
        else{
            erreurEmail.setText("Aucun compte trouvé avec cette adresse e-mail..");
        }
    }

    public int generateCodeSecurite(){
        Random random = new Random();
        int codeSecurite = random.nextInt(999999 - 111111) + 111111;
        return codeSecurite;
    }

    public void envoieCodeSecurite(){
        ProgressDialog progressDialog = new ProgressDialog(PasswordOublie1Activity.this, R.style.progress_dialog_bleu_ciel);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Envoi de code de sécurité en cours..");
        progressDialog.show();

        Thread t = new Thread(new Runnable() {
            public void run() {
                String codeSecurite = String.valueOf(generateCodeSecurite());
                String pagesPhp = new AdresseIp().urlPagesPhp();
                String url = pagesPhp + "EnvoyerCodeSecuriter.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gestionReponseServeurEnvoieCodeDeSecurite(response.trim(), codeSecurite);
                        progressDialog.dismiss();
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
                        params.put("email",email.getText().toString());
                        params.put("code",codeSecurite);
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

    public void gestionReponseServeurEnvoieCodeDeSecurite(String reponse, String codeSecurite){
        if(reponse.equals("Code de sécurité envoyé")){
            ouvriPasswordOublie2Activity(codeSecurite);
        }

        else{
            ouvrirConnexionServeurDesactiverActivity(PasswordOublie1Activity.class);
        }
    }

    public void ouvriPasswordOublie2Activity(String code){
        Intent intent = new Intent(PasswordOublie1Activity.this, PasswordOublie2Activity.class);
        intent.putExtra("email",email.getText().toString());
        intent.putExtra("codeSecurite",code);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PasswordOublie1Activity.this, R.style.alerte_bleu_ciel);
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