package com.retr0spect.quit.social.media.addiction;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aditya on 2/14/2017.
 */

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder> {


    private List<ProfileContentsFull> pcf;

    ProfileListAdapter(List<ProfileContentsFull> pcf) {
        this.pcf = pcf;
        Log.d("Aditya", "Inside Constructor");
    }

    @Override
    public ProfileListAdapter.ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Aditya", "Inside onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_main_page, parent, false);
        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileListAdapter.ProfileViewHolder holder, int position) {
        Log.d("Aditya", "Inside onBindViewHolder");
        holder.profileName.setText(pcf.get(position).getProfileName());
        for (ApplicationInfo ai : pcf.get(position).getPackageInfos()) {
            try {
                Drawable icon = holder.linearLayout.getContext().getPackageManager().getApplicationIcon(ai.packageName);
                ImageView imageView = new ImageView(holder.linearLayout.getContext());
                imageView.setImageDrawable(icon);
                holder.linearLayout.addView(imageView);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return pcf.size();
    }


    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        public TextView profileName;
        public LinearLayout linearLayout;
//        public ToggleButton toggle;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            Log.d("Aditya", "Inside ProfileViewHolder");
            profileName = (TextView) itemView.findViewById(R.id.profile_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.profile_app_icons);
//            toggle = (ToggleButton) itemView.findViewById(R.id.main_page_toggle);
        }
    }

}
