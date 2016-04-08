package net.rubicon.indepth.db;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.regions.Regions;

import java.util.List;

/**
 * Created by donal on 4/7/16.
 */
public class ServerFacade {
    public static final String LOG_TAG = ServerFacade.class.getName();

    private static final String DATA_SET = "myDataset";


    private static ServerFacade INSTANCE;

    private CognitoSyncManager _syncClient;

    public static ServerFacade getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ServerFacade(context);
        }

        return INSTANCE;
    }

    private ServerFacade(Context context) {


    }

    public void set(String name, String value) {
        Dataset dataset = _syncClient.openOrCreateDataset(DATA_SET);
        dataset.put(name, value);
        dataset.synchronize(new DefaultSyncCallback() {
            @Override
            public void onSuccess(Dataset dataset, List newRecords) {
                //Your handler code here
                Log.i(LOG_TAG, "Added key & value!!!");
            }
        });

    }

    public String get(String name) {
        Dataset dataset = _syncClient.openOrCreateDataset(DATA_SET);

        return dataset.get(name);
    }
}
