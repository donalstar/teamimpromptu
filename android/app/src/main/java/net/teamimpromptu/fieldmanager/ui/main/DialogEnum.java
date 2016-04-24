package net.teamimpromptu.fieldmanager.ui.main;

/**
 *
 */
public enum DialogEnum {
    ADD_INVENTORY_DIALOG("AddInventory"),
    ADD_SITE_DIALOG("AddSite"),
    UNKNOWN("Unknown");

    private final String _name;

    DialogEnum(String arg) {
        _name = arg;
    }

    @Override
    public String toString() {
        return _name;
    }

    /**
     * string to enum conversion
     *
     * @param arg
     * @return
     */
    public static DialogEnum discoverMatchingEnum(String arg) {
        DialogEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (DialogEnum token : DialogEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
