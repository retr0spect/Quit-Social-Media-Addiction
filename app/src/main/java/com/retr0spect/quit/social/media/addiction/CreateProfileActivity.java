package com.retr0spect.quit.social.media.addiction;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CreateProfileActivity extends AppCompatActivity implements AppListDialogFragment.OnCompleteListener, DataTransferInterface {

    Button btnAddApp, btnSave;
    AppListSelectedAdapter adapter;
    RecyclerView recyclerView;
    List<AppMetadata> selectedApps;

    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Profile");

        final String profileName = getIntent().getExtras().getString("ProfileName");
        mPrefs = PreferenceManager.getDefaultSharedPreferences(CreateProfileActivity.this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View rootView = inflater.inflate(R.layout.activity_create_profile, null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.selected_apps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setContentView(rootView);

        final FragmentManager fragmentManager = getFragmentManager();
        final AppListDialogFragment fragment = new AppListDialogFragment();

        btnAddApp = (Button) findViewById(R.id.add_app);
        btnAddApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.show(fragmentManager, "AppListDialogFragment");
            }
        });

        btnSave = (Button) findViewById(R.id.save_Profile);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> packageNames = new ArrayList<>();
                for (AppMetadata am : selectedApps) {
                    packageNames.add(am.getPackageName());
                }

                ProfileContents pc = new ProfileContents();
                pc.setPackageNames(packageNames);
                pc.setProfileName(profileName);

                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(pc);
                prefsEditor.putString("1", json);
                prefsEditor.apply();
            }
        });
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
    }

    @Override
    public void setValues(ArrayList<?> al) {
        selectedApps = (List<AppMetadata>) al;
        adapter = new AppListSelectedAdapter(selectedApps, this);
        recyclerView.setAdapter(adapter);
    }
}
