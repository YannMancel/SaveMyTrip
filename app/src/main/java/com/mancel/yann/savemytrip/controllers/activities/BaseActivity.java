package com.mancel.yann.savemytrip.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Yann MANCEL on 06/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.controllers.activities
 *
 * A {@link AppCompatActivity} subclass.
 */
public abstract class BaseActivity extends AppCompatActivity {

    // METHODS -------------------------------------------------------------------------------------

    protected abstract int getActivityLayout();
    protected abstract void configureDesign();

    // ACTIVITY ************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associates the layout file to this class
        setContentView(this.getActivityLayout());

        // Using the ButterKnife library
        ButterKnife.bind(this);

        // configures the design of the activity
        this.configureDesign();
    }

    // TOOLBAR *************************************************************************************

    /**
     * Configures the ToolBar in adding the Up button
     */
    protected void configureToolBar() {
        // Gets a Support ActionBar object corresponding to this ToolBar
        ActionBar actionBar = getSupportActionBar();

        // Enables the Up Button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
