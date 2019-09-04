package com.mancel.yann.savemytrip.models.pojos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Yann MANCEL on 07/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.models.pojos
 */
@Entity
public class User {

    // FIELDS --------------------------------------------------------------------------------------

    @PrimaryKey
    private long id;
    private String userName;
    private String urlPicture;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Initializes an User object
     * @param id an long integer that contains the user Id
     * @param userName a String object that contains the user name
     * @param urlPicture a String object that contains the URL of the picture
     */
    public User(long id, String userName, String urlPicture) {
        this.id = id;
        this.userName = userName;
        this.urlPicture = urlPicture;
    }

    // METHODS -------------------------------------------------------------------------------------

    public long getId() {
        return this.id;
    }
    public String getUserName() {
        return this.userName;
    }
    public String getUrlPicture() {
        return this.urlPicture;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }
}
