package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

class ConditionOfCode extends ConditionAbstract {
    @Override
    public int getTypeOfInfo() {
        return 0;
    }

    @Override
    protected boolean isAllowed(char c) {
        return isDigit(c) || (c == ' ');
    }

    @Override
    public boolean validateResult(String result) {
        return result.length() == 6;
    }
}
