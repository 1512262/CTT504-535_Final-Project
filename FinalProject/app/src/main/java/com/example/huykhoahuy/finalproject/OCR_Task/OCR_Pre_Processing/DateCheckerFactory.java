package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

import java.util.ArrayList;

public class DateCheckerFactory implements DateChecker {

    private ArrayList<DateCheckerAbstract> getListOfDateChecker(String rawDate) {
        ArrayList<DateCheckerAbstract> dca = new ArrayList<>();
        if (rawDate.length() == 8)
            dca.add(new DateCheckerLen8());
        else if (rawDate.length() == 7) {
            dca.add(new DateCheckerLen7a());
            dca.add(new DateCheckerLen7b());
        }
        else if (rawDate.length() == 6) {
            dca.add(new DateCheckerLen6());
        }
        return dca;
    }

    @Override
    public boolean checkDate(String rawDate) {
        ArrayList<DateCheckerAbstract> dca = getListOfDateChecker(rawDate);
        for (int i = 0; i < dca.size(); ++i) {
            if (dca.get(i).checkDate(rawDate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String parseStringToDate(String rawDate) {
        ArrayList<DateCheckerAbstract> dca = getListOfDateChecker(rawDate);
        for (int i = 0; i < dca.size(); ++i) {
            if (dca.get(i).checkDate(rawDate)) {
                return dca.get(i).parseStringToDate(rawDate);
            }
        }
        return "";
    }
}
