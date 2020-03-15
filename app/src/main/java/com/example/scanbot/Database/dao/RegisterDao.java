package com.example.scanbot.Database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.scanbot.Database.model.Register;

import java.util.List;
@Dao
public interface RegisterDao {
    @Query("SELECT * FROM " + "users_table")
    List<Register> getUser();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    long insertRegister(Register register);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateRegister(Register register);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */

    @Query("DELETE FROM users_table")
    void deleteRegister();

}
