package Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
  	  PaneOrganizer organizer = new PaneOrganizer();
  	  Scene scene = new Scene(organizer.getRoot(), Constants.BOARD_WIDTH + 115, Constants.BOARD_HEIGHT );
  	  
  	  primaryStage.setScene(scene);
  	  primaryStage.setTitle("Tetris");
  	  primaryStage.show();
  	  
    }
   
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
