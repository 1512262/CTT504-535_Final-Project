package com.example.huykhoahuy.finalproject.Class;

import java.util.ArrayList;

// 1 tờ vé số
public class Lottery {
    private String Lottery_Code;
    private String Lottery_Company_Name;
    private String Lottery_Date_Begin;
    private String Lottery_Province_ID;
    private int prize=-1;

    public String getLotteryCode() {
        return this.Lottery_Code;
    }

    public String getLotteryCompanyName() {
        return this.Lottery_Company_Name;
    }

    public String getLotteryDateBegin() {
        return this.Lottery_Date_Begin;
    }

    public String getLotteryProvinceID() {
        return this.Lottery_Province_ID;
    }

    public Lottery(String lottery_Code, String lottery_Company_Name, String lottery_Date_Begin, String lottery_Province_ID) {
        Lottery_Code = lottery_Code;
        Lottery_Company_Name = lottery_Company_Name;
        Lottery_Date_Begin = lottery_Date_Begin;
        Lottery_Province_ID = lottery_Province_ID;
    }

    public void checkResult(ArrayList<String> list_result) {
        int iCode = Integer.valueOf(this.Lottery_Code);
        for(int i=0;i<list_result.size();i++)
        {
            String result = list_result.get(i);
            int iResult = Integer.valueOf(result);
            if(i==0) {
                if(iCode%100==iResult%100)
                    prize=8;
            }
            if(i==1) {
                if(iCode%1000==iResult%1000)
                    prize=7;
            }
            if(i>=2&&i<=4){
                if(iCode%10000==iResult%10000)
                    prize=6;
            }
            if(i==5) {
                if(iCode%10000==iResult%10000)
                    prize=5;
            }
            if(i>=6&&i<=12){
                if(iCode%100000==iResult%100000)
                    prize=4;
            }
            if(i==13&&i==14){
                if(iCode%100000==iResult%100000)
                    prize=3;
            }
            if(i==15){
                if(iCode%100000==iResult%100000)
                    prize=2;
            }
            if(i==16){
                if(iCode%100000==iResult%100000)
                    prize=1;
            }
            if(i==17){
                if(Lottery_Code==result)
                {
                    prize=0;
                    break;
                }
                else{
                    int count=0;
                    for(int j=0;j<result.length();j++)
                    {
                        if(result.charAt(i)==Lottery_Code.charAt(i)) {
                            count++;
                        }
                    }
                    if(count==4)
                        prize=9;
                }

            }
        }
    }

    public int getPrize() {
        return prize;
    }

    public String getLottery_Company_Name() {
        return Lottery_Company_Name;
    }

    public void setLottery_Company_Name(String lottery_Company_Name) {
        Lottery_Company_Name = lottery_Company_Name;
    }

    public String getLottery_Date_Begin() {
        return Lottery_Date_Begin;
    }

    public void setLottery_Date_Begin(String lottery_Date_Begin) {
        Lottery_Date_Begin = lottery_Date_Begin;
    }


    public void setLottery_Code(String lottery_Code) {
        Lottery_Code = lottery_Code;
    }

    public String getLottery_Province_ID() {
        return Lottery_Province_ID;
    }

    public void setLottery_Province_ID(String lottery_Province_ID) {
        Lottery_Province_ID = lottery_Province_ID;
    }
}
