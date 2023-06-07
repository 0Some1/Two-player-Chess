import javax.swing.*;
import java.util.ArrayList;

public class Pawn extends Piece{


    public Pawn(String name, ImageIcon imageIcon, int color) {
        this.color=color;
        this.name=name;
        this.imageIcon=imageIcon;
    }

    public ArrayList<Cell> move(Cell pos[][], int x, int y) {


        possibleMoves.clear();
        if(color==0) {
            if(x==0)
                return possibleMoves;
            if(pos[x-1][y].piece==null) {
                possibleMoves.add(pos[x-1][y]);
                if(x==6) {
                    if(pos[4][y].piece==null) {
                        possibleMoves.add(pos[4][y]);
                    }
                }
            }
            if((y>0)&&(pos[x-1][y-1].piece!=null)&&(pos[x-1][y-1].piece.color!=this.color)) {
                possibleMoves.add(pos[x-1][y-1]);
            }
            if((y<7)&&(pos[x-1][y+1].piece!=null)&&(pos[x-1][y+1].piece.color!=this.color)) {
                possibleMoves.add(pos[x-1][y+1]);
            }
        } else {
            if(x==8)
                return possibleMoves;
            if(pos[x+1][y].piece==null) {
                possibleMoves.add(pos[x+1][y]);
                if(x==1) {
                    if(pos[3][y].piece==null) {
                        possibleMoves.add(pos[3][y]);
                    }
                }
            }
            if((y>0)&&(pos[x+1][y-1].piece!=null)&&(pos[x+1][y-1].piece.color!=this.color)) {
                possibleMoves.add(pos[x+1][y-1]);
            }
            if((y<7)&&(pos[x+1][y+1].piece!=null)&&(pos[x+1][y+1].piece.color!=this.color)) {
                possibleMoves.add(pos[x+1][y+1]);
            }
        }
        return possibleMoves;
    }
}
