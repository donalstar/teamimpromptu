package net.rubicon.indepth.chain;

import android.content.Context;

import net.rubicon.indepth.db.DataBaseModel;


/**
 * Created by donal
 */
public class UpdateCtx extends AbstractCmdCtx {
    private DataBaseModel _model;

    public UpdateCtx(CommandEnum command, Context androidContext) {
        super(command, androidContext);
    }

    public DataBaseModel getModel() {
        return _model;
    }

    public void setModel(DataBaseModel arg) {
        _model = arg;
    }
}
