package net.teamimpromptu.fieldmanager.ui.main;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.rubicon.indepth.R;
import net.teamimpromptu.fieldmanager.db.ContentFacade;
import net.teamimpromptu.fieldmanager.db.StrikeTeamModel;
import net.teamimpromptu.fieldmanager.ui.utility.StatusIndicatorEnum;
import net.teamimpromptu.fieldmanager.ui.utility.ToastHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Show team on a map
 */
public class StrikeTeamsMapFragment extends SupportMapFragment implements FragmentContext {
    private static int DEFAULT_ZOOM_LEVEL = 14;
    private static int DETAIL_ZOOM_LEVEL = 17;

    public static final String LOG_TAG = StrikeTeamsMapFragment.class.getName();

    public static final String ARG_PARAM_VIEW_TYPE = "PARAM_VIEW_TYPE";
    public static final String ARG_PARAM_SITE_ID = "PARAM_SITE_ID";

    private MainActivityListener _listener;

    private ContentFacade _contentFacade = new ContentFacade();

    private Map<Marker, StrikeTeamModel> _markers = new HashMap<>();

    public static StrikeTeamsMapFragment newInstance(Bundle bundle) {
        StrikeTeamsMapFragment fragment = new StrikeTeamsMapFragment();

        fragment.setArguments(bundle);

        return fragment;
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (isPlayServicesUpdateRequired()) {
            ToastHelper.show(getResources().getString(R.string.play_services_update_warning), this.getActivity());

            return view;
        }

        LatLng latLng = getCurrentUserLocation();


        int zoomLevel = DEFAULT_ZOOM_LEVEL;

        final GoogleMap map = getMap();

        List<StrikeTeamModel> siteModels = _contentFacade.selectStrikeTeamAll(getActivity());

        // info window for marker
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                View v = inflater.inflate(R.layout.info_window_layout, null);

                TextView siteTitleView = (TextView) v.findViewById(R.id.site_title);

                siteTitleView.setText(marker.getTitle());

                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });


        for (StrikeTeamModel siteModel : siteModels) {
            int iconResourceId = StatusIndicatorEnum.getMapImageResourceForStatus(siteModel.getStatus());

            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(iconResourceId));

            LatLng siteLocation =
                    new LatLng(siteModel.getLocation().getLatitude(), siteModel.getLocation().getLongitude());

            Marker marker = map.addMarker(markerOptions
                    .position(siteLocation)
                    .title(siteModel.getName()));

            _markers.put(marker, siteModel);
        }

        if (!siteModels.isEmpty() && (latLng == null)) {
            StrikeTeamModel siteModel = siteModels.get(0);

            Location location = siteModel.getLocation();

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }


        if (latLng != null)

        {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomLevel);

            map.moveCamera(cameraUpdate);

            map.animateCamera(zoom);
        }

        map.setMyLocationEnabled(true);

        Log.i(LOG_TAG, "onCreateView/done");
        return view;
    }

    /**
     * Make sure required Google Play Service level is installed
     */

    private boolean isPlayServicesUpdateRequired() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity());

        Log.i(LOG_TAG, "google play status " + status);

        return (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED);
    }

    /**
     * @return
     */
    private LatLng getCurrentUserLocation() {
        LocationManager service = (LocationManager) getActivity().getSystemService(Activity.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);

        return (location != null) ? new LatLng(location.getLatitude(), location.getLongitude()) : null;
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


