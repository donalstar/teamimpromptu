package net.teamimpromptu.fieldmanager.db;

/**
 * job status types
 */
public enum StatusEnum {
    UNKNOWN("Unknown"),
    NORMAL("Normal"),
    GUARDED("Guarded"),
    SERIOUS("Serious"),
    CRITICAL("Critical");

    private final String _name;

    StatusEnum(String arg) {
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
    public static StatusEnum discoverMatchingEnum(String arg) {
        StatusEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (StatusEnum token : StatusEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
