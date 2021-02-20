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

/**
 * This class imports asset images and use Markov chains based on frequencies of color pixels
 * to generate a new image (Markov image)
 *
 * @author Bruce Tang
 * @date  2021-02-20
 */
public class MarkovImage {

    int[][] pixels;
    HashMap<Integer, Colour> allCol;
    BufferedImage image;
    Random rand;

    public MarkovImage(HashMap<Integer, Colour> allCol, int fileNum) {
        this.allCol = allCol;
        rand = new Random();
        int it = 1;

        while (it <= fileNum) {
            try {
                image = ImageIO.read(new File("/Users/tang/Desktop/Spring 2021/Missions/Mission3/assets2/test"
                        + it + ".jpg"));
            } catch (IOException e) {
                System.out.println("Image not found!");
            }

            //imported image dissected into individual pixels and stored in a 2D array.
            pixels = new int[image.getWidth()][image.getHeight()];

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    pixels[i][j] = image.getRGB(i, j);
                }
            }
            this.getProb();
            this.createImg(it);
            it++;
        }

    }

    private void getProb() {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                //using a hash map to store colour objects and their RGB value
                //makes it easier to go back and forth
                if (!allCol.containsKey(pixels[i][j])) {
                    Colour col = new Colour(pixels[i][j]);
                    allCol.put(pixels[i][j], col);
                }

                Colour curCol = allCol.get(pixels[i][j]);
                //connect surrounding colors if inbound
                if (i - 1 >= 0) {
                    curCol.addColor(pixels[i-1][j]);
                }
                if (i + 1 < pixels.length) {
                    curCol.addColor(pixels[i+1][j]);
                }
                if (j - 1 >= 0) {
                    curCol.addColor(pixels[i][j-1]);
                }
                if (j + 1 < pixels[0].length) {
                    curCol.addColor(pixels[i][j+1]);
                }

            }
        }
    }

    public void createImg(int it) {
        BufferedImage img = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);
        //pick a random color to start with
        Object[] colours = allCol.keySet().toArray();
        int newCol = (int) colours[rand.nextInt(allCol.size())];

        //fill by row
//        for (int i = 0; i < HEIGHT; i++) {
//            for (int j = 0; j < WIDTH; j++) {
//                img.setRGB(i, j, newCol);
//                com.company.Colour curCol = allCol.get(newCol);
//                newCol = curCol.getRandCol();
//            }
//        }

        //fill by BFS from (0,0)
        Queue<int[]> q = new LinkedList<>();
        Boolean[][] filled = new Boolean[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                filled[i][j] = false;
            }
        }
        img.setRGB(0, 0, newCol);
        q.add(new int[] {0, 0});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            Colour curCol = allCol.get(img.getRGB(cur[0], cur[1]));

            if (cur[0] + 1 < pixels.length && cur[1] < pixels[0].length && !filled[cur[0] + 1][cur[1]]) {
                q.add(new int[] {cur[0] + 1, cur[1]});
                img.setRGB(cur[0] + 1, cur[1], curCol.getRandCol());
                filled[cur[0] + 1][cur[1]] = true;
            }
            if (cur[1] + 1 < pixels[0].length && cur[0] < pixels.length && !filled[cur[0]][cur[1] + 1]) {
                q.add(new int[] {cur[0], cur[1] + 1});
                img.setRGB(cur[0], cur[1] + 1, curCol.getRandCol());
                filled[cur[0]][cur[1] + 1] = true;
            }
        }

        try {
            ImageIO.write(img, "jpg", new File("/Users/tang/Desktop/Spring 2021/" +
                    "Missions/Mission3/examples/gen" + it + ".jpg"));
            System.out.println("Markov Image generated!");
        } catch (IOException e) {
            System.out.println("FAILURE");
        }
    }
}
