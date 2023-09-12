package com.example.mm;

public class Main {
   // public static void main(String[] args) {

    public void test() {
        KMeans kMeans = new KMeans();
        Uniform uniform = new Uniform();
        Quadtree quadtree = new Quadtree();
        IndexedImage indexedImage = new IndexedImage();
        ColorPaletteViewer palette = new ColorPaletteViewer();
        ColorHistogramViewer histogram = new ColorHistogramViewer();

        //kMeans.kMeans("C:/folder/win.png", 32, "C:/folder/kmeans.png");
       // uniform.uniform("C:/folder/win.png", 8, "C:/folder/uniform.png");
        quadtree.quadtree("C:/folder/win.png", 8, "C:/folder/quadtree.png");
        indexedImage.indexedImage("C:/folder/kmeans.png", 64, "C:/folder/indexed.png");
       // palette.colorPalette("C:/folder/indexed.png");
        //histogram.colorHistogram("C:/folder/indexed.png");
    }

}