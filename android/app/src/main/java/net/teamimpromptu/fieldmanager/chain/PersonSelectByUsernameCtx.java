package net.teamimpromptu.fieldmanager.chain;

import android.content.Context;

import net.teamimpromptu.fieldmanager.db.PersonModel;


/**
 * select person
 */
public class PersonSelectByUsernameCtx extends AbstractCmdCtx {
    private String _username;
    private PersonModel _selected;

    public PersonSelectByUsernameCtx(Context androidContext) {
        super(CommandEnum.PERSON_SELECT_BY_USERNAME, androidContext);
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String arg) {
        _username = arg;
    }

    public PersonModel getSelected() {
        return _selected;
    }

    public void setSelected(PersonModel arg) {
        _selected = arg;
    }
}