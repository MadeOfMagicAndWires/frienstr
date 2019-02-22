package online.madeofmagicandwires.friendstr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {

    public final String RATING_PREFS = "ratings";
    private Friend mFriend;


    private class FriendRatingChangeListener implements RatingBar.OnRatingBarChangeListener {

        /**
         * Saves the updated rating to SharedPreferences under the key RATING_PREFS,
         * with the Friend's name set as the key.
         *
         * @param ratingBar The RatingBar whose rating has changed.
         * @param rating    The current rating. This will be in the range
         *                  0..numStars.
         * @param fromUser  True if the rating change was initiated by a user's
         */
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            SharedPreferences prefs = getSharedPreferences(RATING_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            // You're not supposed to cast doubles to floats,
            // but since we're dealing with a number between 0 and 5 with intervals of 0.5
            // I figure we'll be alright.
            edit.putFloat(mFriend.getName(), rating);
            edit.apply();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        retrieveFriend();
        bindActivity(mFriend);
    }

    /**
     * Sets mFriend variable
     * @return
     */
    private Friend retrieveFriend() {
        Intent intent = getIntent();
        mFriend =  (Friend) intent.getSerializableExtra("friend");
        return mFriend;
    }

    private double retrieveFriendRating(Friend friend) {
        SharedPreferences prefs = getSharedPreferences(RATING_PREFS, MODE_PRIVATE);
        return (double) prefs.getFloat(friend.getName(), (float) friend.getRating());

    }

    private void bindActivity(Friend data) {
        ImageView friendImg = findViewById(R.id.infoFriendAvatar);
        TextView friendName = findViewById(R.id.infoFriendName);
        TextView friendBio  = findViewById(R.id.infoFriendBio);
        RatingBar friendRating = findViewById(R.id.infoFriendRating);

        //update ActionBar
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
        friendRating.setOnRatingBarChangeListener(new FriendRatingChangeListener());
    }
}
