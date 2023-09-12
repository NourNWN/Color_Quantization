package com.example.mm;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import static com.example.mm.IOFils.loadImage;

public class HelloController implements Initializable  {

    private Uniform uniform;
    private KMeans kMeans;
    private Quadtree quadtree;
    private ColorPaletteViewer colorPaletteViewer;
    private ColorHistogramViewer colorHistogramViewer;
    private IndexedImage indexedImage;
    @FXML
    public ImageView indexedResult;
    @FXML
    public MenuItem kMeansMenuItem;
    @FXML
    public ImageView kmeansResult;
    @FXML
    public MenuItem quadTreeMenuItem;
    @FXML
    public ImageView quadTreeResult;
    @FXML
    public ImageView uniformResult;
    @FXML
    public MenuItem uniformMenuItem;
    @FXML
    public MenuItem indexedUniform;
    @FXML
    public MenuItem indexedKMeans;
    @FXML
    public MenuItem indexedQuadtree;
    @FXML
    private Button chooseImage;
    private String selectedImagePath;
    @FXML
    public MenuItem colorPaletteMenuItem;
    @FXML
    private Rectangle color1;

    @FXML
    private Rectangle color2;

    @FXML
    private Rectangle color3;

    @FXML
    private Rectangle color4;

    @FXML
    private Rectangle color5;
    @FXML
    private Rectangle color6;

    @FXML
    private Rectangle color7;

    @FXML
    private Rectangle color8;
    @FXML
    private BarChart<String, Number> histogramChart;
    @FXML
    public MenuItem colorHistogramMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        uniform = new Uniform();
        kMeans = new KMeans();
        quadtree = new Quadtree();
        indexedImage = new IndexedImage();
        colorPaletteViewer = new ColorPaletteViewer();
        colorHistogramViewer = new ColorHistogramViewer();

