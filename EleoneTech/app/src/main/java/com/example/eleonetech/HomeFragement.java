package com.example.eleonetech;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.net.Socket;

public class HomeFragement extends Fragment {
    private CardView equipements, visites, rapports, etalonage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_dashboard_home, container, false);
        equipements = (CardView) view.findViewById(R.id.card_view_equipements);

        equipements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur1();
            }
        });

        visites = (CardView) view.findViewById(R.id.card_view_visites);
        visites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur2();
            }
        });

        rapports = (CardView) view.findViewById(R.id.card_rapports);
        rapports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur3();
            }
        });

        etalonage = (CardView) view.findViewById(R.id.card_view_etalonage);
        etalonage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeur4();
            }
        });
        return view;
    }

    public void verifierConnexionWifiEtServeur1(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    if(verifierConnexionWifi() == false){
                        Thread.sleep(3000);
                        progressDialog.dismiss();
                        ouvrirConnexionDesactiverActivity(VisitesActivity.class);
                    }

                    else {
                        if(verifierConnexionServeur() == false){
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
                        }

                        else{
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirListeEquipementsActivity();
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

    public boolean testConnexionOuvert(){
        boolean wifiConnected;
        boolean internet = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public void ouvrirListeEquipementsActivity(){
        Intent intent = new Intent(getActivity(), ListeEquipementsActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(getActivity(), ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionServeurDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(getActivity(), ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }


    public void verifierConnexionWifiEtServeur2(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    if(verifierConnexionWifi() == false){
                        Thread.sleep(3000);
                        progressDialog.dismiss();
                        ouvrirConnexionDesactiverActivity(VisitesActivity.class);
                    }

                    else {
                        if(verifierConnexionServeur() == false){
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
                        }

                        else{
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirVisitesActivity();
                        }
                    }
                }
                catch (InterruptedException e) {
                    ouvrirConnexionServeurDesactiverActivity(VisitesActivity.class);
                }
            }
        });
        t.start();
    }

    public void verifierConnexionWifiEtServeur3(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    if(verifierConnexionWifi() == false){
                        Thread.sleep(3000);
                        progressDialog.dismiss();
                        ouvrirConnexionDesactiverActivity(ChercherRapportActivity.class);
                    }

                    else {
                        if(verifierConnexionServeur() == false){
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirConnexionServeurDesactiverActivity(ChercherRapportActivity.class);
                        }

                        else{
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirRapportsActivity();
                        }
                    }
                }
                catch (InterruptedException e) {
                    ouvrirConnexionServeurDesactiverActivity(ChercherRapportActivity.class);
                }
            }
        });
        t.start();
    }

    public void verifierConnexionWifiEtServeur4(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    if(verifierConnexionWifi() == false){
                        Thread.sleep(3000);
                        progressDialog.dismiss();
                        ouvrirConnexionDesactiverActivity(CertificatActivity.class);
                    }

                    else {
                        if(verifierConnexionServeur() == false){
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirConnexionServeurDesactiverActivity(CertificatActivity.class);
                        }

                        else{
                            Thread.sleep(3000);
                            progressDialog.dismiss();
                            ouvrirEtalonageActivity();
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

    public void ouvrirVisitesActivity(){
        Intent intent = new Intent(getActivity(), VisitesActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirRapportsActivity(){
        Intent intent = new Intent(getActivity(), ChercherRapportActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirEtalonageActivity(){
        Intent intent = new Intent(getActivity(), CertificatActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}
