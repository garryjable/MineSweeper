package cs2410.model;



import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Collections;


/**
 * This is the game board there is a 25 * 25 grid of buttons that pretty much manages all minesweeper gameplay
 * @version 1.0
 * @author  Garrett Jennings A01491831
 */
public class GameGrid {
    /**
     * this is the 2 dimensional array that contains gameBtns all game buttons are either mines or not mines
     */
    public GameBtn[][] grid;

    /**
     * this array list is mostly used in the construction of the game board. It simplifies loading in the desired amount
     * of mines and not mines and shuffling before passing them to the grid.
     */
    ArrayList<GameBtn> inventory;

    /**
     * the grid width since i didn't add a resizing feature it should always be twenty five but it eliminates
     * magic numbers in my code and makes it more readable.
     */
    int gridWidth;
    /**
     * the grid height since i didn't add a resizing feature it should always be twenty five but it eliminates
     * magic numbers in my code and makes it more readable.
     */
    int gridHeight;

    /**
     * the total number of cells calculated by the product of width and height
     */
    int totalCells;

    /**
     * the percent of the grid cells that are bombs can be changed with the the difficulty menu.
     */
    double percentOfBombs;

    /**
     * the total number of bombs on the map. used for checking win conditions
     */
    int numberOfBombs;


    /**
     * used to give user a quick diagnostic of how well they played after they lost
     */
    int numMinesCorrectlyMarked = 0;

    /**
     * the time that has elapsed. used in score reporting
     */
    int timeElapsed = 0;

    /**
     * file imput output class for recording scores
     */
    public ScoreBoard scoreBoard = new ScoreBoard();


    /**
     * a boolean for win conditions
     */
    boolean win = false;


    /**
     * flag image used for marking cells
     */
    Image flag = new Image("http://minesweeperonline.com/flag.png",  10, 10,false,false);
    /**
     * same flag just bigger used for win message
     */
    Image flagBig = new Image("http://minesweeperonline.com/flag.png",  30, 30,false,false);
    /**
     * bomb image shown in loss or win
     */
    Image bomb = new Image("https://freemobileapk.com/wp-content/uploads/2014/09/biz_toway_games_minesweeper.png",15,15,false,false);
    /**
     * bigger bomb for loss message
     */
    Image bombBig = new Image("https://freemobileapk.com/wp-content/uploads/2014/09/biz_toway_games_minesweeper.png",30,30,false,false);
    /**
     * question mark image for marking cells
     */
    Image question = new Image("https://cdn4.iconfinder.com/data/icons/defaulticon/icons/png/256x256/help.png",  10, 10,false,false);


    /**
     * function to set the boolean to win
     * @param win
     */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     * get for win condition
     * @return
     */
    public boolean isWin() {
        return win;
    }

    /**
     * increments the number of correectly marked mines used in score reporting
     */
    public void incrementNumMinesCorrectlyMarked(){
        numMinesCorrectlyMarked++;
    }

    /**
     * gets the number of correctly marked mines used in score reporting
     * @return
     */
    public int getNumMinesCorrectlyMarked(){
        return numMinesCorrectlyMarked;
    }

    /**
     * incrememnts the time used in score reporting
     */
    public void incrementTime(){
        timeElapsed++;
    }

    /**
     * gets the time elapsed used for score reporting
     * @return
     */
    public int getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * gets the big bomb image used by loss message
     * @return
     */
    public Image getBombBig() {
        return bombBig;
    }

    /**
     * gets the big flag image used by win message
     * @return
     */
    public Image getFlagBig() {
        return flagBig;
    }

    /**
     * gets the flage image for marking cells
     * @return
     */
    public Image getFlag() {
        return flag;
    }

    /**
     * gets the bomb image when game is over
     * @return
     */
    public Image getBomb() {
        return bomb;
    }

    /**
     * gets a question image for marking cells
     * @return
     */
    public Image getQuestion() {
        return question;
    }

    /**
     * Gets the grid height I was trying to implement the ability to change the size of the board but
     * i couldnt get it to work out this is here so I can add it later
     * @return
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * gets the grid width I was trying to implement the ability to change the size of the board but
     * i couldnt get it to work out this is here so I can add it later
     * @return
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * gets the number of bombs total
     * @return
     */
    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    /**
     * gets the total number of cells
     * @return
     */
    public int getTotalCells() { return totalCells; }

    /**
     * gets the percent of bombs used in difficulty calculation
     * @return
     */
    public double getPercentOfBombs() { return percentOfBombs; }

