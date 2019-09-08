package com.mancel.yann.savemytrip.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mancel.yann.savemytrip.repositories.ItemDataRepository;
import com.mancel.yann.savemytrip.repositories.UserDataRepository;
import com.mancel.yann.savemytrip.viewModels.ItemViewModel;

import java.util.concurrent.Executor;

/**
 * Created by Yann MANCEL on 05/09/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.injections
 *
 * A class which implements {@link ViewModelProvider.Factory}
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    // FIELDS --------------------------------------------------------------------------------------

    private final ItemDataRepository mItemDataRepository;
    private final UserDataRepository mUserDataRepository;
    private final Executor mExecutor;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param itemDataRepository a {@link ItemDataRepository}
     * @param userDataRepository a {@link UserDataRepository}
     * @param executor a {@link Executor}
     */
    public ViewModelFactory(ItemDataRepository itemDataRepository, UserDataRepository userDataRepository, Executor executor) {
        this.mItemDataRepository = itemDataRepository;
        this.mUserDataRepository = userDataRepository;
        this.mExecutor = executor;
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemViewModel.class)) {
            return (T) new ItemViewModel(this.mItemDataRepository,
                                         this.mUserDataRepository,
                                         this.mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
