package net.teamimpromptu.fieldmanager.ui.main;

public enum FragmentsEnum {
    UNKNOWN("Unknown"),
    PROFILE_VIEW("ProfileView"),
    TEAM_VIEW("TeamView"),
    STRIKE_TEAMS_VIEW("StrikeTeamsView");


    private final String _name;

    FragmentsEnum(String arg) {
        _name = arg;
    }

    @Override
    public String toString() {
        return _name;
    }

}
