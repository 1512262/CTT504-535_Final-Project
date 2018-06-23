package com.example.huykhoahuy.finalproject.Interface;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.huykhoahuy.finalproject.Class.Lottery;

import java.util.List;

@Dao
public interface LotteryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLottery(Lottery lottery);

    @Delete
    public void deleteLottery(Lottery lottery);

    @Update
    public void updateLottery(Lottery lottery);

    @Query("SELECT * FROM lottery ORDER BY lottery_province_id")
    public LiveData<List<Lottery>> loadAllLotteries();

    @Query("DELETE FROM lottery")
    public void deleteAllLotteries();
}
