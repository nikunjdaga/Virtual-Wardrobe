package com.example.nikunj.virtualwardrobe;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nikunj on 1/10/15.
 */
public class CreateDirectoryAndSavePhoto {
    public CreateDirectoryAndSavePhoto(){}
    public CreateDirectoryAndSavePhoto(Bitmap imageToSave) {

        String dateCreated = getPhotoCreateDate();

        String fileName= "image-" + dateCreated +".jpg";
        File direct = new File(Environment.getExternalStorageDirectory() + "/VirtualWardrobe");

        if (!direct.exists()) {
            File virtualWardrobeDirectory = new File(Environment.getExternalStorageDirectory().getPath() +"/VirtualWardrobe/");
            virtualWardrobeDirectory.mkdirs();
        }

        File file = new File(new File(Environment.getExternalStorageDirectory().getPath() +"/VirtualWardrobe/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            //imageToSave.sameAs();
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 0, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLocationPath(){

        String dateCreated = getPhotoCreateDate();

        String fileName= "image-" + dateCreated +".jpg";
        File direct = new File(Environment.getExternalStorageDirectory() + "/VirtualWardrobe");

        if (!direct.exists()) {
            File virtualWardrobeDirectory = new File(Environment.getExternalStorageDirectory().getPath() +"/VirtualWardrobe/");
            virtualWardrobeDirectory.mkdirs();
        }

        File file = new File(new File(Environment.getExternalStorageDirectory().getPath() +"/VirtualWardrobe/"), fileName);
        if (file.exists()) {
            file.delete();
        }

        String mainFilePath = Environment.getExternalStorageDirectory().getPath() +"/VirtualWardrobe/" + fileName;
        return mainFilePath;
    }
    public String getPhotoCreateDate(){

        DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        String date = df.format(Calendar.getInstance().getTime());
        return date;

    }
}
