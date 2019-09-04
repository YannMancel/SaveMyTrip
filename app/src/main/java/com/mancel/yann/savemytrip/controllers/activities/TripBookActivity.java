package com.mancel.yann.savemytrip.controllers.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mancel.yann.savemytrip.R;
import com.mancel.yann.savemytrip.models.utils.StorageUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Yann MANCEL on 06/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.controllers.activities
 *
 * A {@link BaseActivity} subclass.
 */
public class TripBookActivity extends BaseActivity {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.trip_book_activity_external_choice) LinearLayout linearLayoutExternalChoice;
    @BindView(R.id.trip_book_activity_internal_choice) LinearLayout linearLayoutInternalChoice;
    @BindView(R.id.trip_book_activity_radio_external) RadioButton radioButtonExternalChoice;
    @BindView(R.id.trip_book_activity_radio_public) RadioButton radioButtonExternalPublicChoice;
    @BindView(R.id.trip_book_activity_radio_volatile) RadioButton radioButtonInternalVolatileChoice;
    @BindView(R.id.trip_book_activity_edit_text) EditText editText;

    private static final String FILENAME = "tripBook.txt";
    private static final String FOLDERNAME = "bookTrip";

    private static final int RC_STORAGE_WRITE_PERMS = 100;

    private static final String AUTHORITY="com.mancel.yann.savemytrip.fileprovider";

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_trip_book;
    }

    @Override
    protected void configureDesign() {
        // Configures the ToolBar field
        this.configureToolBar();

        // Reads from storage when starting
        this.readFromStorage();
    }

    // MENU ****************************************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creates a MenuInflater object to add the menu xml file to this activity
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Depending on the item Id
        switch (item.getItemId()) {

            case R.id.action_share: {
                // Shares the file
                this.shareFile();
                return true;
            }
            case R.id.action_save: {
                // Saves the text into the file
                this.save();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    // PERMISSION **********************************************************************************

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Uses EasyPermissions library [For android versions >= API 23]
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // ACTION **************************************************************************************

    @OnCheckedChanged({R.id.trip_book_activity_radio_internal, R.id.trip_book_activity_radio_external,
                       R.id.trip_book_activity_radio_private, R.id.trip_book_activity_radio_public,
                       R.id.trip_book_activity_radio_normal, R.id.trip_book_activity_radio_volatile})
    public void onClickRadioButton(CompoundButton button, boolean isChecked){
        if (isChecked) {
            switch (button.getId()) {
                case R.id.trip_book_activity_radio_internal: {
                    this.linearLayoutExternalChoice.setVisibility(View.GONE);
                    this.linearLayoutInternalChoice.setVisibility(View.VISIBLE);
                    break;
                }
                case R.id.trip_book_activity_radio_external: {
                    this.linearLayoutExternalChoice.setVisibility(View.VISIBLE);
                    this.linearLayoutInternalChoice.setVisibility(View.GONE);
                    break;
                }
            }
        }

        // Reads from storage after user clicked on radio buttons
        this.readFromStorage();
    }

    // USEFUL **************************************************************************************

    /**
     * Saves the text into the file
     */
    private void save() {
        if (this.radioButtonExternalChoice.isChecked()) {
            this.writeOnExternalStorage();
        }
        else {
            this.writeOnInternalStorage();
        }
    }

    /**
     * Shares the file
     */
    private void shareFile() {
        // Internal - Normal
        File internalFile = StorageUtils.getFileFromStorage(getFilesDir(),this, FILENAME, FOLDERNAME);

        // Retrieves the Uri object for the file
        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), AUTHORITY, internalFile);

        // Creates an Intent to share the file
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, contentUri);

        startActivity(Intent.createChooser(sharingIntent, getString(R.string.trip_book_share)));
    }

    // STORAGE *************************************************************************************

    /**
     * Reads the file from storage
     */
    @AfterPermissionGranted(RC_STORAGE_WRITE_PERMS)
    private void readFromStorage(){
        // Checks if the permission is accepted
        if (!EasyPermissions.hasPermissions(this, WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission_files_acess), RC_STORAGE_WRITE_PERMS, WRITE_EXTERNAL_STORAGE);
            return;
        }

        // Checks the button state
        if (this.radioButtonExternalChoice.isChecked()){
            if (StorageUtils.isExternalStorageReadable()){
                // EXTERNAL
                if (this.radioButtonExternalPublicChoice.isChecked()){
                    // External - Public
                    this.editText.setText(StorageUtils.getTextFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),this, FILENAME, FOLDERNAME));
                } else {
                    // External - Private
                    this.editText.setText(StorageUtils.getTextFromStorage(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), this, FILENAME, FOLDERNAME));
                }
            }
        } else {
            // INTERNAL
            if (this.radioButtonInternalVolatileChoice.isChecked()) {
                // Internal - Cache
                this.editText.setText(StorageUtils.getTextFromStorage(getCacheDir(), this, FILENAME, FOLDERNAME));
            }
            else {
                // Internal - Normal
                this.editText.setText(StorageUtils.getTextFromStorage(getFilesDir(), this, FILENAME, FOLDERNAME));
            }
        }
    }

    /**
     * Writes the file to external storage
     */
    private void writeOnExternalStorage(){
        if (StorageUtils.isExternalStorageWritable()){
            if (this.radioButtonExternalPublicChoice.isChecked()){
                StorageUtils.setTextInStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
            } else {
                StorageUtils.setTextInStorage(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
            }
        } else {
            Toast.makeText(this, getString(R.string.external_storage_impossible_create_file), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Writes the file to internal storage
     */
    private void writeOnInternalStorage(){
        if (this.radioButtonInternalVolatileChoice.isChecked()){
            StorageUtils.setTextInStorage(getCacheDir(), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
        } else {
            StorageUtils.setTextInStorage(getFilesDir(), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
        }
    }
}
