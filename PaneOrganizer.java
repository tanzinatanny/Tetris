package Tetris;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneOrganizer {
	private BorderPane _border;
	private VBox _vbox;
	private Pane _centerPane;
	
	
	public PaneOrganizer(){
		_border = new BorderPane();
		_border.setFocusTraversable(true);
	
		this.setupCenterPane();
		this.setupRightPane();
		
		new Game(_centerPane);
		
	}
	// setup the right side pane
	public void setupRightPane (){
		_vbox = new VBox();
		_border.setRight(_vbox);
		
		Button b1 = new Button("Quit");
		b1.setOnAction(new ButtonHandler("quit"));
		
		Label label1 = new Label("press Q to quit");
		
		_vbox.getChildren().addAll( label1, b1 );
		
		_vbox.setSpacing(30);
		_vbox.setAlignment(Pos.CENTER);
		_vbox.setPadding(new Insets(10,12,10,12));
		_vbox.setStyle("-fx-background-color: rgb(232, 194, 118) ");
		
	}
	
	//setup the center pane to hold the board
	public void setupCenterPane (){
		_centerPane = new Pane();
		_centerPane.requestFocus();
		_centerPane.setFocusTraversable(true);
	
		_border.setCenter(_centerPane);
		
	}
	
	//handle button events
	private class ButtonHandler implements EventHandler <ActionEvent> {
		
		String _name;
		
		public ButtonHandler(String name){
			_name = name;
		}
		
		public void handle(ActionEvent event){
			
			switch (_name) {
			
				case "quit":
					Platform.exit();
					break;
				
			}
			
		}
		
	}
	
	public BorderPane getRoot(){
		return _border;
	}
	
}
		


		
	

