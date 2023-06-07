import javax.swing.*;
import java.util.ArrayList;

public abstract class Piece implements Cloneable {
    String name;
    ImageIcon imageIcon = new ImageIcon();
    int color;  // 0 white     1 black
    protected ArrayList<Cell> possibleMoves = new ArrayList<Cell>();


    public abstract ArrayList<Cell> move(Cell pos[][],int x,int y);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Cell> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Cell> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public Piece getcopy() throws CloneNotSupportedException {
        return (Piece) this.clone();
    }
}
