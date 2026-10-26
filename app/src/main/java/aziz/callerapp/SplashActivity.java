package aziz.callerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("session", Context.MODE_PRIVATE);
                boolean connected = sp.getBoolean("connected", false);
                String user = sp.getString("USER", "");

                Intent i;
                if (connected) {
                    i = new Intent(SplashActivity.this, Accueil.class);
                    i.putExtra("USER", user);
                } else {
                    i = new Intent(SplashActivity.this, MainActivity.class);  // ton écran de login
                }
                startActivity(i);
                finish();
            }
        }, 2500);
    }
}
