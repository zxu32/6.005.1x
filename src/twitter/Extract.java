package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(Instant.now(), Instant.now());
        }
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        for(Tweet tweet : tweets) {
            Instant time = tweet.getTimestamp();
            if (time.isBefore(start)) {
                start = time;
            }
            if (time.isAfter(end)) {
                end = time;
            }
        }
        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String validLetters = lowerCase + lowerCase.toUpperCase() + "0123456789-_";
        Set<String> nameMentioned = new HashSet<>();
        String username = "";
        boolean usernameMention = false;

        if (tweets.isEmpty()) {
            return nameMentioned;
        }
        for(Tweet tw : tweets) {
            String text = tw.getText();
            for (int i = 0; i < text.length(); i++) {

                char c = text.charAt(i);

                if (c == '@') {
                    if ( i == 0 || (i > 0 && !validLetters.contains(Character.toString(text.charAt(i-1))))) {
                        // test start of username
                        usernameMention = true;
                        continue;
                    }
                }
                if (!validLetters.contains(Character.toString(c))) {
                    // invalid char
                    usernameMention = false;
                    if (!username.isEmpty()) {nameMentioned.add(username);}
                    username = "";
                }
                if (usernameMention) {
                    username += Character.toLowerCase(c);
                    if (i == text.length()-1 || username.length() >= 140) {
                        // valid char but end of username
                        usernameMention = false;
                        if (!username.isEmpty()) {nameMentioned.add(username);}
                        username = "";
                    }
                }
            }
        }
        return nameMentioned;
    }
    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
