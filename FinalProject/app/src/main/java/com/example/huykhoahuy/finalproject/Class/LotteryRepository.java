package com.example.huykhoahuy.finalproject.Class;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.huykhoahuy.finalproject.Interface.LotteryDao;

import java.util.List;

public class LotteryRepository {
    private static LotteryRepository INSTANCE;

    private LotteryDao mLotteryDao;
    private LiveData<List<Lottery>> mAllLotteries;

    private LotteryRepository(Application application) {
        LotteryDatabase db = LotteryDatabase.getDatabase(application);
        mLotteryDao = db.lotteryDao();
        mAllLotteries = mLotteryDao.loadAllLotteries();
    }

    public static LotteryRepository getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new LotteryRepository(application);
        }
        return INSTANCE;
    }

    LiveData<List<Lottery>> getAllLotteries() {
        return mAllLotteries;
    }

    public void insertLottery(Lottery lottery) {
        new insertAsyncTask(mLotteryDao).execute(lottery);
    }
    public void deleteLottery(Lottery lottery) {
        new deleteAsyncTask(mLotteryDao).execute(lottery);
    }
    public void updateLottery(Lottery lottery) {
        new updateAsyncTask(mLotteryDao).execute(lottery);
    }
    public void deleteAllLotteries() {
        new deleteAllAsyncTask(mLotteryDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Lottery, Void, Void> {
        private LotteryDao mAsyncTaskDao;

        insertAsyncTask(LotteryDao lotteryDao) {
            mAsyncTaskDao = lotteryDao;
        }

        @Override
        protected Void doInBackground(Lottery... lotteries) {
            mAsyncTaskDao.insertLottery(lotteries[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Lottery, Void, Void> {
        private LotteryDao mAsyncTaskDao;

        deleteAsyncTask(LotteryDao lotteryDao) {
            mAsyncTaskDao = lotteryDao;
        }

        @Override
        protected Void doInBackground(final Lottery... lotteries) {
            mAsyncTaskDao.deleteLottery(lotteries[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<Lottery, Void, Void> {
        private LotteryDao mAsyncTaskDao;

        public updateAsyncTask(LotteryDao lotteryDao) {
            mAsyncTaskDao = lotteryDao;
        }

        @Override
        protected Void doInBackground(Lottery... lotteries) {
            mAsyncTaskDao.updateLottery(lotteries[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private LotteryDao mAsyncTaskDao;

        public deleteAllAsyncTask(LotteryDao lotteryDao) {
            mAsyncTaskDao = lotteryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllLotteries();
            return null;
        }
    }
}
