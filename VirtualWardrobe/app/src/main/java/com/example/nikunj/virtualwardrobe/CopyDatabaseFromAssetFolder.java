package com.example.nikunj.virtualwardrobe;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDatabaseFromAssetFolder {
	final static int CHAR_BUF_SIZE = 1024;
	final static String DATABASE_NAME = "colorlist.db";

	public static void copyAssetDatabase(Context context) {
		try {
			String db_dir = context.getApplicationInfo().dataDir + "/databases/";
			String db_file = db_dir + DATABASE_NAME;
		
			File deviceDb = new File(db_file);
			if (!deviceDb.exists()) {
				copyDataBase(context);
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
	private static void copyDataBase(Context context) throws IOException {
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

		copyFile(copy, dbOut);
	}

	/**
	 * Copies given input stream to output stream
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	private static void copyFile(InputStream inputStream,
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

	//    public CIE_LAB calculateCIELAB() {
//
//
//        CIE_LAB lab2 = new CIE_LAB();
//        String s3 = "";
//        Cursor cursor = myDataBase.query((new StringBuilder(" tb_colordata where _id = 1")).append(s3).toString(), null, null, null, null, null, null);
//        cursor.moveToFirst();
//        String s7 = "", s8 = "", s9 = "";
//
//        while (cursor.moveToNext()) {
//            s7 = cursor.getString(cursor.getColumnIndex("_L"));
//            s8 = cursor.getString(cursor.getColumnIndex("_A"));
//            s9 = cursor.getString(cursor.getColumnIndex("_B"));
//
//            lab2.L = Float.parseFloat(s7);
//            lab2.A = Float.parseFloat(s8);
//            lab2.B = Float.parseFloat(s9);
//
//            Log.d("L value", lab2.L + "");
//            Log.d("A value", lab2.A + "");
//            Log.d("B value", lab2.B + "");
//        }
//
//
//        cursor.close();
//        myDataBase.close();
//        return lab2;
//    }
}
