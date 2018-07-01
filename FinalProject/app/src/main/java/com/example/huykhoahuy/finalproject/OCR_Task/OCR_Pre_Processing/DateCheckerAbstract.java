package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

abstract public class DateCheckerAbstract implements DateChecker {
    boolean checkIfDayExist(int day, int month, int year) {
        if (month == 2) {
            if (isLeap(year)) {
                return 1 <= day && day <= 29;
            }
            else {
                return 1 <= day && day <= 28;
            }
        }
        else if (month == 1 || month == 3 || month == 5 ||
                month == 7 || month == 8 || month == 10 ||
                month == 12) {
            return 1 <= day && day <= 31;
        }
        else if (month >= 4 && month <= 11){
            return 1 <= day && day <= 30;
        }
        else {
            return false;
        }
    }

    private boolean isLeap(int year) {
        return year%400 == 0 || (year%4 == 0 && year%100 != 0);
    }
}
