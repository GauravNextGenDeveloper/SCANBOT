package com.example.scanbot.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.scanbot.Database.dao.CartDao;
import com.example.scanbot.Database.dao.HistoryDao;
import com.example.scanbot.Database.dao.RegisterDao;
import com.example.scanbot.Database.model.BuyOne;
import com.example.scanbot.Database.model.Cart;
import com.example.scanbot.Database.model.Register;


@Database(entities = { Register.class, Cart.class, BuyOne.class}, version = 1)
public abstract class ScanBotDatabase extends RoomDatabase {

    public abstract RegisterDao getUsers();
    public abstract CartDao getCart();
    public abstract HistoryDao gethistory();


    private static ScanBotDatabase scanBotDatabase;

    // synchronized is use to avoid concurrent access in multithred environment
    public static /*synchronized*/ ScanBotDatabase getInstance(Context context) {
        if (null == scanBotDatabase) {
            scanBotDatabase = buildDatabaseInstance(context);
        }
        return scanBotDatabase;
    }

    private static ScanBotDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                ScanBotDatabase.class,
                "SCANBOT").allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        scanBotDatabase = null;
    }
}