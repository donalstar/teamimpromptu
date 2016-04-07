package net.rubicon.indepth.ui.main;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.rubicon.indepth.R;
import net.rubicon.indepth.chain.CommandFacade;
import net.rubicon.indepth.db.PersonModel;
import net.rubicon.indepth.ui.utility.UserPreferenceHelper;

public class MainActivity extends AppCompatActivity implements MainActivityListener {
    public static final String LOG_TAG = MainActivity.class.getName();

    private DrawerLayout _drawerLayout;
    private Toolbar _toolBar;


    private static final int DEFAULT_PROFILE_IMAGE_ID = R.drawable.ic_profile_default;

    private static final UserPreferenceHelper uph = new UserPreferenceHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        _drawerLayout = (DrawerLayout) findViewById(R.id.navDrawerLayout);

        if (uph.isFirstTimeUse(this)) {
            _drawerLayout.openDrawer(GravityCompat.START);

            uph.setFirstTimeUse(this, false);
        }

        _toolBar = (Toolbar) findViewById(R.id.navToolBar);

        setSupportActionBar(_toolBar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();


        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                Log.i(LOG_TAG, "addOnBackStackChangedListener!");

                Fragment fragment = fragmentManager.findFragmentById(R.id.contentLayout);

                ActionBar actionBar = getSupportActionBar();

                if (fragment != null) {
                    FragmentContext fragmentContext = (FragmentContext) fragment;

                    if (actionBar != null) {
                        actionBar.setHomeAsUpIndicator(fragmentContext.getHomeIndicator());
                    }
                }

                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();

                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);

                    if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                        actionBar.setHomeButtonEnabled(true);
                    } else {
                        actionBar.setHomeButtonEnabled(false);
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                }

            }

        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.leftNavDrawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                navDrawerDispatch(menuItem.getItemId());
                return true;
            }
        });

        View headerUserName = findViewById(R.id.userDetailLayout);


        View profileLinkImageView = findViewById(R.id.profileLinkChevron);

        profileLinkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSelect(FragmentsEnum.PROFILE_VIEW, null);

                _drawerLayout.closeDrawers();
            }
        });

        fragmentSelect(FragmentsEnum.TEAM_VIEW, new Bundle());

        PersonModel person = CommandFacade.getLoggedInUser(this);

        if (person.getRole().equals("member")) {
            hideItem();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void activitySelect(MainActivityEnum selected, Bundle args) {

    }

    @Override
    public void dialogSelect(DialogEnum selected, DialogListener dialogListener, Bundle args) {

    }

    @Override
    public void fragmentSelect(FragmentsEnum selected, Bundle args) {
        fragmentSelect(selected, args, false);
    }


    @Override
    public void fragmentSelect(FragmentsEnum selected, Bundle args, boolean addToBackStack) {
        Fragment fragment = null;

        switch (selected) {

            case TEAM_VIEW:
                fragment = TeamPagerFragment.newInstance();
                break;
            case STRIKE_TEAMS_VIEW:
                fragment = StrikeTeamsPagerFragment.newInstance();
                break;

            case PROFILE_VIEW:
                fragment = ProfileFragment.newInstance();
                break;
        }

        if (fragment == null) {
            throw new IllegalArgumentException("missing fragment:" + selected);
        } else {
            FragmentContext fragmentContext = (FragmentContext) fragment;

            String title = fragmentContext.getName();

            setSupportActionBar(_toolBar); // force menu reload

            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.setTitle(title);

                actionBar.setHomeAsUpIndicator(fragmentContext.getHomeIndicator());

                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (fragmentContext.animateTransitions()) {
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit);
            }

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.replace(R.id.contentLayout, fragment, fragment.getTag());

            fragmentTransaction.commit();
        }
    }


    @Override
    public void fragmentPop() {

    }


    @Override
    public void navDrawerOpen(boolean arg) {
        if (arg) {
            _drawerLayout.openDrawer(GravityCompat.START);
        } else {
            _drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void navDrawerDispatch(int arg) {
        _drawerLayout.closeDrawers();

        Bundle bundle = new Bundle();

        switch (arg) {
            case R.id.navTeam:
                fragmentSelect(FragmentsEnum.TEAM_VIEW, bundle);
                break;
            case R.id.navStrikeTeams:
                fragmentSelect(FragmentsEnum.STRIKE_TEAMS_VIEW, bundle);
                break;
            case R.id.navSignOut:
                break;
            default:
                throw new IllegalArgumentException("unknown nav drawer selection");
        }
    }


    private void hideItem() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.leftNavDrawer);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.navStrikeTeams).setVisible(false);
    }
}
