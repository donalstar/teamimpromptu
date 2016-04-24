package net.teamimpromptu.fieldmanager.ui.main;

import android.os.Bundle;

import net.rubicon.indepth.R;


/**
 *
 */
public class StrikeTeamsPagerFragment extends PagerFragment implements FragmentContext {

    public static final String FRAGMENT_TAG = "FRAGMENT_STRIKE_TEAMS_PAGER";

    public static final String LOG_TAG = StrikeTeamsPagerFragment.class.getName();

    public static StrikeTeamsPagerFragment newInstance() {
        return new StrikeTeamsPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _adapter = new StrikeTeamsPagerAdapter(getChildFragmentManager());
    }

    @Override
    public String getName() {
        return "Strike Teams";
    }

    @Override
    public int getHomeIndicator() {
        return R.drawable.ic_menu_white;
    }

    @Override
    public boolean animateTransitions() {
        return false;
    }
}




