package net.teamimpromptu.fieldmanager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import net.teamimpromptu.fieldmanager.db.ServerFacade;
import net.teamimpromptu.fieldmanager.ui.utility.ApplicationProperties;
import net.teamimpromptu.fieldmanager.ui.utility.PermissionsHandler;
import net.teamimpromptu.fieldmanager.ui.utility.UserPreferenceHelper;
import net.teamimpromptu.fieldmanager.ui.utility.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 *
 */
public class FieldManagerApplication extends Application {
    public static final String LOG_TAG = FieldManagerApplication.class.getName();

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

        ServerFacade serverFacade = ServerFacade.getInstance(getApplicationContext());

        serverFacade.set("STATUS", "ACTIVE");

        invokeRestService();

        Log.i(LOG_TAG, "Application created");
    }

    private void invokeRestService() {
        Log.i(LOG_TAG, "invokeRestService");

        String apiId = "9ph1zj3fxg";
        String resourcePath = "DynamoDBManager4";

        String params = null;

        try {
            JSONObject parent = new JSONObject();
            JSONObject payload = new JSONObject();

            payload.put("username", "adm");
            payload.put("password", "pwd");
            parent.put("operation", "echo");
            parent.put("payload", payload);

            params = parent.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new WebService(
                "https://" + apiId + ".execute-api.us-east-1.amazonaws.com/prod/" + resourcePath,
                params).execute();
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




