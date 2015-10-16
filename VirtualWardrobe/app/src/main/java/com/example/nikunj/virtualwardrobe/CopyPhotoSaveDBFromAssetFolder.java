package com.example.nikunj.virtualwardrobe;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nikunj on 12/10/15.
 */
public class CopyPhotoSaveDBFromAssetFolder {
    final static int CHAR_BUF_SIZE = 1024;
    final static String DATABASE_NAME = "photoSaveManager.db";

    public static void copyPhotoSaveAssetDatabase(Context context) {
        try {
            String db_dir = context.getApplicationInfo().dataDir + "/databases/";
            String db_file = db_dir + DATABASE_NAME;

            File deviceDb = new File(db_file);
            if (!deviceDb.exists()) {
                copyDataBasePhotoSave(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Copies the database file in asset folder to app folder
     *
     * @throws IOException
     */
    private static void copyDataBasePhotoSave(Context context) throws IOException {
        // open the local database
        InputStream copy = context.getAssets()
                .open(DATABASE_NAME);

        String db_dir = context.getApplicationInfo().dataDir + "/databases/";
        String db_file = db_dir + DATABASE_NAME;
        File dbPath = new File(db_dir);

        if (!dbPath.exists()) {
            dbPath.mkdir();
        }

        // Open the empty dbOut as the output stream
        OutputStream dbOut = new FileOutputStream(db_file);

        copyFilePhotoSave(copy, dbOut);
    }

    /**
     * Copies given input stream to output stream
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    private static void copyFilePhotoSave(InputStream inputStream,
                                 OutputStream outputStream) throws IOException {
        // copy database from the inputfile to the outputfile
        byte[] buffer = new byte[CHAR_BUF_SIZE];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        // Close the streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}
