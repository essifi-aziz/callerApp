package aziz.callerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ajout extends AppCompatActivity {

    EditText ednom_ajout , edpseudo_ajout , ednum_ajout ;
    Button btn_ajouter , btn_annuler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajout);
        ednom_ajout=findViewById(R.id.ednom_ajout);
        edpseudo_ajout=findViewById(R.id.edpseudo_ajout);
        ednum_ajout=findViewById(R.id.ednum_ajout);
        btn_ajouter=findViewById(R.id.btn_ajout);
        btn_annuler=findViewById(R.id.btn_annuler);
        btn_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ednom_ajout.getText().toString();
                String pseudo = edpseudo_ajout.getText().toString();
                String number = ednum_ajout.getText().toString();   // corrigé

                if (nom.isEmpty() || number.isEmpty()) {
                    Toast.makeText(Ajout.this, "Nom et numéro obligatoires", Toast.LENGTH_LONG).show();
                    return;
                }

                ContactManager manager = new ContactManager(Ajout.this);
                manager.ouvrir();
                long id = manager.ajout(nom, pseudo, number);
                Accueil.data = manager.getAllContact();
                manager.fermer();

                Toast.makeText(Ajout.this, "Done :) ", Toast.LENGTH_LONG).show();
                ednom_ajout.setText("");
                edpseudo_ajout.setText("");
                ednum_ajout.setText("");
            }
        });

    }
}
