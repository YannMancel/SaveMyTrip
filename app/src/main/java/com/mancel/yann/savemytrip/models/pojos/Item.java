package com.mancel.yann.savemytrip.models.pojos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Yann MANCEL on 07/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.models.pojos
 */
@Entity(foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "id",
                                  childColumns = "userId"))
public class Item {

    // FIELDS --------------------------------------------------------------------------------------

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String text;
    private int category;
    private Boolean isSelected;
    private long userId;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Initializes an Item object
     * @param text a String object that contains the text
     * @param category an integer that contains the category type
     * @param userId an long integer that contains the user Id
     */
    public Item(String text, int category, long userId) {
        this.text = text;
        this.category = category;
        this.isSelected = false;
        this.userId = userId;
    }

    // METHODS -------------------------------------------------------------------------------------

    public long getId() {
        return this.id;
    }
    public String getText() {
        return this.text;
    }
    public int getCategory() {
        return this.category;
    }
    public Boolean getSelected() {
        return this.isSelected;
    }
    public long getUserId() {
        return this.userId;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public void setSelected(Boolean selected) {
        this.isSelected = selected;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
