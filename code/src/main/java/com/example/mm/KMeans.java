package com.example.mm;

import java.awt.image.BufferedImage;

/*public class KMeans {
    public void kMeans(String input, int k, String  output) {
        // تحميل الصورة
        BufferedImage image = IOFils.loadImage(input);

        // استخراج بيانات الصورة
        assert image != null;
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = image.getRGB(i, j);
            }
        }

        // تعيين عدد الألوان
        //int k = 16;

        // تطبيق خوارزمية K-means
        int[][] clusteredPixels = kmeans(pixels, k);

        // تحويل الصورة المجزأة إلى BufferedImage
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                resultImage.setRGB(i, j, clusteredPixels[i][j]);
            }
        }

        // حفظ الصورة المجزأة
        IOFils.saveImage(image, output);
    }

    private static int[][] kmeans(int[][] pixels, int k) {
        int width = pixels.length;
        int height = pixels[0].length;
        int[][] clusteredPixels = new int[width][height];
        int[] centers = new int[k];

        // تحديد قيم بدءية للمراكز
        for (int i = 0; i < k; i++) {
            centers[i] = pixels[(i * width) / k][(i * height) / k];
        }

        boolean converged = false;

        while (!converged) {
            // حساب المسافة بين كل بكسل ومراكز الألوان
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = pixels[i][j];
                    int minDistance = Integer.MAX_VALUE;
                    int closestCenter = 0;

                    for (int c = 0; c < k; c++) {
                        int distance = calculateDistance(pixel, centers[c]);

                        if (distance < minDistance) {
                            minDistance = distance;
                            closestCenter = c;
                        }
                    }

                    clusteredPixels[i][j] = centers[closestCenter];
                }
            }

            // تحديث المراكز
            int[] newCenters = new int[k];
            int[] counts = new int[k];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = clusteredPixels[i][j];
                    int centerIndex = findCenterIndex(pixel, centers);

                    newCenters[centerIndex] += pixel;
                    counts[centerIndex]++;
                }
            }

            // حساب المراكز الجديدة
            for (int c = 0; c < k; c++) {
                if (counts[c] > 0) {
                    newCenters[c] /= counts[c];
                }
            }

            // التحقق من تحقيق التقارب
            converged = true;

            for (int c = 0; c < k; c++) {
                if (newCenters[c] != centers[c]) {
                    converged = false;
                    break;
                }
            }

            // تحديث المراكز
            centers = newCenters;
        }

        return clusteredPixels;
    }

    private static int calculateDistance(int pixel1, int pixel2) {
        Color color1 = new Color(pixel1);
        Color color2 = new Color(pixel2);
        int redDiff = color1.getRed() - color2.getRed();
        int greenDiff = color1.getGreen() - color2.getGreen();
        int blueDiff = color1.getBlue() - color2.getBlue();
        return (redDiff * redDiff) + (greenDiff * greenDiff) + (blueDiff * blueDiff);
    }

    private static int findCenterIndex(int pixel, int[] centers) {
        int minDistance = Integer.MAX_VALUE;
        int closestCenter = 0;

        for (int c = 0; c < centers.length; c++) {
            int distance = calculateDistance(pixel, centers[c]);

            if (distance < minDistance) {
                minDistance = distance;
                closestCenter = c;
            }
        }

        return closestCenter;
    }


} */


import java.util.ArrayList;
import java.util.List;

import java.awt.image.WritableRaster;

public class KMeans {

