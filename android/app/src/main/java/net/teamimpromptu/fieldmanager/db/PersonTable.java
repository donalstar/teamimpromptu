package net.teamimpromptu.fieldmanager.db;

import android.net.Uri;
import android.provider.BaseColumns;

import net.teamimpromptu.fieldmanager.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *

 */
public class PersonTable implements DataBaseTable {

    public static final String TABLE_NAME = "person";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.zandor." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.zandor." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.NAME + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.SERVER_ID + " INTEGER,"
            + Columns.NAME + " TEXT NOT NULL,"
            + Columns.USERNAME + " TEXT NOT NULL,"
            + Columns.ROLE + " TEXT NOT NULL,"
            + Columns.STATUS + " TEXT NOT NULL,"
            + Columns.SKILLS + " TEXT NOT NULL,"
            + Columns.CERTIFICATIONS + " TEXT NOT NULL,"
            + Columns.TEAM + " TEXT NOT NULL,"
            + Columns.LATITUDE + " FLOAT NOT NULL,"
            + Columns.LONGITUDE + " FLOAT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String SERVER_ID = "server_id";
        public static final String NAME = "name";
        public static final String USERNAME = "username";
        public static final String ROLE = "role";
        public static final String STATUS = "status";
        public static final String SKILLS = "skills";
        public static final String CERTIFICATIONS = "certifications";
        public static final String TEAM = "team";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(PersonTable.Columns._ID, PersonTable.Columns._ID);
        PROJECTION_MAP.put(PersonTable.Columns.SERVER_ID, PersonTable.Columns.SERVER_ID);
        PROJECTION_MAP.put(PersonTable.Columns.NAME, PersonTable.Columns.NAME);
        PROJECTION_MAP.put(PersonTable.Columns.USERNAME, PersonTable.Columns.USERNAME);
        PROJECTION_MAP.put(PersonTable.Columns.ROLE, PersonTable.Columns.ROLE);
        PROJECTION_MAP.put(PersonTable.Columns.STATUS, PersonTable.Columns.STATUS);
        PROJECTION_MAP.put(Columns.SKILLS, Columns.SKILLS);
        PROJECTION_MAP.put(Columns.CERTIFICATIONS, Columns.CERTIFICATIONS);
        PROJECTION_MAP.put(Columns.TEAM, PersonTable.Columns.TEAM);
        PROJECTION_MAP.put(Columns.LATITUDE, PersonTable.Columns.LATITUDE);
        PROJECTION_MAP.put(Columns.LONGITUDE, PersonTable.Columns.LONGITUDE);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Uri getContentUri() {
        return CONTENT_URI;
    }


    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public String getContentItemType() {
        return CONTENT_ITEM_TYPE;
    }

    @Override
    public String getIdColumnName() {
        return Columns._ID;
    }

    @Override
    public String getDefaultSortOrder() {
        return DEFAULT_SORT_ORDER;
    }

    @Override
    public String[] getDefaultProjection() {
        Set<String> keySet = PersonTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }


    @Override
    public HashMap<String, String> getProjectionMap() {
        return PROJECTION_MAP;
    }
}
