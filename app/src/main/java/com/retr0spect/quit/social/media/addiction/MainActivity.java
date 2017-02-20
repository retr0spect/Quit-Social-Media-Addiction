package com.retr0spect.quit.social.media.addiction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPrefs;
    RecyclerView mRecyclerView;
    ProfileListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final LayoutInflater inflater = LayoutInflater.from(this);
        View rootView = inflater.inflate(R.layout.activity_main, null);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_page_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(rootView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Create New Profile");
                final EditText input = new EditText(MainActivity.this);
                input.setHint("Profile Name");
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, CreateProfileActivity.class);
                        String profileName = input.getText().toString().trim();
                        boolean exists = checkIfProfileNameExists(profileName);
                        if (exists) {
                            Toast.makeText(MainActivity.this, "This Profile Name already exists. Choose another name.", Toast.LENGTH_LONG).show();
                        } else {
                            if (profileName.equals("")) {
                                Toast.makeText(MainActivity.this, "Profile Name cannot be empty", Toast.LENGTH_LONG).show();
                            } else {
                                intent.putExtra("ProfileName", profileName);
                                startActivity(intent);
                            }
                        }
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        checkForFirstRun(sharedPrefs);

        ArrayList<ProfileContentsFull> pcf = getProfileContentsFullFromProfileContents(
                Utils.getProfilesFromSharedPrefs(sharedPrefs));
        adapter = new ProfileListAdapter(pcf);
        mRecyclerView.setAdapter(adapter);
    }

    private boolean checkIfProfileNameExists(String profileName) {
        ArrayList<ProfileContents> pcs = Utils.getProfilesFromSharedPrefs(sharedPrefs);
        if (pcs != null) {
            for (ProfileContents pc : pcs) {
                if (pc.getProfileName().equalsIgnoreCase(profileName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkForFirstRun(SharedPreferences sharedPrefs) {
        String firstRun = sharedPrefs.getString("first_run", null);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        if (firstRun == null) {
            prefsEditor.putString("first_run", "true");
            prefsEditor.apply();
            firstRun();
        } else if (firstRun.equals("true")) {
            prefsEditor.putString("first_run", "false");
            prefsEditor.apply();
            firstRun();
        }
    }


    @NonNull
    private ArrayList<ProfileContentsFull> getProfileContentsFullFromProfileContents(ArrayList<ProfileContents> pcs) {
        ArrayList<ProfileContentsFull> pcf = new ArrayList<>();
        if (pcs != null) {
            for (ProfileContents p : pcs) {
                ArrayList<String> pNames = p.getPackageNames();
                ArrayList<ApplicationInfo> appInfos = new ArrayList<>();
                for (String pName : pNames) {
                    try {
                        appInfos.add(this.getPackageManager().getApplicationInfo(pName, 0));
                    } catch (PackageManager.NameNotFoundException e) {
                        Toast toast = Toast.makeText(this, "Error in getting icon", Toast.LENGTH_SHORT);
                        toast.show();
                        e.printStackTrace();
                    }
                }
                pcf.add(new ProfileContentsFull(p.getProfileName(), appInfos, p.getDays(), p.isActive()));
            }
        }
        return pcf;
    }

    private void firstRun() {
        String[] weekendPackages = new String[]{
                "com.google.android.gm",
                "com.google.android.apps.docs"
        };
        String[] socialMediaPackages = new String[]{
                "com.facebook.katana",
                "ccom.instagram.android"
        };

        ArrayList<String> weekendPackagesExist = new ArrayList<>();
        for (String pack : weekendPackages) {
            if (Utils.doesPackageExist(pack, MainActivity.this)) {
                weekendPackagesExist.add(pack);
            }
        }

        ArrayList<String> socialMediaPackagesExist = new ArrayList<>();
        for (String pack : socialMediaPackages) {
            if (Utils.doesPackageExist(pack, MainActivity.this)) {
                socialMediaPackagesExist.add(pack);
            }
        }

        ArrayList<ProfileContents> pcs = new ArrayList<>();

        if (weekendPackagesExist.size() > 0) {
            pcs.add(new ProfileContents("Weekend", weekendPackagesExist));
        }
        if (socialMediaPackagesExist.size() > 0) {
            pcs.add(new ProfileContents("Social Media", socialMediaPackagesExist));
        }

        Utils.saveProfilesToSharedPrefs(pcs, sharedPrefs);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
