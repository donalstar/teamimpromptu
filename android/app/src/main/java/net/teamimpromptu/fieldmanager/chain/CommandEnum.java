package net.teamimpromptu.fieldmanager.chain;

/**
 * legal command options
 */
public enum CommandEnum {
    UNKNOWN("Unknown"),
    PERSON_SELECT_BY_USERNAME("PersonSelectByUsername"),
    STRIKE_TEAM_UPDATE("StrikeTeamUpdate"),
    PERSON_UPDATE("PersonUpdate"),
    SELECT_BY_ROW_ID("SelectByRowId"),
    TEAM_UPDATE("TeamUpdate");

    private final String _name;

    CommandEnum(String arg) {
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
    public static CommandEnum discoverMatchingEnum(String arg) {
        CommandEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (CommandEnum token : CommandEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
