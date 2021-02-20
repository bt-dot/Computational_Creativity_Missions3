package com.company;

import java.util.*;

public class Colour {
    HashMap<Integer, Integer> adjColors;
    int curCol;

    public Colour(int curCor) {
        adjColors = new HashMap<>();
        this.curCol = curCor;
    }

    //add new color encountered to the adj map of the current color
    public void addColor(int newColor) {
        if (adjColors.containsKey(newColor)) {
            adjColors.put(newColor, adjColors.get(newColor) + 1);
        } else {
            adjColors.put(newColor, 1);
        }
    }

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
