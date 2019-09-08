package com.mancel.yann.savemytrip.repositories;

import android.arch.lifecycle.LiveData;

import com.mancel.yann.savemytrip.models.databases.dao.UserDao;
import com.mancel.yann.savemytrip.models.pojos.User;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.repositories
 */
public class UserDataRepository {

    // FIELDS --------------------------------------------------------------------------------------

    private final UserDao mUserDao;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param userDao a {@link UserDao}
     */
    public UserDataRepository(UserDao userDao) {
        this.mUserDao = userDao;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    /**
     * Creates a {@link User} and return its id value
     * @param user a {@link User}
     * @return an integer that contains the id value
     */
    public long createUser(User user) {
        return this.mUserDao.createUser(user);
    }

    // -- READ --

    /**
     * Returns a {@link LiveData} of {@link User} tanhks to an id value in argument
     * @param userId an integer that contains the id value
     * @return a {@link LiveData} of {@link User}
     */
    public LiveData<User> getUser(final long userId) {
        return this.mUserDao.getUser(userId);
    }
}
