package com.example.eleonetech;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {
    private TextView copyright;
    private ImageView logoSociete;
    private TextView sloganSociete;
    private Animation top, bottom;
    private SharedPreferences pref;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logoSociete = (ImageView) findViewById(R.id.logo_societe_splash);
        sloganSociete = (TextView) findViewById(R.id.slogan_societe_splash);
        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);

        copyright = (TextView) findViewById(R.id.copyright_splash);
        setCopyrightText(copyright);

        animationSplash();
        threadAnimationOuvrirPage();
    }

    public void setCopyrightText(TextView copyright){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        copyright.setText("© " + year + " " + getResources().getString(R.string.societe_copyright));
    }

    public void animationSplash(){
        logoSociete.setAnimation(top);
        sloganSociete.setAnimation(bottom);
    }

    public void threadAnimationOuvrirPage(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pref = getSharedPreferences("utilisateur",MODE_PRIVATE);
                email = pref.getString("email",null);
                testSession(email);
            }
        }, 2500);
    }

    public void testSession(String email){
        if(email == null){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}