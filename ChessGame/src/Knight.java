import javax.swing.*;
import java.util.ArrayList;

/**
 * This is the Knight Class inherited from the Piece abstract class
 *  
 *
 */
public class Knight extends Piece{
	
	//Constructor
	public Knight(String name, ImageIcon imageIcon, int color)
	{
		this.color=color;
		this.name=name;
		this.imageIcon=imageIcon;
	}
	

	public ArrayList<Cell> move(Cell state[][],int x,int y) {
		possibleMoves.clear();
		int posy[]={y-2,y+2,y-1,y+1,y-2,y+2,y-1,y+1};
		int posx[]={x+1,x+1,x+2,x+2,x-1,x-1,x-2,x-2};
		for(int i=0;i<8;i++) {
			if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8)) {
				if((state[posx[i]][posy[i]].piece==null||state[posx[i]][posy[i]].piece.color!=this.color)) {
					possibleMoves.add(state[posx[i]][posy[i]]);
				}
			}
		}
		return possibleMoves;
	}
}