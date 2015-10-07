package com.example.nikunj.virtualwardrobe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kimo.lib.alexei.Alexei;
import com.kimo.lib.alexei.Answer;
import com.kimo.lib.alexei.Utils;
import com.kimo.lib.alexei.calculus.ColorPaletteCalculus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class GetCroppedPhoto extends FragmentActivity {



    ArrayList<String> mAllNames = new ArrayList<String>();
    int count=0;
    ArrayList<Float> mAllLValues = new ArrayList<Float>();
    ArrayList<Float> mAllAValues = new ArrayList<Float>();
    ArrayList<Float> mAllBValues = new ArrayList<Float>();
    ArrayList<Integer> mAllRedValues = new ArrayList<Integer>();
    ArrayList<Integer> mAllGreenValues = new ArrayList<Integer>();
    ArrayList<Integer> mAllBlueValues = new ArrayList<Integer>();
    ArrayList<Integer> mAllIDValues = new ArrayList<Integer>();


    public CIE_LAB lab1, Lab2;
    float DEL_E = 0.0f;
    float TEMP_DEL_E = 0.0f;
    float TEMP_RED, TEMP_GREEN, TEMP_BLUE, MAIN_R, MAIN_G, MAIN_B;
    //    final String TABLE_USER_DETAILS = "tb_colordata";
    TextView DeltaE_textView;

    String NAME = null, MAIN_NAME = null;


    public ImageView croppedImageView;
    private int redValue ,blueValue ,greenValue;

    private View mainView, progressView;

    private Spinner typeListSpinner,collectionListSpinner;

   // public static final String TAG = GetCroppedPhoto.class.getSimpleName();
    private FragmentManager fm = getSupportFragmentManager();;
    public Bitmap bitmapA,resized ;
    public TextView rgbText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLAB();

        setContentView(R.layout.activity_get_cropped_photo);

        FileInputStream fis;
        croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
        rgbText = (TextView) findViewById(R.id.rgbText);
        DeltaE_textView = (TextView) findViewById(R.id.calculate_DeltaE_textView);
        bitmapA = null;
        resized = null;

        try {
            fis = openFileInput("BITMAP_A");
            bitmapA = BitmapFactory.decodeStream(fis);
            fis.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        resized = Bitmap.createScaledBitmap(bitmapA, 500, 500, true);
        croppedImageView.setImageBitmap(resized);

        croppedImageView.setOnTouchListener(new ImageView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub


                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x < resized.getWidth() && y < resized.getHeight() && y >= 0 && x >= 0) {
                    int pixel = resized.getPixel(x, y);
                    redValue = Color.red(pixel);
                    blueValue = Color.blue(pixel);
                    greenValue = Color.green(pixel);

                    rgbText.setText("R:" + redValue + " G:" + greenValue + " B:" + blueValue);

                    Log.v("RGB", pixel + " :R: " + redValue + " G: " + greenValue + " B:" + blueValue);

                }

                int action = event.getActionMasked();
                if (action == event.ACTION_UP) {
                    Log.e("touch ended yay", "");

                    ConvertRGBtoLab convertRGBtoLab = new ConvertRGBtoLab(redValue, greenValue, blueValue);

                    lab1 = convertRGBtoLab.conversion();
                    Lab2 = new CIE_LAB();
                    count = 0;
                    DEL_E = Float.MAX_VALUE;
                    for (int i = 0; i < mAllIDValues.size(); i++) {

                        Lab2.L = mAllLValues.get(i);
                        Lab2.A = mAllAValues.get(i);
                        Lab2.B = mAllBValues.get(i);
                        TEMP_RED = mAllRedValues.get(i);
                        TEMP_GREEN = mAllGreenValues.get(i);
                        TEMP_BLUE = mAllBlueValues.get(i);
                        NAME = mAllNames.get(i);

                        DeltaECalculator deltaECalculator = new DeltaECalculator(lab1, Lab2);

                        TEMP_DEL_E = deltaECalculator.CIEDeltaE2000();

                        Log.e("finished", "" + count++);

                        if (DEL_E > TEMP_DEL_E || DEL_E == 0) {
                            DEL_E = TEMP_DEL_E;
                            MAIN_R = TEMP_RED;
                            MAIN_G = TEMP_GREEN;
                            MAIN_B = TEMP_BLUE;
                            MAIN_NAME = NAME;
                        }


                    }
                    DeltaE_textView.setText("DELTA E is = " + DEL_E + " R : " + MAIN_R + " G : " + MAIN_G + " B :" + MAIN_B + MAIN_NAME);

                }
                return true;
            }

        });
        configure();

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_cropped_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getLAB(){
        AssetDataBaseHelper assetdbhelper= new AssetDataBaseHelper(this);
        SQLiteDatabase db = assetdbhelper.getReadableDatabase();
        String columns[] = {DBOpenHelper.COLOR_DATA_ID ,
                DBOpenHelper.COLOR_DATA_NAME_COLUMN ,
                DBOpenHelper.COLOR_DATA_RED_VALUE ,
                DBOpenHelper.COLOR_DATA_GREEN_VALUE ,
                DBOpenHelper.COLOR_DATA_BLUE_VALUE ,
                DBOpenHelper.COLOR_DATA_L_VALUE ,
                DBOpenHelper.COLOR_DATA_A_VALUE ,
                DBOpenHelper.COLOR_DATA_B_VALUE};

        Cursor c = db.query("tb_colordata", columns, null, null, null, null, null);

        c.moveToFirst();

        while (c.moveToNext()) {
            Integer Idvalue = c.getInt(c.getColumnIndex(DbColumns.ID_VALUE));
            String name = c.getString(c.getColumnIndex(DbColumns.NAME_VALUE));
            Integer Redvalue = c.getInt(c.getColumnIndex(DbColumns.RED_VALUE));
            Integer Greenvalue = c.getInt(c.getColumnIndex(DbColumns.GREEN_VALUE));
            Integer Bluevalue = c.getInt(c.getColumnIndex(DbColumns.BLUE_VALUE));
            float L_value = c.getFloat(c.getColumnIndex(DbColumns.L_VALUE));
            float A_value = c.getFloat(c.getColumnIndex(DbColumns.A_VALUE));
            float B_value = c.getFloat(c.getColumnIndex(DbColumns.B_VALUE));

            mAllIDValues.add(Idvalue);
            mAllNames.add(name);
            mAllLValues.add(L_value);
            mAllAValues.add(A_value);
            mAllBValues.add(B_value);
            mAllRedValues.add(Redvalue);
            mAllGreenValues.add(Greenvalue);
            mAllBlueValues.add(Bluevalue);

        }
        c.close();

    }

    private void configure() {
        mainView = findViewById(R.id.main_container);
        progressView = findViewById(R.id.progress);
        croppedImageView = (ImageView) findViewById(R.id.croppedImageView);



        Alexei.with(GetCroppedPhoto.this)
                .analyze(croppedImageView)
                .perform(new ColorPaletteCalculus(resized,10))
                .showMe(new Answer<List<Integer>>() {
                    @Override
                    public void beforeExecution() {
                        mainView.setVisibility(View.GONE);
                        progressView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void afterExecution(List<Integer> answer, long elapsedTime) {

                        try {
                            fm.beginTransaction().replace(R.id.info_area, ColorPaletteResultsFragment.newInstance((java.util.ArrayList<Integer>) answer, elapsedTime)).commit();

                        } catch (NullPointerException e){}

                        mainView.setVisibility(View.VISIBLE);
                        progressView.setVisibility(View.GONE);
                    }

                    @Override
                    public void ifFails(Exception error) {
                        mainView.setVisibility(View.VISIBLE);
                        progressView.setVisibility(View.GONE);

                        Toast.makeText(GetCroppedPhoto.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
