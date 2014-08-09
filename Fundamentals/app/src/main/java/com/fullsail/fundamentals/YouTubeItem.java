package com.fullsail.fundamentals;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by administrator on 8/9/14.
 */
public class YouTubeItem {

    public final String TAG = "MainActivity.TAG";

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
