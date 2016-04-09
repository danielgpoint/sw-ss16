package com.sw_ss16.lc_app.ui.learning_center_one;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sw_ss16.lc_app.R;
import com.sw_ss16.lc_app.content.LearningCenter;
import com.sw_ss16.lc_app.content.LearningCenterContent;
import com.sw_ss16.lc_app.ui.base.BaseActivity;
import com.sw_ss16.lc_app.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Shows the description detail page.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class StudyRoomDetailFragment extends BaseFragment {

    /**
     * The argument represents the dummy item ID of this fragment.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy lc_address of this fragment.
     */
    private LearningCenter current_learning_center;

    private LearningCenterContent lc_contentmanager = new LearningCenterContent();

    @Bind(R.id.lc_description)
    TextView description;

    @Bind(R.id.lc_address)
    TextView address;

    @Bind(R.id.backdrop)
    ImageView backdropImg;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // load dummy item by using the passed item ID.

            lc_contentmanager.setApplicationContext(getActivity().getApplicationContext());
            current_learning_center = lc_contentmanager.getLcObject(getArguments().getString(ARG_ITEM_ID));
            //current_learning_center = StudyRoomsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_article_detail);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fav_fab_btn);

        if(lc_contentmanager.getLearningCenterFavoriteStatus(Integer.parseInt(current_learning_center.id))){
            fab.setImageResource(R.drawable.ic_remove_white_24dp);
        }

        else{
            fab.setImageResource(R.drawable.ic_add_white_24dp);
        }



        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        if (current_learning_center != null) {
            loadBackdrop();
            collapsingToolbar.setTitle(current_learning_center.name);
            address.setText(current_learning_center.address);
            description.setText(current_learning_center.description);
        }

        return rootView;
    }

    private void loadBackdrop() {
        // Glide.with(this).load(current_learning_center.photoId).centerCrop().into(backdropImg);
        Glide.with(this).load(current_learning_center.image_in_url).centerCrop().into(backdropImg);
    }

    @OnClick(R.id.fav_fab_btn)
    public void onFabClicked(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fav_fab_btn);

        if(lc_contentmanager.getLearningCenterFavoriteStatus(Integer.parseInt(current_learning_center.id)))
        {
            lc_contentmanager.setLearningCenterFavoriteStatus(Integer.parseInt(current_learning_center.id), false);
            fab.setImageResource(R.drawable.ic_add_white_24dp);
            Snackbar.make(view, "Removed from favorites", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        } else {
            lc_contentmanager.setLearningCenterFavoriteStatus(Integer.parseInt(current_learning_center.id), true);
            fab.setImageResource(R.drawable.ic_remove_white_24dp);
            Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sample_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // your logic
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static StudyRoomDetailFragment newInstance(String itemID) {
        StudyRoomDetailFragment fragment = new StudyRoomDetailFragment();
        Bundle args = new Bundle();
        args.putString(StudyRoomDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public StudyRoomDetailFragment() {}
}