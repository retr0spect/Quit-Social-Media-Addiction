package com.retr0spect.quit.social.media.addiction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 2/13/2017.
 */

public class AppListSelectedAdapter extends RecyclerView.Adapter<AppListSelectedAdapter.SelectedAppsHolder> {

    public List<AppMetadata> checkedApps;
    private List<AppMetadata> apps;

    AppListSelectedAdapter(List<AppMetadata> apps) {
        this.apps = apps;
    }

    @Override
    public AppListSelectedAdapter.SelectedAppsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        checkedApps = new ArrayList<>();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_app_list, parent, false);
        return new SelectedAppsHolder(v);
    }

    @Override
    public void onBindViewHolder(AppListSelectedAdapter.SelectedAppsHolder holder, final int position) {
        holder.appNameTextView.setText(apps.get(position).getAppName());
        holder.appIconImageView.setImageDrawable(apps.get(position).getIcon());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class SelectedAppsHolder extends RecyclerView.ViewHolder {
        public TextView appNameTextView;
        public ImageView appIconImageView;
        public Button btnDelete;

        public SelectedAppsHolder(View itemView) {
            super(itemView);
            appNameTextView = (TextView) itemView.findViewById(R.id.selected_app_name_text_view);
            appIconImageView = (ImageView) itemView.findViewById(R.id.selected_app_list_image_view);
            btnDelete = (Button) itemView.findViewById(R.id.select_app_list_delete_button);
        }
    }
}
