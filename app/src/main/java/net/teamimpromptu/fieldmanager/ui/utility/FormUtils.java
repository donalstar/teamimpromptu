package net.teamimpromptu.fieldmanager.ui.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import net.rubicon.indepth.R;

/**
 * Created by donal on 3/24/16.
 */
public class FormUtils {

    /**
     * Change button state when all entry fields contain text
     *
     * @param button
     * @param editFields
     * @return
     */
    public static TextWatcher createEntryFieldTextWatcher(final Button button, final EditText editFields[]) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                FormUtils.setButtonState(button, FormUtils.fieldsFilled(editFields));
            }
        };
    }

    /**
     * @param button
     * @param enabled
     */
    public static void setButtonState(Button button, boolean enabled) {
        float alpha = enabled ? 1.0f : 0.5f;

        button.setAlpha(alpha);
        button.setEnabled(enabled);
    }

    /**
     * @return
     */
    public static boolean fieldsFilled(EditText fields[]) {
        boolean allFilled = true;

        for (EditText field : fields) {
            if (field.getText().toString().isEmpty()) {
                allFilled = false;
                break;
            }
        }

        return allFilled;
    }

    /**
     *
     * @param context
     * @param title
     * @param message
     */
    public static void showDialog(Context context, String title, String message) {
        showDialog(context, title, message, null);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param listener
     */
    public static void showDialog(Context context, String title, String message,
                                  DialogInterface.OnClickListener listener) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                .setIcon(R.drawable.rubicon)
                .show();

    }

}



