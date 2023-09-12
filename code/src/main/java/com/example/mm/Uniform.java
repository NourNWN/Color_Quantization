package com.example.mm;

import java.awt.image.BufferedImage;

public class Uniform {

    public void uniform(String input,  int quantizationLevel, String output) {
        //int quantizationLevel = 128;

        BufferedImage image = IOFils.loadImage(input);
        int width = image.getWidth();
        int height = image.getHeight();

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                for (int rc = 0; rc < quantizationLevel; rc++) {
                    for (int cc = 0; cc < quantizationLevel; cc++) {
                        int pixelX = c * quantizationLevel + cc;
                        int pixelY = r * quantizationLevel + rc;

                        if (pixelX < width && pixelY < height) {
                            int quantizationLevelValue = calculateQuantizationLevel(image.getRGB(pixelX, pixelY), quantizationLevel);
                            int L1 = quantizationLevelValue;
                            int L2 = L1 + 1;
                            int I2 = (L1 + L2) / 2;
                            int outputPixelValue = I2 * quantizationLevel;
                            image.setRGB(pixelX, pixelY, outputPixelValue);
                        }
                    }
                }
            }
        }

        IOFils.saveImage(image, output);
    }

    private static int calculateQuantizationLevel(int rgbValue, int quantizationLevel) {
        int red = (rgbValue >> 16) & 0xFF;
        int green = (rgbValue >> 8) & 0xFF;
        int blue = rgbValue & 0xFF;

        int maxColorValue = 255;
        int quantizationLevelValue = (int) ((red / (double) maxColorValue) * quantizationLevel);
        return quantizationLevelValue;
    }
}




