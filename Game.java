package Tetris;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Game{
	
	private TetrisSquare[][] _board;
	private Pane _centerPane;
	private Timeline _timeline;
	private Piece _currentPiece;
	private boolean _paused;
	
	private Label _label;
	
	private ArrayList<TetrisSquare> _nodes = new ArrayList<TetrisSquare>();
	private ArrayList<Integer> _clearRows = new ArrayList<Integer>();
	
	public Game( Pane centerPane ) {
		
		_centerPane = centerPane;
		_centerPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());
		
		_board = new TetrisSquare[Constants.NUM_COLS][Constants.NUM_ROWS];
		_paused = false;
		
		initialize_board(); 
		generateRandomPiece();
		updateBoard();
	}
	
	//Initialize the Board
	private void initialize_board(){
		for( int row = 0; row< Constants.NUM_ROWS; row++ ){
			
			for( int col = 0; col < Constants.NUM_COLS; col++ ){
				 TetrisSquare t = new TetrisSquare( col, row, Color.BLACK );
				_centerPane.getChildren().add(t);
			}
			
		}
	}
	
	//Keep the board updated with shapes using time line
	private void updateBoard(){
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION),new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
             	movePieceDown();
             	clearLines();
             	
            }
        });
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}
	
	//Generate a new random piece
	private void generateRandomPiece(){
		 
		int rand = (int) (Math.random() * 7);
		_currentPiece = new Piece( rand );
		
		int flag = 0;
	
		for( int row = 0; row < _currentPiece._array.length; row++ ){
			for( int col = 0; col < _currentPiece._array[0].length; col++ ){
				if( _currentPiece._array[row][col] != 0 ){
					
					int x = (int) (col+ _currentPiece.xLoc);
					int y = (int) (row + _currentPiece.yLoc);
					
					if( board_is_fillable(x, y) ){
						flag++;
					}
					
				}
			}
		}
		
		//if all 4 squares have space in board we add them to pane otherwise the new piece can't enter the board so game over
		if( flag == 4 ){
			for( int row = 0; row < _currentPiece._array.length; row++ ){
				for( int col = 0; col < _currentPiece._array[0].length; col++ ){
					if( _currentPiece._array[row][col] != 0 ){
						
						TetrisSquare t = new TetrisSquare( col+ _currentPiece.xLoc, row + _currentPiece.yLoc, _currentPiece.color );
						_nodes.add(t);
						_centerPane.getChildren().add(t);
					}
				}
			}
		} else {
			gameOver();
		}
		
	}
	
	//Move the piece down by 1 Square
	private void movePieceDown(){
		
		int flag = 0;
		Boolean landed = false;
		for (TetrisSquare t : _nodes) {
			int nextY =  t._position_y + 1;
			int nextX =  t._position_x;
			
			if( board_is_fillable(nextX, nextY) ){
					flag++;
			}
		}
		
		for (TetrisSquare t : _nodes) {
			// only if all 4 squares can move down shift shape down
			if( flag == 4 ){
				t.setY(t.getY() + Constants.SQUARE_SIZE);
				t._position_y = t._position_y + 1;
			} else {
				_board[t._position_x][t._position_y] = t;
				landed = true;
			}
		}
		
		if( landed ){
			_nodes.clear();
			generateRandomPiece();
		}
		
	}
	
	// Move the piece Left by 1 Square
	private void moveLeft(){
		
		int flag = 0;
		for (TetrisSquare t : _nodes) {
			int nextX = t._position_x - 1;
			
			if( board_is_fillable(nextX, t._position_y) ){
				flag++;
			}
		}
		
		if( flag == 4 ){
			for (TetrisSquare t : _nodes) {
				
				t.setX(t.getX() - Constants.SQUARE_SIZE );
				t._position_x = t._position_x - 1;
				
			}
		}
	}
	
	// Move the piece Right by 1 Square
	private void moveRight(){
		int flag = 0;
		for (TetrisSquare t : _nodes) {
			int nextX = t._position_x + 1;
			
			if( board_is_fillable(nextX, t._position_y) ){
				flag++;
			}
		}
		
		if( flag == 4 ){
			for (TetrisSquare t : _nodes) {
				t.setX(t.getX() + Constants.SQUARE_SIZE );
				t._position_x = t._position_x + 1;
				
			}
		}
	}
	
	//Rotate the current piece
	private void rotatePiece(){
		int flag = 0;
		TetrisSquare center = _nodes.get(2);
		
		for (TetrisSquare t : _nodes) {
			
			int nextX = (int) (center._position_x - center._position_y + t._position_y);
			int nextY = (int) (center._position_x + center._position_y - t._position_x);
			
			if( board_is_fillable( nextX, nextY ) ){
				flag++;
			}
		}
		
		if( flag == 4 ){
			for (TetrisSquare t : _nodes) {
				double nextX = center.getX() - center.getY() + t.getY();
				double nextY = center.getX() + center.getY() - t.getX();
				
				int posX = (int) (center._position_x - center._position_y + t._position_y);
				int posY = (int) (center._position_x + center._position_y - t._position_x);
				t.setX( nextX );
				t.setY( nextY );
				t._position_x = posX;
				t._position_y = posY;
				
			}
		}
	}
	
	//Check if the space is fillable in board
	private Boolean board_is_fillable( int x, int y ){
		
		if( !validateX(x) || !validateY(y) ){
			return false;
		}
		
		if( _board[x][y] == null ){
			return true;
		}
		
		return false;
	}
	
	//validate X position 
	private Boolean validateX( int x ){
		
		if( x < 0 || x > Constants.NUM_COLS - 1 ){
			return false;
		}
		
		return true;
	}
	
	//validate Y position 
	private Boolean validateY( int y ){
		
		if( y < 0 || y > Constants.NUM_ROWS - 1 ){
			return false;
		}
		
		return true;
	}
	
	//pause the game
	private void pauseGame(){
		if( !_paused ){
			
			_timeline.pause();
			_label = new Label( "Paused" );
			_label.setAlignment(Pos.CENTER_LEFT);
			_label.setLayoutX(60 );
			_label.setLayoutY(200);
			_label.setStyle(" -fx-font: 50px Tahoma;");
			_label.setTextFill(Color.WHITE);
			_centerPane.getChildren().add(_label);
			_paused = true;
			
		} else {
			_timeline.play();
			_centerPane.getChildren().remove(_label);
			_paused = false;
		}
		
	}
	
	//show game over text and end game
	private void gameOver(){
		_timeline.stop();
		
		_label = new Label( "Game Over" );
		_label.setAlignment(Pos.CENTER_LEFT);
		_label.setLayoutX(30);
		_label.setLayoutY(200);
		_label.setStyle(" -fx-font: 50px Tahoma;");
		_label.setTextFill(Color.RED);
		_centerPane.getChildren().add(_label);
	}
	
	//Check for filled horizontal row and clear them
	private void clearLines(){
		_timeline.pause();
		for( int row = Constants.NUM_ROWS - 1; row >= 0; row--  ){
					
			int filled = 0;
			
			for( int col = Constants.NUM_COLS -1; col >= 0 ; col-- ){
				if( !board_is_fillable(col, row) ){
					filled++;
				}
			}
			
			if( filled == Constants.NUM_COLS ){
				_clearRows.add(row);
				clearFilledRows(row);
				moveDown(row);
				
			}
		}
		
		_timeline.play();
	}
	
	//reset the filled row logically
	private void clearFilledRows(int row){
			for( int col = Constants.NUM_COLS -1; col >= 0 ; col-- ){		
				_centerPane.getChildren().remove(_board[col][row]);
				_board[col][row] = null;
			}
			
	}
	
	private void moveDown( int clearRow ){

		//clear row is already cleared shoud start from above of clearRow
		for( int row = clearRow - 1; row > 0; row-- ){
			for( int col = 9; col >= 0; col--){
				if( _board[col][row + 1] == null && _board[col][row] != null){
					
					TetrisSquare t = _board[col][row];
					
					t.setY(t.getY() + Constants.SQUARE_SIZE );
					
					_board[col][row + 1] = _board[col][row];
					_board[col][row] = null;
				}
			}
			
		}
		
	}
	
	//Key handler class to handle KeyPress events
	private class KeyHandler implements EventHandler<KeyEvent> {
		
		@Override
		public void handle(KeyEvent e) {
			KeyCode keyPressed = e.getCode();
			
		        switch (keyPressed) {
		           
		            case LEFT:
		            	moveLeft();
		            	e.consume();
			            break;
			            
		            case RIGHT:
		            	moveRight();
		            	e.consume();
			            break;
			            
		            case UP:
		            	rotatePiece();
		            	e.consume();
			            break;
			            
		            case DOWN:
		            	movePieceDown();
		            	e.consume();
			            break;
			            
		            case SPACE:
		            	movePieceDown();
		            	e.consume();
			            break;
			            
		            case P:
		            	pauseGame();
		            	break;
			            
		            case Q:
		            	Platform.exit();
		            	break;
		            	
					default:
						break;
		        }
		        
			}
	}

}