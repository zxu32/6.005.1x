package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T08:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T09:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d6 = Instant.parse("2016-02-17T13:00:00Z");

    private static Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "talk in 30 minutes #hype", d3);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "rivest talk", d4);
    private static final Tweet tweet5 = new Tweet(5, "bbitdiddle", "rivesttalkin 30 minutes #hype", d5);
    private static final Tweet tweet6 = new Tweet(6, "bbitdiddle", "rivest TALK in 30 minutes #hype", d6);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    @Test
    public void testWrittenByEmptyTweetList() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(), "alyssa2");
        assertTrue("expected empty set", writtenBy.isEmpty());
    }

    @Test
    public void testWrittenByNoModificationToInput() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals(new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1), tweet1);
        assertEquals(new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2), tweet2);

    }

    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    @Test
    public void teststInTimespanEmptyTweetList() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(), new Timespan(testStart, testEnd));
        assertTrue("expected empty set", inTimespan.isEmpty());
    }

    @Test
    public void testInTimespanMoreTweets() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6), new Timespan(testStart, testEnd));

        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    @Test
    public void testContainingNoModificationInput() {
        List<Tweet> containing = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");
        assertEquals(new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1), tweet1);

    }

    @Test
    public void testContainingEmptyTweetList(){
        List<Tweet> containing = Filter.writtenBy(Arrays.asList(), "alyssa2");
        assertTrue("expected empty set", containing.isEmpty());
    }

    @Test
    public void testContainingSomeTweets() {
        List<Tweet> containing = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1)));
    }

    @Test
    public void testContainingWordAtStringBoundaries() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6), Arrays.asList("talk"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet6)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }


    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
