package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class MazeDisplay extends Canvas {

    private Maze maze;
    private Solution solution;
    private int playerRow = 0;
    private int playerCol = 0;
    Image currentImage = new Image(getImageResourceAsStream("images/RedDragonDown.png"));
    public static int DragonColor = 1;
    private double cellHeight,cellWidth;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();



    public static void setDragonColor(int dragonColor) { MazeDisplay.DragonColor = dragonColor; }

    @Override
    public boolean isResizable(){
        return true;
    }

    @Override
    public double minHeight(double width)
    {
        return 350;
    }

    @Override
    public double maxHeight(double width)
    {
        return 1000;
    }

    @Override
    public double prefHeight(double width)
    {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height)
    {
        return 0;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }

    @Override
    public void resize(double width, double height)
    {
        super.setWidth(width);
        super.setHeight(height);
        draw();
    }

    private InputStream getImageResourceAsStream(String imagePath){
        return getClass().getClassLoader().getResourceAsStream(imagePath);
    }

    public void setPlayerPosition(int row, int col) {
        if (DragonColor == 1){
            if (row > playerRow)
                currentImage = new Image(getImageResourceAsStream("images/RedDragonDown.png"));
            else if (row < playerRow)
                currentImage = new Image(getImageResourceAsStream("images/RedDragonUp.png"));
            else if (col >playerCol)
                currentImage = new Image(getImageResourceAsStream("images/RedDragonRight.png"));
            else
                currentImage = new Image(getImageResourceAsStream("images/RedDragonLeft.png"));
        }
        else if (DragonColor == 2){
            if (row > playerRow)
                currentImage = new Image(getImageResourceAsStream("images/BlueDragonDown.png"));
            else if (row < playerRow)
                currentImage = new Image(getImageResourceAsStream("images/BlueDragonUp.png"));
            else if (col >playerCol)
                currentImage = new Image(getImageResourceAsStream("images/BlueDragonRight.png"));
            else
                currentImage = new Image(getImageResourceAsStream("images/BlueDragonLeft.png"));
        }
        else{
            if (row > playerRow)
                currentImage = new Image(getImageResourceAsStream("images/YellowDragonDown.png"));
            else if (row < playerRow)
                currentImage = new Image(getImageResourceAsStream("images/YellowDragonUp.png"));
            else if (col >playerCol)
                currentImage = new Image(getImageResourceAsStream("images/YellowDragonRight.png"));
            else
                currentImage = new Image(getImageResourceAsStream("images/YellowDragonLeft.png"));
        }


        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public void setSolution(Solution solution){
        this.solution = solution;
        draw();
    }

    public int getPlayerCol(){
        return playerCol;
    }

    public int getPlayerRow(){
        return playerRow;
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }



    public void drawMaze(Maze maze) {
        this.maze = maze;
        draw();
    }



    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        Image solutionImage = new Image(getImageResourceAsStream("images/FireSolve.png"));

        Position sol;
        for (AState state : solution.getSolutionPath()){
            sol = (Position)(state).getObject();
            graphicsContext.drawImage(solutionImage, sol.getColumnIndex()*cellWidth, sol.getRowIndex()*cellHeight, cellWidth, cellHeight);

        }

    }

    public double getCellHeight() {
        return cellHeight;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    private void draw() {
        if(maze != null){
            int[][] mazeBody = maze.getMatrix();
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = mazeBody.length;
            int cols = mazeBody[0].length;

            cellHeight = canvasHeight / rows;
            cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if(solution != null)
                drawSolution(graphicsContext, cellHeight, cellWidth);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;

        if(currentImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else{
            graphicsContext.drawImage(currentImage, x, y, cellWidth, cellHeight);
            }
        }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        int[][] mazeBody = maze.getMatrix();

        graphicsContext.setFill(Color.RED);
        javafx.scene.image.Image wallImage = null;

            wallImage = new Image(getImageResourceAsStream(getImageFileNameWall()));
        if (wallImage == null) {
            System.out.println("There is no wall image file");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(mazeBody[i][j] == 1){
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null) {
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
        Image startingPoint = new Image(getImageResourceAsStream("images/BluePort.png"));
        Image goalPoint = new Image(getImageResourceAsStream("images/DragonEgg.png"));
        double x,y;
        x = maze.getStartPosition().getColumnIndex()*cellWidth;
        y = maze.getStartPosition().getRowIndex()*cellHeight;
        graphicsContext.drawImage(startingPoint,x,y,cellWidth,cellHeight);
        x = maze.getGoalPosition().getColumnIndex()*cellWidth;
        y = maze.getGoalPosition().getRowIndex()*cellHeight;
        graphicsContext.drawImage(goalPoint,x,y,cellWidth,cellHeight);

    }

}
