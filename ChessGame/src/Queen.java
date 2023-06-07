import javax.swing.*;
import java.util.ArrayList;

/**
 * This is the Queen Class inherited from the abstract Piece class
 *
 */
public class Queen extends Piece{
	
	//Constructors
	public Queen(String name, ImageIcon imageIcon, int color) {
		this.color=color;
		this.name=name;
		this.imageIcon=imageIcon;
	}
	

	public ArrayList<Cell> move(Cell pos[][],int x,int y) {

		possibleMoves.clear();
		

		int tempx=x-1;
		while(tempx>=0) {
			if(pos[tempx][y].piece==null) {
				possibleMoves.add(pos[tempx][y]);
			} else if(pos[tempx][y].piece.color==this.color) {
				break;
			} else {
				possibleMoves.add(pos[tempx][y]);
				break;
			}
			tempx--;
		}
		
		tempx=x+1;
		while(tempx<8) {
			if(pos[tempx][y].piece==null) {
				possibleMoves.add(pos[tempx][y]);
			} else if(pos[tempx][y].piece.color==this.color) {
				break;
			} else {
				possibleMoves.add(pos[tempx][y]);
				break;
			}
			tempx++;
		}
		
		

		int tempy=y-1;
		while(tempy >= 0) {
			if(pos[x][tempy].piece==null) {
				possibleMoves.add(pos[x][tempy]);
			} else if(pos[x][tempy].piece.color==this.color) {
				break;
			} else {
				possibleMoves.add(pos[x][tempy]);
				break;
			}
			tempy--;
		}
		tempy=y+1;
		while(tempy<8) {
			if(pos[x][tempy].piece==null) {
				possibleMoves.add(pos[x][tempy]);
			} else if(pos[x][tempy].piece.color==this.color) {
				break;
			} else {
				possibleMoves.add(pos[x][tempy]);
				break;
			}
			tempy++;
		}
		

		tempx=x+1;tempy=y-1;
		while(tempx<8&&tempy>=0) {
			if(pos[tempx][tempy].piece==null) {
				possibleMoves.add(pos[tempx][tempy]);
			} else if(pos[tempx][tempy].piece.color==this.color) {
				break;
			} else {
				possibleMoves.add(pos[tempx][tempy]);
				break;
			}
			tempx++;
			tempy--;
		}
		tempx=x-1;tempy=y+1;
		while(tempx>=0&&tempy<8) {
			if(pos[tempx][tempy].piece==null) {
				possibleMoves.add(pos[tempx][tempy]);
			} else if(pos[tempx][tempy].piece.color==this.color) {
				break;
			} else {
				possibleMoves.add(pos[tempx][tempy]);
				break;
			}
			tempx--;
			tempy++;
		}
		tempx=x-1;tempy=y-1;
		while(tempx>=0&&tempy>=0) {
			if(pos[tempx][tempy].piece==null) {
				possibleMoves.add(pos[tempx][tempy]);
			} else if(pos[tempx][tempy].piece.color==this.color) {
				break;
			} else
			{
				possibleMoves.add(pos[tempx][tempy]);
				break;
			}
			tempx--;
			tempy--;
		}
		tempx=x+1;tempy=y+1;
		while(tempx<8&&tempy<8) {
			if(pos[tempx][tempy].piece==null) {
				possibleMoves.add(pos[tempx][tempy]);
			} else if(pos[tempx][tempy].piece.color==this.color) {
				break;
			} else {
				possibleMoves.add(pos[tempx][tempy]);
				break;
			}
			tempx++;
			tempy++;
		}
		return possibleMoves;
	}
}