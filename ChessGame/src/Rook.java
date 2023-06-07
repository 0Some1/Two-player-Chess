import javax.swing.*;
import java.util.ArrayList;


public class Rook extends Piece{
	

	public Rook(String name, ImageIcon imageIcon, int color) {
		this.color=color;
		this.name=name;
		this.imageIcon=imageIcon;
	}

	public ArrayList<Cell> move(Cell state[][],int x,int y) {

		possibleMoves.clear();
		int tempx=x-1;
		while(tempx>=0)
		{
			if(state[tempx][y].getpiece()==null) {
				possibleMoves.add(state[tempx][y]);
			} else if(state[tempx][y].getpiece().getColor()==this.getColor()) {
				break;
			} else {
				possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx--;
		}
		tempx=x+1;
		while(tempx<8)
		{
			if(state[tempx][y].getpiece()==null) {
				possibleMoves.add(state[tempx][y]);
			} else if(state[tempx][y].getpiece().getColor()==this.getColor()) {
				break;
			} else {
				possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx++;
		}
		int tempy=y-1;
		while(tempy>=0) {
			if(state[x][tempy].getpiece()==null) {
				possibleMoves.add(state[x][tempy]);
			} else if(state[x][tempy].getpiece().getColor()==this.getColor()) {
				break;
			} else {
				possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy--;
		}
		tempy=y+1;
		while(tempy<8) {
			if(state[x][tempy].getpiece()==null) {
				possibleMoves.add(state[x][tempy]);
			} else if(state[x][tempy].getpiece().getColor()==this.getColor()) {
				break;
			} else {
				possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy++;
		}
		return possibleMoves;
	}
}
