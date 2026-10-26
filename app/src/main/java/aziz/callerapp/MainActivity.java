package aziz.callerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Declaration des composants
    EditText ednom , edmp;
    Button btnval, btnqte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperation des composants
        edmp=findViewById(R.id.edmp_auth);   //R pour res
        ednom=findViewById(R.id.ednom_ajout);   // voir remarque 1
        btnval=findViewById(R.id.btnval_auth);
        btnqte=findViewById(R.id.btnqte_auth);


        //ecouteur action

        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();    // ou bien MainActivity.this.finish();
            }
        });

        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom=ednom.getText().toString();
                String mp=edmp.getText().toString();
                
                // Simple authentication - accepts any non-empty name with password "123456"
                if (!nom.isEmpty() && mp.equals("123456")){

                    // sauvegarder la session (rester connecté)
                    SharedPreferences sp = getSharedPreferences("session", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("connected", true);
                    editor.putString("USER", nom);
                    editor.apply();

                    Intent i =new Intent(MainActivity.this,Accueil.class);
                    i.putExtra("USER",nom);
                    startActivity(i);
                    finish();

                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
