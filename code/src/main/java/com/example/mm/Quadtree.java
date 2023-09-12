package com.example.mm;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Quadtree {
    private static class QuadtreeNode {
        private int colorCount;
        private int redSum;
        private int greenSum;
        private int blueSum;
        private int alphaSum;
        private QuadtreeNode[] children;

        public QuadtreeNode() {
            colorCount = 0;
            redSum = 0;
            greenSum = 0;
            blueSum = 0;
            alphaSum = 0;
            children = new QuadtreeNode[4];
        }
    }

    private static final int MAX_COLOR_LEVELS = 8; // Number of bits per color channel (8-bit RGB)
    private static final int MAX_TREE_DEPTH = 8; // Maximum depth of the quadtree

    private QuadtreeNode root;

    public Quadtree() {
        root = new QuadtreeNode();
    }

    public BufferedImage quantizeImage(BufferedImage image, int colorLevels) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage quantizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Build the quadtree
        buildQuadtree(image);

        // Quantize each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = new Color(image.getRGB(x, y));
                Color quantizedColor = quantizeColor(originalColor, colorLevels);
                quantizedImage.setRGB(x, y, quantizedColor.getRGB());
            }
        }

        return quantizedImage;
    }

    private void buildQuadtree(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Insert each pixel into the quadtree
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                insertColor(color, root, 0);
            }
        }
    }

    private void insertColor(Color color, QuadtreeNode node, int depth) {
        if (depth == MAX_TREE_DEPTH) {
            // Reached the maximum depth, update the color statistics
            node.colorCount++;
            node.redSum += color.getRed();
            node.greenSum += color.getGreen();
            node.blueSum += color.getBlue();
            node.alphaSum += color.getAlpha();
        } else {
            int index = getIndex(color, depth);

            if (node.children[index] == null) {
                node.children[index] = new QuadtreeNode();
            }

            insertColor(color, node.children[index], depth + 1);
        }
    }

    private int getIndex(Color color, int depth) {
        int red = color.getRed() >> (MAX_COLOR_LEVELS - depth - 1) & 1;
        int green = color.getGreen() >> (MAX_COLOR_LEVELS - depth - 1) & 1;
        int blue = color.getBlue() >> (MAX_COLOR_LEVELS - depth - 1) & 1;
        int alpha = color.getAlpha() >> (MAX_COLOR_LEVELS - depth - 1) & 1;

        return (red << 1) | green << 1 | blue;
    }

    private Color quantizeColor(Color color, int colorLevels) {
        int red = quantizeChannel(color.getRed(), colorLevels);
        int green = quantizeChannel(color.getGreen(), colorLevels);
        int blue = quantizeChannel(color.getBlue(), colorLevels);
        int alpha = quantizeChannel(color.getAlpha(), colorLevels);

        return new Color(red, green, blue, alpha);
    }

    private int quantizeChannel(int value, int colorLevels) {
        int colorRange = 255;
        int colorStep = colorRange / (colorLevels - 1);
        int quantizedValue = Math.round(Math.round(value / (double) colorStep) * colorStep);
        return Math.min(colorRange, quantizedValue);
    }

    public void quadtree(String input,  int colorLevels, String output) {
        BufferedImage inputImage = IOFils.loadImage(input);

        Quadtree colorQuantizer = new Quadtree();
        BufferedImage outputImage = colorQuantizer.quantizeImage(inputImage, colorLevels);

        IOFils.saveImage(outputImage, output);
    }
}

