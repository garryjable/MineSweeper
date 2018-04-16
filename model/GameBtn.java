package cs2410.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 * this is the game button class it inherits from button and has a few things unique to it.
 * @version 1.0
 * @author Garrett Jennings A01491831
 */
public abstract class GameBtn extends Button {
    /**
     * this is the j coordinate of the button
     */
    int jCoordinate;
    /**
     * this is the i coordinate of the button
     */
    int iCoordinate;
    /**
     * this is a boolean that gets switched when the button is clicked.
     */
    boolean isClicked = false;
    /**
     * this a boolean that is used to prevent a button from being clicked when it is marked.
     */
    boolean isMarked = false;


    /**
     * the constructor sets the size so that it fits the pane. Sets the text to null
     */
    GameBtn() {
        setMaxSize(32, 32);
        setText(null);
    }

    /**
     * returns is marked used to prevent clicking
     * @return
     */
    public boolean isMarked() {
        return isMarked;
    }

    /**
     * this function marks a cell
     */
    public void bombMarked(){
        isMarked = true;
    }

    /**
     * this function unmarks a cell
     */
    public void bombUnmarked(){
        isMarked = false;
    }

    /**
     * this function sets the i coordinate
     * @param iCoordinate
     */
    public void setiCoordinate(int iCoordinate) {
        this.iCoordinate = iCoordinate;
    }

    /**
     * this function sets the j coordinate
     * @param jCoordinate
     */
    public void setjCoordinate(int jCoordinate) {
        this.jCoordinate = jCoordinate;
    }

    /**
     * this function gets the i coordinate
     * @return
     */
    public int getiCoordinate() {
        return iCoordinate;
    }

    /**
     * this function gets the j coordinate
     * @return
     */
    public int getjCoordinate() {
        return jCoordinate;
    }

    /**
     * this function sets the boolean clicked
     * @param clicked
     */
    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    /**
     * this function returns the clicked boolean
     * @return
     */
    public boolean isClicked() {
        return isClicked;
    }
}