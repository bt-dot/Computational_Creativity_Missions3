# Computational_Creativity_Missions3
A coding project that uses a Markov chain to generate visual art

This system takes in a folder of image(s) and store each pixels and those that are around it in a hash map. The program than utilizes that relationship to generate images based on the frequency of adjacent pixels. 

To run the program, change the file path in Markov Image class to the desire asset folder. Then simply run the Main class. The images generated will be stored in the example folder. (java files can be found in the src folder)

This project seemed impossible to me initially as I have never dealt with image processing in java before. The idea of dissecting an image into pixels and manipulate them was a big "nope" for me. So to approach this, I assumed I know how to manipulate pixels and went ahead with the rest of the project. The rest of the project is mostly utilizing data structures (Hashmap mostly) to associate indiviudal colors with its adjacent ones. By the time I figured all that out, the image stuff didn't seem to be that big of a challenge as before. And after reading about bufferedimage on oracle and stackoverflow it was quickly resolved. 
Moving forward, I need to get comfortable with learning new libraries and to use them. I do think i'm comfortable with reading documentations, but the idea of having to do that sometimes discourage me from starting a new project, and that's something I need to get over with. 

Sources credited: 
https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html

I don't think this system is creative necessarily. From what I have seen, I don't believe Markov chain produces creative outputs. These are merely generating ouputs probabilistically, and in the case of image processing, I don't think mostly random color pixels together can be considered creative or art. I think some type of guidence and rules must be applied on top of the probilities for something to be considered creative. 

