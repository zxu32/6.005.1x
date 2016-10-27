package twitter;

import java.util.*;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        for (Tweet tweet: tweets) {
            //put tweet into a list because getMentionedUsers function takes list as param
//            List<Tweet> singleTweet = new ArrayList<>();
//            singleTweet.add(tweet);
            Set<String> followed = new HashSet<>(Extract.getMentionedUsers(Arrays.asList(tweet)));
            followsGraph.put(tweet.getAuthor(), followed);
        }
        return followsGraph;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        //create a dictionary as <username, username count>
        Map<String, Integer> influencerMap = new HashMap<>();
        //iterate through followerGraph
        for (Map.Entry<String, Set<String>> entry : followsGraph.entrySet()) {
            //add key to dictionary if it's not already added
            if (!influencerMap.containsKey(entry.getKey())) {
                influencerMap.put(entry.getKey(), 0);
            } else {
                //else increase the value of that key by 1
                influencerMap.put(entry.getKey(), influencerMap.get(entry.getKey()) + 1);
            }
            //iterate through followerGraph value set
            for (String username: followsGraph.get(entry.getKey())) {
                //if it's not already added, add each item in the set to dictionary
                if (!influencerMap.containsKey(username)) {
                    influencerMap.put(username, 0);
                } else {
                    //else increase the value of that key by 1
                    influencerMap.put(username, influencerMap.get(username) + 1);
                }
            }
        }
        //create a map to contain sorted map
        Map<String, Integer> sortedInfluencerMap = sortByValue(influencerMap);
        List<String> influencer = new ArrayList<>(sortedInfluencerMap.keySet());

        //return keys of sorted dictionary as list
        return influencer;
    }

    /**
     * take in an unsorted map, sort entries by value in ascending order, return sorted map
     * @param unsortMap unsorted map
     * @param <K> key
     * @param <V> value
     * @return sorted map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
