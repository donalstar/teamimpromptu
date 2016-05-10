package net.teamimpromptu.fieldmanager.service;

import android.content.Context;
import android.util.Log;

import net.teamimpromptu.fieldmanager.ui.utility.WebService;

import java.util.Map;

/**
 * Created by donal on 4/7/16.
 */
public class ServerFacade {
    public static final String LOG_TAG = ServerFacade.class.getName();


    private static ServerFacade INSTANCE;

    public static ServerFacade createInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ServerFacade(context);
        }

        return INSTANCE;
    }


    /**
     * @return
     */
    public static ServerFacade getInstance() {
        return INSTANCE;
    }

    private ServerFacade(Context context) {

    }


    public void getTeamMembers(final ServerFacadeCallback callback) {
        Log.i(LOG_TAG, "Get team members");

        String url = "https://s2z34x36ai.execute-api.us-east-1.amazonaws.com/prod/ImpromptuDBConnect";

        new WebService(
                url,
                callback).execute();
    }


    // *********************************************************************
    //
    // Callback handler interface
    //
    // *********************************************************************

    public interface ServerFacadeCallback {
        void onFailure(String status);

        void onSuccess(Map<String, Object> attributes);
    }
}