        public void kMeans(String input, int k, String output) {

            BufferedImage image = IOFils.loadImage(input);
            int width = image.getWidth();
            int height = image.getHeight();

            // Convert the image to RGB color model
            BufferedImage rgbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            rgbImage.getGraphics().drawImage(image, 0, 0, null);

            // Create a list to store the RGB pixel values
            List<Integer> pixels = new ArrayList<>();

            // Collect the RGB pixel values from the image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = rgbImage.getRGB(x, y);
                    pixels.add(rgb);
                }
            }

            // Apply K-Means clustering to the pixel values
            List<Integer> quantizedPixels = kMeansClustering(pixels, k);

            // Create a new image with the quantized pixel values
            BufferedImage quantizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            WritableRaster raster = quantizedImage.getRaster();

            // Assign the quantized pixel values to the image
            for (int i = 0; i < quantizedPixels.size(); i++) {
                int x = i % width;
                int y = i / width;
                int rgb = quantizedPixels.get(i);

                raster.setPixel(x, y, new int[]{(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF});
            }

       IOFils.saveImage(quantizedImage, output);
            //return quantizedImage;
        }

    private static List<Integer> kMeansClustering(List<Integer> pixels, int k) {
        List<Integer> centroids = initializeCentroids(pixels, k);

        while (true) {
            List<Integer> newCentroids = new ArrayList<>();
            List<List<Integer>> clusters = new ArrayList<>();

            // Initialize empty clusters
            for (int i = 0; i < k; i++) {
                clusters.add(new ArrayList<>());
            }

            // Assign each pixel to the nearest centroid
            for (int pixel : pixels) {
                int nearestCentroid = findNearestCentroid(pixel, centroids);
                clusters.get(nearestCentroid).add(pixel);
            }

            // Calculate new centroids based on the average of the cluster pixels
            for (List<Integer> cluster : clusters) {
                if (cluster.isEmpty()) {
                    newCentroids.add(0);
                } else {
                    int sumR = 0, sumG = 0, sumB = 0;
                    for (int pixel : cluster) {
                        sumR += (pixel >> 16) & 0xFF;
                        sumG += (pixel >> 8) & 0xFF;
                        sumB += pixel & 0xFF;
                    }
                    int avgR = sumR / cluster.size();
                    int avgG = sumG / cluster.size();
                    int avgB = sumB / cluster.size();
                    int newCentroid = (avgR << 16) | (avgG << 8) | avgB;
                    newCentroids.add(newCentroid);
                }
            }

            // Check if centroids have converged
            if (newCentroids.equals(centroids)) {
                break;
            }

            centroids = newCentroids;
        }

        // Map the pixel values to the nearest centroid values
        List<Integer> quantizedPixels = new ArrayList<>();
        for (int pixel : pixels) {
            int nearestCentroid = findNearestCentroid(pixel, centroids);
            quantizedPixels.add(centroids.get(nearestCentroid));
        }

        return quantizedPixels;
    }

    private static List<Integer> initializeCentroids(List<Integer> pixels, int k) {
        List<Integer> centroids = new ArrayList<>();

        // Choose initial centroids randomly from the pixel values
        for (int i = 0; i < k; i++) {
            int randomIndex = (int) (Math.random() * pixels.size());
            int centroid = pixels.get(randomIndex);
            centroids.add(centroid);
        }

        return centroids;
    }

    private static int findNearestCentroid(int pixel, List<Integer> centroids) {
        int nearestCentroid = 0;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            int centroid = centroids.get(i);
            int distance = calculateDistance(pixel, centroid);

            if (distance < minDistance) {
                minDistance = distance;
                nearestCentroid = i;
            }
        }

        return nearestCentroid;
    }

    private static int calculateDistance(int pixel1, int pixel2) {
        int r1 = (pixel1 >> 16) & 0xFF;
        int g1 = (pixel1 >> 8) & 0xFF;
        int b1 = pixel1 & 0xFF;

        int r2 = (pixel2 >> 16) & 0xFF;
        int g2 = (pixel2 >> 8) & 0xFF;
        int b2 = pixel2 & 0xFF;

        int dr = r1 - r2;
        int dg = g1 - g2;
        int db = b1 - b2;

        return dr * dr + dg * dg + db * db;
    }
    }



   /* private static class Point {
        int x;
        int y;
        int z;
        int cluster;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public void kMeans(String input, int k, String  output) {
       // String imagePath = "path/to/your/image.jpg";
        BufferedImage imagePath = IOFils.loadImage(input);
        int numClusters = 8;

            //BufferedImage image = ImageIO.read(imagePath);
            List<Point> points = extractPointsFromImage(imagePath);
            List<Point> clusters = initializeClusters(points, numClusters);

            kmeans(points, clusters);

            BufferedImage resultImage = createResultImage(imagePath, clusters);
            IOFils.saveImage(resultImage, output);

            System.out.println("K-means clustering completed successfully!");
    }

    private static List<Point> extractPointsFromImage(BufferedImage image) {
        List<Point> points = new ArrayList<>();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                points.add(new Point(color.getRed(), color.getGreen(), color.getBlue()));
            }
        }

        return points;
    }

    private static List<Point> initializeClusters(List<Point> points, int numClusters) {
        List<Point> clusters = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numClusters; i++) {
            int index = random.nextInt(points.size());
            Point cluster = new Point(points.get(index).x, points.get(index).y, points.get(index).z);
            cluster.cluster = i;
            clusters.add(cluster);
        }

        return clusters;
    }

    private static void kmeans(List<Point> points, List<Point> clusters) {
        boolean changed;

        do {
            changed = assignPointsToClusters(points, clusters);
            updateClusterCenters(points, clusters);
        } while (changed);
    }

    private static boolean assignPointsToClusters(List<Point> points, List<Point> clusters) {
        boolean changed = false;

        for (Point point : points) {
            double minDistance = Double.MAX_VALUE;
            int newCluster = point.cluster;

            for (int i = 0; i < clusters.size(); i++) {
                double distance = calculateDistance(point, clusters.get(i));

                if (distance < minDistance) {
                    minDistance = distance;
                    newCluster = i;
                }
            }

            if (newCluster != point.cluster) {
                point.cluster = newCluster;
                changed = true;
            }
        }

        return changed;
    }

    private static double calculateDistance(Point p1, Point p2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static void updateClusterCenters(List<Point> points, List<Point> clusters) {
        int[] count = new int[clusters.size()];
        int[] sumRed = new int[clusters.size()];
        int[] sumGreen = new int[clusters.size()];
        int[] sumBlue = new int[clusters.size()];

        for (Point point : points) {
            int cluster = point.cluster;
            count[cluster]++;
            sumRed[cluster] += point.x;
            sumGreen[cluster] += point.y;
            sumBlue[cluster] += point.z;
        }

        for (int i = 0; i < clusters.size(); i++) {
            Point cluster = clusters.get(i);
            cluster.x = sumRed[i] / count[i];
            cluster.y = sumGreen[i] / count[i];
            cluster.z = sumBlue[i] / count[i];
        }
    }

    private static BufferedImage createResultImage(BufferedImage originalImage, List<Point> clusters) {
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                int rgb = originalImage.getRGB(x, y);
                Color color = new Color(rgb);
                Point point = new Point(color.getRed(), color.getGreen(), color.getBlue());
                int cluster = findNearestCluster(point, clusters);
                resultImage.setRGB(x, y, clusters.get(cluster).toRGB());
            }
        }

        return resultImage;
    }

    private static int findNearestCluster(Point point, List<Point> clusters) {
        double minDistance = Double.MAX_VALUE;
        int nearestCluster = -1;

        for (int i = 0; i < clusters.size(); i++) {
            double distance = calculateDistance(point, clusters.get(i));

            if (distance < minDistance) {
                minDistance = distance;
                nearestCluster = i;
            }
        }

        return nearestCluster;
    }
} */


