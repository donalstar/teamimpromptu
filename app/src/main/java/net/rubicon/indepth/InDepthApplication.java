package net.rubicon.indepth;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import net.rubicon.indepth.ui.utility.PermissionsHandler;
import net.rubicon.indepth.ui.utility.UserPreferenceHelper;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class InDepthApplication extends Application {
    public static final String LOG_TAG = InDepthApplication.class.getName();

    private UserPreferenceHelper _uph;

    @Override
    public void onCreate() {
        super.onCreate();

        _uph = new UserPreferenceHelper();
        if (_uph.isEmptyPreferences(this)) {
            Log.i(LOG_TAG, "Preferences are empty");
            _uph.writeDefaults(this);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // register for Zandor client app startup broadcasts
            listenForPermissionsGrantedBroadcast();
        } else {
            // no need to wait... (older API level)
            initializeDatabase();
        }

        Log.i(LOG_TAG, "Application created");
    }


    /**
     *
     */
    private void listenForPermissionsGrantedBroadcast() {
        IntentFilter filter = new IntentFilter(PermissionsHandler.PERMISSIONS_GRANTED_INTENT);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initializeDatabase();
            }
        }, filter);
    }


    private void initializeDatabase() {
        Log.i(LOG_TAG, "initializeDatabase");

        if (_uph.isFirstTimeUse(this)) {
            doDatabaseInit();
        }


    }


    private void doDatabaseInit() {
        Log.i(LOG_TAG, "doDatabaseInit");

        DataBaseScenario scenario = new DataBaseScenario();

        scenario.loadTeam(this);
        scenario.loadStrikeTeams(this);
        scenario.loadPersons(this);
    }

}