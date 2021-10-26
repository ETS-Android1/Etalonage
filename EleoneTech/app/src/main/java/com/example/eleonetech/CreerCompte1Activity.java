package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import java.net.Socket;

public class CreerCompte1Activity extends AppCompatActivity {
    private TextInputEditText email, password;
    private TextView erreurEmail, erreurPassword;
    private AppCompatButton suivant1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        email = (TextInputEditText) findViewById(R.id.email_creer_compte1);
        password = (TextInputEditText) findViewById(R.id.password_creer_compte1);
        erreurEmail = (TextView) findViewById(R.id.erreur_email_creer_compte1);
        erreurPassword = (TextView) findViewById(R.id.erreur_password_creer_compte1);
        suivant1 = (AppCompatButton) findViewById(R.id.suivant_creer_compte1);

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

        suivant1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                validerFormulaireCreerCompte1();
            }
        });
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

    public void validerFormulaireCreerCompte1(){
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
            verifierConnexionWifiEtServeur();
        }
    }

    public void verifierConnexionWifiEtServeur(){
        final ProgressDialog progressDialog = new ProgressDialog(CreerCompte1Activity.this, R.style.progress_dialog);
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
                        testExistEmail();
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
        Intent intent = new Intent(CreerCompte1Activity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(CreerCompte1Activity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
    }

    public void testExistEmail(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "VerifEmailExist.php?email="+email.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseServeur(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(CreerCompte1Activity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseServeur(String reponse){
        if(reponse.equals("Email trouvé")){
            erreurEmail.setText("Cette adresse e-mail est associé à un autre compte..");
        }
        else{
            erreurEmail.setText("");
            ouvrirCreerCompte2Activity();
        }
    }

    public void ouvrirCreerCompte2Activity(){
        Intent intent = new Intent(CreerCompte1Activity.this, CreerCompte2Activity.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreerCompte1Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}