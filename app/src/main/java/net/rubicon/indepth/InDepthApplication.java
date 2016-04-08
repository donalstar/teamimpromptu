package net.rubicon.indepth;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.regions.Regions;

import net.rubicon.indepth.db.ServerFacade;
import net.rubicon.indepth.ui.utility.PermissionsHandler;
import net.rubicon.indepth.ui.utility.UserPreferenceHelper;

import java.util.List;
import java.util.Map;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.*;

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


        ServerFacade serverFacade = ServerFacade.getInstance(getApplicationContext());

        serverFacade.set("STATUS", "ACTIVE");
//


//        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
//
//        Log.i(LOG_TAG, "Got db client -- " + ddbClient);
//
//
//



        Log.i(LOG_TAG, "Application created");

    }


    class DbGet extends AsyncTask<CognitoCachingCredentialsProvider, Void, String> {

        private Exception exception;

        QueryResult result;

        protected String doInBackground(CognitoCachingCredentialsProvider... credentialsProviders) {
            try {

                AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProviders[0]);

                Log.i(LOG_TAG, "Got db client -- " + ddbClient);

//        QueryRequest
//        ddbClient.query()


                // Create Query request
                QueryRequest request = new QueryRequest("InDepthStatus");

                request.setKeyConditionExpression("stat = :STATUS");

//                'KeyConditionExpression' => 'feed_guid = :v_guid1 or feed_guid = :v_guid2',


//                KeyConditionExpression, :artist

                result = ddbClient.query(request);

                Log.i(LOG_TAG, "result -- " + result);

                return "ok";
            } catch (Exception e) {
                Log.i(LOG_TAG, "ex " + e);
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(CognitoCachingCredentialsProvider provider) {
            // TODO: check this.exception
            // TODO: do something with the feed

            Log.i(LOG_TAG, "DB QUERY DONE " + result);
        }
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