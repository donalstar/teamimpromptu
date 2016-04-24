package net.teamimpromptu.fieldmanager.chain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.teamimpromptu.fieldmanager.db.DataBaseHelper;
import net.teamimpromptu.fieldmanager.db.DataBaseModel;
import net.teamimpromptu.fieldmanager.db.DataBaseTable;
import net.teamimpromptu.fieldmanager.db.PersonModel;
import net.teamimpromptu.fieldmanager.db.PersonTable;

/**
 *
 */
public class DataBaseFacade {
    public static final String LOG_TAG = DataBaseFacade.class.getName();

    private DataBaseHelper _dbh;

    /**
     * @param context
     */
    public DataBaseFacade(Context context) {
        _dbh = new DataBaseHelper(context);
    }

    /**
     * insert/update a database table from model
     *
     * @param rowId
     * @param tableName
     * @return
     */
    public int deleteModel(Long rowId, String tableName) {
        String selection = "_id=?";
        String selectionArgs[] = new String[]{rowId.toString()};

        SQLiteDatabase sqlDb = _dbh.getWritableDatabase();
        int count = sqlDb.delete(tableName, selection, selectionArgs);
        sqlDb.close();

        return count;
    }

    /**
     * insert/update a database table from model
     *
     * @param arg populated model
     * @return true is success
     */
    public boolean updateModel(DataBaseModel arg) {
        boolean flag = false;

        SQLiteDatabase sqlDb = _dbh.getWritableDatabase();

        if ((arg.getId() == null) || (arg.getId() < 1)) {
            //insert
            long result = sqlDb.insert(arg.getTableName(), null, arg.toContentValues());
            if (result > 0) {
                arg.setId(result);
                flag = true;
            }
        } else {
            //update
            String selection = "_id=?";
            String[] selectionArgs = new String[]{arg.getId().toString()};
            int result = sqlDb.update(arg.getTableName(), arg.toContentValues(), selection, selectionArgs);
            if (result > 0) {
                flag = true;
            }
        }

        sqlDb.close();

        return flag;
    }

    /**
     * Select a database row, return as model
     *
     * @param rowId   target row ID
     * @param dbTable target table
     * @return
     */
    public DataBaseModel selectModel(Long rowId, DataBaseTable dbTable) {
        String tableName = dbTable.getTableName();
        String[] projection = dbTable.getDefaultProjection();

        String selection = "_id=?";
        String[] selectionArgs = new String[]{rowId.toString()};

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(tableName, projection, selection, selectionArgs, null, null, null);

        DataBaseModel result = toModel(tableName);
        if (cursor.moveToFirst()) {
            result.fromCursor(cursor);
        } else {
            Log.i(LOG_TAG, "selection failure:" + rowId + ":" + dbTable.getTableName());
        }

        cursor.close();
        sqlDb.close();

        return result;
    }

    /**
     * Map cursor to model
     *
     * @param tableName
     * @return populated model
     */
    private DataBaseModel toModel(String tableName) {
        DataBaseModel result;

        switch (tableName) {

            case PersonTable.TABLE_NAME:
                result = new PersonModel();
                break;
            default:
                throw new IllegalArgumentException("unknown table:" + tableName);
        }

        result.setDefault();
        return result;
    }

    /**
     * @param userName
     * @return
     */
    public PersonModel selectPerson(String userName) {
        DataBaseTable table = new PersonTable();

        String[] projection = table.getDefaultProjection();
        String orderBy = table.getDefaultSortOrder();

        String selection = PersonTable.Columns.USERNAME + "=?";
        String[] selectionArgs = {userName};

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(table.getTableName(), projection, selection, selectionArgs, null, null, orderBy);

        PersonModel model = new PersonModel();
        model.setDefault();

        if (cursor.moveToFirst()) {
            model.fromCursor(cursor);
        }

        cursor.close();
        sqlDb.close();

        return model;
    }



}