        //////Uniform to indexed////////
        indexedUniform.setOnAction(event -> {
            String imagePath = "C://testImages//uniform.png"; // احصل على مسار الصورة المحددة من زر chooseImage
            int quantizationLevel = 255; // قيمة مستوى الكمة المحددة بواسطة المستخدم
            String output = "C://testImages//indexed.png";

            // إنشاء ByteArrayOutputStream للحصول على تسلسل ثنائي للصورة
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            indexedImage.indexedImage(imagePath, quantizationLevel, output); // تنفيذ التابع uniform()

            try {
                // تحويل الصورة المجزأة إلى تسلسل ثنائي
                ImageIO.write(ImageIO.read(new File(output)), "png", outputStream);

                // تحويل التسلسل الثنائي إلى Base64
                String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());

                // عرض الصورة الناتجة في الواجهة (resultImageView)
                Image resultImage = new Image("data:image/png;base64," + encodedImage);
                indexedResult.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //////K-Means to Algorithm//////
        indexedKMeans.setOnAction(event -> {
            String imagePath = "C://testImages//kmeans.png"; // احصل على مسار الصورة المحددة من زر chooseImage
            int quantizationLevel = 255; // قيمة مستوى الكمة المحددة بواسطة المستخدم
            String output = "C://testImages//indexed.png";

            // إنشاء ByteArrayOutputStream للحصول على تسلسل ثنائي للصورة
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            indexedImage.indexedImage(imagePath, quantizationLevel, output); // تنفيذ التابع uniform()

            try {
                // تحويل الصورة المجزأة إلى تسلسل ثنائي
                ImageIO.write(ImageIO.read(new File(output)), "png", outputStream);

                // تحويل التسلسل الثنائي إلى Base64
                String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());

                // عرض الصورة الناتجة في الواجهة (resultImageView)
                Image resultImage = new Image("data:image/png;base64," + encodedImage);
                indexedResult.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //////QuadTree to Algorithm///////
        indexedQuadtree.setOnAction(event -> {
            String imagePath = "C://testImages//quadTree.png"; // احصل على مسار الصورة المحددة من زر chooseImage
            int quantizationLevel = 255; // قيمة مستوى الكمة المحددة بواسطة المستخدم
            String output = "C://testImages//indexed.png";

            // إنشاء ByteArrayOutputStream للحصول على تسلسل ثنائي للصورة
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            indexedImage.indexedImage(imagePath, quantizationLevel, output); // تنفيذ التابع uniform()

            try {
                // تحويل الصورة المجزأة إلى تسلسل ثنائي
                ImageIO.write(ImageIO.read(new File(output)), "png", outputStream);

                // تحويل التسلسل الثنائي إلى Base64
                String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());

                // عرض الصورة الناتجة في الواجهة (resultImageView)
                Image resultImage = new Image("data:image/png;base64," + encodedImage);
                indexedResult.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //////////View Color Palette///////
        colorPaletteMenuItem.setOnAction(event -> {
        String imagePath = "C://testImages//KMeans.png"; // تعديل المسار إلى صورتك الفعلية
        int paletteSize = 8; // حجم الـ color palette المطلوب

        javafx.scene.paint.Color[] palette = colorPaletteViewer.generatePalette(imagePath, paletteSize);

        if (palette.length >= 1) {
            color1.setFill(palette[0]);
        }
        if (palette.length >= 2) {
            color2.setFill(palette[1]);
        }
        if (palette.length >= 3) {
            color3.setFill(palette[2]);
        }
        if (palette.length >= 4) {
            color4.setFill(palette[3]);
        }
        if (palette.length >= 5) {
            color5.setFill(palette[4]);
        }
         if (palette.length >= 6) {
             color6.setFill(palette[5]);
         }
         if (palette.length >= 7) {
             color7.setFill(palette[6]);
         }
         if (palette.length >= 8) {
             color8.setFill(palette[7]);
         }
        });

        //////////View Color Histogram///////
        colorHistogramMenuItem.setOnAction(event -> {
            String imagePath = "C://testImages//KMeans.png";
            int[] histogramData = colorHistogramViewer.getColorHistogramData(imagePath);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (int i = 0; i < histogramData.length; i++) {
                series.getData().add(new XYChart.Data<>(String.valueOf(i), histogramData[i]));
            }

            histogramChart.getData().clear();
            histogramChart.getData().add(series);

        });

        ///////Uniform Algorithm
        uniformMenuItem.setOnAction(event -> {
            String imagePath = selectedImagePath; // احصل على مسار الصورة المحددة من زر chooseImage
            int quantizationLevel = 16; // قيمة مستوى الكمة المحددة بواسطة المستخدم

            // إنشاء ByteArrayOutputStream للحصول على تسلسل ثنائي للصورة
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String output = "C://testImages//uniform.png";

            uniform.uniform(imagePath, quantizationLevel, output); // تنفيذ التابع uniform()

            try {
                // تحويل الصورة المجزأة إلى تسلسل ثنائي
                ImageIO.write(ImageIO.read(new File(output)), "png", outputStream);

                // تحويل التسلسل الثنائي إلى Base64
                String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());

                // عرض الصورة الناتجة في الواجهة (resultImageView)
                Image resultImage = new Image("data:image/png;base64," + encodedImage);
                uniformResult.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //////K-Means Algorithm
        kMeansMenuItem.setOnAction(event -> {
            String imagePath = selectedImagePath; // احصل على مسار الصورة المحددة من زر chooseImage
            int quantizationLevel = 16; // قيمة مستوى الكمة المحددة بواسطة المستخدم

            // إنشاء ByteArrayOutputStream للحصول على تسلسل ثنائي للصورة
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String output = "C://testImages//KMeans.png";

            kMeans.kMeans(imagePath, quantizationLevel, output); // تنفيذ التابع uniform()

            try {
                // تحويل الصورة المجزأة إلى تسلسل ثنائي
                ImageIO.write(ImageIO.read(new File(output)), "png", outputStream);

                // تحويل التسلسل الثنائي إلى Base64
                String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());

                // عرض الصورة الناتجة في الواجهة (resultImageView)
                Image resultImage = new Image("data:image/png;base64," + encodedImage);
                kmeansResult.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //////QuadTree Algorithm
        quadTreeMenuItem.setOnAction(event -> {
            String imagePath = selectedImagePath; // احصل على مسار الصورة المحددة من زر chooseImage
            int quantizationLevel = 16; // قيمة مستوى الكمة المحددة بواسطة المستخدم

            // إنشاء ByteArrayOutputStream للحصول على تسلسل ثنائي للصورة
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String output = "C://testImages//quadtree.png";

            quadtree.quadtree(imagePath, quantizationLevel, output); // تنفيذ التابع uniform()

            try {
                // تحويل الصورة المجزأة إلى تسلسل ثنائي
                ImageIO.write(ImageIO.read(new File(output)), "png", outputStream);

                // تحويل التسلسل الثنائي إلى Base64
                String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());

                // عرض الصورة الناتجة في الواجهة (resultImageView)
                Image resultImage = new Image("data:image/png;base64," + encodedImage);
                quadTreeResult.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //////Indexed Algorithm
       /* quadTreeMenuItem.setOnAction(event -> {
            String imagePath = selectedImagePath; // احصل على مسار الصورة المحددة من زر chooseImage
            int quantizationLevel = 256; // قيمة مستوى الكمة المحددة بواسطة المستخدم

            // إنشاء ByteArrayOutputStream للحصول على تسلسل ثنائي للصورة
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String output = "C://testImages//indexed.png";

            indexedImage.indexedImage(imagePath, quantizationLevel, output); // تنفيذ التابع uniform()

            try {
                // تحويل الصورة المجزأة إلى تسلسل ثنائي
                ImageIO.write(ImageIO.read(new File(output)), "png", outputStream);

                // تحويل التسلسل الثنائي إلى Base64
                String encodedImage = Base64.getEncoder().encodeToString(outputStream.toByteArray());

                // عرض الصورة الناتجة في الواجهة (resultImageView)
                Image resultImage = new Image("data:image/png;base64," + encodedImage);
                quadTreeResult.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
    }

    //Choose Image
    public void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        Stage stage = (Stage) chooseImage.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedImagePath = file.getPath();
           BufferedImage image = loadImage(selectedImagePath);
            // يمكنك تنفيذ المنطق اللازم على الصورة المحملة هنا
        }
    }

}
 /*  public void handleUniformAction() {
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            // استدعاء التابع uniform() وتمرير selectedImagePath كمعامل
            int quantizationLevel = 16;
            // بعد الانتهاء من التعديلات على الصورة، قم بالحصول على مسار الصورة الناتجة بصيغة String
           // String outputPath = "C://folder//uniform.png";
            // بعد الانتهاء من التعديلات على الصورة، قم بتحديث عنصر الـ ImageView
            Image resultImage = new Image("file:C://folder//uniform.png");
            // بعد الانتهاء من تعديل الصورة، قم بتحديث صورة الـ ImageView
            uniformResult.setImage(resultImage);
            uniform.uniform(selectedImagePath, quantizationLevel, String.valueOf(resultImage));
        }
    }

    //////K-Means Algorithm//////
    public void handleKMeansAction() {
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            // استدعاء التابع uniform() وتمرير selectedImagePath كمعامل
            int quantizationLevel = 16;
            // بعد الانتهاء من التعديلات على الصورة، قم بالحصول على مسار الصورة الناتجة بصيغة String
            // String outputPath = "C://folder//uniform.png";
            // بعد الانتهاء من التعديلات على الصورة، قم بتحديث عنصر الـ ImageView
            Image resultImage = new Image("file:C://folder//kmeans.png");
            // بعد الانتهاء من تعديل الصورة، قم بتحديث صورة الـ ImageView
            kmeansResult.setImage(resultImage);
            kMeans.kMeans(selectedImagePath, quantizationLevel, String.valueOf(resultImage));
        }
    }

    //////QuadTree Algorithm///////
    public void handleQuadtreeAction() {
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            // استدعاء التابع uniform() وتمرير selectedImagePath كمعامل
            int quantizationLevel = 16;
            // بعد الانتهاء من التعديلات على الصورة، قم بالحصول على مسار الصورة الناتجة بصيغة String
            // String outputPath = "C://folder//uniform.png";
            // بعد الانتهاء من التعديلات على الصورة، قم بتحديث عنصر الـ ImageView
            Image resultImage = new Image("file:C://folder//quadTree.png");
            // بعد الانتهاء من تعديل الصورة، قم بتحديث صورة الـ ImageView
            quadTreeResult.setImage(resultImage);
            quadtree.quadtree(selectedImagePath, quantizationLevel, String.valueOf(resultImage));
        }
    }*/

    /*public void uniform2Indexed() {
        String result = "C://folder//uniform.png";
       // Image output = new Image("file:C://folder//indexed.png");
      //  if (result != null && !result.isEmpty()) {
            // استدعاء التابع uniform() وتمرير selectedImagePath كمعامل
            int quantizationLevel = 8;
            // بعد الانتهاء من التعديلات على الصورة، قم بالحصول على مسار الصورة الناتجة بصيغة String
            // String outputPath = "C://folder//uniform.png";
            // بعد الانتهاء من التعديلات على الصورة، قم بتحديث عنصر الـ ImageView
            Image resultImage = new Image("file:C://folder//indexed.png");
            // بعد الانتهاء من تعديل الصورة، قم بتحديث صورة الـ ImageView

            indexedImage.indexedImage(result, quantizationLevel, String.valueOf(resultImage));
        indexedResult.setImage(resultImage);
       // }
            } */