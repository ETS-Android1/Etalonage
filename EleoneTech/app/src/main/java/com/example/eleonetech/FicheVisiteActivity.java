package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FicheVisiteActivity extends AppCompatActivity {
    private TextView currentDate, code, description, emplacement, numeroSerie, dateFinValidite, erreurSociete, erreurTechnicien, erreurRemarques, erreurCategorie;
    private EditText societeEtalonage, technicien, remarques;
    private List<String> exigenceTunac, lieuE, categoryE;
    private AppCompatSpinner category, exigence, lieuEtalonage;
    private String codeEquipementChercher;
    private AppCompatButton validerQrCode;
    private ScrollView scrollView;
    private LinearLayout groupeSociete, groupeTechnicien, groupeRemarques, groupeCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_visite);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        currentDate = (TextView) findViewById(R.id.mini_tittre_fiche_visites);
        setCurentDate();

        category = (AppCompatSpinner) findViewById(R.id.category_fiche_visite);
        exigence = (AppCompatSpinner) findViewById(R.id.exigence_fiche_visite);
        lieuEtalonage = (AppCompatSpinner) findViewById(R.id.lieu_etalonage_fiche_visite);
        remplirListeParReponse();
        setListeReponseToSpinner();

        code = (TextView) findViewById(R.id.code_equipement_fiche_visite);
        description = (TextView) findViewById(R.id.description_equipement_fiche_visite);
        emplacement = (TextView) findViewById(R.id.emplacement_equipement_fiche_visite);
        numeroSerie = (TextView) findViewById(R.id.numero_serie_equipement_fiche_visite);
        dateFinValidite = (TextView) findViewById(R.id.date_fin_validite_equipement_fiche_visite);
        societeEtalonage = (EditText) findViewById(R.id.societe_eleonetage_fiche_visite);
        technicien = (EditText) findViewById(R.id.technicien_fiche_visite);
        remarques = (EditText) findViewById(R.id.remarque_fiche_visite);

        erreurSociete = (TextView) findViewById(R.id.erreur_societe_eleonetage_fiche_visite);
        erreurTechnicien = (TextView) findViewById(R.id.erreur_technicien_fiche_visite);
        erreurRemarques = (TextView) findViewById(R.id.erreur_remarque_fiche_visite);
        erreurCategorie = (TextView) findViewById(R.id.erreur_category_fiche_visite);
        getCodeEquipement();
        getInformationsEquipement();

        societeEtalonage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurSociete.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        technicien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurTechnicien.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        remarques.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                erreurRemarques.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        scrollView = (ScrollView) findViewById(R.id.scroll_view_fiche_visites);
        groupeSociete = (LinearLayout) findViewById(R.id.linear_groupe_societe);
        groupeTechnicien = (LinearLayout) findViewById(R.id.linear_groupe_technicien);
        groupeRemarques = (LinearLayout) findViewById(R.id.linear_groupe_remarques);
        groupeCategorie = (LinearLayout) findViewById(R.id.linear_groupe_categorie);
        validerQrCode = (AppCompatButton) findViewById(R.id.valider_qr_code_fiche_visite);
        validerQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerFormulaireFicheVisite();
            }
        });

    }

    public void setCurentDate(){
        String date = generateCurentDate();
        currentDate.setText(stylingDateFinValidite(date));
    }

    public String generateCurentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public String stylingDateFinValidite(String dateFinValidite){
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date date = null;

        try {
            date = formatter1.parse(dateFinValidite);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter2.format(date);
    }

    public String stylingDateFinValiditeRecuperer(String dateFinValidite){
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date date = null;

        try {
            date = formatter1.parse(dateFinValidite);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter2.format(date);
    }

    public void remplirListeParReponse(){
        exigenceTunac = new ArrayList<>();
        exigenceTunac.add("Oui");
        exigenceTunac.add("Non");

        lieuE = new ArrayList<>();
        lieuE.add("Laboratoire");
        lieuE.add("Usine");

        categoryE = new ArrayList<>();
        categoryE.add("Température");
        categoryE.add("Pesage");
        categoryE.add("Dimensionnel");
        categoryE.add("Electricité");
    }

    public void setListeReponseToSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exigenceTunac){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(13);
                ((TextView) v).setTextColor(getResources().getColor(R.color.bleu));
                return v;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exigence.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lieuE){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(13);
                ((TextView) v).setTextColor(getResources().getColor(R.color.bleu));
                return v;
            }
        };

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lieuEtalonage.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryE){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(13);
                ((TextView) v).setTextColor(getResources().getColor(R.color.bleu));
                return v;
            }
        };

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter3);
    }

    public void getCodeEquipement(){
        Intent intent = getIntent();
        ItemEquipementParDate itemEquipements = (ItemEquipementParDate) intent.getSerializableExtra("data");
        codeEquipementChercher = itemEquipements.getCode();
    }

    public void getInformationsEquipement(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsEquipement.php?code="+codeEquipementChercher;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionRepnseServeurApresRecuperationInformations(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
            }
        });
        requestQueue.add(request);
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
        Intent intent = new Intent(FicheVisiteActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(FicheVisiteActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void gestionRepnseServeurApresRecuperationInformations(String reponce){
        if(reponce.equals("Aucun équipement trouvé")){
            setEquipementInformationsVides();
        }

        else{
            try{
                JSONObject equipement = new JSONObject(reponce);;
                String descriptionEquipement = equipement.getString("description");
                String dateFinEquipement = equipement.getString("date_fin");
                String emplacementEquipement = equipement.getString("emplacement");
                String numSerieEquipement = equipement.getString("numero_serie");

                setEquipementInformations(descriptionEquipement, dateFinEquipement, emplacementEquipement, numSerieEquipement);
            }

            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void setEquipementInformationsVides(){
        code.setText("Vide");
        description.setText("Vide");
        emplacement.setText("Vide");
        numeroSerie.setText("Vide");
        dateFinValidite.setText("Vide");

    }

    public void setEquipementInformations(String descriptionEquipement, String dateFinEquipement, String emplacementEquipement, String numSerieEquipement){
        code.setText(codeEquipementChercher);

        if(descriptionEquipement.equals("null")){
            description.setText("Vide");
        }

        else{
            description.setText(descriptionEquipement);
        }

        if(dateFinEquipement.equals("null")){
            dateFinValidite.setText("Vide");
        }

        else{
            dateFinValidite.setText(stylingDateFinValiditeRecuperer(dateFinEquipement));
        }

        if(emplacementEquipement.equals("null")){
            emplacement.setText("Vide");
        }

        else{
            emplacement.setText(emplacementEquipement);
        }

        if(numSerieEquipement.equals("")){
            numeroSerie.setText("Vide");
        }

        else{
            numeroSerie.setText(numSerieEquipement);
        }
    }

    public boolean inputObligatoire(EditText input){
        String inputString = input.getText().toString().trim();

        if (inputString.isEmpty()) {
            return false;
        }

        else {
            return true;
        }
    }

    public void validerFormulaireFicheVisite(){
        if(!inputObligatoire(societeEtalonage)){
            erreurSociete.setText("La société est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeSociete.getBottom()).setDuration(1500).start();
            societeEtalonage.requestFocus();
        }

        else if(!inputObligatoire(technicien)){
            erreurTechnicien.setText("Le technicien est obligatoire..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeTechnicien.getBottom()).setDuration(1500).start();
            technicien.requestFocus();
        }

        else if(!inputObligatoire(remarques)){
            erreurRemarques.setText("Les remarques sont obligatoires..");
            ObjectAnimator.ofInt(scrollView, "scrollY", groupeRemarques.getBottom()).setDuration(1500).start();
            remarques.requestFocus();
        }

        else{
            verifierConnexionWifiEtServeurAvantScannerCodeQr();
        }
    }

    public void verifierConnexionWifiEtServeurAvantScannerCodeQr(){
        final ProgressDialog progressDialog = new ProgressDialog(FicheVisiteActivity.this, R.style.progress_dialog);
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
                        ouvrirQrCodeScanner();
                    }
                }
            }
        },3000);
    }

    public void ouvrirQrCodeScanner(){
        Intent intent = new Intent(FicheVisiteActivity.this, QrScannerCodeEquipementActivity.class);
        intent.putExtra("code", codeEquipementChercher);
        intent.putExtra("description",description.getText().toString());
        intent.putExtra("emplacement",emplacement.getText().toString());
        intent.putExtra("numeroSerie",numeroSerie.getText().toString());
        intent.putExtra("dateVisite",dateFinValidite.getText().toString());
        intent.putExtra("technicien",technicien.getText().toString());
        intent.putExtra("societe",societeEtalonage.getText().toString());
        intent.putExtra("remarque",remarques.getText().toString());
        intent.putExtra("categorie",category.getSelectedItem().toString());
        intent.putExtra("exigence",exigence.getSelectedItem().toString());
        intent.putExtra("lieu",lieuEtalonage.getSelectedItem().toString());
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FicheVisiteActivity.this, VisitesActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}