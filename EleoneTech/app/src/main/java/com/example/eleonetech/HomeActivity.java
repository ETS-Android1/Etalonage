package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import java.io.IOException;
import java.net.Socket;

public class HomeActivity extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private SharedPreferences pref;
    private String emailSession;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        chipNavigationBar = (ChipNavigationBar) findViewById(R.id.navigation_bottom);
        if(savedInstanceState == null){
            navbarButtomAuDebut();
            verifierDestination();
        }

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                configurationNavbarButtom(id);
            }
        });
    }

    public void navbarButtomAuDebut(){
        chipNavigationBar.setItemSelected(R.id.container_frame_layout_home, true);
        fragmentManager = getSupportFragmentManager();
        HomeFragement homeFragement = new HomeFragement();
        fragmentManager.beginTransaction().replace(R.id.container_frame_layout_home, homeFragement).commit();
    }

    public void verifierDestination(){
        String ch = null;
        Bundle intent = getIntent().getExtras();
        if(intent == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frame_layout_home, new HomeFragement()).commit();
        }

        else{
            ch = intent.getString("destination");
            if(ch.equals("Profil")){
                ouvrirProfilFragement();
            }
        }
    }

    public void configurationNavbarButtom(int id){
        switch (id){
            case R.id.home_navigation_botom : verifierConnexionWifiEtServeur1();
            break;

            case R.id.aide_navigation_botom : verifierConnexionWifiEtServeur2();
            break;

            case R.id.deconnexion_navigation_botom : verifierConnexionWifiEtServeur3();
            break;

            case R.id.profil_navigation_botom : verifierConnexionWifiEtServeur4();
            break;
        }
    }

    public void verifierConnexionWifiEtServeur1(){
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(HomeActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirHomeFragement();
                    }
                }
            }
        },3000);
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

    public void ouvrirHomeFragement(){
        fragment = new HomeFragement();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.goup, R.anim.godown).replace(R.id.container_frame_layout_home, fragment).commit();
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(HomeActivity.this, ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
        finish();
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

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(HomeActivity.this, ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void verifierConnexionWifiEtServeur2(){
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(HomeActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        ouvrirAideFragement();
                    }
                }
            }
        },3000);
    }

    public void ouvrirAideFragement(){
        fragment = new AideFragement();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.goup, R.anim.godown).replace(R.id.container_frame_layout_home, fragment).commit();
    }

    public void verifierConnexionWifiEtServeur3(){
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(HomeActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
                    }

                    else{
                        progressDialog.dismiss();
                        deconnexion();
                    }
                }
            }
        },3000);
    }

    public void deconnexion(){
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Déconnexion..");
        progressDialog.show();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    if(verifierConnexionWifi() == false){
                        Thread.sleep(3000);
                        progressDialog.dismiss();
                        ouvrirConnexionDesactiverActivity(HomeActivity.class);
                    }

                    else {
                        if(verifierConnexionServeur() == false){
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
                        }

                        else{
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            fermerSession();
                            ouvriLoginActivity();
                        }
                    }
                }
                catch (InterruptedException e) {
                    ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
                }
            }
        });
        t.start();
    }

    public void fermerSession(){
        pref = getSharedPreferences("utilisateur",MODE_PRIVATE);
        emailSession = pref.getString("username",null);
        password = pref.getString("password", null);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void ouvriLoginActivity(){
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void verifierConnexionWifiEtServeur4(){
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this, R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verifierConnexionWifi() == false){
                    progressDialog.dismiss();
                    ouvrirConnexionDesactiverActivity(HomeActivity.class);
                }

                else {
                    if(verifierConnexionServeur() == false){
                        progressDialog.dismiss();
                        ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
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
        fragment = new ProfilFragement();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_frame_layout_home, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.alerte);
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