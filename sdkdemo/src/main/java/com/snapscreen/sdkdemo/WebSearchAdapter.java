package com.snapscreen.sdkdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snapscreen.sdk.model.websearch.WebSearchImageResult;
import com.snapscreen.sdk.model.websearch.WebSearchResult;
import com.snapscreen.sdk.model.websearch.WebSearchSiteResult;
import com.snapscreen.sdk.model.websearch.WebSearchVideoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 27/10/2016.
 */

public class WebSearchAdapter extends BaseAdapter {

    private final Context context;
    private List<? extends WebSearchResult> results;

    public WebSearchAdapter(Context context) {
        this.context = context;
        this.results = new ArrayList<>();
    }

    public void updateResults(List<? extends WebSearchResult> results) {
        this.results = results;
        this.notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup viewGroup) {
        View view = convertView;

        WebSearchRowViewHolder rowViewHolder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.websearch_row, null);

            rowViewHolder = new WebSearchRowViewHolder();
            rowViewHolder.titleView = (TextView) view.findViewById(R.id.title_view);
            rowViewHolder.detailView = (TextView) view.findViewById(R.id.detail_view);

            view.setTag(rowViewHolder);
        } else {
            rowViewHolder = (WebSearchRowViewHolder) view.getTag();
        }

        WebSearchResult result = (WebSearchResult) getItem(index);

        if (result instanceof WebSearchSiteResult) {
            WebSearchSiteResult siteResult = (WebSearchSiteResult) result;
            rowViewHolder.titleView.setText(siteResult.getTitle());
            rowViewHolder.detailView.setText(siteResult.getUrl());
        } else if (result instanceof WebSearchVideoResult) {
            WebSearchVideoResult videoResult = (WebSearchVideoResult) result;
            rowViewHolder.titleView.setText(videoResult.getTitle());
            rowViewHolder.detailView.setText(videoResult.getUrl());
        } else if (result instanceof WebSearchImageResult) {
            WebSearchImageResult imageResult = (WebSearchImageResult) result;
            rowViewHolder.titleView.setText(imageResult.getTitle());
            rowViewHolder.detailView.setText(imageResult.getUrl());
        }


        return view;
    }

    public class WebSearchRowViewHolder {
        TextView titleView;
        TextView detailView;
    }
}
