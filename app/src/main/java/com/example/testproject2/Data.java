package com.example.testproject2;

public class Data {
    public Data(String niveau ,String date,String heure){
        this.niveau=niveau;
        this.date=date;
        this.heure=heure;

    }
    public Data(String niveau ,String date){
        this.niveau=niveau;
        this.date=date;


    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    private String niveau,date,heure;
}
