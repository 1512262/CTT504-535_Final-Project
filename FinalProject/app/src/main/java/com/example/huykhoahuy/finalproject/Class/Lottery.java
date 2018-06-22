package com.example.huykhoahuy.finalproject.Class;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "lottery")
public class Lottery {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lottery_id")
    private int Lottery_ID = 0;

    @ColumnInfo(name = "lottery_company_name")
    private String Lottery_Company_Name;

    @ColumnInfo(name = "lottery_date")
    private String Lottery_Date;

    @ColumnInfo(name = "lottery_province_id")
    private String Lottery_Province_ID;

    @ColumnInfo(name = "lottery_code")
    public String Lottery_Code;

    @ColumnInfo(name = "lottery_check_date")
    private String Lottery_Check_Date = "";

    @ColumnInfo(name = "lottery_check_time")
    private String Lottery_Check_Time = "";

    @ColumnInfo(name = "lottery_prize")
    private String Lottery_Prize = "Chúc bạn may mắn lần sau";


    public Lottery(){}

    @Ignore
    public Lottery(String lottery_Company_Name, String lottery_Date, String lottery_Province_ID, String lottery_Code) {
        Lottery_Company_Name = lottery_Company_Name;
        Lottery_Date = lottery_Date;
        Lottery_Province_ID = lottery_Province_ID;
        Lottery_Code = lottery_Code;
    }

    public void checkResult(ArrayList<String> list_result) {
        int iCode = Integer.valueOf(this.Lottery_Code);
        for(int i=0;i<list_result.size();i++)
        {
            String result = list_result.get(i);
            int iResult = Integer.valueOf(result);
            if(i==0) {
                if(iCode%100==iResult%100)
                    Lottery_Prize="Giải Tám";
            }
            if(i==1) {
                if(iCode%1000==iResult%1000)
                    Lottery_Prize="Giải Bảy";
            }
            if(i>=2&&i<=4){
                if(iCode%10000==iResult%10000)
                    Lottery_Prize="Giải Sáu";
            }
            if(i==5) {
                if(iCode%10000==iResult%10000)
                    Lottery_Prize="Giải Năm";
            }
            if(i>=6&&i<=12){
                if(iCode%100000==iResult%100000)
                    Lottery_Prize="Giải Tư";
            }
            if(i==13&&i==14){
                if(iCode%100000==iResult%100000)
                    Lottery_Prize="Giải Ba";
            }
            if(i==15){
                if(iCode%100000==iResult%100000)
                    Lottery_Prize="Giải Nhì";
            }
            if(i==16){
                if(iCode%100000==iResult%100000)
                    Lottery_Prize="Giải Nhất";
            }
            if(i==17){
                if(Lottery_Code.equals(result))
                {
                    Lottery_Prize="Giải Đặc biệt";
                    break;
                }
                else{
                    int count=0;
                    for(int j=0;j<result.length();j++)
                    {
                        if(result.charAt(j)==Lottery_Code.charAt(j)) {
                            count++;
                        }
                    }
                    if(count==5)
                        Lottery_Prize="Giải Khuyến Khích";
                }

            }
        }
    }


    public String getLottery_Company_Name() {
        return Lottery_Company_Name;
    }

    public void setLottery_Company_Name(String lottery_Company_Name) {
        Lottery_Company_Name = lottery_Company_Name;
    }

    public String getLottery_Date() {
        return Lottery_Date;
    }

    public void setLottery_Date(String lottery_Date) {
        Lottery_Date = lottery_Date;
    }






    public String getLottery_Check_Date() {
        return Lottery_Check_Date;
    }

    public void setLottery_Check_Date(String lottery_Check_Date) {
        Lottery_Check_Date = lottery_Check_Date;
    }

    public String getLottery_Check_Time() {
        return Lottery_Check_Time;
    }

    public void setLottery_Check_Time(String lottery_Check_Time) {
        Lottery_Check_Time = lottery_Check_Time;
    }

    public String getLottery_Prize() {
        return Lottery_Prize;
    }

    public void setLottery_Prize(String lottery_Prize) {
        Lottery_Prize = lottery_Prize;
    }

    public String getLottery_Province_ID() {
        return Lottery_Province_ID;
    }

    public void setLottery_Province_ID(String lottery_Province_ID) {
        Lottery_Province_ID = lottery_Province_ID;
    }

    public int getLottery_ID() {
        return Lottery_ID;
    }

    public void setLottery_ID(int lottery_ID) {
        Lottery_ID = lottery_ID;
    }
}
