package online.madeofmagicandwires.friendstr;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.XmlRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Friend> friendList;
    private FriendsAdapter adapter;
    private GridLayoutManager manager;

    /*
    * We don't have a OnItemClick listener class because RecyclerView doesn't have one.
    * Instead ViewHolder directly implements View.OnClickListener and it is set in onBindViewHolder.
    */


    /**
     * Called when the Activity is rendered
     * @param savedInstanceState Bundle containing data saved by onSaveInstanceState
     * @see AppCompatActivity#onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // testXmlPullParser();
        populateList(R.xml.friends);
        initRecyclerView();
    }

    /**
     * Populates the list of Friends for us from an xml resource
     */
    public void populateList(int xmlId) {
        FriendParser friendParser = new FriendParser(this);
        friendList = friendParser.createFriendListFrom(xmlId);
    }


    /**
     * Applies data, adapter and layout manager to the RecyclerView.
     */
    public void initRecyclerView() {
        RecyclerView v = findViewById(R.id.recycler);

        manager = new GridLayoutManager(this, 3);
        adapter = new FriendsAdapter(this, friendList);

        v.setLayoutManager(manager);
        v.setAdapter(adapter);

    }




    /**
     * Tests our FriendParser
     */
    public void testXmlPullParser() {
        FriendParser parser = new FriendParser(getApplicationContext());
        List<Friend> test = parser.createFriendListFrom(R.xml.friends);
        for(int i=0;i<test.size();i++) {
            Friend fr = test.get(i);
            Log.d("friendsr", fr.toString());
        }

    }


}
