package com.example.chesstest;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;
import java.util.stream.Collectors;

public class Piece extends ImageView {

    private String name;
    private boolean isEnemy;
    private int[][] movementConstrain;
    private boolean isPawn;
    private double pieceSize;
    private GridPane grid;
    private boolean isLong;
    private String[] longPiece = {"r", "b", "q"};
    private int  isInverted = 1;
    private List<int[]> moveToSkip;

    public boolean isAlive = true;
    public int[] position;

    public Piece(GridPane grid,String name, boolean isEnemy, int[][] movementConstrain, boolean isPawn, int[] position, double pieceSize)
    {
        super(new Image("C:\\Users\\Casa\\Desktop\\ChessJava\\ChessTest\\src\\main\\resources\\pieces\\" + ((isEnemy) ? "b" : "w") + name + ".png")); //
        this.setCursor(Cursor.HAND);
        this.name = name;
        this.isEnemy = isEnemy;
        this.movementConstrain = movementConstrain;
        this.isPawn = isPawn;
        this.position = position;
        this.pieceSize = pieceSize;
        this.grid = grid;
        this.moveToSkip = new ArrayList<>();


        if (Arrays.asList(longPiece).contains(name)){
            isLong = true;
        }else {isLong = false;}


        if(name.equals("p") && isEnemy){
            for (int i = 0; i < movementConstrain[0].length; i++) {
                this.movementConstrain[i][0] = this.movementConstrain[i][0] * -1;
                this.movementConstrain[i][1] = this.movementConstrain[i][1] * -1;
            }
            this.isInverted = -1;
        }



        this.setFitHeight(pieceSize);
        this.setFitWidth(pieceSize);

        setOnMouseClicked(event -> {
            onMouse();
        });

    }

    private void onMouse(){

        List<Hint> hints = new ArrayList<>();
        List<Eat> eats = new ArrayList<>();
        List<Hint> hints_rm = new ArrayList<>();
        List<Eat> eats_rm = new ArrayList<>();

        ObservableList<Node> nodesArray_add =  grid.getChildren();
        Iterator<Node> iterator_add = nodesArray_add.iterator();
        while(iterator_add.hasNext()){

            Node node_add = iterator_add.next();

            int col = GridPane.getColumnIndex(node_add);
            int row = GridPane.getRowIndex(node_add);

            if (!isLong) {
                boolean isVal = isValidCell(row, col);


                if (isVal && (node_add instanceof Rectangle)) {
                    Hint hint = new Hint(20, row, col, this, grid);
                    hints.add(hint);
                }
            }
        }

        // Long pieces movement handler
        boolean passMov = false;
        List<Node> children = grid.getChildren();
        if (isLong){

            int thisCol = GridPane.getColumnIndex(this);
            int thisRow = GridPane.getRowIndex(this);

            for (int[] allow : this.movementConstrain) {
                passMov = false;
                for (int i = 0; i <= 8; i++) {
                    int targetRow = thisRow + (i * allow[1]);
                    int targetCol = thisCol + (i * allow[0]);

                    if (targetRow < 8 && targetRow >= 0 && targetCol < 8 && targetCol >= 0){
                        if (thisCol != targetCol || thisRow != targetRow) {

                            List<Node> mateElementsAtPosition = children.stream()
                                    .filter(node -> GridPane.getColumnIndex(node) == targetCol && GridPane.getRowIndex(node) == targetRow && node instanceof Piece && ((((Piece) node).isEnemy) == isEnemy))
                                    .toList();
                            List<Node> enemyElementsAtPosition = children.stream()
                                    .filter(node -> GridPane.getColumnIndex(node) == targetCol && GridPane.getRowIndex(node) == targetRow && node instanceof Piece &&
                                            ((((Piece) node).isEnemy) != isEnemy))
                                    .toList();

                            int[] thisIndex = {targetRow, targetCol};

                            if ((long) enemyElementsAtPosition.size() > 0)
                            {
                                if (!passMov) {
                                    Eat eat = new Eat(10, targetRow, targetCol, this, grid);
                                    eats.add(eat);
                                    passMov = true;
                                    Piece a = (Piece)enemyElementsAtPosition.get(0);
                                    a.EatColor();
                                    break;
                                }else {}
                            }
                            if ((long) mateElementsAtPosition.size() == 0)
                            {
                                Hint hint = new Hint(20, targetRow, targetCol, this, grid);
                                hints.add(hint);
                            }

                            if ((long) mateElementsAtPosition.size() > 0 || enemyElementsAtPosition.size() > 0){break; }
                        }
                    }

                }
            }
        }


        for (Node n : grid.getChildren())
        {
            if (n instanceof Hint)
            {
                hints_rm.add((Hint) n);
            }
            if (n instanceof Eat)
            {
                eats_rm.add((Eat) n);
            }
        }




        for (Hint a : hints_rm)
        {
            grid.getChildren().remove(a);
        }
        for (Eat a : eats_rm)
        {
            grid.getChildren().remove(a);
        }


        for (Hint hin : hints){
            int col = hin.col;
            int row = hin.row;
            grid.add(hin, col, row);
        }
        for (Eat e : eats){
            int col = e.col;
            int row = e.row;
            grid.add(e, col, row);
        }

        eats = new ArrayList<>();
        eats_rm = new ArrayList<>();
        hints_rm = new ArrayList<>();
        hints = new ArrayList<>();
        this.moveToSkip = new ArrayList<>();
    }

