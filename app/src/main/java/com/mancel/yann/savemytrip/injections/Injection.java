package com.mancel.yann.savemytrip.injections;

import android.content.Context;

import com.mancel.yann.savemytrip.models.databases.SaveMyTripDatabase;
import com.mancel.yann.savemytrip.repositories.ItemDataRepository;
import com.mancel.yann.savemytrip.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.injections
 */
public abstract class Injection {

    // METHODS -------------------------------------------------------------------------------------

    // -- ITEM REPOSITORY --

    public static ItemDataRepository provideItemDataRepository(final Context context) {
        SaveMyTripDatabase database = SaveMyTripDatabase.getInstance(context);

        return new ItemDataRepository(database.itemDao());
    }

    // -- USER REPOSITORY --

    public static UserDataRepository provideUserDataRepository(final Context context) {
        SaveMyTripDatabase database = SaveMyTripDatabase.getInstance(context);

        return new UserDataRepository(database.userDao());
    }

    // -- EXECUTOR --

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    // -- VIEW MODEL FACTORY--

    public static ViewModelFactory provideViewModelFactory(final Context context) {
        ItemDataRepository itemData = provideItemDataRepository(context);
        UserDataRepository userData = provideUserDataRepository(context);
        Executor executor = provideExecutor();

        return new ViewModelFactory(itemData, userData, executor);
    }
}
