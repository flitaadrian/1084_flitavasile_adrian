package com.example.adrianflita.models;

import java.util.List;

public class BankInfo {
    private String sucursala;
    private String sediu;
    private String CIF;
    private String telefon;
    private String FAX;
    private List<Cont> contList;

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

    public List<Cont> getContList() {
        return contList;
    }

    public void setContList(List<Cont> contList) {
        this.contList = contList;
    }

    public BankInfo(String sucursala, String sediu, String CIF, String telefon, String FAX, List<Cont> contList) {
        this.sucursala = sucursala;
        this.sediu = sediu;
        this.CIF = CIF;
        this.telefon = telefon;
        this.FAX = FAX;
        this.contList = contList;
    }
}
