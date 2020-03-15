package com.example.scanbot.Database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.scanbot.Database.model.BuyOne;
import com.example.scanbot.Database.model.Cart;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM " + "buyhistory_table")
    List<BuyOne> getProduct();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */

    @Query("SELECT * FROM buyhistory_table WHERE user_id IN(:userid)")
    List<BuyOne> findByIds(long userid);


    @Insert
    long insertCart(BuyOne buyOne);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateCart(BuyOne buyOne);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */

    @Query("DELETE FROM buyhistory_table")
    void deleteCart();

}
