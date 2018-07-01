package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

import java.util.ArrayList;

class ParsingListResultToGetInformation {
    private ArrayList<Condition> conditions;

    ParsingListResultToGetInformation() {
        conditions = new ArrayList<>();
        conditions.add(new ConditionOfCode());
        conditions.add(new ConditionOfDate());
        conditions.add(new ConditionOfHost());
    }


    public LotteryInfo getLotteryInfo(ArrayList<String> listResult) {
        LotteryInfo lotteryInfo = new LotteryInfo();
        for (int i = 0; i < listResult.size(); ++i) {
            // Says, we only care about the continuous recognized results
            // For example: 6 4 2 3 9 7 (the space is optional)
            // For example: SO XO HAU GIANG
            // etc.

            String[] resultInOneBlock = listResult.get(i).split("\n");
            for (int j = 0; j < resultInOneBlock.length; ++j) {
                String resultInALine = resultInOneBlock[j];

                for (int k = 0; k < conditions.size(); ++k) {
                    String result = conditions.get(k).extractInformationWithCondition(resultInALine);
                    if (result == null) {
                        continue;
                    }
                    switch (conditions.get(k).getTypeOfInfo()) {
                        case 0:
                            lotteryInfo.setLotteryCode(result);
                            break;
                        case 1:
                            lotteryInfo.setLotteryDate(result);
                            break;
                        case 2:
                            lotteryInfo.setLotteryHost(result);
                            break;
                    }

                }
            }
        }
        return lotteryInfo;
    }
}
