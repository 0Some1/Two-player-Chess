import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;

public class OfflineBoard {

	private JFrame frame;
	private JButton[][] buttons=new JButton[8][8];
	private Cell[][] pos=Initialize.cells;
	private JPanel panel;
	private Player WPlayer;
	private Player BPlayer;
	private Cell firstCell;
	private int time;
	private int plusTime;
	private ArrayList<Cell> greenCells=new ArrayList<>();
	private int Turn=0;// 0 means whitePlayer turn and 1 is ...
    JLabel labelTurn;
    JLabel lblTimer;
    boolean timeW=false;
    boolean timeB=false;
    


	/**
	 * Launch the application.
	 */
	public static void run(String WPlayerName,String BPlayerName,int time,int plusTime) {
		try {
			OfflineBoard window = new OfflineBoard(WPlayerName,BPlayerName,time,plusTime);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public OfflineBoard(String WPlayerName,String BPlayerName,int time,int plusTime) {
		WPlayer=new Player(WPlayerName,0,true,time*60);
		BPlayer=new Player(BPlayerName,1,false,time*60);
		this.time=time*60;
		this.plusTime=plusTime;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1033, 835);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(186, 182, 187));
		frame.setLocationRelativeTo(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 800, 800);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(8, 8, 0, 0));
		
		JLabel lblWhitePlayer = new JLabel("White Player :");
		lblWhitePlayer.setBounds(813, 33, 88, 14);
		frame.getContentPane().add(lblWhitePlayer);
		
		JLabel lblBlackPlayer = new JLabel("Black Player :");
		lblBlackPlayer.setBounds(813, 60, 88, 14);
		frame.getContentPane().add(lblBlackPlayer);
		
		JLabel lblNameW = new JLabel(WPlayer.name);
		lblNameW.setBounds(904, 33, 107, 16);
		frame.getContentPane().add(lblNameW);
		
		JLabel lblNameB = new JLabel(BPlayer.name);
		lblNameB.setBounds(904, 58, 107, 16);
		frame.getContentPane().add(lblNameB);
		
		lblTimer = new JLabel("TIme");
		lblTimer.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTimer.setBounds(886, 254, 107, 65);
		frame.getContentPane().add(lblTimer);
		
		JLabel lblTurn = new JLabel("Turn :");
		lblTurn.setBounds(816, 150, 55, 31);
		frame.getContentPane().add(lblTurn);
		
		labelTurn = new JLabel("White");
		labelTurn.setFont(new Font("Dialog",Font.BOLD,15));
		labelTurn.setForeground(Color.WHITE);
		labelTurn.setBounds(855, 150, 138, 31);
		frame.getContentPane().add(labelTurn);


		for (int i = 0; i <8 ; i++) {
			for (int j = 0; j <8 ; j++) {
				buttons[i][j]=new JButton();
				buttons[i][j].setActionCommand(i+" "+j);
				if ((i+j)%2!=0){
					buttons[i][j].setBackground(new Color(251, 192, 122));
				}else {
					buttons[i][j].setBackground(new Color(251, 161, 45));
				}
				if (pos[i][j].piece != null){
					buttons[i][j].setIcon(pos[i][j].piece.imageIcon);
				}
				panel.add(buttons[i][j]);
			}
		}


//time starting
		timeW=true;
		new TimerWPlayer().start();

