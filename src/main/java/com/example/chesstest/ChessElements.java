package com.example.chesstest;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class ChessElements {
    public static String[][] chessBoardElements = new String[][]{
            {"r", "kn", "b", "q", "ki", "b", "kn", "r"},
            {"p", "p", "p", "p", "p", "p", "p", "p"}

    };

    static int[][][] chessElementMovement = {
            // Pawn
            {
                    {0, 1}, {0, 2}
            },
            // Rock
            {
                    {1, 0}, {-1, 0}, {0, 1}, {0, -1}
            },
            // Knight
            {
                    {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
            },
            // Bishop
            {
                    {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
            },
            // Queen
            {
                    {1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
            },
            // King
            {
                    {1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
            }
    };

    static String[][] getChessElements(){
        return chessBoardElements;
    }
    static int[][] getPieceMovement(String piece){

        switch (piece){

            case "p":
                return chessElementMovement[0];
            case "r":
                return chessElementMovement[1];
            case "kn":
                return chessElementMovement[2];
            case "b":
                return chessElementMovement[3];
            case "q":
                return chessElementMovement[4];
            case "ki":
                return chessElementMovement[5];
            default:
                return new int[][]{{155,155}};
        }

    }
}
