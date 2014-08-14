//Brett Gear
//Java2 1408

package com.fullsail.fundamentals;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Brett Gear on 8/7/14.
 */

public class DetailFragment extends Fragment{

    public static final String TAG = "DetailFragment.TAG";
    public static final String ARG_TEXT = "DetailFragment.ARG_STRING";
    public static final String ARG_ITEM_ID = "item_id";

    YoutubeItem object = null;

    public static DetailFragment newInstance(String string) {

        DetailFragment frag = new DetailFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, string);
        frag.setArguments(args);

        return frag;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            String argString = getArguments().getString(ARG_TEXT);
            try {
                object = buildYoutubeItem(new JSONObject(argString));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {

        View view = _inflater.inflate(R.layout.detail_fragment, _container, false);




        TextView titleTV = (TextView) getView().findViewById(R.id.video_title);
        TextView channelTV = (TextView)getView().findViewById(R.id.channel_title);
        TextView descriptionTV = (TextView)getView().findViewById(R.id.description);
        TextView publishedTV = (TextView)getView().findViewById(R.id.published);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TEXT)) {
            YoutubeItem object = null;
            String argString = args.getString(ARG_TEXT);
            try {
              object = buildYoutubeItem(new JSONObject(argString));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public YoutubeItem buildYoutubeItem (JSONObject object){

        YoutubeItem returnedItem = null;

        try {
            String publishedAt = (object != null) ? object.getString("publishedAt") : null;
            String channelTitle = (object != null) ? object.getString("channelTitle") : null;
            String videoTitle = (object != null) ? object.getString("title") : null;
            String description = (object != null) ? object.getString("description") : null;
            String complete = (object != null) ? object.toString() : null;
            returnedItem = new YoutubeItem(publishedAt, channelTitle, videoTitle, description, complete);
        } catch (JSONException e){
            Log.e(TAG, "Error setting up strings");
        }

        return returnedItem;
    };

    public class YoutubeItem{
        public String publishedAt;
        public String channelTitle;
        public String videoTitle;
        public String description;
        public String complete;

        public YoutubeItem(String publishedAt, String channelTitle, String videoTitle, String description, String complete){
            this.publishedAt = publishedAt;
            this.channelTitle = channelTitle;
            this.videoTitle = videoTitle;
            this.description = description;
            this.complete = complete;
        }

        @Override
        public String toString() {
            return this.videoTitle;
        }
    }


}
