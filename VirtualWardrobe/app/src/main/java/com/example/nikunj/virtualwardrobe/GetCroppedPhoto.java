package com.example.nikunj.virtualwardrobe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kimo.lib.alexei.Alexei;
import com.kimo.lib.alexei.Answer;
import com.kimo.lib.alexei.calculus.ColorPaletteCalculus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.CompoundButton.OnCheckedChangeListener;

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
    float TEMP_RED, TEMP_GREEN, TEMP_BLUE, MAIN_R, MAIN_G, MAIN_B,MAIN_L_VALUE,MAIN_A_VALUE,MAIN_B_VALUE;
    //    final String TABLE_USER_DETAILS = "tb_colordata";
    TextView DeltaE_textView;

    String NAME = null, MAIN_NAME = null;


    public ImageView croppedImageView;
    private int redValue ,blueValue ,greenValue;

    private View mainView, progressView;

    private Spinner typeListSpinner,collectionListSpinner;

    private Switch isFavourite;

    private EditText description;



    private Button savePhoto;

    private String descriptionText, photoLocationPath;

    public Integer isFavouriteInteger = 0;

    SavePhotoDBOpenHelper savePhotoDB;

    private ArrayList<String> typeAllListItems = new ArrayList<String>();
    private ArrayList<String> collectionAllListItems = new ArrayList<String>();

    ArrayList<Integer> collectionListNamesId = new ArrayList<>();
    ArrayList<Integer> typesListNamesId = new ArrayList<>();

    Integer mainBracketNameId;

   // public static final String TAG = GetCroppedPhoto.class.getSimpleName();
    private FragmentManager fm = getSupportFragmentManager();;
    public Bitmap bitmapA,resized ;
    public TextView rgbText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savePhotoDB = SavePhotoDBOpenHelper.getInstance(this);
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
//        catch (OutOfMemoryError e) {
//            e.printStackTrace();
//        }


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

                    //Log.v("RGB", pixel + " :R: " + redValue + " G: " + greenValue + " B:" + blueValue);

                }

                int action = event.getActionMasked();
                if (action == event.ACTION_UP) {
                   // Log.e("touch ended yay", "");

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

                        //Log.e("finished", "" + count++);

                        if (DEL_E > TEMP_DEL_E || DEL_E == 0) {
                            DEL_E = TEMP_DEL_E;
                            MAIN_R = TEMP_RED;
                            MAIN_G = TEMP_GREEN;
                            MAIN_B = TEMP_BLUE;
                            MAIN_NAME = NAME;
                            MAIN_L_VALUE = Lab2.L;
                            MAIN_A_VALUE = Lab2.A;
                            MAIN_B_VALUE = Lab2.B;
                        }


                    }
                    DeltaE_textView.setText("DELTA E is = " + DEL_E + " R : " + MAIN_R + " G : " + MAIN_G + " B :" + MAIN_B + MAIN_NAME);


                }
                return true;
            }

        });


        configure();



        typeListSpinner = (Spinner) findViewById(R.id.typeListSpinner);

        //Array List for spinner type
        ArrayAdapter<String> typeListDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getTypeAllListItemNames(savePhotoDB.getAllTypeListItem(getApplicationContext())));
        typeListDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeListSpinner.setAdapter(typeListDataAdapter);

        collectionListSpinner = (Spinner) findViewById(R.id.collectionListSpinner);

        //Array List for spinner collection
        ArrayAdapter<String> collectionsListArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getCollectionAllListItemNames(savePhotoDB.getAllCollectionListItem(getApplicationContext())));
        collectionsListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collectionListSpinner.setAdapter(collectionsListArrayAdapter);


        isFavourite = (Switch) findViewById(R.id.favouriteSwitch);

        //set the switch to OFF
        isFavourite.setChecked(false);
        //attach a listener to check for changes in state
        isFavourite.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){

                    isFavouriteInteger = 1;

                }else{
                    isFavouriteInteger = 0;
                }

            }
        });

        //check the current state before we display the screen
        if(isFavourite.isChecked()){
            isFavouriteInteger = 1;
        }
        else {
            isFavouriteInteger = 0;
        }



         final String  photoCreatedDate = new CreateDirectoryAndSavePhoto().getPhotoCreateDate();
         final Float photoCreatedDateInteger = Float.parseFloat(photoCreatedDate);
        //Log.e("Photo Created Date ",photoCreatedDate);

        photoLocationPath = new CreateDirectoryAndSavePhoto().getLocationPath();

        description = (EditText) findViewById(R.id.descriptionEdittext);

        descriptionText = description.getText().toString();
        Log.e("descriptionText",descriptionText);


        savePhoto = (Button) findViewById(R.id.savePhoto);

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                String typeListSpinnerString = String.valueOf(typeListSpinner.getSelectedItem());
//                String collectionListSpinnerString = String.valueOf(collectionListSpinner.getSelectedItem());
                Integer typeListSpinnerInteger = typesListNamesId.get(typeListSpinner.getSelectedItemPosition());
                Integer collectionListSpinnerInteger = collectionListNamesId.get(collectionListSpinner.getSelectedItemPosition());

                //Log.e("typeListItem",typeListSpinnerString);
                final Integer mainNameId = getMainBracketNameId();

                savePhotoDB.addPhotoItem(getApplicationContext(), new SavedPhotoUtil(descriptionText, isFavouriteInteger,
                        photoCreatedDateInteger, photoLocationPath, MAIN_NAME,
                        typeListSpinnerInteger, collectionListSpinnerInteger, mainNameId));
                Toast.makeText(GetCroppedPhoto.this, "Photo Added", Toast.LENGTH_SHORT).show();


                finish();
                Intent intent = new Intent(getApplicationContext(), MainViewPager.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });

        savePhotoDB.close();


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        bitmapA.recycle();
        savePhotoDB.close();
        System.gc();
    }



    public ArrayList<String> getTypeAllListItemNames(List<TypeList> typeListItems){

        ArrayList<String> typeListNames = new ArrayList<>();

        for (int i = 0; i < typeListItems.size(); i++) {
            typeListNames.add(typeListItems.get(i).getTypeName());
            typesListNamesId.add(typeListItems.get(i).getTypeListItemId());
            //add(typeListItems.get(i).getTypeName());
           // Log.e("Type List Names added",typeListNames.get(i));
        }

        return typeListNames;

    }

    public ArrayList<String> getCollectionAllListItemNames(List<CollectionsList> collectionListItems){

        ArrayList<String> collectionListNames = new ArrayList<>();

        for (int i = 0; i < collectionListItems.size(); i++) {
            collectionListNames.add(collectionListItems.get(i).getCollectionsName());
            collectionListNamesId.add(collectionListItems.get(i).getCollectionListItemId());
        }

        return collectionListNames;

    }





    public void getLAB(){
        AssetDataBaseHelper assetdbhelper = AssetDataBaseHelper.getInstance(this);
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
        db.close();
    }

    private Integer getMainBracketNameId(){

        Integer listSize = savePhotoDB.getColorMainListCount(this);

        CIE_LAB MainL1 = new CIE_LAB();
        CIE_LAB MainL2 = new CIE_LAB();

        MainL1.L = MAIN_L_VALUE;
        MainL1.A = MAIN_A_VALUE;
        MainL1.B = MAIN_B_VALUE;

        Float TEMP_DEL_E_MAIN = Float.MAX_VALUE;
        Float DEL_E_MAIN;

        List<ColorMainList> colorMainListDBItems = savePhotoDB.getAllColorMainListItem(getApplicationContext());

        for (Integer k = 0; k < listSize; k++){

            MainL2.L = colorMainListDBItems.get(k).getColorMainLvalue();
            MainL2.A = colorMainListDBItems.get(k).getColorMainAvalue();
            MainL2.B = colorMainListDBItems.get(k).getColorMainBvalue();

            DeltaECalculator deltaECalculatorMain = new DeltaECalculator(MainL1, MainL2);
           // Log.e("for loop chala",k + "");
            DEL_E_MAIN = deltaECalculatorMain.CIEDeltaE2000();

            if(TEMP_DEL_E_MAIN > DEL_E_MAIN) {
               // Log.e("if loop chala"," main name set");
                TEMP_DEL_E_MAIN = DEL_E_MAIN;
                mainBracketNameId = colorMainListDBItems.get(k).getColorMainListItemId();
            }

        }
        savePhotoDB.close();
        return mainBracketNameId;
    }

    private void configure() {
        mainView = findViewById(R.id.main_container);
        progressView = findViewById(R.id.progress);
        croppedImageView = (ImageView) findViewById(R.id.croppedImageView);



        Alexei.with(GetCroppedPhoto.this)
                .analyze(croppedImageView)
                .perform(new ColorPaletteCalculus(resized,5))
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
//                            Log.e("ColorListInteger",answer + "");

                        } catch (NullPointerException e) {
                        }

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
}
