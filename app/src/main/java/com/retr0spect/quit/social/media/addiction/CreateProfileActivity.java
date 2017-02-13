package com.retr0spect.quit.social.media.addiction;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CreateProfileActivity extends AppCompatActivity {

    Button btnAddApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        setTitle("Create Profile");

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
}
