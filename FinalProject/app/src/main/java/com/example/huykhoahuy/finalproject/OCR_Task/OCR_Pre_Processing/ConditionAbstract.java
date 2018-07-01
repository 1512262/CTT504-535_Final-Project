package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

abstract public class ConditionAbstract implements Condition {
    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    abstract protected boolean isAllowed(char c);

    @Override
    abstract public int getTypeOfInfo();

    @Override
    abstract public boolean validateResult(String result);

    @Override
    public String extractInformationWithCondition(String inputString) {
        int len = inputString.length();
        for (int i = 0; i < len; ) {
            int j = i;
            StringBuilder result = new StringBuilder();
            for ( ; j < len; ++j) {
                char c = inputString.charAt(j);
                if (!isAllowed(c))
                    break;
                if (isDigit(c)) {
                    result.append(c);
                }
            }
            if (validateResult(result.toString())) {
                return result.toString();
            }
            i = j + 1;
        }
        return null;
    }
}
