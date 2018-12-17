package com.snapscreen.sdkdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snapscreen.sdk.model.SnapConfiguration;
import com.snapscreen.sdk.model.sharing.SnapscreenClipShareInformation;
import com.snapscreen.sdk.model.snap.SearchResult;
import com.snapscreen.sdk.model.snap.SportEventSearchResultEntry;
import com.snapscreen.sdk.model.sports.SportEventCompetitor;
import com.snapscreen.sdk.ui.ClipSharingActivity;
import com.snapscreen.sdk.ui.SnapActivity;
import com.snapscreen.sdk.ui.SnapscreenClipSharingConfiguration;
import com.snapscreen.sdk.ui.SnapscreenClipSharingTutorialContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                MainActivityFragment.this.startActivityForResult(SnapActivity.getIntentForContext(MainActivityFragment.this.getContext(), configuration, null), 1);
            }
        });

        view.findViewById(R.id.button_snap_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapConfiguration configuration = new SnapConfiguration();
                configuration.setSearchForAdvertisements(true);
                MainActivityFragment.this.startActivityForResult(SnapActivity.getIntentForContext(MainActivityFragment.this.getContext(), configuration, null), 2);
            }
        });

        view.findViewById(R.id.button_snap_sports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapConfiguration configuration = new SnapConfiguration();
                configuration.setSearchForSportEvents(true);
                MainActivityFragment.this.startActivityForResult(SnapActivity.getIntentForContext(MainActivityFragment.this.getContext(), configuration, null), 3);
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

        view.findViewById(R.id.button_clipsharing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ClipSharingActivity.class);
                SnapscreenClipSharingConfiguration configuration = new SnapscreenClipSharingConfiguration();
                configuration.setLargeSponsorImageResourceId(R.mipmap.sponsor_sample);

                configuration.getTutorialContent().add(new SnapscreenClipSharingTutorialContent(R.mipmap.tutorial_sample, "Point and scan your screen"));
                configuration.getTutorialContent().add(new SnapscreenClipSharingTutorialContent(R.mipmap.tutorial_sample, "Point and scan your screen"));
                configuration.getTutorialContent().add(new SnapscreenClipSharingTutorialContent(R.mipmap.tutorial_sample, "Point and scan your screen"));
                configuration.setSharingIntroductionHintImageResourceId(R.mipmap.tutorial_sample);
                configuration.setSharingIntroductionHint(SpannableString.valueOf("Some hint"));

                intent.putExtra(ClipSharingActivity.CONFIGURATION_EXTRA_KEY, configuration);
                startActivityForResult(intent, 4);
            }
        });

        view.findViewById(R.id.button_configuration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getContext(), VideosActivity.class));
            }
        });

        return view;
    }

    private String getDescriptionForResult(SearchResult searchResult) {
        if (searchResult.getTvSearchResult() != null) {
            return searchResult.getTvSearchResult().getResults().get(0).getTvChannel().getName();
        } else if (searchResult.getAdvertisementSearchResult() != null) {
            return searchResult.getAdvertisementSearchResult().getResults().get(0).getAdvertisement().getAdvertisementTitle();
        } else if (searchResult.getSportEventSearchResult() != null) {
            StringBuilder builder = new StringBuilder();
            for (SportEventSearchResultEntry entry : searchResult.getSportEventSearchResult().getResults()) {
                if (entry.getSportEvent() != null) {
                    StringBuilder competitorString = new StringBuilder();
                    for (SportEventCompetitor competitor : entry.getSportEvent().getCompetitors()) {
                        if (competitorString.length() > 0) {
                            competitorString.append(" ");
                        }
                        competitorString.append(competitor.getName());
                    }
                    if (builder.length() > 0) {
                        builder.append("\n");
                    }
                    builder.append("Sport Event: " + entry.getSportEvent().getSport() + " " + entry.getSportEvent().getTournament() + ": " + competitorString);
                } else {
                    if (builder.length() > 0) {
                        builder.append("\n");
                    }
                    builder.append("Sport Event Channel Only: " + entry.getTvChannel().getName());
                }
            }
            return builder.toString();
        }
        return "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data.getParcelableExtra("result") != null) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }

            if (requestCode == 1) {
                builder.setTitle("Snapped TV");
            } else if (requestCode == 2) {
                builder.setTitle("Snapped Ad");
            } else if (requestCode == 3) {
                builder.setTitle("Snapped Sport Event");
            }
            builder.setMessage(getDescriptionForResult((SearchResult) data.getParcelableExtra("result")));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.show();
        } else if (resultCode == Activity.RESULT_OK && data.getParcelableExtra(ClipSharingActivity.SHAREINFORMATION_RESULT_KEY) != null) {
            SnapscreenClipShareInformation shareInformation = data.getParcelableExtra(ClipSharingActivity.SHAREINFORMATION_RESULT_KEY);
            ShareCompat.IntentBuilder.from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle("Clip Share")
                    .setText(shareInformation.getVideoPlayerURL())
                    .startChooser();
        }
    }
}
