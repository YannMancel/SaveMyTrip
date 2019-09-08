package com.mancel.yann.savemytrip.views.activities;

import android.content.Intent;

import com.mancel.yann.savemytrip.R;
import com.mancel.yann.savemytrip.controllers.activities.TodoListActivity;
import com.mancel.yann.savemytrip.controllers.activities.TripBookActivity;

import butterknife.OnClick;

/**
 * Created by Yann MANCEL on 06/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.controllers.activities
 *
 * A {@link BaseActivity} subclass.
 */
public class MainActivity extends BaseActivity {

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void configureDesign() {}

    // ACTIONS *************************************************************************************

    @OnClick(R.id.main_activity_button_trip_book)
    public void onClickTripBookButton() {
        Intent intent = new Intent(this, TripBookActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_activity_button_todo_list)
    public void onClickTodoListButton() {
        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }
}
