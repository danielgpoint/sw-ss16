package com.sw_ss16.lc_app.ui.other;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sw_ss16.lc_app.R;
import com.sw_ss16.lc_app.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity demonstrates some GUI functionalities from the Android support library.
 * <p/>
 * Created by Andreas Schrade on 14.12.2015.
 */
public class ViewSamplesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view) {
        Snackbar.make(view, "Hello Snackbar!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return 1;
        /*return R.id.nav_samples;*/
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
