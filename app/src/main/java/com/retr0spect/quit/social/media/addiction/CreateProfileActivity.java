package com.retr0spect.quit.social.media.addiction;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class CreateProfileActivity extends AppCompatActivity implements AppListDialogFragment.OnCompleteListener {

    Button btnAddApp;
    AppListSelectedAdapter adapter;
    RecyclerView recyclerView;
    List<AppMetadata> selectedApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Profile");
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
    }

    @Override
    public void onComplete(List<AppMetadata> apps) {
        selectedApps = apps;
        adapter = new AppListSelectedAdapter(selectedApps);
        recyclerView.setAdapter(adapter);
    }

}
