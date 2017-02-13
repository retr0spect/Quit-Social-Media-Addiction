package com.retr0spect.quit.social.media.addiction;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity {
    int flags = PackageManager.GET_META_DATA |
            PackageManager.GET_SHARED_LIBRARY_FILES |
            PackageManager.GET_UNINSTALLED_PACKAGES;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AppMetadata> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Apps");
        setSupportActionBar(toolbar);

        apps = loadApps();

        mRecyclerView = (RecyclerView) findViewById(R.id.app_list_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AppListAdapter(apps);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private ArrayList<AppMetadata> loadApps() {
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(flags);
        ArrayList<AppMetadata> appMetas = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            PackageInfo p = apps.get(i);
            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                AppMetadata appMetadata = new AppMetadata(
                        p.applicationInfo.loadLabel(getPackageManager()).toString(),
                        p.packageName,
                        p.applicationInfo.loadIcon(getPackageManager())
                );
                appMetas.add(appMetadata);
            }
        }
        return appMetas;
    }


}
