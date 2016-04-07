package net.rubicon.indepth.ui.main;

import android.os.Bundle;

/**
 * MainActivity listener
 */
public interface MainActivityListener {

    /**
     * display selected activity
     *
     * @param selected
     * @param args
     */
    void activitySelect(MainActivityEnum selected, Bundle args);

    /**
     *
     * @param selected
     * @param dialogListener
     * @param args
     */
    void dialogSelect(DialogEnum selected, DialogListener dialogListener, Bundle args);

    /**
     * display selected fragment (within MainActivity)
     *
     * @param selected
     * @param args
     */
    void fragmentSelect(FragmentsEnum selected, Bundle args);

    /**
     * display selected fragment (within MainActivity)
     *
     * @param selected
     * @param args
     */
    void fragmentSelect(FragmentsEnum selected, Bundle args, boolean addToBackStack);


    /**
     * pop to previous fragment (within MainActivity)
     */
    void fragmentPop();

    /**
     * open/close navigation drawer
     *
     * @param arg true, open navigation drawer
     */
    void navDrawerOpen(boolean arg);
}
