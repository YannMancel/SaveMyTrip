package com.mancel.yann.savemytrip.controllers.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mancel.yann.savemytrip.R;
import com.mancel.yann.savemytrip.injections.Injection;
import com.mancel.yann.savemytrip.injections.ViewModelFactory;
import com.mancel.yann.savemytrip.models.pojos.Item;
import com.mancel.yann.savemytrip.models.pojos.User;
import com.mancel.yann.savemytrip.utils.ItemClickSupport;
import com.mancel.yann.savemytrip.viewModels.ItemViewModel;
import com.mancel.yann.savemytrip.views.activities.BaseActivity;
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

    private ItemAdapter mAdapter;
    private ItemViewModel mItemViewModel;

    private static int USER_ID = 1;

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_todo_list;
    }

    @Override
    protected void configureDesign() {
        this.configureToolBar();
        this.configureSpinner();
        this.configureRecyclerView();
        this.configureViewModel();
        this.getCurrentUser(USER_ID);
        this.getItems(USER_ID);
    }

    // LISTENER INTERFACE OF ITEM ADAPTER **********************************************************

    @Override
    public void onClickDeleteButton(int position) {
        this.deleteItem(this.mAdapter.getItem(position));
    }

    // -- ACTIONS --

    @OnClick(R.id.todo_list_activity_button_add)
    public void onClickAddButton() {
        this.createItem();
    }

    // -- SPINNER --

    /**
     * Configures the {@link Spinner}
     */
    private void configureSpinner() {
        // ADAPTER
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // SPINNER
        this.mSpinner.setAdapter(adapter);
    }

    // -- RECYCLER VIEW --

    /**
     * Configures the {@link RecyclerView}
     */
    private void configureRecyclerView() {
        // ADAPTER
        this.mAdapter = new ItemAdapter(this);

        // RECYCLER VIEW
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(this.mRecyclerView, R.layout.activity_todo_list_item)
                        .setOnItemClickListener((recyclerView1, position, v) -> this.updateItem(this.mAdapter.getItem(position)));
    }

    // -- VIEW MODEL --

    /**
     * Configures the {@link android.arch.lifecycle.ViewModel}
     */
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);

        this.mItemViewModel = ViewModelProviders.of(this, viewModelFactory)
                                                .get(ItemViewModel.class);

        this.mItemViewModel.init(USER_ID);
    }

    // -- USERS --

    private void getCurrentUser(final long userId) {
        this.mItemViewModel.getUser(userId).observe(this, this::updateHeader);
    }

    // -- ITEMS --

    private void getItems(final long userId) {
        this.mItemViewModel.getItems(userId).observe(this, this::updateItemsList);
    }

    private void createItem(){
        Item item = new Item(this.mEditText.getText().toString(),
                             this.mSpinner.getSelectedItemPosition(),
                             USER_ID);
        this.mEditText.setText("");
        this.mItemViewModel.createItem(item);
    }

    private void deleteItem(Item item){
        this.mItemViewModel.deleteItem(item.getId());
    }

    private void updateItem(Item item){
        item.setSelected(!item.getSelected());
        this.mItemViewModel.updateItem(item);
    }

    // -- UI --

    private void updateHeader(User user){
        this.mProfileText.setText(user.getUserName());

        Glide.with(this)
             .load(user.getUrlPicture())
             .apply(RequestOptions.circleCropTransform())
             .into(this.mProfileImage);
    }

    private void updateItemsList(List<Item> items){
        this.mAdapter.updateData(items);
    }
}
