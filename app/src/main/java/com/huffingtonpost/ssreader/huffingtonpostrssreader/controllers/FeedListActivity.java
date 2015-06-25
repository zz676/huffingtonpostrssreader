package com.huffingtonpost.ssreader.huffingtonpostrssreader.controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.huffingtonpost.ssreader.huffingtonpostrssreader.R;
import com.huffingtonpost.ssreader.huffingtonpostrssreader.modules.Feed;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * An activity representing a list of Feeds. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FeedDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link FeedListFragment} and the item details
 * (if present) is a {@link FeedDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link FeedListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class FeedListActivity extends FragmentActivity
        implements FeedListFragment.Callbacks {

    //private static final String  URL = "https://itunes.apple.com/us/rss/topgrossingapplications/limit=50/json";
    //private static final String  URL = "http://www.huffingtonpost.com/feeds/index.xml";
    private static final String  URL = "http://feeds.contenthub.aol.com/syndication/2.0/feed/557ef73a1f117";




    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        if (findViewById(R.id.feed_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((FeedListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.feed_list))
                    .setActivateOnItemClick(true);
        }

        new RetrieveFeedTask().execute(URL);


        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link FeedListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(FeedDetailFragment.ARG_ITEM_ID, id);
            FeedDetailFragment fragment = new FeedDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.feed_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, FeedDetailActivity.class);
            detailIntent.putExtra(FeedDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Feed> {

        private Exception exception;

        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }



        protected Feed doInBackground(String... urls) {
            android.os.Debug.waitForDebugger();

            String response = null;
            try {
                response = run(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(response);

            return null;
        }

        protected void onPostExecute(Feed feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}
