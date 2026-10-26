package aziz.callerapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {
    public static ArrayList<Contact> data = new ArrayList<>();

    TextView tvusername;
    Button btnajout , btnaff,btndec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        tvusername=findViewById(R.id.tvuser_acc);
        btnajout=findViewById(R.id.btnajout_acc);
        btnaff=findViewById(R.id.btnaff_acc);
        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Accueil.this , Ajout.class);
                startActivity(i);
            }
        });
        btnaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Accueil.this , Affiche.class);
                startActivity(i);
            }
        });
        Intent x=this.getIntent();
        SharedPreferences sp = getSharedPreferences("session", Context.MODE_PRIVATE);
        String u = sp.getString("USER", "");
        tvusername.setText("Accueil de Mr. " + u);
        btndec = findViewById(R.id.btndec_acc);
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("session", Context.MODE_PRIVATE);
                sp.edit().clear().apply();

                Intent i = new Intent(Accueil.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
