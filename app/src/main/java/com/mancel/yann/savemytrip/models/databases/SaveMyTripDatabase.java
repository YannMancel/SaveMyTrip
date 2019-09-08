package com.mancel.yann.savemytrip.models.databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mancel.yann.savemytrip.models.databases.dao.ItemDao;
import com.mancel.yann.savemytrip.models.databases.dao.UserDao;
import com.mancel.yann.savemytrip.models.pojos.Item;
import com.mancel.yann.savemytrip.models.pojos.User;

/**
 * Created by Yann MANCEL on 13/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.models.databases.dao
 *
 * A {@link RoomDatabase} subclass.
 */
@Database(entities = {Item.class,
                      User.class},
          version = 1,
          exportSchema = false)
public abstract class SaveMyTripDatabase extends RoomDatabase {

    // FIELDS --------------------------------------------------------------------------------------

    private static volatile SaveMyTripDatabase INSTANCE;

    // METHODS -------------------------------------------------------------------------------------

    public abstract ItemDao itemDao();
    public abstract UserDao userDao();

    // INSTANCE ************************************************************************************

    /**
     * Returns the instance of SaveMyTripDatabase
     * @param context a Context object that contains the context
     * @return a SaveMyTripDatabase object that corresponds to the database
     */
    public static SaveMyTripDatabase getInstance(Context context) {
        // Creation of Singleton
        if (INSTANCE == null) {
            synchronized (SaveMyTripDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    SaveMyTripDatabase.class, "MyDatabase.db")
                                   .addCallback(prepopulateDatabase())
                                   .build();
                }
            }
        }

        return INSTANCE;
    }

    // OTHERS **************************************************************************************

    /**
     * Creates and returns a Callback object
     * @return a Callback object that contains the first user
     */
    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                // Creates a ContentValues that represents an User
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("userName", "Yann");
                contentValues.put("urlPicture", "https://api.adorable.io/AVATARS/512/20.png");

                // Creates an User in the "User" database
                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
