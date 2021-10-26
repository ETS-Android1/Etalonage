package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AfficherCertifActivity extends AppCompatActivity {
    private PDFView afficherCertif;
    private String codeEquipement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_certif);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        ItemCertificat itemCertificat = (ItemCertificat) intent.getSerializableExtra("data");
        codeEquipement = itemCertificat.getCode();

        afficherCertif = (PDFView) findViewById(R.id.certificat_afficher_certif);
        getCertificat();
    }

    public void getCertificat(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "AfficherCertif.php?code="+codeEquipement;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionRepnseServeurApresRecuperationCertif(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(CertificatActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionRepnseServeurApresRecuperationCertif(String reponse){
        try{
            JSONObject certifs = new JSONObject(reponse);
            String cer = certifs.getString("certificat");
            setCertificats(cer);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void setCertificats(String certif){
        String pagesPhp = new AdresseIp().urlPagesPhp() + certif;
        new DonwloadPdf().execute(pagesPhp);
    }



    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(AfficherCertifActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(AfficherCertifActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AfficherCertifActivity.this, CertificatActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public class DonwloadPdf extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if(urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            afficherCertif.fromStream(inputStream).load();
        }
    }
}