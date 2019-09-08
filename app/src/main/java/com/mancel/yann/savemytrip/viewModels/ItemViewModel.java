package com.mancel.yann.savemytrip.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.mancel.yann.savemytrip.models.pojos.Item;
import com.mancel.yann.savemytrip.models.pojos.User;
import com.mancel.yann.savemytrip.repositories.ItemDataRepository;
import com.mancel.yann.savemytrip.repositories.UserDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.viewModels
 *
 * A {@link ViewModel} subclass.
 */
public class ItemViewModel extends ViewModel {

    // FIELDS --------------------------------------------------------------------------------------

    private final ItemDataRepository mItemDataRepository;
    private final UserDataRepository mUserDataRepository;
    private final Executor mExecutor;

    @Nullable
    private LiveData<User> mCurrentUser;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param itemDataRepository a {@link ItemDataRepository}
     * @param userDataRepository a {@link UserDataRepository}
     * @param executor a {@link Executor}
     */
    public ItemViewModel(ItemDataRepository itemDataRepository, UserDataRepository userDataRepository, Executor executor) {
        this.mItemDataRepository = itemDataRepository;
        this.mUserDataRepository = userDataRepository;
        this.mExecutor = executor;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- INIT --

    public void init(final long userId) {
        if (this.mCurrentUser != null) {
            return;
        }

        this.mCurrentUser = this.mUserDataRepository.getUser(userId);
    }

    // -- USER --

    public LiveData<User> getUser(final long userId) {
        return this.mCurrentUser;
    }

    // -- ITEM --

    public void createItem(final Item item) {
        this.mExecutor.execute(() -> mItemDataRepository.insertItem(item));
    }

    public LiveData<List<Item>> getItems(final long userId) {
        return this.mItemDataRepository.getItems(userId);
    }

    public void deleteItem(final long itemId) {
        this.mExecutor.execute(() -> mItemDataRepository.deleteItem(itemId));
    }

    public void updateItem(final Item item) {
        this.mExecutor.execute(() -> mItemDataRepository.updateItem(item));
    }
}
