package cs2410.control;

import cs2410.model.GameBtn;
import cs2410.model.GameGrid;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


/**
 * this is the controller class. Alot goes on but ive tried to organize interactions with the game board to the controls
 * class and interactions with the gui to this class
 * @version 1.0
 * @author Garrett Jennings A01491831
 */
public class Controller implements Initializable {

    /**
     * this is the menu button to change the difficulty to easy or 10% bombs
     */
    @FXML
    MenuItem easyMenuItem;
    /**
     * this is the menu button to change the difficulty to medium or 25% bombs
     */
    @FXML
    MenuItem moderateMenuItem;
    /**
     * this is the menu button to change the difficulty to hard or 40% bombs
     */
    @FXML
    MenuItem hardMenuItem;

    /**
     * this is the menu button to change the mode to time trial
     */
    @FXML
    MenuItem timeTrialMenuItem;
//    @FXML
//    MenuItem smallMenuItem;
//    @FXML
//    MenuItem mediumMenuItem;
//    @FXML
//    MenuItem largeMenuItem;

    /**
     * this is the menu item to display all the scores
     */
    @FXML
    MenuItem allScoresMenuItem;
    /**
     * this is the menu item to clear the scores.txt file
     */
    @FXML
    MenuItem clearHistoryItem;


    /**
     * this is the start button to start the game
     */
    @FXML
    Button startBtn;
    /**
     * this is the label that shows the amount of seconds passed
     */
    @FXML
    Label timeElapsedLabel;
    /**
     * this is the grid pane that is 25*25 that contains all the game buttons
     */
    @FXML
    GridPane gridPane;

    /**
     * this is the label that shows the number of bombs that are hypothetically still unmarked
     */
    @FXML
    Label numBombsLeft;


    /**
     * this is the integer property that is bound to the text property of the numBombsLeft
     */
    IntegerProperty mineCountProperty;

    /**
     * instance of game grid
     */
    GameGrid gameGrid = new GameGrid();
    /**
     * this is an instance of the controls class the controller uses this to interact with the game board.
     */
    Controls controls;
    /**
     * this is a button that used is the current button and honestly I forgot what it is for.
     */
    GameBtn currentBtn;
    /**
     * this is the property to be bound with the text property of the time label
     */
    IntegerProperty timeElapsedProperty = new SimpleIntegerProperty(0);
    /**
     * this is the boolean used to track if a click is the first of the game than the timer starts
     */
    boolean firstClick = true;
    /**
     * this boolean is used to track if the game is over
     */
    boolean isOver = false;
    /**
     * this is a boolean to track if the game is in time trial mode
     */
    boolean timeTrialMode = false;
    /**
     * this is the timer used to start a thread and count seconds in the background while the
     * player is playing
     */
    private Timer timer = new Timer();

    /**
     * this is the initialize function and it just gets alot of the basic things and handlers put together.
     * @param location
     * @param Resources
     */
    public void initialize(URL location, ResourceBundle Resources){
        mineCountProperty = new SimpleIntegerProperty(gameGrid.getNumberOfBombs());
        controls = new Controls(gameGrid);
        numBombsLeft.textProperty().bindBidirectional(mineCountProperty, new NumberStringConverter());
        timeElapsedLabel.textProperty().bindBidirectional(timeElapsedProperty, new NumberStringConverter());
        startBtn.setOnAction(this::handleStartButtonClick);
        initMenuHandlers();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Your Choice Points");
        alert.setContentText("I implemented the following features.\n" +
                            "   \t10 pts - difficulty feature. Fully functional.\n" +
                            "   \t5  pts - reporting feature. Fully functional.\n" +
                            "   \t15 pts - time trial feature. Fully functional.\n" +
                            "I hope it is sufficient for the total 30 points.");
        alert.showAndWait();
    }

