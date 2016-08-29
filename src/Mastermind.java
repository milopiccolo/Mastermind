// A simple guesser for the Mastermind game I wrote in high school.
// Experimental analysis showed an average of 4.47608024691358 guesses/combo.

//        Copyright 2016 Michael LoPiccolo
//
//        This program is free software: you can redistribute it and/or modify
//        it under the terms of the GNU General Public License as published by
//        the Free Software Foundation, either version 3 of the License, or
//        (at your option) any later version.
//
//        This program is distributed in the hope that it will be useful,
//        but WITHOUT ANY WARRANTY; without even the implied warranty of
//        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//        GNU General Public License for more details.
//
//        You should have received a copy of the GNU General Public License
//        along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class Mastermind {
    public static void main(String... args) {
        TreeSet<Combo> all = new TreeSet<Combo>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    for (int l = 0; l < 6; l++) {
                        int[] nums = {i, j, k, l};
                        all.add(new Combo(nums));
                    }
                }
            }
        }

        Scanner in = new Scanner(System.in);
        main:
        while (true) {
            Combo.randomizeDisplay();
            TreeSet<Combo> remaining = new TreeSet<Combo>(all);
            int redFound;
            int runs = 0;
            do {
                if (remaining.size() == 0) {
                    System.out.println("There are no more possibilities - you incorrectly responded to some clue earlier.");
                    continue main;
                }
                runs++;
                int maxSet = Integer.MAX_VALUE;
                boolean inRem = false;
                Combo best = null;
                for (Combo test : all) {
                    int[][] filters = new int[5][5];
                    int testMaxSet = 0;
                    for (Combo check : remaining) {
                        int red = check.red(test);
                        int white = check.white(test);
                        filters[red][white]++;
                        if (filters[red][white] > testMaxSet) {
                            testMaxSet = filters[red][white];
                        }
                    }
                    boolean testInRem = remaining.contains(test);
                    if (testMaxSet < maxSet || testMaxSet == maxSet && !inRem && testInRem) {
                        maxSet = testMaxSet;
                        best = test;
                        inRem = testInRem;
                    }
                }
                System.out.println(best);
                System.out.print("Red pegs? (Correct place and color) ");
                redFound = in.nextInt();
                if (redFound != 4) {
                    System.out.print("White pegs? (Correct color, wrong place) ");
                    int whiteFound = in.nextInt();
                    Iterator<Combo> iter = remaining.iterator();
                    while (iter.hasNext()) {
                        if (!iter.next().works(best, redFound, whiteFound))
                            iter.remove();
                    }
                }
                System.out.println();
            } while (redFound != 4);
            System.out.println("Found in " + runs);
            System.out.println();
            System.out.println("----------");
            System.out.println();
        }
    }
}
