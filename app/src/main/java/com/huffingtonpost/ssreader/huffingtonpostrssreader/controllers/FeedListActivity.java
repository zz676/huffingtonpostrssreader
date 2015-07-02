package com.huffingtonpost.ssreader.huffingtonpostrssreader.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.huffingtonpost.ssreader.huffingtonpostrssreader.R;
import com.huffingtonpost.ssreader.huffingtonpostrssreader.modules.RssItem;

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
public class FeedListActivity extends ActionBarActivity
        implements FeedListFragment.Callbacks {
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
           // FeedListFragment feedListFragment  = ((FeedListFragment) getSupportFragmentManager().findFragmentById(R.id.list_item));
            //feedListFragment.setActivateOnItemClick(true);


            FragmentManager fragmentManager =  getSupportFragmentManager();
            FeedListFragment feedListFragment = (FeedListFragment)fragmentManager.findFragmentById(R.id.feed_list);
            feedListFragment.setActivateOnItemClick(true);
        }

        //new RetrieveFeedTask().execute(URL);


        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link FeedListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(RssItem item) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(FeedDetailFragment.SELECTED_ITEM, item);
            FeedDetailFragment fragment = new FeedDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.feed_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, FeedDetailActivity.class);
            detailIntent.putExtra(FeedDetailFragment.SELECTED_ITEM, item);
            startActivity(detailIntent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                Toast msg = Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG);
                msg.show();
                return true;
            case R.id.info:
                Toast msgx = Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG);
                msgx.show();
                return true;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
