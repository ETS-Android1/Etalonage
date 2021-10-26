package com.example.eleonetech;

import java.io.Serializable;

public class ItemRapport implements Serializable {
    private String codeEquipement;
    private String dateFinValidite;

    public ItemRapport(String codeEquipement, String dateFinValidite) {
        this.codeEquipement = codeEquipement;
        this.dateFinValidite = dateFinValidite;
    }

    public String getCodeEquipement() {
        return codeEquipement;
    }

    public void setCodeEquipement(String codeEquipement) {
        this.codeEquipement = codeEquipement;
    }

    public String getDateFinValidite() {
        return dateFinValidite;
    }

    public void setDateFinValidite(String dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }
}
