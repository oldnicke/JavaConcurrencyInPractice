package com.oldnicke.learn.jcip.composingobjects;

import java.util.*;

import com.oldnicke.learn.jcip.annotations.GuardedBy;
import com.oldnicke.learn.jcip.annotations.ThreadSafe;

/**
 * PersonSet
 * <p/>
 * Using confinement to ensure thread safety
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
public class PersonSet {
    @GuardedBy("this")
    private final Set<Person> mySet = new HashSet<Person>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    interface Person {
    }
}
