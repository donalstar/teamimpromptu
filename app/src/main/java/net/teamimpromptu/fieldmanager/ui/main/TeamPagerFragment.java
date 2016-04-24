package net.teamimpromptu.fieldmanager.ui.main;

import android.os.Bundle;

import net.rubicon.indepth.R;


/**
 *
 */
public class TeamPagerFragment extends PagerFragment implements FragmentContext {

    public static final String FRAGMENT_TAG = "FRAGMENT_TEAM_PAGER";

    public static final String LOG_TAG = TeamPagerFragment.class.getName();

    public static TeamPagerFragment newInstance() {
        return new TeamPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _adapter = new TeamPagerAdapter(getChildFragmentManager());
    }

    @Override
    public String getName() {
        return "Team";
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




