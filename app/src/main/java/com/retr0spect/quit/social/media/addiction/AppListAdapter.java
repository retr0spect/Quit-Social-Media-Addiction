package com.retr0spect.quit.social.media.addiction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 2/11/2017.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppMetaViewHolder> {

    public List<AppMetadata> checkedApps;
    private List<AppMetadata> apps;

    AppListAdapter(List<AppMetadata> apps) {
        this.apps = apps;
    }

    @Override
    public AppMetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        checkedApps = new ArrayList<>();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app_list, parent, false);
        AppMetaViewHolder vh = new AppMetaViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AppMetaViewHolder holder, final int position) {
        holder.appNameTextView.setText(apps.get(position).getAppName());
        holder.appIconImageView.setImageDrawable(apps.get(position).getIcon());
        holder.isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    checkedApps.add(apps.get(position));
                } else {
                    checkedApps.remove(apps.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class AppMetaViewHolder extends RecyclerView.ViewHolder {
        public TextView appNameTextView;
        public ImageView appIconImageView;
        public CheckBox isChecked;

        public AppMetaViewHolder(View itemView) {
            super(itemView);
            appNameTextView = (TextView) itemView.findViewById(R.id.app_name_text_view);
            appIconImageView = (ImageView) itemView.findViewById(R.id.app_list_image_view);
            isChecked = (CheckBox) itemView.findViewById(R.id.app_list_checkbox);
        }
    }

}
