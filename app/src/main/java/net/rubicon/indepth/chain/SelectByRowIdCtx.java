package net.rubicon.indepth.chain;

import android.content.Context;

import net.rubicon.indepth.db.DataBaseModel;
import net.rubicon.indepth.db.DataBaseTable;

/**
 * select by row ID command context
 */
public class SelectByRowIdCtx extends AbstractCmdCtx {
    private long _rowId;
    private DataBaseTable _table;
    private DataBaseModel _selected;

    public SelectByRowIdCtx(Context androidContext) {
        super(CommandEnum.SELECT_BY_ROW_ID, androidContext);
    }

    public Long getRowId() {
        return _rowId;
    }
    public void setRowId(Long arg) {
        _rowId = arg;
    }

    public DataBaseTable getTable() {
        return _table;
    }
    public void setTable(DataBaseTable arg) {
        _table = arg;
    }

    public DataBaseModel getSelected() {
        return _selected;
    }
    public void setSelected(DataBaseModel arg) {
        _selected = arg;
    }
}
