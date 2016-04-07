package net.rubicon.indepth.ui.main;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.rubicon.indepth.R;
import net.rubicon.indepth.db.StrikeTeamModel;
import net.rubicon.indepth.db.TeamModel;
import net.rubicon.indepth.ui.utility.StatusIndicatorEnum;


/**
 *
 */
public class StrikeTeamsListAdapter extends RecyclerView.Adapter<StrikeTeamsListAdapter.ViewHolder> {

    public static final String LOG_TAG = StrikeTeamsListAdapter.class.getName();

    private MainActivityListener _listener;

    private Context _context;
    private Cursor _cursor;

    public StrikeTeamsListAdapter(Activity activity) {
        _listener = (MainActivityListener) activity;
        _context = activity;
    }

    public void setCursor(Cursor cursor) {
        _cursor = cursor;

    }


    @Override
    public int getItemCount() {
        int count = 0;

        if (_cursor != null) {
            count = _cursor.getCount();
        }

        return count;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if (_cursor != null) {
            _cursor.moveToPosition(position);

            final StrikeTeamModel model = new StrikeTeamModel();
            model.setDefault();
            model.fromCursor(_cursor);

            int itemIconResourceId = StatusIndicatorEnum.getListImageResourceForStatus(model.getStatus());

            String itemName = model.getName();

            viewHolder.itemName.setText(itemName);

            viewHolder.itemIcon.setImageResource(itemIconResourceId);

            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(SiteDetailPagerFragment.ARG_PARAM_TITLE, model.getName());
//
//                    bundle.putSerializable(SiteDetailPagerFragment.ARG_PARAM_SITE, model);
//
//                    _listener.fragmentSelect(FragmentsEnum.SITE_DETAIL_PAGER_VIEW, bundle, true);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewId = R.layout.row_team;

        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);

        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public final TextView itemName;
        public final ImageView itemIcon;
        public Long id;


        public ViewHolder(View arg) {
            super(arg);
            view = arg;

            itemName = (TextView) view.findViewById(R.id.itemName);
            itemIcon = (ImageView) view.findViewById(R.id.itemIcon);
        }
    }
}