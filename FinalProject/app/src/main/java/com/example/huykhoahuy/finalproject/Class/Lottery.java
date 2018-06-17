package com.example.huykhoahuy.finalproject.Class;

public class Lottery {
    private String LotteryCode;
    private String LotteryCompanyName;
    private String LotteryDateBegin;
    private LotteryResult lotteryResult;

    public String getLotteryCode() {
        return LotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        LotteryCode = lotteryCode;
    }

    public String getLotteryCompanyName() {
        return LotteryCompanyName;
    }

    public void setLotteryCompanyName(String lotteryCompanyName) {
        LotteryCompanyName = lotteryCompanyName;
    }

    public String getLotteryDateBegin() {
        return LotteryDateBegin;
    }

    public void setLotteryDateBegin(String lotteryDateBegin) {
        LotteryDateBegin = lotteryDateBegin;
    }

    public LotteryResult getLotteryResult() {
        return lotteryResult;
    }

    public void setLotteryResult(LotteryResult lotteryResult) {
        this.lotteryResult = lotteryResult;
    }
}
