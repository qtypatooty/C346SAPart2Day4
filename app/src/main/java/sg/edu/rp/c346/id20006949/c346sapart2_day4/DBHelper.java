package sg.edu.rp.c346.id20006949.c346sapart2_day4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "psireading.db";
    private static final String TABLE_PSI = "psi";
    private static final String COLUMN_INDEX = "index";
    private static final String COLUMN_PLACE = "place";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPSISql = "CREATE TABLE " + TABLE_PSI +  "("
                + COLUMN_INDEX + " INTEGER,"
                + COLUMN_PLACE + " TEXT )";
        db.execSQL(createPSISql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_PSI  + " ADD COLUMN  module_name TEXT ");
        onCreate(db);
    }
    public long insertPsi(int index, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INDEX, index);
        values.put(COLUMN_PLACE, place);
        long result = db.insert(TABLE_PSI, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result); //id returned, shouldn’t be -1
        return result;
    }
    public ArrayList<PSIReading> getAllReadings() {
        ArrayList<PSIReading> readings = new ArrayList<PSIReading>();

        String selectQuery = "SELECT "
                + COLUMN_INDEX+ ","
                + COLUMN_PLACE +" FROM " + TABLE_PSI;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int index = cursor.getInt(0);
                String place = cursor.getString(1);

                PSIReading reading = new PSIReading( index, place);
                reading.setIndex(index);
                readings.add(reading);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return readings;

    }
    public ArrayList<PSIReading> getAllReadings(String keyword) {
        ArrayList<PSIReading> readings = new ArrayList<PSIReading>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_INDEX,COLUMN_PLACE};
        String condition = COLUMN_INDEX + " Like ?";
        String condition2 = COLUMN_PLACE + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_PSI, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int index = cursor.getInt(1);
                String place = cursor.getString(2);
                PSIReading reading= new PSIReading(index, place);
                readings.add(reading);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return readings;
    }
}
