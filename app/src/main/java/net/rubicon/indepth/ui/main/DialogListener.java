package net.rubicon.indepth.ui.main;

import android.os.Bundle;

/**
 * Created by donal on 3/29/16.
 */
public interface DialogListener {

    /**
     *
     * @param bundle
     */
    void handleOK(Bundle bundle);

    void handleCancel();
}
