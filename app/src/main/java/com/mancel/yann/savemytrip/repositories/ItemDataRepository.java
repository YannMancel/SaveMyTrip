package com.mancel.yann.savemytrip.repositories;

import android.arch.lifecycle.LiveData;

import com.mancel.yann.savemytrip.models.databases.dao.ItemDao;
import com.mancel.yann.savemytrip.models.pojos.Item;

import java.util.List;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.repositories
 */
public class ItemDataRepository {

    // FIELDS --------------------------------------------------------------------------------------

    private final ItemDao mItemDao;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param itemDao a {@link ItemDao}
     */
    public ItemDataRepository(ItemDao itemDao) {
        this.mItemDao = itemDao;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    /**
     * Inserts an {@link Item} in argument
     * @param item an {@link Item}
     * @return an integer that contains the id value of the tuple
     */
    public long insertItem(Item item) {
        return this.mItemDao.insertItem(item);
    }

    // -- READ --

    /**
     * Returns a {@link LiveData} of {@link List} of {@link Item} by the id value in argument
     * @param userId an integer that contains the id value
     * @return a {@link LiveData} of {@link List} of {@link Item}
     */
    public LiveData<List<Item>> getItems(final long userId) {
        return this.mItemDao.getItems(userId);
    }

    // -- UPDATE --

    /**
     * Updates the {@link Item} that has the same id that the {@link Item} in argument
     * @param item an {@link Item}
     * @return an integer that contains the updated line number
     */
    public int updateItem(Item item) {
        return this.mItemDao.updateItem(item);
    }

    // -- DELETE --

    /**
     * Deletes the {@link Item} that has the id in argument
     * @param itemId an integer that contains the id value
     * @return an integer that contains the deleted line number
     */
    public int deleteItem(long itemId) {
        return this.mItemDao.deleteItem(itemId);
    }
}
