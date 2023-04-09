package com.example.chesstest;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;



public class Main extends Application {

    static int WIDTH = 800;
    static int HEIGHT = 800;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Diooooooovvvvaaaacccaaaaa
        Group root = new Group();

        GridPane grid = new GridPane();

        root.getChildren().add(grid);

        Creators.CreateGrid(grid, 8,8, (int)WIDTH/8);
        //grid.setPrefSize(800,800);
        grid.setHgap(0.0);
        grid.setVgap(0.0);

        root.autosize();
        grid.autosize();
        grid.setAlignment(Pos.CENTER);


        Creators.CreateElements(grid);

        Scene scene = new Scene(root, 1200,800,Color.YELLOW);
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Chess");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        System.out.println(grid.getChildren().stream().count());

    }


}