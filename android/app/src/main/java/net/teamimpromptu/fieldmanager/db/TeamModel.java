package net.teamimpromptu.fieldmanager.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;

import java.io.Serializable;

/**
 *
 */
public class TeamModel implements DataBaseModel, Serializable {
    public static final String PROVIDER = "FieldManager";


    private Long _id;
    private String _name;
    transient private Location _location;
    private StatusEnum _status;

    @Override
    public void setDefault() {
        _id = 0L;

        _name = "Unknown";
        _location = new Location(PROVIDER);
        _status = StatusEnum.UNKNOWN;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(TeamTable.Columns.NAME, _name);
        cv.put(TeamTable.Columns.LATITUDE, _location.getLatitude());
        cv.put(TeamTable.Columns.LONGITUDE, _location.getLongitude());
        cv.put(TeamTable.Columns.STATUS, _status.toString());

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(TeamTable.Columns._ID));
        _name = cursor.getString(cursor.getColumnIndex(TeamTable.Columns.NAME));

        _location = new Location(PROVIDER);
        _location.setLatitude(cursor.getDouble(cursor.getColumnIndex(TeamTable.Columns.LATITUDE)));
        _location.setLongitude(cursor.getDouble(cursor.getColumnIndex(TeamTable.Columns.LONGITUDE)));

        _status
                = StatusEnum.discoverMatchingEnum(
                cursor.getString((cursor.getColumnIndex(TeamTable.Columns.STATUS))));
    }

    @Override
    public String getTableName() {
        return TeamTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return TeamTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String arg) {
        _name = arg;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        this._location = location;
    }

    public StatusEnum getStatus() {
        return _status;
    }

    public void setStatus(StatusEnum status) {
        this._status = status;
    }
}
