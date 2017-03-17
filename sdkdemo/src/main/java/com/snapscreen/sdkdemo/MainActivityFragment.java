package com.snapscreen.sdkdemo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snapscreen.sdk.model.SnapConfiguration;
import com.snapscreen.sdk.ui.SnapActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.button_snap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapConfiguration configuration = new SnapConfiguration();
                configuration.setSearchForTvResults(true);
                MainActivityFragment.this.startActivityForResult(SnapActivity.getIntentForContext(MainActivityFragment.this.getContext(), configuration), 123);
            }
        });

        view.findViewById(R.id.button_snap_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapConfiguration configuration = new SnapConfiguration();
                configuration.setSearchForAdvertisements(true);
                MainActivityFragment.this.startActivityForResult(SnapActivity.getIntentForContext(MainActivityFragment.this.getContext(), configuration), 123);
            }
        });

        view.findViewById(R.id.button_sites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WebsitesActivity.class));
            }
        });

        view.findViewById(R.id.button_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ImagesActivity.class));
            }
        });

        view.findViewById(R.id.button_videos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VideosActivity.class));
            }
        });

        return view;
    }
}
