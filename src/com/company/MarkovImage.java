package com.company;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

//read the image; for each image generate a 2d array of rgb values
//iterate through the rgb values to create adjcolor map
//all colors are stored in a hashmap, which will be passed in
//https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
//https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html

public class MarkovImage {

    int DIM = 500;
    int[][] pixels;
    HashMap<Integer, Colour> allCol;
    BufferedImage image;
    Random rand;

    public MarkovImage(HashMap<Integer, Colour> allCol, int fileNum) {
        pixels = new int[DIM][DIM];
        this.allCol = allCol;
        rand = new Random();
        int it = 1;

        while (it <= fileNum) {
            try {
                image = ImageIO.read(new File("/Users/tang/Desktop/Spring 2021/Missions/images/test"
                        + it + ".jpg"));
            } catch (IOException e) {
                System.out.println("Image not found!");
            }

            for (int i = 0; i < DIM; i++) {
                for (int j = 0; j < DIM; j++) {
                    pixels[i][j] = image.getRGB(i, j);
                }
            }
            this.getProb();
            it++;
        }
        this.createImg();
    }


    private void getProb() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (!allCol.containsKey(pixels[i][j])) {
                    Colour col = new Colour(pixels[i][j]);
                    allCol.put(pixels[i][j], col);
                }

                Colour curCol = allCol.get(pixels[i][j]);
                //connect surrounding colors
                if (i - 1 >= 0) {
                    curCol.addColor(pixels[i-1][j]);
                }
                if (i + 1 < DIM) {
                    curCol.addColor(pixels[i+1][j]);
                }
                if (j - 1 >= 0) {
                    curCol.addColor(pixels[i][j-1]);
                }
                if (j + 1 < DIM) {
                    curCol.addColor(pixels[i][j+1]);
                }

            }
        }
    }

    public void createImg() {
        BufferedImage img = new BufferedImage(DIM, DIM, BufferedImage.TYPE_INT_RGB);
        //pick a random color to start with
        Object[] colours = allCol.keySet().toArray();
        int newCol = (int) colours[rand.nextInt(allCol.size())];

        //fill by row
//        for (int i = 0; i < HEIGHT; i++) {
//            for (int j = 0; j < WIDTH; j++) {
//                img.setRGB(i, j, newCol);
//                Colour curCol = allCol.get(newCol);
//                newCol = curCol.getRandCol();
//            }
//        }

        //fill by BFS from (0,0)
        Queue<int[]> q = new LinkedList<>();
        Boolean[][] filled = new Boolean[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                filled[i][j] = false;
            }
        }
        img.setRGB(0, 0, newCol);
        q.add(new int[] {0, 0});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            Colour curCol = allCol.get(img.getRGB(cur[0], cur[1]));

            //System.out.println(cur[0] + 1 < WIDTH);
            if (cur[0] + 1 < DIM && cur[1] < DIM && !filled[cur[0] + 1][cur[1]]) {
                q.add(new int[] {cur[0] + 1, cur[1]});
                img.setRGB(cur[0] + 1, cur[1], curCol.getRandCol());
                filled[cur[0] + 1][cur[1]] = true;
            }
            if (cur[1] + 1 < DIM && cur[0] < DIM && !filled[cur[0]][cur[1] + 1]) {
                q.add(new int[] {cur[0], cur[1] + 1});
                img.setRGB(cur[0], cur[1] + 1, curCol.getRandCol());
                filled[cur[0]][cur[1] + 1] = true;
            }
        }

        try {
            ImageIO.write(img, "jpg", new File("/Users/tang/Desktop/Spring 2021/Missions/result/" +
                    "gen.jpg"));
            System.out.println("Markov Image generated!");
        } catch (IOException e) {
            System.out.println("FAILURE");
        }
    }
}
