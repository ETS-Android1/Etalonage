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

public class CertificatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ItemCertificat> itemCertificat;
    private TextInputEditText chercherCertif;
    private TextView pasCertif;
    private CertificatAdapter certificatAdapter;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificat);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_certificat);
        chercherCertif = (TextInputEditText) findViewById(R.id.search_certif_certificats);
        pasCertif = (TextView) findViewById(R.id.pas_certif_certificat);
        scrollView = (ScrollView) findViewById(R.id.scroll_view_certificat);
        itemCertificat = new ArrayList<>();
        buildRecyclerView();
        getCertifs();
    }

    public void buildRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getCertifs(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "AfficherListeCertifs.php";

        RequestQueue requestQueue = Volley.newRequestQueue(CertificatActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseServeurApresRecuperationCertifs(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(CertificatActivity.class);
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
        Intent intent = new Intent(CertificatActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(CertificatActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void gestionReponseServeurApresRecuperationCertifs(String reponse){
        if(reponse.equals("Aucun certificat trouvé")){
            pasCertif.setText("Vous n'avez pas encore des certificats à afficher..");
        }

        else{
            try {
                JSONObject jsonObject = new JSONObject(reponse);
                JSONArray array = jsonObject.getJSONArray("certifs");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject certifs = array.getJSONObject(i);
                    ItemCertificat item = new ItemCertificat(
                            certifs.getString("code_equipement"),
                            certifs.getString("date_certificat"),
                            certifs.getString("certificat")
                    );

                    itemCertificat.add(item);
                }

                certificatAdapter = new CertificatAdapter(CertificatActivity.this, itemCertificat, new OnClick() {
                    @Override
                    public void onClickSelectedEquipement(ItemEquipements itemEquipements) {

                    }

                    @Override
                    public void onClickSelectedEquipementParMois(ItemEquipementParDate itemEquipements) {

                    }

                    @Override
                    public void onClickSelectedRapport(ItemRapport itemRapport) {

                    }

                    @Override
                    public void onClickSelectedCertif(ItemCertificat itemCertificat) {
                        verifierConnexionWifiEtServeurAvantAfficherInformationsCertifs(itemCertificat);
                    }
                });

                chercherCertif.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        certificatAdapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ObjectAnimator.ofInt(scrollView, "scrollY", recyclerView.getTop()).setDuration(1500).start();
                    }
                });

                recyclerView.setAdapter(certificatAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifierConnexionWifiEtServeurAvantAfficherInformationsCertifs(ItemCertificat item){
        final ProgressDialog progressDialog = new ProgressDialog(CertificatActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(CertificatActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(CertificatActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirPageAfficherCertifs(item);
                    }
                }
            }
        },3000);
    }

    public void setAucunCertif(String text){
        pasCertif.setText(text);
    }

    public void setExisteCertif(){
        pasCertif.setText("");
    }

    public void ouvrirPageAfficherCertifs(ItemCertificat item){
        Intent intent = new Intent(CertificatActivity.this, AfficherCertifActivity.class);
        intent.putExtra("data", item);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CertificatActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}