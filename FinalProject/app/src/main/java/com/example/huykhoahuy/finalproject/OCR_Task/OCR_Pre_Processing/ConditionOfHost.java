package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

class ConditionOfHost extends ConditionAbstract {
    static boolean isDigit(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    @Override
    protected boolean isAllowed(char c) {
        return isDigit(c) || (c == ' ');
    }

    @Override
    public int getTypeOfInfo() {
        return 2;
    }

    private boolean isEqualChar(char a, char b) {
        if (b < 'a' || b > 'z') return false;
        return a == b || a == b-'a'+'A';
    }

    @Override
    public boolean validateResult(String result) {
        // First dummy implementation
        return result.length() >= 4 && isEqualChar(result.charAt(0), 'x') && isEqualChar(result.charAt(1), 's');
    }

    public String extractInformationWithCondition(String inputString) {
        int len = inputString.length();
        for (int i = 0; i < len; ) {
            int j = i;
            StringBuilder result = new StringBuilder();
            char firstC = 0, secondC = 0;
            for ( ; j < len; ++j) {
                char c = inputString.charAt(j);
                if (!isAllowed(c))
                    break;

                if (!isDigit(c))
                    continue;

                if (firstC == 0) {
                    if (isEqualChar(c, 'x'))
                        firstC = 'x';
                }
                else if (secondC == 0) {
                    if (isEqualChar(c, 's')) {
                        secondC = 's';
                        result.append("xs");
                    }
                    else {
                        firstC = 0;
                    }
                }
                else {
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
