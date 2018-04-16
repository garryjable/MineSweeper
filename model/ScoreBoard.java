package cs2410.model;


import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * ScoreBoard class for recording and reporting scores
 * @version 1.0
 * @author Garrett Jennings A01491831
 */
public class ScoreBoard {


    /**
     * the file name that contains the scores
     */
    private static final String fileName = "src/cs2410/resources/scores.txt";

    /**
     * blank constructor
     */
    public ScoreBoard(){

    }

    /**
     * this is used to get the scores from the file and then returns so it can
     * be reported to the screen
     * @return
     */
    public String readScores(){
        Scanner fileInput = null;
        String scores = "";
        PrintWriter fileOutput = null;
        try {
            fileInput = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (fileInput.hasNext()) {
            scores += fileInput.nextLine();
            scores += "\n";
        }
        return scores;
    }

    /**
     * this function takes a time and an name and a difficulty and creates an entry in the scores file
     * @param time
     * @param Name
     * @param difficulty
     */
    public void writeScore(int time, String Name, String difficulty){

        PrintWriter fileOutput = null;

        try {
            fileOutput = new PrintWriter(new FileOutputStream(fileName, true)); //try changing to false
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileOutput.println(Name + " cleared " + difficulty + " board in " + time + " seconds");
        fileOutput.close();

    }

    /**
     * this clears the scores file called by the clear history button
     */
    public void clearFile(){
        PrintWriter fileOutput = null;
        try {
            fileOutput = new PrintWriter(new FileOutputStream(fileName, false)); //try changing to false
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileOutput.close();
    }

}
