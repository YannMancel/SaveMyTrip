package com.mancel.yann.savemytrip.models.databases.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mancel.yann.savemytrip.models.pojos.User;

/**
 * Created by Yann MANCEL on 13/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.models.databases.dao
 */
@Dao
public interface UserDao {

    // CREATE **************************************************************************************

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createUser(final User user);

    // READ ****************************************************************************************

    @Query("SELECT * FROM User WHERE id = :userId")
    LiveData<User> getUser(final long userId);
}
