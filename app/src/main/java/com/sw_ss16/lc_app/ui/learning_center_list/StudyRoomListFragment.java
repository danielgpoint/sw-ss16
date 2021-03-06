package com.sw_ss16.lc_app.ui.learning_center_list;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import com.sw_ss16.lc_app.R;
import com.sw_ss16.lc_app.content.LearningCenter;
import com.sw_ss16.lc_app.content.LearningCenterContent;

/**
 * Shows a list of all available quotes.
 * <p/>
 * Created by Andreas Schrade on 14.12.2015.
 */
public class StudyRoomListFragment extends ListFragment {

    private Callback callback = dummyCallback;

    private LearningCenterContent lc_contentmanager = new LearningCenterContent();


    /**
     * A callback interface. Called whenever a item has been selected.
     */
    public interface Callback {
        void onItemSelected(String id);
    }

    /**
     * A dummy no-op implementation of the Callback interface. Only used when no active Activity is present.
     */
    private static final Callback dummyCallback = new Callback() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new MyListAdapter());
        setHasOptionsMenu(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // notify callback about the selected list item

        lc_contentmanager.setApplicationContext(getActivity().getApplicationContext());
        callback.onItemSelected(lc_contentmanager.getLcObject(lc_contentmanager.getListOfFavLcIds().get(position)).id);
        //callback.onItemSelected(FavoriteStudyRoomsContent.ITEMS.get(position).id);
    }

    /**
     * onAttach(Context) is not called on pre API 23 versions of Android.
     * onAttach(Activity) is deprecated but still necessary on older devices.
     */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /**
     * Deprecated on API 23 but still necessary for pre API 23 devices.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /**
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        if (!(context instanceof Callback)) {
            throw new IllegalStateException("Activity must implement callback interface.");
        }

        callback = (Callback) context;
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            lc_contentmanager.setApplicationContext(getActivity().getApplicationContext());

            return lc_contentmanager.getListOfFavLcIds().size();

            //return FavoriteStudyRoomsContent.ITEMS.size();
        }

        @Override
        public LearningCenter getItem(int position) {
            lc_contentmanager.setApplicationContext(getActivity().getApplicationContext());
            return lc_contentmanager.getLcObject(lc_contentmanager.getListOfFavLcIds().get(position));
            //return FavoriteStudyRoomsContent.ITEMS.get(position);
        }

        @Override
        public long getItemId(int position) {
            lc_contentmanager.setApplicationContext(getActivity().getApplicationContext());
            return Long.parseLong(lc_contentmanager.getListOfFavLcIds().get(position));
            //return FavoriteStudyRoomsContent.ITEMS.get(position).id.hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_article, container, false);
            }

            final LearningCenter item = getItem(position);
            ((TextView) convertView.findViewById(R.id.article_title)).setText(item.name);
            ((TextView) convertView.findViewById(R.id.article_subtitle)).setText(item.description);
            final ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);
            Glide.with(getActivity()).load(item.image_out_url).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });

            return convertView;
        }
    }

    public StudyRoomListFragment() {
    }
}
