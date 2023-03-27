package com.example.chesstest;

import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Hint extends Circle {

    public int row;
    public int col;
    private GridPane grid;
    public Piece linkedPiece;
    public Hint(int size, int row, int col, Piece piece, GridPane grid){
        super((double)size, Color.YELLOW);

        this.setOpacity(1.0);
        this.row = row;
        this.col = col;
        this.linkedPiece = piece;
        this.grid = grid;

        this.setCursor(Cursor.HAND);

        setOnMouseClicked(event -> {
            onMouse();
        });
    }

    private void onMouse()
    {
        int col = GridPane.getColumnIndex(this);
        int row = GridPane.getRowIndex(this);

        this.linkedPiece.MovePieceTo(row, col);
    }
}
