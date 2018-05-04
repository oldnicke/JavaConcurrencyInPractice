package com.oldnicke.learn.jcip.performance;

import java.util.*;
import java.util.regex.*;

import com.oldnicke.learn.jcip.annotations.GuardedBy;
import com.oldnicke.learn.jcip.annotations.ThreadSafe;


/**
 * AttributeStore
 * <p/>
 * Holding a lock longer than necessary
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class AttributeStore {
    @GuardedBy("this")
    private final Map<String, String> attributes = new HashMap<String, String>();

    public synchronized boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}
