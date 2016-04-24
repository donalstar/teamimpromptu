package net.teamimpromptu.fieldmanager.chain;

import android.content.Context;


/**
 *
 */
public class ContextFactory {
    public static final String LOG_TAG = ContextFactory.class.getName();

    public static AbstractCmdCtx factory(CommandEnum command, Context androidContext) {
        AbstractCmdCtx result;

        switch (command) {

            case PERSON_SELECT_BY_USERNAME:
                result = new PersonSelectByUsernameCtx(androidContext);
                break;

            case SELECT_BY_ROW_ID:
                result = new SelectByRowIdCtx(androidContext);
                break;

            case PERSON_UPDATE:
            case STRIKE_TEAM_UPDATE:
            case TEAM_UPDATE:
                result = new UpdateCtx(command, androidContext);
                break;

            default:
                throw new IllegalArgumentException("unknown command:" + command);
        }

        return result;
    }
}