		for (int i = 0; i <8 ; i++) {
			for (int j = 0; j <8 ; j++) {
				buttons[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int x=Integer.parseInt(e.getActionCommand().split(" ")[0]);
						int y=Integer.parseInt(e.getActionCommand().split(" ")[1]);
						cleanAllBorders();

						if (firstCell==null){ // first act
						    if (pos[x][y].piece != null && Turn==pos[x][y].piece.color){

                                firstCell=pos[x][y];
                                greenCells=firstCell.piece.move(pos,x,y);
                                if (pos[x][y].piece instanceof King){
                                    greenCells=filterGreenCells(greenCells,pos[x][y]);
                                }else {
                                    assert getKing(Turn) != null;
                                    if (pos[getKing(Turn).x][getKing(Turn).y].isChecked){
                                        greenCells=filterGreenCells(greenCells,pos[x][y]);
                                    }else {
                                        if (!greenCells.isEmpty() && willKingBeInDanger(pos[x][y],greenCells.get(0))){
                                            greenCells.clear();
                                        }

                                    }
                                }

                                changeBorderBaseOnPiece(x,y);
                            }

						}else { // second act
						    if (isValidMove(pos[x][y])){
						        pos[x][y].piece=firstCell.piece;
						        pos[firstCell.x][firstCell.y].piece=null;

						        //changeIcon();
						        //changeTurn();
						        King currentK=getKing(Turn);
						        King sideKing=getKing(Turn^1);
                                assert currentK != null;
                                assert sideKing != null;

                                if (!currentK.isInDanger(pos)){
                                    pos[currentK.x][currentK.y].isChecked=false;
                                }
                                if (pos[x][y].piece instanceof King){
                                    int tempx=currentK.x;
                                    int tempy=currentK.y;
                                    currentK.x=x;
                                    currentK.y=y;
                                    if (!currentK.isInDanger(pos)){
                                        pos[tempx][tempy].isChecked=false;
                                    }
                                }



                                if(sideKing.isInDanger(pos)) {
                                    pos[sideKing.x][sideKing.y].isChecked=true;
                                    if (checkmate(sideKing.color)) {
                                        //The game is end here
										changeIcon();
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e1) {
											e1.printStackTrace();
										}
										gameFinished(Turn);

                                    }
                                }

                                if (pos[x][y].piece instanceof Pawn){
                                    Cell cell=pos[x][y];
                                    Object[] options = { "Queen","Rook","Knight","Bishop" };
                                    int res;
                                    if (cell.piece.color==0 && cell.x==0){
                                        res=JOptionPane.showOptionDialog(null, "What do you want to Pawn be ?", "Choices",
                                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                                options, options[0]);
                                        switch (res){
                                            case 0:
                                                Queen queen=new Queen("wqeen2",new ImageIcon(Initialize.class.getResource("/resources/wq.png")),0);
                                                cell.piece=queen;
                                                break;
                                            case 1:
                                                Rook rook=new Rook("wrook3",new ImageIcon(Initialize.class.getResource("/resources/wr.png")),0);
                                                cell.piece=rook;
                                                break;
                                            case 2:
                                                Knight knight=new Knight("wknight3",new ImageIcon(Initialize.class.getResource("/resources/wn.png")),0);
                                                cell.piece=knight;
                                                break;
                                            case 3:
                                                Bishop bishop=new Bishop("wbishop3",new ImageIcon(Initialize.class.getResource("/resources/wb.png")),0);
                                                cell.piece=bishop;
                                                break;
                                        }
                                    }

                                    if (cell.piece.color==1 && cell.x==7){
                                        res=JOptionPane.showOptionDialog(null, "What do you want to Pawn be ?", "Choices",
                                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                                options, options[0]);
                                        switch (res){
                                            case 0:
                                                Queen queen=new Queen("bqeen2",new ImageIcon(Initialize.class.getResource("/resources/bq.png")),1);
                                                cell.piece=queen;
                                                break;
                                            case 1:
                                                Rook rook=new Rook("brook3",new ImageIcon(Initialize.class.getResource("/resources/br.png")),1);
                                                cell.piece=rook;
                                                break;
                                            case 2:
                                                Knight knight=new Knight("bknight3",new ImageIcon(Initialize.class.getResource("/resources/bn.png")),1);
                                                cell.piece=knight;
                                                break;
                                            case 3:
                                                Bishop bishop=new Bishop("bbishop3",new ImageIcon(Initialize.class.getResource("/resources/bb.png")),1);
                                                cell.piece=bishop;
                                                break;
                                        }

                                    }

                                }

                                firstCell=null;
                                changeIcon();
                                changeTurn();
                            }else {

						        if(firstCell.piece instanceof Rook && pos[x][y].piece instanceof King && firstCell.piece.color == pos[x][y].piece.color){
						            //ghale giri

                                    if (Turn==0){
                                        if (firstCell.x==7 && firstCell.y==7 && x==7 && y==3){
                                            if (pos[7][4].piece==null && pos[7][5].piece==null && pos[7][6].piece==null){
                                                pos[7][4].piece=firstCell.piece;
                                                pos[7][7].piece=null;
                                                pos[7][5].piece=pos[x][y].piece;
                                                pos[x][y].piece=null;
                                                getKing(0).x=7;
                                                getKing(0).y=5;
                                            }
                                        }

                                        if (firstCell.x==7 && firstCell.y==0 && x==7 && y==3){
                                            if (pos[7][1].piece==null && pos[7][2].piece==null){
                                                pos[7][2].piece=firstCell.piece;
                                                pos[7][0].piece=null;
                                                pos[7][1].piece=pos[x][y].piece;
                                                pos[x][y].piece=null;
                                                getKing(0).x=7;
                                                getKing(0).y=1;
                                            }
                                        }


                                    }else {
                                        if (firstCell.x==0 && firstCell.y==0 && x==0 &&y==3){
                                            if (pos[0][1].piece==null && pos[0][2].piece==null ){
                                                pos[0][2].piece=firstCell.piece;
                                                pos[0][0].piece=null;
                                                pos[0][1].piece=pos[x][y].piece;
                                                pos[x][y].piece=null;
                                                getKing(1).x=0;
                                                getKing(1).y=1;
                                            }
                                        }

                                        if (firstCell.x==0 && firstCell.y==7 && x==0 &&y==3){
                                            if (pos[0][4].piece==null && pos[0][5].piece==null && pos[0][6].piece==null){
                                                pos[0][4].piece=firstCell.piece;
                                                pos[0][7].piece=null;
                                                pos[0][5].piece=pos[x][y].piece;
                                                pos[x][y].piece=null;
                                                getKing(1).x=0;
                                                getKing(1).y=5;
                                            }
                                        }

                                    }


                                    changeIcon();
                                    changeTurn();
                                }


						        firstCell=null;
                            }
						}



					}
				});
			}
		}


	}



	public void gameFinished(int turn){
		//game is end

		if (turn==0){
			JOptionPane.showMessageDialog(null,"Game is End! "+WPlayer.name+" won");
			frame.dispose();
			Welcome.main(null);
		}else {
			JOptionPane.showMessageDialog(null,"Game is End! "+BPlayer.name+" won");
			frame.dispose();
			Welcome.main(null);

		}

	}


	public King getKing(int color) {
	    //It's wide and clear like the sky !
		switch (color){
			case 0:
				return Initialize.WK;
			case 1:
				return Initialize.BK;

		}
		return null;
	}


	public boolean checkmate(int color) {
		//check mate checker
		ArrayList<Cell> dlist = new ArrayList<Cell>();
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if (pos[i][j].piece!=null && pos[i][j].piece.getColor()==color)
				{
					dlist.clear();
					dlist=pos[i][j].piece.move(pos, i, j);
					dlist= checksFilter(dlist,pos[i][j],color);
					if(dlist.size()!=0)
						return false;
				}
			}
		}
		return true;
	}

	public boolean willKingBeInDanger(Cell from,Cell to) {
		//check willKingBeInDanger or not if ...
		Cell newboardstate[][] = new Cell[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++)
			{
				newboardstate[i][j] = new Cell(pos[i][j]);
			}
		}

		if(newboardstate[to.x][to.y].piece!=null)
			newboardstate[to.x][to.y].removePiece();

		newboardstate[to.x][to.y].setPiece(newboardstate[from.x][from.y].piece);
		if(newboardstate[to.x][to.y].piece instanceof King)
		{
			((King)(newboardstate[to.x][to.y].piece)).setx(to.x);
			((King)(newboardstate[to.x][to.y].piece)).sety(to.y);
		}
		newboardstate[from.x][from.y].removePiece();
		if (((King)(newboardstate[getKing(Turn).getx()][getKing(Turn).gety()].piece)).isInDanger(newboardstate)==true)
			return true;
		else
			return false;
	}

	class  TimerWPlayer extends Thread{
//handle the white player time
        @Override
        public void run() {

            int min,sec;

            while (WPlayer.time > 0 && timeW) {
                min=WPlayer.time/60;
                sec=WPlayer.time%60;
                lblTimer.setText(String.valueOf(min)+":"+(sec>=10?String.valueOf(sec):"0"+String.valueOf(sec)));
                WPlayer.time--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


			if (WPlayer.time==0){
				lblTimer.setText("Time is up!");
				gameFinished(Turn^1);
			}




        }
    }

	class  TimerBPlayer extends Thread{
////handle the white player time
		@Override
		public void run() {


			int min,sec;

			while (BPlayer.time > 0 && timeB) {
				min=BPlayer.time/60;
				sec=BPlayer.time%60;
				lblTimer.setText(String.valueOf(min)+":"+(sec>=10?String.valueOf(sec):"0"+String.valueOf(sec)));
				BPlayer.time--;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


			if (BPlayer.time==0){
				lblTimer.setText("Time is up!");
				gameFinished(Turn^1);
			}



		}
	}

	public boolean isValidMove(Cell to){
		//is valid move
        for (int i = 0; i <greenCells.size() ; i++) {
            if (greenCells.get(i).x==to.x &&greenCells.get(i).y==to.y){
                return true;
            }
        }
	    return false;

    }

	public void cleanAllBorders(){
		//clean all boarders
		for (int i = 0; i <8 ; i++) {
			for (int j = 0; j <8 ; j++) {
				buttons[i][j].setBorder(null);

			}
		}
	}

	public ArrayList<Cell> checksFilter(ArrayList<Cell> greenCells, Cell from, int color) {
		//check pos and filter the green cells from moves that put king in danger
		ArrayList<Cell> tempList = new ArrayList<Cell>();
		Cell newPos[][] = new Cell[8][8];

		for (int k = 0; k <greenCells.size() ; k++) {
			int x,y;

			//cloning the pos for check filter
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++)
				{
					newPos[i][j] = new Cell(pos[i][j]);
				}
			}

			if(newPos[greenCells.get(k).x][greenCells.get(k).y].piece!=null) {
				newPos[greenCells.get(k).x][greenCells.get(k).y].removePiece();
			}
			newPos[greenCells.get(k).x][greenCells.get(k).y].setPiece(newPos[from.x][from.y].piece);
			assert getKing(color) != null;
			x=getKing(color).x;
			assert getKing(color) != null;
			y=getKing(color).y;
			if(newPos[greenCells.get(k).x][greenCells.get(k).y].piece instanceof King)
			{
				((King)(newPos[greenCells.get(k).x][greenCells.get(k).y].piece)).setx(greenCells.get(k).x);
				((King)(newPos[greenCells.get(k).x][greenCells.get(k).y].piece)).sety(greenCells.get(k).y);
				x=greenCells.get(k).x;
				y=greenCells.get(k).y;
			}
			newPos[from.x][from.y].removePiece();
			if ((!((King) (newPos[x][y].piece)).isInDanger(newPos)))
				tempList.add(greenCells.get(k));
		}



		return tempList;
	}

	public void changeBorderBaseOnPiece(int x,int y){
		if (pos[x][y].piece!=null){
			for (int i = 0; i <8 ; i++) {
				for (int j = 0; j <8 ; j++) {

					for (int k = 0; k <greenCells.size() ; k++) {
						if (i==greenCells.get(k).x && j==greenCells.get(k).y){
							buttons[i][j].setBorder(new LineBorder(Color.GREEN,2));
						}
					}

				}
			}
		}

	}

	public void changeIcon(){
		//change icon base on cells
		for (int i = 0; i <8 ; i++) {
			for (int j = 0; j <8 ; j++) {
				if (pos[i][j].piece != null){
					buttons[i][j].setIcon(pos[i][j].piece.imageIcon);
				}else {
                    buttons[i][j].setIcon(null);
                }

				if (pos[i][j].isChecked){
				    buttons[i][j].setBackground(Color.RED);
                }else {
                    if ((i+j)%2!=0){
                        buttons[i][j].setBackground(new Color(251, 192, 122));
                    }else {
                        buttons[i][j].setBackground(new Color(251, 161, 45));
                    }
                }
			}
		}


	}

	public ArrayList<Cell> filterGreenCells (ArrayList<Cell> greenCells, Cell from) {
		//check all the moves that from "fromCell" To cell are in green cells and filter them !!
		ArrayList<Cell> newlist = new ArrayList<Cell>();
		Cell newboardstate[][] = new Cell[8][8];
		for (int k = 0; k <greenCells.size() ; k++) {
			int x,y;
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++)
				{
					newboardstate[i][j] = new Cell(pos[i][j]);
				}
			}


			if(newboardstate[greenCells.get(k).x][greenCells.get(k).y].piece!=null)
				newboardstate[greenCells.get(k).x][greenCells.get(k).y].removePiece();
			newboardstate[greenCells.get(k).x][greenCells.get(k).y].setPiece(newboardstate[from.x][from.y].piece);
			x=getKing(Turn).getx();
			y=getKing(Turn).gety();
			if(newboardstate[from.x][from.y].piece instanceof King)
			{
				((King)(newboardstate[greenCells.get(k).x][greenCells.get(k).y].piece)).setx(greenCells.get(k).x);
				((King)(newboardstate[greenCells.get(k).x][greenCells.get(k).y].piece)).sety(greenCells.get(k).y);
				x=greenCells.get(k).x;
				y=greenCells.get(k).y;
			}
			newboardstate[from.x][from.y].removePiece();
			if ((((King)(newboardstate[x][y].piece)).isInDanger(newboardstate)==false))
				newlist.add(greenCells.get(k));
		}
		return newlist;
	}

	public void changeTurn(){
		//this function help to change turn
        if (labelTurn.getText().equals("White")){
            labelTurn.setText("Black");
            labelTurn.setForeground(Color.BLACK);
        }else {
            labelTurn.setText("White");
            labelTurn.setForeground(Color.WHITE);
        }
        if (Turn==0){
            timeW=false;
            WPlayer.time+=plusTime;
            timeB=true;
            new TimerBPlayer().start();

        }else {
            timeB=false;
            BPlayer.time+=plusTime;
            timeW=true;
            new TimerWPlayer().start();
        }
        Turn^=1;
    }


}
