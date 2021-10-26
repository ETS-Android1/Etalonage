package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ListeEquipementsActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private List<ItemEquipements> itemEquipements;
    private TextInputEditText chercherEquipement;
    private TextView pasEquipement;
    private EquipementAdapter equipementAdapter;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_equipements);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        recyclerView = findViewById(R.id.recycler_view_liste_equipement);
        chercherEquipement = findViewById(R.id.search_equipement_liste_equipement);
        pasEquipement = (TextView) findViewById(R.id.pas_equipement_liste_equipement);
        scrollView = (ScrollView) findViewById(R.id.scroll_view_liste_equipement);
        itemEquipements = new ArrayList<>();
        buildRecyclerView();
        getEquipements();
    }

    public void buildRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getEquipements(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "AfficherListeCodeEquipement.php";

        RequestQueue requestQueue = Volley.newRequestQueue(ListeEquipementsActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseServeurApresRecuperationEquipements(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ListeEquipementsActivity.class);
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
        Intent intent = new Intent(ListeEquipementsActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(ListeEquipementsActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }


    public void gestionReponseServeurApresRecuperationEquipements(String reponse){
        if(reponse.equals("Aucun équipement trouvé")){
            pasEquipement.setText("Vous n'avez pas encore des équipements à afficher..");
        }

        else{
            try {
                JSONObject jsonObject = new JSONObject(reponse);
                JSONArray array = jsonObject.getJSONArray("equipements");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject equipement = array.getJSONObject(i);
                    ItemEquipements item = new ItemEquipements(
                            equipement.getString("Code_equipement"),
                            equipement.getString("Etat")
                    );

                    itemEquipements.add(item);
                }

                equipementAdapter = new EquipementAdapter(ListeEquipementsActivity.this, itemEquipements, new OnClick() {
                    @Override
                    public void onClickSelectedEquipement(ItemEquipements itemEquipements) {
                        verifierConnexionWifiEtServeurAvantAfficherInformationsEquipements(itemEquipements);
                    }

                    @Override
                    public void onClickSelectedEquipementParMois(ItemEquipementParDate itemEquipements) {

                    }

                    @Override
                    public void onClickSelectedRapport(ItemRapport itemRapport) {

                    }

                    @Override
                    public void onClickSelectedCertif(ItemCertificat itemCertificat) {

                    }
                });

                chercherEquipement.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        equipementAdapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ObjectAnimator.ofInt(scrollView, "scrollY", recyclerView.getTop()).setDuration(1500).start();
                    }
                });

                recyclerView.setAdapter(equipementAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifierConnexionWifiEtServeurAvantAfficherInformationsEquipements(ItemEquipements item){
        final ProgressDialog progressDialog = new ProgressDialog(ListeEquipementsActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ListeEquipementsActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ListeEquipementsActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirInformationsEquipementActivity(item);
                    }
                }
            }
        },3000);
    }

    public void setAucunEquipement(String text){
        pasEquipement.setText(text);
    }

    public void setEquipementInformationVide(){
        pasEquipement.setText("");
    }


    public void ouvrirInformationsEquipementActivity(ItemEquipements item){
        Intent intent = new Intent(ListeEquipementsActivity.this, InformationsEquipementActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListeEquipementsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}