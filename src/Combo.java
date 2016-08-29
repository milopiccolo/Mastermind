// Copyright 2016 Michael LoPiccolo
// See license info in Mastermind.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Combo implements Comparable<Combo> {
    private static final ArrayList<String> colors = new ArrayList<String>();
    private static final ArrayList<Integer> places = new ArrayList<Integer>();
    private static String[] colorsCurrent = new String[6];
    private static int[] placesCurrent = new int[4];

    static {
        Collections.addAll(colors, "Red", "Purple", "Blue", "Green", "Orange", "Yellow");
        // Choose one!
//      Collections.addAll(colors, "Red", "Green", "White", "Yellow", "Blue", "Black");
//      Collections.addAll(colors, "1", "2", "3", "4", "5", "6");
        Collections.addAll(places, 0, 1, 2, 3);
        randomizeDisplay();
    }

    public int[] nums;

    public Combo(int[] nums) {
        this.nums = nums;
    }

    public static void randomizeDisplay() {
        ArrayList<String> temp = new ArrayList<String>(colors);
        for (int i = 0; i < 6; i++) {
            int rnd = (int) (Math.random() * temp.size());
            colorsCurrent[i] = temp.remove(rnd);
        }
        ArrayList<Integer> temp2 = new ArrayList<Integer>(places);
        for (int i = 0; i < 4; i++) {
            int rnd = (int) (Math.random() * temp2.size());
            placesCurrent[i] = temp2.remove(rnd);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Combo combo = (Combo) o;

        if (!Arrays.equals(nums, combo.nums)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nums != null ? Arrays.hashCode(nums) : 0;
    }

    public boolean works(Combo test, int red, int white) {
        return red(test) == red && white(test) == white;
    }

    public int red(Combo test) {
        int redFound = 0;
        for (int i = 0; i < 4; i++) {
            if (test.nums[i] == nums[i])
                redFound++;
        }
        return redFound;
    }

    public int white(Combo test) {
        int whiteFound = 0;
        PriorityQueue<Integer> a = new PriorityQueue<Integer>();
        PriorityQueue<Integer> b = new PriorityQueue<Integer>();
        for (int i = 0; i < 4; i++) {
            if (test.nums[i] != nums[i]) {
                a.add(test.nums[i]);
                b.add(nums[i]);
            }
        }
        for (int i : a) {
            if (b.contains(i)) {
                b.remove(i);
                whiteFound++;
            }
        }
        return whiteFound;
    }

    public String toString() {
        String ret = "";
        for (int i = 0; i < 4; i++) {
            ret += colorsCurrent[nums[placesCurrent[i]]] + ", ";
        }
        return ret.substring(0, ret.length() - 2);
    }

    @Override
    public int compareTo(Combo o) {
        for (int i = 0; i < 4; i++) {
            if (nums[i] > o.nums[i])
                return 1;
            if (nums[i] < o.nums[i])
                return -1;
        }
        return 0;
    }
}
