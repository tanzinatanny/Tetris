package Tetris;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrisSquare extends Rectangle {
	
	public int _position_x;
	public int _position_y;
	
	public TetrisSquare( double x_loc, double y_loc, Color color ) {
		super( Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, color );
		this.setX( x_loc * Constants.SQUARE_SIZE );
		this.setY( y_loc * Constants.SQUARE_SIZE );
		this.setStroke(Color.WHITE);
		this.setStrokeWidth(0.3);
		this._position_x = (int) x_loc;
		this._position_y = (int) y_loc;
	}
	
}