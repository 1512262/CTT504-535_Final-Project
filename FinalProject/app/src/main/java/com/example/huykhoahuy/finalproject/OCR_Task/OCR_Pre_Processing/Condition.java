package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

interface Condition {
    int getTypeOfInfo();
    boolean validateResult(String result);
    String extractInformationWithCondition(String inputString);
}
