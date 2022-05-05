package com.example.adrianflita.models;

import java.io.Serializable;

public class ExtraPachet implements Serializable {
    private boolean cont_valuta;
    private boolean conversie_interbancara;

    public ExtraPachet(boolean cont_valuta, boolean conversie_interbancara) {
        this.cont_valuta = cont_valuta;
        this.conversie_interbancara = conversie_interbancara;
    }

    public boolean isCont_valuta() {
        return cont_valuta;
    }

    public void setCont_valuta(boolean cont_valuta) {
        this.cont_valuta = cont_valuta;
    }

    public boolean isConversie_interbancara() {
        return conversie_interbancara;
    }

    public void setConversie_interbancara(boolean conversie_interbancara) {
        this.conversie_interbancara = conversie_interbancara;
    }

    @Override
    public String toString() {
        return "ExtraPachet{" +
                "cont_valuta=" + cont_valuta +
                ", conversie_interbancara=" + conversie_interbancara +
                '}';
    }
}
