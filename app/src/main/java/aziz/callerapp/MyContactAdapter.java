package aziz.callerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyContactAdapter extends BaseAdapter
{
    Context c ;
    ArrayList<Contact> data ;
    public MyContactAdapter (Context c , ArrayList<Contact> data ){
        this.c=c;
        this.data=data ;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //creer view holder
        LayoutInflater inf =LayoutInflater.from(c);
        View v =inf.inflate(R.layout.view_contact, null);


        TextView tvnom=v.findViewById(R.id.tv_nom);
        TextView tvpseudo=v.findViewById(R.id.tv_pseudo);
        TextView tvnumber=v.findViewById(R.id.tv_num);
        ImageView imgcall=v.findViewById(R.id.imageViewCall);
        ImageView imgDelete=v.findViewById(R.id.imageViewDelete);
        ImageView imgEdit=v.findViewById(R.id.imageViewEdit);


        Contact con =data.get(position);
        tvnom.setText(con.nom);
        tvpseudo.setText(con.pseudo);
        tvnumber.setText(con.number);

        //evenement
        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL); /// appel direct  ;
                i.setData(Uri.parse("tel :"+con.number));
                c.startActivity(i);
            }

        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(c);
                alert.setTitle("Suppression");
                alert.setMessage("vous confirmer le suppression ! ");
                alert.setPositiveButton("Confirmer",null);
                alert.setNegativeButton("Annuler",null);
                alert.setNeutralButton("OK",null);
                alert.show();
                ContactManager manager=new ContactManager(c);

                manager.ouvrir();
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return v;
    }
}
