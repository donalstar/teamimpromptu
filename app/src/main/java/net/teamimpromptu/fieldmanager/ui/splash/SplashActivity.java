package net.teamimpromptu.fieldmanager.ui.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import net.rubicon.indepth.R;
import net.teamimpromptu.fieldmanager.chain.CommandFacade;
import net.teamimpromptu.fieldmanager.ui.main.MainActivity;
import net.teamimpromptu.fieldmanager.ui.utility.FormUtils;
import net.teamimpromptu.fieldmanager.ui.utility.PermissionsHandler;
import net.teamimpromptu.fieldmanager.ui.utility.ToastHelper;
import net.teamimpromptu.fieldmanager.ui.utility.UserPreferenceHelper;

import java.util.List;


/**
 * Login activity
 */
public class SplashActivity extends Activity {
    public static final String LOG_TAG = SplashActivity.class.getName();

    private static final String REQUIRED_PERMISSIONS[] = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private Button _loginButton;
    private EditText _usernameField;
    private EditText _passwordField;
    private TextView _forgotPasswordLink;

    private PermissionsHandler _permissionsHandler;

    private static final UserPreferenceHelper uph = new UserPreferenceHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        _permissionsHandler = new PermissionsHandler(new PermissionsHandler.Callback() {
            @Override
            public void onPermissionGranted() {
                handlePermissionGranted();
            }

            @Override
            public void onPermissionDenied(List<String> permissionTypes) {
                handlePermissionDenied(permissionTypes);
            }
        });

        _loginButton = (Button) findViewById(R.id.login);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        _loginButton.setText(getString(R.string.login_button_text));

        _usernameField = (EditText) findViewById(R.id.editUserName);

        _passwordField = (EditText) findViewById(R.id.editPassword);

        EditText fields[] = new EditText[]{_usernameField, _passwordField};

        TextWatcher entryFieldWatcher = FormUtils.createEntryFieldTextWatcher(_loginButton, fields);

        _usernameField.addTextChangedListener(entryFieldWatcher);

        _passwordField.addTextChangedListener(entryFieldWatcher);


        _permissionsHandler.requestPermissions(this, REQUIRED_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        _permissionsHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void handlePermissionGranted() {
        _loginButton.setEnabled(true);

        Log.i(LOG_TAG, "Permissions granted");

        sendBroadcast(new Intent(PermissionsHandler.PERMISSIONS_GRANTED_INTENT));
    }

    private void handlePermissionDenied(List<String> permissionTypes) {
        String missingPermsClause = "";

        for (int i = 0; i < permissionTypes.size(); i++) {
            if (i > 0) {
                missingPermsClause += ",";
            }

            missingPermsClause += " \n" + permissionTypes.get(i);
        }

        String message = String.format(
                this.getResources().getString(R.string.insufficient_permissions_message),
                missingPermsClause);

        View view = findViewById(android.R.id.content);

        Snackbar.Callback snackbarDismissedCallback =
                new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        finish();
                    }
                };

        ToastHelper.showSnackbar(view, message,
                null, null, snackbarDismissedCallback,
                3000, 5);

    }

    /**
     *
     */
    private void doLogin() {
        String userName = _usernameField.getText().toString().trim();
        String password = _passwordField.getText().toString().trim();

        if (!CommandFacade.authenticationSignIn(userName, password, this)) {
            FormUtils.showDialog(this,
                    getResources().getString(R.string.auth_error_header),
                    getResources().getString(R.string.auth_error_message));
        } else {



            startActivity(new Intent(getBaseContext(), MainActivity.class));

            finish();  // inhibit navigation back to splash
        }
    }
}
