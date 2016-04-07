package net.rubicon.indepth.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;

import java.io.Serializable;

/**
 *
 */
public class PersonModel implements DataBaseModel, Serializable {
    public static final String PROVIDER = "InDepth";


    private Long _id;
    private String _name;
    private String _username;
    private String _role;
    private String _status;
    private String _skills;
    private String _team;

    @Override
    public void setDefault() {
        _id = 0L;

        _name = "Unknown";
        _username = "Unknown";
        _role = "Unknown";
        _status = "Unknown";
        _skills = "Unknown";
        _team = "Unknown";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(PersonTable.Columns.NAME, _name);
        cv.put(PersonTable.Columns.USERNAME, _username);
        cv.put(PersonTable.Columns.ROLE, _role);
        cv.put(PersonTable.Columns.STATUS, _status);
        cv.put(PersonTable.Columns.SKILLS, _skills);
        cv.put(PersonTable.Columns.TEAM, _team);

        return cv;
    }


    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(PersonTable.Columns._ID));
        _name = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.NAME));
        _username = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.USERNAME));
        _role = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.ROLE));
        _status = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.STATUS));
        _skills = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.SKILLS));
        _team = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.TEAM));
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
}
