package net.teamimpromptu.fieldmanager.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;

import java.io.Serializable;

/**
 *
 */
public class PersonModel implements DataBaseModel, Serializable {
    public static final String PROVIDER = "FieldManager";

    private Long _id;
    private Long _serverId;
    private String _name;
    private String _username;
    private String _role;
    private String _status;
    private String _skills;
    private String _certifications;
    private String _team;
    transient private Location _location;

    @Override
    public void setDefault() {
        _id = 0L;

        _serverId = 0L;
        _name = "Unknown";
        _username = "Unknown";
        _role = "Unknown";
        _status = "Unknown";
        _skills = "Unknown";
        _certifications = "Unknown";
        _team = "Unknown";

        _location = new Location(PROVIDER);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(PersonTable.Columns.NAME, _name);
        cv.put(PersonTable.Columns.SERVER_ID, _serverId);
        cv.put(PersonTable.Columns.USERNAME, _username);
        cv.put(PersonTable.Columns.ROLE, _role);
        cv.put(PersonTable.Columns.STATUS, _status);
        cv.put(PersonTable.Columns.SKILLS, _skills);
        cv.put(PersonTable.Columns.CERTIFICATIONS, _certifications);
        cv.put(PersonTable.Columns.TEAM, _team);
        cv.put(PersonTable.Columns.LATITUDE, _location.getLatitude());
        cv.put(PersonTable.Columns.LONGITUDE, _location.getLongitude());

        return cv;
    }


    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(PersonTable.Columns._ID));
        _serverId = cursor.getLong(cursor.getColumnIndex(PersonTable.Columns.SERVER_ID));
        _name = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.NAME));
        _username = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.USERNAME));
        _role = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.ROLE));
        _status = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.STATUS));
        _skills = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.SKILLS));
        _certifications = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.CERTIFICATIONS));
        _team = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.TEAM));

        _location = new Location(PROVIDER);
        _location.setLatitude(cursor.getDouble(cursor.getColumnIndex(PersonTable.Columns.LATITUDE)));
        _location.setLongitude(cursor.getDouble(cursor.getColumnIndex(PersonTable.Columns.LONGITUDE)));
    }

    @Override
    public String getTableName() {
        return PersonTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return PersonTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public Long getServerId() {
        return _serverId;
    }

    public void setServerId(Long _serverId) {
        this._serverId = _serverId;
    }

    public String getName() {
        return _name;
    }

    public void setName(String arg) {
        _name = arg;
    }

    public String getRole() {
        return _role;
    }

    public void setRole(String _role) {
        this._role = _role;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    public String getSkills() {
        return _skills;
    }

    public void setSkills(String _skills) {
        this._skills = _skills;
    }

    public String getCertifications() {
        return _certifications;
    }

    public void setCertifications(String _certifications) {
        this._certifications = _certifications;
    }

    public String getTeam() {
        return _team;
    }

    public void setTeam(String _team) {
        this._team = _team;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        this._location = location;
    }
}
