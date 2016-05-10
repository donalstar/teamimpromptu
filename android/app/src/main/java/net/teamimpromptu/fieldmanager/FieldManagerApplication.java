package net.teamimpromptu.fieldmanager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import net.teamimpromptu.fieldmanager.db.DatabaseSync;
import net.teamimpromptu.fieldmanager.service.ServerFacade;
import net.teamimpromptu.fieldmanager.ui.utility.ApplicationProperties;
import net.teamimpromptu.fieldmanager.ui.utility.PermissionsHandler;
import net.teamimpromptu.fieldmanager.ui.utility.UserPreferenceHelper;

import java.io.IOException;


/**
 *
 */
public class FieldManagerApplication extends Application {
    public static final String LOG_TAG = FieldManagerApplication.class.getName();


    private ServerFacade _serverFacade;
    private UserPreferenceHelper _uph;

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationProperties properties = null;

        try {
            properties = ApplicationProperties.getInstance(getBaseContext());

            initialize(properties);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to locate application properties: " + e);

            Toast.makeText(this, "FATAL: Failed to load application properties!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param properties
     */
    private void initialize(ApplicationProperties properties) {
        _serverFacade = ServerFacade.createInstance(this);

        _uph = new UserPreferenceHelper();
        if (_uph.isEmptyPreferences(this)) {
            Log.i(LOG_TAG, "Preferences are empty");
            _uph.writeDefaults(this);
        }

        _uph.setIdentityPoolId(this, properties.getProperty(ApplicationProperties.IDENTITY_POOL_ID));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // register for client app startup broadcasts
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

        DatabaseSync.sync(this);

    }

    private void doDatabaseInit() {
        Log.i(LOG_TAG, "doDatabaseInit");

        DataBaseScenario scenario = new DataBaseScenario();

        scenario.loadStrikeTeams(this);
    }


}




