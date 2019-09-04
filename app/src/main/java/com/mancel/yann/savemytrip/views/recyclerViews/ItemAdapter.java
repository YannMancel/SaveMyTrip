package com.mancel.yann.savemytrip.views.recyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancel.yann.savemytrip.R;
import com.mancel.yann.savemytrip.models.pojos.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yann MANCEL on 07/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.views
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    // INTERFACES ----------------------------------------------------------------------------------

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    // FIELDS --------------------------------------------------------------------------------------

    private List<Item> mItemList;
    private Listener mListener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Initializes an ItemAdapter object
     * @param listener a ItemAdapter.Listener interface for the callback
     */
    public ItemAdapter(Listener listener) {
        this.mItemList = new ArrayList<>();
        this.mListener = listener;
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Creates a Context object to the LayoutInflater object
        Context context = viewGroup.getContext();

        // Creates an inflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // Creates the View thanks to the inflater
        View view = layoutInflater.inflate(R.layout.activity_todo_list_item, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        // Updates the item at the position i
        itemViewHolder.updateItem(this.mItemList.get(i), this.mListener);
    }

    @Override
    public int getItemCount() {
        return this.mItemList.size();
    }

    /**
     * Returns the Item at the position given in argument
     * @param position an integer that contains the position into the item list
     * @return an Item object that correspond to the item at the position given in argument
     */
    public Item getItem(final int position) {
        return this.mItemList.get(position);
    }

    /**
     * Updates the data of the item list
     * @param items a List<Item> object that contain the new item list
     */
    public void updateData(final List<Item> items) {
        // Updates the List<Item> field
        this.mItemList = items;

        // Refreshes the RecyclerView
        this.notifyDataSetChanged();
    }
}
