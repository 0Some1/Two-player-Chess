import javax.swing.*;

public class Initialize {
    public static Cell[][] cells=new Cell[8][8];
    public static King WK,BK;
    public static Knight WK1,WK2,BK1,BK2;
    public static Pawn[] wp=new Pawn[8];
    public static Pawn[]  bp=new Pawn[8];
    public static Queen WQ,BQ;
    public static Bishop WB1,WB2,BB1,BB2;
    public static Rook WR1,WR2,BR1,BR2;

    static {


        WK=new King("wking",new ImageIcon(Initialize.class.getResource("/resources/wk.png")),0,7,3);
        BK=new King("bking",new ImageIcon(Initialize.class.getResource("/resources/bk.png")),1,0,3);
        WK1=new Knight("wknight1",new ImageIcon(Initialize.class.getResource("/resources/wn.png")),0);
        WK2=new Knight("wknight2",new ImageIcon(Initialize.class.getResource("/resources/wn.png")),0);
        BK1=new Knight("bknight1",new ImageIcon(Initialize.class.getResource("/resources/bn.png")),1);
        BK2=new Knight("bknight2",new ImageIcon(Initialize.class.getResource("/resources/bn.png")),1);
        WQ=new Queen("wqeen",new ImageIcon(Initialize.class.getResource("/resources/wq.png")),0);
        BQ=new Queen("bqeen",new ImageIcon(Initialize.class.getResource("/resources/bq.png")),1);
        WB1=new Bishop("wbishop1",new ImageIcon(Initialize.class.getResource("/resources/wb.png")),0);
        WB2=new Bishop("wbishop2",new ImageIcon(Initialize.class.getResource("/resources/wb.png")),0);
        BB1=new Bishop("bbishop1",new ImageIcon(Initialize.class.getResource("/resources/bb.png")),1);
        BB2=new Bishop("bbishop2",new ImageIcon(Initialize.class.getResource("/resources/bb.png")),1);
        WR1=new Rook("wrook1",new ImageIcon(Initialize.class.getResource("/resources/wr.png")),0);
        WR2=new Rook("wrook2",new ImageIcon(Initialize.class.getResource("/resources/wr.png")),0);
        BR1=new Rook("brook1",new ImageIcon(Initialize.class.getResource("/resources/br.png")),1);
        BR2=new Rook("brook2",new ImageIcon(Initialize.class.getResource("/resources/br.png")),1);

        for(int i=0; i<8; i++) {
            wp[i]=new Pawn("wpawn"+(i+1),new ImageIcon(Initialize.class.getResource("/resources/wp.png")),0);
            bp[i]=new Pawn("bpawn"+(i+1),new ImageIcon(Initialize.class.getResource("/resources/bp.png")),1);
        }

        Piece piece;
        cells=new Cell[8][8];
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                piece = null;
                if (i == 0 && j == 0) {
                    piece = BR1;
                } else if (i == 0 && j == 7) {
                    piece = BR2;
                } else if (i == 7 && j == 0) {
                    piece = WR1;
                } else if (i == 7 && j == 7) {
                    piece = WR2;
                } else if (i == 0 && j == 1) {
                    piece = BK1;
                } else if (i == 0 && j == 6) {
                    piece = BK2;
                } else if (i == 7 && j == 1) {
                    piece = WK1;
                } else if (i == 7 && j == 6) {
                    piece = WK2;
                } else if (i == 0 && j == 2) {
                    piece = BB1;
                } else if (i == 0 && j == 5) {
                    piece = BB2;
                } else if (i == 7 && j == 2) {
                    piece = WB1;
                } else if (i == 7 && j == 5) {
                    piece = WB2;
                } else if (i == 0 && j == 3) {
                    piece = BK;
                } else if (i == 0 && j == 4) {
                    piece = BQ;
                } else if (i == 7 && j == 3) {
                    piece = WK;
                } else if (i == 7 && j == 4) {
                    piece = WQ;
                } else if (i == 1) {
                    piece = bp[j];
                } else if (i == 6) {
                    piece = wp[j];
                }

                Cell cell = new Cell(i, j, piece);
                cells[i][j] = cell;
            }

        }



    }
}
