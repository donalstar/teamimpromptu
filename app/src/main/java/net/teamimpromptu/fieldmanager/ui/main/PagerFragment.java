package net.teamimpromptu.fieldmanager.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.rubicon.indepth.R;


/**
 *
 */
public class PagerFragment extends Fragment implements FragmentContext {

    public static final String FRAGMENT_TAG = "FRAGMENT_PAGER";

    public static final String LOG_TAG = PagerFragment.class.getName();

    private MainActivityListener _listener;

    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private FloatingActionButton _fab;

    protected FragmentStatePagerAdapter _adapter;

    public static PagerFragment newInstance() {
        return new PagerFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _adapter = new TeamPagerAdapter(getChildFragmentManager());

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        _viewPager = (ViewPager) view.findViewById(R.id.tabPager);
        _tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        _viewPager.setAdapter(_adapter);

        _tabLayout.post(new Runnable() {
            @Override
            public void run() {
                _tabLayout.setTabsFromPagerAdapter(_adapter);

                _tabLayout.setupWithViewPager(_viewPager);
            }
        });

        // no fab
        _fab = (FloatingActionButton) view.findViewById(R.id.fab);
        _fab.setVisibility(View.GONE);

        Log.i(LOG_TAG, "onCreateView/done");

        return view;
    }

    @Override
    public void onResume() {
        Log.i(LOG_TAG, "onResume");

        super.onResume();


        Log.i(LOG_TAG, "onResume/done");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_jobs, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.navDrawerOpen(true);
                break;
            case R.id.action_settings:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option : id = " + item.getItemId());
        }

        return true;
    }

    @Override
    public String getName() {
        return "Sites";
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




