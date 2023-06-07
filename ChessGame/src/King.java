import javax.swing.*;
import java.util.ArrayList;

public class King extends Piece{
	
	int x,y; //Extra variables for King class to keep a track of king's position
	

	public King(String name, ImageIcon imageIcon, int color, int x, int y) {
		this.x=x;
		this.y=y;
		this.imageIcon=imageIcon;
		this.color=color;
	}
	

	public void setx(int x)
	{
		this.x=x;
	}
	public void sety(int y)
	{
		this.y=y;
	}
	public int getx()
	{
		return x;
	}
	public int gety()
	{
		return y;
	}

	public ArrayList<Cell> move(Cell[][] state, int x, int y) {

		possibleMoves.clear();
		int[] posy = {y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1};
		int[] posx = {x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1};
		for(int i=0;i<8;i++) {
			if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8)) {
				if((state[posx[i]][posy[i]].piece==null||state[posx[i]][posy[i]].piece.color!=this.color))
					possibleMoves.add(state[posx[i]][posy[i]]);
			}
		}
		return possibleMoves;
	}

	public boolean isInDanger(Cell[][] pos) {
		

    	for(int i=x+1;i<8;i++) {
    		if(pos[i][y].piece==null) {
				continue;
			} else if(pos[i][y].piece.color==this.color) {
				break;
			} else {
    			if ((pos[i][y].piece instanceof Rook) || (pos[i][y].piece instanceof Queen)) {
					return true;
				} else {
					break;
				}
    		}
    	}
    	for(int i=x-1;i>=0;i--) {
    		if(pos[i][y].piece==null)
    			continue;
    		else if(pos[i][y].piece.color==this.color)
    			break;
    		else
    		{
    			if ((pos[i][y].piece instanceof Rook) || (pos[i][y].piece instanceof Queen))
    				return true;
    			else
    				break;
    		}
    	}
    	for(int i=y+1;i<8;i++) {
    		if(pos[x][i].piece==null)
    			continue;
    		else if(pos[x][i].piece.color==this.color)
    			break;
    		else
    		{
    			if ((pos[x][i].piece instanceof Rook) || (pos[x][i].piece instanceof Queen))
    				return true;
    			else
    				break;
    		}
    	}
    	for(int i=y-1;i>=0;i--) {
    		if(pos[x][i].piece==null)
    			continue;
    		else if(pos[x][i].piece.color==this.color)
    			break;
    		else
    		{
    			if ((pos[x][i].piece instanceof Rook) || (pos[x][i].piece instanceof Queen))
    				return true;
    			else
    				break;
    		}
    	}

    	int tempx=x+1,tempy=y-1;
		while(tempx<8&&tempy>=0){
			if(pos[tempx][tempy].piece==null)
			{
				tempx++;
				tempy--;
			}
			else if(pos[tempx][tempy].piece.color==this.color)
				break;
			else
			{
				if (pos[tempx][tempy].piece instanceof Bishop || pos[tempx][tempy].piece instanceof Queen)
    				return true;
    			else
    				break;
			}
		}
		tempx=x-1;tempy=y+1;
		while(tempx>=0&&tempy<8) {
			if(pos[tempx][tempy].piece==null)
			{
				tempx--;
				tempy++;
			}
			else if(pos[tempx][tempy].piece.color==this.color)
				break;
			else
			{
				if (pos[tempx][tempy].piece instanceof Bishop || pos[tempx][tempy].piece instanceof Queen)
    				return true;
    			else
    				break;
			}
		}
		tempx=x-1;
		tempy=y-1;
		while(tempx>=0&&tempy>=0) {
			if(pos[tempx][tempy].piece==null)
			{
				tempx--;
				tempy--;
			}
			else if(pos[tempx][tempy].piece.color==this.color)
				break;
			else
			{
				if (pos[tempx][tempy].piece instanceof Bishop || pos[tempx][tempy].piece instanceof Queen)
    				return true;
    			else
    				break;
			}
		}
		tempx=x+1;tempy=y+1;
		while(tempx<8&&tempy<8) {
			if(pos[tempx][tempy].piece==null)
			{
				tempx++;
				tempy++;
			}
			else if(pos[tempx][tempy].piece.color==this.color)
				break;
			else
			{
				if (pos[tempx][tempy].piece instanceof Bishop || pos[tempx][tempy].piece instanceof Queen)
    				return true;
    			else
    				break;
			}
		}


		int[] posy = {y - 2, y + 2, y - 1, y + 1, y - 2, y + 2, y - 1, y + 1};
		int[] posx = {x + 1, x + 1, x + 2, x + 2, x - 1, x - 1, x - 2, x - 2};

		for(int i=0;i<8;i++) {
			if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8)) {
				if(pos[posx[i]][posy[i]].piece!=null && pos[posx[i]][posy[i]].piece.color!=this.color && (pos[posx[i]][posy[i]].piece instanceof Knight)) {
					return true;
				}
			}
		}

		int[] poy = {y - 1, y + 1, y, y + 1, y - 1, y + 1, y - 1, y};
		int[] pox = {x + 1, x + 1, x + 1, x, x, x - 1, x - 1, x - 1};
		{
			for(int i=0;i<8;i++)
				if((pox[i]>=0&&pox[i]<8&&poy[i]>=0&&poy[i]<8)) {
					if(pos[pox[i]][poy[i]].piece!=null && pos[pox[i]][poy[i]].piece.color!=this.color && (pos[pox[i]][poy[i]].piece instanceof King)) {
						return true;
					}
				}
		}
		if(color==0) {
			if(x>0&&y>0&&pos[x-1][y-1].piece!=null&&pos[x-1][y-1].piece.color==1&&(pos[x-1][y-1].piece instanceof Pawn)) {
				return true;
			}
			return x > 0 && y < 7 && pos[x - 1][y + 1].piece != null && pos[x - 1][y + 1].piece.color == 1 && (pos[x - 1][y + 1].piece instanceof Pawn);
		} else {
			if(x<7&&y>0&&pos[x+1][y-1].piece!=null&&pos[x+1][y-1].piece.color==0&&(pos[x+1][y-1].piece instanceof Pawn)) {
				return true;
			}
			return x < 7 && y < 7 && pos[x + 1][y + 1].piece != null && pos[x + 1][y + 1].piece.color == 0 && (pos[x + 1][y + 1].piece instanceof Pawn);
		}
	}

}