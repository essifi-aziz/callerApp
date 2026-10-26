package aziz.callerapp;

public class Contact {
    String nom , pseudo , number ;
    int id ;


    public Contact(int id , String nom, String pseudo, String number) {
        this.id=id;
        this.nom = nom;
        this.pseudo = pseudo;
        this.number = number;
    }

    public Contact(String nom, String pseudo, String number) {
        this.nom = nom;
        this.pseudo = pseudo;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getNumber() {
        return number;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
