package net.teamimpromptu.fieldmanager.db;

import android.net.Uri;
import android.provider.BaseColumns;


import net.teamimpromptu.fieldmanager.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class TeamTable implements DataBaseTable {

    public static final String TABLE_NAME = "team";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.zandor." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.zandor." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.NAME + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.NAME + " TEXT NOT NULL,"
            + Columns.STATUS + " TEXT NOT NULL,"
            + Columns.LATITUDE + " FLOAT NOT NULL,"
            + Columns.LONGITUDE + " FLOAT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(TeamTable.Columns._ID, TeamTable.Columns._ID);
        PROJECTION_MAP.put(TeamTable.Columns.NAME, TeamTable.Columns.NAME);
        PROJECTION_MAP.put(TeamTable.Columns.STATUS, TeamTable.Columns.STATUS);
        PROJECTION_MAP.put(TeamTable.Columns.LATITUDE, TeamTable.Columns.LATITUDE);
        PROJECTION_MAP.put(TeamTable.Columns.LONGITUDE, TeamTable.Columns.LONGITUDE);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Uri getContentUri() { return CONTENT_URI; }


    @Override
    public String getContentType() { return CONTENT_TYPE; }

    @Override
    public String getContentItemType() { return CONTENT_ITEM_TYPE; }

    @Override
    public String getIdColumnName() { return Columns._ID; }

    @Override
    public String getDefaultSortOrder() {
        return DEFAULT_SORT_ORDER;
    }

    @Override
    public String[] getDefaultProjection() {
        Set<String> keySet = TeamTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }


    @Override
    public HashMap<String, String> getProjectionMap() { return PROJECTION_MAP; }
}
