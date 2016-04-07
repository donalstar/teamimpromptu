package net.rubicon.indepth.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_FILE_NAME = "indepth.db";
    public static final int DATABASE_VERSION = 1;

    public static final String LOG_TAG = DataBaseHelper.class.getName();

    public DataBaseHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
//        super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG, "onCreate");

        db.execSQL(PersonTable.CREATE_TABLE);
        db.execSQL(TeamTable.CREATE_TABLE);
        db.execSQL(StrikeTeamTable.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }
}
