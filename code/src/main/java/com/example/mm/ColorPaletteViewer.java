package com.example.mm;

import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Arrays;
import java.util.Comparator;

public class ColorPaletteViewer {
    // public static void main(String[] args) {

   /* public void colorPalette(String input) {

        //String inputImagePath = "C:/folder/indexed.png";
        BufferedImage inputImage = IOFils.loadImage(input);

        // BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create an array to store unique colors
        boolean[][] visited = new boolean[width][height];
        Color[] colorPalette = new Color[width * height];
        int colorCount = 0;

        // Iterate through each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                Color color = new Color(rgb);

                // Check if the color is already visited
                if (!visited[x][y]) {
                    // Add the unique color to the palette
                    colorPalette[colorCount] = color;
                    colorCount++;

                    // Mark all pixels with the same color as visited
                    for (int j = y; j < height; j++) {
                        for (int i = x; i < width; i++) {
                            if (inputImage.getRGB(i, j) == rgb) {
                                visited[i][j] = true;
                            }
                        }
                    }
                }
            }
        }

        // Display the color palette
       /* for (int i = 0; i < colorCount; i++) {
            Color color = colorPalette[i];
            System.out.println("Color " + i + ": " + color);
        }*/
   // } */

   /* public void colorPalette(String imagePath, ListView<Color> colorPaletteListView) {
        BufferedImage inputImage = IOFils.loadImage(imagePath);

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create an array to store unique colors
        boolean[][] visited = new boolean[width][height];
        Color[] colorPalette = new Color[width * height];
        int colorCount = 0;

        // Iterate through each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                Color color = new Color(rgb);

                // Check if the color is already visited
                if (!visited[x][y]) {
                    // Add the unique color to the palette
                    colorPalette[colorCount] = color;
                    colorCount++;

                    // Mark all pixels with the same color as visited
                    for (int j = y; j < height; j++) {
                        for (int i = x; i < width; i++) {
                            if (inputImage.getRGB(i, j) == rgb) {
                                visited[i][j] = true;
                            }
                        }
                    }
                }
            }
        }

        // Display the color palette
        ObservableList<Color> paletteItems = FXCollections.observableArrayList();
        for (int i = 0; i < colorCount; i++) {
            Color color = colorPalette[i];
            paletteItems.add(color);
        }

        colorPaletteListView.setItems(paletteItems);
    } */

    /*public Color[] generatePalette(String imagePath, int paletteSize) {
        BufferedImage image = IOFils.loadImage(imagePath);
        return generatePalette(image, paletteSize);
    }*/

    public Color[] generatePalette(String imagePath, int paletteSize) {
        BufferedImage image = IOFils.loadImage(imagePath);
        int width =  image.getWidth();
        int height = image.getHeight();
       // Color[] palette = new Color[paletteSize];

        int[] pixels = new int[width * height];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try {
            pixelGrabber.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int[] colorCount = new int[256 * 256 * 256];
        for (int pixel : pixels) {
            int red = (pixel >> 16) & 0xFF;
            int green = (pixel >> 8) & 0xFF;
            int blue = pixel & 0xFF;
            int index = (red << 16) | (green << 8) | blue;
            colorCount[index]++;
        }

        Color[] palette = new Color[paletteSize];
        Integer[] sortedIndices = new Integer[colorCount.length];
        for (int i = 0; i < sortedIndices.length; i++) {
            sortedIndices[i] = i;
        }
        Arrays.sort(sortedIndices, Comparator.comparingInt(a -> colorCount[a]));
        for (int i = 0; i < paletteSize; i++) {
            int index = sortedIndices[colorCount.length - i - 1];
            int red = (index >> 16) & 0xFF;
            int green = (index >> 8) & 0xFF;
            int blue = index & 0xFF;
            palette[i] = Color.rgb(red, green, blue);
        }

        return palette;
    }

}
