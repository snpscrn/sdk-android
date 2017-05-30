package com.snapscreen.sdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.snapscreen.sdk.SnapscreenKit;
import com.snapscreen.sdk.model.websearch.WebSearchResponse;
import com.snapscreen.sdk.model.websearch.WebSearchVideoResult;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by martin on 27/10/2016.
 */

public class VideosActivity extends AppCompatActivity {
    private WebSearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_websearch);

        ListView listView = (ListView) findViewById(R.id.result_list);
        this.adapter = new WebSearchAdapter(this);
        listView.setAdapter(this.adapter);

        SnapscreenKit.getInstance().getWebSearchService().searchVideos("Big Bang Theory", "at_DE", "at-orf1", SnapscreenKit.getInstance().getCurrentSnapscreenTimestamp(), 0, 25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WebSearchResponse<WebSearchVideoResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WebSearchResponse<WebSearchVideoResult> webSearchVideoResultWebSearchResponse) {
                        if (webSearchVideoResultWebSearchResponse.getWebSearchEntries() != null) {
                            adapter.updateResults(webSearchVideoResultWebSearchResponse.getWebSearchEntries());
                        } else {
                        }
                    }
                });
    }
}
