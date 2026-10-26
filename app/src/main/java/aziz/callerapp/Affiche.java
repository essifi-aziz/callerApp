package aziz.callerapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Affiche extends AppCompatActivity {

    RecyclerView rv_affiche;
    EditText ed_recherche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_affiche);
        rv_affiche = findViewById(R.id.rv_affiche);
        ed_recherche = findViewById(R.id.ed_recherche);

        ContactManager manager = new ContactManager(Affiche.this);
        manager.ouvrir();
        Accueil.data = manager.getAllContact();

        MyRecycleContactAdapter ad = new MyRecycleContactAdapter(Affiche.this, Accueil.data);
        rv_affiche.setAdapter(ad);

        GridLayoutManager layoutManager = new GridLayoutManager(Affiche.this, 1, GridLayoutManager.VERTICAL, false);
        rv_affiche.setLayoutManager(layoutManager);

        ed_recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mot = s.toString().trim();
                if (mot.isEmpty()) {
                    ad.updateList(manager.getAllContact());
                } else {
                    ad.updateList(manager.rechercher(mot));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
