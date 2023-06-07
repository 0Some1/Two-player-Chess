import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class OnlineBoard {
    private JFrame frame;
    private JButton[][] buttons=new JButton[8][8];
    private Cell[][] pos=Initialize.cells;
    private JPanel panel;
    private Player Player;
    private Cell firstCell;
    private int time;
    private int plusTime;
    private ArrayList<Cell> greenCells=new ArrayList<>();
    private int Turn=0;// 0 means whitePlayer turn and 1 is ...
    private JLabel labelTurn;
    private JLabel lblTimer;
    private JLabel lblNameW;
    private JLabel lblNameB;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream  objectOutputStream;
    boolean isTime;
    JLabel lblTurn;



    /**
     * Launch the application.
     */
    public static void run(Socket socket,String WPlayerName,int time,int plusTime,int color) {
        try {
            OnlineBoard window = new OnlineBoard(socket,WPlayerName,time,plusTime,color);
            window.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the application.
     */
    public OnlineBoard(Socket socket,String PlayerName, int time, int plusTime,int color) throws IOException {
        Player=new Player(PlayerName,color,false,time*60);
        this.time=time*60;
        this.plusTime=plusTime;
        this.socket=socket;
        scanner=new Scanner(this.socket.getInputStream());
        printWriter=new PrintWriter(this.socket.getOutputStream(),true);
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

        lblNameW = new JLabel();
        lblNameW.setBounds(904, 33, 107, 16);
        frame.getContentPane().add(lblNameW);

        lblNameB = new JLabel();
        lblNameB.setBounds(904, 58, 107, 16);
        frame.getContentPane().add(lblNameB);

        lblTimer = new JLabel("TIme");
        lblTimer.setFont(new Font("Dialog", Font.BOLD, 18));
        lblTimer.setBounds(886, 254, 107, 65);
        frame.getContentPane().add(lblTimer);

        lblTurn = new JLabel("Turn :");
        lblTurn.setBounds(816, 150, 55, 31);
        frame.getContentPane().add(lblTurn);

        labelTurn = new JLabel("White");
        labelTurn.setFont(new Font("Dialog", Font.BOLD, 15));
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

        if (Player.color==0){
            lblNameW.setText(Player.name);
            lblNameB.setText("anonymous");
            isTime=true;
            new TimerPlayer().start();
        }else {
            lblNameB.setText(Player.name);
            lblNameW.setText("anonymous");
        }

        if (Turn!=Player.color) {
            new GetPoses().start();
        }
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j <8 ; j++) {
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int x=Integer.parseInt(e.getActionCommand().split(" ")[0]);
                        int y=Integer.parseInt(e.getActionCommand().split(" ")[1]);
                        cleanAllBorders();
                        if (Turn==Player.color){
                            if (firstCell==null && pos[x][y]!=null && pos[x][y].piece.color==Player.color){
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


                            }else if (firstCell!=null){
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
                                        printWriter.println(firstCell.x+" "+firstCell.y+" "+x+" "+y+" check");
                                        pos[sideKing.x][sideKing.y].isChecked=true;
                                        if (checkmate(sideKing.color)) {
                                            //The game is end here
                                            printWriter.println(firstCell.x+" "+firstCell.y+" "+x+" "+y+" end");
                                            gameFinished(Turn);
                                        }
                                    }else {
                                        printWriter.println(firstCell.x+" "+firstCell.y+" "+x+" "+y);
                                    }


                                    firstCell=null;
                                    changeIcon();
                                    changeTurn();
                                    isTime=false;
                                    time+=plusTime;
                                    GetPoses getPoses=new GetPoses();
                                    getPoses.start();

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

        if (turn==Player.color){
            JOptionPane.showMessageDialog(frame,"Game is End! "+Player.name+" won");
            frame.dispose();
            Welcome.main(null);
        }else {
            JOptionPane.showMessageDialog(frame,"Game is End! "+"OtherPlayer"+" won");
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

    class  TimerPlayer extends Thread{

        @Override
        public void run() {

            int min,sec;

            while (Player.time > 0 && isTime) {
                min=Player.time/60;
                sec=Player.time%60;
                lblTimer.setText(String.valueOf(min)+":"+(sec>=10?String.valueOf(sec):"0"+String.valueOf(sec)));
                Player.time--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if (Player.time==0){
                lblTimer.setText("Time is up!");
                printWriter.println("time");
                gameFinished(Turn^1);
            }




        }
    }

    public boolean isValidMove(Cell to){
        for (int i = 0; i <greenCells.size() ; i++) {
            if (greenCells.get(i).x==to.x &&greenCells.get(i).y==to.y){
                return true;
            }
        }
        return false;

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

    public void cleanAllBorders(){
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

    public void changeTurn(){
        if (labelTurn.getText().equals("White")){
            labelTurn.setText("Black");
            labelTurn.setForeground(Color.BLACK);
        }else {
            labelTurn.setText("White");
            labelTurn.setForeground(Color.WHITE);
        }

        if (Player.color==0){
            lblTurn.setForeground(Color.white);
        }else {
            lblTurn.setForeground(Color.black);
        }

        Turn^=1;


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

    class GetPoses extends Thread{
        // this function help to getposes frm socket and other things maybe
        @Override
        public void run() {
            if (scanner.hasNextLine()){
                String s=scanner.nextLine();

                if (s.equals("time")){
                    gameFinished(Player.color);
                    return;
                }

                String[] strings=s.split(" ");
                int fx= Integer.parseInt(strings[0]);
                int fy= Integer.parseInt(strings[1]);
                int tx= Integer.parseInt(strings[2]);
                int ty= Integer.parseInt(strings[3]);


                if (strings.length<5){
                    pos[tx][ty].piece=pos[fx][fy].piece;
                    pos[fx][fy].piece=null;
                    if (pos[tx][ty].piece instanceof King){
                        getKing(pos[tx][ty].piece.color).x=tx;
                        getKing(pos[tx][ty].piece.color).y=ty;
                        if (!getKing(Player.color^1).isInDanger(pos)){
                            pos[getKing(Player.color^1).x][getKing(Player.color^1).y].isChecked=false;
                            pos[fx][fy].isChecked=false;
                        }
                    }
                    if (!getKing(Player.color^1).isInDanger(pos)){
                        pos[getKing(Player.color^1).x][getKing(Player.color^1).y].isChecked=false;
                    }
                    changeIcon();
                }else {
                    pos[tx][ty].piece=pos[fx][fy].piece;
                    pos[fx][fy].piece=null;
                    if (pos[tx][ty].piece instanceof King){
                        getKing(pos[tx][ty].piece.color).x=tx;
                        getKing(pos[tx][ty].piece.color).y=ty;
                    }
                    if (!getKing(Player.color^1).isInDanger(pos)){
                        pos[getKing(Player.color^1).x][getKing(Player.color^1).y].isChecked=false;
                    }
                    if (strings[4].contains("check")){
                        pos[getKing(Player.color).x][getKing(Player.color).y].isChecked=true;
                        changeIcon();
                    }else {
                        pos[getKing(Player.color).x][getKing(Player.color).y].isChecked=true;
                        changeIcon();
                        gameFinished(Turn^1);
                    }
                }

                isTime=true;
                new TimerPlayer().start();
                changeTurn();
            }
        }
    }

}
