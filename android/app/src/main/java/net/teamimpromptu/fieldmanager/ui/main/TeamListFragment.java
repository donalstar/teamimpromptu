package net.teamimpromptu.fieldmanager.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.rubicon.indepth.R;
import net.teamimpromptu.fieldmanager.db.DataBaseTable;
import net.teamimpromptu.fieldmanager.db.PersonTable;
import net.teamimpromptu.fieldmanager.ui.utility.ToastHelper;


public class TeamListFragment extends Fragment implements FragmentContext, LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = TeamListFragment.class.getName();

    private static final String TITLE = "Team";

    public static final int LOADER_ID = 271828;

    private MainActivityListener _listener;

    private TeamListAdapter _adapter;

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DataBaseTable table = new PersonTable();

        String[] projection = table.getDefaultProjection();

        String selection = null;

        String[] selectionArgs = null;

        return new CursorLoader(getActivity(),
                PersonTable.CONTENT_URI, projection, selection, selectionArgs, null);
    }


    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            _adapter.setCursor(cursor);
            _adapter.notifyDataSetChanged();
        }

    }

    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
    }

    public static Fragment newInstance(Bundle args) {
        TeamListFragment fragment = new TeamListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        _listener = (MainActivityListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        _adapter = new TeamListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(_adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.show("Add NEW TEAM MEMBER! (request b/e)", getActivity());
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        Log.i(LOG_TAG, "onResume");

        super.onResume();

        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_jobs, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public String getName() {
        return TITLE;
    }

    @Override
    public int getHomeIndicator() {
        return R.drawable.ic_menu_white;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public boolean animateTransitions() {
        return false;
    }
}