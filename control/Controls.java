package cs2410.control;

import cs2410.model.GameBtn;
import cs2410.model.GameGrid;
import cs2410.model.Mine;
import cs2410.model.NotMine;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * this is a function that has some added controls and function of the game board its kind of like an intermediary
 * class that the controller controls the gameboard with.
 * @version 1.0
 * @author Garrett Jennings A01491831
 */
public class Controls {
    /**
     * instance of the gameGrid
     */
    private GameGrid gameGrid;


    /**
     * constructor that sets the game grid
     * @param gameGrid
     */
    Controls(GameGrid gameGrid){
        this.gameGrid = gameGrid;
    }


    /**
     * performs clicking routine if the button is a mine, the game is over. If it is not, it marks it as clicked should
     * change the text to the number of neighbors that are mine if no mines are neighbors then click method is
     * called on all neighbors. If a notMine is marked this is bypassed in the recursion
     * @param gameBtn
     * @param ignoreFlags
     */
    public boolean clickingMethod(GameBtn gameBtn, boolean ignoreFlags ) {
        boolean isOver = false;
        if(ignoreFlags){
            gameBtn.setGraphic(null);
        }
        if (!gameBtn.isMarked() || ignoreFlags){
            if (!gameBtn.isClicked()) {
                gameBtn.setClicked(true);
                if (gameBtn instanceof Mine) {
                    endGame(false);
                    isOver = true;
                } else {
                    //isOver = checkWin();
                    NotMine notMine = (NotMine) gameBtn;
                    notMine.setDisable(true);
                    if(notMine.getMineCount() == 0) {
                        gameBtn.setText(null);
                    }
                    if (notMine.getMineCount() > 0) {
                        gameBtn.setText(Integer.toString(notMine.getMineCount()));
                    }
                    else {
                        //CALL CLICKED ON ALL NEIGHBORS
                        int i = gameBtn.getiCoordinate();
                        int j = gameBtn.getjCoordinate();
                        boolean iPlus = false;
                        boolean jPlus = false;
                        boolean iMinus = false;
                        boolean jMinus = false;
                        if ((i + 1) < 25) {
                            iPlus = true;
                        }
                        if ((j + 1) < 25) {
                            jPlus = true;
                        }
                        if ((i - 1) >= 0) {
                            iMinus = true;
                        }
                        if ((j - 1) >= 0) {
                            jMinus = true;
                        }
                        if (iPlus) {
                            clickingMethod(gameGrid.getGameBtn(i + 1, j),true);
                        }
                        if (iPlus && jPlus) {
                            clickingMethod(gameGrid.getGameBtn(i + 1, j + 1), true);
                        }
                        if (jPlus) {
                            clickingMethod(gameGrid.getGameBtn(i, j + 1), true);
                        }
                        if (iMinus && jPlus) {
                            clickingMethod(gameGrid.getGameBtn(i - 1, j + 1), true);
                        }
                        if (iMinus) {
                            clickingMethod(gameGrid.getGameBtn(i - 1, j), true);
                        }
                        if (iMinus && jMinus) {
                            clickingMethod(gameGrid.getGameBtn(i - 1, j - 1), true);
                        }
                        if (jMinus) {
                            clickingMethod(gameGrid.getGameBtn(i, j - 1), true);
                        }
                        if (iPlus && jMinus) {
                            clickingMethod(gameGrid.getGameBtn(i + 1, j - 1), true);
                        }
                    }
                    isOver = checkWin();
                }
            }
    }
        return isOver;
    }

    /**how to make an image smaller in javafx
     * This is the method that is called when a button is right clicked it gets an mark of a flag or a question mark
     * if it is marked it will be unable to be clicked.
     * @param gameBtn
     * @param mineCount
     */
    public void markingMethod(GameBtn gameBtn, IntegerProperty mineCount){
        ImageView imageView = (ImageView)gameBtn.getGraphic();
        if(gameBtn.isMarked() == false ){
            gameBtn.bombMarked();
            gameBtn.setGraphic(new ImageView(gameGrid.getFlag()));
            mineCount.set(mineCount.getValue() -1);
        }
        else if ( imageView.getImage() == gameGrid.getFlag()){
            gameBtn.setGraphic(null);
            gameBtn.setGraphic(new ImageView(gameGrid.getQuestion()));
        }
        else if ( imageView.getImage() == gameGrid.getQuestion()){
            gameBtn.bombUnmarked();
            gameBtn.setGraphic(null);
            mineCount.set(mineCount.getValue()+1);
        }
    }

    /**
     * This is called after every click to check the win conditions if they didn't win nothing happens otherwise the
     * game is over.
     */
    private boolean checkWin(){
        int totalNotMines = (gameGrid.getTotalCells()-gameGrid.getNumberOfBombs());
        int count = 0;
        for (int i = 0; i < gameGrid.getGridWidth(); i++) {
            for (int j = 0; j < gameGrid.getGridHeight(); j++) {
                if(gameGrid.grid[i][j] instanceof NotMine && gameGrid.grid[i][j].isClicked() == true ){
                    count++;
                }
            }
        }
        if(count == totalNotMines){

            endGame(true);
            return true;
        }
        return false;
    }


    /**
     * function for ending the game, changes all bombs to red, marked bombs green, and marked non bombs yellow.
     * then it should display an alert box with score. Call the score storing function and then we reactivate the start button
     * to play again.
     * If the player wins it marks all the bombs green
     * @param win
     */
    public void endGame(boolean win){
      //  System.exit(0);
        gameGrid.setWin(win);
        for (int i = 0; i < gameGrid.getGridWidth(); i++) {
            for (int j = 0; j < gameGrid.getGridHeight(); j++) {
                ImageView imageView = (ImageView)gameGrid.grid[i][j].getGraphic();
                if ( gameGrid.grid[i][j] instanceof Mine) {
                    if ((gameGrid.grid[i][j].isMarked() == true && imageView.getImage() == gameGrid.getFlag()) ) {
                        gameGrid.grid[i][j].setStyle("-fx-background-color: #008000; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;-fx-background-insets: 0, 1, 1, 2;");
                        gameGrid.incrementNumMinesCorrectlyMarked();
                    } else {
                        if(win){
                            gameGrid.grid[i][j].setStyle("-fx-background-color: #008000; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;-fx-background-insets: 0, 1, 1, 2;");
                        }
                        else {
                            gameGrid.grid[i][j].setStyle("-fx-background-color: #ff0000; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;-fx-background-insets: 0, 1, 1, 2;");
                        }
                        gameGrid.grid[i][j].setGraphic(new ImageView(gameGrid.getBomb()));
                    }
                }
                if (gameGrid.grid[i][j] instanceof NotMine) {
                    if(gameGrid.grid[i][j].isMarked() == true) {
                        if (imageView.getImage() == gameGrid.getFlag()) {
                            gameGrid.grid[i][j].setStyle("-fx-background-color: #FFD700; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;-fx-background-insets: 0, 1, 1, 2;");
                        }
                    }
                }
            }
        }
    }

}
