package net.rubicon.indepth.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


import net.rubicon.indepth.Constant;

import java.util.HashMap;
import java.util.Map;


public class DataBaseProvider extends ContentProvider {
    public static final String LOG_TAG = DataBaseProvider.class.getName();

    //URI Matcher Targets

    private static final int URI_MATCH_TEAM = 3;
    private static final int URI_MATCH_TEAM_ID = 4;

    private static final int URI_MATCH_STRIKE_TEAM = 5;
    private static final int URI_MATCH_STRIKE_TEAM_ID = 6;

    private static final int URI_MATCH_PERSON = 7;
    private static final int URI_MATCH_PERSON_ID = 8;

    private DataBaseHelper _dbHelper;

    private Map<Integer, MatcherMetadata> _tablesLookup;

    private UriMatcher _uriMatcher;

    public DataBaseProvider() {
        super();

        _tablesLookup = new HashMap<>();

        _tablesLookup.put(URI_MATCH_TEAM, new MatcherMetadata(new TeamTable(), false));
        _tablesLookup.put(URI_MATCH_TEAM_ID, new MatcherMetadata(new TeamTable(), true));

        _tablesLookup.put(URI_MATCH_STRIKE_TEAM, new MatcherMetadata(new StrikeTeamTable(), false));
        _tablesLookup.put(URI_MATCH_STRIKE_TEAM_ID, new MatcherMetadata(new StrikeTeamTable(), true));

        _tablesLookup.put(URI_MATCH_PERSON, new MatcherMetadata(new PersonTable(), false));
        _tablesLookup.put(URI_MATCH_PERSON_ID, new MatcherMetadata(new PersonTable(), true));


        _uriMatcher = getUriMatcher();
    }

    /**
     * @return
     */
    private UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        for (Integer key : _tablesLookup.keySet()) {
            MatcherMetadata matcherMetadata = _tablesLookup.get(key);

            DataBaseTable dbTable = matcherMetadata.dataBaseTable;

            String path;

            path = (matcherMetadata.matchById)
                    ? dbTable.getTableName() + "/#" : dbTable.getTableName();

            uriMatcher.addURI(Constant.AUTHORITY, path, key);
        }

        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String id = "";

        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        MatcherMetadata matcherMetadata = _tablesLookup.get(_uriMatcher.match(uri));

        if (matcherMetadata != null) {
            DataBaseTable dbTable = matcherMetadata.dataBaseTable;

            if (matcherMetadata.matchById) {
                id = uri.getPathSegments().get(1);

                selection = getItemIdSelectionClause(dbTable.getIdColumnName(), id, selection);
            }

            count = db.delete(dbTable.getTableName(), selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private String getItemIdSelectionClause(String idColumn, String id, String selection) {
        String selectionClause = selection;

        if (id != null && !id.isEmpty()) {
            selectionClause = idColumn + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
        }

        return selectionClause;
    }

    @Override
    public String getType(Uri uri) {
        MatcherMetadata matcherMetadata = _tablesLookup.get(_uriMatcher.match(uri));

        if (matcherMetadata != null) {
            DataBaseTable dbTable = matcherMetadata.dataBaseTable;

            if (matcherMetadata.matchById) {
                return dbTable.getContentItemType();
            } else {
                return dbTable.getContentType();
            }
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        MatcherMetadata matcherMetadata = _tablesLookup.get(_uriMatcher.match(uri));

        if (matcherMetadata != null) {
            DataBaseTable dbTable = matcherMetadata.dataBaseTable;

            long rowId = db.insert(dbTable.getTableName(), null, values);

            if (rowId > 0) {
                Uri result = ContentUris.withAppendedId(dbTable.getContentUri(), rowId);
                getContext().getContentResolver().notifyChange(dbTable.getContentUri(), null);
                return result;
            }
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        throw new SQLException("insert failure:" + uri);
    }

    @Override
    public boolean onCreate() {
        _dbHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = sortOrder;

        MatcherMetadata matcherMetadata = _tablesLookup.get(_uriMatcher.match(uri));

        if (matcherMetadata != null) {
            DataBaseTable dbTable = matcherMetadata.dataBaseTable;
            qb.setTables(dbTable.getTableName());
            qb.setProjectionMap(dbTable.getProjectionMap());

            if (matcherMetadata.matchById) {
                qb.appendWhere(dbTable.getIdColumnName() + "=" + uri.getPathSegments().get(1));
            }

            if (sortOrder == null) {
                orderBy = dbTable.getDefaultSortOrder();
            }
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        String id;
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        String tableName = "";
        String whereClause = selection;

        MatcherMetadata matcherMetadata = _tablesLookup.get(_uriMatcher.match(uri));

        if (matcherMetadata != null) {
            DataBaseTable dbTable = matcherMetadata.dataBaseTable;

            tableName = dbTable.getTableName();

            if (matcherMetadata.matchById) {
                id = uri.getPathSegments().get(1);

                whereClause = getUpdateWhereClause(dbTable.getIdColumnName(), id, selection);
            }
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        count = db.update(tableName, values, whereClause, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    /**
     * @param idColumn
     * @param id
     * @param selection
     * @return
     */
    private String getUpdateWhereClause(String idColumn, String id, String selection) {
        return idColumn + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
    }

    private class MatcherMetadata {
        DataBaseTable dataBaseTable;
        boolean matchById;

        MatcherMetadata(DataBaseTable dataBaseTable, boolean matchById) {
            this.dataBaseTable = dataBaseTable;
            this.matchById = matchById;
        }
    }
}