    /**
     * I have this function here if I decide to implement game board size adjusting in the future
     * @param gridHeight
     */
    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }
    /**
     * I have this function here if I decide to implement game board size adjusting in the future
     * @param gridWidth
     */
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }
    /**
     * I have this function here if I decide to implement game board size adjusting in the future
     * @param numberOfBombs
     */
    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }

    /**
     * constructor for creating the gameBoard sets all members to default values and easy difficulty and then calls
     * create grid
     */
    public GameGrid() {

        gridWidth = 25;
        gridHeight = 25;
        totalCells = 25 * 25; //625
        percentOfBombs = .1; //10%
        numberOfBombs = (int) (totalCells * percentOfBombs); // 62
        createGrid();
    }

    /**
     * function for creating the gameBoard first we create an array list and add 500 notMines and 125 Mines
     * the list is shuffled and then read into the 2 by 2 array map of the game board then each mine counts the
     * number of neighbors that are mines and each button stores its own coordinates in the grid
     */
    public void createGrid() {
        grid = null;
        grid = new GameBtn[gridWidth][gridHeight];
        inventory = null;
        inventory = new ArrayList<>();
        for (int i = 0; i < (totalCells - numberOfBombs); i++) {
            NotMine notMine = new NotMine();
            inventory.add(notMine);
        }
        for (int i = 0; i < numberOfBombs; i++) {
            Mine mine = new Mine();
            inventory.add(mine);
        }
        Collections.shuffle(inventory);
        int k = 0;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j] = inventory.get(k);
                k++;
            }
        }
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j] instanceof NotMine) {
                    NotMine notMine = (NotMine) grid[i][j];
                    notMine.setMineCount(countMines(i, j));
                }
                grid[i][j].setiCoordinate(i);
                grid[i][j].setjCoordinate(j);
                grid[i][j].setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;-fx-background-insets: 0, 1, 1, 2;");
               // grid[i][j].setStyle("");
             //   grid[i][j].setStyle();
            }
        }

    }

    /**
     * This countMines function takes the indexes of a button and then checks if it is next to the edge and then
     * it goes on the count the number of mines around the button and then returns the number of mines
     *
     * @param i
     * @param j
     * @return
     */
    public int countMines(int i, int j) {
        int numMines = 0;
        boolean iPlus = false;
        boolean jPlus = false;
        boolean iMinus = false;
        boolean jMinus = false;
        if ((i + 1) < gridWidth) {
            iPlus = true;
        }
        if ((j + 1) < gridHeight) {
            jPlus = true;
        }
        if ((i - 1) >= 0) {
            iMinus = true;
        }
        if ((j - 1) >= 0) {
            jMinus = true;
        }
        if (iPlus) {
            if (grid[i + 1][j] instanceof Mine) {
                numMines++;
            }
        }
        if (iPlus && jPlus) {
            if (grid[i + 1][j + 1] instanceof Mine) {
                numMines++;
            }
        }
        if (jPlus) {
            if (grid[i][j + 1] instanceof Mine) {
                numMines++;
            }
        }
        if (iMinus && jPlus) {
            if (grid[i - 1][j + 1] instanceof Mine) {
                numMines++;
            }
        }
        if (iMinus) {
            if (grid[i - 1][j] instanceof Mine) {
                numMines++;
            }
        }
        if (iMinus && jMinus) {
            if (grid[i - 1][j - 1] instanceof Mine) {
                numMines++;
            }
        }
        if (jMinus) {
            if (grid[i][j - 1] instanceof Mine) {
                numMines++;
            }
        }
        if (iPlus && jMinus) {
            if (grid[i + 1][j - 1] instanceof Mine) {
                numMines++;
            }
        }
        return numMines;
    }

    /**
     * getter function that given a set of coordinates will return the button
     *
     * @param iCoordinate
     * @param jCoordinate
     * @return
     */
    public GameBtn getGameBtn(int iCoordinate, int jCoordinate) {
        return grid[iCoordinate][jCoordinate];
    }


    /**
     * function for changing the amount of bombs in the game. Recalculates the needed members based on new difficulty
     * @param percentOfBombs
     */
    public void changeDifficulty(double percentOfBombs) {
        this.percentOfBombs = percentOfBombs;
        numberOfBombs = (int)(totalCells*this.percentOfBombs);
    }
//    public void changeSize(int gridHeight, int gridWidth){
//        setGridHeight(gridHeight);
//        setGridWidth(gridWidth);
//        totalCells = this.gridHeight*this.gridWidth;
//        numberOfBombs = (int)(totalCells*percentOfBombs);
//    }
}
