package aziz.callerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecycleContactAdapter extends RecyclerView.Adapter<MyRecycleContactAdapter.MyViewHolder> {

    Context con;
    ArrayList<Contact> data;
    ContactManager manager;

    public MyRecycleContactAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
        manager = new ContactManager(con);
        manager.ouvrir();
    }

    // utilisé par la recherche
    public void updateList(ArrayList<Contact> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecycleContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_contact, parent, false);   // corrigé
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleContactAdapter.MyViewHolder holder, int position) {
        Contact c = data.get(position);
        holder.tvnom.setText(c.nom);
        holder.tvpseudo.setText(c.pseudo);
        holder.tvnumber.setText(c.number);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvnom, tvpseudo, tvnumber;
        ImageView imgcall, imgDelete, imgEdit;

        public MyViewHolder(@NonNull View v) {
            super(v);
            tvnom = v.findViewById(R.id.tv_nom);
            tvpseudo = v.findViewById(R.id.tv_pseudo);
            tvnumber = v.findViewById(R.id.tv_num);
            imgcall = v.findViewById(R.id.imageViewCall);
            imgDelete = v.findViewById(R.id.imageViewDelete);
            imgEdit = v.findViewById(R.id.imageViewEdit);

            imgcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedIndex = getAbsoluteAdapterPosition();
                    Contact selectContact = data.get(selectedIndex);

                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:" + selectContact.number));   // corrigé
                    con.startActivity(i);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedIndex = getAbsoluteAdapterPosition();
                    Contact selectContact = data.get(selectedIndex);

                    AlertDialog.Builder alert = new AlertDialog.Builder(con);
                    alert.setTitle("Suppression");
                    alert.setMessage("Vous confirmez la suppression ? ");
                    alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            manager.supprimer(selectContact.id);   // SQLite
                            data.remove(selectedIndex);
                            notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("Annuler", null);
                    alert.show();
                }
            });

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedIndex = getAbsoluteAdapterPosition();
                    Contact selectContact = data.get(selectedIndex);

                    AlertDialog.Builder alert = new AlertDialog.Builder(con);
                    alert.setTitle("Edition");
                    alert.setMessage("Modifier les informations ...! ");

                    LayoutInflater inf = LayoutInflater.from(con);
                    View vd = inf.inflate(R.layout.view_dialog, null);
                    alert.setView(vd);

                    EditText ed_nom = vd.findViewById(R.id.ed_nom);
                    EditText ed_pseudo = vd.findViewById(R.id.ed_pseudo);
                    EditText phone = vd.findViewById(R.id.phone);
                    ed_nom.setText(selectContact.nom);
                    ed_pseudo.setText(selectContact.pseudo);
                    phone.setText(selectContact.number);

                    alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            String nom = ed_nom.getText().toString();
                            String pseudo = ed_pseudo.getText().toString();
                            String number = phone.getText().toString();

                            Contact c = new Contact(selectContact.id, nom, pseudo, number); // garder l'id
                            manager.modifier(c);   // SQLite
                            data.set(selectedIndex, c);
                            notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("Annuler", null);
                    alert.show();
                }
            });
        }
    }
}
