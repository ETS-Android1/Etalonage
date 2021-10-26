package com.example.eleonetech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VisitesActivity extends AppCompatActivity {
    private AppCompatSpinner mois;
    private List<String> moisNames;
    private AppCompatButton rechercherEquipements;
    private TextView erreurMois, pasVisite, aucunVisite;
    private ImageView pageNotFound;
    private RecyclerView recyclerView;
    private List<ItemEquipementParDate> itemEquipementsParDates;
    private EquipementParMoisAdapter equipementParMoisAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visites);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mois = (AppCompatSpinner) findViewById(R.id.liste_mois_visites);
        remplirListeParMois();
        setListeMoisToSpinner();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_liste_visite);
        erreurMois = (TextView) findViewById(R.id.erreur_spinner_visites);
        pageNotFound = (ImageView) findViewById(R.id.icon_404_not_found_visite);
        aucunVisite = (TextView) findViewById(R.id.aucun_equipement_visite);
        rechercherEquipements = (AppCompatButton) findViewById(R.id.rechercher_equipement_visites);
        rechercherEquipements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerSpinnerObligatoire();
            }
        });

        mois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAucunMoisSelectionner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pasVisite = (TextView) findViewById(R.id.pas_visite_afficher_visite);
        setPasVisite();
    }

    public void remplirListeParMois(){
        moisNames = new ArrayList<>();
        moisNames.add("Choisissez un mois..");
        moisNames.add("Janvier");
        moisNames.add("Février");
        moisNames.add("Mars");
        moisNames.add("Avril");
        moisNames.add("Mai");
        moisNames.add("Juin");
        moisNames.add("Juillet");
        moisNames.add("Aout");
        moisNames.add("Septembre");
        moisNames.add("Octobre");
        moisNames.add("Novembre");
        moisNames.add("Décembre");
    }

    public void setListeMoisToSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, moisNames){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                TextView textview = (TextView) v;
                if (position == 0) {
                    textview.setTextColor(Color.GRAY);
                } else {
                    textview.setTextColor(Color.BLACK);
                }
                return v;
            }

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(14);
                return v;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mois.setAdapter(adapter);
    }

    public void validerSpinnerObligatoire(){
        if(mois.getSelectedItemPosition() == 0){
            erreurMois.setText("Aucun mois séléctionné..");
            mois.requestFocus();
        }

        else{
            pasVisite.setText("");
            if(itemEquipementsParDates != null){
                clearRecyclerView();
                clearDataNotFound();
                verifierConnexionWifiEtServeurAvantAfficherInformations();
            }

            else{
                verifierConnexionWifiEtServeurAvantAfficherInformations();
            }
        }
    }

    public void setAucunMoisSelectionner(){
        erreurMois.setText("");
    }

    public void verifierConnexionWifiEtServeurAvantAfficherInformations(){
        final ProgressDialog progressDialog = new ProgressDialog(VisitesActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(VisitesActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        itemEquipementsParDates = new ArrayList<>();
                        buildRecyclerView();
                        getInformationsVisites();
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
        Intent intent = new Intent(VisitesActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(VisitesActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void buildRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(VisitesActivity.this));
    }

    public String sortMoisSelectionner(){
        String choix = "";
        int spinner = mois.getSelectedItemPosition();

        switch (spinner){
            case 1 : choix = "01";
            break;

            case 2 : choix = "02";
            break;

            case 3 : choix = "03";
            break;

            case 4 : choix = "04";
            break;

            case 5 : choix = "05";
            break;

            case 6 : choix = "06";
            break;

            case 7 : choix = "07";
            break;

            case 8 : choix = "08";
            break;

            case 9 : choix = "09";
            break;

            case 10 : choix = "10";
            break;

            case 11 : choix = "11";
            break;

            case 12 : choix = "12";
            break;

            default : choix = "01";
            break;
        }
        return choix;
    }

    public void getInformationsVisites(){
        String choix = sortMoisSelectionner();
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetEquipementParDate.php?date="+choix;
        RequestQueue requestQueue = Volley.newRequestQueue(VisitesActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseServeurApresRecuperationInformations(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
            }
        });
        requestQueue.add(request);

    }

    public void gestionReponseServeurApresRecuperationInformations(String reponse){
        if(reponse.equals("Aucun équipement trouvé")){
            setAucunVisiteTrouver();
        }

        else{
            try {
                JSONObject jsonObject = new JSONObject(reponse);
                JSONArray array = jsonObject.getJSONArray("equipements");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject equipement = array.getJSONObject(i);
                    ItemEquipementParDate item = new ItemEquipementParDate(
                            equipement.getString("Code_equipement"),
                            equipement.getString("Description_equipement"),
                            stylingDateFinValidite(equipement.getString("Date_fin_validite"))
                    );

                    itemEquipementsParDates.add(item);
                }

                equipementParMoisAdapter = new EquipementParMoisAdapter(VisitesActivity.this, itemEquipementsParDates, new OnClick() {
                    @Override
                    public void onClickSelectedEquipement(ItemEquipements itemEquipements) {

                    }

                    @Override
                    public void onClickSelectedEquipementParMois(ItemEquipementParDate itemEquipements) {
                        verifierConnexionWifiEtServeurAvantOuvrirFicheVisite(itemEquipements);
                    }

                    @Override
                    public void onClickSelectedRapport(ItemRapport itemRapport) {

                    }

                    @Override
                    public void onClickSelectedCertif(ItemCertificat itemCertificat) {

                    }
                });

                recyclerView.setAdapter(equipementParMoisAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String stylingDateFinValidite(String dateFinValidite){
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

    public void verifierConnexionWifiEtServeurAvantOuvrirFicheVisite(ItemEquipementParDate item){
        final ProgressDialog progressDialog = new ProgressDialog(VisitesActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(VisitesActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirFicheVisiteActivity(item);
                    }
                }
            }
        },3000);
    }

    public void ouvrirFicheVisiteActivity(ItemEquipementParDate item){
        Intent intent = new Intent(VisitesActivity.this, FicheVisiteActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void setPasVisite(){
        pasVisite.setText("Vous n'avez pas encore choisissez un mois..");
    }

    public void setAucunVisiteTrouver(){
        aucunVisite.setText("Aucune visites trouvée pour ce mois");
        pageNotFound.setBackgroundResource(R.drawable.page_not_found);
        pageNotFound.getLayoutParams().height = 200;
    }

    public void clearRecyclerView(){
        itemEquipementsParDates.clear();
    }

    public void clearDataNotFound(){
        aucunVisite.setText("");
        pageNotFound.setBackgroundResource(0);
        pageNotFound.getLayoutParams().height = 0;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VisitesActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}