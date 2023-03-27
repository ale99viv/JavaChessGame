package com.example.chesstest;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.example.chesstest.ChessElements.*;

public class Creators {

    public static void CreateGrid (GridPane grid, int row, int col, int size){
        boolean colSwitch = true;
        Color color;

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {

                Rectangle rec = new Rectangle(100.0, 100.0);
                color = ((i+j)%2 == 0) ? Color.WHITE : Color.DARKGREEN;
                rec.setFill(color);

                grid.add(rec, i,j);


            }
        }
        for (int i = 0; i < col; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / col);
            colConst.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < row; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / row);
            rowConst.setValignment(VPos.CENTER);
            grid.getRowConstraints().add(rowConst);
        }

    }

    public static void CreateElements(GridPane grid)
    {
        String[][] elements = ChessElements.getChessElements();
        int count = 0;
        int[] both = new int[]{0,7};
        boolean isEnemy = false;

        for(int a : both) {
            for (int i = 0; i < elements.length; i++) {
                for (int j = 0; j < elements[0].length; j++) {
                    String elem = elements[i][j];
                    boolean isPawn = elem.equals("p");
                    int[][] mov = ChessElements.getPieceMovement(elem);
                    Piece actor = new Piece(grid, elem, isEnemy, mov, isPawn, new int[]{0, count}, 100.0);

                    if (!isEnemy) {
                        int row = j;
                        int col = i;
                        grid.add(actor, row, col);
                    }
                    else {
                        int row  = j;
                        int col = a - i;
                        grid.add(actor, row, col);
                    }
                }
            }
            isEnemy = true;
        }

    }
}
