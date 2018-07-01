package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

class ConditionOfDate extends ConditionAbstract {
    @Override
    public int getTypeOfInfo() {
        return 1;
    }

    @Override
    protected boolean isAllowed(char c) {
        return isDigit(c) || (c == '-') || (c == ' ');
    }

    @Override
    public boolean validateResult(String result) {
        DateCheckerFactory dcf = new DateCheckerFactory();
        return dcf.checkDate(result);
    }
}