    /**
     * this is the function to set up all the menu handlers for picking difficulty or whatever
     */
    private void initMenuHandlers(){
        easyMenuItem.setOnAction(this::easyMenuItemHandle);
        moderateMenuItem.setOnAction(this::moderateMenuItemHandle);
        hardMenuItem.setOnAction(this::hardMenuItemHandle);
//        smallMenuItem.setOnAction(this::smallMenuItemHandle);
//        mediumMenuItem.setOnAction(this::mediumMenuItemHandle);
//        largeMenuItem.setOnAction(this::largeMenuItemHandle);
        allScoresMenuItem.setOnAction(this::allScoresMenuItemHandle);
        clearHistoryItem.setOnAction(this::clearHistoryItemHandle);
        timeTrialMenuItem.setOnAction(this::timeTrialMenuItemHandle);
    }

    /**
     * stops the timer and then makes a new grid with 10% of the cells as bombs
     * @param event
     */
    private void easyMenuItemHandle(ActionEvent event){
        timer.cancel();
        //small 10 percent grid
        gameGrid.changeDifficulty(.1);
        //call start function and reset
        start();
    }

    /**
     * stops the timer and then makes a new grid with 25% of the cells as bombs
     * @param event
     */
    private void moderateMenuItemHandle(ActionEvent event){
        timer.cancel();
        //moderate 25 percent grid
        gameGrid.changeDifficulty(.25);
        //call start function and reset
        start();
    }

    /**
     * stops the timer and then makes a new grid with 40% of the cells as bombs
     * @param event
     */
    private void hardMenuItemHandle(ActionEvent event){
        timer.cancel();
       //hard 40 percent grid
        gameGrid.changeDifficulty(.40);
        //call start function and reset
        start();

    }

    /**
     * this stops the timer and resets up the game in time trial mode.
     *
     * @param event
     */
    private void timeTrialMenuItemHandle(ActionEvent event) {
        timer.cancel();
      timer = new Timer();
        start();
        TextInputDialog nameEntry = new TextInputDialog("---");
        nameEntry.setTitle("Time Trial Mode");
        nameEntry.setHeaderText(null);
        nameEntry.setContentText("Enter the amount of time for your trial: ");
        Optional<String> time = nameEntry.showAndWait();
        if (time.isPresent()) {
                timeElapsedProperty.setValue(Integer.parseInt(time.get()));
        }
        timeTrialMode = true;
    }
//    private void smallMenuItemHandle(ActionEvent event){
//        // makes the grid size ten by ten
//        gameGrid.changeSize(10,10);
//        //call start function and reset
//        gameGrid.createGrid();
//        start();
//    }
//    private void mediumMenuItemHandle(ActionEvent event){
//        // makes the grid size 25 by 25
//        gameGrid.changeSize(25,25);
//        // call start function and reset
//        gameGrid.createGrid();
//        start();
//    }
//    private void largeMenuItemHandle(ActionEvent event){
//        // makes the grid size 50 by 25
//        gameGrid.changeSize(50, 25);
//        // call start function and reset
//        gameGrid.createGrid();
//        start();
//    }

    /**
     * brings up the record of all recorded stores
     * @param event
     */
    private void allScoresMenuItemHandle(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Scores");
        alert.setGraphic(new ImageView(gameGrid.getFlagBig()));
        alert.setContentText(gameGrid.scoreBoard.readScores() + "\nNo Other Scores to Show");
        alert.showAndWait();
        // shows all scores with a little tag of the difficulty
    }

    /**
     * clears the scores.txt file
     * @param event
     */
    private void clearHistoryItemHandle (ActionEvent event){
        gameGrid.scoreBoard.clearFile();
    }

