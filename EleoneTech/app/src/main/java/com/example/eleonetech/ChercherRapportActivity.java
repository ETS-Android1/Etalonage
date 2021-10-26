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

public class ChercherRapportActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ItemRapport> itemRapports;
    private TextInputEditText chercherRapport;
    private TextView pasRapport;
    private RapportAdapter rapportAdapter;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chercher_rapport);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_chercher_rapport);
        chercherRapport = (TextInputEditText) findViewById(R.id.search_rapport_chercher_rapport);
        pasRapport = (TextView) findViewById(R.id.pas_rapport_chercher_rapport);
        scrollView = (ScrollView) findViewById(R.id.scroll_view_chercher_rapports);
        itemRapports = new ArrayList<>();
        buildRecyclerView();
        getRapports();
    }

    public void buildRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getRapports(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "AfficherListeRapports.php";

        RequestQueue requestQueue = Volley.newRequestQueue(ChercherRapportActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseServeurApresRecuperationRapports(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ChercherRapportActivity.class);
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
        Intent intent = new Intent(ChercherRapportActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(ChercherRapportActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void gestionReponseServeurApresRecuperationRapports(String reponse){
        if(reponse.equals("Aucun rapport trouvé")){
            pasRapport.setText("Vous n'avez pas encore des rappors à afficher..");
        }

        else{
            try {
                JSONObject jsonObject = new JSONObject(reponse);
                JSONArray array = jsonObject.getJSONArray("rapports");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject rapport = array.getJSONObject(i);
                    ItemRapport item = new ItemRapport(
                            rapport.getString("code_equipement"),
                            rapport.getString("date_visite")
                    );

                    itemRapports.add(item);
                }

                rapportAdapter = new RapportAdapter(ChercherRapportActivity.this, itemRapports, new OnClick() {
                    @Override
                    public void onClickSelectedEquipement(ItemEquipements itemEquipements) {

                    }

                    @Override
                    public void onClickSelectedEquipementParMois(ItemEquipementParDate itemEquipements) {

                    }

                    @Override
                    public void onClickSelectedRapport(ItemRapport itemRapport) {
                        verifierConnexionWifiEtServeurAvantAfficherInformationsRapports(itemRapport);
                    }

                    @Override
                    public void onClickSelectedCertif(ItemCertificat itemCertificat) {

                    }
                });

                chercherRapport.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        rapportAdapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ObjectAnimator.ofInt(scrollView, "scrollY", recyclerView.getTop()).setDuration(1500).start();
                    }
                });

                recyclerView.setAdapter(rapportAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifierConnexionWifiEtServeurAvantAfficherInformationsRapports(ItemRapport item){
        final ProgressDialog progressDialog = new ProgressDialog(ChercherRapportActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(ChercherRapportActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(ChercherRapportActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirInformationsRapports(item);
                    }
                }
            }
        },3000);
    }

    public void setAucunRapport(String text){
        pasRapport.setText(text);
    }

    public void setExisteRapport(){
        pasRapport.setText("");
    }

    public void ouvrirInformationsRapports(ItemRapport item){
        Intent intent = new Intent(ChercherRapportActivity.this, InformationsRapportsActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChercherRapportActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}