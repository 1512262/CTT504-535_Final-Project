package com.example.huykhoahuy.finalproject.Class;


// Lấy dữ liệu từ API và check
public class LotterStatus {
    private String provice_id;
    private String yourcode;
    private String date;
    private Boolean yourresult = false;

    public LotterStatus() {
    }

    public String getProvice_id() {
        return provice_id;
    }

    public void setProvice_id(String provice_id) {
        this.provice_id = provice_id;
    }

    public String getYourcode() {
        return yourcode;
    }

    public void setYourcode(String yourcode) {
        this.yourcode = yourcode;
    }


    public Boolean getYourresult() {
        return yourresult;
    }

    public void setYourresult(Boolean yourresult) {
        this.yourresult = yourresult;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
