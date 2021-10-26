package com.example.eleonetech;

import java.io.Serializable;

public class ItemEquipementParDate implements Serializable {
    private String code;
    private String description;
    private String dateFinValidite;

    public ItemEquipementParDate(String code, String description, String dateFinValidite) {
        this.code = code;
        this.description = description;
        this.dateFinValidite = dateFinValidite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateFinValidite() {
        return dateFinValidite;
    }

    public void setDateFinValidite(String dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }
}
