package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

public class DateCheckerLen8 extends DateCheckerAbstract {
    @Override
    public boolean checkDate(String rawDate) {
        int day = Integer.parseInt(rawDate.substring(0, 2));
        int month = Integer.parseInt(rawDate.substring(2, 4));
        int year = Integer.parseInt(rawDate.substring(4, 8));
        return checkIfDayExist(day, month, year);
    }

    @Override
    public String parseStringToDate(String rawDate) {
        StringBuilder result = new StringBuilder();

        result.append(rawDate.substring(0, 2));
        result.append("-");
        result.append(rawDate.substring(2, 4));
        result.append("-");
        result.append(rawDate.substring(4, 8));

        return result.toString();
    }
}
