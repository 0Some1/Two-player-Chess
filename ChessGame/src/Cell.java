
public class Cell implements Cloneable {

    Piece piece;
    int x,y;
    boolean isChecked=false;

    public Cell(int x,int y,Piece p) {
        this.x=x;
        this.y=y;
        piece=p;

    }

    public Cell(Cell cell) {
        this.x=cell.x;
        this.y=cell.y;

        if(cell.piece!=null) {
            try {
                setPiece(cell.piece.getcopy());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        else
            piece=null;
    }

    public Piece getpiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece(){   //Function to remove a piece from the cell{
        piece=null;
    }

    public boolean isChecked(){
        return isChecked;
    }

}
