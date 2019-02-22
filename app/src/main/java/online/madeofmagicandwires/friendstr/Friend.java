package online.madeofmagicandwires.friendstr;


import java.io.Serializable;

/**
 * Friends
 * Represents the data model of a person we're friendly to
 * and several methods to interact with Friend instances.
 *
 */
public class Friend implements Serializable {
    // I see no need to change these after initialisation.
    private final String NAME;
    private final int DRAWABLE_ID;

    private String bio;
    private double rating;

    /**
     * Constructor
     * @param aName String representing the Friend's name
     * @param drawableId int linking to a drawable resource,
     *                   preferably one representing the friend's face
     * @param aBio  String of the Friend's short bio
     * @param aRating float pf the Friend's initial rating.
     */
    public Friend(String aName, int drawableId, String aBio, double aRating) {
        this.NAME = aName;
        this.DRAWABLE_ID = drawableId;
        this.bio = aBio;
        this.rating = aRating;
    }

    /**
     * Returns this Friend's name
     * @return the name of this friend.
     */
    public String getName() {
        return this.NAME;
    }

    /**
     * Returns this Friend's drawable
     * @return the resource id of this friend's drawable
     */
    public int getDrawableId() {
        return this.DRAWABLE_ID;
    }

    /**
     * Returns a String containing this Friend's bio.
     * @return String containing a short biography
     */
    public String getBio() {
        return this.bio;
    }

    /**
     * Returns a float of this Friend's current rating.
     * @return float representing the current rating
     */
    public double getRating() {
        return this.rating;
    }

    /**
     * Updates this Friend's bio
     * @param bio a short biography
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Updates this Friend's rating.
     * @param rating the new rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }


    /**
     * Returns a string representing the data in the object.
     * @return Human readable String representing this isntance's data.
     */
    @Override
    public String toString() {
        return "{ " + "name: " + "'" + this.NAME + "', " +
                "bio: " + "'" + this.bio + "', " +
                "rating: " + this.rating + ", " +
                "drawableId: " + this.DRAWABLE_ID + " }";
    }
}
