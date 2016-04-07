package net.rubicon.indepth.chain;


import net.rubicon.indepth.db.ContentFacade;
import net.rubicon.indepth.db.DataBaseModel;

/**
 * Created by donal on 10/5/15.
 */
public class UpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = UpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    private CommandEnum _command;

    /**
     * @param command
     */
    public UpdateCmd(CommandEnum command) {
        _command = command;
    }

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final UpdateCtx ctx = (UpdateCtx) context;
        final DataBaseModel model = ctx.getModel();

        _contentFacade.updateModel(_command, model, ctx.getAndroidContext());

        return result;
    }
}
