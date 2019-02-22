package online.madeofmagicandwires.friendstr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

@SuppressWarnings("WeakerAccess")
public class InfoActivity extends AppCompatActivity {

    /** default key of the sharedpreference database to open **/
    public final String RATING_PREFS = "ratings";
    /** friend object to be retrieved or created **/
    private Friend mFriend;


    /**
     * Rating bar event listener that saves the changed rating to a shared preferences object
     */
    private static class FriendRatingChangeListener implements RatingBar.OnRatingBarChangeListener {

        /** sharedprefs database containing each friend's rating **/
        private final SharedPreferences ratings;
        private final String keyString;

        /**
         * Constructor
         * @param ratingSharedPrefs the shared preferences object to save the rating to
         * @param friend the friend object to save the rating of
         */

        public FriendRatingChangeListener(@NonNull SharedPreferences ratingSharedPrefs,
                                          @NonNull Friend friend) {
            this.ratings = ratingSharedPrefs;
            this.keyString = friend.getName();
        }

        /**
         * Alternative constructor using a specific key, rather than retrieving it from a friend obj
         * @param ratingSharedPrefs the shared preferences object to save the rating to
         * @param key the key under which to save the rating under in the database
         */
        public FriendRatingChangeListener(@NonNull SharedPreferences ratingSharedPrefs,
                                          @NonNull String key) {
            this.ratings = ratingSharedPrefs;
            this.keyString = key;
        }

        /**
         * Saves the updated rating to SharedPreferences
         * using the Friend's name as the key.
         *
         * @param ratingBar The RatingBar whose rating has changed.
         * @param rating    The current rating. This will be in the range
         *                  0..numStars.
         * @param fromUser  True if the rating change was initiated by a user
         */
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            SharedPreferences.Editor edit = ratings.edit();
            // You're not supposed to cast doubles to floats,
            // but since we're dealing with a number between 0 and 5 with intervals of 0.5
            // I figure we'll be alright.
            edit.putFloat(keyString, rating);
            edit.apply();
        }
    }


    /**
     * Called near start of Activity lifecycle; draws the activity's views.
     * @param savedInstanceState previously saved instance state; not used.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFriend = retrieveFriend();
        if(mFriend != null) {
            bindActivity(mFriend);
        }
    }

    /**
     * Sets mFriend variable
     * @return friend object sent by underlying activity
     */
    private Friend retrieveFriend() {
        Intent intent = getIntent();
        return (Friend) intent.getSerializableExtra("friend");
    }


    /**
     * Retrieves the friend's rating from shared preferences
     * @param friend friend to retrieve ratings from
     * @return the rating of the friend
     */
    private double retrieveFriendRating(Friend friend) {
        SharedPreferences prefs = getSharedPreferences(RATING_PREFS, MODE_PRIVATE);
        return (double) prefs.getFloat(friend.getName(), (float) friend.getRating());

    }

    /**
     * Binds the friend's data to the activity layout
     * @param data the friend object to show
     */
    private void bindActivity(Friend data) {
        ImageView friendImg = findViewById(R.id.infoFriendAvatar);
        TextView friendName = findViewById(R.id.infoFriendName);
        TextView friendBio  = findViewById(R.id.infoFriendBio);
        RatingBar friendRating = findViewById(R.id.infoFriendRating);

        //update ActionBar title
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(data.getName());
        }


        // Bind data from friend object to views
        friendImg.setImageResource(data.getDrawableId());
        friendName.setText(data.getName());
        friendBio.setText(data.getBio());
        // call retrieve Friend Rating from SharedPreferences or friend object
        friendRating.setRating((float) retrieveFriendRating(data));
        // set listener to send updates to preferences.
        friendRating.setOnRatingBarChangeListener(
                new FriendRatingChangeListener(
                        getSharedPreferences(RATING_PREFS, MODE_PRIVATE),
                        mFriend
                )
        );
    }
}