    /**
     * this starts the timer to count up or down depending on the mode
     */
    private void startTimer(){

        timer.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run (){
                Platform.runLater(() -> {
                    if(timeTrialMode){
                        timeElapsedProperty.setValue(timeElapsedProperty.getValue() - 1);
                        gameGrid.incrementTime();
                        if(timeElapsedProperty.getValue() == 0){
                            controls.endGame(false);
                            winLoseProcess();
                            timer.cancel();
                        }
                    }
                    else {
                        timeElapsedProperty.setValue(timeElapsedProperty.getValue() + 1);
                        gameGrid.incrementTime();
                    }
                });
            }
        }, 1000 , 1000);
    }

    /**
     * this handles all the clicks on the game board right or left and then it also calls function to track the
     * players progress and to end the game when appropriate
     * @param event
     */
    private void clickHandle(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY) {
            if (!isOver) {
                if (firstClick) {
                    timer.cancel();
                    timer = new Timer();
                    startTimer();
                    firstClick = false;
                }
                isOver = controls.clickingMethod((GameBtn) event.getSource(), false);
                if (isOver) {
                    winLoseProcess();
                }
            }
        }
        if(!isOver) {
            if (event.getButton() == MouseButton.SECONDARY) {
                controls.markingMethod((GameBtn) event.getSource(), mineCountProperty);

            }
        }

  }

    /**
     * this function brings alerts to the screen when the game is over to notify the
     * player of their performance and give the option to record their score given they
     * won and didn't lose
     */
  public void winLoseProcess(){
      startBtn.setDisable(false);
      timer.cancel();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      int bombsMarked = gameGrid.getNumMinesCorrectlyMarked();
      int time = gameGrid.getTimeElapsed();
      if(gameGrid.isWin()){

          //do something
          alert.setHeaderText("YOU WIN");
          alert.setGraphic(new ImageView(gameGrid.getFlagBig()));

          alert.setContentText("time passed: " + time);


      }
      else{
          alert.setHeaderText("YOU LOSE");
          alert.setGraphic(new ImageView(gameGrid.getBombBig()));
          alert.setContentText("time passed: " + time + "\nbombs correctly marked: " + bombsMarked );
      }

      alert.showAndWait();
      if(gameGrid.isWin()) {
          TextInputDialog nameEntry = new TextInputDialog("Name");
          nameEntry.setTitle("Name Entry");
          nameEntry.setHeaderText(null);
          nameEntry.setContentText("Enter your name: ");
          Optional<String> name = nameEntry.showAndWait();
          if (name.isPresent()) {
              String difficulty;
              if (gameGrid.getPercentOfBombs() == .1) {
                  difficulty = "easy";
              } else if (gameGrid.getPercentOfBombs() == .25) {
                  difficulty = "medium";
              } else if (gameGrid.getPercentOfBombs() == .4) {
                  difficulty = "hard";
              } else {
                  difficulty = "test";
              }
              gameGrid.scoreBoard.writeScore(gameGrid.getTimeElapsed(), name.get(), difficulty);
          }
      }
  }

    /**
     * this function resets up the grid so that there are no errors when playing second and 3rd and nth times
     */
    public void resetGrid(){

        gridPane.getChildren().clear();


        for(int i = 0; i < gameGrid.getGridWidth() ; i++){
            for(int j = 0; j < gameGrid.getGridHeight() ;  j++){

                gridPane.add(gameGrid.grid[i][j], i,j);
                gameGrid.grid[i][j].setiCoordinate(i);
                gameGrid.grid[i][j].setjCoordinate(j);
                currentBtn = gameGrid.grid[i][j];
                gameGrid.grid[i][j].setOnMouseClicked(this::clickHandle);
            }
        }
    }

    /**
     * this is the function that is called by the start button and when changing the modes of the game
     */
    private void start(){
        timeTrialMode = false;
        startBtn.setDisable(true);
        timeElapsedProperty.setValue(0);
        firstClick = true;
        isOver = false;
        double percent = gameGrid.getPercentOfBombs();
        gameGrid = new GameGrid();
//        gameGrid.changeSize(width, height);
        gameGrid.changeDifficulty(percent);
        mineCountProperty.setValue(gameGrid.getNumberOfBombs());
        controls = new Controls(gameGrid);
        gameGrid.createGrid();
        resetGrid();
    }

    /**
     * starts the game
     * @param event
     */
    private void handleStartButtonClick(ActionEvent event){
        start();
    }
}