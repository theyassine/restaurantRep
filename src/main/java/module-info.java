module tn.esprit.yummyfoodfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.swing;
    requires jxmapviewer2;
    requires org.controlsfx.controls;

    opens tn.esprit.yummyfoodfx to javafx.fxml;
    exports tn.esprit.yummyfoodfx;
    exports tn.esprit.yummyfoodfx.controller;
    opens tn.esprit.yummyfoodfx.controller to javafx.fxml;
}