package net.teamimpromptu.fieldmanager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import net.teamimpromptu.fieldmanager.db.ServerFacade;
import net.teamimpromptu.fieldmanager.ui.utility.ApplicationProperties;
import net.teamimpromptu.fieldmanager.ui.utility.PermissionsHandler;
import net.teamimpromptu.fieldmanager.ui.utility.UserPreferenceHelper;

import java.io.IOException;

//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

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

//

//        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
//
//        Log.i(LOG_TAG, "Got db client -- " + ddbClient);
//
//
//
////        QueryRequest
////        ddbClient.query()
//
//
//        // Create Query request
//        QueryRequest request = new QueryRequest("InDepthStatus");

//        new DbGet().execute( credentialsProvider );

//        {
//            TableName = "SampleTable",
//                    ExclusiveStartKey = startKey,
//                    KeyConditions = keyConditions
//        };

        // Issue request

//        QueryResult result = ddbClient.query(request);
//
//        Log.i(LOG_TAG, "db result -- " + result);

//
//
//        CognitoSyncManager syncClient = new CognitoSyncManager(
//                getApplicationContext(),
//                Regions.US_EAST_1, // Region
//                credentialsProvider);
//
//// Create a record in a dataset and synchronize with the server
//        Dataset dataset = syncClient.openOrCreateDataset("myDataset");
//        dataset.put("myKey", "myValue");
//        dataset.synchronize(new DefaultSyncCallback() {
//            @Override
//            public void onSuccess(Dataset dataset, List newRecords) {
//                //Your handler code here
//                Log.i(LOG_TAG, "Added key & value!!!");
//
//
//                String value = dataset.get("myKey");
//
//                Log.i(LOG_TAG, "Got value from dataset! " + value);
//            }
//        });

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