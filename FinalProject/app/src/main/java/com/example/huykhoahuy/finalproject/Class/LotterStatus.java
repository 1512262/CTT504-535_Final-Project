package com.example.huykhoahuy.finalproject.Class;

import java.util.Date;

// Lấy dữ liệu từ API và check
public class LotterStatus {
    private String provice_id;
    private String yourcode;
    private Date date;
    private Boolean yourresult;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getYourresult() {
        return yourresult;
    }

    public void setYourresult(Boolean yourresult) {
        this.yourresult = yourresult;
    }
}
