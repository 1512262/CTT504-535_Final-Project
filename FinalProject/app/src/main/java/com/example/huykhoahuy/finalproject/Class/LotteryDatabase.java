package com.example.huykhoahuy.finalproject.Class;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Lottery.class, version = 1)
public abstract class LotteryDatabase extends RoomDatabase{
    public abstract LotteryDao lotteryDao();

    private static LotteryDatabase INSTANCE;
    public static LotteryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Lottery.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LotteryDatabase.class, "LotteryDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
