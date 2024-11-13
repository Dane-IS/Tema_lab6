package Problema_firma;

import java.time.LocalDate;

public class Angajat {
    private String nume;
    private String post;
    private LocalDate data_angajare;
    private float salar;

    public Angajat() {}
    public Angajat(String nume, String post, LocalDate data_angajare, float salar) {
        super();
        this.nume = nume;
        this.post = post;
        this.data_angajare = data_angajare;
        this.salar = salar;
    }

    public String getNume(){
        return nume;
    }
    public void setNume(String nume){
        this.nume = nume;
    }

    public String getPost(){
        return post;
    }
    public void setPost(String post){
        this.post = post;
    }

    public LocalDate getData_angajare(){
        return data_angajare;
    }
    public void setData_angajare(LocalDate data_angajare){
        this.data_angajare = data_angajare;
    }

    public float getSalar(){
        return salar;
    }
    public void setSalar(float salar){
        this.salar = salar;
    }

    @Override
    public String toString() {
        return "\n Angajat {" +
                "\n Numele: " + nume +
                "\n Postul: " + post +
                "\n Data angajarii: " + data_angajare +
                "\n Salarul: "+ salar +
                "\n}";
    }
}