    private boolean isValidCell(int row, int col)
    {
        int selfCol = GridPane.getColumnIndex(this);
        int selfRow = GridPane.getRowIndex(this);

        if (selfRow == row && selfCol == col){
            return false;
        }



        if (!isLong) {
            for (int[] allow : this.movementConstrain) {
                if (false) {
                    for (int i = 0; i < 8; i++) {
                        if ((row == (selfRow + (i * allow[1]))) && (col == (selfCol + (i * allow[0])))) {
                            if (this.moveToSkip != null) {
                                if (this.moveToSkip.contains(allow)) {
                                    return false;
                                }
                            }
                            if (isPiece(row, col)) {
                                this.moveToSkip.add(allow);
                                return false;
                            }
                            return true;
                        }
                        if ((row == (selfRow - (i * allow[1]))) && (col == (selfCol - (i * allow[0])))) {
                            if (this.moveToSkip != null) {
                                if (this.moveToSkip.contains(allow)) {
                                    return false;
                                }
                            }
                            if (isPiece(row, col)) {
                                this.moveToSkip.add(allow);
                                return false;
                            }
                            return true;
                        }
                    }
                } else {
                    if ((row == (selfRow + (allow[1] * isInverted))) && (col == (selfCol + (allow[0] * isInverted)))) {

                        if (CanEat(row, col)) {

                        }

                        return !isPiece(selfRow + (allow[1] * isInverted), selfCol + (allow[0] * isInverted));
                    }
                }
            }
        }


        return false;
    }




    private boolean CanEat(int row, int col)
    {
        //  Error adding rectangle doesn't work because the main loop is inside getChildren

        int selfCol = GridPane.getColumnIndex(this);
        int selfRow = GridPane.getRowIndex(this);

        if (isPawn)
        {
            int[][] pawnEat = {{1, isInverted}, {-1,isInverted}};

            for (int[] pEat : pawnEat){
                int targCol = selfCol + pEat[0];
                int targRow = selfRow + pEat[1];

                if (isPiece(targRow, targCol))
                {
                    System.out.println("Is a Piece");
                    return true;
                }
            }
        }



        return false;
    }


    public void MovePieceTo(int row, int col) {

        GridPane.setConstraints(this, col, row);

        if (name.equals("p"))
        {
            int[][] movOne = {movementConstrain[0]};
            movementConstrain = movOne;
        }
        removeHint();
    }


    private void removeHint()
    {
        ObservableList<Node> nodesArray_add = grid.getChildren();
        Iterator<Node> iterator_add = nodesArray_add.iterator();
        List<Hint> hints = new ArrayList<>();

        while (iterator_add.hasNext()) {

            Node node_add = iterator_add.next();
            if (node_add instanceof Hint) {
               hints.add((Hint) node_add);
            }
        }

        for (Hint a : hints)
        {
            grid.getChildren().remove(a);
        }
    }

    private boolean isPiece(int row, int col){
        for (Node nodeP : grid.getChildren()) {
            if ((nodeP instanceof Piece) ) {
                int pieceCol = GridPane.getColumnIndex(nodeP);
                int pieceRow = GridPane.getRowIndex(nodeP);

                if (row == pieceRow && col == pieceCol){
                    return true;
                }
            }
        }
        return false;
    }

    public void EatColor()
    {
        Blend gray = new Blend(BlendMode.SRC_ATOP, null, new ColorInput(0, 0, 100.0,100.0,
                Color.RED));

        this.setEffect(gray);
    }
}
