package com.example.nikunj.virtualwardrobe;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by root on 1/10/15.
 */
public class CreateDirectoryAndSaveFile {

    public CreateDirectoryAndSaveFile(Bitmap imageToSave) {

        DateFormat df = new SimpleDateFormat("yyMMddHHmmssZ");
        String date = df.format(Calendar.getInstance().getTime());

        String fileName= "image-" + date +".png";
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
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
