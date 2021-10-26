package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class ModifierInformationsProfilActivity extends AppCompatActivity {
    private AppCompatButton modifierPassword, compteProfil, modifierInformations;
    private CircleImageView imageProfil;
    private SharedPreferences pref;
    private String emailSession;
    private TextView nomPrenom, poste, erreurPrenom, erreurNom, erreurPoste, erreurNumero, erreurAdresse, erreurNaissance;
    private TextInputEditText prenom, nom, email, poste2, numero, adresse, naissance;
    private DatePickerDialog.OnDateSetListener date;
    private ScrollView scrollView;
    private LinearLayout groupePrenom, groupeNom, groupePoste, groupeNumero, groupeAdresse, groupeNaissance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_informations_profil);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        modifierPassword = (AppCompatButton) findViewById(R.id.update_password_modifier_password);
        modifierPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirModifierPasswordActivity();
            }
        });

        compteProfil = (AppCompatButton) findViewById(R.id.consulter_profil_modifier_profil);
        compteProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirProfilFragement();
            }
        });

        pref = this.getSharedPreferences("utilisateur", Context.MODE_PRIVATE);
        emailSession = pref.getString("email",null);

        imageProfil = (CircleImageView) findViewById(R.id.photo_modifier_profil);
        nomPrenom = (TextView) findViewById(R.id.nom_prenom_modifier_profil);
        poste = (TextView) findViewById(R.id.poste_modifier_profil);
        getImageProfil();

        prenom = (TextInputEditText) findViewById(R.id.prenom_modifier_profil);
        nom = (TextInputEditText) findViewById(R.id.nom_modifier_profil);
        email = (TextInputEditText) findViewById(R.id.email_modifier_profil);
        poste2 = (TextInputEditText) findViewById(R.id.poste2_modifier_profil);
        numero = (TextInputEditText) findViewById(R.id.mobile_modifier_profil);
        adresse = (TextInputEditText) findViewById(R.id.adresse_modifier_profil);
        naissance = (TextInputEditText) findViewById(R.id.naissance_modifier_profil);
        getInformationsProfil();

        naissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurationNaissanceInput();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setNaissanceParametres(year, month, dayOfMonth);
            }
        };

        erreurPrenom = (TextView) findViewById(R.id.erreur_prenom_modifier_profil);
        erreurNom = (TextView) findViewById(R.id.erreur_nom_modifier_profil);
        erreurPoste = (TextView) findViewById(R.id.erreur_poste_modifier_profil);
        erreurNumero = (TextView) findViewById(R.id.erreur_mobile_modifier_profil);
        erreurAdresse = (TextView) findViewById(R.id.erreur_adresse_modifier_profil);

        prenom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurPrenom.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        nom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurNom.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        poste2.addTextChangedListener(new TextWatcher() {
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

        scrollView = (ScrollView) findViewById(R.id.scroll_view_modifier_informations);
        groupePrenom = (LinearLayout) findViewById(R.id.groupe_prenom);
        groupeNom = (LinearLayout) findViewById(R.id.groupe_nom);
        groupePoste = (LinearLayout) findViewById(R.id.groupe_poste);
        groupeNumero = (LinearLayout) findViewById(R.id.groupe_mobile);
        groupeAdresse = (LinearLayout) findViewById(R.id.groupe_adresse);
        groupeNaissance = (LinearLayout) findViewById(R.id.groupe_naissance);
        modifierInformations = (AppCompatButton) findViewById(R.id.update_profil_modifier_profil);
        modifierInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerFormulaireModifierInformations();
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
        Intent intent = new Intent(ModifierInformationsProfilActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(ModifierInformationsProfilActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirModifierPasswordActivity(){
        final ProgressDialog progressDialog = new ProgressDialog(ModifierInformationsProfilActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ModifierInformationsProfilActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ModifierInformationsProfilActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirModifierPasswordActivity();
                    }
                }
            }
        },3000);
    }

    public void ouvrirModifierPasswordActivity(){
        Intent intent = new Intent(ModifierInformationsProfilActivity.this, ModifierPasswordProfilActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirProfilFragement(){
        final ProgressDialog progressDialog = new ProgressDialog(ModifierInformationsProfilActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ModifierInformationsProfilActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ModifierInformationsProfilActivity.class);
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
        Intent intent = new Intent(ModifierInformationsProfilActivity.this, HomeActivity.class);
        String classe = "Profil";
        intent.putExtra("destination",classe);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void getImageProfil(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsProfil.php?email="+emailSession;
        RequestQueue requestQueue = Volley.newRequestQueue(ModifierInformationsProfilActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresRecuperationImageProfil(response.trim(), pagesPhp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierInformationsProfilActivity.class);
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
        RequestQueue requestQueue = Volley.newRequestQueue(ModifierInformationsProfilActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresRecuperationInformationsProfil(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierInformationsProfilActivity.class);
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
                String mobileUtilisateur = utilisateur.getString("mobile");
                String adresseUtilisateur = utilisateur.getString("adresse");
                String naissanceUtilisateur = utilisateur.getString("naissance");

                setProfilInformations(nomUtilisateur, prenomUtilisateur, emailSession, posteUtilisateur, mobileUtilisateur, adresseUtilisateur, naissanceUtilisateur);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void setProfilInformationsVides(){
        nomPrenom.setText("Vide");
        poste.setText("(Vide)");
        prenom.setText("Vide");
        nom.setText("Vide");
        email.setText(emailSession);
        poste2.setText("Vide");
        numero.setText("Vide");
        adresse.setText("Vide");
        naissance.setText("Vide");
    }

    public void setProfilInformations(String nomR, String prenomR, String emailR, String posteR, String numeroR, String adresseR, String naissanceR){
        if(nomR.equals("null")){
            nom.setText("Vide");
        }

        else{
            nom.setText(nomR);
        }

        if(prenomR.equals("null")){
            prenom.setText("Vide");
        }

        else {
            prenom.setText(prenomR);
        }

        email.setText(emailR);

        if(posteR.equals("null")){
            poste.setText("Vide");
            poste2.setText("(Vide)");
        }

        else{
            poste2.setText(posteR);
            poste.setText("("+posteR+")");
        }

        if(numeroR.equals("null")){
            numero.setText("Vide");
        }

        else{
            numero.setText(numeroR);
        }

        if(adresseR.equals("null")){
            adresse.setText("Vide");
        }

        else{
            adresse.setText(adresseR);
        }

        if(naissanceR.equals("null")){
            naissance.setText("Vide");
        }

        else{
            naissance.setText(stylingDateNaissance(naissanceR));
        }

        if(naissanceR.equals("null") && prenomR.equals("null")){
            nomPrenom.setText("Vide");
        }

        else{
            nomPrenom.setText(prenomR + " " + nomR);
        }
    }

    public void configurationNaissanceInput(){
        Calendar agenda = Calendar.getInstance();
        int year = agenda.get(Calendar.YEAR);
        int month = agenda.get(Calendar.MONTH);
        int day = agenda.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(ModifierInformationsProfilActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, date, year , month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void setNaissanceParametres(int year, int month, int day){
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

    public String stylingDateNaissance(String dateNaissance){
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = formatter1.parse(dateNaissance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter2.format(date);
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

    private boolean numeroLength(TextInputEditText numero){
        String numeroString = numero.getText().toString().trim();
        if (numeroString.length() != 8){
            return false;
        }

        else{
            return true;
        }
    }

    public void validerFormulaireModifierInformations(){
        if(!inputObligatoire(prenom)){
            erreurPrenom.setText("Le prénom est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupePrenom.getBottom()).setDuration(1500).start();
            prenom.requestFocus();
        }

        else if(!inputChaine(prenom)){
            erreurPrenom.setText("Le prénom n'est pas valide..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupePrenom.getBottom()).setDuration(1500).start();
            prenom.requestFocus();
        }

        else if(!inputObligatoire(nom)){
            erreurNom.setText("Le nom est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeNom.getBottom()).setDuration(1500).start();
            nom.requestFocus();
        }

        else if(!inputChaine(nom)){
            erreurNom.setText("Le nom n'est pas valide..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeNom.getBottom()).setDuration(1500).start();
            nom.requestFocus();
        }

        else if(!inputObligatoire(poste2)){
            erreurPoste.setText("Le poste est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupePoste.getBottom()).setDuration(1500).start();
            poste2.requestFocus();
        }

        else if(!inputChaine(poste2)){
            erreurPoste.setText("Le poste n'est pas valide..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupePoste.getBottom()).setDuration(1500).start();
            poste2.requestFocus();
        }

        else if(!inputObligatoire(numero)){
            erreurNumero.setText("Le numéro est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeNumero.getBottom()).setDuration(1500).start();
            numero.requestFocus();
        }

        else if(!numeroLength(numero)){
            erreurNumero.setText("Le numéro mobile doit être de 8 chiffres..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeNumero.getBottom()).setDuration(1500).start();
            numero.requestFocus();
        }

        else if(!inputObligatoire(adresse)){
            erreurAdresse.setText("L'adresse est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeAdresse.getBottom()).setDuration(1500).start();
            adresse.requestFocus();
        }

        else if(!inputObligatoire(naissance)){
            erreurNaissance.setText("Le date de naissance est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeNaissance.getBottom()).setDuration(1500).start();
            naissance.requestFocus();
        }

        else{
            verifierConnexionWifiEtServeurAvantModifierInformations();
        }
    }

    public void verifierConnexionWifiEtServeurAvantModifierInformations(){
        final ProgressDialog progressDialog = new ProgressDialog(ModifierInformationsProfilActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification de profil..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ModifierInformationsProfilActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ModifierInformationsProfilActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        modifierInformationsProfil();
                    }
                }
            }
        },3000);
    }

    public void modifierInformationsProfil(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "UpdateInformationsCompte.php";
        RequestQueue requestQueue = Volley.newRequestQueue(ModifierInformationsProfilActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresModificationCompte(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierInformationsProfilActivity.class);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailSession);
                params.put("nom", nom.getText().toString());
                params.put("prenom", prenom.getText().toString());
                params.put("poste", poste2.getText().toString());
                params.put("numero", numero.getText().toString());
                params.put("adresse", adresse.getText().toString());
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

    public void gestionReponseApresModificationCompte(String reponse){
        if(reponse.equals("Profil modifié")){
            ouvrirProfilFragement();
        }

        else{
            afficherProfilNonModifier();
        }
    }

    public void afficherProfilNonModifier(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifierInformationsProfilActivity.this, R.style.alerte);
        builder.setMessage("Nous avons trouvés des erreurs concernant votre demande de modification de profil. Merci de ressayer ultérieurement");
        builder.setTitle("Profil non modifié");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifierInformationsProfilActivity.this, R.style.alerte);
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