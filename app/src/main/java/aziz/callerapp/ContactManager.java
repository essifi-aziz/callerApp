package aziz.callerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactManager {
    SQLiteDatabase db = null;
    Context con;

    ContactManager(Context con) {
        this.con = con;
    }

    public void ouvrir() {
        ContactHelper helper = new ContactHelper(con, "mabase.db", null, 1);
        db = helper.getWritableDatabase();
    }

    public long ajout(String nom, String pseudo, String number) {
        long a = 0;
        ContentValues values = new ContentValues();
        values.put(ContactHelper.col_nom, nom);
        values.put(ContactHelper.col_pseudo, pseudo);
        values.put(ContactHelper.col_number, number);   // corrigé
        a = db.insert(ContactHelper.table_contact, null, values);
        return a;
    }

    public int modifier(Contact c) {
        ContentValues values = new ContentValues();
        values.put(ContactHelper.col_nom, c.nom);
        values.put(ContactHelper.col_pseudo, c.pseudo);
        values.put(ContactHelper.col_number, c.number);
        return db.update(ContactHelper.table_contact, values,
                ContactHelper.col_id + "=?", new String[]{String.valueOf(c.id)});
    }

    public int supprimer(int id) {
        return db.delete(ContactHelper.table_contact,
                ContactHelper.col_id + "=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Contact> getAllContact() {
        Cursor cr = db.query(ContactHelper.table_contact, new String[]{
                ContactHelper.col_id, ContactHelper.col_nom,
                ContactHelper.col_pseudo, ContactHelper.col_number
        }, null, null, null, null, ContactHelper.col_nom + " ASC");
        return cursorToList(cr);
    }

    // 4) Recherche : par nom, pseudo ou numéro
    public ArrayList<Contact> rechercher(String mot) {
        String like = "%" + mot + "%";
        Cursor cr = db.query(ContactHelper.table_contact, new String[]{
                        ContactHelper.col_id, ContactHelper.col_nom,
                        ContactHelper.col_pseudo, ContactHelper.col_number
                },
                ContactHelper.col_nom + " LIKE ? OR " +
                        ContactHelper.col_pseudo + " LIKE ? OR " +
                        ContactHelper.col_number + " LIKE ?",
                new String[]{like, like, like},
                null, null, ContactHelper.col_nom + " ASC");
        return cursorToList(cr);
    }

    private ArrayList<Contact> cursorToList(Cursor cr) {
        ArrayList<Contact> l = new ArrayList<Contact>();
        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            int i1 = cr.getInt(0);
            String i2 = cr.getString(1);
            String i3 = cr.getString(2);
            String i4 = cr.getString(3);
            l.add(new Contact(i1, i2, i3, i4));
            cr.moveToNext();
        }
        cr.close();
        return l;
    }

    public void fermer() {
        if (db != null) db.close();
    }
}
