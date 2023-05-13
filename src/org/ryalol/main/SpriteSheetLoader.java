package org.ryalol.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class SpriteSheetLoader {
    //private static final int FRAME_SIZE;
	
	private BufferedImage[][] frames;

    public SpriteSheetLoader(String filePath, int FRAME_SIZE) throws IOException {
        loadSpriteSheet(filePath, FRAME_SIZE);
    }

    private void loadSpriteSheet(String filePath, int FRAME_SIZE) throws IOException {
        // Load the spritesheet image
        BufferedImage spritesheet = ImageIO.read(new File(filePath));

        // Get the number of frames horizontally and vertically
        int numFramesX = spritesheet.getWidth() / FRAME_SIZE;
        int numFramesY = spritesheet.getHeight() / FRAME_SIZE;

        // Store the frames in a 2D array
        frames = new BufferedImage[numFramesY][numFramesX];

        // Extract each frame and store it in the array
        for (int y = 0; y < numFramesY; y++) {
            for (int x = 0; x < numFramesX; x++) {
                frames[y][x] = spritesheet.getSubimage(x * FRAME_SIZE, y * FRAME_SIZE, FRAME_SIZE, FRAME_SIZE);
            }
        }
    }

    public BufferedImage[][] getFrames() {
        return frames;
    }

    public BufferedImage[] playRow(int rowIndex) {
        
        if (rowIndex < 0 || rowIndex >= frames.length) {
            throw new IllegalArgumentException("Invalid row index");
        }

        BufferedImage[] rowFrames = frames[rowIndex];
        List<BufferedImage> nonEmptyFrames = new ArrayList<>();

        // Check each frame in the row and add non-empty frames to the list
        for (BufferedImage frame : rowFrames) {
            boolean isEmpty = true;

            // Check if the frame is empty (contains only transparent pixels)
            for (int pixelX = 0; pixelX < frame.getWidth(); pixelX++) {
                for (int pixelY = 0; pixelY < frame.getHeight(); pixelY++) {
                    int pixel = frame.getRGB(pixelX, pixelY);
                    if ((pixel >> 24) != 0x00) { // Check the alpha channel of the pixel
                        isEmpty = false;
                        break;
                    }
                }
                if (!isEmpty) {
                    break;
                }
            }

            // Add non-empty frames to the list
            if (!isEmpty) {
                nonEmptyFrames.add(frame);
            }
        }

        // Convert the list to an array
        BufferedImage[] result = new BufferedImage[nonEmptyFrames.size()];
        nonEmptyFrames.toArray(result);

        return result;

    }

    /*public static void playRow(BufferedImage[][] frames, int row, Graphics2D g2, int x, int y) {
        // Validate the row index
        if (row < 0 || row >= frames.length) {
            System.out.println("Invalid row index.");
            return;
        }

        // Iterate through the frames in the specified row and process each frame
        for (BufferedImage frame : frames[row]) {
            // Process each frame as needed
            // ...
            // For example, you can display the frame or perform operations on it
            System.out.println("Displaying frame of row " + row);
            g2.drawImage(frame, x, y, null);
            //displayFrame(frame);
        }
    }*/

}