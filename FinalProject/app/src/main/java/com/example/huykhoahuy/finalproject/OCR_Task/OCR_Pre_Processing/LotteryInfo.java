package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

import android.content.Context;

import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.OCR_Task.OCR_Activity;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.RetrieveLotteryResult;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LotteryInfo {

    private String lotteryDate;
    private String lotteryHost;
    private String lotteryCode;
    private ArrayList<String> hostList;

    static boolean isDigit(char c) {
        return c <= '9' && c >= '0';
    }
    static boolean isAllDigit(String s) {
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (!isDigit(c))
                return false;
        }
        return true;
    }



    LotteryInfo() {
        this.lotteryDate = "";
        this.lotteryCode = "";
        this.lotteryHost = "";
        hostList = new ArrayList<String>();
        hostList.add("xsst");
        hostList.add("xshg");
        hostList.add("xshcm");
        hostList.add("xsla");
        hostList.add("xsbd");
        hostList.add("xstv");
        hostList.add("xsvl");
        hostList.add("xsbth");
        hostList.add("xsag");
        hostList.add("xstn");
        hostList.add("xsct");
        hostList.add("xsdn");
        hostList.add("xsbth");
        hostList.add("xsvt");
        hostList.add("xscm");
        hostList.add("xsdl");
        hostList.add("xstg");

    }



    // Setter

    public void setLotteryCode(String lotteryCode) {
        if (lotteryCode.length() == 6 && isAllDigit(lotteryCode))
            this.lotteryCode = lotteryCode;
    }

    public void setLotteryDate(String lotteryDate) {
        if (lotteryDate.length() == 8 && isAllDigit(lotteryDate)) {
            DateCheckerFactory dcf = new DateCheckerFactory();
            this.lotteryDate = dcf.parseStringToDate(lotteryDate);

        }
    }

    public void setLotteryHost(String lotteryHost) {
        int maxScore = -1;
        for (int i = 0; i < this.hostList.size(); ++i) {
            int score = calculateScore(lotteryHost, this.hostList.get(i));
            if (score > maxScore) {
                maxScore = score;
                this.lotteryHost = this.hostList.get(i);
            }
        }
    }

    private int calculateScore(String u, String v) {
        int n = u.length(), m = v.length();
        int f[][] = new int[n+1][m+1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (isEqual(u.charAt(i), v.charAt(j))) {
                    f[i+1][j+1] = f[i][j]+1;
                }
                else {
                    f[i+1][j+1] = Math.max(f[i][j+1], f[i+1][j]);
                }
            }
        }
        return f[n][m];
    }

    private char convertChar(char c) {
        if (c >= 'A' && c <= 'Z')
            return (char) (c - 'A' + 'a');
        return c;
    }


    private boolean isEqual(char a, char b) {
        return convertChar(a) == convertChar(b);
    }

    // Getter
    public String getLotteryDate() {
        return lotteryDate;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public String getLotteryHost() {
        return lotteryHost;
    }
}
