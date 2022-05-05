package com.example.adrianflita.models;

import java.io.Serializable;

public class BankingAccount implements Serializable {
    private String sucursala;
    private String sediu;
    private String CIF;
    private String telefon;
    private String FAX;

    public String getSucursala() {
        return sucursala;
    }

    public void setSucursala(String sucursala) {
        this.sucursala = sucursala;
    }

    public String getSediu() {
        return sediu;
    }

    public void setSediu(String sediu) {
        this.sediu = sediu;
    }

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFAX() {
        return FAX;
    }

    public void setFAX(String FAX) {
        this.FAX = FAX;
    }

    public BankingAccount(String sucursala, String sediu, String CIF, String telefon, String FAX) {
        this.sucursala = sucursala;
        this.sediu = sediu;
        this.CIF = CIF;
        this.telefon = telefon;
        this.FAX = FAX;
    }
}
