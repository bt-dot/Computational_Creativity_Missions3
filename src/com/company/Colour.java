package com.company;
import java.util.*;

/**
 * The Colour class specifies a color with its integer RGB value, along with
 * a hash map consists of its adjacent colors and corresponding frequencies.
 *
 * @author  Bruce Tang
 * @date  2021-02-20
 */
public class Colour {
    HashMap<Integer, Integer> adjColors;
    int curCol;

    public Colour(int curCor) {
        adjColors = new HashMap<>();
        this.curCol = curCor;
    }

    /**
     * This method adds a new adjacent color to the hash map of current colour
     * @param newColor the integer RGB value of the new color being added
     */
    //add new color encountered to the adj map of the current color
    public void addColor(int newColor) {
        if (adjColors.containsKey(newColor)) {
            adjColors.put(newColor, adjColors.get(newColor) + 1);
        } else {
            adjColors.put(newColor, 1);
        }
    }

    /**
     * This method used the frequencies to randomly pick an adjacent color to fill the new image.
     * @return int This returns the integer RGB value of a random color
     */
    //picking a random color by frequency of appearance in adjacent spot
    public int getRandCol() {
        int sum = 0;
        for (int freq : adjColors.values()) {
            sum += freq;
        }
        Random rand = new Random();
        int randCol = rand.nextInt(sum);
        for (Map.Entry<Integer, Integer> entry : adjColors.entrySet()) {
            if (randCol < entry.getValue()) {
                return entry.getKey();
            }
            randCol -= entry.getValue();
        }
        return randCol;
    }
}