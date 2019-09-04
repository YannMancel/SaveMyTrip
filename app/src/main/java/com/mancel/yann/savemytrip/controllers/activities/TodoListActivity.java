package com.mancel.yann.savemytrip.controllers.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mancel.yann.savemytrip.R;
import com.mancel.yann.savemytrip.models.pojos.Item;
import com.mancel.yann.savemytrip.views.recyclerViews.ItemAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Yann MANCEL on 06/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.controllers.activities
 *
 * A {@link BaseActivity} subclass which implements {@link ItemAdapter.Listener}.
 */
public class TodoListActivity extends BaseActivity implements ItemAdapter.Listener{

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.todo_list_activity_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.todo_list_activity_spinner) Spinner mSpinner;
    @BindView(R.id.todo_list_activity_edit_text) EditText mEditText;
    @BindView(R.id.todo_list_activity_header_profile_image) ImageView mProfileImage;
    @BindView(R.id.todo_list_activity_header_profile_text) TextView mProfileText;

    private List<Item> mItemList;
    private ItemAdapter mItemAdapter;

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_todo_list;
    }

    @Override
    protected void configureDesign() {
        // Configures the ToolBar field
        this.configureToolBar();

        // Configures the Spinner field
        this.configureSpinner();

        // Configures the RecyclerView field
        this.configureRecyclerView();
    }

    // LISTENER INTERFACE OF ITEM ADAPTER **********************************************************

    @Override
    public void onClickDeleteButton(int position) {}

    // ACTIONS *************************************************************************************

    @OnClick(R.id.todo_list_activity_button_add)
    public void onClickAddButton() { /*TODO*/ }

    // SPINNER *************************************************************************************

    /**
     * Configures the {@link Spinner}
     */
    private void configureSpinner() {
        // Creates an ArrayAdapter<CharSequence> object
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Updates the Spinner field
        this.mSpinner.setAdapter(adapter);
    }

    // RECYCLER VIEW *******************************************************************************

    /**
     * Configures the {@link RecyclerView}
     */
    private void configureRecyclerView() {
        // Initializes the ItemAdapter
        this.mItemAdapter = new ItemAdapter(this);

        // Attaches the adapter to the RecyclerView to populate items
        this.mRecyclerView.setAdapter(this.mItemAdapter);

        // Sets layout manager to position the items
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
