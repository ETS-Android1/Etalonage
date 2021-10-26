package com.example.eleonetech;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerCodeEquipementActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView scannerView;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (ContextCompat.checkSelfPermission(QrScannerCodeEquipementActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(QrScannerCodeEquipementActivity.this, new String[]{Manifest.permission.CAMERA}, 123);
        }
    }

    @Override
    public void handleResult(Result result) {
        code = getIntent().getStringExtra("code");

        if(!result.getText().equals(code)){
            ouvrirQrCodeIncorrecteActivity();
        }

        else{
            ouvrirResultatQrCodeScanner();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public void ouvrirQrCodeIncorrecteActivity(){
        Intent intent = new Intent(QrScannerCodeEquipementActivity.this, QrCodeIncorrecteActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirResultatQrCodeScanner(){
        Intent intent = new Intent(QrScannerCodeEquipementActivity.this, ResultatQrCodeScannerActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("description",getIntent().getStringExtra("description"));
        intent.putExtra("emplacement",getIntent().getStringExtra("emplacement"));
        intent.putExtra("numeroSerie",getIntent().getStringExtra("numeroSerie"));
        intent.putExtra("dateVisite",getIntent().getStringExtra("dateVisite"));
        intent.putExtra("societe",getIntent().getStringExtra("societe"));
        intent.putExtra("technicien",getIntent().getStringExtra("technicien"));
        intent.putExtra("remarque",getIntent().getStringExtra("remarque"));
        intent.putExtra("categorie",getIntent().getStringExtra("categorie"));
        intent.putExtra("exigence",getIntent().getStringExtra("exigence"));
        intent.putExtra("lieu",getIntent().getStringExtra("lieu"));
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}