package net.teamimpromptu.fieldmanager.ui.utility;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

/**
 * Properties
 */
public class ApplicationProperties {

    public static final String IDENTITY_POOL_ID = "identity_pool_id";

    private static ApplicationProperties INSTANCE;

    private static final String PROPERTIES_FILE = "app.properties";

    private Properties _properties;

    /**
     * @return
     */
    public static ApplicationProperties getInstance(Context baseContext) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationProperties(baseContext);
        }

        return INSTANCE;
    }

    private ApplicationProperties(Context baseContext) throws IOException {
        _properties = new Properties();

        _properties.load(baseContext.getAssets().open(PROPERTIES_FILE));
    }

    public String getProperty(String key) {
        return (String) _properties.get(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public long getLongProperty(String key) {
        long result = -1;

        String value = getProperty(key);

        try {
            result = Long.parseLong(value);
        } catch (NumberFormatException e) {

        }

        return result;
    }
}
