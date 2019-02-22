package online.madeofmagicandwires.friendstr;

import android.content.Context;
import android.content.res.XmlResourceParser;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


/**
 * FriendParser
 * Helper class that handles parsing xml files for friend entries
 * @see XmlPullParser
 * @see Friend
 * @see "https://developer.android.com/training/basics/network-ops/xml#java"
 */
public class FriendParser {

    private Context currentContext;

    /**
     * Constructor. Requires context for looking up resources
     *
     * @param context the application context
     */
    public FriendParser(Context context) {
        this.currentContext = context;

    }

    /**
     * Parses the xml file for Friends and creates an ArrayList of Friend objects from them.
     * @param xmlResId the id of the xml resource.
     * @see    Friend
     * @return an ArrayList of Friend objects. List is empty if there was an exception thrown.
     *
     */
    public List<Friend> createFriendListFrom(int xmlResId) {
        ArrayList<Friend> emptyList = new ArrayList<>();
        try {
            XmlResourceParser parser = currentContext.getResources().getXml(xmlResId);
            parser.next();
            return readFriends(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return emptyList;
        } catch (IOException e) {
            e.printStackTrace();
            return emptyList;
        }
    }

    /**
     * Handles <Friends></Friends> tags and returns an ArrayList of objects for every child element
     * @param parser XmlResourceParser instance created from the parsed xml resource to be parsed.
     * @return an ArrayList of Friend objects read from the file.
     * @throws XmlPullParserException when parsing fails
     * @throws IOException when reading  the resource fails
     */
    private List<Friend> readFriends(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Friend> friends = new ArrayList<>();

        while(parser.next() != XmlResourceParser.END_DOCUMENT){
            if(parser.getEventType() == XmlResourceParser.START_TAG) {
                String tagName = parser.getName();

                if(tagName.equalsIgnoreCase("friend")) {
                    friends.add(createFriend(parser));
                }
            }
        }
        return friends;
    }

    /**
     * Parses a <Friend></Friend> tag and creates a Friend object from the data found within.
     * @param parser the XmlResourceParser created from the internal xml resource
     * @return a Friend object containing all the data found in the tag's attributes.
     */
    private Friend createFriend(XmlPullParser parser) {
        String name = null;
        int drawableId = -1;
        String bio = null;
        double rating = 0;

        name = parser.getAttributeValue(null, "name");
        bio = parser.getAttributeValue(null, "bio");
        drawableId = getDrawableIdFromFileName(currentContext,
                parser.getAttributeValue(null, "drawable").toLowerCase());
        if(parser.getAttributeValue(null , "rating") != null) {
            rating = Double.parseDouble(parser.getAttributeValue(null, "rating"));
        }
        return new Friend(name, drawableId, bio, rating);

    }

    /**
     * Returns a Resource id by a filename of a drawable or -1 if it doesn't exist.
     * @param context Application context required to get resource iddentifier
     * @param fileName the name of the file
     * @return the resource id of the drawable corresponding to the filename
     */
    private int getDrawableIdFromFileName(Context context, String fileName) {
        if(fileName != null) {
            // remove any possible file extensions.
            if(fileName.contains(".")) {
                String[] splitFileName = fileName.split("\\.");
                fileName = splitFileName[0];
            }
            return context.getResources().getIdentifier(
                    fileName,
                    "drawable",
                    context.getPackageName());

        } else {
            return -1;
        }
    }

}
