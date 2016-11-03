package twitter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {


    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */

    private static final Tweet tweet1 = new Tweet(1, "Charles", "@Andy,@Lisa,", Instant.now());
    private static final Tweet tweet2 = new Tweet(2, "Owen", "@Stephy,@Fiora,", Instant.now());
    private static final Tweet tweet3 = new Tweet(3, "Jane", "@Shelley, @Stephy, @Fiora", Instant.now());


    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSomeTweets() {
        List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet1, tweet2));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        Map<String, Set<String>> answer = new HashMap<>();

        answer.put("CHARLES", new HashSet<>(Arrays.asList("andy", "lisa")));
        answer.put("OWEN", new HashSet<>(Arrays.asList("stephy", "fiora")));

        assertFalse("expected non-empty map", followsGraph.isEmpty());
        assertThat("check map equals answer", followsGraph, is(answer));
    }

    @Test
    public void testGuessFollowsGraphSomeTweets2() {
        List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet1, tweet2, tweet3));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        Map<String, Set<String>> answer = new HashMap<>();

        answer.put("CHARLES", new HashSet<>(Arrays.asList("andy", "lisa")));
        answer.put("OWEN", new HashSet<>(Arrays.asList("stephy", "fiora")));
        answer.put("JANE", new HashSet<>(Arrays.asList("shelley", "stephy", "fiora")));

        assertFalse("expected non-empty map", followsGraph.isEmpty());
        assertThat("check map equals answer", followsGraph, is(answer));
    }

    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSomeInfluencers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        followsGraph.put("a", new HashSet<>(Arrays.asList("a", "c")));
        followsGraph.put("b", new HashSet<>(Arrays.asList("b", "c")));
        followsGraph.put("c", new HashSet<>(Arrays.asList("a", "c")));

        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertFalse("expected non-empty list", influencers.isEmpty());
        assertTrue("expected list to contain username",
                influencers.containsAll(Arrays.asList("c", "a", "b")));
    }

    @Test
    public void testInfluencersSomeInfluencers2() {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        followsGraph.put("a", new HashSet<>(Arrays.asList("a", "c")));
        followsGraph.put("b", new HashSet<>(Arrays.asList("b", "c")));
        followsGraph.put("c", new HashSet<>(Arrays.asList("a", "c")));
        followsGraph.put("d", new HashSet<>(Arrays.asList("a", "c", "e")));


        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertFalse("expected non-empty list", influencers.isEmpty());
        assertTrue("expected list to contain username",
                influencers.containsAll(Arrays.asList("c", "a", "b", "e")));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
