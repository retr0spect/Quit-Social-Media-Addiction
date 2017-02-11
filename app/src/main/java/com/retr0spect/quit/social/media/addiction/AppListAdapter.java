package com.retr0spect.quit.social.media.addiction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aditya on 2/11/2017.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppMetaViewHolder> {

    public static class AppMetaViewHolder extends RecyclerView.ViewHolder {
        public TextView appNameTextView;
        public AppMetaViewHolder(View itemView) {
            super(itemView);
            appNameTextView = (TextView) itemView.findViewById(R.id.app_name_text_view);
        }
    }

    private List<AppMetadata> apps;

    public AppListAdapter(List<AppMetadata> apps) {
        this.apps = apps;
    }

    @Override
    public AppMetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_app_list, parent, false);
        AppMetaViewHolder vh = new AppMetaViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AppMetaViewHolder holder, int position) {
        holder.appNameTextView.setText(apps.get(position).appName);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

}
