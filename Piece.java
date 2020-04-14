package Tetris;

import javafx.scene.paint.Color;

public class Piece {
	
	public int[][] _array;
	public Color color;
	double xLoc,yLoc;
	
	private int[][] _I = { 
			{ 1, 0},
			{ 1, 0},
			{ 1, 0},
			{ 1, 0},
	};
	
	private int[][] _J = { 
		    {1, 0, 0},
		    {1, 1, 1},
	};
	
	private int[][] _L = { 
		    {0, 0, 1},
		    {1, 1, 1},
	};
	
	private int[][] _O = { 
		    {1, 1},
		    {1, 1},
	};
	
	private int[][] _S = { 
		    {0, 1, 1},
		    {1, 1, 0},
	};
	
	private int[][] _Z = { 
		    {1, 1, 0},
		    {0, 1, 1},
	};
	
	private int[][] _T = { 
		    {0, 1, 0},
		    {1, 1, 1},
	};
	
	
	
	public Piece( int rand ){
		
		switch( rand ) {
		
			case 0:
				this._array = _I;
				this.color = Color.BLUE;
				break;
			case 1:
				this._array = _J;
				this.color = Color.RED;
				break;
			case 2: 
				this._array = _L;
				this.color = Color.CYAN;
				break;
			case 3: 
				this._array = _O;
				this.color = Color.GREEN;
				break;
			case 4: 
				this._array = _S;
				this.color = Color.WHITE;
				break;
			case 5: 
				this._array = _Z;
				this.color = Color.GREY;
				break;
			case 6: 
				this._array = _T;
				this.color = Color.PINK;
				break;
				
			default:
				this._array = _I;
				this.color = Color.BLUE;
				break;
					
		}
		
		this.xLoc = 3;
		this.yLoc = 0;
	}
	
}