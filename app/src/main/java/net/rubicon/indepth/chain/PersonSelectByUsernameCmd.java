package net.rubicon.indepth.chain;


/**
 * Select a person
 */
public class PersonSelectByUsernameCmd extends AbstractCmd {
    public static final String LOG_TAG = PersonSelectByUsernameCmd.class.getName();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final PersonSelectByUsernameCtx ctx = (PersonSelectByUsernameCtx) context;

        DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());

        ctx.setSelected(dbf.selectPerson(ctx.getUsername()));

        return returnToSender(ctx, ResultEnum.OK);
    }
}