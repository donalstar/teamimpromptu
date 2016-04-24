package net.teamimpromptu.fieldmanager.ui.main;

/**
 *
 */
public enum MainActivityEnum {
    SCAN_BARCODE("ScanBarcode"),
    UNKNOWN("Unknown");


    private final String _name;

    MainActivityEnum(String arg) {
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
    public static MainActivityEnum discoverMatchingEnum(String arg) {
        MainActivityEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (MainActivityEnum token : MainActivityEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
