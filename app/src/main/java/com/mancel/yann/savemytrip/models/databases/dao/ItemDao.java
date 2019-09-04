package com.mancel.yann.savemytrip.models.databases.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mancel.yann.savemytrip.models.pojos.Item;

import java.util.List;

/**
 * Created by Yann MANCEL on 13/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.models.databases.dao
 */
@Dao
public interface ItemDao {

    // CREATE **************************************************************************************

    @Insert
    long insertItem(Item item);

    // READ ****************************************************************************************

    @Query("SELECT * FROM Item WHERE userId = :userId")
    LiveData<List<Item>> getItems(long userId);

    // UPDATE **************************************************************************************

    @Update
    int updateItem(Item item);

    // DELETE **************************************************************************************

    @Query("DELETE FROM Item WHERE id = :itemId")
    int deleteItem(long itemId);
}
