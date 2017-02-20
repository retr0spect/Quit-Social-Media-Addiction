package com.retr0spect.quit.social.media.addiction;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class CreateProfileActivity extends AppCompatActivity implements AppListDialogFragment.OnCompleteListener, DataTransferInterface {

    Button btnAddApp, btnSave;
    AppListSelectedAdapter adapter;
    RecyclerView recyclerView;
    List<AppMetadata> selectedApps;

    SharedPreferences mPrefs;
    String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Profile");
        mPrefs = PreferenceManager.getDefaultSharedPreferences(CreateProfileActivity.this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View rootView = inflater.inflate(R.layout.activity_create_profile, null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.selected_apps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setContentView(rootView);

        profileName = getIntent().getExtras().getString("ProfileName");
        final String fromCard = getIntent().getExtras().getString("FromCard", "false");
        if (fromCard.equals("true")) {
            selectedApps = populateSelectedAppsForAProfile(profileName, Utils.getProfilesFromSharedPrefs(mPrefs));
            adapter = new AppListSelectedAdapter(selectedApps, this);
            recyclerView.setAdapter(adapter);
        }

        final FragmentManager fragmentManager = getFragmentManager();
        final AppListDialogFragment fragment = new AppListDialogFragment();

        btnAddApp = (Button) findViewById(R.id.add_app);
        btnAddApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.show(fragmentManager, "AppListDialogFragment");
            }
        });

    }

    private List<AppMetadata> populateSelectedAppsForAProfile(String profileName, ArrayList<ProfileContents> pcs) {
        ArrayList<AppMetadata> sa = new ArrayList<>();
        for (ProfileContents p : pcs) {
            if (p.getProfileName().equals(profileName)) {
                for (String pName : p.getPackageNames()) {
                    try {
                        AppMetadata amd = new AppMetadata(
                                (String) getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(pName, PackageManager.GET_META_DATA)),
                                pName,
                                getPackageManager().getApplicationIcon(pName)
                        );
                        sa.add(amd);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sa;
    }


    @Override
    public void onComplete(List<AppMetadata> apps) {
        if (selectedApps == null) {
            selectedApps = new ArrayList<>();
        }
        for (AppMetadata a : apps) {
            if (!selectedApps.contains(a)) {
                selectedApps.add(a);
            }
        }
        adapter = new AppListSelectedAdapter(selectedApps, this);
        recyclerView.setAdapter(adapter);
        saveUpdatedProfileContents(selectedApps, mPrefs, profileName);
    }


    @Override
    public void setValues(ArrayList<?> al) {
        selectedApps = (List<AppMetadata>) al;
        adapter = new AppListSelectedAdapter(selectedApps, this);
        recyclerView.setAdapter(adapter);
        saveUpdatedProfileContents(selectedApps, mPrefs, profileName);
    }

    private void saveUpdatedProfileContents(List<AppMetadata> selectedApps, SharedPreferences mPrefs, String profileName) {
        ArrayList<String> packageNames = new ArrayList<>();
        for (AppMetadata am : selectedApps) {
            packageNames.add(am.getPackageName());
        }
        ProfileContents pc = new ProfileContents(profileName, packageNames);
        ArrayList<ProfileContents> pcs = Utils.getProfilesFromSharedPrefs(mPrefs);
        if (pcs != null) {
            for (ProfileContents p : pcs) {
                if (p.getProfileName().equals(pc.getProfileName())) {
                    pcs.remove(p);
                    break;
                }
            }
        }
        if (pcs != null) {
            pcs.add(pc);
        }
        Utils.saveProfilesToSharedPrefs(pcs, mPrefs);
    }
}
