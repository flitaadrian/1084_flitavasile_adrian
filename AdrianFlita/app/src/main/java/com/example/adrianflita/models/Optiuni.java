package com.example.adrianflita.models;

import java.io.Serializable;

public class Optiuni implements Serializable {
    private String comision;
    private boolean crypto;
    private ExtraPachet extra_pachet;

    public Optiuni(String comision, boolean crypto, ExtraPachet extra_pachet) {
        this.comision = comision;
        this.crypto = crypto;
        this.extra_pachet = extra_pachet;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public boolean isCrypto() {
        return crypto;
    }

    public void setCrypto(boolean crypto) {
        this.crypto = crypto;
    }

    public ExtraPachet getExtra_pachet() {
        return extra_pachet;
    }

    public void setExtra_pachet(ExtraPachet extra_pachet) {
        this.extra_pachet = extra_pachet;
    }

    @Override
    public String toString() {
        return "Optiuni{" +
                "comision='" + comision + '\'' +
                ", crypto=" + crypto +
                '}';
    }
}
