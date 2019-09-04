package com.mancel.yann.savemytrip.views.recyclerViews;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mancel.yann.savemytrip.R;
import com.mancel.yann.savemytrip.models.pojos.Item;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yann MANCEL on 07/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.views
 */
public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.activity_todo_list_item_text) TextView mTextView;
    @BindView(R.id.activity_todo_list_item_image) ImageView mImageView;
    @BindView(R.id.activity_todo_list_item_remove) ImageButton mImageButton;

    private WeakReference<ItemAdapter.Listener> mCallback;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        // Using the ButterKnife library
        ButterKnife.bind(this, itemView);
    }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        // Calls the related Listener method (callback)
        ItemAdapter.Listener callback = this.mCallback.get();

        if (callback != null) {
            callback.onClickDeleteButton(getAdapterPosition());
        }
    }

    /**
     * Updates the item of the RecyclerView
     * @param item an Item object that contains all information on the item
     * @param listener a ItemAdapter.Listener interface for the callback
     */
    public void updateItem(final Item item, final ItemAdapter.Listener listener) {
        // Updates the TextView field (text)
        this.mTextView.setText(item.getText());

        if (item.getSelected()){
            this.mTextView.setPaintFlags(this.mTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            this.mTextView.setPaintFlags(this.mTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Updates the TextView field (image)
        switch (item.getCategory()) {
            // VISIT
            case 0: {
                this.mImageView.setBackgroundResource(R.drawable.ic_room_black_24px);
                break;
            }
            // IDEA
            case 1: {
                this.mImageView.setBackgroundResource(R.drawable.ic_lightbulb_outline_black_24px);
                break;
            }
            // RESTAURANT
            case 2: {
                this.mImageView.setBackgroundResource(R.drawable.ic_local_cafe_black_24px);
                break;
            }
        }

        // Adds a listener to the ImageButton field (whole class)
        this.mImageButton.setOnClickListener(this);

        // Initializes the WeakReference<ItemAdapter.Listener> field to use the callback method
        this.mCallback = new WeakReference<>(listener);
    }
}
