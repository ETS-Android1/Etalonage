package com.example.eleonetech;

import java.io.Serializable;

public class ItemEquipements implements Serializable {
    private String code;
    private String etat;

    public ItemEquipements(String code, String etat) {
        this.code = code;
        this.etat = etat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
