package com.example.adrianflita.models;

import java.io.Serializable;

public class Cont implements Serializable {
    private String tip;
    private Optiuni optiuni;

    public Cont(String tip, Optiuni optiuni) {
        this.tip = tip;
        this.optiuni = optiuni;
    }

    public String getTip() {
        return tip;
    }

    public void setName(String tip) {
        this.tip = tip;
    }

    public Optiuni getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(Optiuni optiuni) {
        this.optiuni = optiuni;
    }

    @Override
    public String toString() {
        return "Cont{" +
                "tip='" + tip + '\'' +
                ", optiuni=" + optiuni +
                '}';
    }
}
