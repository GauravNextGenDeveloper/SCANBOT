package com.example.scanbot.Database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.scanbot.Database.model.Cart;
import com.example.scanbot.Database.model.Register;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM " + "product_table")
    List<Cart> getProduct();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */


    @Query("SELECT * FROM product_table WHERE user_id IN(:userid)")
    List<Cart> findByIds(long userid);

    @Insert
    long insertCart(Cart cart);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateCart(Cart cart);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void deleteCart();


}
