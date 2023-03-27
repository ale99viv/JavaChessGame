module com.example.chesstest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chesstest to javafx.fxml;
    exports com.example.chesstest;
}