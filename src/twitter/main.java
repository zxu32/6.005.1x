package twitter;

import java.time.Instant;
import java.util.*;
import java.util.regex.*;


/**
 * created for testing
 */
public class main {
    public static void main(String[] args) {
        String s1 = "I love painting";
        String[] wordList = s1.split("\\b");
        for (String s: wordList) {
            System.out.println(s);
        }

//        boolean startRecording = false;
//        String sub = "";
//        Set<String> subSet = new HashSet<>();
//        Assert.assertNotNull(s1);
//        for (int i = 0; i < s1.length(); i++) {
//            if (s1.charAt(i) == 'j') {
//                startRecording = false;
//                if (!sub.isEmpty()) {
//                    subSet.add(sub);
//                    sub = "";
//                }
//            }
//            if (startRecording) {
//                sub += s1.charAt(i);
//            }
//            if (s1.charAt(i) == 'c') {
//                startRecording = true;
//            }
//        }
//        System.out.println(subSet);
    }
}