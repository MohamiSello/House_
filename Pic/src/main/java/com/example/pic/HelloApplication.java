package com.example.pic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    // Import the images
    private static final String[] IMAGE_PATHS = {
            "pic1.jpg", "pic2.jpg", "pic3.jpg", "pic4.jpg", "pic5.jpg",
            "pic6.jpg", "pic7.jpg", "pic8.jpg", "pic9.jpg", "pic10.jpg"
    };



    // Image sizes
    private static final double IMAGE_HEIGHT = 190;
    private static final double IMAGE_WIDTH = 170;

    private int currentIndex = 0;

    @Override
    public void start(Stage stage) {


        Label house = new Label("Luxurious Homes Display");
        house.setId("house");
        // Containers
        HBox root = new HBox();
        VBox display1 = new VBox();
        VBox display2 = new VBox();
        HBox buttonBar = new HBox();
        buttonBar.setId("buttonBar");
        HBox cont1 = new HBox();
        HBox cont2 = new HBox();
        HBox cont3 = new HBox();
        VBox left = new VBox();
        VBox right = new VBox();

        //Controllers
        Button btnClose = new Button("Close");
        Button btnNext = new Button("Next");
        Button btnPrevious = new Button("Previous");
        btnPrevious.setId("btnPrevious");
        btnNext.setId("btnNext");
        btnClose.setId("btnClose");
        btnClose.setOnAction(event -> Platform.exit()); // Close application on click



        //ImageViews to make grid display
        List<ImageView> imageViews1 = createImageViews(3, 0);
        List<ImageView> imageViews2 = createImageViews(3, 4);
        List<ImageView> imageViews3 = createImageViews(3, 7);
        ImageView mainDisplay = new ImageView();
        mainDisplay.setFitWidth(600);
        mainDisplay.setPreserveRatio(true);
        mainDisplay.setId("mainDisplay");

        // Adding imageViews to their containers
        cont1.getChildren().addAll(imageViews1);
        cont2.getChildren().addAll(imageViews2);
        cont3.getChildren().addAll(imageViews3);
        cont1.setId("cont");
        cont2.setId("cont");
        cont3.setId("cont");

        // Adding children
        buttonBar.getChildren().addAll(btnNext, btnClose, btnPrevious);
        display1.getChildren().addAll(cont1, cont2);
        display2.getChildren().addAll(cont3);
        right.getChildren().addAll(buttonBar, mainDisplay);
        left.getChildren().addAll(house, display1, display2);
        root.getChildren().addAll( left, right);


        // Click event handlers to update the main display and set currentIndex
        for (ImageView imageView : imageViews1) {
            imageView.setOnMouseClicked(event -> {
                updateMainDisplay(mainDisplay, imageView);
                currentIndex = getImageIndex(imageView); // Set currentIndex based on clicked image
            });
        }
        for (ImageView imageView : imageViews2) {
            imageView.setOnMouseClicked(event -> {
                updateMainDisplay(mainDisplay, imageView);
                currentIndex = getImageIndex(imageView); // Set currentIndex based on clicked image
            });
        }
        for (ImageView imageView : imageViews3) {
            imageView.setOnMouseClicked(event -> {
                updateMainDisplay(mainDisplay, imageView);
                currentIndex = getImageIndex(imageView); // Set currentIndex based on clicked image
            });
        }
        btnPrevious.setOnAction(event -> handlePrevious(mainDisplay));
        btnNext.setOnAction(event -> handleNext(mainDisplay));



        // Scene
        Scene scene = new Scene(root, 1000, 640);
        scene.getStylesheets().add(getClass().getResource("zxc.css").toExternalForm());
        stage.setTitle("Pictures");
        stage.setScene(scene);
        stage.show();
    }
    // Creates a list of ImageViews based on a specified count and starting index

    private List<ImageView> createImageViews(int count, int startIndex) {

        // Creates an empty list to store the ImageViews
        List<ImageView> imageViews = new ArrayList<>();

        // Defines the number of images displayed per row (columns)
        int columns = 3;

        // Iterates through the specified number of images (count)
        for (int i = 0; i < count; i++) {

            // Calculates the row index based on the current image position (i) and columns
            int rowIndex = i / columns;

            // Calculates the column index based on the current image position (i) and columns (modulo operator for remainder)
            int colIndex = i % columns;

            // Creates an Image object from the image path at the calculated index (startIndex + i)
            // relative to the IMAGE_PATHS array
            Image image = new Image(getClass().getResource(IMAGE_PATHS[startIndex + i]).toExternalForm());

            // Creates an ImageView object and sets the image to the newly created Image object
            ImageView imageView = new ImageView(image);

            // Sets the preferred height of the ImageView
            imageView.setFitHeight(IMAGE_HEIGHT);

            // Sets the preferred width of the ImageView
            imageView.setFitWidth(IMAGE_WIDTH);

            // Maintains the aspect ratio of the image when resizing
            imageView.setPreserveRatio(true);

            // Adds the created ImageView to the imageViews list
            imageViews.add(imageView);
        }

        // Returns the list containing the created ImageViews
        return imageViews;
    }

    // Main display
    private void updateMainDisplay(ImageView mainDisplay, ImageView clickedImage) {
        mainDisplay.setImage(clickedImage.getImage());
    }
    // Previous button
    private void handlePrevious(ImageView mainDisplay) {
        int totalImages = IMAGE_PATHS.length;

        // Find the index of the currently displayed image (avoiding unnecessary loop)
        int currentImageIndex = currentIndex;

        // Handle going to the "left" image (wrapping around)
        if (currentImageIndex > 0) {
            currentIndex = currentImageIndex - 1;
        } else {
            currentIndex = totalImages - 1;
        }

        Image image = new Image(getClass().getResource(IMAGE_PATHS[currentIndex]).toExternalForm());
        mainDisplay.setImage(image);
    }

    // Next button (fixed to display the correct next image)
    private void handleNext(ImageView mainDisplay) {
        int totalImages = IMAGE_PATHS.length;

        // Directly use currentIndex (already holds the last displayed image index)
        currentIndex = (currentIndex + 1) % totalImages;

        Image image = new Image(getClass().getResource(IMAGE_PATHS[currentIndex]).toExternalForm());
        mainDisplay.setImage(image);
    }
    // Helper method to get image index from ImageView
    private int getImageIndex(ImageView imageView) {
        String imagePath = imageView.getImage().getUrl();
        for (int i = 0; i < IMAGE_PATHS.length; i++) {
            if (IMAGE_PATHS[i].equals(imagePath.substring(imagePath.lastIndexOf("/") + 1))) {
                return i;
            }
        }
        return -1; // Not found (shouldn't happen)
    }



    public static void main(String[] args) {
        launch();
    }
}
