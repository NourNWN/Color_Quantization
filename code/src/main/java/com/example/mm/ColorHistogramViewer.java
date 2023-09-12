package com.example.mm;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ColorHistogramViewer {
    // public static void main(String[] args) {

    /*public void colorHistogram(String input) {

        //String inputImagePath = "C:/folder/indexed.png";
        BufferedImage inputImage = IOFils.loadImage(input);

        //BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        // Create an array to store color frequencies
        int[] colorHistogram = new int[256];

        // Calculate color frequencies
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                Color color = new Color(rgb);
                int red = (int) color.getRed();
                int green = (int) color.getGreen();
                int blue = (int) color.getBlue();

                // Convert RGB to grayscale
                int grayscale = (red + green + blue) / 3;

                // Increment the frequency count for the grayscale value
                colorHistogram[grayscale]++;
            }
        }

        // Display the color histogram
        for (int i = 0; i < colorHistogram.length; i++) {
            System.out.println("Grayscale " + i + ": " + colorHistogram[i]);
        }
    } */

  /*  public void initialize() {
        String imagePath = "path/to/your/image.jpg"; // تعديل المسار إلى صورتك الفعلية
        int numBins = 256; // عدد الـ bins المطلوبة في الـ histogram

        Image image = new Image(imagePath);
        PixelReader pixelReader = image.getPixelReader();
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();

        int[] histogramRed = new int[numBins];
        int[] histogramGreen = new int[numBins];
        int[] histogramBlue = new int[numBins];

        // حساب الـ histogram لكل قناة اللون (أحمر، أخضر، أزرق)
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color color = pixelReader.getColor(x, y);
                int red = (int) (color.getRed() * (numBins - 1));
                int green = (int) (color.getGreen() * (numBins - 1));
                int blue = (int) (color.getBlue() * (numBins - 1));
                histogramRed[red]++;
                histogramGreen[green]++;
                histogramBlue[blue]++;
            }
        }

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Intensity");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequency");

        XYChart.Series<String, Number> seriesRed = new XYChart.Series<>();
        seriesRed.setName("Red");
        XYChart.Series<String, Number> seriesGreen = new XYChart.Series<>();
        seriesGreen.setName("Green");
        XYChart.Series<String, Number> seriesBlue = new XYChart.Series<>();
        seriesBlue.setName("Blue");

        for (int i = 0; i < numBins; i++) {
            seriesRed.getData().add(new XYChart.Data<>(String.valueOf(i), histogramRed[i]));
            seriesGreen.getData().add(new XYChart.Data<>(String.valueOf(i), histogramGreen[i]));
            seriesBlue.getData().add(new XYChart.Data<>(String.valueOf(i), histogramBlue[i]));
        }

        histogramChart.getData().addAll(seriesRed, seriesGreen, seriesBlue);
    }*/

   /* public void displayColorHistogram(String imagePath) {
            // Load the image
            BufferedImage image = IOFils.loadImage(imagePath);

            // Get the dimensions of the image
            int width = image.getWidth();
            int height = image.getHeight();

            // Create an array to store the histogram values for each color channel
            int[] redHistogram = new int[256];
            int[] greenHistogram = new int[256];
            int[] blueHistogram = new int[256];

            // Compute the histogram for each color channel
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get the RGB values of the pixel
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Increment the corresponding histogram bin for each color channel
                    redHistogram[red]++;
                    greenHistogram[green]++;
                    blueHistogram[blue]++;
                }
            }

            // Display the color histogram
            System.out.println("Red Histogram:");
            for (int i = 0; i < 256; i++) {
                System.out.println("Bin " + i + ": " + redHistogram[i]);
            }

            System.out.println("Green Histogram:");
            for (int i = 0; i < 256; i++) {
                System.out.println("Bin " + i + ": " + greenHistogram[i]);
            }

            System.out.println("Blue Histogram:");
            for (int i = 0; i < 256; i++) {
                System.out.println("Bin " + i + ": " + blueHistogram[i]);
            }

    } */

    public int[] getColorHistogramData(String imagePath) {
        // قم بتحميل الصورة باستخدام imagePath
        BufferedImage image = IOFils.loadImage(imagePath);

        // قم بحساب عدد القنوات للصورة (مثلاً RGB)
        int numChannels = 8;

        // قم بتهيئة مصفوفة لتخزين بيانات الهيستوجرام
        int[] histogramData = new int[256 * numChannels];

        // قم بحساب الهيستوجرام
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                // استخراج قيم القنوات
                int[] channelValues = new int[numChannels];
                for (int i = 0; i < numChannels; i++) {
                    channelValues[i] = (pixel >> (8 * i)) & 0xFF;
                }

                // تحديث العدادات في مصفوفة الهيستوجرام
                for (int i = 0; i < numChannels; i++) {
                    histogramData[i * 256 + channelValues[i]]++;
                }
            }
        }

        return histogramData;
    }



}

