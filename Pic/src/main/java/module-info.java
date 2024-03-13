module com.example.pic {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pic to javafx.fxml;
    exports com.example.pic;
}