package online.madeofmagicandwires.friendstr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * FriendsAdapter
 * Adapts an ArrayList of Friends to views containing the right information in the right layout.
 *
 * We're extending RecyclerView instead of ArrayAdapter because apparently it's better.
 * The process remains the same, except there's a LayoutManager instead of a GridView
 * and a separate ViewHolder class
 * @see RecyclerView
 * @see Friend
 * @see RecyclerView.ViewHolder
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    /** LayoutInflator needed to inflate grid_item.xml **/
    private final LayoutInflater INFLATOR;
    /** ArrayList of Friend objects **/
    private  List<Friend> mFriendList;
    /** Represents the mFriendList's size **/
    private  int mFriendListSize;


    /**
     * ViewHolder class needed for FriendsAdapter.
     * @see RecyclerView.ViewHolder;
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        /** The root view of this viewholder **/
        private View root;
        /** Friend avatar view **/
        private ImageView friendImg;
        /** Friend name view **/
        private TextView  friendName;

        /**
         * FriendAdapter.ViewHolder constructor
         *
         * Never initialise a new instance of this class manually, as it breaks the closure
         * Use {@link FriendsAdapter#onCreateViewHolder(ViewGroup, int)} instead
         *
         * @param itemView view inflated from layout file.
         * @see FriendsAdapter#onCreateViewHolder(ViewGroup, int)
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.root = itemView;
            this.friendImg = itemView.findViewById(R.id.friendAvatar);
            this.friendName = itemView.findViewById(R.id.friendName);
        }

        /**
         * Called when one of the ViewHolders has been clicked
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            // First, get our friend object.
            Friend mfriend = getItemData(getAdapterPosition());

            //Create new intent add friend object, start activity
            Context context = v.getContext();

            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("friend", mfriend);
            context.startActivity(intent);

        }
    }

    /**
     * FriendsAdapter constructor.
     * @param context Application Context
     * @param friendList the Friend data you want to present in the RecyclerView
     */
    public FriendsAdapter(@NonNull Context context, @NonNull List<Friend> friendList) {
        this.INFLATOR = LayoutInflater.from(context);
        this.mFriendList = friendList;
        this.mFriendListSize = friendList.size();
    }

    /**
     * Creates a ViewHolder instance to represent the data.
     * @param parent parent View
     * @param i      position of the
     * @return       new ViewHolder object
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = INFLATOR.inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Binds data to show to a ViewHolder object after creation
     * @param viewHolder the ViewHolder object to bind the data to
     * @param i the position of the data in mFriendList
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Friend mFriend = getItemData(i);
        viewHolder.friendImg.setImageResource(mFriend.getDrawableId());
        viewHolder.friendName.setText(mFriend.getName());
        viewHolder.root.setOnClickListener(viewHolder);
    }

    /**
     * Gets the total amount of items in DataList
     * @return int of the total amount of items
     */
    @Override
    public int getItemCount() {
        return mFriendListSize;
    }

    /**
     * Returns the Friend object at specific position from the FriendList
     *
     * @param pos the position of Friend object to get
     * @return the Friend object at position pos
     * @throws ArrayIndexOutOfBoundsException when position is bigger than the
     *                                        total amount of objects in the List
     */
    public Friend getItemData(int pos) throws ArrayIndexOutOfBoundsException{
        int index = Math.abs(pos);
        if(index < mFriendListSize) {
            return mFriendList.get(index);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


}
