package net.rubicon.indepth.chain;

import android.content.Context;

import net.rubicon.indepth.db.PersonModel;
import net.rubicon.indepth.db.PersonTable;
import net.rubicon.indepth.ui.utility.UserPreferenceHelper;

/**
 * Created by donal on 3/17/16.
 */
public class CommandFacade {

    public static final String LOG_TAG = CommandFacade.class.getName();


    /**
     * @param userName
     * @param password
     * @param androidContext
     * @return
     */


    public static boolean authenticationSignIn(String userName, String password, Context androidContext) {
        boolean success = false;

        PersonModel model = personSelectByUsername(userName, androidContext);

        if (model != null) {
            success = true;

            new UserPreferenceHelper().setCurrentUser(androidContext, model.getId());
        }

        return success;
    }

    /**
     * @param username
     * @param androidContext
     * @return
     */
    public static PersonModel personSelectByUsername(String username, Context androidContext) {
        PersonSelectByUsernameCtx ctx = (PersonSelectByUsernameCtx) ContextFactory.factory(CommandEnum.PERSON_SELECT_BY_USERNAME, androidContext);
        ctx.setUsername(username);
        CommandFactory.execute(ctx);

        PersonModel model = ctx.getSelected();

        return (model == null || model.getId() == 0) ? null : model;
    }

    /**
     * @param androidContext
     * @return
     */
    public static PersonModel getLoggedInUser(Context androidContext) {
        Long id = new UserPreferenceHelper().getCurrentUser(androidContext);

        PersonModel personModel = null;

        if (!id.equals(UserPreferenceHelper.NO_CURRENT_USER)) {
            personModel = personSelectByRowId(id, androidContext);
        }

        return personModel;
    }

    /**
     * @param rowId
     * @param androidContext
     * @return
     */
    public static PersonModel personSelectByRowId(long rowId, Context androidContext) {
        SelectByRowIdCtx ctx = (SelectByRowIdCtx) ContextFactory.factory(CommandEnum.SELECT_BY_ROW_ID, androidContext);
        ctx.setRowId(rowId);
        ctx.setTable(new PersonTable());
        CommandFactory.execute(ctx);
        return (PersonModel) ctx.getSelected();
    }

}