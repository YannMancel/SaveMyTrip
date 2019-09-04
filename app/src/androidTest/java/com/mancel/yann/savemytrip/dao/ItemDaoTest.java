package com.mancel.yann.savemytrip.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mancel.yann.savemytrip.models.databases.SaveMyTripDatabase;
import com.mancel.yann.savemytrip.models.pojos.Item;
import com.mancel.yann.savemytrip.models.pojos.User;
import com.mancel.yann.savemytrip.testUtil.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Yann MANCEL on 14/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip
 */
@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    // FIELDS --------------------------------------------------------------------------------------

    private SaveMyTripDatabase mDatabase;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private static long USER_ID = 1;
    private static User USER_DEMO = new User(USER_ID, "Yann", "https://www.google.fr");

    private static Item NEW_ITEM_PLACE_TO_VISIT = new Item("Visite cet endroit de rêve !", 0, USER_ID);
    private static Item NEW_ITEM_IDEA = new Item("On pourrait faire du chien de traîneau ?", 1, USER_ID);
    private static Item NEW_ITEM_RESTAURANTS = new Item("Ce restaurant à l'air sympa", 2, USER_ID);

    // METHODS -------------------------------------------------------------------------------------

    @Before
    public void initDatabase() throws Exception {
        // Initializes the database field
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                                                      SaveMyTripDatabase.class)
                             .allowMainThreadQueries()
                             .build();
    }

    @After
    public void closeDatabase() throws Exception {
        // Closes the database field
        this.mDatabase.close();
    }

    // USER TESTS **********************************************************************************

    @Test
    public void insertAndGetUser() throws InterruptedException {
        // Creates a new user (Insert)
        this.mDatabase.userDao().createUser(USER_DEMO);

        // Reads an user (Get)
        User user = LiveDataTestUtil.getValue(this.mDatabase.userDao().getUser(USER_ID));

        // Test
        assertTrue(user.getUserName().equals(USER_DEMO.getUserName()) && user.getId() == USER_ID);
    }

    // ITEM TESTS **********************************************************************************

    @Test
    public void getItemsWhenNoItemInserted() throws InterruptedException {
        // Reads items (Get)
        List<Item> items = LiveDataTestUtil.getValue(this.mDatabase.itemDao().getItems(USER_ID));

        // Test
        assertTrue(items.isEmpty());
    }

    @Test
    public void insertAndGetItems() throws InterruptedException {
        // Creates a new user (Insert)
        this.mDatabase.userDao().createUser(USER_DEMO);

        // Creates items (Insert)
        this.mDatabase.itemDao().insertItem(NEW_ITEM_PLACE_TO_VISIT);
        this.mDatabase.itemDao().insertItem(NEW_ITEM_IDEA);
        this.mDatabase.itemDao().insertItem(NEW_ITEM_RESTAURANTS);

        // Reads items (Get)
        List<Item> items = LiveDataTestUtil.getValue(this.mDatabase.itemDao().getItems(USER_ID));

        // Test
        assertTrue(items.size() == 3);
    }

    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // Creates a new user (Insert)
        this.mDatabase.userDao().createUser(USER_DEMO);

        // Creates an item (Insert)
        this.mDatabase.itemDao().insertItem(NEW_ITEM_PLACE_TO_VISIT);

        // Reads items (Get)
        Item itemAdded = LiveDataTestUtil.getValue(this.mDatabase.itemDao().getItems(USER_ID)).get(0);

        // Updates an item (Update)
        itemAdded.setSelected(true);
        this.mDatabase.itemDao().updateItem(itemAdded);

        // Reads items (Get)
        List<Item> items = LiveDataTestUtil.getValue(this.mDatabase.itemDao().getItems(USER_ID));

        // Test
        assertTrue(items.size() == 1 && items.get(0).getSelected());
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // Creates a new user (Insert)
        this.mDatabase.userDao().createUser(USER_DEMO);

        // Creates an item (Insert)
        this.mDatabase.itemDao().insertItem(NEW_ITEM_PLACE_TO_VISIT);

        // Reads an item et deletes this item (Get and Delete)
        Item itemAdded = LiveDataTestUtil.getValue(this.mDatabase.itemDao().getItems(USER_ID)).get(0);
        this.mDatabase.itemDao().deleteItem(itemAdded.getId());

        // Reads items (Get)
        List<Item> items = LiveDataTestUtil.getValue(this.mDatabase.itemDao().getItems(USER_ID));

        // Test
        assertTrue(items.isEmpty());
    }
}
