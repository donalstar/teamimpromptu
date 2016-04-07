package net.rubicon.indepth.ui.utility;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donal on 3/15/16.
 */
public class PermissionsHandler {
    public static final String LOG_TAG = PermissionsHandler.class.getName();

    private static int API_VERSION = android.os.Build.VERSION.SDK_INT;

    private Callback _callback;

    private static final int PERMISSIONS_GRANTED = 100;

    public static final String PERMISSIONS_GRANTED_INTENT = "PermissionsGranted";

    public PermissionsHandler(Callback callback) {
        _callback = callback;
    }

    /**
     * @param activity
     * @param permissionTypes
     */
    public void requestPermissions(Activity activity,
                                   String permissionTypes[]) {
        if (API_VERSION >= android.os.Build.VERSION_CODES.M) {
            List<String> permissionsToRequest = new ArrayList<>();

            for (String permissionType : permissionTypes) {
                if (ContextCompat.checkSelfPermission(activity,
                        permissionType) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(LOG_TAG, "Do not have " + permissionType + " permission");

                    permissionsToRequest.add(permissionType);
                }
            }

            if (!permissionsToRequest.isEmpty()) {
                String permissions[] = new String[permissionsToRequest.size()];
                permissions = permissionsToRequest.toArray(permissions);

                ActivityCompat.requestPermissions(activity,
                        permissions,
                        PERMISSIONS_GRANTED);
            }
            else {
                _callback.onPermissionGranted();
            }
        } else {
            _callback.onPermissionGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == PERMISSIONS_GRANTED) {
            List<String> permissionsDenied = new ArrayList<>();

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != 0) {
                    Log.w(LOG_TAG, "Permission " + permissions[i] + " denied");
                    permissionsDenied.add(permissions[i]);
                }
            }

            if (permissionsDenied.isEmpty()) {
                _callback.onPermissionGranted();
            } else {
                _callback.onPermissionDenied(permissionsDenied);
            }
        }
    }

    public interface Callback {
        void onPermissionGranted();

        void onPermissionDenied(List<String> permissionTypes);
    }
}
