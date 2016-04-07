package net.rubicon.indepth.db;

import android.net.Uri;

import java.util.HashMap;

/**
 * common parent for table abstraction
 */
public interface DataBaseTable {

    /**
     * @return associated table name
     */
    String getTableName();

    /**
     *
     * @return
     */
    Uri getContentUri();

    /**
     *
     * @return
     */
    String getContentType();

    /**
     *
     * @return
     */
    String getContentItemType();

    /**
     *
     * @return default sort order
     */
    String getDefaultSortOrder();

    /**
     *
     * @return
     */
    String getIdColumnName();

    /**
     *
     * @return default projection (column names);
     */
    String[] getDefaultProjection();

    /**
     *
     * @return
     */
    HashMap<String, String> getProjectionMap();

}
