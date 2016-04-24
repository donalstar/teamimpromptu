package net.rubicon.indepth.ui.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

/**
 * SharedPreference wrapper.
 */
public class UserPreferenceHelper {

    public static final String FIRST_TIME_USE = "firstTimeUse";
    public static final String USER = "user";
    public static final Long NO_CURRENT_USER = -1L;
    public static final String DISPLAY_TRANSITION_AUDIO_CUE = "displayTransitionCue";

    public static final String IDENTITY_POOL_ID = "identityPoolId";

    /**
     * @param context
     */
    public void writeDefaults(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putLong(USER, NO_CURRENT_USER);

        editor.putBoolean(FIRST_TIME_USE, true);

        editor.putBoolean(DISPLAY_TRANSITION_AUDIO_CUE, true);

        editor.commit();
    }

    /**
     * Could only be true on a fresh install
     *
     * @param context
     * @return true if user preferences are empty
     */
    public boolean isEmptyPreferences(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> map = sp.getAll();
        return map.isEmpty();
    }


    /**
     * Update user preferences file
     *
     * @param context
     * @param key
     * @param flag
     */
    private void setBoolean(Context context, String key, boolean flag) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, flag);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param arg
     */
    private void setLong(Context context, String key, long arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, arg);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param arg
     */
    private void setString(Context context, String key, String arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, arg);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param arg
     */
    private void setFloat(Context context, String key, float arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, arg);
        editor.commit();
    }


    /**
     * @param context
     * @return
     */
    public Boolean isFirstTimeUse(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(FIRST_TIME_USE, false);
    }

    /**
     * @param context
     * @param flag
     */
    public void setFirstTimeUse(Context context, boolean flag) {
        setBoolean(context, FIRST_TIME_USE, flag);
    }

    /**
     * return current user
     *
     * @param context
     * @return row id in Person table of current user
     */
    public long getCurrentUser(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(USER, NO_CURRENT_USER);
    }

    /**
     * define current user
     *
     * @param context
     * @param arg     row id in Person table of current user
     */
    public void setCurrentUser(Context context, long arg) {
        setLong(context, USER, arg);
    }

    /**
     * determine if display transition audio cues are enabled
     *
     * @param context
     * @return true, display transition audio cue is enabled
     */
    public boolean isDisplayTransitionAudioCue(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(DISPLAY_TRANSITION_AUDIO_CUE, true);
    }

    /**
     * enable/disable display transition audio cues
     *
     * @param context
     * @param flag    true, display transition audio cue is enabled
     */
    public void setDisplayTransitionAudioCue(Context context, boolean flag) {
        setBoolean(context, DISPLAY_TRANSITION_AUDIO_CUE, flag);
    }

    /**
     *
     * @param context
     * @param value
     */
    public void setIdentityPoolId(Context context, String value) {
        setString(context, IDENTITY_POOL_ID, value);
    }

    /**
     * @param context
     * @return
     */
    public String getIdentityPoolId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(IDENTITY_POOL_ID, null);
    }
}
