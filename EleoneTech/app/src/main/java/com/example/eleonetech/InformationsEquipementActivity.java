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

public class InformationsEquipementActivity extends AppCompatActivity {
    private String codeEquipement;
    private TextView codeEquipementTitre, codeInfo, descriptionInfo, dateFinInfo, emplacementInfo, etatInfo, numeroSerieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations_equipement);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        ItemEquipements itemEquipements = (ItemEquipements) intent.getSerializableExtra("data");
        codeEquipement = itemEquipements.getCode();
        codeEquipementTitre = (TextView) findViewById(R.id.mini_mini_tittre_informations_equipements);

        codeInfo = (TextView) findViewById(R.id.code_equipement);
        descriptionInfo = (TextView) findViewById(R.id.description_equipement);
        dateFinInfo = (TextView) findViewById(R.id.date_fin_equipement);
        emplacementInfo = (TextView) findViewById(R.id.emplacement_equipement);
        etatInfo = (TextView) findViewById(R.id.etat_equipement);
        numeroSerieInfo = (TextView) findViewById(R.id.numero_serie_equipement);
        setCodeEquipementTittre();
        getInformationEquipement();
    }

    public void setCodeEquipementTittre(){
        codeEquipementTitre.setText(codeEquipement);
    }

    public void getInformationEquipement(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsEquipement.php?code="+codeEquipement;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionRepnseServeurApresRecuperationInformationsEquipements(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ListeEquipementsActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionRepnseServeurApresRecuperationInformationsEquipements(String reponse){
        if(reponse.equals("Aucun équipement trouvé")){
            setEquipementInformationsVides();
        }

        else{
            try{
                JSONObject equipement = new JSONObject(reponse);;
                String descriptionEquipement = equipement.getString("description");
                String dateFinEquipement = equipement.getString("date_fin");
                String emplacementEquipement = equipement.getString("emplacement");
                String etatEquipement = equipement.getString("etat");
                String numSerieEquipement = equipement.getString("numero_serie");

                setEquipementInformations(descriptionEquipement, dateFinEquipement, emplacementEquipement, etatEquipement, numSerieEquipement);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(InformationsEquipementActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void setEquipementInformationsVides(){
        codeInfo.setText("Vide");
        descriptionInfo.setText("Vide");
        dateFinInfo.setText("Vide");
        emplacementInfo.setText("Vide");
        etatInfo.setText("Vide");
        numeroSerieInfo.setText("Vide");
    }

    public void setEquipementInformations(String descriptionEquipement, String dateFinEquipement, String emplacementEquipement, String etatEquipement, String numSerieEquipement){
        codeInfo.setText(codeEquipement);
        if(descriptionEquipement.equals("null")){
            descriptionInfo.setText("Vide");
        }

        else{
            descriptionInfo.setText(descriptionEquipement);
        }

        if(dateFinEquipement.equals("null")){
            dateFinInfo.setText("Vide");
        }

        else{
            dateFinInfo.setText(stylingDateFinValidite(dateFinEquipement));
        }

        if(emplacementEquipement.equals("null")){
            emplacementInfo.setText("Vide");
        }

        else{
            emplacementInfo.setText(emplacementEquipement);
        }

        if(etatEquipement.equals("null")){
            etatInfo.setText("Vide");
        }

        else{
            if(etatEquipement.equals("V")){
                etatInfo.setText("Visité");
            }

            else{
                etatInfo.setText("Non visité");
            }
        }

        if(numSerieEquipement.equals("")){
            numeroSerieInfo.setText("Vide");
        }

        else{
            numeroSerieInfo.setText(numSerieEquipement);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InformationsEquipementActivity.this, ListeEquipementsActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}