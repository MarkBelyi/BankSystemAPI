package com.example.banksystemapi.Responses;

public class InfoResponse {
    private String info;

    public InfoResponse(String info){
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }
}
