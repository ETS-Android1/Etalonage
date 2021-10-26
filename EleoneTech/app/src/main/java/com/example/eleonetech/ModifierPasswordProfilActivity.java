package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class ModifierPasswordProfilActivity extends AppCompatActivity {
    private AppCompatButton modifierProfil, compteProfil, modifierPassword;
    private CircleImageView imageProfil;
    private SharedPreferences pref;
    private String emailSession;
    private TextView nomPrenom, poste, erreurAncien, erreurNouveau, erreurConfirm;
    private TextInputEditText ancienPassword, nouveauPassword, confirmerNouveauPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_password_profil);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        modifierProfil = (AppCompatButton) findViewById(R.id.update_informations_modifier_password);
        modifierProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirModifierProfilActivity();
            }
        });

        compteProfil = (AppCompatButton) findViewById(R.id.consulter_profil_modifier_password);
        compteProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirProfilFragement();
            }
        });

        pref = this.getSharedPreferences("utilisateur", Context.MODE_PRIVATE);
        emailSession = pref.getString("email",null);

        imageProfil = (CircleImageView) findViewById(R.id.photo_modifier_password);
        nomPrenom = (TextView) findViewById(R.id.nom_prenom_modifier_password);
        poste = (TextView) findViewById(R.id.poste_modifier_password);
        getImageProfil();
        getInformationsProfil();

        ancienPassword = (TextInputEditText) findViewById(R.id.ancien_password_modifier_password);
        nouveauPassword = (TextInputEditText) findViewById(R.id.nouveau_password_modifier_password);
        confirmerNouveauPassword = (TextInputEditText) findViewById(R.id.confirmer_nouveau_password_modifier_password);
        erreurAncien = (TextView) findViewById(R.id.erreur_ancien_password_modifier_password);
        erreurNouveau = (TextView) findViewById(R.id.erreur_nouveau_password_modifier_password);
        erreurConfirm = (TextView) findViewById(R.id.erreur_confirmer_nouveau_password_modifier_password);

        ancienPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurAncien.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        nouveauPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurNouveau.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        confirmerNouveauPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurConfirm.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        modifierPassword = (AppCompatButton) findViewById(R.id.update_password_modifier_password);
        modifierPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerFormulaireModifierPassword();
            }
        });
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirModifierProfilActivity(){
        final ProgressDialog progressDialog = new ProgressDialog(ModifierPasswordProfilActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ModifierPasswordProfilActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirModifierProfilActivity();
                    }
                }
            }
        },3000);
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

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(ModifierPasswordProfilActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(ModifierPasswordProfilActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirModifierProfilActivity(){
        Intent intent = new Intent(ModifierPasswordProfilActivity.this, ModifierInformationsProfilActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirProfilFragement(){
        final ProgressDialog progressDialog = new ProgressDialog(ModifierPasswordProfilActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ModifierPasswordProfilActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirProfilFragement();

                    }
                }
            }
        },3000);
    }

    public void ouvrirProfilFragement(){
        Intent intent = new Intent(ModifierPasswordProfilActivity.this, HomeActivity.class);
        String classe = "Profil";
        intent.putExtra("destination",classe);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void getImageProfil(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsProfil.php?email="+emailSession;
        RequestQueue requestQueue = Volley.newRequestQueue(ModifierPasswordProfilActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresRecuperationImageProfil(response.trim(), pagesPhp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseApresRecuperationImageProfil(String reponse, String url){
        if(!reponse.equals("Les informations sont indisponibles")){
            try{
                JSONObject utilisateur = new JSONObject(reponse);
                String imageUtilisateur = utilisateur.getString("image");

                setProfilImage(url, imageUtilisateur);
            }

            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void setProfilImage(String url, String image){
        String pathImage = url + image;
        convertStringToImage(pathImage);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void convertStringToImage(String path){
        InputStream in;

        try {
            in = new URL(path).openStream();
            Bitmap bimage = BitmapFactory.decodeStream(in);
            imageProfil.setImageBitmap(getResizedBitmap(bimage, 100));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInformationsProfil(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsProfil.php?email="+emailSession;
        RequestQueue requestQueue = Volley.newRequestQueue(ModifierPasswordProfilActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresRecuperationInformationsProfil(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseApresRecuperationInformationsProfil(String reponse){
        if(reponse.equals("Les informations sont indisponibles")){
            setProfilInformationsVides();
        }

        else{
            try{
                JSONObject utilisateur = new JSONObject(reponse);
                String nomUtilisateur = utilisateur.getString("nom");
                String prenomUtilisateur = utilisateur.getString("prenom");
                String posteUtilisateur = utilisateur.getString("poste");

                setProfilInformations(nomUtilisateur, prenomUtilisateur, posteUtilisateur);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void setProfilInformationsVides(){
        nomPrenom.setText("Vide");
        poste.setText("Vide");
    }

    public void setProfilInformations(String nom, String prenom, String posteR){
        nomPrenom.setText(prenom + " " + nom);
        poste.setText("(" + posteR + ")");
    }

    public boolean passwordObligatoire(TextInputEditText input){
        String passwordString = input.getText().toString().trim();

        if (passwordString.isEmpty()) {
            return false;
        }

        else {
            return true;
        }
    }

    public boolean passwordLength(TextInputEditText input){
        String passwordString = input.getText().toString().trim();

        if (passwordString.length() < 4) {
            return false;
        }

        else {
            return true;
        }
    }

    public boolean verifierEgalitePassword(){
        String nouveauPasswordString = nouveauPassword.getText().toString().trim();
        String confirmerNouveauPasswordString = confirmerNouveauPassword.getText().toString().trim();

        if(!nouveauPasswordString.equals(confirmerNouveauPasswordString)){
            return false;
        }

        else{
            return true;
        }
    }

    public void validerFormulaireModifierPassword(){
        if(!passwordObligatoire(ancienPassword)){
            erreurAncien.setText("Le mot de passe est obligatoire..");
        }

        else if(!passwordLength(ancienPassword)){
            erreurAncien.setText("Le mot de passe doit être de 4 caractéres au minimum..");
        }

        else if(!passwordObligatoire(nouveauPassword)){
            erreurNouveau.setText("Le mot de passe est obligatoire..");
        }

        else if(!passwordLength(nouveauPassword)){
            erreurNouveau.setText("Le mot de passe doit être de 4 caractéres au minimum..");
        }

        else if(!passwordObligatoire(confirmerNouveauPassword)){
            erreurConfirm.setText("Le mot de passe est obligatoire..");
        }

        else if(!passwordLength(confirmerNouveauPassword)){
            erreurConfirm.setText("Le mot de passe doit être de 4 caractéres au minimum..");
        }

        else if(!verifierEgalitePassword()){
            erreurNouveau.setText("Les mots de passes ne sont pas identiques..");
            erreurConfirm.setText("Les mots de passes ne sont pas identiques..");
        }

        else{
            erreurAncien.setText("");
            erreurNouveau.setText("");
            erreurConfirm.setText("");
            verifierAncienPassword();
        }
    }

    public void verifierAncienPassword(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "TestAncienPassword.php?email="+emailSession+"&ancienPassword="+ancienPassword.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(ModifierPasswordProfilActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresRecuperationReponseEtatPassword(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseApresRecuperationReponseEtatPassword(String reponse){
        if(reponse.equals("Mot de passe non vérifié")){
            erreurAncien.setText("Le mot de passe saisie n'est pas correcte..");
        }

        else {
            verifierConnexionWifiEtServeurAvantModifierPassword();
        }
    }

    public void verifierConnexionWifiEtServeurAvantModifierPassword(){
        final ProgressDialog progressDialog = new ProgressDialog(ModifierPasswordProfilActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification de password..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ModifierPasswordProfilActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        modifierPasswordCompte();
                    }
                }
            }
        },3000);
    }

    public void modifierPasswordCompte(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "UpdatePassword.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresChangementPassword(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailSession);
                params.put("password", nouveauPassword.getText().toString());
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

    public void gestionReponseApresChangementPassword(String reponse){
        if(reponse.equals("Password modifié")){
            ouvrirProfilFragement();
        }

        else {
            afficherPasswordNonModifier();
        }
    }

    public void afficherPasswordNonModifier(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifierPasswordProfilActivity.this, R.style.alerte);
        builder.setMessage("Nous avons trouvés des erreurs concernant votre demande de modification de mot de passe. Merci de ressayer ultérieurement");
        builder.setTitle("Mot de passe non modifié");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifierPasswordProfilActivity.this, R.style.alerte);
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