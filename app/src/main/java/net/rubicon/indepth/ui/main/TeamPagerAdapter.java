package net.rubicon.indepth.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 *
 */
public class TeamPagerAdapter extends FragmentStatePagerAdapter {
    public static final String LOG_TAG = TeamPagerAdapter.class.getName();

    public TeamPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch (position) {
            case 0:
                title = "List";
                break;
            case 1:
                title = "Map";
                break;
            default:
                break;
        }

        return title;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = TeamListFragment.newInstance(new Bundle());
                break;
            case 1:

                fragment = TeamMapFragment.newInstance(bundle);
                break;
            default:
                break;
        }

        return fragment;
    }
}
