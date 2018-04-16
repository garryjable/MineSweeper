package cs2410.view;

import cs2410.control.Controls;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Our Main class here everything begins to run minesweeper,
 * ideally I should have used this main class more but I kinda messed up on organization.
 * @version 1.0
 * @author  Garrett Jennings A01491831
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/cs2410/resources/view.fxml"));

        primaryStage.setTitle("Minesweeperish");
        primaryStage.setScene(new Scene(root, 800,800));
        primaryStage.setResizable(false);











        primaryStage.show();





    }

    /**
     * ideally I should have used this main class more but I messed up on organization.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
