package com.example.huykhoahuy.finalproject.Class;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class LotteryViewModel extends AndroidViewModel {
    private static LotteryViewModel INSTANCE = null;

    private LotteryRepository mLotteryRepository;
    private LiveData<List<Lottery>> mAllLotteries;

    public LotteryViewModel(@NonNull Application application) {
        super(application);
        mLotteryRepository = LotteryRepository.getInstance(application);
        mAllLotteries = mLotteryRepository.getAllLotteries();
    }

    public static void setInstance(LotteryViewModel lotteryViewModel) {
        if (lotteryViewModel != null && INSTANCE == null) {
            INSTANCE = lotteryViewModel;
        }
    }

    public static LotteryViewModel getInstance() {
        return INSTANCE;
    }

    public LiveData<List<Lottery>> getAllLotteries() {return mAllLotteries;}

    public void insertLottery(Lottery lottery) {mLotteryRepository.insertLottery(lottery);}
    public void deleteLottery(Lottery lottery) {mLotteryRepository.deleteLottery(lottery);}
    public void updateLottery(Lottery lottery) {mLotteryRepository.updateLottery(lottery);}
    public void deleteAllLotteries() {mLotteryRepository.deleteAllLotteries();}
}
