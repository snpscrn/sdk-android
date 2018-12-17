package com.snapscreen.sdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.snapscreen.sdk.SnapscreenKit;
import com.snapscreen.sdk.model.websearch.WebSearchResponse;
import com.snapscreen.sdk.model.websearch.WebSearchSiteResult;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by martin on 27/10/2016.
 */

public class WebsitesActivity extends AppCompatActivity {
    private WebSearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_websearch);

        ListView listView = (ListView) findViewById(R.id.result_list);
        this.adapter = new WebSearchAdapter(this);
        listView.setAdapter(this.adapter);

        Disposable disposable = SnapscreenKit.getInstance().getWebSearchService().searchSites("Big Bang Theory", "at_DE", "at-orf1", SnapscreenKit.getInstance().getCurrentSnapscreenTimestamp(), 0, 25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Consumer<WebSearchResponse<WebSearchSiteResult>>() {
                    @Override
                    public void accept(WebSearchResponse<WebSearchSiteResult> webSearchSiteResultWebSearchResponse) throws Exception {
                        if (webSearchSiteResultWebSearchResponse.getWebSearchEntries() != null) {
                            adapter.updateResults(webSearchSiteResultWebSearchResponse.getWebSearchEntries());
                        }
                    }
                });
    }
}
