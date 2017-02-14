package com.retr0spect.quit.social.media.addiction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 2/12/2017.
 */

public class AppListDialogFragment extends DialogFragment {

    int flags = PackageManager.GET_META_DATA |
            PackageManager.GET_SHARED_LIBRARY_FILES |
            PackageManager.GET_UNINSTALLED_PACKAGES;
    RecyclerView recyclerView;
    AppListAdapter adapter;
    private OnCompleteListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogFragmentStyle);
        adapter = new AppListAdapter(loadApps());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View rootView = inflater.inflate(R.layout.content_app_list, null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.app_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        builder.setView(rootView);
        builder.setTitle("Select Apps");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onComplete(adapter.checkedApps);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private ArrayList<AppMetadata> loadApps() {
        List<PackageInfo> apps = getActivity().getPackageManager().getInstalledPackages(flags);
        ArrayList<AppMetadata> appMetas = new ArrayList<>();
        for (int i = 0; i < apps.size(); i++) {
            PackageInfo p = apps.get(i);
            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                AppMetadata appMetadata = new AppMetadata(
                        p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(),
                        p.packageName,
                        p.applicationInfo.loadIcon(getActivity().getPackageManager())
                );
                appMetas.add(appMetadata);
            }
        }
        return appMetas;
    }

    public interface OnCompleteListener {
        void onComplete(List<AppMetadata> apps);
    }
}
