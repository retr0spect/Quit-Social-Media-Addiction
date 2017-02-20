package com.retr0spect.quit.social.media.addiction;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
    }

    @Override
    public ProfileListAdapter.ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_main_page, parent, false);
        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProfileListAdapter.ProfileViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(v.getContext(), CreateProfileActivity.class));
                intent.putExtra("ProfileName", pcf.get(position).getProfileName());
                intent.putExtra("FromCard", "true");
                v.getContext().startActivity(intent);
            }
        });
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
        public CardView cardView;
        public TextView profileName;
        public LinearLayout linearLayout;
//        public ToggleButton toggle;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.main_card_view);
            profileName = (TextView) itemView.findViewById(R.id.profile_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.profile_app_icons);
//            toggle = (ToggleButton) itemView.findViewById(R.id.main_page_toggle);
        }
    }

}
