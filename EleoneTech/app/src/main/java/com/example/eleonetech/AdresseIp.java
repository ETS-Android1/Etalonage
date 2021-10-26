package com.example.eleonetech;

public class AdresseIp {
    private final String adresseIp = "192.168.8.101";

    public AdresseIp() {
        super();
    }

    public String getAdresse() {
        return adresseIp;
    }

    public String urlPagesPhp(){
        return "http://" + getAdresse() + "/eleonetech/";
    }

}
