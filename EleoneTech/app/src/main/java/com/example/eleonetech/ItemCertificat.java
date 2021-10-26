package com.example.eleonetech;

import java.io.Serializable;

public class ItemCertificat implements Serializable {
    private String code;
    private String date;
    private String certif;

    public ItemCertificat(String code, String date, String certif) {
        this.code = code;
        this.date = date;
        this.certif = certif;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCertif() {
        return certif;
    }

    public void setCertif(String certif) {
        this.certif = certif;
    }
}
