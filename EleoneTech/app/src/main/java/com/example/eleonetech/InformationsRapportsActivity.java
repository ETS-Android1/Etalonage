package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InformationsRapportsActivity extends AppCompatActivity {
    private String codeEquipement;
    private TextView codeEquipementTitre, codeInfo, descriptionInfo, emplacementInfo, numeroSerieInfo, technicienInfo, societeInfo, remarqueInfo, etatInfo, dateFinInfo, categorieInfo, exigenceInfo, lieuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations_rapports);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        ItemRapport itemRapport = (ItemRapport) intent.getSerializableExtra("data");
        codeEquipement = itemRapport.getCodeEquipement();
        codeEquipementTitre = (TextView) findViewById(R.id.mini_mini_tittre_informations_rapports);

        codeInfo = (TextView) findViewById(R.id.code_equipement_informations_rapports);
        descriptionInfo = (TextView) findViewById(R.id.description_equipement_informations_rapports);
        emplacementInfo = (TextView) findViewById(R.id.emplacement_equipement_informations_rapports);
        numeroSerieInfo = (TextView) findViewById(R.id.numero_serie_equipement_informations_rapports);
        technicienInfo = (TextView) findViewById(R.id.technicien_equipement_informations_rapports);
        societeInfo = (TextView) findViewById(R.id.societe_equipement_informations_rapports);
        remarqueInfo = (TextView) findViewById(R.id.remarque_equipement_informations_rapports);
        etatInfo = (TextView) findViewById(R.id.etat_equipement_informations_rapports);
        dateFinInfo = (TextView) findViewById(R.id.date_fin_equipement_informations_rapports);
        categorieInfo = (TextView) findViewById(R.id.categorie_equipement_informations_rapports);
        exigenceInfo = (TextView) findViewById(R.id.exigence_equipement_informations_rapports);
        lieuInfo = (TextView) findViewById(R.id.lieu_equipement_informations_rapports);
        setCodeEquipementTittre();
        getInformationsRapports();
    }

    public void setCodeEquipementTittre(){
       codeEquipementTitre.setText(codeEquipement);
    }

    public void getInformationsRapports(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsRapports.php?code_equipement="+codeEquipement;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionRepnseServeurApresRecuperationInformations(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ChercherRapportActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(InformationsRapportsActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void gestionRepnseServeurApresRecuperationInformations(String reponse){
        if(reponse.equals("Aucun rapport trouvé")){
            setRapportInformationsVides();
        }

        else{
            try{
                JSONObject rapport = new JSONObject(reponse);
                String description = rapport.getString("description");
                String emplacement = rapport.getString("emplcacement");
                String numSerie = rapport.getString("num_serie");
                String technicien = rapport.getString("technicien");
                String societe = rapport.getString("societe");
                String remarque = rapport.getString("remarque");
                String etat = rapport.getString("etat");
                String date = rapport.getString("date_visite");
                String categorie = rapport.getString("categorie");
                String exigence = rapport.getString("exigence");
                String lieu = rapport.getString("lieu");

                setRapportInformations(description, emplacement, numSerie, technicien, societe,  remarque, etat, date, categorie, exigence, lieu);
            }

            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void setRapportInformationsVides(){
        codeInfo.setText("Vide");
        descriptionInfo.setText("Vide");
        emplacementInfo.setText("Vide");
        numeroSerieInfo.setText("Vide");
        technicienInfo.setText("Vide");
        societeInfo.setText("Vide");
        remarqueInfo.setText("Vide");
        etatInfo.setText("Vide");
        dateFinInfo.setText("Vide");
        categorieInfo.setText("Vide");
        exigenceInfo.setText("Vide");
        lieuInfo.setText("Vide");
    }

    public void setRapportInformations(String description, String emplacement, String numSerie, String technicien, String societe, String remarque, String etat, String date, String categorie, String exigence, String lieu){
        codeInfo.setText(codeEquipement);

        if(description.equals(null)){
            descriptionInfo.setText("Vide");
        }

        else{
            descriptionInfo.setText(description);
        }

        if(emplacement.equals(null)){
            emplacementInfo.setText("Vide");
        }

        else{
            emplacementInfo.setText(emplacement);
        }

        if(numSerie.equals(null)){
            numeroSerieInfo.setText("Vide");
        }

        else{
            numeroSerieInfo.setText(numSerie);
        }

        if(etat.equals(null)){
            etatInfo.setText("Vide");
        }

        else{
            if(etat.equals("V")){
              etatInfo.setText("Visité");
            }
        }

        if(technicien.equals(null)){
            technicienInfo.setText("Vide");
        }

        else{
            technicienInfo.setText(technicien);
        }

        if(societe.equals(null)){
            societeInfo.setText("Vide");
        }

        else{
            societeInfo.setText(societe);
        }

        if(remarque.equals(null)){
            remarqueInfo.setText("Vide");
        }

        else{
            remarqueInfo.setText(remarque);
        }

        if(date.equals("null")){
            dateFinInfo.setText("Vide");
        }

        else{
            dateFinInfo.setText(stylingDateFinValidite(date));
        }

        if(categorie.equals(null)){
            categorieInfo.setText("Vide");
        }

        else{
            categorieInfo.setText(categorie);
        }

        if(exigence.equals(null)){
            exigenceInfo.setText("Vide");
        }

        else{
            exigenceInfo.setText(exigence);
        }

        if(lieu.equals(null)){
            lieuInfo.setText("Vide");
        }

        else{
            lieuInfo.setText(lieu);
        }
    }

    public String stylingDateFinValidite(String dateFinValidite){
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        Date date = null;

        try {
            date = formatter1.parse(dateFinValidite);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter2.format(date);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InformationsRapportsActivity.this, ChercherRapportActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}