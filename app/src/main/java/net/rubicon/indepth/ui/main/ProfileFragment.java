package net.rubicon.indepth.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import net.rubicon.indepth.R;
import net.rubicon.indepth.chain.CommandFacade;
import net.rubicon.indepth.db.PersonModel;


public class ProfileFragment extends Fragment implements FragmentContext {
    public static final String FRAGMENT_TAG = "FRAGMENT_PROFILE";

    public static final String LOG_TAG = ProfileFragment.class.getName();

    private MainActivityListener _listener;

    private TextView _name;
    private TextView _role;
    private TextView _skills;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        _name = (TextView) view.findViewById(R.id.name);

        _role = (TextView) view.findViewById(R.id.role);
        _skills = (TextView) view.findViewById(R.id.skills);

        PersonModel person = CommandFacade.getLoggedInUser(getActivity());

        _name.setText(person.getName());

        _role.setText(person.getRole());
        _skills.setText(person.getSkills());

        Spinner dropdown = (Spinner) view.findViewById(R.id.status);

        int index = person.getStatus().equals("active") ? 0 : 1;

        String[] items = new String[]{"ACTIVE", "INACTIVE"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setSelection(index);

        return view;
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
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    @Override
    public String getName() {
        return "Profile";
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
