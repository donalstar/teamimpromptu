package net.teamimpromptu.fieldmanager.db;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import net.teamimpromptu.fieldmanager.chain.CommandEnum;

import java.util.ArrayList;
import java.util.List;


/**
 * access database via ContentProvider
 */
public class ContentFacade {
    public static final String LOG_TAG = ContentFacade.class.getName();


    //////////////////////////
    //////////////////////////
    //////////////////////////


    /**
     * @param command
     * @param model
     * @param context
     */
    public void updateModel(CommandEnum command, DataBaseModel model, Context context) {
        Uri contentUri = null;

        switch (command) {
            case PERSON_UPDATE:
                contentUri = PersonTable.CONTENT_URI;
                break;
            case STRIKE_TEAM_UPDATE:
                contentUri = StrikeTeamTable.CONTENT_URI;
                break;
            default:
                break;
        }

        if (contentUri != null) {
            updateModel(model, contentUri, context);
        }
    }

    /**
     * @param model
     * @param contentUri
     * @param context
     */
    private void updateModel(DataBaseModel model, Uri contentUri, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());

            Log.i(LOG_TAG, "Update model -- id " + model.getId());
            context.getContentResolver().update(contentUri, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(contentUri, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }


    /**
     * @param context
     * @return
     */
    public List<PersonModel> selectPersonAll(Context context) {
        List<PersonModel> result = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(PersonTable.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PersonModel model = new PersonModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    public List<StrikeTeamModel> selectStrikeTeamAll(Context context) {
        List<StrikeTeamModel> result = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(StrikeTeamTable.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                StrikeTeamModel model = new StrikeTeamModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }


    /**
     * @param serverId
     * @param context
     * @return
     */
    public PersonModel selectPersonByServerId(int serverId, Context context) {
        PersonModel model = new PersonModel();
        model.setDefault();
        String selection = "server_id=?";

        String[] selectionArgs = new String[]{String.valueOf(serverId)};

        Cursor cursor = context.getContentResolver().query(PersonTable.CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            model.fromCursor(cursor);
        }

        cursor.close();

        return (model.getId() == 0L) ? null : model;
    }
}