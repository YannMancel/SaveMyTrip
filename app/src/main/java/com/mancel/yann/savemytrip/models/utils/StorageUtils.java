package com.mancel.yann.savemytrip.models.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.mancel.yann.savemytrip.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Yann MANCEL on 06/06/2019.
 * Name of the project: SaveMyTrip
 * Name of the package: com.mancel.yann.savemytrip.models
 */
public abstract class StorageUtils {

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Creates or gets a File object path
     * @param destination a File object that contains the destination path
     * @param fileName a String object that contains the file name
     * @param folderName a String object that contains the folder name
     * @return a File object that contains the file path
     */
    private static File createOrGetFile(File destination, String fileName, String folderName) {
        File folder = new File(destination, folderName);

        return new File(folder, fileName);
    }

    /**
     * Reads on the file
     * @param context a Context object that contains the activity context
     * @param file a File object that contains the file path
     * @return a String object that contains all the file text
     */
    private static String readOnFile(Context context, File file){

        String result = null;

        if (file.exists()) {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(file));
                try {
                    StringBuilder sb = new StringBuilder();

                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }
                    result = sb.toString();
                }
                finally {
                    br.close();
                }
            }
            catch (IOException e) {
                Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG).show();
            }
        }

        return result;
    }

    /**
     * Writes on the file
     * @param context a Context object that contains the activity context
     * @param text a String object that contains the text
     * @param file a File object that contains the file path
     */
    private static void writeOnFile(Context context, String text, File file){

        try {
            // Creates the folder
            file.getParentFile().mkdirs();

            FileOutputStream fos = new FileOutputStream(file);
            Writer w = new BufferedWriter(new OutputStreamWriter(fos));

            try {
                w.write(text);
                w.flush();
                fos.getFD().sync();
            } finally {
                w.close();
                Toast.makeText(context, context.getString(R.string.saved), Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Retrieves the text from a file
     * @param rootDestination a File object that contains the destination path
     * @param context a Context object that contains the activity context
     * @param fileName a String object that contains the file name
     * @param folderName a String object that contains the folder name
     * @return a String object that contains all the file text
     */
    public static String getTextFromStorage(File rootDestination, Context context, String fileName, String folderName){
        File file = createOrGetFile(rootDestination, fileName, folderName);
        return readOnFile(context, file);
    }

    /**
     * Writes a text into a file
     * @param rootDestination a File object that contains the destination path
     * @param context a Context object that contains the activity context
     * @param fileName a String object that contains the file name
     * @param folderName a String object that contains the folder name
     * @param text a String object that contains the text
     */
    public static void setTextInStorage(File rootDestination, Context context, String fileName, String folderName, String text){
        File file = createOrGetFile(rootDestination, fileName, folderName);
        writeOnFile(context, text, file);
    }

    // EXTERNAL STORAGE ****************************************************************************

    /**
     * Checks if the external storage is writable
     * @return a boolean value
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    /**
     * Checks if the external storage is readable
     * @return a boolean value
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // FILE ****************************************************************************************

    /**
     * Gets the file from the storage
     * @param rootDestination a File object that contains the destination path
     * @param context a Context object that contains the activity context
     * @param fileName a String object that contains the file name
     * @param folderName a String object that contains the folder name
     * @return a File object that contains the file path
     */
    public static File getFileFromStorage(File rootDestination, Context context, String fileName, String folderName) {
        return createOrGetFile(rootDestination, fileName, folderName);
    }
}
