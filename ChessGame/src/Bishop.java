import javax.swing.*;
import java.util.ArrayList;


public class Bishop extends Piece{
	

	public Bishop(String name, ImageIcon imageIcon, int color) {
		this.color=color;
		this.name=name;
		this.imageIcon=imageIcon;
	}
	

	public ArrayList<Cell> move(Cell pos[][],int x,int y) {
		possibleMoves.clear();
		int tempx=x+1;
		int tempy=y-1;
		while(tempx<8 && tempy>=0) {
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
		tempx=x-1;
		tempy=y+1;
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
		tempx=x-1;
		tempy=y-1;
		while(tempx>=0 && tempy>=0) {
			if(pos[tempx][tempy].piece==null) {
				possibleMoves.add(pos[tempx][tempy]);
			} else if(pos[tempx][tempy].piece.color==this.color) {
				break;
			} else {
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