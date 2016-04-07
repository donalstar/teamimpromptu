package net.rubicon.indepth.db;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import net.rubicon.indepth.chain.CommandEnum;

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
            case TEAM_UPDATE:
                contentUri = TeamTable.CONTENT_URI;
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
    public List<TeamModel> selectTeamAll(Context context) {
        List<TeamModel> result = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(TeamTable.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                TeamModel model = new TeamModel();
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



}