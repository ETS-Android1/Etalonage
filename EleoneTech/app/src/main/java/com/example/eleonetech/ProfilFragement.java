package com.example.eleonetech;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragement extends Fragment {
    private AppCompatButton modifierProfil, modifierPassword, deconnexion;
    private CircleImageView imageProfil;
    private SharedPreferences pref;
    private String emailSession;
    private TextView nomPrenom, poste, nom, prenom, email, poste2, numero, adresse, naissance;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    private Bitmap bitmap;
    private ImageView capturePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_profil_home, container, false);

        imageProfil = (CircleImageView) view.findViewById(R.id.photo_profil);
        nomPrenom = (TextView) view.findViewById(R.id.nom_prenom_profil);
        poste = (TextView) view.findViewById(R.id.poste_profil);
        nom = (TextView) view.findViewById(R.id.nom_profil);
        prenom = (TextView) view.findViewById(R.id.prenom_profil);
        email = (TextView) view.findViewById(R.id.email_profil);
        poste2 = (TextView) view.findViewById(R.id.poste_info_profil);
        numero = (TextView) view.findViewById(R.id.mobile_profil);
        adresse = (TextView) view.findViewById(R.id.adresse_profil);
        naissance = (TextView) view.findViewById(R.id.naissance_profil);

        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirGallerie();
            }
        });

        pref = this.getActivity().getSharedPreferences("utilisateur", Context.MODE_PRIVATE);
        emailSession = pref.getString("email",null);

        modifierProfil = (AppCompatButton) view.findViewById(R.id.update_informations_profil);
        modifierProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirModifierProfilActivity();
            }
        });

        modifierPassword = (AppCompatButton) view.findViewById(R.id.update_password_profil);
        modifierPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirModifierPasswordActivity();
            }
        });

        capturePhoto = (ImageView) view.findViewById(R.id.capture_icon_profil);
        capturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifierConnexionWifiEtServeurAvantOuvrirAppareilPhoto();
            }
        });

        deconnexion = (AppCompatButton) view.findViewById(R.id.deconnexion_profil);
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deconnexion();
            }
        });

        getImageProfil();
        getInformationsProfil();

        return view;
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
        Intent intent = new Intent(getActivity(), ConnexionServeurDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void ouvrirConnexionDesactiverActivity(Class<?> ch){
        Intent intent = new Intent(getActivity(), ConnexionDesactiverActivity.class);
        intent.putExtra("activity", ch);
        startActivity(intent);
    }

    public void getImageProfil(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsProfil.php?email="+emailSession;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresRecuperationImageProfil(response.trim(), pagesPhp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(ModifierPasswordProfilActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseApresRecuperationImageProfil(String reponse, String url){
        if(!reponse.equals("Les informations sont indisponibles")){
            try{
                JSONObject utilisateur = new JSONObject(reponse);
                String imageUtilisateur = utilisateur.getString("image");

                setProfilImage(url, imageUtilisateur);
            }

            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void setProfilImage(String url, String image){
        String pathImage = url + image;
        convertStringToImage(pathImage);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void convertStringToImage(String path){
        InputStream in;

        try {
            in = new URL(path).openStream();
            Bitmap bimage = BitmapFactory.decodeStream(in);
            imageProfil.setImageBitmap(getResizedBitmap(bimage, 100));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInformationsProfil(){
        String pagesPhp = new AdresseIp().urlPagesPhp();
        String url = pagesPhp + "GetInformationsProfil.php?email="+emailSession;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gestionReponseApresRecuperationInformationProfil(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
            }
        });
        requestQueue.add(request);
    }

    public void gestionReponseApresRecuperationInformationProfil(String reponse){
        if(reponse.equals("Les informations sont indisponibles")){
            setProfilInformationsVides();
        }

        else{
            try{
                JSONObject utilisateur = new JSONObject(reponse);
                String nomUtilisateur = utilisateur.getString("nom");
                String prenomUtilisateur = utilisateur.getString("prenom");
                String posteUtilisateur = utilisateur.getString("poste");
                String mobileUtilisateur = utilisateur.getString("mobile");
                String adresseUtilisateur = utilisateur.getString("adresse");
                String naissanceUtilisateur = utilisateur.getString("naissance");

                setProfilInformations(nomUtilisateur, prenomUtilisateur, emailSession, posteUtilisateur, mobileUtilisateur, adresseUtilisateur, naissanceUtilisateur);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void setProfilInformationsVides(){
        nomPrenom.setText("Vide");
        poste.setText("(Vide)");
        prenom.setText("Vide");
        nom.setText("Vide");
        email.setText("(Vide)");
        poste2.setText("(Vide)");
        numero.setText("Vide");
        adresse.setText("Vide");
        naissance.setText("Vide");
    }

    public void convertStringToImage(String path, ImageView photo){
        InputStream in;

        try {
            in = new URL(path).openStream();
            Bitmap bimage = BitmapFactory.decodeStream(in);
            photo.setImageBitmap(bimage);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProfilInformations(String nomR, String prenomR, String emailR, String posteR, String numeroR, String adresseR, String naissanceR){
        if(nomR.equals("null")){
            nom.setText("Vide");
        }

        else{
            nom.setText(nomR);
        }

        if(prenomR.equals("null")){
            prenom.setText("Vide");
        }

        else {
            prenom.setText(prenomR);
        }

        email.setText(emailR);

        if(posteR.equals("null")){
            poste.setText("Vide");
            poste2.setText("(Vide)");
        }

        else{
            poste2.setText(posteR);
            poste.setText("("+posteR+")");
        }

        if(numeroR.equals("null")){
            numero.setText("Vide");
        }

        else{
            numero.setText("(+216)" + " " + numeroR);
        }

        if(adresseR.equals("null")){
            adresse.setText("Vide");
        }

        else{
            adresse.setText(adresseR);
        }

        if(naissanceR.equals("null")){
            naissance.setText("Vide");
        }

        else{
           naissance.setText(stylingDateNaissance(naissanceR));
        }

        if(naissanceR.equals("null") && prenomR.equals("null")){
            nomPrenom.setText("Vide");
        }

        else{
            nomPrenom.setText(prenomR + " " + nomR);
        }
    }

    public String stylingDateNaissance(String dateNaissance){
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = formatter1.parse(dateNaissance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter2.format(date);
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirModifierProfilActivity(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
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
                        ouvrirModifierProfilActivity();
                    }
                }
            }
        },3000);
    }

    public void ouvrirModifierProfilActivity(){
        Intent intent = new Intent(getActivity(), ModifierInformationsProfilActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirModifierPasswordActivity(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
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
                        ouvrirModifierPasswordProfilActivity();
                    }
                }
            }
        },3000);
    }

    public void ouvrirModifierPasswordProfilActivity(){
        Intent intent = new Intent(getActivity(), ModifierPasswordProfilActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirAppareilPhoto(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
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
                        ouvrirAppareilPhoto();
                    }
                }
            }
        },3000);
    }

    public void ouvrirAppareilPhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void verifierConnexionWifiEtServeurAvantOuvrirGallerie(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
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
                        ouvrirAlbumPhoto();
                    }
                }
            }
        },3000);
    }

    public void ouvrirAlbumPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0 && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                verifierConnexionWifiEtServeurAvantModifierImageProfil(bitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        else if (resultCode != 0 && requestCode == REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            verifierConnexionWifiEtServeurAvantModifierImageProfil(imageBitmap);
        }
    }

    public void verifierConnexionWifiEtServeurAvantModifierImageProfil(Bitmap image){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Patienter s'il vous plait..");
        progressDialog.show();

        Thread t = new Thread(new Runnable() {
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
                        String pagesPhp = new AdresseIp().urlPagesPhp();
                        String url = pagesPhp + "UpdateImageCompte.php";
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                gestionReponseApresModificationImage(response.trim(), progressDialog, image);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("email", emailSession);
                                String imageData = convertImageToString(image);
                                params.put("image", imageData);
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("Content-Type","application/x-www-form-urlencoded");
                                return params;
                            }
                        };
                        requestQueue.add(request);
                    }
                }
            }
        });
        t.start();
    }

    public String convertImageToString(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void gestionReponseApresModificationImage(String reponse, ProgressDialog progressDialog, Bitmap image){
        if(reponse.equals("Image modifié")){
            imageProfil.setImageBitmap(image);
            progressDialog.dismiss();
        }

        else{
            ouvrirConnexionServeurDesactiverActivity(HomeActivity.class);
            progressDialog.dismiss();
        }
    }

    public void deconnexion(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Déconnexion en cours..");
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
                        fermerSession();
                        ouvriLoginActivity();
                    }
                }
            }
        },3000);
    }

    public void fermerSession(){
        pref = getActivity().getSharedPreferences("utilisateur", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void ouvriLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}